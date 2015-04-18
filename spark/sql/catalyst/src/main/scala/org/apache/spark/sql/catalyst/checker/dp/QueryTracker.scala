package org.apache.spark.sql.catalyst.checker.dp;

import com.microsoft.z3.BoolExpr
import scala.collection.mutable.ArrayBuffer
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.apache.spark.sql.catalyst.expressions.Expression
import org.apache.spark.sql.catalyst.expressions.AggregateExpression

object QueryTracker {

  def newQueryTracker(budget: DPBudgetManager): DPQueryTracker[_] = {
    budget match {
      case fine: FineBudgetManager => new DPQueryTracker[FinePartition](fine, new FinePartition(_, budget));
      case global: GlobalBudgetManager => new DPQueryTracker[GlobalPartition](global, new GlobalPartition(_, budget));
    }
  }

}

abstract class QueryTracker(val budget: DPBudgetManager) {
  protected val tmpQueries = new ArrayBuffer[DPQuery];

  def track(plan: Aggregate, ranges: Map[Expression, (Int, Int)]);

  def commit(failed: Set[Int]);

  def testBudget() {
    val copy = budget.copy;
    tmpQueries.foreach(copy.consume(_));
  }

  protected def collectDPQueries(plan: Aggregate, constraint: BoolExpr, ranges: Map[String, Interval]) {
    //decompose the aggregate query
    plan.aggregateExpressions.foreach(agg => agg.foreach(expr => {
      expr match {
        case a: AggregateExpression if (a.enableDP && a.sensitivity > 0) => tmpQueries.append(new DPQuery(constraint, a, plan, ranges));
        case _ =>
      }
    }));
  }

}

class DummyQueryTracker(budget: DPBudgetManager) extends QueryTracker(budget) {

  def track(plan: Aggregate, ranges: Map[Expression, (Int, Int)]) {
    collectDPQueries(plan, null, null);
  }

  def commit(failed: Set[Int]) {
    tmpQueries.foreach(budget.consume(_));
    tmpQueries.clear;
    budget.show;
  }
}