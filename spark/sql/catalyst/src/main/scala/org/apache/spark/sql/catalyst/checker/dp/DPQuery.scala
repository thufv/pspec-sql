package org.apache.spark.sql.catalyst.checker.dp

import com.microsoft.z3.BoolExpr
import scala.collection.mutable.ArrayBuffer
import org.apache.spark.sql.catalyst.expressions.AggregateExpression
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import scala.collection.mutable.HashMap
import scala.collection.mutable.HashSet
import com.microsoft.z3.Context
import edu.thu.ss.spec.lang.pojo.DataCategory
import org.apache.spark.sql.catalyst.checker.util.CheckerUtil._
import org.apache.spark.sql.catalyst.checker.util.TypeUtil._
import scala.collection.mutable.Buffer
import scala.collection.mutable.ListBuffer

case class Interval(start: Int, end: Int) {

  def joint(other: Interval): Boolean = {
    return !disjoint(other);

  }

  def disjoint(other: Interval): Boolean = {
    return this.end < other.start || other.end < this.start;
  }

  def overlap(other: Interval): Boolean = {
    return !this.disjoint(other) && !(this.includes(other) || other.includes(this));
  }

  def includes(other: Interval): Boolean = {
    return start <= other.start && end >= other.end;
  }

}

object DPQuery {
  private var nextId = 0;

  def getId(): Int = {
    nextId += 1;
    nextId;
  }
}

class DPQuery(val constraint: BoolExpr, val columns: Set[String], var aggregate: AggregateExpression, var plan: Aggregate, var ranges: Map[String, Interval]) extends Equals {
  val dpId = DPQuery.getId;

  aggregate.dpId = dpId;

  def canEqual(other: Any) = {
    other.isInstanceOf[org.apache.spark.sql.catalyst.checker.dp.DPQuery]
  }

  override def equals(other: Any) = {
    other match {
      case that: org.apache.spark.sql.catalyst.checker.dp.DPQuery => that.canEqual(DPQuery.this) && dpId == that.dpId;
      case _ => false
    }
  }

  override def hashCode() = {
    val prime = 41
    prime + dpId.hashCode;
  }

  def clear() {
    //for gc
    aggregate = null;
    ranges = null;
    plan = null;
  }
}

object DPPartition {
  private var nextId = 0;

  private def getId = {
    nextId += 1;
    nextId;
  }
}

/**
 * a partition represents a set of disjoint queries, the total privacy cost takes the maximum
 */
abstract class DPPartition(val context: Context, val budget: DPBudgetManager) extends Equals {
  private val id = DPPartition.getId;

  private val queries = new ArrayBuffer[DPQuery];

  private var constraint: BoolExpr = context.mkFalse();

  val ranges = new HashMap[String, Buffer[Interval]];

  //record all effective columns
  private val columns = new HashSet[String];

  def add(query: DPQuery) {
    queries.append(query);
    constraint = context.mkOr(constraint, query.constraint).simplify().asInstanceOf[BoolExpr];
    columns ++= query.columns;
    updateBudget(query);
  }

  def init(query: DPQuery) {
    add(query);
    query.ranges.foreach(t => {
      val column = t._1;
      val interval = t._2;

      val buffer = new ArrayBuffer[Interval];
      buffer.append(interval);
      ranges.put(column, buffer);
    })
  }

  def disjoint(column: String, interval: Interval): Boolean = {
    val range = ranges.getOrElse(column, null);
    if (range == null) {
      return false;
    }
    range.foreach(r => {
      if (r.joint(interval)) {
        return false;
      }
    });
    return true;

  }

  def disjoint(query: DPQuery): Boolean = {
    var intersect = false;
    query.columns.foreach(column => { if (columns.contains(column)) intersect = true });
    if (!intersect) {
      return false;
    }

    val cond = context.mkAnd(constraint, query.constraint);
    return !satisfiable(cond, context);
  }

  def updateBudget(query: DPQuery);

  def canEqual(other: Any) = {
    other.isInstanceOf[org.apache.spark.sql.catalyst.checker.dp.DPPartition]
  }

  override def equals(other: Any) = {
    other match {
      case that: org.apache.spark.sql.catalyst.checker.dp.DPPartition => that.canEqual(DPPartition.this) && id == that.id;
      case _ => false
    }
  }

  override def hashCode() = {
    val prime = 41
    prime + id.hashCode
  }

}

private[dp] class GlobalPartition(context: Context, budget: DPBudgetManager) extends DPPartition(context, budget) {

  private var maximum = 0.0;

  def updateBudget(query: DPQuery) {
    if (query.aggregate.epsilon > maximum) {
      budget.consume(null, query.aggregate.epsilon - maximum);
      maximum = query.aggregate.epsilon;
    }
  }
}

private[dp] class FinePartition(context: Context, budget: DPBudgetManager) extends DPPartition(context, budget) {
  private val maximum = new HashMap[DataCategory, Double];

  def updateBudget(query: DPQuery) {
    val epsilon = query.aggregate.epsilon;

    val set = new HashSet[DataCategory];
    val attr = resolveSimpleAttribute(query.aggregate.children(0));
    set ++= query.plan.childLabel(attr).getDatas;
    query.plan.condLabels.foreach(set ++= _.getDatas);

    set.foreach(data => {
      val consumed = maximum.getOrElse(data, 0.0);
      if (consumed < epsilon) {
        budget.consume(data, epsilon - consumed);
        maximum.put(data, epsilon);
      }
    });

  }
}
