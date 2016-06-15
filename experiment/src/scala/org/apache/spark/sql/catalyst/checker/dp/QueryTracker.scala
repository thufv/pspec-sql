package org.apache.spark.sql.catalyst.checker.dp;

import com.microsoft.z3.BoolExpr
import scala.collection.mutable.ArrayBuffer
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.apache.spark.sql.catalyst.expressions.Expression
import org.apache.spark.sql.catalyst.expressions.AggregateExpression
import org.apache.spark.SparkConf
import java.io.PrintWriter
import java.io.PrintStream
import java.io.FileOutputStream
import edu.thu.ss.experiment.ExperimentParams
import java.io.File
import scala.collection.mutable.HashMap

object QueryTracker {

  def newInstance(budget: DPBudgetManager, tracking: Boolean, range: Boolean, limit: Int): QueryTracker = {
    if (tracking) {
      budget match {
        case global: GlobalBudgetManager => {
          if (range) {
            new RangeQueryTracker[GlobalPartition](global, limit, new GlobalPartition(_, budget));
          } else {
            new DPQueryTracker[GlobalPartition](global, limit, new GlobalPartition(_, budget));
          }
        }
      }
    } else {
      return new DummyQueryTracker(budget);
    }
  }

}

abstract class QueryTracker(val budget: DPBudgetManager) {
  protected val stat = TrackerStat.get;

  def track(plan: Aggregate);

  def clear() {}

  def stop();

}

class DummyQueryTracker(budget: DPBudgetManager) extends QueryTracker(budget) {

  def track(plan: Aggregate) {

  }

  def stop() {

  }
}