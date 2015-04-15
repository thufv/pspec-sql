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

/**
 * TODO luochen a temporary solution for returning back privacy budgets
 * the spark checker should be accessed by the spark execution plans
 */
object SparkChecker {
  private var _checker: SparkChecker = null;

  def set(checker: SparkChecker) {
    this._checker = checker;
  }

  def get(): SparkChecker = _checker;

}

/**
 * entrance class for spark privacy checker
 */
class SparkChecker(catalog: Catalog, policy: String, meta: String) extends Logging {

  private var tableInfo: TableInfo = null;
  private var budgetManager: DPBudgetManager = null;
  private var running = false;
  private var error = false;

  var epsilon = 0.0;
  var accuracyProb: Double = 0;
  var accurcayNoise: Double = 0;
  lazy val user: UserCategory = MetaManager.currentUser();

  private val failedAggregates = new HashSet[Int];
  private lazy val tracker = QueryTracker.newQueryTracker(budgetManager);

  loadPolicy(policy);
  loadMeta(meta, catalog);

  private def loadPolicy(path: String): Unit = {
    val parser = new PolicyParser;
    try {
      val policy = parser.parse(path, true, false);
      budgetManager = DPBudgetManager(policy.getPrivacyParams(), user);
      accuracyProb = policy.getPrivacyParams().getProbability();
      accurcayNoise = policy.getPrivacyParams().getNoiseRatio();
    } catch {
      case e: Exception => error = true; logError(e.getMessage, e);
    }
  }

  private def loadMeta(path: String, catalog: Catalog): Unit = {
    val parser = new XMLMetaRegistryParser;
    try {
      val meta = parser.parse(path);
      val checker = new MetaChecker;
      checker.checkMeta(meta, catalog);
    } catch {
      case e: Exception => error = true; logError(e.getMessage, e);
    }
  }
  /**
   * wrap of spark checker
   */
  def apply(plan: LogicalPlan) {
    if (!running) {
      return ;
    }
    val begin = System.currentTimeMillis();

    try {
      val propagator = new LabelPropagator;
      val policies = propagator(plan);

      val builder = new PathBuilder;
      val projects = new HashSet[Label];
      projects.++=(plan.projectLabels.values);
      val flows = builder(projects, plan.condLabels);

      val checker = new SparkPolicyChecker(budgetManager, epsilon);
      checker.check(flows, policies);

      val dpEnforcer = new DPEnforcer(tableInfo, tracker, epsilon);
      dpEnforcer(plan);
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

  def commit(): Unit = {
    if (running) {
      tracker.commit(failedAggregates);
      failedAggregates.clear;
    }
  }

  def failAggregate(dpId: Int) {
    failedAggregates.add(dpId);
  }

  private class MetaChecker {

    /**
     * check whether column is properly labeled
     */
    def checkMeta(meta: MetaRegistry, catalog: Catalog) {
      val databases = meta.getDatabases();
      for (db <- databases) {
        val dbName = Some(db._1); ;
        val tables = db._2.getTables();
        for (t <- tables) {
          val table = t._1;
          val relation = lookupRelation(catalog, dbName, table);
          if (relation == null) {
            error = true;
            logError(s"Error in MetaRegistry, table: ${table} not found in database: ${db._1}.");
          } else {
            t._2.getColumns().values.foreach(c => checkColumn(c.getName(), c.getType(), relation, table));
            t._2.getCondColumns().values.foreach(c => {
              c.getTypes().values.foreach(t => checkColumn(c.getName(), t, relation, table));
            });
            val conds = t._2.getAllConditions();
            val condColumns = new HashSet[String];
            conds.foreach(join => {
              val list = join.getJoinColumns();
              list.foreach(e => condColumns.add(e.column));
              val name = join.getJoinTable();
              val relation = lookupRelation(catalog, dbName, name);
              if (relation == null) {
                error = true;
                logError(s"Error in MetaRegistry, table: $name (joined with ${t._1}) not found in database: ${db._1}.");
              } else {
                val cols = list.map(_.target);
                cols.foreach(checkColumn(_, null, relation, table));
              }
            });
          }
        }
      }
    }

    private def checkColumn(name: String, labelType: BaseType, relation: LogicalPlan, table: String) = {
      val attribute = relation.output.find(attr => attr.name == name).getOrElse(null);
      if (attribute == null) {
        error = true;
        logError(s"Error in MetaRegistry. Column: $name not exist in table: ${table}");
      }
      if (labelType != null) {
        if (checkDataType(labelType, attribute.dataType)) {
          error = true;
          logError(s"Error in MetaRegistry. Type mismatch for column: $name in table: $table. Expected type: ${attribute.dataType}");
        }
      }
    }

    private def checkDataType(labelType: BaseType, attrType: DataType): Boolean = {
      var error = false;
      labelType match {
        case _: PrimitiveType =>
        case _: CompositeType =>
        case array: ArrayType => {
          attrType match {
            case attrArray: types.ArrayType => {
              error = array.getAllTypes().values.exists(item => checkDataType(item, attrArray.elementType));
            }
            case _ => error = true;
          }
        }
        case struct: StructType => {
          attrType match {
            case attrStruct: types.StructType => {
              error = struct.getAllTypes().exists(t => {
                val fieldName = t._1;
                val fieldType = t._2;
                val attrField = attrStruct.fields.find(f => f.name == fieldName).getOrElse(null);
                if (attrField == null) {
                  true;
                } else {
                  checkDataType(fieldType, attrField.dataType);
                }
              });
            }
            case _ => error = true;
          }
        }
        case map: MapType => {
          attrType match {
            case attrMap: types.MapType => {
              error = map.getAllTypes.values.exists(entry => {
                checkDataType(entry, attrMap.valueType);
              });
            }
            case _ => error = true;
          }
        }
      }
      error;
    }

    private def lookupRelation(catalog: Catalog, database: Option[String], table: String): LogicalPlan = {
      try {
        database match {
          case Some(db) => catalog.lookupRelation(db :: table :: Nil, None);
          case _ => catalog.lookupRelation(table :: Nil, None);
        }
      } catch {
        case _: Throwable => null;
      }
    }

  }

}
