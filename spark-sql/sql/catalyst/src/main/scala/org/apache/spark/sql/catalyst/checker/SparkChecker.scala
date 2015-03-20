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
import org.apache.spark.sql.catalyst.types.DataType
import edu.thu.ss.spec.meta.PrimitiveType
import edu.thu.ss.spec.meta.CompositeType
import org.apache.spark.sql.catalyst.types
import edu.thu.ss.spec.meta.ArrayType
import edu.thu.ss.spec.meta.StructType
import edu.thu.ss.spec.meta.MapType
import edu.thu.ss.spec.lang.pojo.Restriction

class SparkChecker extends PrivacyChecker with Logging {

  lazy val user = MetaManager.currentUser();
  var policies: collection.Set[Policy] = null;

  var projectionPaths: Map[Policy, Map[DataCategory, Set[Path]]] = null;
  var conditionPaths: Map[Policy, Map[DataCategory, Set[Path]]] = null;
  var violated = false;

  def check(projectionPaths: Map[Policy, Map[DataCategory, Set[Path]]], conditionPaths: Map[Policy, Map[DataCategory, Set[Path]]], policies: collection.Set[Policy]): Unit = {
    if (policies.size == 0) {
      return ;
    }
    violated = false;
    this.policies = policies;
    this.projectionPaths = projectionPaths;
    this.conditionPaths = conditionPaths;
    //check each rule

    policies.foreach(p => p.getExpandedRules().asScala.foreach(checkRule(_, p)));
    if (violated) {
      throw new PrivacyException("");
    }
  }

  /**
   * check rule
   *
   * @throws PrivacyException
   */
  private def checkRule(rule: ExpandedRule, policy: Policy): Unit = {
    // check user
    if (!rule.contains(user)) {
      return ;
    }
    var error = false;
    if (rule.isSingle()) {
      //single rule
      val access = new HashSet[DataCategory];
      val dataRef = rule.getDataRef();
      //collect all applicable data categories
      collectDatas(dataRef, access, policy);
      error = checkRestriction(rule, access, policy);
    } else {
      //association rule
      val association = rule.getAssociation();
      val accesses = Array.fill(association.getDimension())(new HashSet[DataCategory]);
      val dataRefs = association.getDataRefs();
      //collect all applicable data categories
      for (i <- 0 to dataRefs.size - 1) {
        collectDatas(dataRefs.get(i), accesses(i), policy);
      }
      error = checkRestrictions(rule, accesses, policy);
    }
    if (error) {
      violated = true;
      logError(s"The SQL query violates the rule: #${rule.getRuleId()}.");
    }
  }

  /**
   * collect all applicable data categories into access.
   * note that data ref/ association is essentially treated as n (>=1) buckets,
   * and we fill these buckets based on data categories accessed by the query.
   */
  private def collectDatas(ref: DataRef, access: Set[DataCategory], policy: Policy) {
    ref.getAction() match {
      case Action.All => {
        collectDatas(ref, projectionPaths, access, policy);
        collectDatas(ref, conditionPaths, access, policy);
      }
      case Action.Projection => collectDatas(ref, projectionPaths, access, policy);
      case Action.Condition => collectDatas(ref, conditionPaths, access, policy);
    }
  }

  private def collectDatas(ref: DataRef, paths: Map[Policy, Map[DataCategory, Set[Path]]], access: Set[DataCategory], policy: Policy): Unit = {
    if (ref.isGlobal()) {
      // all data categories are applicable
      paths.values.foreach(_.keys.foreach(data => {
        if (ref.contains(data)) {
          access.add(data);
        }
      }));
    } else {
      //only collect data categories that for current policy
      val map = paths.getOrElse(policy, null);
      if (map != null) {
        ref.getMaterialized().asScala.foreach(data => {
          if (map.contains(data)) {
            access.add(data);
          }
        });
      }
    }
  }

  /**
   * check restriction for single rule
   * only 1 restriction, and only 1 desensitization
   */
  private def checkRestriction(rule: ExpandedRule, access: HashSet[DataCategory], policy: Policy): Boolean = {
    if (access.size == 0) {
      return false;
    }

    val restriction = rule.getRestriction();
    if (restriction.isForbid()) {
      return true;
    }
    val de = restriction.getDesensitization();
    val ref = rule.getDataRef();
    for (data <- access) {
      if (checkDesensitization(ref, data, de, policy, ref.isGlobal())) {
        return true;
      }
    }

    false;
  }

  /**
   * check restriction for association rule
   */
  private def checkRestrictions(rule: ExpandedRule, accesses: Array[HashSet[DataCategory]], policy: Policy): Boolean = {
    //if any bucket is empty, then return.
    if (accesses.exists(_.size == 0)) {
      return false;
    }
    if (rule.getRestriction().isForbid()) {
      return true;
    }
    val array = new Array[DataCategory](accesses.length);
    return satisfyRestrictions(array, 0, rule, accesses, policy);
  }

  /**
   * check restrictions recursively.
   * for buckets with m1, m2, ..., mn elements, we need to check m1 * m2 *... mn combinations.
   */
  private def satisfyRestrictions(array: Array[DataCategory], i: Int, rule: ExpandedRule, accesses: Array[HashSet[DataCategory]], policy: Policy): Boolean = {
    if (i == accesses.length) {
      val restrictions = rule.getRestrictions();
      val association = rule.getAssociation();
      //a combination of element, and check whether exist a satisfied restriction

      return restrictions.exists(satisfyRestriction(_, array, rule, policy));
    } else {
      val access = accesses(i);
      for (data <- access) {
        array(i) = data;
        if (!satisfyRestrictions(array, i + 1, rule, accesses, policy)) {
          return false;
        }
      }
      return true;
    }
  }

  private def satisfyRestriction(res: Restriction, array: Array[DataCategory], rule: ExpandedRule, policy: Policy): Boolean = {
    if (res.isForbid()) {
      return false;
    }
    val des = res.getDesensitizations();

    var i = 0;
    for (de <- res.getDesensitizations()) {
      if (de != null) {
        val ref = rule.getAssociation().get(i);
        val data = array(i);
        val global = ref.isGlobal();
        if (!checkDesensitization(ref, data, de, policy, global)) {
          return false;
        }
      }
      i += 1;
    }
    return true;

  }

  /**
   * check whether a desensitization is satisfied
   */
  private def checkDesensitization(ref: DataRef, data: DataCategory, de: Desensitization, policy: Policy, global: Boolean): Boolean = {
    ref.getAction() match {
      case Action.All => if (checkOperations(de, data, projectionPaths, policy, global) || checkOperations(de, data, conditionPaths, policy, global)) {
        return true;
      }
      case Action.Projection => if (checkOperations(de, data, projectionPaths, policy, global)) {
        return true;
      }
      case Action.Condition => if (checkOperations(de, data, conditionPaths, policy, global)) {
        return true;
      }
    }
    false;
  }

  /**
   * check all paths for a data category is desensitized with one of the operations
   */
  private def checkOperations(de: Desensitization, data: DataCategory, paths: Map[Policy, Map[DataCategory, Set[Path]]], policy: Policy, global: Boolean): Boolean = {
    if (global) {
      for (map <- paths.values) {
        if (checkOperations(de, data, map)) {
          return true;
        }
      }
    } else {
      val map = paths.getOrElse(policy, null);
      if (map != null) {
        return checkOperations(de, data, map);
      }
    }
    return false;
  }

  private def checkOperations(de: Desensitization, data: DataCategory, paths: Map[DataCategory, Set[Path]]): Boolean = {
    val set = paths.getOrElse(data, null);
    if (set == null) {
      return false;
    }
    var ops = de.getOperations();
    if (ops == null) {
      //fall back to default desensitize operations
      ops = data.getOperations();
    }
    return !set.forall(path => path.ops.exists(ops.contains(_)));
  }

}

object SparkChecker extends Logging {

  /**
   * load policy and meta during startup.
   * may need to be modified in a pluggable way.
   */
  def init(catalog: Catalog, policyPath: String, metaPath: String): Unit = {
    loadPolicy(policyPath);
    loadMeta(metaPath, catalog);
  }

  def loadPolicy(path: String): Unit = {
    val parser = new PolicyParser;
    try {
      parser.parse(path, true);
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

  /**
   * wrap of spark checker
   */
  def apply(plan: LogicalPlan): Unit = {
    val begin = System.currentTimeMillis();

    try {
      val propagator = new LabelPropagator;
      val policies = propagator(plan);

      val builder = new PathBuilder;
      val (projectionPaths, conditionPaths) = builder(plan.projections.values.toSet, plan.conditions);

      val checker = new SparkChecker;
      checker.check(projectionPaths, conditionPaths, policies);

      val dpEnforcer = new DPEnforcer();
      dpEnforcer.enforce(plan);
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
      if (checkType(name, labelType, attribute.dataType)) {
        logError(s"Error in MetaRegistry. Type mismatch for column: $name in table: $table. Expected type: ${attribute.dataType}");
      }
    }
  }

  private def checkType(name: String, labelType: BaseType, attrType: DataType): Boolean = {
    var error = false;
    labelType match {
      case _: PrimitiveType =>
      case _: CompositeType =>
      case array: ArrayType => {
        attrType match {
          case attrArray: types.ArrayType => {
            error = checkType(name, array.getItemType(), attrArray.elementType)
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
                checkType(name, field.getType(), attrField.dataType);
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
              checkType(name, entry.valueType, attrMap.valueType);
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
      catalog.lookupRelation(database, table);
    } catch {
      case _: Throwable => null;
    }
  }
}
