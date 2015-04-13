package org.apache.spark.sql.catalyst.checker.dp

import scala.collection.JavaConverters._
import scala.collection.mutable
import scala.collection.mutable.HashMap
import org.apache.spark.Logging
import org.apache.spark.sql.catalyst.checker.PrivacyException
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import edu.thu.ss.spec.lang.pojo.DataCategory
import edu.thu.ss.spec.lang.pojo.PrivacyParams
import edu.thu.ss.spec.lang.pojo.UserCategory
import edu.thu.ss.spec.lang.pojo.GlobalBudget
import edu.thu.ss.spec.lang.pojo.FineBudget
import org.apache.spark.sql.catalyst.expressions.Expression
import org.apache.spark.sql.catalyst.expressions.Attribute
import org.apache.spark.sql.catalyst.expressions.Cast
import org.apache.spark.sql.catalyst.expressions.Alias
import org.apache.spark.sql.catalyst.expressions.AggregateExpression
import org.apache.spark.sql.catalyst.checker.util.TypeUtil._
import scala.collection.mutable.HashSet
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan

object DPBudgetManager {
  def apply(param: PrivacyParams, user: UserCategory): DPBudgetManager = {
    val budget = param.getPrivacyBudget;

    budget match {
      case global: GlobalBudget => {
        val budget = global.getBudget(user);
        if (budget == null) {
          return new GlobalBudgetManager(0);
        } else {
          return new GlobalBudgetManager(budget);
        }
      }
      case fine: FineBudget => {
        val map = fine.getBudget(user);
        val converted = new HashMap[DataCategory, Double];
        map.asScala.foreach(t => converted += ((t._1, t._2)));
        return new FineBudgetManager(converted);
      }
    }
  }
}

trait DPBudgetManager {

  def defined(data: DataCategory): Boolean;

  def consume(data: DataCategory, epsilon: Double);

  def consume(query: DPQuery);

  def getBudget(data: DataCategory): Double;

  def copy(): DPBudgetManager;

  def show();
}

class GlobalBudgetManager(private var budget: Double) extends DPBudgetManager with Logging {

  def defined(data: DataCategory) = true;

  def consume(query: DPQuery) {
    val epsilon = query.aggregate.epsilon;
    consume(null, epsilon);
  }

  def consume(data: DataCategory, epsilon: Double) {
    if (budget - epsilon < 0) {
      throw new PrivacyException(s"No enough global privacy budget for the query. Privacy budget left: $budget");
    }
    budget -= epsilon;
  }

  def getBudget(data: DataCategory) = budget;

  def copy() = new GlobalBudgetManager(budget);

  def show() {
    logWarning(s"global privacy budget left: $budget");
  }
}

class FineBudgetManager(val budgets: mutable.Map[DataCategory, Double]) extends DPBudgetManager with Logging {

  def defined(data: DataCategory): Boolean = {
    return budgets.contains(data);
  }

  def consume(query: DPQuery) {
    val epsilon = query.aggregate.epsilon;

    val set = new HashSet[DataCategory];
    val attr = resolveSimpleAttribute(query.aggregate.children(0));
    set ++= query.plan.childLabel(attr).getDatas;
    query.plan.condLabels.foreach(set ++= _.getDatas);

    set.foreach(consume(_, epsilon));
  }

  def consume(data: DataCategory, epsilon: Double) {
    if (!defined(data)) {
      return ;
    }
    val budget = budgets.getOrElse(data, 0.0);
    if (budget - epsilon < 0) {
      throw new PrivacyException(s"No enough privacy budget for ${data.getId}, privacy budget left: $budget");
    }
    budgets.put(data, budget - epsilon);
  }

  def getBudget(data: DataCategory) = budgets.getOrElse(data, 0.0);

  def copy(): DPBudgetManager = {
    val map = new HashMap[DataCategory, Double];
    budgets.foreach(map += _);
    new FineBudgetManager(map);
  }

  def show() {
    val sb = new StringBuilder;
    for (t <- budgets) {
      sb.append(s"\tdata: ${t._1.getId()}, budget: ${t._2}\n");
    }
    logWarning(s"fine privacy budget left:\n ${sb.toString}");

  }

}
