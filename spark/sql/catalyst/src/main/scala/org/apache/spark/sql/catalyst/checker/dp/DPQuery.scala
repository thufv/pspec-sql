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

object DPQuery {
  private var nextId = 0;

  def getId(): Int = {
    nextId += 1;
    nextId;
  }
}

class DPQuery(val constraint: BoolExpr, val columns: Set[String], var aggregate: AggregateExpression, var plan: Aggregate, var ranges: Map[String, Range]) extends Equals {
  val queryId = DPQuery.getId;

  aggregate.dpId = queryId;

  def canEqual(other: Any) = {
    other.isInstanceOf[org.apache.spark.sql.catalyst.checker.dp.DPQuery]
  }

  override def equals(other: Any) = {
    other match {
      case that: org.apache.spark.sql.catalyst.checker.dp.DPQuery => that.canEqual(DPQuery.this) && queryId == that.queryId;
      case _ => false
    }
  }

  override def hashCode() = {
    val prime = 41
    prime + queryId.hashCode;
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
abstract class DPPartition(val context: Context, val budget: DPBudgetManager) extends Equals with Comparable[DPPartition] {
  private val id = DPPartition.getId;

  private val queries = new ArrayBuffer[DPQuery];

  private var constraint: BoolExpr = context.mkFalse();

  private var ranges = Map.empty[String, Range];

  private var initialized = false;

  //record all effective columns
  private val columns = new HashSet[String];

  def getRanges = ranges;

  def getQueries = queries;

  def getId = id;

  def compareTo(that: DPPartition): Int = {
    return Integer.compare(queries.length, that.queries.length);
  }

  def add(query: DPQuery) {
    queries.append(query);
    if (!initialized) {
      ranges ++= query.ranges;
      initialized = true;
    } else {
      ranges = disjuncate(ranges, query.ranges);
    }

    constraint = context.mkOr(constraint, query.constraint).simplify().asInstanceOf[BoolExpr];
    columns ++= query.columns;

    updateBudget(query);
  }

  def disjoint(column: String, that: Range): Boolean = {
    val range = ranges.getOrElse(column, null);
    if (range == null) {
      return false;
    }
    return range.disjoint(that);
  }

  def shareColumns(query: DPQuery): Boolean = {
    return query.columns.exists(columns.contains(_));
  }

  def fastDisjoint(query: DPQuery): Boolean = {
    query.ranges.foreach(t => {
      val column = t._1;
      val range = t._2;
      if (disjoint(column, range)) {
        return true;
      }
    });
    return false;
  }

  def disjoint(query: DPQuery): Boolean = {
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
