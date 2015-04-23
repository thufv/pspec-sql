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

object DPQueryTracker {

  private val constants = new HashMap[String, Int];

  private var constantId = 0;

  def getConstant(string: String): Int = {
    val result = constants.get(string);
    result match {
      case Some(i) => i;
      case None => {
        val id = constantId;
        constants.put(string, id);
        constantId += 1;
        return id;
      }
    }
  }
}

/**
 * tracking submitted dp-enabled queries for parallel composition theorem
 */
class DPQueryTracker[T <: DPPartition] private[dp] (budget: DPBudgetManager, partitionBuilder: (Context) => T) extends QueryTracker(budget) with Logging {

  private case class IntWrapper(val value: Int) extends Comparable[IntWrapper] {
    def compareTo(other: IntWrapper): Int = {
      return Integer.compare(this.value, other.value);
    }
  }

  private class PartitionIndex(val column: String) {
    private val startTree = new RedBlackBST[IntWrapper, Set[T]];
    private val endTree = new RedBlackBST[IntWrapper, Set[T]];

    def addPartition(partition: T) {
      val intervals = partition.ranges.getOrElse(column, null);
      if (intervals != null) {
        intervals.foreach(addPartition(_, partition));
      }
    }

    def addPartition(interval: Interval, partition: T) {
      def add(point: Int, partition: T, tree: RedBlackBST[IntWrapper, Set[T]]) {
        val set = tree.get(IntWrapper(point));
        if (set != null) {
          set.add(partition);
        } else {
          val set = new HashSet[T];
          set.add(partition);
          tree.put(IntWrapper(point), set);
        }
      }

      add(interval.start, partition, startTree);
      add(interval.end, partition, endTree);
    }

    def removePartition(partition: T) {
      val intervals = partition.ranges.getOrElse(column, null);
      intervals.foreach(removePartition(_, partition));
    }

    def removePartition(interval: Interval, partition: T) {
      def remove(point: Int, partition: T, tree: RedBlackBST[IntWrapper, Set[T]]) {
        val set = tree.get(IntWrapper(point));
        assert(set != null);
        set.remove(partition);
        if (set == null) {
          tree.delete(IntWrapper(point));
        }
      }
      remove(interval.start, partition, startTree);
      remove(interval.end, partition, endTree);
    }

    def lookupDisjoint(interval: Interval): T = {
      val start = interval.start;
      val end = interval.end;

      val left = lookupByStart(end, interval, startTree.root);
      if (left != null) {
        return left;
      }
      return lookupByEnd(start, interval, endTree.root);
    }

    /**
     * lookup by the start tree, only consider nodes > point
     */
    private def lookupByStart(point: Int, interval: Interval, node: Node[IntWrapper, Set[T]]): T = {
      if (node == null) {
        return null.asInstanceOf[T];
      }
      val start = node.key;
      if (start.value > point) {
        val current = checkPartitions(interval, node.value);
        if (current != null) {
          return current;
        }
        val t = lookupByStart(point, interval, node.left);
        if (t != null) {
          return t;
        } else {
          return lookupByStart(point, interval, node.right);
        }
      } else {
        return lookupByStart(point, interval, node.right);
      }
    }

    private def lookupByEnd(point: Int, interval: Interval, node: Node[IntWrapper, Set[T]]): T = {
      if (node == null) {
        return null.asInstanceOf[T];
      }
      val end = node.key;
      if (end.value < point) {
        val current = checkPartitions(interval, node.value);
        if (current != null) {
          return current;
        }
        val t = lookupByEnd(point, interval, node.right);
        if (t != null) {
          return t;
        } else {
          return lookupByEnd(point, interval, node.left);
        }
      } else {
        return lookupByEnd(point, interval, node.left);
      }
    }

    private def checkPartitions(interval: Interval, partitions: Set[T]): T = {
      partitions.foreach(p => {
        if (p.disjoint(column, interval)) {
          return p;
        }
      });
      return null.asInstanceOf[T];
    }
  }

  private val context = new Context;

  private val partitions = new ArrayBuffer[T];

  private val partitionIndex = new HashMap[String, PartitionIndex];

  def track(plan: Aggregate, ranges: Map[Expression, (Int, Int)]) {
    if (ranges.values.exists(_ == null)) {
      logWarning("invalid range, ignore query");
      return ;
    }

    val builder = new SMTBuilder(context);
    val columns = new HashSet[String];
    val constraint = builder.buildSMT(plan, columns);
    println(constraint);
    //check satisfiability
    if (!satisfiable(constraint, context)) {
      logWarning("Range constraint unsatisfiable, ignore query");
      return ;
    }
    //check soundness of the estimated intervals
    val checkedRanges = new HashMap[String, Interval];
    ranges.foreach(t => {
      val attr = t._1;
      val range = t._2;
      //ensure no join table appear for each attribute
      val left = new HashSet[String];
      val right = new HashSet[String];
      val label = plan.childLabel(attr);
      collectAttributes(transformComplexLabel(label), left, right, plan, builder.model);

      if (left.size >= right.size) {
        //sound
        val column = getColumnString(getAttributeString(attr, plan));
        checkedRanges.put(column, Interval(range._1, range._2));
      }
    });
    collectDPQueries(plan, constraint, columns, checkedRanges);
  }

  private def collectAttributes(label: Label, left: Set[String], right: Set[String], plan: LogicalPlan, model: SMTModel) {
    def addAttribute(label: Label, table: String) {
      val str = getLabelString(label);
      left.add(str);
      val column = getColumnString(str);
      val set = model.getAttributesByColumn(column);
      if (set != null) {
        right ++= set;
      }
    }

    label match {
      case column: ColumnLabel => {
        addAttribute(column, column.table);
      }
      case func: FunctionLabel => {
        func.transform match {
          case Func_Union | Func_Except | Func_Intersect => func.children.foreach(collectAttributes(_, left, right, plan, model));
          case get if (isGetOperation(get)) => {
            addAttribute(func, func.getTables().head);
          }
        }
      }
      case _ =>

    }
  }

  def commit(failed: Set[Int]) {
    //put queries into partitions
    var success = true;
    var pi = partitions.length - 1;
    tmpQueries.withFilter(q => !failed.contains(q.dpId)).foreach(query => {
      if (success) {
        success = commitQueryByIndex(query);
      }
      if (!success) {
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
    });

    tmpQueries.foreach(_.clear);
    tmpQueries.clear;
    budget.show;
  }

  private def createPartition(query: DPQuery) {
    val partition = partitionBuilder(context);
    partition.init(query);

    partition.ranges.foreach(t => {
      val column = t._1;
      val index = partitionIndex.getOrElseUpdate(column, new PartitionIndex(column));
      index.addPartition(partition);
    });

    this.partitions.append(partition);
    logWarning("fail to locate a disjoint partition, create a new one");
  }

  private def updatePartition(partition: T, query: DPQuery) {
    partition.add(query);
    //
    val toRemove = new ArrayBuffer[String];
    partition.ranges.foreach(t => {
      val column = t._1;
      if (!query.ranges.contains(t._1)) {
        toRemove.append(column);
        val index = partitionIndex.getOrElse(column, null);
        //the column range is no longer invalid, remove all intervals
        if (index != null) {
          index.removePartition(partition);
        }
      }
    });
    toRemove.foreach(partition.ranges.remove(_));

    partition.ranges.foreach(t => {
      val column = t._1;
      val list = t._2;
      val interval = query.ranges.getOrElse(column, null);
      assert(interval != null);
      //union the list and interval
      val index = partitionIndex.getOrElse(column, null);

      val firstIndex = list.indexWhere(_.joint(interval));
      val lastIndex = list.lastIndexWhere(_.joint(interval));

      if (firstIndex >= 0) {
        //union takes effective
        val start = Math.min(interval.start, list(firstIndex).start);
        val end = Math.max(interval.end, list(lastIndex).end);
        val newInterval = Interval(start, end);
        for (i <- firstIndex to lastIndex) {
          index.removePartition(list(i), partition);
        }
        list.remove(firstIndex, lastIndex - firstIndex + 1);

        list.insert(firstIndex, newInterval);
        index.addPartition(newInterval, partition);
      } else {
        val i = list.indexWhere(_.start > interval.start);
        if (i >= 0) {
          list.insert(i, interval);
        } else {
          list.append(interval);
        }
        index.addPartition(interval, partition);

      }
    });

  }

  /**
   * lookup the index to commit the query
   */
  private def commitQueryByIndex(query: DPQuery): Boolean = {
    val ranges = query.ranges;
    ranges.foreach(t => {
      val column = t._1;
      val interval = t._2;
      val index = partitionIndex.getOrElse(column, null);
      if (index != null) {
        val partition = index.lookupDisjoint(interval);
        if (partition != null) {
          logWarning("find disjoint partition with index, no SMT solving needed");
          updatePartition(partition, query);
          return true;
        }
      }
    });

    return false;
  }

}