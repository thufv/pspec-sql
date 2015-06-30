package org.apache.spark.sql.catalyst.checker.dp;

import com.microsoft.z3.BoolExpr
import scala.collection.mutable.ArrayBuffer
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.apache.spark.sql.catalyst.expressions.Expression
import org.apache.spark.sql.catalyst.expressions.AggregateExpression
import org.apache.spark.SparkConf
import org.apache.spark.sql.catalyst.checker.SparkChecker

object TrackerStatistics {
  private var _stat = new TrackerStatistics;

  def reset() = _stat = new TrackerStatistics;

  def get = _stat;

}

class TrackerStatistics private () {
  private var constraintBuildingTime = 0L;
  private var constraintSolvingTime = 0L;
  private var indexHittingTime = 0L;
  var indexHit = 0;
  var queryNum = 0;
  private var partitionNum = 0;
  //  val trackingTimes = new ArrayBuffer[Int](5000);
  // val budgetUsages = new ArrayBuffer[Int](5000);
  private var startTime = 0L;

  def budgetUsage = partitionNum;

  def totalTime = constraintSolvingTime + indexHittingTime;

  def averageTime = totalTime / queryNum;

  def onCreatePartition {
    //    if (budgetUsages.length == 0) {
    //      budgetUsages.append(1);
    //    } else {
    //      budgetUsages.append(budgetUsages.last + 1);
    //    }
    partitionNum += 1;
  }

  def onTrackQuery {
    queryNum += 1;
  }

  def onIndexHit {
    indexHit += 1;
  }

  def startTiming {
    startTime = System.currentTimeMillis();
  }

  def addConstraintBuilding(time: Long) {
    constraintBuildingTime += time;
  }

  def endConstraintSolving {
    val time = System.currentTimeMillis() - startTime;
    if (time < 20 * 1000) {
      constraintSolvingTime += time;
    } else {
      println(s"warning: too long for constraint solving $time ms");
    }
    //    trackingTimes.append(time.toInt);
  }

  def endIndexHitting {
    val time = System.currentTimeMillis() - startTime;
    if (time < 20 * 1000) {
      indexHittingTime += time;
    } else {
      println(s"warning: too long for index hitting $time ms");
    }
    //   trackingTimes.append(time.toInt);
  }

  def onSuccess {
    //    budgetUsages.append(budgetUsages.last);
  }

  def show {
    println(s"Total Number of Queries:\t$queryNum");
    println(s"Total Number of Partitions:\t$partitionNum");
    println(s"Total Number of Index Hit:\t$indexHit");
    println(s"Total Number of Constraint Solving:\t${queryNum - indexHit}");

    val avgConstraintBuilding = average(constraintBuildingTime, queryNum);
    println(s"Average Time of Constraint Building:\t$avgConstraintBuilding ms");

    val avgConstraintSolving = average(constraintSolvingTime, queryNum - indexHit);
    println(s"Average Time of Constraint Solving:\t$avgConstraintSolving ms");

    val avgIndexHitting = average(indexHittingTime, indexHit);
    println(s"Average Time of Index Hitting:\t$avgIndexHitting ms");

    val avgCommitting = average(constraintSolvingTime + indexHittingTime, queryNum);
    println(s"Average Time of Commiting:\t$avgCommitting ms");

    val avgTracking = average(constraintBuildingTime + constraintSolvingTime + indexHittingTime, queryNum);
    println(s"Average Time of Query Tracking:\t$avgTracking ms");

    //  val detail = trackingTimes.mkString(" ");
    //  println(s"Detailed Time of Query Tracking:\t$detail");
  }

  private def average(time: Long, num: Int): Long = {
    if (num == 0) {
      return 0;
    } else {
      return time / num;
    }
  }

}

object QueryTracker {

  def newInstance(budget: DPBudgetManager, conf: SparkConf): QueryTracker = {
    val tracking = conf.getBoolean(SparkChecker.Conf_Privacy_Tracking, true);
    val index = conf.getBoolean(SparkChecker.Conf_Privacy_Tracking_Index, true);
    val limit = conf.getInt(SparkChecker.Conf_Privacy_Tracking_Limit, Integer.MAX_VALUE);
    if (tracking) {
      budget match {
        case fine: FineBudgetManager => {
          if (index) {
            new IndexedQueryTracker[FinePartition](fine, limit, new FinePartition(_, budget));
          } else {
            new DPQueryTracker[FinePartition](fine, limit, new FinePartition(_, budget));
          }
        }
        case global: GlobalBudgetManager => {
          if (index) {
            new IndexedQueryTracker[GlobalPartition](global, limit, new GlobalPartition(_, budget));
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
  protected val currentQueries = new ArrayBuffer[DPQuery];

  protected val stat = TrackerStatistics.get;

  def track(plan: Aggregate);

  def commit(failed: Set[Int]);

  def clear() {}

  def testBudget() {
    val copy = budget.copy;
    currentQueries.foreach(copy.consume(_));
  }

  protected def collectDPQueries(plan: Aggregate, constraint: BoolExpr, columns: Set[String], ranges: Map[String, Range]) {
    //decompose the aggregate query
    plan.aggregateExpressions.foreach(agg => agg.foreach(expr => {
      expr match {
        //TODO luochen remove a.sensitivity>0 temporarily
        case a: AggregateExpression if (a.enableDP) =>
          currentQueries.append(new DPQuery(constraint, columns, a, plan, ranges));
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
    currentQueries.foreach(budget.consume(_));
    currentQueries.clear;
    budget.show;
  }
}