package org.apache.spark.sql.catalyst.checker.dp

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
import org.apache.spark.sql.catalyst.checker.dp.DPUtil._
import org.apache.spark.sql.catalyst.checker.DataLabel
import org.apache.spark.sql.catalyst.checker.FunctionLabel
import org.apache.spark.sql.catalyst.checker.Label
import solver.constraints._
import solver.variables._
import org.apache.spark.sql.catalyst.checker.ConstantLabel
import org.apache.spark.sql.catalyst.checker.util.CheckerUtil._
import org.apache.spark.sql.catalyst.checker.util.TypeUtil._
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Set
import org.apache.spark.sql.catalyst.expressions.Projection
import org.apache.spark.sql.catalyst.plans.logical.Project
import org.apache.spark.sql.catalyst.expressions.GetItem
import org.apache.spark.sql.catalyst.expressions.GetField
import org.apache.spark.sql.catalyst.expressions.GetItem
import scala.collection.mutable.Queue
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.apache.spark.sql.catalyst.plans.logical.LeafNode

class AttributeRangeRefiner(val infos: TableInfo, val aggregate: Aggregate) extends Logging {

  private class PredicateFilter {
    private val attributes = new HashSet[String];

    private val relevantGraph = new SimpleGraph[String, DefaultEdge](classOf[DefaultEdge]);

    //TODO: possible problems for union on complex types
    private val equiGraph = new SimpleGraph[String, DefaultEdge](classOf[DefaultEdge]);

    private val aggAttributes = new HashSet[String];

    private val complexAttributes = new HashSet[String];

    def initialize(plan: Aggregate) {
      buildGraph(plan);
      val alg = new ConnectivityInspector[String, DefaultEdge](relevantGraph);

      aggAttributes.foreach(attr => {
        val set = alg.connectedSetOf(attr);
        attributes ++= set;
      });

      attributes.foreach(attr => {
        if (isComplexAttribute(attr)) {
          val pre = getComplexAttribute(attr);
          val set = attributeSubs.getOrElseUpdate(attr, new HashSet[String]);
          set.add(attr);
        }
      });
    }

    /**
     * test whether a given predicate should be added as constraint
     */
    def effective(pred: Expression, plan: LogicalPlan): Boolean = {
      pred match {
        case attr if (isAttribute(attr)) => {
          return effective(attr);
        }
        case _ => {
          return pred.children.exists(effective(_, plan));
        }
      }

      return false;
    }

    def effective(attribute: Expression): Boolean = {
      val string = getAttributeString(attribute);
      if (string == null) {
        return false;
      } else {
        return attributes.contains(string);
      }
    }

    private def buildGraph(plan: Aggregate) {
      plan.aggregateExpressions.foreach(resolveAggregateExpression(_, plan));

      plan.child.foreach(node => {
        node match {
          case filter: Filter => {
            resolveExpression(filter.condition);
          }
          case join: Join => {
            join.condition match {
              case Some(cond) => resolveExpression(cond);
              case _ =>
            }
          }
          case agg: Aggregate => {
            for (i <- 0 to agg.output.length - 1) {
              connectOutput(agg.aggregateExpressions(i), agg.output(i).asInstanceOf[Attribute]);
            }
          }
          case project: Project => {
            for (i <- 0 to project.output.length - 1) {
              connectOutput(project.projectList(i), project.output(i).asInstanceOf[Attribute]);
            }
          }
          case binary: BinaryNode => {
            //connect left and right attributes in the binary plan
            for (i <- 0 to binary.output.length - 1) {
              val left = binary.left.output(i);
              val right = binary.right.output(i);
              val attr = binary.output(i);
              connectOutput(left, attr);
              connectOutput(right, attr);
            }
          }
          case _ =>
        }
      });

      //post processing, create nodes and edges for complex types
      val alg = new ConnectivityInspector[String, DefaultEdge](equiGraph);

      val queue = new Queue[String];
      complexAttributes.foreach(queue.enqueue(_));
      while (!queue.isEmpty) {
        val attr = queue.dequeue;
        val pre = getComplexAttribute(attr);
        val subtypes = getComplexSubtypes(attr);

        val equivalents = alg.connectedSetOf(pre);
        for (equi <- equivalents) {
          val equiAttr = concatComplexAttribute(equi, subtypes);
          if (!relevantGraph.containsVertex(equiAttr)) {
            relevantGraph.addVertex(equiAttr);
            relevantGraph.addEdge(attr, equiAttr);
            queue.enqueue(equiAttr);
          }
        }
      }
    }

    private def resolveAggregateExpression(expr: Expression, plan: LogicalPlan) {
      expr match {
        case cast: Cast => resolveAggregateExpression(cast.child, plan);
        case alias: Alias => resolveAggregateExpression(alias.child, plan);
        case agg: AggregateExpression => {
          if (agg.enableDP) {
            val attr = agg.children(0);
            aggAttributes.add(getAttributeString(attr));
          }
        }
        case _ =>
      }
    }

    private def resolveExpression(expr: Expression) {
      expr match {
        case _: And | _: Or | _: Not => {
          expr.children.foreach(resolveExpression(_));
        }
        case _: BinaryComparison | _: In | _: InSet => {
          val list = new ListBuffer[String];
          if (collectAttributes(expr, list)) {
            connect(list: _*);
          }
        }
        case _ =>
      }
    }

    private def collectAttributes(expr: Expression, list: ListBuffer[String]): Boolean = {
      expr match {
        case a if (isAttribute(a)) => {
          list.append(getAttributeString(a));
          true;
        }
        case e if (supportArithmetic(e)) => {
          e.children.forall(collectAttributes(_, list));
        }
        case _ => false;
      }
    }

    private def connectOutput(expr: Expression, output: Attribute) {
      val list = new ListBuffer[String];
      list.append(getAttributeString(output));
      if (collectAttributes(expr, list)) {
        connect(list: _*);
      }

      if (output.dataType.isPrimitive) {
        return ;
      }
      val attr = resolveSimpleAttribute(expr);
      if (attr != null) {
        val str1 = getAttributeString(output);
        val str2 = getAttributeString(attr);
        equiGraph.addVertex(str1);
        equiGraph.addVertex(str2);
        equiGraph.addEdge(str1, str2);
      }
    }

    private def connect(attrs: String*) {
      attrs.foreach(attr => {
        relevantGraph.addVertex(attr);
        if (isComplexAttribute(attr)) {
          complexAttributes.add(attr);
        }
      });

      for (i <- 0 to attrs.length - 2) {
        relevantGraph.addEdge(attrs(i), attrs(i + 1));
      }
    }

  }

  private class ConstraintModel {
    private val attrVars = new HashMap[String, IntVar];
    private val tmpVars = new HashSet[IntVar];

    private val constraintStack = new Stack[Constraint];
    private var constraintIndex = 0;

    private lazy val allVars: Array[IntVar] = {
      val array = new Array[IntVar](attrVars.size + tmpVars.size);
      attrVars.values.copyToArray(array);
      tmpVars.copyToArray(array, attrVars.size);
      array;
    }

    def getAllVaraibles(head: IntVar): Array[IntVar] = {
      val target = allVars.indexOf(head);
      val tmp = allVars(0);
      allVars(0) = head;
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

    def getVariable(attr: String): IntVar = {
      return attrVars.getOrElse(attr, null);
    }

    def addTempVariable(v: IntVar): IntVar = {
      tmpVars.add(v);
      return v;
    }
    def addAttrVariable(attr: String, v: IntVar): IntVar = {
      attrVars.put(attr, v);
      return v;
    }
  }

  private var id = 0;

  private val Search_Limit = 2000; //in ms

  private val solver: Solver = new Solver;

  private val attributeSubs = new HashMap[String, Set[String]];

  private val predFilter = new PredicateFilter;

  private val model = new ConstraintModel;

  private val refinedRanges = new HashMap[String, (Int, Int)];

  initialize(aggregate);

  def initialize(plan: Aggregate) {
    SearchMonitorFactory.limitTime(solver, Search_Limit);
    predFilter.initialize(plan);

    plan.aggregateExpressions.foreach(resolveAggregateAttribute(_, plan));
    model.commitConstraint;

    resolvePlan(plan);
  }

  //TODO luochen, add support for ignore refinement
  def get(expr: Expression, plan: LogicalPlan, refine: Boolean): (Int, Int) = {
    val attr = getAttributeString(expr);
    val result = refinedRanges.getOrElse(attr, null);
    if (result != null) {
      return result;
    }
    val attrVar = model.getVariable(attr);
    if (!refine || attrVar == null) {
      logWarning(s"no attribute refinement needed, return original range for $attr");
      //TODO add support for complex types;
      val label = plan.childLabel(expr);
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
      case agg: Aggregate => {
        //TODO

      }
      case project: Project => {
        //TODO
      }
      case binary: BinaryNode => {

      }
      case leaf: LeafNode => {
        //create initial variables

      }
      case _ =>
    }
  }

  private def resolveAggregateAttribute(expr: Expression, plan: Aggregate) {
    expr match {
      case attr if (isAttribute(attr)) => {
        resolveAttributeVar(attr, plan);
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
        model.getVariable(getAttributeString(attr));
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

  private def resolveAttributeVar(attr: Expression, plan: LogicalPlan): IntVar = {
    val str = getAttributeString(attr);
    return model.getVariable(str);
    //try to create variable, should only happen for complex types for top aggregate expressions
    /* val label = plan.childLabel(attr);
    val transformed = transformComplexLabel(label);
    val range = resolveAttributeRange(transformed);

    val created = VariableFactory.bounded(str, range._1, range._2, solver);
    model.addAttrVariable(str, created);
     */
  }

  //TODO: support complex types
  private def resolveAttributeRange(label: Label): (Int, Int) = {
    def rangeHelper(func: FunctionLabel, trans: ((Int, Int), (Int, Int)) => (Int, Int)): (Int, Int) = {
      val left = resolveAttributeRange(func.children(0));
      val right = resolveAttributeRange(func.children(0));
      trans(left, right);
    }

    label match {
      case column: ColumnLabel => {
        val info = infos.get(column.database, column.table, column.attr.name);
        (info.low, info.up);
      }
      case func: FunctionLabel => {
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
    nextId;
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