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
import edu.thu.ss.spec.lang.pojo.DataCategory
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

/**
 * tracking submitted dp-enabled queries for parallel composition theorem
 */
class DPQueryTracker[T <: DPPartition] private[dp] (budget: DPBudgetManager, limit: Int, partitionBuilder: (Context) => T)
  extends QueryTracker(budget) with Logging {

  protected val context = new Context;
  protected val partitions = new ListBuffer[T];

  private val EmptyRange = Map.empty[String, Range];

  protected var pi: Iterator[T] = null;

  protected val tmpPartitions = new ArrayBuffer[DPQuery];

  protected var buildingTime = 0L;

  protected val printConstraint = System.getProperty("z3.print", "false").toBoolean;

  override def clear() {
    context.dispose();
  }

  def track(plan: Aggregate) {
    val start = System.currentTimeMillis();
    val builder = new SMTBuilder(context);
    val columns = new HashSet[String];
    val constraint = builder.buildSMT(plan, columns);
    if (printConstraint) {
      println(constraint.toString());
    }
    buildingTime = System.currentTimeMillis() - start;
    //check satisfiability
    if (!satisfiable(constraint, context)) {
      logWarning("Range constraint unsatisfiable, ignore query");
      return ;
    }
    collectDPQueries(plan, constraint, columns, resolveRange(plan));

  }

  def commit(failed: Set[Int]) {
    //put queries into partitions
    beforeCommit;

    currentQueries.foreach(query => {
      if (!failed.contains(query.queryId)) {
        stat.onTrackQuery;

        commitByConstraint(query);
        stat.endConstraintSolving;
        stat.addConstraintBuilding(buildingTime);
      }
    });
    afterCommit;
  }

  protected def resolveRange(plan: Aggregate) = EmptyRange;

  protected def beforeCommit() {
    pi = partitions.iterator;

  }

  protected def afterCommit() {
    tmpPartitions.foreach(createPartition(_));
    tmpPartitions.clear;
    currentQueries.foreach(_.clear);
    currentQueries.clear;
    budget.show;

    if (stat.queryNum % 100 == 0) {
      stat.show;
      partitions.foreach(partition => {
        val queries = partition.getQueries.map(_.queryId).mkString(" ");
        println(s"Partition: ${partition.getId}\t Queries: {$queries}");
      });
    }

  }

  protected def commitByConstraint(query: DPQuery) {
    var checked = 0;
    var found = false;
    val start = System.currentTimeMillis();
    while (!found && pi.hasNext && checked < limit) {
      val partition = pi.next;
      if (partition.shareColumns(query)) {
        val checkStart = System.currentTimeMillis();
        if (partition.disjoint(query)) {
          found = true;
          updatePartition(partition, query);
        }
        val checkEnd = System.currentTimeMillis();
        if (checkEnd - checkStart > System.getProperty("z3.timeout", "5").toInt * 1024) {
          println(s"takes too much time ${checkEnd - checkStart}ms for constraint solving, drop the partition ${partition.getId} and query");
          removePartition(partition);
          stat.onCreatePartition;
          return ;
        } else {
          checked += 1;
        }
      }
    }
    println(s"checked $checked partitions in ${System.currentTimeMillis() - start} ms");
    if (!found) {
      tmpPartitions.append(query);
    }
  }

  protected def removePartition(partition: T) {
    partitions -= partition;
  }

  protected def createPartition(query: DPQuery): T = {
    stat.onCreatePartition;

    val partition = partitionBuilder(context);
    partition.add(query);

    partitions.prepend(partition);

    logWarning("fail to locate a disjoint partition, create a new one");
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
