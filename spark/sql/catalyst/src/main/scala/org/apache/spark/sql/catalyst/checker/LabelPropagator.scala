package org.apache.spark.sql.catalyst.checker

import org.apache.spark.sql.catalyst.expressions.AggregateExpression
import org.apache.spark.sql.catalyst.expressions.Alias
import org.apache.spark.sql.catalyst.expressions.And
import org.apache.spark.sql.catalyst.expressions.AttributeReference
import org.apache.spark.sql.catalyst.expressions.BinaryArithmetic
import org.apache.spark.sql.catalyst.expressions.BinaryComparison
import org.apache.spark.sql.catalyst.expressions.CaseWhen
import org.apache.spark.sql.catalyst.expressions.Contains
import org.apache.spark.sql.catalyst.expressions.EndsWith
import org.apache.spark.sql.catalyst.expressions.Expression
import org.apache.spark.sql.catalyst.expressions.If
import org.apache.spark.sql.catalyst.expressions.LeafExpression
import org.apache.spark.sql.catalyst.expressions.Like
import org.apache.spark.sql.catalyst.expressions.Literal
import org.apache.spark.sql.catalyst.expressions.MaxOf
import org.apache.spark.sql.catalyst.expressions.MutableLiteral
import org.apache.spark.sql.catalyst.expressions.NamedExpression
import org.apache.spark.sql.catalyst.expressions.Not
import org.apache.spark.sql.catalyst.expressions.Or
import org.apache.spark.sql.catalyst.expressions.RLike
import org.apache.spark.sql.catalyst.expressions.StartsWith
import org.apache.spark.sql.catalyst.expressions.Substring
import org.apache.spark.sql.catalyst.expressions.UnaryExpression
import org.apache.spark.sql.catalyst.plans.LeftSemi
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.apache.spark.sql.catalyst.plans.logical.BinaryNode
import org.apache.spark.sql.catalyst.plans.logical.Distinct
import org.apache.spark.sql.catalyst.plans.logical.Except
import org.apache.spark.sql.catalyst.plans.logical.Filter
import org.apache.spark.sql.catalyst.plans.logical.Generate
import org.apache.spark.sql.catalyst.plans.logical.Intersect
import org.apache.spark.sql.catalyst.plans.logical.Join
import org.apache.spark.sql.catalyst.plans.logical.LeafNode
import org.apache.spark.sql.catalyst.plans.logical.Limit
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import org.apache.spark.sql.catalyst.plans.logical.Project
import org.apache.spark.sql.catalyst.plans.logical.RedistributeData
import org.apache.spark.sql.catalyst.plans.logical.Sample
import org.apache.spark.sql.catalyst.plans.logical.ScriptTransformation
import org.apache.spark.sql.catalyst.plans.logical.Sort
import org.apache.spark.sql.catalyst.plans.logical.Subquery
import org.apache.spark.sql.catalyst.plans.logical.UnaryNode
import org.apache.spark.sql.catalyst.plans.logical.Union
import org.apache.spark.sql.catalyst.plans.logical.WriteToFile
import org.apache.spark.sql.catalyst.expressions.In
import org.apache.spark.sql.catalyst.expressions.ScalaUdf
import org.apache.spark.sql.catalyst.expressions.Attribute
import org.apache.spark.Logging
import org.apache.spark.sql.catalyst.expressions.In
import org.apache.spark.sql.catalyst.plans.LeftSemi
import org.apache.spark.sql.catalyst.plans.logical.ScriptTransformation
import org.apache.spark.sql.catalyst.expressions.RLike
import org.apache.spark.sql.catalyst.expressions.StartsWith
import org.apache.spark.sql.catalyst.expressions.Substring
import edu.thu.ss.spec.meta.MetaRegistry
import org.apache.spark.sql.catalyst.expressions.CaseWhen
import org.apache.spark.sql.catalyst.expressions.ScalaUdf
import org.apache.spark.sql.catalyst.expressions.MutableLiteral
import org.apache.spark.sql.catalyst.expressions.BinaryComparison
import org.apache.spark.sql.catalyst.plans.logical.RedistributeData
import org.apache.spark.sql.catalyst.expressions.Contains
import org.apache.spark.sql.catalyst.expressions.EndsWith
import org.apache.spark.sql.catalyst.expressions.AggregateExpression
import org.apache.spark.sql.catalyst.expressions.EqualTo
import org.apache.spark.sql.catalyst.expressions.EqualNullSafe
import org.apache.spark.sql.catalyst.expressions.AttributeReference
import org.apache.spark.sql.catalyst.expressions.BinaryExpression
import org.apache.spark.sql.catalyst.expressions.AttributeReference
import scala.collection.mutable
import scala.collection.mutable.HashSet
import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer
import org.apache.spark.sql.catalyst.expressions.AttributeReference
import edu.thu.ss.spec.lang.pojo.Policy
import org.apache.spark.sql.catalyst.plans.logical.Command
import org.apache.spark.sql.catalyst.expressions.GetItem
import org.apache.spark.sql.catalyst.expressions.IsNull
import org.apache.spark.sql.catalyst.expressions.Coalesce
import org.apache.spark.sql.catalyst.expressions.Alias
import org.apache.spark.sql.catalyst.expressions.IsNotNull
import org.apache.spark.sql.catalyst.expressions.Count
import org.apache.spark.sql.catalyst.expressions
import org.apache.spark.sql.catalyst.expressions.IntegerLiteral
import org.apache.spark.sql.catalyst.plans.logical.InsertIntoTable
import org.apache.spark.sql.catalyst.plans.logical.Expand
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import org.jgrapht.alg.ConnectivityInspector

/**
 * vertex class for equi-graph
 * used when resolving conditional data categories
 */
abstract class EquiVertex;

/**
 * vertex class for attribute
 */
case class ColumnVertex(attr: AttributeReference) extends EquiVertex;

/**
 * vertex class for constant
 */
case class ConstantVertex(value: Any) extends EquiVertex;

/**
 * Perform label propagation on logical plan,
 * used for calculating projectLabels and condLabels used in the logical plan
 */
class LabelPropagator extends Logging {

  /**
   * column_labal -> (column_name -> column_label)
   * for each column label, we can find the corresponding table (column_name -> column_label).
   * table is actually a set of columns.
   */
  lazy val tables = new mutable.HashMap[ColumnLabel, Map[String, ColumnLabel]];

  lazy val equiGraph = new SimpleGraph[EquiVertex, DefaultEdge](classOf[DefaultEdge]);

  lazy val alg = new ConnectivityInspector[EquiVertex, DefaultEdge](equiGraph);

  /**
   * a set of applicable policies on the logical plan
   */
  lazy val policies = new mutable.HashSet[Policy];

  /**
   * performs label propagation, and collects all applicable policy
   */
  def apply(plan: LogicalPlan): mutable.Set[Policy] = {
    propagate(plan);

    //resolve conditional labels
    plan.projectLabels.values.foreach(fulfillConditions(_));
    plan.condLabels.foreach(fulfillConditions(_));

    return policies;
  }

  private def propagate(plan: LogicalPlan): Unit = {
    plan match {
      case _: Command => //skip all command

      case leaf: LeafNode => {
        //dispatch to spark-hive
        val policy = leaf.calculateLabels;
        if (policy != null) {
          policies.add(policy);
        }
        addTable(leaf.projectLabels);
      }

      case unary: UnaryNode => propagateUnary(unary);
      case binary: BinaryNode => propagateBinary(binary);
      case insert: InsertIntoTable => {
        insert.projectLabels ++= insert.child.projectLabels;
        insert.condLabels ++= insert.child.condLabels;
      }
      case _ => logWarning(s"unknown logical plan:$plan");
    }
  }

  /**
   * propagate unary plan
   */
  private def propagateUnary(unary: UnaryNode): Unit = {

    propagate(unary.child);
    val childProjs = unary.child.projectLabels;
    val childConds = unary.child.condLabels;

    unary match {
      case aggregate: Aggregate => {
        //resolve aggregate expression
        aggregate.aggregateExpressions.foreach(resolveNamedExpression(_, unary));
        aggregate.condLabels ++= childConds;
      }
      case filter: Filter => {
        //resolve filter expression, projectLabels unchanged
        resolveExpression(filter.condition, filter);
        filter.condLabels ++= childConds;
        filter.projectLabels ++= childProjs;
      }
      case project: Project => {
        //resolve projection list
        project.projectList.foreach(resolveNamedExpression(_, unary));
        project.condLabels ++= childConds;
      }
      case expand: Expand => {
        expand.output.foreach(resolveNamedExpression(_, unary));
        expand.condLabels ++= childConds;
      }
      case subquery: Subquery => {
        //renaming attribute names
        //but not used in optimized logical plan?
        childProjs.foreach(tuple => subquery.projectLabels.put(tuple._1.withQualifiers(List(subquery.alias)), tuple._2));
        subquery.condLabels ++ childConds;
      }
      case sort: Sort => {
        //resolve sort expression
        sort.order.foreach(order => resolveExpression(order.child, sort));
        sort.projectLabels ++= childProjs;
        sort.condLabels ++= childConds;
      }
      case script: ScriptTransformation => {
        //TODO
        propagateDefault(script);
      }
      case generate: Generate => {
        propagateDefault(generate);
      }
      case distinct: Distinct => {
        propagateDefault(distinct);
      }
      case limit: Limit => {
        propagateDefault(limit);
      }
      case redis: RedistributeData => {
        propagateDefault(redis);
      }
      case sample: Sample => {
        propagateDefault(sample);
      }
      case write: WriteToFile => {
        propagateDefault(write);
      }
      case _ => {
        //this should be turn off in production
        throw new UnsupportedPlanException(s"unkown unary plan: $unary");
      }
    }
  }

  /**
   * propagate binary plans
   */
  private def propagateBinary(binary: BinaryNode): Unit = {
    propagate(binary.left);
    propagate(binary.right);

    val leftProjs = binary.left.projectLabels;
    val rightProjs = binary.right.projectLabels;
    val leftTests = binary.left.condLabels;
    val rightTests = binary.right.condLabels;

    binary match {
      case except: Except => {
        propogateSetOperators(binary, LabelConstants.Func_Except);
      }
      case intersect: Intersect => {
        propogateSetOperators(binary, LabelConstants.Func_Intersect);
      }
      case union: Union => {
        propogateSetOperators(binary, LabelConstants.Func_Union);
      }
      case join: Join => {
        //based on different join types
        join.joinType match {
          case LeftSemi => {
            join.projectLabels ++= leftProjs;
          }
          case _ => {
            join.projectLabels ++= leftProjs ++= rightProjs;
          }
        }
        join.condLabels ++= leftTests ++= rightTests;
        join.condition match {
          case Some(condition) => resolveExpression(condition, binary);
          case None =>
        }
      }
    }
  }

  /**
   * propagate set operators (intersect, union, except)
   * the output attribute is actually a combination of attributes from left and right plan.
   */
  private def propogateSetOperators(binary: BinaryNode, name: String): Unit = {
    for (i <- 0 to binary.output.length - 1) {
      val leftLabel = binary.left.projectLabels.getOrElse(binary.left.output(i), null);
      val rightLabel = binary.right.projectLabels.getOrElse(binary.right.output(i), null);
      binary.projectLabels.put(binary.output(i), Function(List(leftLabel, rightLabel), name, null));
    }
    binary.condLabels ++= binary.left.condLabels ++= binary.right.condLabels;
  }

  /**
   * Default propagation, inherit down
   */
  private def propagateDefault(unary: UnaryNode): Unit = {
    unary.projectLabels ++= unary.child.projectLabels;
    unary.condLabels ++= unary.child.condLabels;
  }

  /**
   * after resolve a relation, add all attributes and lineage trees to the table
   */
  private def addTable(projectLabels: mutable.Map[Attribute, Label]): Unit = {
    val table = projectLabels.values.map(label => {
      val cond = label.asInstanceOf[ColumnLabel];
      (cond.attr.name, cond);
    }).toMap;
    table.foreach(t => tables.put(t._2, table));
  }

  /**
   * resolve named expressions, only for aggregate and project operator.
   * attribute and the corresponding lineage tree is put in unary.
   */
  private def resolveNamedExpression(expression: NamedExpression, unary: UnaryNode): Unit = {
    val childProjs = unary.child.projectLabels;
    expression match {
      case attr: AttributeReference => {
        val label = childProjs.getOrElse(attr, null);
        unary.projectLabels.put(attr, label);
      }
      case alias: Alias => {
        var label = childProjs.getOrElse(alias.toAttribute, null);
        if (label != null) {
          unary.projectLabels.put(alias.toAttribute, label);
        } else {
          label = resolveExpression(alias.child, unary);
          if (label != null) {
            unary.projectLabels.put(alias.toAttribute, label);
          }
        }
      }
      case _ => throw new UnsupportedPlanException(s"unknown named expression: $expression");
    }
  }

  /**
   * if expression is boolean expression, then add lineage trees for boolean expressions (predicates) to condLabels.
   * otherwise, return lineage tree for the expression
   */
  private def resolveExpression(expression: Expression, plan: LogicalPlan): Label = {
    expression match {
      case _: And | _: Or | _: Not => {
        //boolean expression
        expression.children.foreach(resolveExpression(_, plan));
        return null;
      };
      case binary: BinaryComparison => {
        //boolean expression, and resolve join condition to update equi-graph
        resolveJoinCondition(binary, plan);
        resolvePredicate(expression, plan)
      };
      case _: Contains => resolvePredicate(expression, plan);
      case _: EndsWith => resolvePredicate(expression, plan);
      case _: Like => resolvePredicate(expression, plan);
      case _: RLike => resolvePredicate(expression, plan);
      case _: StartsWith => resolvePredicate(expression, plan);
      case _: In => resolvePredicate(expression, plan);
      case _: IsNull => resolvePredicate(expression, plan);
      case _: IsNotNull => resolvePredicate(expression, plan);

      //not boolean expression, resolve term and return lineage tree
      case _ => resolveTerm(expression, plan);
    }
  }

  /**
   * return a predicate expression
   */
  private def resolvePredicate(predicate: Expression, plan: LogicalPlan): Label = {
    val labels = predicate.children.map(resolveTerm(_, plan));
    plan.condLabels.add(PredicateLabel(labels, ExpressionRegistry.resolvePredicate(predicate)));
    return null;
  }

  /**
   * resolve a term expression, and return a lineage tree
   * the lineage tree is built upon lineage trees for expression attributes
   */
  private def resolveTerm(expression: Expression, plan: LogicalPlan): Label = {
    expression match {
      //retrieve lineage tree from child plan
      case attr: AttributeReference => plan.childLabel(attr);
      case alias: Alias => resolveExpression(alias.child, plan);

      case leaf: LeafExpression => {
        leaf match {
          //a constant node
          case l: Literal => Constant(l.value);
          case l: MutableLiteral => Constant(l.value);
          case _ => throw new UnsupportedPlanException(s"unknown leaf expression: $leaf");
        }
      }
      case count: Count => {
        count.child match {
          case IntegerLiteral(1) => {
            val labels = plan.children.flatMap(_.projectLabels.values.toSeq);
            Function(labels, ExpressionRegistry.resolveFunction(expression), count);
          }
          case _ => resolveTermFunction(expression, plan);
        }
      }
      case _: AggregateExpression => {
        resolveTermFunction(expression, plan);
      }
      case _: UnaryExpression => {
        resolveTermFunction(expression, plan);
      }
      case _: BinaryArithmetic => {
        resolveTermFunction(expression, plan);
      }
      case _: MaxOf => {
        resolveTermFunction(expression, plan);
      }
      case _: Substring => {
        resolveTermFunction(expression, plan);
      }
      case _: GetItem => {
        resolveTermFunction(expression, plan);
      }
      case _: Coalesce => {
        resolveTermFunction(expression, plan);
      }
      case udf: ScalaUdf => {
        val labels = udf.children.map(resolveTerm(_, plan));
        return Function(labels, udf.name, udf);
      }
      case when: CaseWhen => {
        //collect all predicates in condLabels
        when.predicates.foreach(resolveExpression(_, plan));
        //build a lineage tree that is combination of all values
        val labels = when.values.map(resolveTerm(_, plan));
        when.elseValue match {
          case Some(expr) => Function(labels :+ (resolveTerm(expr, plan)), ExpressionRegistry.resolveFunction(when), when);
          case None => Function(labels, ExpressionRegistry.resolveFunction(when), when);
        }
      }
      case i: If => {
        //same to when
        resolveExpression(i.predicate, plan);
        val tLabel = resolveTerm(i.trueValue, plan);
        val fLabel = resolveTerm(i.falseValue, plan);
        Function(List(tLabel, fLabel), ExpressionRegistry.resolveFunction(i), i);
      }

      case _ => Function(expression.children.map(resolveExpression(_, plan)), expression.nodeName, expression);
    }
  }

  /**
   * return a lineage tree for function
   */
  private def resolveTermFunction(expression: Expression, plan: LogicalPlan): Label = {
    val labels = expression.children.map(resolveTerm(_, plan));

    val func = ExpressionRegistry.resolveFunction(expression);
    if (func != null) {
      Function(labels, ExpressionRegistry.resolveFunction(expression), expression);
    } else {
      labels(0);
    }
  }

  /**
   * resolve join condition and update equi-graph
   */
  private def resolveJoinCondition(expression: BinaryComparison, plan: LogicalPlan): Unit = {
    var lefts: mutable.Set[EquiVertex] = null;
    var rights: mutable.Set[EquiVertex] = null;
    //collect all stored attributes(column) in the left part and right part of the comparison
    expression match {
      case _: EqualTo | _: EqualNullSafe => {
        lefts = resolveJoinColumn(expression.left, plan);
        rights = resolveJoinColumn(expression.right, plan);
      }
      case _ => ;
    }
    if (lefts == null || rights == null) {
      return ;
    }

    //all stored attributes from left and right are considered equal point-wisely.
    for (left <- lefts) {
      for (right <- rights) {
        addEquiEdge(left, right);
        addEquiEdge(right, left);
      }
    }
  }

  /**
   * collect all stored attribute from the expression
   */
  private def resolveJoinColumn(expr: Expression, plan: LogicalPlan, set: mutable.Set[EquiVertex] = new HashSet): mutable.Set[EquiVertex] = {
    expr match {
      case attr: AttributeReference => {
        val label = plan.childLabel(attr);
        if (label != null) {
          resolveJoinLabel(label, set);
        }
      }
      case alias: Alias => {
        return resolveJoinColumn(alias.child, plan);
      }
      case when: CaseWhen => {
        when.values.foreach(resolveJoinColumn(_, plan, set));
        when.elseValue match {
          case Some(v) => resolveJoinColumn(v, plan, set);
          case None =>
        }
      }
      case unary: UnaryExpression => return resolveJoinColumn(unary.child, plan);
      case binary: BinaryExpression => {
        resolveJoinColumn(binary.left, plan);
        resolveJoinColumn(binary.right, plan)
      }
      case leaf: Literal => set.add(ConstantVertex(leaf.value));
      case _ => ;
    }
    return set;
  }

  /**
   * given a lineage tree, collect all stored attributes (leaf nodes)
   */
  private def resolveJoinLabel(label: Label, set: mutable.Set[EquiVertex]): Unit = {
    label match {
      case col: ColumnLabel => set.add(ColumnVertex(col.attr));
      case cons: Constant => set.add(ConstantVertex(cons.value));
      case func: Function => func.children.foreach(resolveJoinLabel(_, set));
      case _ => throw new RuntimeException(s"Predicate $label should not appear in equi-join expression.");
    }
  }

  private def addEquiEdge(a: EquiVertex, b: EquiVertex): Unit = {
    equiGraph.addVertex(a);
    equiGraph.addVertex(b);
    equiGraph.addEdge(a, b);
  }

  /**
   * given a lineage tree, for all conditional nodes, check which join condLabels are satisfied by the query
   */
  private def fulfillConditions(label: Label): Unit = {
    label match {
      case cond: ConditionalLabel => fulfillCondition(cond);
      case func: Function => func.children.foreach(fulfillConditions(_));
      case pred: PredicateLabel => pred.children.foreach(fulfillConditions(_));
      case _ =>
    }
  }

  /**
   * add actual data categories for conditional labeling to cond.fulfilled
   */
  private def fulfillCondition(cond: ConditionalLabel): Unit = {
    if (cond.fulfilled != null) {
      return ;
    }
    cond.fulfilled = new HashSet;
    val cols = tables.getOrElse(cond, null);
    if (cols == null) {
      return ;
    }
    for (join <- cond.conds.keys) {
      var table = join.getJoinTable();
      var entries = join.getJoinColumns().asScala;
      var joinTables = new HashSet[Map[String, ColumnLabel]];
      //attributes from multiple references of a same table should be differentiated.
      tables.foreach(t => {
        val col = t._1;
        if (col.database == cond.database && col.table == table) {
          joinTables.add(t._2);
        }
      });
      val result = joinTables.exists(joinCols =>
        entries.forall(e => {
          val equiCols = getEquis(ColumnVertex(cols.getOrElse(e.column, null).attr));
          val contain = equiCols.contains(ColumnVertex(joinCols.getOrElse(e.target, null).attr));
          contain;
        }));
      if (result) {
        cond.fulfilled.add(cond.conds.getOrElse(join, null));
      }
    }
  }

  /**
   * get all vertex that are equal to v.
   * performs reachability analysis on equi-graph
   */
  private def getEquis(v: EquiVertex): mutable.Set[EquiVertex] = {
    return alg.connectedSetOf(v).asScala;
  }

}