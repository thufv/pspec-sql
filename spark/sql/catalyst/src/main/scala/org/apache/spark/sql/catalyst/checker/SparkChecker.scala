package org.apache.spark.sql.catalyst.checker

import scala.collection.JavaConversions._
import org.apache.spark.Logging
import org.apache.spark.sql.catalyst.analysis.Catalog
import org.apache.spark.sql.catalyst.checker.dp.DPBudgetManager
import org.apache.spark.sql.catalyst.checker.dp.DPEnforcer
import org.apache.spark.sql.catalyst.checker.dp.TableInfo
import org.apache.spark.sql.catalyst.expressions.Attribute
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import org.apache.spark.sql.types
import org.apache.spark.sql.types.DataType
import edu.thu.ss.spec.global.MetaManager
import edu.thu.ss.spec.lang.parser.PolicyParser
import edu.thu.ss.spec.lang.pojo.Policy
import edu.thu.ss.spec.lang.pojo.UserCategory
import edu.thu.ss.spec.meta.ArrayType
import edu.thu.ss.spec.meta.BaseType
import edu.thu.ss.spec.meta.CompositeType
import edu.thu.ss.spec.meta.MapType
import edu.thu.ss.spec.meta.MetaRegistry
import edu.thu.ss.spec.meta.PrimitiveType
import edu.thu.ss.spec.meta.StructType
import edu.thu.ss.spec.meta.xml.XMLMetaRegistryParser
import scala.collection.mutable.HashSet
import scala.collection.mutable.HashSet
import scala.collection.mutable.ArrayBuffer
import org.apache.spark.sql.catalyst.checker.dp.DPQueryTracker
import org.apache.spark.sql.catalyst.checker.dp.QueryTracker
import org.apache.spark.SparkConf
import org.apache.spark.sql.catalyst.checker.SparkChecker._
import scala.collection.mutable.HashMap

/**
 * TODO luochen a temporary solution for returning back privacy budgets
 * the spark checker should be accessed by the spark execution plans
 */
object SparkChecker {
  val Conf_Privacy_Tracking = "spark.privacy.tracking";
  val Conf_Privacy_Tracking_Index = "spark.privacy.tracking.index";
  val Conf_Privacy_Tracking_Limit = "spark.privacy.tracking.limit";
  val Conf_Privacy_Refine = "spark.privacy.refine";
  val Conf_Privacy_Policy = "spark.privacy.policy";
  val Conf_Privacy_Meta = "spark.privacy.meta";

  private var _checker: SparkChecker = null;

  def set(checker: SparkChecker) {
    this._checker = checker;
  }

  def get(): SparkChecker = _checker;

}

/**
 * entrance class for spark privacy checker
 */
class SparkChecker(catalog: Catalog, conf: SparkConf) extends Logging {

  private var tableInfo: TableInfo = null;
  private var budgetManager: DPBudgetManager = null;
  private var running = false;
  private var error = false;

  private var epsilon = 0.0;
  private var accuracyProb: Double = 0;
  private var accurcayNoise: Double = 0;
  private var checkAccuracy: Boolean = false;
  private val trackers = new HashMap[UserCategory, QueryTracker];

  private val refineAttribute = conf.getBoolean(Conf_Privacy_Refine, true);

  lazy val user: UserCategory = MetaManager.currentUser();

  private val failedAggregates = new HashSet[Int];

  {
    val policyPath = conf.get(Conf_Privacy_Policy, "res/spark-policy.xml");
    val metaPath = conf.get(Conf_Privacy_Meta, "res/spark-meta.xml");
    loadPolicy(policyPath);
    loadMeta(metaPath, catalog);
  }

  private def loadPolicy(path: String): Unit = {
    val parser = new PolicyParser;
    try {
      val policy = parser.parse(path, true, false);
      budgetManager = DPBudgetManager(policy.getPrivacyParams(), user);
      accuracyProb = policy.getPrivacyParams().getProbability();
      accurcayNoise = policy.getPrivacyParams().getNoiseRatio();
      checkAccuracy = policy.getPrivacyParams().isCheckAccuracy();
    } catch {
      case e: Exception => error = true; logError(e.getMessage, e);
    }
  }

  private def loadMeta(path: String, catalog: Catalog): Unit = {
    val parser = new XMLMetaRegistryParser;
    try {
      val meta = parser.parse(path);
      val checker = new MetaChecker;
      error = checker.checkMeta(meta, catalog);
    } catch {
      case e: Exception => error = true; logError(e.getMessage, e);
    }
  }
  /**
   * wrap of spark checker
   */
  def check(user: UserCategory, plan: LogicalPlan, epsilon: Double) {
    if (!running) {
      return ;
    }
    val begin = System.currentTimeMillis();
    setEpsilon(epsilon);
    try {
      val propagator = new LabelPropagator;
      val policies = propagator(plan);

      val builder = new PathBuilder;
      val projects = new HashSet[Label];
      projects.++=(plan.projectLabels.values);
      val flows = builder(projects, plan.condLabels);

      val checker = new PolicyChecker(user, budgetManager, epsilon);
      checker.check(flows, policies);

      val tracker = trackers.getOrElseUpdate(user, newQueryTracker);
      val dpEnforcer = new DPEnforcer(tableInfo, tracker, epsilon, refineAttribute);
      dpEnforcer.enforce(plan);
    } finally {
      val end = System.currentTimeMillis();
      val time = end - begin;
      println(s"privacy checking finished in $time ms");
    }
  }

  def start(info: TableInfo) {
    if (error) {
      logError("SparkChecker fail to initialize, see messages above");
    } else {
      this.tableInfo = info;
      this.running = true;
      logWarning("SparkChecker successfully initialized");
    }
  }

  def pause() {
    running = false;
  }

  def resume() {
    if (!error) {
      running = true;
    }
  }

  def commit(user: UserCategory) {
    if (running) {
      val tracker = trackers.getOrElse(user, null);
      tracker.commit(failedAggregates);
      failedAggregates.clear;
    }
  }

  def failAggregate(dpId: Int) {
    failedAggregates.add(dpId);
  }

  def isCheckAccuracy = checkAccuracy;

  def getAccurcacyProb = accuracyProb;

  def getAccurarcyNoise = accurcayNoise;

  def getEpsilon = epsilon;

  def setEpsilon(epsilon: Double) {
    this.epsilon = epsilon;
  }

  private def newQueryTracker(): QueryTracker = {
    QueryTracker.newInstance(budgetManager, conf);
  }

}
