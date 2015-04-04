package org.apache.spark.sql.catalyst.checker

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer
import org.apache.spark.Logging
import org.apache.spark.sql.catalyst.analysis.Catalog
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import edu.thu.ss.spec.global.MetaManager
import edu.thu.ss.spec.lang.pojo.Action
import edu.thu.ss.spec.lang.pojo.DataCategory
import edu.thu.ss.spec.lang.pojo.DataRef
import edu.thu.ss.spec.lang.pojo.Desensitization
import edu.thu.ss.spec.lang.pojo.ExpandedRule
import edu.thu.ss.spec.lang.pojo.Policy
import edu.thu.ss.spec.lang.pojo.Restriction
import scala.collection.mutable.HashMap
import edu.thu.ss.spec.lang.pojo.DataRef
import scala.collection.mutable.HashMap
import org.apache.spark.sql.catalyst.checker.LabelConstants._
import org.apache.spark.sql.catalyst.expressions.AggregateExpression

/**
 * interface for privacy checker
 */
trait PolicyChecker extends Logging {

  def check(flows: Map[Policy, Set[Flow]], policies: Set[Policy]): Unit;
}
private class IndexEntry(val projects: Map[DataCategory, Set[Flow]], val conds: Map[DataCategory, Set[Flow]]) {

}

private class FlowIndex(val flows: Map[Policy, Set[Flow]]) {

  private val index = new HashMap[Policy, IndexEntry];

  def collect(policy: Policy, ref: DataRef): Seq[Flow] = {
    val action = ref.getAction();
    val list = new ListBuffer[Flow];
    if (ref.isGlobal()) {
      index.values.foreach(collect(_, ref, list));
    } else {
      val entry = index.getOrElse(policy, null);
      if (entry != null) {
        collect(entry, ref, list);
      }

      val set = flows.getOrElse(policy, null);
      if (set != null) {
        set.foreach(flow =>
          if (flow.action.ancestorOf(action) && ref.contains(flow.data)) {
            list.append(flow);
          });
      }
    }

    return list;
  }

  private def collect(entry: IndexEntry, ref: DataRef, list: ListBuffer[Flow]) {
    ref.getAction() match {
      case Action.Projection => {
        collect(entry.projects, ref, list);
      }
      case Action.Condition => {
        collect(entry.conds, ref, list);
      }
      case Action.All => {
        collect(entry.projects, ref, list);
        collect(entry.conds, ref, list);
      }
    }
  }

  private def collect(index: Map[DataCategory, Set[Flow]], ref: DataRef, list: ListBuffer[Flow]) {
    ref.getMaterialized().asScala.foreach(data => {
      val set = index.get(data);
      set match {
        case Some(s) => list ++= s;
        case None =>
      }
    });

  }
}

class SparkPolicyChecker extends PolicyChecker with Logging {

  lazy val user = MetaManager.currentUser();

  private var violated = false;

  private var flowIndex: FlowIndex = null;

  def check(flows: Map[Policy, Set[Flow]], policies: Set[Policy]): Unit = {
    if (policies.size == 0) {
      return ;
    }
    this.flowIndex = new FlowIndex(flows);

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
  private def checkRule(rule: ExpandedRule, policy: Policy) {
    // check user
    if (!rule.contains(user)) {
      return ;
    }
    val accesses = new Array[Seq[Flow]](rule.getDimension());
    if (rule.isSingle()) {
      val dataRef = rule.getDataRef();
      //collect all applicable data categories
      accesses(0) = flowIndex.collect(policy, dataRef);
    } else {
      val association = rule.getAssociation();
      val dataRefs = association.getDataRefs();
      for (i <- 0 to dataRefs.size - 1) {
        accesses(i) = flowIndex.collect(policy, dataRefs.get(i));
      }
    }
    if (accesses.exists(_.size == 0)) {
      return ;
    }

    val sat = checkFlows(rule, accesses, policy);

    if (!sat) {
      violated = true;
      logError(s"The SQL query violates the rule: #${rule.getRuleId()}.");
    }
  }

  /**
   * check restriction for association rule
   * return flows satisfy the rule
   */
  private def checkFlows(rule: ExpandedRule, accesses: Array[Seq[Flow]], policy: Policy): Boolean = {
    //if any bucket is empty, then return.
    if (rule.getRestriction().isForbid()) {
      return false;
    }
    val array = new Array[Flow](accesses.length);
    return checkFlows(array, 0, rule, accesses, policy);
  }

  /**
   * check restrictions recursively.
   * for buckets with m1, m2, ..., mn elements, we need to check m1 * m2 *... mn combinations.
   */
  private def checkFlows(flows: Array[Flow], i: Int, rule: ExpandedRule, accesses: Array[Seq[Flow]], policy: Policy): Boolean = {
    if (i == accesses.length) {
      val restrictions = rule.getRestrictions();
      val association = rule.getAssociation();
      //a combination of flows, and check whether exist a satisfied restriction
      //if exist, then no error (false)
      var minCost = Double.MaxValue;
      var minRes: Restriction = null;
      for (res <- restrictions) {
        //choose a restriction 
        if (checkRestriction(res, flows, rule, policy)) {
          val cost = estimateCost(res, flows);
          if (cost < minCost) {
            minCost = cost;
            minRes = res;
          }
        }
      }
      if (minRes == null) {
        //unsatisfy
        return false;
      } else {
        setDP(minRes, flows);
        return true;
      }
    } else {
      val seq = accesses(i);
      for (flow <- seq) {
        flows(i) = flow;
        if (!checkFlows(flows, i + 1, rule, accesses, policy)) {
          //error
          return false;
        }
      }
      return true;
    }
  }

  private def checkRestriction(res: Restriction, flows: Array[Flow], rule: ExpandedRule, policy: Policy): Boolean = {
    val des = res.getDesensitizations();
    var i = 0;
    for (de <- res.getDesensitizations()) {
      if (de != null) {
        val ref = rule.getAssociation().get(i);
        val flow = flows(i);
        if (!checkDesensitization(flow, de)) {
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
  private def checkDesensitization(flow: Flow, de: Desensitization): Boolean = {
    val required =
      if (de.getOperations() != null) {
        de.getOperations();
      } else {
        flow.data.getOperations();
      }
    if (!required.contains(flow.path.op)) {
      return false;
    }
    return required.contains(flow.path.op);
  }

  private def estimateCost(res: Restriction, flows: Array[Flow]): Double = {
    //TODO finish cost estimation
    0.0;
  }

  private def setDP(res: Restriction, flows: Array[Flow]) {
    var i = 0;
    res.getDesensitizations().foreach(de => {
      val flow = flows(i);
      if (de != null && Op_Aggregates.contains(flow.path.op)) {
        val agg = asAggregate(flow.path.func);
        agg.enableDP = true;
        //TODO should consume epsilon for temporarily
      }
      i += 1;
    });
  }

  private def asAggregate(func: Function): AggregateExpression = func.expression.asInstanceOf[AggregateExpression];
}