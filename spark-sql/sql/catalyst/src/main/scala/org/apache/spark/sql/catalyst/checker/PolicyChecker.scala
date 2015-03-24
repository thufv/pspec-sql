package org.apache.spark.sql.catalyst.checker

import scala.collection.mutable.Set
import scala.collection.mutable.Map
import org.apache.spark.Logging
import scala.collection.JavaConverters._
import edu.thu.ss.spec.meta.MetaRegistry
import edu.thu.ss.spec.lang.pojo.ExpandedRule
import edu.thu.ss.spec.lang.pojo.Policy
import edu.thu.ss.spec.lang.parser.PolicyParser
import edu.thu.ss.spec.meta.xml.XMLMetaRegistryParser
import org.apache.spark.sql.catalyst.analysis.Catalog
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import edu.thu.ss.spec.lang.pojo.DataCategory
import edu.thu.ss.spec.lang.pojo.Restriction
import edu.thu.ss.spec.global.MetaManager
import edu.thu.ss.spec.lang.pojo.DataRef
import edu.thu.ss.spec.lang.pojo.Desensitization
import scala.collection.mutable.HashSet
import edu.thu.ss.spec.lang.pojo.Action

/**
 * interface for privacy checker
 */
trait PolicyChecker extends Logging {

  def check(projectionPaths: Map[Policy, Map[DataCategory, Set[Path]]], conditionPaths: Map[Policy, Map[DataCategory, Set[Path]]], policies: collection.Set[Policy]): Unit;
}

class SparkPolicyChecker extends PolicyChecker with Logging {

  lazy val user = MetaManager.currentUser();
  var policies: collection.Set[Policy] = null;

  var projectionPaths: Map[Policy, Map[DataCategory, Set[Path]]] = null;
  var conditionPaths: Map[Policy, Map[DataCategory, Set[Path]]] = null;
  var violated = false;

  def check(projectionPaths: Map[Policy, Map[DataCategory, Set[Path]]], conditionPaths: Map[Policy, Map[DataCategory, Set[Path]]], policies: collection.Set[Policy]): Unit = {
    if (policies.size == 0) {
      return ;
    }
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