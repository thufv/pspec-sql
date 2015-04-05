package org.apache.spark.sql.catalyst.dp

import scala.collection.JavaConversions._
import scala.collection.mutable.HashMap
import scala.collection.mutable.HashSet
import scala.collection.mutable.Stack
import scala.collection.mutable.LinkedHashSet
import org.apache.spark.Logging
import org.apache.spark.sql.catalyst.checker.ExpressionRegistry._
import org.apache.spark.sql.catalyst.expressions.Abs
import org.apache.spark.sql.catalyst.expressions.Add
import org.apache.spark.sql.catalyst.expressions.AggregateExpression
import org.apache.spark.sql.catalyst.expressions.Alias
import org.apache.spark.sql.catalyst.expressions.And
import org.apache.spark.sql.catalyst.expressions.Attribute
import org.apache.spark.sql.catalyst.expressions.BinaryArithmetic
import org.apache.spark.sql.catalyst.expressions.BinaryComparison
import org.apache.spark.sql.catalyst.expressions.Cast
import org.apache.spark.sql.catalyst.expressions.Divide
import org.apache.spark.sql.catalyst.expressions.EqualNullSafe
import org.apache.spark.sql.catalyst.expressions.EqualTo
import org.apache.spark.sql.catalyst.expressions.Expression
import org.apache.spark.sql.catalyst.expressions.GreaterThan
import org.apache.spark.sql.catalyst.expressions.GreaterThanOrEqual
import org.apache.spark.sql.catalyst.expressions.In
import org.apache.spark.sql.catalyst.expressions.InSet
import org.apache.spark.sql.catalyst.expressions.LessThan
import org.apache.spark.sql.catalyst.expressions.LessThanOrEqual
import org.apache.spark.sql.catalyst.expressions.Literal
import org.apache.spark.sql.catalyst.expressions.Multiply
import org.apache.spark.sql.catalyst.expressions.MutableLiteral
import org.apache.spark.sql.catalyst.expressions.Not
import org.apache.spark.sql.catalyst.expressions.Or
import org.apache.spark.sql.catalyst.expressions.Predicate
import org.apache.spark.sql.catalyst.expressions.Remainder
import org.apache.spark.sql.catalyst.expressions.Subtract
import org.apache.spark.sql.catalyst.expressions.UnaryExpression
import org.apache.spark.sql.catalyst.expressions.UnaryMinus
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.apache.spark.sql.catalyst.plans.logical.BinaryNode
import org.apache.spark.sql.catalyst.plans.logical.Filter
import org.apache.spark.sql.catalyst.plans.logical.Join
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import org.jgrapht.alg.ConnectivityInspector
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import solver.ResolutionPolicy
import solver.Solver
import solver.constraints.Constraint
import solver.constraints.IntConstraintFactory
import solver.constraints.LogicalConstraintFactory
import solver.constraints._
import solver.search.strategy.IntStrategyFactory
import solver.variables.IntVar
import solver.variables.VariableFactory
import solver.variables._
import solver.search.loop.monitors.SearchMonitorFactory
import org.apache.spark.sql.catalyst.checker.ColumnLabel
import org.apache.spark.sql.catalyst.dp.DPUtil._
import org.apache.spark.sql.catalyst.checker.DataLabel
import org.apache.spark.sql.catalyst.checker.Function
import org.apache.spark.sql.catalyst.checker.Label
import solver.constraints._
import solver.variables._
import org.apache.spark.sql.catalyst.checker.Constant
import org.apache.spark.sql.catalyst.checker.CheckerUtil._

class AttributeRangeRefiner(val infos: TableInfo, val aggregate: Aggregate) extends Logging {

  private class PredicateFilter {
    private val attributes = new HashSet[Attribute];

    private val attributeGraph = new SimpleGraph[Attribute, DefaultEdge](classOf[DefaultEdge]);

    private val rootAttributes = new HashSet[Attribute];

    def initialize(plan: Aggregate) {
      buildGraph(plan);
      val alg = new ConnectivityInspector[Attribute, DefaultEdge](attributeGraph);

      rootAttributes.foreach(attr => {
        val set = alg.connectedSetOf(attr);
        attributes.++=(set);
      });
    }

    /**
     * test whether a given predicate should be added as constraint
     */
    def effective(pred: Expression, plan: LogicalPlan): Boolean = {
      pred.foreach(expr => {
        expr match {
          case attr: Attribute => {
            val label = plan.childLabel(attr);
            for (a <- label.attributes) yield {
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

    private def effective(attr: Attribute): Boolean = {
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
          case binary: BinaryNode => {
            //connect left and right attributes in the binary plan
            for (i <- 0 to binary.output.length - 1) {
              val left = collectAttributes(binary.left.output(i), binary);
              val right = collectAttributes(binary.right.output(i), binary);
              connect(left);
              connect(right);
              connect(List(left(0), right(0)));
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
          if (DPUtil.supported(agg)) {
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
          rootAttributes.add(data.attr);
        }
        case func: Function => {
          func.transform match {
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
          connect(attrs);
        }
        case _ =>
      }
    }

    private def connect(attrs: Seq[Attribute]) {
      attrs.foreach(attributeGraph.addVertex(_));
      for (i <- 0 to attrs.length - 2) {
        attributeGraph.addEdge(attrs(i), attrs(i + 1));
      }
    }

    private def collectAttributes(expr: Expression, plan: LogicalPlan): Seq[Attribute] = {
      expr match {
        case attr: Attribute => {
          val label = plan.childLabel(attr);
          collectLabels(label, plan);
        }
        case _ => {
          expr.children.flatMap(collectAttributes(_, plan));
        }
      }
    }

    private def collectLabels(label: Label, plan: LogicalPlan): Seq[Attribute] = {
      label match {
        case column: ColumnLabel => {
          List(column.attr);
        }
        case func: Function => {
          func.children.flatMap(collectLabels(_, plan));
        }
        case _ => Nil;
      }
    }
  }

  private class ConstraintModel {
    private val varMaps = new HashMap[Attribute, IntVar];
    private val attrVars = new LinkedHashSet[IntVar];
    private val tmpVars = new LinkedHashSet[IntVar];

    private val constraintStack = new Stack[Constraint];
    private var constraintIndex = 0;

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
      constraintStack.push(cons);
      return cons;
    }

    def markConstraint(): Int = constraintStack.size;

    def commitConstraint() {
      constraintStack.foreach(solver.post(_));
      constraintStack.clear;
    }

    def rollbackConstraint(mark: Int) {
      while (constraintStack.size > mark) {
        constraintStack.pop;
      }
    }

    def getVariable(attr: Attribute): IntVar = {
      return varMaps.getOrElse(attr, null);

    }

    def addTempVariable(v: IntVar): IntVar = {
      tmpVars.add(v);
      return v;
    }
    def addAttrVariable(attr: Attribute, v: IntVar, force: Boolean = false): IntVar = {
      if (!varMaps.contains(attr) || force) {
        attrVars.add(v);
        varMaps.put(attr, v);
      }
      return v;
    }
  }

  private val Search_Limit = 2000; //in ms

  private val solver: Solver = new Solver;

  private var id = 0;

  private val predFilter = new PredicateFilter;

  private val model = new ConstraintModel;

  private val refinedRanges = new HashMap[Attribute, (Int, Int)];

  initialize(aggregate);

  def initialize(plan: Aggregate) {
    SearchMonitorFactory.limitTime(solver, Search_Limit);
    predFilter.initialize(plan);

    plan.aggregateExpressions.foreach(resolveAggregateAttribute(_, plan));
    model.commitConstraint;

    resolvePlan(plan);
  }

  //TODO luochen, add support for ignore refinement
  def get(attr: Attribute, plan: LogicalPlan): (Int, Int) = {
    val result = refinedRanges.getOrElse(attr, null);
    if (result != null) {
      return result;
    }
    val attrVar = model.getVariable(attr);
    if (attrVar == null) {
      logWarning(s"no attribute refinement needed, return original range for $attr");
      val label = plan.childLabel(attr);
      val range = resolveAttributeRange(label);
      refinedRanges.put(attr, (range));
      return range;
    }

    //constraint solving
    val allVars = model.getAllVaraibles(attrVar);

    logWarning(s"start solving upper bound for $attr");
    solver.set(IntStrategyFactory.lexico_UB(allVars: _*));
    solver.findOptimalSolution(ResolutionPolicy.MAXIMIZE, attrVar);
    val up = attrVar.getUB();
    if (solver.hasReachedLimit()) {
      logWarning(s"solving upper bound for $attr timeout, fall back to original $up");
    }
    solver.getSearchLoop.reset;

    logWarning(s"start solving lower bound for $attr");
    solver.set(IntStrategyFactory.lexico_LB(allVars: _*));
    solver.findOptimalSolution(ResolutionPolicy.MINIMIZE, attrVar);
    val low = attrVar.getLB();
    if (solver.hasReachedLimit()) {
      logWarning(s"solving lower bound for $attr timeout, fall back to original $low");
    }
    solver.getSearchLoop.reset;

    val range = (low, up);
    refinedRanges.put(attr, range);
    logWarning(s"update range for $attr as [$low, $up]");
    return range;
  }

  private def resolvePlan(plan: LogicalPlan) {
    //initialize
    plan.children.foreach(resolvePlan(_));
    plan match {
      case filter: Filter => {
        val constraint = resolveExpression(filter.condition, plan);
        if (constraint != null) {
          model.commitConstraint;
          solver.post(constraint);
        }
      }
      case join: Join => {
        join.condition match {
          case Some(cond) => {
            val constraint = resolveExpression(cond, plan);
            if (constraint != null) {
              model.commitConstraint;
              solver.post(constraint);
            }
          }
          case _ =>
        }
      }
      case _ =>
    }
  }

  private def resolveAggregateAttribute(expr: Expression, plan: Aggregate) {
    expr match {
      case attr: Attribute => {
        val label = plan.childLabel(attr);
        if (label.contains(Func_Union, Func_Intersect, Func_Except)) {
          model.addAttrVariable(attr, resolveLabelVar(label, false), true);
        } else {
          model.addAttrVariable(attr, resolveLabelVar(label, false));
        }
      }
      case alias: Alias => {
        resolveAggregateAttribute(alias.child, plan);
      }
      case cast: Cast => {
        resolveAggregateAttribute(cast.child, plan);
      }
      case agg: AggregateExpression => {
        if (DPUtil.supported(agg)) {
          resolveAggregateAttribute(agg.children(0), plan);
        }
      }
      case _ =>
    }
  }

  private def resolveExpression(cond: Expression, plan: LogicalPlan): Constraint = {

    cond match {
      case and: And => {
        val list = collect(and, classOf[And]).map(resolveExpression(_, plan)).filter(_ != null);
        list.size match {
          case 0 => null;
          case 1 => list(0);
          case _ => LogicalConstraintFactory.and(list.toArray: _*);
        }
      }
      case or: Or => {
        val mark = model.markConstraint;
        val list = collect(or, classOf[Or]).map(resolveExpression(_, plan));
        if (list.exists(_ == null)) {
          model.rollbackConstraint(mark);
          return null;
        } else {
          return LogicalConstraintFactory.or(list.toArray: _*);
        }
      }
      case not: Not => {
        val child = resolveExpression(not.child, plan);
        if (child == null) {
          return null;
        } else {
          return LogicalConstraintFactory.not(child);
        }
      }

      case pred: Predicate => {
        val mark = model.markConstraint;
        val constraint = resolvePredicate(pred, plan);
        if (constraint == null) {
          //clear temp constraints
          model.rollbackConstraint(mark);
          return null;
        } else {
          return constraint;
        }
      }

      case _ => null;
    }
  }

  private def resolvePredicate(pred: Predicate, plan: LogicalPlan): Constraint = {
    def binaryPredicateHelper(binary: BinaryComparison, trans: (IntVar, IntVar) => Constraint): Constraint = {
      try {
        val var1 = resolveTerm(binary.left, plan);
        val var2 = resolveTerm(binary.right, plan);
        if (var1 == null || var2 == null) {
          return null;
        }
        return trans(var1, var2);
      } catch {
        case e: Exception => {
          //TODO
          logWarning(s"${e.getMessage}");
          //e.printStackTrace();
          return null;
        }
      }
    }
    if (!predFilter.effective(pred, plan)) {
      return null;
    }

    pred match {
      case equal: EqualTo => {
        binaryPredicateHelper(equal, IntConstraintFactory.arithm(_, "=", _));
      }
      case equal: EqualNullSafe => {
        binaryPredicateHelper(equal, IntConstraintFactory.arithm(_, "=", _));
      }
      case lt: LessThan => {
        binaryPredicateHelper(lt, IntConstraintFactory.arithm(_, "<", _));
      }
      case lte: LessThanOrEqual => {
        binaryPredicateHelper(lte, IntConstraintFactory.arithm(_, "<=", _));
      }
      case gt: GreaterThan => {
        binaryPredicateHelper(gt, IntConstraintFactory.arithm(_, ">", _));
      }
      case gte: GreaterThanOrEqual => {
        binaryPredicateHelper(gte, IntConstraintFactory.arithm(_, ">=", _));
      }
      case in: In => {
        val left = resolveTerm(in.value, plan);
        val rights = in.list.map(resolveTerm(_, plan));
        if (left == null || rights.exists(_ == null)) {
          return null;
        } else {
          val constraints = rights.map(right => IntConstraintFactory.arithm(left, "=", right));
          model.addConstraint(LogicalConstraintFactory.or(constraints: _*));
        }
      }
      case inSet: InSet => {
        val left = resolveTerm(inSet.value, plan);
        if (left == null) {
          return null;
        } else {
          val constraints = inSet.hset.map(v => IntConstraintFactory.arithm(left, "=", VariableFactory.fixed(v, solver))).toArray;
          model.addConstraint(LogicalConstraintFactory.or(constraints: _*));
        }
      }
      case _ => null
    }
  }

  private def resolveTerm(term: Expression, plan: LogicalPlan): IntVar = {
    term match {
      //add support for complex data types
      case attr: Attribute => {
        val attrVar = model.getVariable(attr);
        if (attrVar != null) {
          return attrVar;
        } else {
          //create a new attribute variable
          val label = plan.childLabel(attr);
          if (label.contains(Func_Union, Func_Intersect, Func_Except)) {
            model.addAttrVariable(attr, resolveLabelVar(label), true);
          } else {
            model.addAttrVariable(attr, resolveLabelVar(label));
          }
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

    def binaryVarHelper(rangeFunc: ((Int, Int), (Int, Int)) => (Int, Int), transFunc: (IntVar, IntVar, IntVar) => Constraint): IntVar = {
      val left = resolveTerm(binary.left, plan);
      val right = resolveTerm(binary.right, plan);
      if (left == null || right == null) {
        return null;
      } else {

        val range = rangeFunc((left.getLB, left.getUB), (right.getLB, right.getUB));
        val tmp =
          if (range == null) {
            VariableFactory.fixed(0, solver);
          } else {
            newTmpVar(range._1, range._2);
          }
        model.addTempVariable(tmp);
        model.addConstraint(transFunc(left, right, tmp));
        return tmp;
      }
    }
    binary match {
      case add: Add => {
        val list = collect(add, classOf[Add]).map(resolveTerm(_, plan));
        if (list.exists(_ == null)) {
          return null;
        }
        val range = rangeAdd(list.map(v => (v.getLB, v.getUB)): _*);
        val tmp = newTmpVar(range._1, range._2);
        model.addTempVariable(tmp);
        model.addConstraint(IntConstraintFactory.sum(list.toArray, tmp));
        return tmp;
      }
      case subtract: Subtract => {
        val list = collect(subtract, classOf[Subtract]).map(resolveTerm(_, plan)).toList;
        if (list.exists(_ == null)) {
          return null;
        }
        val range = rangeSubtract(list.map(v => (v.getLB, v.getUB)): _*);
        val tmp = newTmpVar(range._1, range._2);
        model.addTempVariable(tmp);
        val array = (list.head :: list.tail.map(VariableFactory.minus(_))).toArray;
        model.addConstraint(IntConstraintFactory.sum(array, tmp));
        return tmp;
      }
      case divide: Divide => {
        binaryVarHelper(rangeDivide(_, _), (left, right, tmp) => {
          IntConstraintFactory.eucl_div(left, right, tmp);
        });
      }
      case multiply: Multiply => {
        binaryVarHelper(rangeTimes(_, _), (left, right, tmp) => {
          IntConstraintFactory.times(left, right, tmp);
        });
      }
      case remainder: Remainder => {
        binaryVarHelper(rangeRemainder(_, _), (left, right, tmp) => {
          IntConstraintFactory.mod(left, right, tmp);
        });
      }

      case _ => null;
    }
  }

  private def resolveUnaryArithmetic(unary: UnaryExpression, plan: LogicalPlan): IntVar = {

    def unaryArithmHelper(transFunc: (IntVar) => IntVar): IntVar = {
      val child = resolveTerm(unary.child, plan);
      if (child == null) {
        return null;
      } else {
        return transFunc(child);
      }
    }

    unary match {
      case minus: UnaryMinus => {
        unaryArithmHelper(VariableFactory.minus(_));
      }
      case abs: Abs => {
        unaryArithmHelper(VariableFactory.abs(_));
      }
      case _ => null;

    }
  }

  private def resolveLabelVar(label: Label, arithm: Boolean = true): IntVar = {

    def unaryArithmHelper(func: Function, transFunc: (IntVar) => IntVar): IntVar = {
      val child = resolveLabelVar(func.children(0), arithm);
      if (child == null) {
        return null;
      }
      return transFunc(child);
    }

    def binaryArithmHelper(func: Function, rangeFunc: ((Int, Int), (Int, Int)) => (Int, Int), transFunc: (IntVar, IntVar, IntVar) => Constraint): IntVar = {
      val left = resolveLabelVar(func.children(0), arithm);
      val right = resolveLabelVar(func.children(1), arithm);
      if (left == null || right == null) {
        return null;
      } else {
        val range = rangeFunc((left.getLB, left.getUB), (right.getLB, right.getUB));
        val tmp =
          if (range == null) {
            VariableFactory.fixed(0, solver);
          } else {
            newTmpVar(range._1, range._2);
          }
        model.addTempVariable(tmp);
        model.addConstraint(transFunc(left, right, tmp));
        return tmp;
      }
    }

    label match {
      case column: ColumnLabel => {
        val attrVar = model.getVariable(column.attr);
        if (attrVar != null) {
          return attrVar;
        } else {
          val range = infos.get(column.database, column.table, column.attr.name);
          model.addAttrVariable(column.attr, VariableFactory.bounded(s"${column.attr}", range.low, range.up, solver));
        }
      }
      case cons: Constant => {
        VariableFactory.fixed(cons.value, solver);
      }
      case func: Function => {
        //TODO: luochen add support for complex data types
        func.transform match {
          case Arithmetic_UnaryMinus if (arithm) => {
            unaryArithmHelper(func, VariableFactory.scale(_, -1));
          }
          case Arithmetic_Abs if (arithm) => {
            unaryArithmHelper(func, VariableFactory.abs(_));
          }
          case Arithmetic_Add if (arithm) => {
            val labels = collect(label, Arithmetic_Add);
            val vars = labels.map(resolveLabelVar(_, arithm));
            if (vars.exists(_ == null)) {
              return null;
            } else {
              val range = rangeAdd(vars.map(v => (v.getLB, v.getUB)): _*);
              val tmp = newTmpVar(range._1, range._2);
              model.addTempVariable(tmp);
              model.addConstraint(IntConstraintFactory.sum(vars.toArray, tmp));
              tmp;
            }
          }
          case Arithmetic_Subtract if (arithm) => {
            val labels = collect(label, Arithmetic_Subtract);
            val vars = labels.map(resolveLabelVar(_, arithm));
            if (vars.exists(_ == null)) {
              return null;
            } else {
              val range = rangeSubtract(vars.map(v => (v.getLB, v.getUB)): _*);
              val tmp = newTmpVar(range._1, range._2);
              model.addTempVariable(tmp);
              model.addConstraint(IntConstraintFactory.sum(vars.toArray, tmp));
              tmp;
            }
          }
          case Arithmetic_Divide if (arithm) => {
            binaryArithmHelper(func, rangeDivide(_, _), (left: IntVar, right: IntVar, result: IntVar) => {
              IntConstraintFactory.eucl_div(left, right, result);
            });
          }
          case Arithmetic_Multiply if (arithm) => {
            binaryArithmHelper(func, rangeTimes(_, _), (left: IntVar, right: IntVar, result: IntVar) => {
              IntConstraintFactory.times(left, right, result);
            });
          }
          case Arithmetic_Remainder if (arithm) => {
            binaryArithmHelper(func, rangeRemainder(_, _), (left: IntVar, right: IntVar, result: IntVar) => {
              IntConstraintFactory.mod(left, right, result);
            });
          }

          case Func_Union => {
            binaryArithmHelper(func, rangeUnion(_, _), (left: IntVar, right: IntVar, result: IntVar) => {
              LogicalConstraintFactory.or(IntConstraintFactory.arithm(result, "=", left), IntConstraintFactory.arithm(result, "=", right));
            });
          }

          case Func_Intersect => {
            binaryArithmHelper(func, rangeIntersect(_, _), (left: IntVar, right: IntVar, result: IntVar) => {
              LogicalConstraintFactory.and(IntConstraintFactory.arithm(result, "=", left), IntConstraintFactory.arithm(result, "=", right));
            });
          }

          case Func_Except => {
            binaryArithmHelper(func, rangeExcept(_, _), (left: IntVar, right: IntVar, result: IntVar) => {
              LogicalConstraintFactory.and(IntConstraintFactory.arithm(result, "=", left), IntConstraintFactory.arithm(result, "!=", right));
            });
          }

          case _ => null;
        }
      }
      case _ => null;

    }
  }

  private def resolveAttributeRange(label: Label): (Int, Int) = {
    def rangeHelper(func: Function, trans: ((Int, Int), (Int, Int)) => (Int, Int)): (Int, Int) = {
      val left = resolveAttributeRange(func.children(0));
      val right = resolveAttributeRange(func.children(0));
      trans(left, right);
    }

    label match {
      case column: ColumnLabel => {
        val info = infos.get(column.database, column.table, column.attr.name);
        (info.low, info.up);
      }
      case func: Function => {
        func.transform match {
          case Func_Union => {
            rangeHelper(func, DPUtil.rangeUnion(_, _));
          }
          case Func_Intersect => {
            rangeHelper(func, DPUtil.rangeIntersect(_, _));
          }
          case Func_Except => {
            rangeHelper(func, DPUtil.rangeExcept(_, _));
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

  private def newTmpVar(min: Int, max: Int): IntVar = {
    VariableFactory.bounded(s"t_$nextId", Math.max(min, VariableFactory.MIN_INT_BOUND), Math.min(max, VariableFactory.MAX_INT_BOUND), solver);
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
      case null => 0;
      case _ => throw new RuntimeException(s"invalid argument: $any.");
    }
  }

}