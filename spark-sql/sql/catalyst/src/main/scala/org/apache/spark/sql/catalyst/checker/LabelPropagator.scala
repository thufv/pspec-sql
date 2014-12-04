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
import org.apache.spark.sql.catalyst.plans.logical.InsertIntoCreatedTable
import org.apache.spark.sql.catalyst.plans.logical.Intersect
import org.apache.spark.sql.catalyst.plans.logical.Join
import org.apache.spark.sql.catalyst.plans.logical.LeafNode
import org.apache.spark.sql.catalyst.plans.logical.Limit
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import org.apache.spark.sql.catalyst.plans.logical.LowerCaseSchema
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
import org.apache.spark.sql.catalyst.plans.logical.NativeCommand
import org.apache.spark.sql.catalyst.plans.logical.Command

abstract class EquiVertex;

case class ColumnVertex(attr: AttributeReference) extends EquiVertex;
case class ConstantVertex(value: Any) extends EquiVertex;

class LabelPropagator extends Logging {

  lazy val tables = new mutable.HashMap[ColumnLabel, Map[String, ColumnLabel]];

  lazy val equiEdges = new mutable.HashMap[EquiVertex, mutable.Set[EquiVertex]];

  lazy val equis = new mutable.HashMap[EquiVertex, mutable.Set[EquiVertex]];

  lazy val policies = new mutable.HashSet[Policy];

  def apply(plan: LogicalPlan): mutable.Set[Policy] = {

    propagate(plan);
    plan.projections.values.foreach(fulfillConditions(_));
    plan.conditions.foreach(fulfillConditions(_));
    policies;
  }

  private def propagate(plan: LogicalPlan): Unit = {
    plan match {
      case _: Command =>
      case leaf: LeafNode => {
        val policy = leaf.calculateLabels;
        if (policy != null) {
          policies.add(policy);
        }
        addTable(leaf.projections);
      }
      case unary: UnaryNode => propagateUnary(unary);
      case binary: BinaryNode => propagateBinary(binary);
      case _ => logWarning(s"unknown logical plan:$plan");
    }
  }

  private def propagateUnary(unary: UnaryNode): Unit = {
    propagate(unary.child);
    val childProjs = unary.child.projections;
    val childTests = unary.child.conditions;

    unary match {
      case aggregate: Aggregate => {
        //resolve aggregation list
        aggregate.aggregateExpressions.foreach(resolveNamedExpression(_, unary));
        aggregate.conditions ++= childTests;
      }
      case filter: Filter => {
        //add conditions
        resolveExpression(filter.condition, filter);
        filter.conditions ++= childTests;
        filter.projections ++= childProjs;
      }
      case generate: Generate => {
        propagateDefault(generate);
      }
      case project: Project => {
        //resolve projection list
        project.projectList.foreach(resolveNamedExpression(_, unary));
        project.conditions ++= childTests;
      }
      case subquery: Subquery => {
        //TODO
        childProjs.foreach(tuple => subquery.projections.put(tuple._1.withQualifiers(List(subquery.alias)), tuple._2));
        subquery.conditions ++ childTests;
      }
      case script: ScriptTransformation => {
        //TODO
        propagateDefault(script);
      }
      case sort: Sort => {
        propagateDefault(sort);
      }
      case distinct: Distinct => {
        propagateDefault(distinct);
      }
      case insert: InsertIntoCreatedTable => {
        propagateDefault(insert);
      }
      case limit: Limit => {
        propagateDefault(limit);
      }
      case lower: LowerCaseSchema => {
        propagateDefault(lower);
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
        throw new UnsupportedPlanException(s"unkown unary plan: $unary");
      }
    }
  }

  private def propagateBinary(binary: BinaryNode): Unit = {
    propagate(binary.left);
    propagate(binary.right);

    val leftProjs = binary.left.projections;
    val rightProjs = binary.right.projections;
    val leftTests = binary.left.conditions;
    val rightTests = binary.right.conditions;

    binary match {
      case except: Except => {
        except.projections ++= leftProjs;
        except.conditions ++= leftTests;
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
            join.projections ++= leftProjs;
          }
          case _ => {
            join.projections ++= leftProjs ++= rightProjs;
          }
        }
        join.conditions ++= leftTests ++= rightTests;
        join.condition match {
          case Some(condition) => resolveExpression(condition, binary);
          case None =>
        }
      }
    }
  }

  private def propogateSetOperators(binary: BinaryNode, name: String): Unit = {
    for (i <- 0 to binary.output.length - 1) {
      val leftLabel = binary.left.projections.getOrElse(binary.left.output(i), null);
      val rightLabel = binary.right.projections.getOrElse(binary.right.output(i), null);
      binary.projections.put(binary.output(i), Function(List(leftLabel, rightLabel), name));
    }
    binary.conditions ++= binary.left.conditions ++= binary.right.conditions;
  }

  /**
   * Default propagation, inherit down
   */
  private def propagateDefault(unary: UnaryNode): Unit = {
    unary.projections ++= unary.child.projections;
    unary.conditions ++= unary.child.conditions;
  }

  private def resolveNamedExpression(expression: NamedExpression, unary: UnaryNode): Unit = {
    val childProjs = unary.child.projections;
    expression match {
      case attr: AttributeReference => {
        val label = childProjs.getOrElse(attr, null);
        unary.projections.put(attr, label);
      }
      case alias: Alias => {
        val label = resolveExpression(alias.child, unary);
        if (label != null) {
          unary.projections.put(alias.toAttribute, label);
        }
      }
      case _ => throw new UnsupportedPlanException(s"unknown named expression: $expression");
    }
  }

  /**
   * if expression is boolean expression, then add term expression into condition sets.
   * otherwise, return the term formula.
   */
  private def resolveExpression(expression: Expression, plan: LogicalPlan): Label = {
    expression match {
      case _: And | _: Or | _: Not => {
        expression.children.foreach(resolveExpression(_, plan));
        null;
      };
      case binary: BinaryComparison => {
        resolveJoinCondition(binary, plan);
        resolvePredicate(expression, plan)
      };
      case _: Contains => resolvePredicate(expression, plan);
      case _: EndsWith => resolvePredicate(expression, plan);
      case _: Like => resolvePredicate(expression, plan);
      case _: RLike => resolvePredicate(expression, plan);
      case _: StartsWith => resolvePredicate(expression, plan);
      case _: In => resolvePredicate(expression, plan);

      case _ => resolveTerm(expression, plan);
    }
  }

  private def resolvePredicate(predicate: Expression, plan: LogicalPlan): Label = {
    val labels = predicate.children.map(resolveTerm(_, plan));
    plan.conditions.add(Predicate(labels, ExpressionRegistry.resolvePredicate(predicate)));
    null;
  }

  private def resolveTerm(expression: Expression, plan: LogicalPlan): Label = {
    expression match {
      case attr: AttributeReference => plan.childLabel(attr);
      case leaf: LeafExpression => {
        leaf match {
          case l: Literal => Constant(l.value);
          case l: MutableLiteral => Constant(l.value);
          case _ => throw new UnsupportedPlanException(s"unknown leaf expression: $leaf");
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
      case udf: ScalaUdf => {
        val labels = udf.children.map(resolveTerm(_, plan));
        Function(labels, udf.name);
      }
      case when: CaseWhen => {
        when.predicates.foreach(resolveExpression(_, plan));
        val labels = when.values.map(resolveTerm(_, plan));
        when.elseValue match {
          case Some(expr) => Function(labels :+ (resolveTerm(expr, plan)), ExpressionRegistry.resolveFunction(when));
          case None => Function(labels, ExpressionRegistry.resolveFunction(when));
        }
      }
      case i: If => {
        resolveExpression(i.predicate, plan);
        val tLabel = resolveTerm(i.trueValue, plan);
        val fLabel = resolveTerm(i.falseValue, plan);
        Function(List(tLabel, fLabel), ExpressionRegistry.resolveFunction(i));
      }

      case _ => Function(expression.children.map(resolveExpression(_, plan)), expression.getName);
    }
  }

  private def resolveTermFunction(expression: Expression, plan: LogicalPlan): Function = {
    val labels = expression.children.map(resolveTerm(_, plan));
    Function(labels, ExpressionRegistry.resolveFunction(expression));
  }

  private def resolveJoinCondition(expression: BinaryComparison, plan: LogicalPlan): Unit = {
    var lefts: mutable.Set[EquiVertex] = null;
    var rights: mutable.Set[EquiVertex] = null;
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
    for (left <- lefts) {
      for (right <- rights) {
        addEquiEdge(left, right);
        addEquiEdge(right, left);
      }
    }
  }

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

  private def resolveJoinLabel(label: Label, set: mutable.Set[EquiVertex]): Unit = {
    label match {
      case col: ColumnLabel => set.add(ColumnVertex(col.attr));
      case cons: Constant => set.add(ConstantVertex(cons.value));
      case func: Function => func.children.foreach(resolveJoinLabel(_, set));
      case _ => throw new RuntimeException(s"Predicate $label should not appear in equi-join expression.");
    }
  }

  private def addTable(projections: mutable.Map[Attribute, Label]): Unit = {
    val table = projections.values.map(label => {
      val cond = label.asInstanceOf[ColumnLabel];
      (cond.attr.name, cond);
    }).toMap;
    table.foreach(t => tables.put(t._2, table));
  }

  private def addEquiEdge(a: EquiVertex, b: EquiVertex): Unit = {
    val set = equiEdges.getOrElseUpdate(a, new mutable.HashSet[EquiVertex]);
    set.add(b);
  }

  private def fulfillConditions(label: Label): Unit = {
    label match {
      case cond: ConditionalLabel => fulfillCondition(cond);
      case func: Function => func.children.foreach(fulfillConditions(_));
      case pred: Predicate => pred.children.foreach(fulfillConditions(_));
      case _ =>
    }
  }

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

  private def getEquis(v: EquiVertex): mutable.Set[EquiVertex] = {
    var reach = equis.getOrElse(v, null);
    if (reach != null) {
      return reach;
    }
    reach = new mutable.HashSet[EquiVertex];
    equis.put(v, reach);

    val processed = new mutable.HashSet[EquiVertex];
    val queue = new mutable.Queue[EquiVertex];
    queue.enqueue(v);
    processed.add(v);
    while (!queue.isEmpty) {
      val cur = queue.dequeue;
      val edge = equiEdges.getOrElse(cur, null);
      if (edge != null) {
        edge.foreach(e => {
          reach.add(e);
          if (!processed.contains(e)) {
            processed.add(e);
            queue.enqueue(e);
          }
        });
      }
    }
    return reach;
  }

}