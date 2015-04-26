package org.apache.spark.sql.catalyst.checker.dp;

import com.microsoft.z3.BoolExpr
import scala.collection.mutable.ArrayBuffer
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.apache.spark.sql.catalyst.expressions.Expression
import org.apache.spark.sql.catalyst.expressions.AggregateExpression

object QueryTracker {

  def newInstance(budget: DPBudgetManager, tracking: Boolean, index: Boolean): QueryTracker = {
    if (tracking) {
      budget match {
        case fine: FineBudgetManager => {
          if (index) {
            new IndexedQueryTracker[FinePartition](fine, new FinePartition(_, budget));
          } else {
            new DPQueryTracker[FinePartition](fine, new FinePartition(_, budget));
          }
        }
        case global: GlobalBudgetManager => {
          if (index) {
            new IndexedQueryTracker[GlobalPartition](global, new GlobalPartition(_, budget));
          } else {
            new DPQueryTracker[GlobalPartition](global, new GlobalPartition(_, budget));
          }
        }
      }
    } else {
      return new DummyQueryTracker(budget);
    }
  }

}

abstract class QueryTracker(val budget: DPBudgetManager) {
  protected val tmpQueries = new ArrayBuffer[DPQuery];

  def track(plan: Aggregate);

  def commit(failed: Set[Int]);

  def testBudget() {
    val copy = budget.copy;
    tmpQueries.foreach(copy.consume(_));
  }

  protected def collectDPQueries(plan: Aggregate, constraint: BoolExpr, columns: Set[String], ranges: Map[String, Range]) {
    //decompose the aggregate query
    plan.aggregateExpressions.foreach(agg => agg.foreach(expr => {
      expr match {
        //TODO luochen remove a.sensitivity>0 temporarily
        case a: AggregateExpression if (a.enableDP) =>
          tmpQueries.append(new DPQuery(constraint, columns, a, plan, ranges));
        case _ =>
      }
    }));
  }

}

class DummyQueryTracker(budget: DPBudgetManager) extends QueryTracker(budget) {

  def track(plan: Aggregate) {
    collectDPQueries(plan, null, null, null);
  }

  def commit(failed: Set[Int]) {
    tmpQueries.foreach(budget.consume(_));
    tmpQueries.clear;
    budget.show;
  }
}