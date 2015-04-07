package org.apache.spark.sql.catalyst.checker.dp

import scala.collection.Set
import scala.collection.mutable
import scala.collection.mutable.HashMap
import scala.collection.mutable.HashSet
import scala.collection.mutable.ListBuffer
import org.apache.spark.Logging
import org.apache.spark.sql.catalyst.checker.ColumnLabel
import org.apache.spark.sql.catalyst.checker.ConditionalLabel
import org.apache.spark.sql.catalyst.checker.ConstantLabel
import org.apache.spark.sql.catalyst.checker.DataLabel
import org.apache.spark.sql.catalyst.checker.FunctionLabel
import org.apache.spark.sql.catalyst.checker.Insensitive
import org.apache.spark.sql.catalyst.checker.Label
import org.apache.spark.sql.catalyst.checker.LabelConstants._
import org.apache.spark.sql.catalyst.checker.PrivacyException
import org.apache.spark.sql.catalyst.expressions.AggregateExpression
import org.apache.spark.sql.catalyst.expressions.Alias
import org.apache.spark.sql.catalyst.expressions.And
import org.apache.spark.sql.catalyst.expressions.Attribute
import org.apache.spark.sql.catalyst.expressions.AttributeReference
import org.apache.spark.sql.catalyst.expressions.Average
import org.apache.spark.sql.catalyst.expressions.Cast
import org.apache.spark.sql.catalyst.expressions.Count
import org.apache.spark.sql.catalyst.expressions.CountDistinct
import org.apache.spark.sql.catalyst.expressions.EqualTo
import org.apache.spark.sql.catalyst.expressions.Expression
import org.apache.spark.sql.catalyst.expressions.Literal
import org.apache.spark.sql.catalyst.expressions.Max
import org.apache.spark.sql.catalyst.expressions.Min
import org.apache.spark.sql.catalyst.expressions.Or
import org.apache.spark.sql.catalyst.expressions.Sum
import org.apache.spark.sql.catalyst.expressions.SumDistinct
import org.apache.spark.sql.catalyst.graph.AdjacencyList
import org.apache.spark.sql.catalyst.graph.EdmondsChuLiu
import org.apache.spark.sql.catalyst.graph.Node
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.apache.spark.sql.catalyst.plans.logical.BinaryNode
import org.apache.spark.sql.catalyst.plans.logical.Except
import org.apache.spark.sql.catalyst.plans.logical.Expand
import org.apache.spark.sql.catalyst.plans.logical.Filter
import org.apache.spark.sql.catalyst.plans.logical.Generate
import org.apache.spark.sql.catalyst.plans.logical.Intersect
import org.apache.spark.sql.catalyst.plans.logical.Join
import org.apache.spark.sql.catalyst.plans.logical.LeafNode
import org.apache.spark.sql.catalyst.plans.logical.Limit
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import org.apache.spark.sql.catalyst.plans.logical.Project
import org.apache.spark.sql.catalyst.plans.logical.UnaryNode
import org.apache.spark.sql.catalyst.plans.logical.Union
import org.apache.spark.sql.catalyst.checker.dp.DPUtil._
import org.apache.spark.sql.catalyst.checker.util.CheckerUtil._
import org.apache.spark.sql.catalyst.checker.util.TypeUtil._
import org.apache.spark.sql.catalyst.plans.logical.Aggregate

/**
 * enforce dp for a query logical plan
 * should be in the last phase of query checking
 */
class DPEnforcer(val tableInfo: TableInfo, val budgetManager: DPBudgetManager, val epsilon: Double) extends Logging {
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

  private val MaxWeight = 10000;

  private var currentRefiner: AttributeRangeRefiner = null;

  private var currentAggPlan: Aggregate = null;

  def apply(plan: LogicalPlan) {
    if (!exists(plan, classOf[Aggregate])) {
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
        budgetManager.consume(agg, epsilon);
        scale;
      }
      case unary: UnaryNode => {
        enforceUnary(unary, dp);
      }
      case binary: BinaryNode => {
        enforceBinary(binary, dp);
      }
      case leaf: LeafNode => {
        1
      }
      case _ => 1
    }
  }

  private def enforceUnary(unary: UnaryNode, dp: Boolean): Int = {
    val scale = enforce(unary.child, dp);
    unary match {
      case generate: Generate => {
        //TODO: luochen, compute scale for generate function?
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
      //TODO: luochen relax the restrictions on join
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
          //join on complex data types is not allowed
          val left = resolveSimpleAttribute(equal.left).asInstanceOf[Attribute];
          val right = resolveSimpleAttribute(equal.right).asInstanceOf[Attribute];
          val lmulti = resolveAttributeMultiplicity(equal.left, plan);
          val rmulti = resolveAttributeMultiplicity(equal.right, plan);
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
    if (!agg.enableDP) {
      return ;
    }
    if (currentAggPlan != plan) {
      currentAggPlan = plan;
      currentRefiner = new AttributeRangeRefiner(tableInfo, plan);
    }
    if (refine) {
      val range = resolveAttributeRange(agg.children(0), plan, refine);
      agg.sensitivity = func(DPUtil.toDouble(range._1), DPUtil.toDouble(range._2));
    } else {
      agg.sensitivity = func(0, 0);
    }
    agg.epsilon = epsilon / scale;

    logWarning(s"enable dp for $agg with sensitivity = ${agg.sensitivity}, epsilon = $epsilon, scale = $scale, and result epsilon = ${agg.epsilon}");
  }

  private def resolveAttributeRange(expr: Expression, plan: LogicalPlan, refine: Boolean): (Int, Int) = {
    expr match {
      //TODO: luochen support operators for complex data types
      case attr if (isAttribute(attr)) => {
        return currentRefiner.get(attr, plan, refine);
      }
      case alias: Alias => {
        resolveAttributeRange(alias.child, plan, refine);
      }
      case cast: Cast => {
        resolveAttributeRange(cast.child, plan, refine);
      }
      case _ => null;
    }
  }

  private def resolveAttributeMultiplicity(expr: Expression, plan: LogicalPlan): Option[Int] = {
    def multiplicityHelper(func: FunctionLabel, trans: (Option[Int], Option[Int]) => Option[Int]): Option[Int] = {
      val left = resolveLabelMultiplicity(func.children(0));
      val right = resolveLabelMultiplicity(func.children(1));
      return trans(left, right);
    }

    def resolveLabelMultiplicity(label: Label): Option[Int] = {
      label match {
        case column: ColumnLabel => {
          val info = tableInfo.get(column.database, column.table, column.attr.name);
          return info.multiplicity;
        }
        case func: FunctionLabel => {
          func.transform match {
            //TODO: luochen support operators for complex data types
            case Func_Union => {
              multiplicityHelper(func, multiplicityUnion(_, _));
            }
            case Func_Intersect => {
              multiplicityHelper(func, multiplicityIntersect(_, _));
            }
            case Func_Except => {
              multiplicityHelper(func, multiplicityUnion(_, _));
            }
            case _ => None;
          }
        }
        case _ => None;

      }
    }

    expr match {
      case attr: AttributeReference => {
        val label = plan.childLabel(attr);
        return resolveLabelMultiplicity(label);
      }
      case alias: Alias => {
        resolveAttributeMultiplicity(alias.child, plan);
      }
      case cast: Cast => {
        resolveAttributeMultiplicity(cast.child, plan);
      }
      case _ => None;
    }
  }

}