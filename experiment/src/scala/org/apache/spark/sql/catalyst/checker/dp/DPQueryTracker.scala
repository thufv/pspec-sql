package org.apache.spark.sql.catalyst.checker.dp

import scala.collection.mutable.ListBuffer
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.HashMap
import scala.collection.JavaConversions._
import org.apache.spark.sql.catalyst.plans.logical._
import org.apache.spark.sql.catalyst.expressions.Expression
import scala.collection.mutable.HashSet
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.apache.spark.sql.catalyst.checker.LabelConstants._
import org.apache.spark.sql.catalyst.checker.util.TypeUtil._
import org.apache.spark.sql.catalyst.checker.util.CheckerUtil._
import org.apache.spark.sql.catalyst.plans.logical.Join
import org.apache.spark.sql.catalyst.expressions.Attribute
import org.apache.spark.sql.catalyst.plans.logical.Project
import scala.collection.mutable.Queue
import org.apache.spark.sql.catalyst.expressions._
import com.microsoft.z3.ArithExpr
import scala.collection.mutable.Stack
import com.microsoft.z3.BoolExpr
import com.microsoft.z3.Context
import org.apache.spark.sql.catalyst.checker.ColumnLabel
import org.apache.spark.sql.types.DataType
import org.apache.spark.sql.types.NumericType
import org.apache.spark.sql.catalyst.expressions.Predicate
import org.apache.spark.Logging
import scala.collection.mutable.Buffer
import com.microsoft.z3.RealExpr
import com.microsoft.z3.Expr
import com.microsoft.z3.IntExpr
import com.microsoft.z3.Status
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.NumericType
import org.apache.spark.sql.types.BooleanType
import scala.collection.mutable.HashMap
import org.apache.spark.sql.types.DataType
import com.microsoft.z3.ArithExpr
import org.apache.spark.sql.types.NumericType
import org.apache.spark.sql.types.DataType
import org.apache.spark.sql.types.NumericType
import com.microsoft.z3.ArithExpr
import org.apache.spark.sql.types.BooleanType
import com.microsoft.z3.ArithExpr
import org.apache.spark.sql.types.DataType
import org.apache.spark.sql.types.DataType
import org.apache.spark.sql.types.DataType
import org.apache.spark.sql.catalyst.structure.RedBlackBST
import org.apache.spark.sql.catalyst.structure.RedBlackBST.Node
import org.apache.spark.sql.types.IntegralType
import org.apache.spark.sql.types.FractionalType
import com.microsoft.z3.Sort
import scala.collection.mutable.PriorityQueue
import scala.collection.mutable.DoubleLinkedList
import org.apache.spark.sql.catalyst.checker.util.CheckerUtil
import edu.thu.ss.experiment.ExperimentConf

/**
 * tracking submitted dp-enabled queries for parallel composition theorem
 */
class DPQueryTracker[T <: DPPartition] private[dp] (budget: DPBudgetManager, limit: Int, partitionBuilder: (Context) => T)
    extends QueryTracker(budget) with Logging {

  val z3TimeOut = System.getProperty("max.solve.time", ExperimentConf.Max_Solve_Time).toInt;

  protected val context = new Context;

  CheckerUtil.context = context;

  protected val partitions = new ListBuffer[T];

  private val EmptyRange = Map.empty[String, Range];

  override def clear() {
    context.dispose();
  }

  def track(plan: Aggregate) {
    val builder = new SMTBuilder(context);
    val columns = new HashSet[String];
    val constraint = builder.buildSMT(plan, columns);

    //check satisfiability
    if (!satisfiable(constraint)) {
      logError("Range constraint unsatisfiable, ignore query");
      return ;
    }
    stat.queryNum += 1;
    stat.beginTiming(TrackerStat.Track_Time);

    val query = new DPQuery(constraint, columns, plan, EmptyRange);
    locatePartition(query);

    stat.endTiming(TrackerStat.Track_Time);

    if (stat.queryNum % 100 == 0) {
      print();
    }
  }

  def locatePartition(query: DPQuery) {
    locatePartitionBySMT(query);

  }

  protected def locatePartitionBySMT(query: DPQuery) {
    stat.beginTiming(TrackerStat.SMT_Time);

    val pi = partitions.iterator;
    var checked = 0;
    var found = false;
    var processed = 0;
    while (!found && pi.hasNext && checked < limit) {
      val partition = pi.next;
      processed += 1;
      if (partition.shareColumns(query)) {
        checked += 1;
        val checkStart = System.currentTimeMillis();
        val disjoint = partition.disjoint(query)
        val checkEnd = System.currentTimeMillis();
        if (checkEnd - checkStart > z3TimeOut) {
          logError(s"takes too much time ${checkEnd - checkStart}ms for constraint solving, drop the partition ${partition.getId} and query");
          removePartition(partition);
          stat.droppedPartition += 1;
          found = true;
        } else if (disjoint) {
          found = true;
          updatePartition(partition, query);
        }

      }
    }
    if (!found) {
      createPartition(query);
    }

    stat.profile(TrackerStat.Processed_Partitions, processed);
    stat.profile(TrackerStat.Checked_Partitions, checked);
    stat.endTiming(TrackerStat.SMT_Time);
  }

  def print() {
    stat.show;
    partitions.foreach(partition => {
      val queries = partition.getQueries.map(_.queryId).mkString(" ");
      println(s"Partition: ${partition.getId}\t Queries: {$queries}");
    });
  }

  def stop() {
    print();
  }
  protected def removePartition(partition: T) {
    partitions -= partition;
  }

  protected def createPartition(query: DPQuery): T = {
    stat.partitionNum += 1;

    val partition = partitionBuilder(context);
    partition.add(query);

    partitions.prepend(partition);

    return partition;
  }

  protected def updatePartition(partition: T, query: DPQuery) {
    partition.add(query);

    partitions -= (partition);
    val index = partitions.indexWhere(partition.getQueries.length < _.getQueries.length);
    if (index >= 0) {
      partitions.insert(index, partition);
    } else {
      partitions.append(partition);
    }

  }

}
