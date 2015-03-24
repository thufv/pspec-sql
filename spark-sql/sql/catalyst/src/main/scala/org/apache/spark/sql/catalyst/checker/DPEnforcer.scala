package org.apache.spark.sql.catalyst.checker

import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.apache.spark.sql.catalyst.expressions.Expression
import org.apache.spark.sql.catalyst.expressions.Alias
import org.apache.spark.sql.catalyst.expressions.BinaryExpression
import org.apache.spark.sql.catalyst.expressions.UnaryExpression
import org.apache.spark.sql.catalyst.expressions.Sum
import org.apache.spark.sql.catalyst.expressions.Average
import org.apache.spark.sql.catalyst.expressions.SumDistinct
import org.apache.spark.sql.catalyst.expressions.CountDistinct
import org.apache.spark.sql.catalyst.expressions.Min
import org.apache.spark.sql.catalyst.expressions.Max
import org.apache.spark.sql.catalyst.expressions.Count
import org.apache.spark.sql.catalyst.expressions.AggregateExpression
import org.apache.spark.sql.catalyst.plans.logical.BinaryNode
import org.apache.spark.sql.catalyst.plans.logical.LeafNode
import org.apache.spark.sql.catalyst.plans.logical.Project
import org.apache.spark.sql.catalyst.plans.logical.Generate
import org.apache.spark.sql.catalyst.plans.logical.Join
import org.apache.spark.sql.catalyst.plans.logical.Union
import org.apache.spark.sql.catalyst.plans.logical.Intersect
import org.apache.spark.sql.catalyst.plans.logical.Except
import org.apache.spark.sql.catalyst.expressions.Attribute
import org.apache.spark.sql.catalyst.plans.logical.UnaryNode
import org.apache.spark.sql.catalyst.trees
import org.apache.spark.sql.catalyst.expressions.AttributeReference
import org.apache.spark.sql.catalyst.checker.LabelConstants._
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.apache.spark.sql.catalyst.expressions.Cast
import org.apache.spark.Logging
import scala.collection.JavaConverters._
import scala.collection.immutable.List
import scala.collection.mutable.Buffer
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.HashMap
import org.apache.spark.sql.catalyst.graph._
import org.apache.spark.sql.catalyst.plans.logical.Join
import org.apache.spark.sql.catalyst.expressions.And
import org.apache.spark.sql.catalyst.expressions.Or
import org.apache.spark.sql.catalyst.expressions.EqualNullSafe
import org.apache.spark.sql.catalyst.expressions.EqualTo
import org.apache.spark.sql.catalyst.plans.logical.Project

object AggregateType extends Enumeration {
  type AggregateType = Value
  val Direct_Aggregate, Derived_Aggregate, Invalid_Aggregate, Insensitive_Aggregate = Value
}
/**
 * enforce dp for a query logical plan
 * should be in the last phase of query checking
 */
class DPEnforcer(val tableInfo: TableInfo, val budget: DPBudget, val epsilon: Double) extends Logging {
  private val MaxWeight = 10000;

  private class JoinGraph {
    val nodes = new HashMap[String, Node[String]];
    val edges = new AdjacencyList[String];

    def addTable(table: String) {
      nodes.put(table, new Node(table));
    }

    def initializeEdges() {
      for (n1 <- nodes.values) {
        for (n2 <- nodes.values) {
          if (n1 != n2) {
            this.edges.addEdge(n1, n2, MaxWeight);
          }
        }
      }
    }

    def updateEdge(table1: String, table2: String, weight: Double) {
      val node1 = nodes.getOrElse(table1, null);
      val node2 = nodes.getOrElse(table2, null);
      this.edges.updateEdge(node1, node2, weight);
    }

    def sensitivity(): Double = {
      val alg = new EdmondsChuLiu[String];
      val weights = nodes.values.map(node => alg.getMinBranching(node, edges).weightProducts(node));
      weights.max;
    }
  }

  def enforce(plan: LogicalPlan): Int = {
    plan match {
      case join: Join => {
        enforceJoin(join);
      }
      case unary: UnaryNode => {
        enforceUnary(unary);
      }
      case binary: BinaryNode => {
        enforceBinary(binary);
      }
      case leaf: LeafNode => {
        enforceLeaf(leaf);
      }
      case _ => 1
    }
  }

  private def enforceUnary(unary: UnaryNode): Int = {
    val scale = enforce(unary.child);
    unary match {
      case agg: Aggregate => {
        agg.aggregateExpressions.foreach(enforceAggregate(_, agg, scale));
        return scale;
      }
      case generate: Generate => {
        //TODO
        return scale;
      }
      case _ => return scale;
    }
  }

  private def enforceBinary(binary: BinaryNode): Int = {
    val scale1 = enforce(binary.left);
    val scale2 = enforce(binary.right);
    binary match {
      case union: Union => {
        Math.max(scale1, scale2);
      }
      case intersect: Intersect => {
        Math.max(scale1, scale2);
      }
      case except: Except => {
        Math.max(scale1, scale2);
      }
    }
  }

  private def enforceLeaf(leaf: LeafNode): Int = {
    1
  }

  /**
   * 1. joined tables must be raw tables;
   * 2. only equi-join and conjunctions are supported.
   */
  private def enforceJoin(join: Join): Int = {
    val graph = new JoinGraph;
    val conditions = new ListBuffer[(Expression, Join)];
    collectJoinTables(join, graph, conditions);

    graph.initializeEdges;
    conditions.foreach(t => updateJoinGraph(graph, t._1, t._2));

    val result = graph.sensitivity;
    if (result >= MaxWeight) {
      throw new PrivacyException("effective join key (equi-join) must be specified");
    }
    return result.toInt;
  }

  private def collectJoinTables(plan: LogicalPlan, graph: JoinGraph, conditions: ListBuffer[(Expression, Join)]) {
    plan match {
      case join: Join => {
        join.condition match {
          case Some(cond) => conditions.append((cond, join));
          case _ =>
        }
        join.children.foreach(collectJoinTables(_, graph, conditions));
      }

      case project: Project => collectJoinTables(project.child, graph, conditions);

      case leaf: LeafNode => {
        //must be table
        val label = leaf.projections.valuesIterator.next.asInstanceOf[ColumnLabel];
        graph.addTable(label.table);
      }
      //TODO relax the restrictions
      case _ => throw new PrivacyException("only direct join on tables are supported");
    }
  }

  private def updateJoinGraph(graph: JoinGraph, condition: Expression, plan: LogicalPlan) {
    condition match {
      case and: And => and.children.foreach(updateJoinGraph(graph, _, plan));
      case or: Or => {
        throw new PrivacyException("OR in join condition is not supported");
      }
      case equal: EqualTo => {
        try {
          val left = resolveAttribute(equal.left);
          val right = resolveAttribute(equal.right);
          val llabel = plan.childLabel(left).asInstanceOf[ColumnLabel];
          val rlabel = plan.childLabel(right).asInstanceOf[ColumnLabel];
          if (llabel.table != rlabel.table) {
            //   graph.updateEdge(lLabel.table, rLabel.table, stat.get(rLabel.database, rLabel.table, right.name));
            val lmulti = tableInfo.get(llabel.database, llabel.table, left.name).multiplicity;
            val rmulti = tableInfo.get(rlabel.database, rlabel.table, right.name).multiplicity;
            if (!lmulti.isDefined || !rmulti.isDefined) {
              throw new PrivacyException(s"join condition $equal is not allowed");
            }
            graph.updateEdge(llabel.table, rlabel.table, rmulti.get);
            graph.updateEdge(rlabel.table, llabel.table, lmulti.get);
          }
        } catch {
          case e: ClassCastException =>
            throw new PrivacyException(s"join condition $equal is invalid, only equi-join is supported");
        }
      }
      case _ =>
    }
  }

  private def enforceAggregate(expression: Expression, plan: Aggregate, scale: Int) {
    expression match {
      case alias: Alias => enforceAggregate(alias.child, plan, scale);
      //estimate sensitivity based on range for each functions
      case sum: Sum => {
        enforceDP(sum, plan, scale, (min, max) => max - min);
      }
      case sum: SumDistinct => {
        enforceDP(sum, plan, scale, (min, max) => max - min);
      }
      case count: Count => {
        enforceDP(count, plan, scale, (min, max) => 1);
      }
      case count: CountDistinct => {
        enforceDP(count, plan, scale, (min, max) => 1);
      }
      case avg: Average => {
        enforceDP(avg, plan, scale, (min, max) => max - min);
      }
      case min: Min => {
        enforceDP(min, plan, scale, (min, max) => max - min);
      }
      case max: Max => {
        enforceDP(max, plan, scale, (min, max) => max - min);
      }
      case _ => expression.children.foreach(enforceAggregate(_, plan, scale));
    }
  }

  private def enforceDP(agg: AggregateExpression, plan: Aggregate, scale: Int, func: (Double, Double) => Double) {
    val types = agg.children.map(resolveAggregateType(_, plan));
    if (types.exists(_ == AggregateType.Invalid_Aggregate)) {
      throw new PrivacyException("only aggregate directly on sensitive attributes are allowed");
    } else if (types.exists(_ == AggregateType.Direct_Aggregate)) {
      val range = resolveAttributeRange(agg.children(0), plan);
      if (range == null) {
        agg.sensitivity = 0;
      } else {
        agg.sensitivity = func(DPHelper.toDouble(range.low), DPHelper.toDouble(range.up));
      }
      //TODO
      budget.consume(epsilon);
      agg.epsilon = epsilon / scale;
      agg.enableDP = true;
      logWarning(s"enable dp for $agg with sensitivity = ${agg.sensitivity}, epsilon = $epsilon, scale = $scale, and result epsilon = ${agg.epsilon}");
    } else {
      logWarning(s"insensitive or derived aggregation $agg, dp disabled");
    }
  }

  private def resolveAggregateType(expr: Expression, plan: Aggregate): AggregateType.Value = {
    expr match {
      case attr: Attribute => {
        val label = plan.childLabel(attr);
        if (!label.sensitive) {
          return AggregateType.Insensitive_Aggregate;
        } else {
          return checkLabelType(label);
        }
      }
      case cast: Cast => {
        return resolveAggregateType(cast.child, plan);
      }
      case alias: Alias => {
        return resolveAggregateType(alias.child, plan);
      }
      case unary: UnaryExpression => {
        val aggType = resolveAggregateType(unary.child, plan);
        if (aggType == AggregateType.Invalid_Aggregate || aggType == AggregateType.Insensitive_Aggregate || aggType == AggregateType.Derived_Aggregate) {
          return aggType;
        } else {
          return AggregateType.Invalid_Aggregate;
        }
      }
      case binary: BinaryExpression => {
        val types = binary.children.map(resolveAggregateType(_, plan));
        if (types.exists(t => t == AggregateType.Invalid_Aggregate || t == AggregateType.Direct_Aggregate)) {
          return AggregateType.Invalid_Aggregate;
        } else if (types.exists(_ == AggregateType.Derived_Aggregate)) {
          return AggregateType.Derived_Aggregate;
        } else {
          return AggregateType.Insensitive_Aggregate;
        }
      }
    }
  }

  private def checkLabelType(label: Label): AggregateType.Value = {
    label match {
      case data: DataLabel => AggregateType.Direct_Aggregate;
      case cons: Constant => AggregateType.Direct_Aggregate;
      case in: Insensitive => AggregateType.Direct_Aggregate;

      case cond: ConditionalLabel => AggregateType.Direct_Aggregate;
      case func: Function => {
        val types = func.children.map(checkLabelType(_));
        if (types.exists(_ == AggregateType.Invalid_Aggregate)) {
          return AggregateType.Invalid_Aggregate;
        }
        func.udf match {
          case Func_Cast => {
            return checkLabelType(func.children(0));
          }
          case Func_Union | Func_Except | Func_Intersect => {
            if (types.forall(_ == AggregateType.Direct_Aggregate)) {
              return AggregateType.Direct_Aggregate;
            } else {
              return AggregateType.Derived_Aggregate;
            }
          }
          case Func_Sum | Func_Count | Func_Min | Func_Max | Func_Avg => {
            return AggregateType.Derived_Aggregate;
          }
          case _ => {
            if (types.exists(_ != AggregateType.Derived_Aggregate)) {
              return AggregateType.Invalid_Aggregate;
            } else {
              return AggregateType.Derived_Aggregate;
            }
          }
        }
      }
      case _ => throw new RuntimeException("should not reach here");
    }
  }

  private def resolveAttribute(expr: Expression): Attribute = {
    expr match {
      case attr: Attribute => attr;
      case alias: Alias => {
        val child = alias.child;
        child match {
          case cast: Cast => resolveAttribute(expr);
          case _ => alias.toAttribute;
        }
      }
      case _ => expr.asInstanceOf[Attribute];
    }
  }

  private def resolveAttributeRange(expr: Expression, plan: Aggregate): ColumnInfo = {
    expr match {
      case attr: AttributeReference => {
        val label = plan.childLabel(attr);
        resolveLabelRange(label);
      }
      case alias: Alias => {
        resolveAttributeRange(alias.child, plan);
      }
      case cast: Cast => {
        resolveAttributeRange(cast.child, plan);
      }
      case _ => throw new RuntimeException("should not reach here.");
    }
  }

  private def resolveLabelRange(label: Label): ColumnInfo = {
    label match {
      case data: DataLabel => {
        tableInfo.get(data.database, data.table, data.attr.name);
      }
      case func: Function => {
        func.udf match {
          case Func_Union => {
            resolveFuncRange(func, (r1: ColumnInfo, r2: ColumnInfo) => {
              if (r1 == null) {
                r2
              } else {
                r1.union(r2)
              }
            });
          }
          case Func_Intersect => {
            resolveFuncRange(func, (r1: ColumnInfo, r2: ColumnInfo) => {
              if (r1 == null) {
                r2
              } else {
                r1.intersect(r2)
              }
            });
          }
          case Func_Except => {
            resolveFuncRange(func, (r1: ColumnInfo, r2: ColumnInfo) => {
              if (r1 == null) {
                r2
              } else {
                r1.except(r2);
              }
            });
          }
        }
      }
      case _ => null;
    }
  }

  private def resolveFuncRange(label: Function, func: (ColumnInfo, ColumnInfo) => ColumnInfo): ColumnInfo = {
    val r1 = resolveLabelRange(label.children(0));
    val r2 = resolveLabelRange(label.children(1));
    func(r1, r2);
  }

}