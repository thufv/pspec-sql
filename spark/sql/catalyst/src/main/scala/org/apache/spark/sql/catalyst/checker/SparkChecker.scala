package org.apache.spark.sql.catalyst.checker

import scala.collection.JavaConverters.asScalaBufferConverter
import scala.collection.JavaConverters.asScalaSetConverter
import scala.collection.JavaConverters.mapAsScalaMapConverter
import org.apache.spark.Logging
import org.apache.spark.sql.catalyst.analysis.Catalog
import org.apache.spark.sql.catalyst.dp.DPBudgetManager
import org.apache.spark.sql.catalyst.dp.DPEnforcer
import org.apache.spark.sql.catalyst.dp.TableInfo
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

/**
 * entrance class for spark privacy checker
 */
object SparkChecker extends Logging {

  private var tableInfo: TableInfo = null;
  private var budgetManager: DPBudgetManager = null;
  private var initialized = false;
  private var error = false;
  var accuracyProb: Double = 0;
  var accurcayNoise: Double = 0;
  lazy val user: UserCategory = MetaManager.currentUser();

  /**
   * load policy and meta during startup.
   * may need to be modified in a pluggable way.
   */
  def init(catalog: Catalog, policyPath: String, metaPath: String): Unit = {
    loadPolicy(policyPath);
    loadMeta(metaPath, catalog);

  }

  def start(info: TableInfo) {
    if (error) {
      logError("SparkChecker fail to initialize, see messages above");
    } else {
      this.tableInfo = info;
      this.initialized = true;
      logWarning("SparkChecker successfully initialized");
    }

  }

  def loadPolicy(path: String): Unit = {
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

  def loadMeta(path: String, catalog: Catalog): Unit = {
    val parser = new XMLMetaRegistryParser;
    try {
      val meta = parser.parse(path);
      checkMeta(meta, catalog);
    } catch {
      case e: Exception => error = true; logError(e.getMessage, e);
    }
  }

  def commit(): Unit = {
    if (initialized) {
      budgetManager.commit;
    }
  }

  def rollback(): Unit = {
    if (initialized) {
      budgetManager.rollback;
    }
  }

  /**
   * wrap of spark checker
   */
  def apply(plan: LogicalPlan, epsilon: Double): Unit = {
    if (!initialized) {
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

      val dpEnforcer = new DPEnforcer(tableInfo, budgetManager, epsilon);
      dpEnforcer(plan);
    } finally {
      val end = System.currentTimeMillis();
      val time = end - begin;
      println(s"privacy checking finished in $time ms");
    }
  }

  /**
   * check whether column is properly labeled
   */
  private def checkMeta(meta: MetaRegistry, catalog: Catalog): Unit = {
    val databases = meta.getDatabases();
    for (db <- databases.asScala) {
      val dbName = Some(db._1); ;
      val tables = db._2.getTables().asScala;
      for (t <- tables) {
        val table = t._1;
        val relation = lookupRelation(catalog, dbName, table);
        if (relation == null) {
          logError(s"Error in MetaRegistry, table: ${table} not found in database: ${db._1}.");
        } else {
          t._2.getColumns().asScala.values.foreach(c => checkColumn(c.getName(), c.getType(), relation, table));
          t._2.getCondColumns().asScala.values.foreach(c => {
            c.getTypes().asScala.values.foreach(t => checkColumn(c.getName(), t, relation, table));
          });
          val conds = t._2.getAllConditions().asScala;
          val condColumns = new HashSet[String];
          conds.foreach(join => {
            val list = join.getJoinColumns().asScala;
            list.foreach(e => condColumns.add(e.column));
            val name = join.getJoinTable();
            val relation = lookupRelation(catalog, dbName, name);
            if (relation == null) {
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

  private def checkColumn(name: String, labelType: BaseType, relation: LogicalPlan, table: String) {
    val attribute = relation.output.find(attr => attr.name == name).getOrElse(null);
    if (attribute == null) {
      logError(s"Error in MetaRegistry. Column: $name not exist in table: ${table}");
      return ;
    }
    if (labelType != null) {
      if (checkDataType(name, labelType, attribute.dataType)) {
        logError(s"Error in MetaRegistry. Type mismatch for column: $name in table: $table. Expected type: ${attribute.dataType}");
      }
    }
  }

  private def checkDataType(name: String, labelType: BaseType, attrType: DataType): Boolean = {
    var error = false;
    labelType match {
      case _: PrimitiveType =>
      case _: CompositeType =>
      case array: ArrayType => {
        attrType match {
          case attrArray: types.ArrayType => {
            error = checkDataType(name, array.getItemType(), attrArray.elementType)
          }
          case _ => error = true;
        }
      }
      case struct: StructType => {
        attrType match {
          case attrStruct: types.StructType => {
            error = struct.getFields().asScala.values.exists(field => {
              val attrField = attrStruct.fields.find(f => f.name == field.name).getOrElse(null);
              if (attrField == null) {
                true;
              } else {
                checkDataType(name, field.getType(), attrField.dataType);
              }
            });
          }
          case _ => error = true;
        }
      }
      case map: MapType => {
        attrType match {
          case attrMap: types.MapType => {
            error = map.getEntries().asScala.values.exists(entry => {
              checkDataType(name, entry.valueType, attrMap.valueType);
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
