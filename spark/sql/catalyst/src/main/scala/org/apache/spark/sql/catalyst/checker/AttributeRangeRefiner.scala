package org.apache.spark.sql.catalyst.checker

import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import solver.Solver
import org.apache.spark.sql.catalyst.expressions.Attribute
import scala.collection.JavaConversions._
import scala.collection.mutable.HashSet
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.jgrapht.graph.SimpleGraph
import org.jgrapht.graph.DefaultEdge
import org.apache.spark.sql.catalyst.expressions.Expression
import org.apache.spark.sql.catalyst.expressions.Cast
import org.apache.spark.sql.catalyst.expressions.Alias
import org.apache.spark.sql.catalyst.checker.ExpressionRegistry._
import org.apache.spark.sql.catalyst.plans.logical.Filter
import org.apache.spark.sql.catalyst.plans.logical.Join
import org.apache.spark.sql.catalyst.expressions.And
import org.apache.spark.sql.catalyst.expressions
import org.apache.spark.sql.catalyst.expressions.Not
import org.apache.spark.sql.catalyst.expressions.Or
import org.apache.spark.sql.catalyst.expressions.BinaryComparison
import org.jgrapht.alg.ConnectivityInspector
import scala.collection.mutable.HashMap
import solver.variables.IntVar
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import solver.constraints.Constraint
import solver.constraints.LogicalConstraintFactory
import org.apache.spark.sql.catalyst.expressions.Predicate
import solver.constraints.IntConstraintFactory
import solver.variables.VariableFactory
import org.apache.spark.sql.catalyst.expressions.EqualTo
import org.apache.spark.sql.catalyst.expressions.LessThan
import org.apache.spark.sql.catalyst.expressions.LessThanOrEqual
import org.apache.spark.sql.catalyst.expressions.GreaterThan
import org.apache.spark.sql.catalyst.expressions.GreaterThanOrEqual
import org.apache.spark.sql.catalyst.expressions.BinaryPredicate
import org.apache.spark.sql.catalyst.expressions.EqualNullSafe
import org.apache.spark.sql.catalyst.expressions.Alias
import org.apache.spark.sql.catalyst.expressions.Coalesce
import org.apache.spark.sql.catalyst.expressions.Subtract
import org.apache.spark.sql.catalyst.expressions.Literal
import org.apache.spark.sql.catalyst.expressions.BinaryArithmetic
import org.apache.spark.sql.catalyst.expressions.Add
import org.apache.spark.sql.catalyst.expressions.Divide
import org.apache.spark.sql.catalyst.expressions.Multiply
import org.apache.spark.sql.catalyst.expressions.Remainder
import org.apache.spark.sql.catalyst.expressions.MutableLiteral
import org.apache.spark.sql.catalyst.expressions.UnaryMinus
import org.apache.spark.sql.catalyst.expressions.UnaryExpression
import org.apache.spark.sql.catalyst.expressions.Abs
import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer
import solver.ResolutionPolicy
import solver.search.strategy.IntStrategyFactory
import org.apache.spark.Logging
import org.apache.spark.sql.catalyst.plans.logical.LeafNode
import org.apache.spark.sql.catalyst.expressions.AggregateExpression
import org.apache.spark.sql.catalyst.expressions.In
import org.apache.spark.sql.catalyst.expressions.InSet
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.apache.spark.sql.catalyst.expressions.And
import org.apache.spark.sql.catalyst.expressions.BinaryExpression
import scala.reflect.ClassTag

class AttributeRangeRefiner(val infos: TableInfo, val aggregate: Aggregate) extends Logging {

  private class AttributeTracker {
    private val attributes = new HashSet[Attribute];

    private val attributeGraph = new SimpleGraph[Attribute, DefaultEdge](classOf[DefaultEdge]);

    private val roots = new HashSet[Attribute];

    def apply(plan: Aggregate) {
      buildGraph(plan);
      val alg = new ConnectivityInspector[Attribute, DefaultEdge](attributeGraph);

      roots.foreach(attr => {
        val set = alg.connectedSetOf(attr);
        attributes.++=(set);
      });
    }

    /**
     * test whether a given predicate should be added as constraint
     */
    def isEffective(pred: Expression, plan: LogicalPlan): Boolean = {
      pred.foreach(expr => {
        expr match {
          case attr: Attribute => {
            val label = plan.childLabel(attr);
            for (a <- label.attributes) {
              if (attributes.contains(a)) {
                return true;
              }
            }
          }
          case _ =>
        }
      });
      return false;
    }

    def isEffective(attr: Attribute): Boolean = {
      attributes.contains(attr);
    }

    private def buildGraph(plan: Aggregate) {
      plan.aggregateExpressions.foreach(checkAggregateExpression(_, plan));

      plan.foreach(node => {
        node match {
          case filter: Filter => {
            checkExpression(filter.condition, plan);
          }
          case join: Join => {
            join.condition match {
              case Some(cond) => checkExpression(cond, plan);
              case _ =>
            }
          }
          case _ =>
        }
      });

    }

    private def checkAggregateExpression(expr: Expression, plan: LogicalPlan) {
      expr match {
        case attr: Attribute => {
          val label = plan.childLabel(attr);
          checkAggregateLabel(label);
        }
        case cast: Cast => checkAggregateExpression(cast.child, plan);
        case alias: Alias => checkAggregateExpression(alias.child, plan);
        case agg: AggregateExpression => {
          if (DPHelper.supported(agg)) {
            checkAggregateExpression(agg.children(0), plan);
          }
        }
        case _ =>
      }
    }

    private def checkAggregateLabel(label: Label) {
      label match {
        case data: DataLabel => {
          attributeGraph.addVertex(data.attr);
          roots.add(data.attr);
        }
        case func: Function => {
          func.udf match {
            case Func_Union | Func_Intersect | Func_Except => func.children.foreach(checkAggregateLabel(_));
            case _ =>
          }
        }
        case _ =>
      }
    }

    private def checkExpression(expr: Expression, plan: LogicalPlan) {
      expr match {
        case _: And | _: Or | _: Not => {
          expr.children.foreach(checkExpression(_, plan));
        }
        case _: BinaryComparison | _: In | _: InSet => {
          val attrs = collectAttributes(expr, plan);
          attrs.foreach(attributeGraph.addVertex(_));
          for (i <- 0 to attrs.length - 2) {
            attributeGraph.addEdge(attrs(i), attrs(i + 1));
          }
        }
        case _ =>
      }
    }

    private def collectAttributes(expr: Expression, plan: LogicalPlan): List[Attribute] = {
      expr match {
        case attr: Attribute => {
          val label = plan.childLabel(attr);
          collectAttributes(label, plan);
        }
        case _ => {
          expr.children.flatMap(collectAttributes(_, plan)).toList;
        }
      }
    }

    private def collectAttributes(label: Label, plan: LogicalPlan): List[Attribute] = {
      label match {
        case column: ColumnLabel => {
          List(column.attr);
        }
        case func: Function => {
          func.children.flatMap(collectAttributes(_, plan)).toList;
        }
        case _ => Nil;
      }
    }

  }

  private class VariableTracker {
    private val varMaps = new HashMap[Attribute, IntVar];
    private val constraintBuffer = new ListBuffer[Constraint];

    private val attrVars = new ListBuffer[IntVar];
    private val tmpVars = new ListBuffer[IntVar];

    private lazy val allVars: Array[IntVar] = {
      val array = new Array[IntVar](attrVars.size + tmpVars.size);
      attrVars.copyToArray(array);
      tmpVars.copyToArray(array, attrVars.size);
      array;
    }

    def getAllVaraibles(attrVar: IntVar): Array[IntVar] = {
      val target = allVars.indexOf(attrVar);
      val tmp = allVars(0);
      allVars(0) = attrVar;
      allVars(target) = tmp;
      return allVars;
    }

    def addConstraint(cons: Constraint): Constraint = {
      constraintBuffer.append(cons);
      return cons;
    }

    def commitConstraint() {
      constraintBuffer.foreach(solver.post(_));
      constraintBuffer.clear;
    }

    def rollbackConstraint() {
      constraintBuffer.clear;
    }

    def getAttrVar(attr: Attribute): IntVar = {
      return varMaps.getOrElse(attr, null);

    }

    def addTempVariable(v: IntVar): IntVar = {
      tmpVars.append(v);
      return v;
    }
    def addAttrVariable(attr: Attribute, v: IntVar): IntVar = {
      if (!varMaps.contains(attr)) {
        attrVars.append(v);
        varMaps.put(attr, v);
      }
      return v;
    }

  }

  private val solver: Solver = new Solver;

  private var id = 0;

  private val attrTracker = new AttributeTracker;

  private val varTracker = new VariableTracker;

  private val refinedRanges = new HashMap[Attribute, ColumnInfo];

  initialize(aggregate);

  def initialize(plan: Aggregate) {
    attrTracker.apply(plan);

    buildConstraints(plan);
  }

  def get(database: String, table: String, attr: Attribute): ColumnInfo = {
    val result = refinedRanges.getOrElse(attr, null);
    if (result != null) {
      return result;
    }
    val range = infos.get(database, table, attr.name);
    val attrVar = varTracker.getAttrVar(attr);
    if (attrVar == null) {
      logWarning(s"no attribute refinement needed, return original range for $attr");
      refinedRanges.put(attr, range);
      return range;
    }

    //constraint solving
    val allVars = varTracker.getAllVaraibles(attrVar);
    solver.set(IntStrategyFactory.lexico_UB(allVars: _*));
    solver.findOptimalSolution(ResolutionPolicy.MAXIMIZE, attrVar);
    val up = attrVar.getValue;
    solver.getSearchLoop.reset;

    solver.set(IntStrategyFactory.lexico_LB(allVars: _*));
    solver.findOptimalSolution(ResolutionPolicy.MINIMIZE, attrVar);
    val low = attrVar.getValue;
    solver.getSearchLoop.reset;

    val newRange = new ColumnInfo(low, up, range.multiplicity, range.dataType);
    refinedRanges.put(attr, range);
    logWarning(s"update range for $attr as [$low, $up]");
    return range;
  }

  private def buildConstraints(plan: LogicalPlan) {
    //initialize
    plan.children.foreach(buildConstraints(_));
    plan match {
      //TODO handle binary plans
      case agg: Aggregate => {
        //do nothing
      }
      case filter: Filter => {
        val constraint = resolveCondition(filter.condition, plan);
        if (constraint != null) {
          solver.post(constraint);
        }
      }
      case join: Join => {
        join.condition match {
          case Some(cond) => {
            val constraint = resolveCondition(cond, plan);
            if (constraint != null) {
              solver.post(constraint);
            }
          }
          case _ =>
        }
      }
      case _ =>
    }
  }

  private def resolveCondition(cond: Expression, plan: LogicalPlan): Constraint = {

    cond match {
      case and: And => {
        val list = collect[And](and).map(resolveCondition(_, plan)).filter(_ != null);
        list.size match {
          case 0 => null;
          case 1 => list(0);
          case _ => LogicalConstraintFactory.and(list.toArray: _*);
        }
      }
      case or: Or => {
        val list = collect[Or](or).map(resolveCondition(_, plan));
        if (list.exists(_ == null)) {
          return null;
        } else {
          return LogicalConstraintFactory.or(list.toArray: _*);
        }
      }
      case not: Not => {
        val child = resolveCondition(not.child, plan);
        if (child == null) {
          return null;
        } else {
          return LogicalConstraintFactory.not(child);
        }
      }

      case pred: Predicate => {
        resolvePredicate(pred, plan);
      }

      case _ => null;
    }
  }

  private def resolvePredicate(pred: Predicate, plan: LogicalPlan): Constraint = {

    def binaryConstraintHelper(binary: BinaryComparison, trans: (IntVar, IntVar) => Constraint): Constraint = {
      try {
        val var1 = resolveTerm(binary.left, plan);
        val var2 = resolveTerm(binary.right, plan);
        if (var1 == null || var2 == null) {
          //temporary constraints should be removed
          varTracker.rollbackConstraint;
          return null;
        }
        varTracker.commitConstraint;
        return trans(var1, var2);
      } catch {
        case e: Exception => {
          //TODO
          e.printStackTrace();
          varTracker.rollbackConstraint;
          return null;
        }
      }
    }

    if (!attrTracker.isEffective(pred, plan)) {
      return null;
    }

    pred match {
      case equal: EqualTo => {
        binaryConstraintHelper(equal, IntConstraintFactory.arithm(_, "=", _));
      }
      case equal: EqualNullSafe => {
        binaryConstraintHelper(equal, IntConstraintFactory.arithm(_, "=", _));
      }
      case lt: LessThan => {
        binaryConstraintHelper(lt, IntConstraintFactory.arithm(_, "<", _));
      }
      case lte: LessThanOrEqual => {
        binaryConstraintHelper(lte, IntConstraintFactory.arithm(_, "<=", _));
      }
      case gt: GreaterThan => {
        binaryConstraintHelper(gt, IntConstraintFactory.arithm(_, ">", _));
      }
      case gte: GreaterThanOrEqual => {
        binaryConstraintHelper(gte, IntConstraintFactory.arithm(_, ">=", _));
      }
      case in: In => {
        val left = resolveTerm(in.value, plan);
        val rights = in.list.map(resolveTerm(_, plan));
        if (rights.exists(_ == null)) {
          return null;
        } else {
          val constraints = rights.map(right => IntConstraintFactory.arithm(left, "=", right));
          varTracker.addConstraint(LogicalConstraintFactory.or(constraints: _*));
        }
      }
      case inSet: InSet => {
        val left = resolveTerm(inSet.value, plan);
        val constraints = inSet.hset.map(v => IntConstraintFactory.arithm(left, "=", VariableFactory.fixed(v, solver))).toArray;
        varTracker.addConstraint(LogicalConstraintFactory.or(constraints: _*));
      }
      case _ => null
    }
  }

  private def resolveTerm(term: Expression, plan: LogicalPlan): IntVar = {
    term match {
      case attr: Attribute => {
        val attrVar = varTracker.getAttrVar(attr);
        if (attrVar != null) {
          return attrVar;
        } else {
          //create a new attribute variable
          val label = plan.childLabel(attr);
          varTracker.addAttrVariable(attr, resolveLabelVar(label));
        }
      }
      case literal: Literal => {
        VariableFactory.fixed(literal.value, solver);
      }
      case mutable: MutableLiteral => {
        VariableFactory.fixed(mutable.value, solver);
      }
      case alias: Alias => {
        resolveTerm(alias.child, plan);
      }
      case cast: Cast => {
        resolveTerm(cast.child, plan);
      }

      case unary: UnaryExpression => {
        resolveUnaryArithmetic(unary, plan);
      }

      case binary: BinaryArithmetic => {
        resolveBinaryArithmetic(binary, plan);
      }
      case _ => null
    }
  }

  private def resolveBinaryArithmetic(binary: BinaryArithmetic, plan: LogicalPlan): IntVar = {

    def binaryVarHelper(trans: (IntVar, IntVar, IntVar) => Constraint): IntVar = {
      val left = resolveTerm(binary.left, plan);
      val right = resolveTerm(binary.right, plan);
      if (left == null || right == null) {
        return null;
      } else {
        val tmp = VariableFactory.bounded(s"t_${nextId}", VariableFactory.MIN_INT_BOUND, VariableFactory.MAX_INT_BOUND, solver);
        varTracker.addTempVariable(tmp);
        varTracker.addConstraint(trans(left, right, tmp));
        return tmp;
      }
    }
    binary match {
      case add: Add => {
        val list = collect[Add](add).map(resolveTerm(_, plan));
        if (list.exists(_ == null)) {
          return null;
        }
        val tmp = VariableFactory.bounded(s"t_${nextId}", VariableFactory.MIN_INT_BOUND, VariableFactory.MAX_INT_BOUND, solver);
        varTracker.addTempVariable(tmp);
        varTracker.addConstraint(IntConstraintFactory.sum(list.toArray, tmp));
        return tmp;
      }
      case subtract: Subtract => {
        val list = collect[Subtract](subtract).map(resolveTerm(_, plan)).toList;
        if (list.exists(_ == null)) {
          return null;
        }
        val tmp = VariableFactory.bounded(s"t_${nextId}", VariableFactory.MIN_INT_BOUND, VariableFactory.MAX_INT_BOUND, solver);
        varTracker.addTempVariable(tmp);
        val array = (list.head :: list.tail.map(VariableFactory.minus(_))).toArray;
        varTracker.addConstraint(IntConstraintFactory.sum(array, tmp));
        return tmp;
      }
      case divide: Divide => {
        binaryVarHelper((left, right, tmp) => {
          IntConstraintFactory.eucl_div(left, right, tmp);
        });
      }
      case multiply: Multiply => {
        binaryVarHelper((left, right, tmp) => {
          IntConstraintFactory.times(left, right, tmp);
        });
      }
      case remainder: Remainder => {
        binaryVarHelper((left, right, tmp) => {
          IntConstraintFactory.mod(left, right, tmp);
        });
      }

      case _ => null;
    }
  }

  private def resolveUnaryArithmetic(unary: UnaryExpression, plan: LogicalPlan): IntVar = {
    def unaryVarHelper(trans: (IntVar) => IntVar): IntVar = {
      val child = resolveTerm(unary.child, plan);
      if (child == null) {
        null;
      } else {
        trans(child);
      }
    }

    unary match {
      case minus: UnaryMinus => {
        unaryVarHelper(VariableFactory.minus(_));
      }
      case abs: Abs => {
        unaryVarHelper(VariableFactory.abs(_));
      }
      case _ => null;

    }
  }

  private def resolveLabelVar(label: Label): IntVar = {

    def unaryLabelHelper(func: Function, trans: (IntVar) => IntVar): IntVar = {
      val child = resolveLabelVar(func.children(0));
      if (child == null) {
        return null;
      }
      return trans(child);
    }

    def binaryLabelHelper(func: Function, trans: (IntVar, IntVar, IntVar) => Constraint): IntVar = {
      val left = resolveLabelVar(func.children(0));
      val right = resolveLabelVar(func.children(1));
      if (left == null || right == null) {
        return null;
      } else {
        val tmp = VariableFactory.bounded(s"t_${nextId}", VariableFactory.MIN_INT_BOUND, VariableFactory.MAX_INT_BOUND, solver);
        varTracker.addTempVariable(tmp);
        varTracker.addConstraint(trans(left, right, tmp));
        return tmp;
      }
    }

    label match {
      case column: ColumnLabel => {
        val attrVar = varTracker.getAttrVar(column.attr);
        if (attrVar != null) {
          return attrVar;
        } else {
          val range = infos.get(column.database, column.table, column.attr.name);
          varTracker.addAttrVariable(column.attr, VariableFactory.bounded(s"${column.attr}", range.low, range.up, solver));

        }
      }
      case cons: Constant => {
        VariableFactory.fixed(cons.value, solver);
      }
      case func: Function => {
        func.udf match {
          case Arithmetic_UnaryMinus => {
            unaryLabelHelper(func, VariableFactory.scale(_, -1));
          }
          case Arithmetic_Abs => {
            unaryLabelHelper(func, VariableFactory.abs(_));
          }
          case Arithmetic_Add => {
            binaryLabelHelper(func, (left: IntVar, right: IntVar, result: IntVar) => {
              IntConstraintFactory.sum(Array(left, right), result);
            });
          }
          case Arithmetic_Subtract => {
            binaryLabelHelper(func, (left: IntVar, right: IntVar, result: IntVar) => {
              IntConstraintFactory.sum(Array(left, VariableFactory.scale(right, -1)), result);
            });
          }
          case Arithmetic_Divide => {
            binaryLabelHelper(func, (left: IntVar, right: IntVar, result: IntVar) => {
              IntConstraintFactory.eucl_div(left, right, result);
            });
          }
          case Arithmetic_Multiply => {
            binaryLabelHelper(func, (left: IntVar, right: IntVar, result: IntVar) => {
              IntConstraintFactory.times(left, right, result);
            });
          }
          case Arithmetic_Remainder => {
            binaryLabelHelper(func, (left: IntVar, right: IntVar, result: IntVar) => {
              IntConstraintFactory.mod(left, right, result);
            });
          }
          case _ => null;
        }
      }
      case _ => null;

    }
  }

  private def nextId(): Int = {
    id += 1;
    id;
  }

  def collect[T <: Expression: ClassTag](expr: Expression): Seq[Expression] = {
    val clazz = implicitly[ClassTag[T]].runtimeClass;
    if (clazz.isInstance(expr)) {
      expr.children.flatMap(collect(_));
    } else {
      List(expr);
    }

  }

  implicit private def anyToInt(any: Any): Int = {
    any match {
      case long: Long => long.toInt;
      case int: Int => int;
      case double: Double => double.toInt;
      case float: Float => float.toInt;
      case short: Short => short.toInt;
      case big: BigDecimal => big.toInt;
      case str: String => str.toInt;
      case null => null.asInstanceOf[Int];
      case _ => throw new RuntimeException(s"invalid argument: $any.");
    }
  }

}