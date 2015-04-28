package org.apache.spark.sql.catalyst.checker.dp

import scala.collection.mutable.ListBuffer
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.HashMap
import scala.collection.JavaConversions._
import org.apache.spark.sql.catalyst.plans.logical._
import org.apache.spark.sql.catalyst.expressions.Expression
import scala.collection.mutable.HashSet
import org.jgrapht.graph.DirectedPseudograph
import org.jgrapht.graph.DefaultEdge
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.jgrapht.alg.ConnectivityInspector
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
import solver.variables.IntVar
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
import org.jgrapht.graph.Pseudograph
import org.apache.spark.sql.catalyst.checker.Label
import org.apache.spark.sql.catalyst.checker.FunctionLabel
import org.apache.spark.sql.catalyst.structure.RedBlackBST
import org.apache.spark.sql.catalyst.structure.RedBlackBST.Node
import org.apache.spark.sql.types.IntegralType
import org.apache.spark.sql.types.FractionalType
import com.microsoft.z3.Sort

/**
 * tracking submitted dp-enabled queries for parallel composition theorem
 */
class DPQueryTracker[T <: DPPartition] private[dp] (budget: DPBudgetManager, limit: Int, partitionBuilder: (Context) => T)
  extends QueryTracker(budget) with Logging {

  protected val context = new Context;

  protected val partitions = new Queue[T];

  private val EmptyRange = Map.empty[String, Range];

  protected var pi: Iterator[T] = null;

  protected val tmpPartitions = new ArrayBuffer[DPQuery];

  protected var buildingTime = 0L;

  def track(plan: Aggregate) {
    val start = System.currentTimeMillis();
    val builder = new SMTBuilder(context);
    val columns = new HashSet[String];
    val constraint = builder.buildSMT(plan, columns);
    buildingTime = System.currentTimeMillis() - start;
    //check satisfiability
    if (!satisfiable(constraint, context)) {
      logWarning("Range constraint unsatisfiable, ignore query");
      return ;
    }
    collectDPQueries(plan, constraint, columns, resolveRange(plan, builder.model.getColumnAttributes));

  }

  def commit(failed: Set[Int]) {
    //put queries into partitions
    beforeCommit;
    currentQueries.foreach(query => {
      if (!failed.contains(query.queryId)) {
        stat.onTrackQuery;
        stat.startTiming;

        commitByConstraint(query);
        stat.endConstraintSolving;
        stat.addConstraintBuilding(buildingTime);
      }
    });
    afterCommit;
  }

  protected def resolveRange(plan: Aggregate, columnAttrs: => Map[String, Seq[String]]) = EmptyRange;

  protected def beforeCommit() {
    pi = partitions.reverseIterator;

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
    var found = false;
    while (!found && pi.hasNext) {
      val partition = pi.next;
      if (partition.disjoint(query)) {
        found = true;
        updatePartition(partition, query);
      }
    }
    if (!found) {
      tmpPartitions.append(query);
    }
  }

  protected def createPartition(query: DPQuery): T = {
    stat.onCreatePartition;

    val partition = partitionBuilder(context);
    partition.add(query);
    this.partitions.enqueue(partition);
    if (this.partitions.length > limit) {
      this.partitions.dequeue;
    }
    logWarning("fail to locate a disjoint partition, create a new one");
    return partition;
  }

  protected def updatePartition(partition: T, query: DPQuery) {
    partition.add(query);
  }

}
