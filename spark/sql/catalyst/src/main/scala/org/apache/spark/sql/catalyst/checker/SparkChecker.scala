package org.apache.spark.sql.catalyst.checker

import scala.collection.JavaConverters.asScalaBufferConverter
import scala.collection.JavaConverters.asScalaSetConverter
import scala.collection.JavaConverters.mapAsScalaMapConverter
import scala.collection.mutable.Map
import scala.collection.mutable.Set
import org.apache.spark.Logging
import org.apache.spark.sql.catalyst.analysis.Catalog
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import edu.thu.ss.spec.global.MetaManager
import edu.thu.ss.spec.lang.parser.PolicyParser
import edu.thu.ss.spec.lang.pojo.Action
import edu.thu.ss.spec.lang.pojo.DataCategory
import edu.thu.ss.spec.lang.pojo.DataRef
import edu.thu.ss.spec.lang.pojo.Desensitization
import edu.thu.ss.spec.lang.pojo.ExpandedRule
import edu.thu.ss.spec.lang.pojo.Policy
import edu.thu.ss.spec.meta.MetaRegistry
import edu.thu.ss.spec.meta.xml.XMLMetaRegistryParser
import scala.collection.mutable.HashSet
import edu.thu.ss.spec.meta.BaseType
import org.apache.spark.sql.catalyst.expressions.Attribute
import org.apache.spark.sql.types.DataType
import edu.thu.ss.spec.meta.PrimitiveType
import edu.thu.ss.spec.meta.CompositeType
import org.apache.spark.sql.types
import edu.thu.ss.spec.meta.ArrayType
import edu.thu.ss.spec.meta.StructType
import edu.thu.ss.spec.meta.MapType
import edu.thu.ss.spec.lang.pojo.Restriction
import org.apache.spark.sql.catalyst.dp.TableInfo
import org.apache.spark.sql.catalyst.dp.DPEnforcer
import org.apache.spark.sql.catalyst.dp.DPBudget

/**
 * entrance class for spark privacy checker
 */
object SparkChecker extends Logging {

  private var tableInfo: TableInfo = null;
  private var budget: DPBudget = null;
  private var initialized = false;

  var accuracyProb: Double = 0;
  var accurcayNoise: Double = 0;

  /**
   * load policy and meta during startup.
   * may need to be modified in a pluggable way.
   */
  def init(catalog: Catalog, policyPath: String, metaPath: String): Unit = {
    loadPolicy(policyPath);
    loadMeta(metaPath, catalog);
  }

  def start(info: TableInfo) {
    this.tableInfo = info;
    this.initialized = true;
    logWarning("SparkChecker successfully initialized");
  }

  def loadPolicy(path: String): Unit = {
    val parser = new PolicyParser;
    try {
      val policy = parser.parse(path, true);
      budget = new DPBudget(policy.getPrivacyBudget().getBudget());
      accuracyProb = policy.getPrivacyBudget().getProbability();
      accurcayNoise = policy.getPrivacyBudget().getNoiseRatio();

    } catch {
      case e: Exception => logError(e.getMessage, e);
    }
  }

  def loadMeta(path: String, catalog: Catalog): Unit = {
    val parser = new XMLMetaRegistryParser;
    try {
      val meta = parser.parse(path);
      checkMeta(meta, catalog);
    } catch {
      case e: Exception => logError(e.getMessage, e);
    }
  }

  def commit(): Unit = {
    if (initialized) {
      budget.commit;
    }
  }

  def rollback(): Unit = {
    if (initialized) {
      budget.rollback;
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
      val (projectionPaths, conditionPaths) = builder(plan.projectLabels.values.toSet, plan.condLabels);

      val checker = new SparkPolicyChecker;
      checker.check(projectionPaths, conditionPaths, policies);

      val dpEnforcer = new DPEnforcer(tableInfo, budget, epsilon);
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
