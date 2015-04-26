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
class DPQueryTracker[T <: DPPartition] private[dp] (budget: DPBudgetManager, partitionBuilder: (Context) => T) extends QueryTracker(budget) with Logging {

  protected val context = new Context;

  protected val partitions = new ArrayBuffer[T];

  private val EmptyRange = Map.empty[String, Range];

  protected var pi = 0;

  def track(plan: Aggregate) {
    val builder = new SMTBuilder(context);
    val columns = new HashSet[String];
    val constraint = builder.buildSMT(plan, columns);
    println(constraint);
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
    tmpQueries.foreach(query => {
      if (!failed.contains(query.dpId)) {
        commitByConstraint(query);
      }
    });
    afterCommit;
  }

  protected def resolveRange(plan: Aggregate, columnAttrs: => Map[String, Seq[String]]) = EmptyRange;

  protected def beforeCommit() {
    pi = partitions.length - 1;
  }

  protected def afterCommit() {
    tmpQueries.foreach(_.clear);
    tmpQueries.clear;
    budget.show;
  }

  protected def commitByConstraint(query: DPQuery) {
    var found = false;
    while (!found && pi >= 0) {
      val partition = partitions(pi);
      if (partition.disjoint(query)) {
        found = true;
        updatePartition(partition, query);
      }
      pi -= 1;
    }
    if (!found) {
      createPartition(query);
    }
  }

  protected def createPartition(query: DPQuery): T = {
    val partition = partitionBuilder(context);
    partition.add(query);
    this.partitions.append(partition);
    logWarning("fail to locate a disjoint partition, create a new one");
    return partition;
  }

  protected def updatePartition(partition: T, query: DPQuery) {
    partition.add(query);
  }

}
