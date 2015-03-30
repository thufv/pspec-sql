package org.apache.spark.sql.catalyst.checker

import scala.collection.mutable.HashMap
import scala.collection.mutable.ListBuffer
import org.apache.spark.Logging
import org.apache.spark.sql.catalyst.checker.LabelConstants.Func_Avg
import org.apache.spark.sql.catalyst.checker.LabelConstants.Func_Cast
import org.apache.spark.sql.catalyst.checker.LabelConstants.Func_Count
import org.apache.spark.sql.catalyst.checker.LabelConstants.Func_Except
import org.apache.spark.sql.catalyst.checker.LabelConstants.Func_Intersect
import org.apache.spark.sql.catalyst.checker.LabelConstants.Func_Max
import org.apache.spark.sql.catalyst.checker.LabelConstants.Func_Min
import org.apache.spark.sql.catalyst.checker.LabelConstants.Func_Sum
import org.apache.spark.sql.catalyst.checker.LabelConstants.Func_Union
import org.apache.spark.sql.catalyst.expressions.AggregateExpression
import org.apache.spark.sql.catalyst.expressions.Alias
import org.apache.spark.sql.catalyst.expressions.And
import org.apache.spark.sql.catalyst.expressions.Attribute
import org.apache.spark.sql.catalyst.expressions.AttributeReference
import org.apache.spark.sql.catalyst.expressions.Average
import org.apache.spark.sql.catalyst.expressions.BinaryExpression
import org.apache.spark.sql.catalyst.expressions.Cast
import org.apache.spark.sql.catalyst.expressions.Count
import org.apache.spark.sql.catalyst.expressions.CountDistinct
import org.apache.spark.sql.catalyst.expressions.EqualTo
import org.apache.spark.sql.catalyst.expressions.Expression
import org.apache.spark.sql.catalyst.expressions.Max
import org.apache.spark.sql.catalyst.expressions.Min
import org.apache.spark.sql.catalyst.expressions.Or
import org.apache.spark.sql.catalyst.expressions.Sum
import org.apache.spark.sql.catalyst.expressions.SumDistinct
import org.apache.spark.sql.catalyst.expressions.UnaryExpression
import org.apache.spark.sql.catalyst.graph.AdjacencyList
import org.apache.spark.sql.catalyst.graph.EdmondsChuLiu
import org.apache.spark.sql.catalyst.graph.Node
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.apache.spark.sql.catalyst.plans.logical.BinaryNode
import org.apache.spark.sql.catalyst.plans.logical.Except
import org.apache.spark.sql.catalyst.plans.logical.Filter
import org.apache.spark.sql.catalyst.plans.logical.Generate
import org.apache.spark.sql.catalyst.plans.logical.Intersect
import org.apache.spark.sql.catalyst.plans.logical.Join
import org.apache.spark.sql.catalyst.plans.logical.LeafNode
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import org.apache.spark.sql.catalyst.plans.logical.Project
import org.apache.spark.sql.catalyst.plans.logical.UnaryNode
import org.apache.spark.sql.catalyst.plans.logical.Union
import scala.collection.immutable.Seq
import scala.collection.mutable.HashSet
import scala.collection.mutable
import scala.collection.Set
import org.apache.spark.sql.catalyst.plans.logical.Limit
import org.apache.spark.sql.catalyst.plans.logical.Union
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.hsqldb.types.Binary
import org.apache.spark.sql.catalyst.expressions.Literal
import org.apache.spark.sql.catalyst.expressions.CaseWhen
import org.apache.spark.sql.catalyst.plans.logical.Expand

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

  private var currentRefiner: AttributeRangeRefiner = null;

  private var currentAggPlan: Aggregate = null;

  private class JoinGraph {
    val nodes = new HashMap[String, Node[String]];
    val edges = new AdjacencyList[String];

    private val tables = new HashMap[String, mutable.Set[Attribute]];
    private val tableSizes = new HashMap[String, Int];
    private val tableMapping = new HashMap[String, Int];

    def addTable(table: String, attrs: Set[Attribute], tableSize: Option[Int] = None) {
      val count = tableMapping.get(table);
      var name: String = null;
      //renaming table with unique id
      count match {
        case Some(i) => {
          name = table + s"#$i";
          tableMapping.put(table, i + 1);
        }
        case None => {
          name = table + "#0";
          tableMapping.put(table, 1);
        }
      }
      nodes.put(name, new Node(name));
      val set = new HashSet[Attribute];
      attrs.foreach(set.add(_));
      tables.put(name, set);
      if (tableSize.isDefined) {
        tableSizes.put(name, tableSize.get);
      }
    }

    def initializeEdges() {
      for (n1 <- nodes.values) {
        for (n2 <- nodes.values) {
          if (n1 != n2) {
            this.edges.addEdge(n1, n2, MaxWeight);
          }
        }
      }
      for (t <- tableSizes) {
        for (n <- nodes.values) {
          if (t._1 != n.getName()) {
            this.edges.updateEdge(nodes.getOrElse(t._1, null), n, t._2);
            this.edges.updateEdge(n, nodes.getOrElse(t._1, null), t._2);
          }
        }
      }

    }

    def updateEdge(attr1: Attribute, attr2: Attribute, weight: Double) {
      val table1 = lookupTable(attr1);
      val table2 = lookupTable(attr2);

      val node1 = nodes.getOrElse(table1, null);
      val node2 = nodes.getOrElse(table2, null);
      this.edges.updateEdge(node1, node2, weight);
    }

    def sensitivity(): Double = {
      val alg = new EdmondsChuLiu[String];
      val weights = nodes.values.map(node => alg.getMinBranching(node, edges).weightProducts(node));
      weights.max;
    }

    private def lookupTable(attr: Attribute): String = {
      for ((table, set) <- tables) {
        if (set.contains(attr)) {
          return table;
        }
      }
      return null;
    }
  }

  def apply(plan: LogicalPlan) {
    if (!fastCheck(plan)) {
      return ;
    }
    enforce(plan);
  }

  private def enforce(plan: LogicalPlan, dp: Boolean = false): Int = {
    plan match {
      case join: Join => {
        if (dp) {
          enforceJoin(join);
        } else {
          join.children.map(enforce(_, dp)).max;
        }
      }
      case agg: Aggregate => {
        val scale = enforce(agg.child, true);
        agg.aggregateExpressions.foreach(enforceAggregate(_, agg, scale));
        scale;
      }
      case unary: UnaryNode => {
        enforceUnary(unary, dp);
      }
      case binary: BinaryNode => {
        enforceBinary(binary, dp);
      }
      case leaf: LeafNode => {
        enforceLeaf(leaf);
      }
      case _ => 1
    }
  }

  private def fastCheck(plan: LogicalPlan): Boolean = {
    plan match {
      case agg: Aggregate => true;
      case unary: UnaryNode => fastCheck(unary.child);
      case binary: BinaryNode => fastCheck(binary.left) || fastCheck(binary.right);
      case _ => false;
    }
  }

  private def enforceUnary(unary: UnaryNode, dp: Boolean): Int = {
    val scale = enforce(unary.child, dp);
    unary match {

      case generate: Generate => {
        //TODO
        return scale;
      }
      case expand: Expand => {
        return scale * expand.projections.size;
      }
      case _ => return scale;
    }
  }

  private def enforceBinary(binary: BinaryNode, dp: Boolean): Int = {
    val scale1 = enforce(binary.left, dp);
    val scale2 = enforce(binary.right, dp);
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
    val condLabels = new ListBuffer[(Expression, Join)];
    collectJoinTables(join, graph, condLabels);

    graph.initializeEdges;
    condLabels.foreach(t => updateJoinGraph(graph, t._1, t._2));

    val result = graph.sensitivity;
    if (result >= MaxWeight) {
      throw new PrivacyException("effective join key (equi-join) must be specified");
    }
    return result.toInt;
  }

  private def collectJoinTables(plan: LogicalPlan, graph: JoinGraph, condLabels: ListBuffer[(Expression, Join)]) {
    plan match {
      //only limited operators can appear inside join.
      case join: Join => {
        join.condition match {
          case Some(cond) => condLabels.append((cond, join));
          case _ =>
        }
        join.children.foreach(collectJoinTables(_, graph, condLabels));
      }

      case project: Project => collectJoinTables(project.child, graph, condLabels);

      case filter: Filter => collectJoinTables(filter.child, graph, condLabels);

      case limit: Limit => collectJoinTables(limit.child, graph, condLabels);

      case aggregate: Aggregate => {
        enforce(aggregate, true);
        //treat aggregate as a new table
        if (aggregate.groupingExpressions.size == 0) {
          graph.addTable(s"aggregate", aggregate.projectLabels.keySet, Some(1));
        } else {
          graph.addTable(s"aggregate", aggregate.projectLabels.keySet);
        }
      }

      case binary: BinaryNode => {
        enforceBinary(binary, true);
        graph.addTable(s"${binary.nodeName}", binary.projectLabels.keySet);
      }

      case leaf: LeafNode => {
        //must be table
        val label = leaf.projectLabels.valuesIterator.next.asInstanceOf[ColumnLabel];
        graph.addTable(label.table, leaf.projectLabels.keySet);
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
          val lmulti = resolveAttributeInfo(left, plan).multiplicity;
          val rmulti = resolveAttributeInfo(right, plan).multiplicity;
          //   graph.updateEdge(lLabel.table, rLabel.table, stat.get(rLabel.database, rLabel.table, right.name));
          if (!lmulti.isDefined || !rmulti.isDefined) {
            throw new PrivacyException(s"join condition $equal is not allowed");
          }
          graph.updateEdge(left, right, rmulti.get);
          graph.updateEdge(right, left, lmulti.get);
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
        enforceDP(sum, plan, scale, true, (min, max) => Math.max(Math.abs(min), Math.abs(max)));
      }
      case sum: SumDistinct => {
        enforceDP(sum, plan, scale, true, (min, max) => max - min);
      }
      case count: Count => {
        enforceDP(count, plan, scale, false, (min, max) => 1);
      }
      case count: CountDistinct => {
        enforceDP(count, plan, scale, false, (min, max) => 1);
      }
      case avg: Average => {
        enforceDP(avg, plan, scale, true, (min, max) => max - min);
      }
      case min: Min => {
        enforceDP(min, plan, 1, true, (min, max) => max - min);
      }
      case max: Max => {
        enforceDP(max, plan, 1, true, (min, max) => max - min);
      }

      case _ => expression.children.foreach(enforceAggregate(_, plan, scale));
    }
  }

  private def enforceDP(agg: AggregateExpression, plan: Aggregate, scale: Int, refine: Boolean, func: (Double, Double) => Double) {
    val types = agg.children.map(resolveAggregateType(_, plan));

    if (types.exists(_ == AggregateType.Invalid_Aggregate)) {
      throw new PrivacyException("only aggregate directly on sensitive attributes are allowed");
    }

    if (types.exists(_ == AggregateType.Direct_Aggregate)) {
      if (currentAggPlan != plan) {
        currentAggPlan = plan;
        currentRefiner = new AttributeRangeRefiner(tableInfo, plan);
      }
      val range = resolveAttributeInfo(agg.children(0), plan);
      if (range == null) {
        agg.sensitivity = func(0, 0);
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
      case literal: Literal => {
        if (plan.child.projectLabels.values.exists(_.sensitive) || plan.condLabels.exists(_.sensitive)) {
          return AggregateType.Direct_Aggregate;
        } else {
          return AggregateType.Insensitive_Aggregate;
        }
      }
      case alias: Alias => {
        return resolveAggregateType(alias.child, plan);
      }
      case _ => {
        val types = expr.children.map(resolveAggregateType(_, plan));
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

  private def resolveAttributeInfo(expr: Expression, plan: LogicalPlan): ColumnInfo = {
    expr match {
      case attr: AttributeReference => {
        val label = plan.childLabel(attr);
        resolveLabelInfo(label);
      }
      case alias: Alias => {
        resolveAttributeInfo(alias.child, plan);
      }
      case cast: Cast => {
        resolveAttributeInfo(cast.child, plan);
      }
      case _ => null;
    }
  }

  private def resolveLabelInfo(label: Label): ColumnInfo = {
    label match {
      case data: DataLabel => {
        currentRefiner.get(data.database, data.table, data.attr);
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
      case _ => throw new PrivacyException(s"invalid attribute used in query: $label");
    }
  }

  private def resolveFuncRange(label: Function, func: (ColumnInfo, ColumnInfo) => ColumnInfo): ColumnInfo = {
    val r1 = resolveLabelInfo(label.children(0));
    val r2 = resolveLabelInfo(label.children(1));
    func(r1, r2);
  }

}