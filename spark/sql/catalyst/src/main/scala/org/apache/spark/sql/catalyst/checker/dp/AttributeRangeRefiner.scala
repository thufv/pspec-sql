package org.apache.spark.sql.catalyst.checker.dp

import scala.annotation.migration
import scala.collection.JavaConversions.asScalaSet
import scala.collection.mutable.HashMap
import scala.collection.mutable.HashSet
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Queue
import org.apache.spark.Logging
import org.apache.spark.sql.catalyst.checker.ColumnLabel
import org.apache.spark.sql.catalyst.checker.PrivacyException
import org.apache.spark.sql.catalyst.checker.util.DPUtil._
import org.apache.spark.sql.catalyst.checker.util.CheckerUtil._
import org.apache.spark.sql.catalyst.checker.util.TypeUtil._
import org.apache.spark.sql.catalyst.expressions._
import org.apache.spark.sql.catalyst.plans.logical._
import org.jgrapht.alg.ConnectivityInspector
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.DirectedPseudograph
import org.jgrapht.graph.Pseudograph
import solver.search.loop.monitors.SearchMonitorFactory
import org.apache.spark.sql.catalyst.checker.ColumnLabel
import org.apache.spark.sql.catalyst.checker.DataLabel
import org.apache.spark.sql.catalyst.checker.FunctionLabel
import org.apache.spark.sql.catalyst.checker.Label
import solver.constraints._
import solver.variables._
import org.apache.spark.sql.catalyst.checker.ConstantLabel
import org.apache.spark.sql.catalyst.checker.util.CheckerUtil._
import org.apache.spark.sql.catalyst.checker.util.TypeUtil._
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Queue
import scala.collection.mutable.Stack
import solver.Solver
import solver.search.strategy.IntStrategyFactory
import solver.ResolutionPolicy
import util.ESat
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Buffer

class AttributeRangeRefiner(val infos: TableInfo, val aggregate: Aggregate) extends Logging {

  private class PredicateFilter {
    private val relevantAttributes = new HashSet[String];

    private val relevantGraph = new Pseudograph[String, DefaultEdge](classOf[DefaultEdge]);

    private val deriveGraph = new Pseudograph[String, DefaultEdge](classOf[DefaultEdge]);

    private val aggAttributes = new HashSet[String];

    private val complexAttributes = new HashSet[String];

    def initialize(plan: Aggregate) {
      buildGraph(plan);
      val alg = new ConnectivityInspector[String, DefaultEdge](relevantGraph);

      aggAttributes.foreach(attr => {
        val set = alg.connectedSetOf(attr);
        relevantAttributes ++= set;
      });

      relevantAttributes.foreach(attr => {
        if (isComplexAttribute(attr)) {
          val pre = getComplexAttribute(attr);
          val set = attributeSubs.getOrElseUpdate(pre, new HashSet[String]);
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
          return effectiveAttribute(attr, plan);
        }
        case _ => {
          return pred.children.exists(effective(_, plan));
        }
      }

      return false;
    }

    private def effectiveAttribute(attribute: Expression, plan: LogicalPlan): Boolean = {
      val string = getAttributeString(attribute, plan);
      if (string == null) {
        return false;
      } else {
        return relevantAttributes.contains(string);
      }
    }

    private def buildGraph(plan: Aggregate) {
      plan.aggregateExpressions.foreach(resolveAggregateExpression(_, plan));

      plan.child.foreach(node => {
        node match {
          case filter: Filter => {
            resolveExpression(filter.condition, filter);
          }
          case join: Join => {
            join.condition match {
              case Some(cond) => resolveExpression(cond, join);
              case _ =>
            }
          }
          case agg: Aggregate => {
            for (i <- 0 to agg.output.length - 1) {
              connectOutput(agg.aggregateExpressions(i), agg.output(i).asInstanceOf[Attribute], agg);
            }
          }
          case project: Project => {
            for (i <- 0 to project.output.length - 1) {
              connectOutput(project.projectList(i), project.output(i).asInstanceOf[Attribute], project);
            }
          }
          case binary: BinaryNode => {
            //connect left and right attributes in the binary plan
            for (i <- 0 to binary.output.length - 1) {
              val left = binary.left.output(i);
              val right = binary.right.output(i);
              val attr = binary.output(i);
              connectOutput(left, attr, binary);
              connectOutput(right, attr, binary);
            }
          }
          case _ =>
        }
      });

      //post processing, create nodes and edges for complex types
      val alg = new ConnectivityInspector[String, DefaultEdge](deriveGraph);

      val queue = new Queue[String];
      complexAttributes.foreach(queue.enqueue(_));
      while (!queue.isEmpty) {
        val attr = queue.dequeue;
        val splits = splitComplexAttribute(attr);
        splits.foreach(t => {
          val pre = t._1;
          val subtypes = t._2;
          if (deriveGraph.containsVertex(pre)) {
            val equivalents = alg.connectedSetOf(pre);
            for (equi <- equivalents) {
              val equiAttr = concatComplexAttribute(equi, subtypes);
              if (!relevantGraph.containsVertex(equiAttr)) {
                queue.enqueue(equiAttr);
                relevantGraph.addVertex(equiAttr);
              }
              relevantGraph.addEdge(attr, equiAttr);
            }
          }
        });
      }
    }

    private def resolveAggregateExpression(expr: Expression, plan: LogicalPlan) {
      expr match {
        case cast: Cast => resolveAggregateExpression(cast.child, plan);
        case alias: Alias => resolveAggregateExpression(alias.child, plan);
        case agg: AggregateExpression => {
          if (agg.enableDP) {
            val attr = resolveSimpleAttribute(agg.children(0));
            val str = getAttributeString(attr, plan);
            if (str == null) {
              throw new PrivacyException(s"Aggregate on ${attr} is not allowed");
            }
            relevantGraph.addVertex(str);
            aggAttributes.add(str);
            if (isComplexAttribute(str)) {
              complexAttributes.add(str);
            }
          }
        }
        case _ =>
      }
    }

    private def resolveExpression(expr: Expression, plan: LogicalPlan) {
      expr match {
        case _: And | _: Or | _: Not => {
          expr.children.foreach(resolveExpression(_, plan));
        }
        case _: BinaryComparison | _: In | _: InSet => {
          val list = new ListBuffer[String];
          if (collectAttributes(expr, list, plan)) {
            connect(list: _*);
          }
        }
        case _ =>
      }
    }

    private def collectAttributes(expr: Expression, list: ListBuffer[String], plan: LogicalPlan): Boolean = {
      expr match {
        case a if (isAttribute(a)) => {
          val str = getAttributeString(a, plan);
          if (str != null) {
            list.append(str);
            return true;
          } else {
            return false;
          }
        }
        case cast: Cast => {
          collectAttributes(cast.child, list, plan);
        }
        case alias: Alias => {
          collectAttributes(alias.child, list, plan);
        }
        case e if (supportArithmetic(e) || supportPredicate(e)) => {
          e.children.forall(collectAttributes(_, list, plan));
        }

        case _: Literal => true;
        case _ => false;
      }
    }

    private def connectOutput(expr: Expression, output: Attribute, plan: LogicalPlan) {
      val list = new ListBuffer[String];
      list.append(getAttributeString(output, plan));
      if (collectAttributes(expr, list, plan)) {
        connect(list: _*);
      }

      if (output.dataType.isPrimitive) {
        return ;
      }
      val attr = resolveSimpleAttribute(expr);
      if (attr != null) {
        val str1 = getAttributeString(output, plan);
        val str2 = getAttributeString(attr, plan);
        deriveGraph.addVertex(str1);
        deriveGraph.addVertex(str2);
        deriveGraph.addEdge(str1, str2);
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
      array.distinct;
    }

    def getAllVaraibles(head: IntVar): Array[IntVar] = {
      if (allVars.isEmpty) {
        logWarning("no variable has been created for constraint solving");
        return allVars;
      }

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

    def postConstraint(cons: Constraint) = solver.post(cons);
  }

  private var nextId = 0;

  private val Search_Limit = 100 * 1000; //in ms

  private val solver: Solver = new Solver;

  private val attributeSubs = new HashMap[String, Set[String]];

  private val predFilter = new PredicateFilter;

  private val model = new ConstraintModel;

  private val refinedRanges = new HashMap[String, (Int, Int)];

  private val attributeRanges = new HashMap[Expression, (Int, Int)];

  initialize(aggregate);

  def ranges = attributeRanges;

  def get(expr: Expression, plan: LogicalPlan, refine: Boolean): (Int, Int) = {
    val attr = getAttributeString(expr, plan);
    val result = refinedRanges.getOrElse(attr, null);
    if (result != null) {
      return result;
    }
    val attrVar = model.getVariable(attr);
    val initialRange = (attrVar.getLB(), attrVar.getUB());
    if (!refine) {
      attributeRanges.put(expr, initialRange);
      refinedRanges.put(attr, initialRange);
      logWarning(s"no attribute refinement needed, return original range for $attr");
      return initialRange;
    }

    //constraint solving
    val allVars = model.getAllVaraibles(attrVar);

    logWarning(s"start solving upper bound for $attr");
    solver.set(IntStrategyFactory.lexico_UB(allVars: _*));
    solver.findOptimalSolution(ResolutionPolicy.MAXIMIZE, attrVar);
    if (solver.isFeasible() != ESat.TRUE) {
      logWarning(s"fail to find any solution, fall back to empty range for $attr"); ;
      val range = null;
      attributeRanges.put(expr, range);
      refinedRanges.put(attr, range);
      return range;
    }
    val up = if (solver.hasReachedLimit()) {
      logWarning(s"solving upper bound for $attr timeout, fall back to original ${initialRange._2}");
      initialRange._2;
    } else {
      attrVar.getUB();
    }
    solver.getSearchLoop.reset;
    // solver.getSearchLoop().getSMList().reset;

    logWarning(s"start solving lower bound for $attr");
    solver.set(IntStrategyFactory.lexico_LB(allVars: _*));
    solver.findOptimalSolution(ResolutionPolicy.MINIMIZE, attrVar);
    val low =
      if (solver.hasReachedLimit()) {
        logWarning(s"solving lower bound for $attr timeout, fall back to original ${initialRange._1}");
        initialRange._1;
      } else {
        attrVar.getLB();
      }
    solver.getSearchLoop.reset;
    //  solver.getSearchLoop().getSMList().reset;

    val range = (low, up);
    attributeRanges.put(expr, range);
    refinedRanges.put(attr, range);
    logWarning(s"update range for $attr as [$low, $up]");
    return range;
  }

  private def initialize(plan: Aggregate) {
    SearchMonitorFactory.limitTime(solver, Search_Limit);
    predFilter.initialize(plan);
    //  plan.aggregateExpressions.foreach(resolveAggregateAttribute(_, plan));
    //  model.commitConstraint;

    model.postConstraint(resolvePlan(plan.child));
  }

  private def resolvePlan(plan: LogicalPlan): Constraint = {
    //initialize
    plan match {
      case filter: Filter => {
        val childConstraint = resolvePlan(filter.child);
        val constraint = resolveExpression(filter.condition, plan);
        if (constraint != null) {
          model.commitConstraint;
          LogicalConstraintFactory.and(childConstraint, constraint);
        } else {
          childConstraint;
        }
      }
      case join: Join => {
        val leftConstraint = resolvePlan(join.left);
        val rightConstraint = resolvePlan(join.right);
        join.condition match {
          case Some(cond) => {
            val constraint = resolveExpression(cond, plan);
            if (constraint != null) {
              model.commitConstraint;
              LogicalConstraintFactory.and(leftConstraint, rightConstraint, constraint);
            } else {
              LogicalConstraintFactory.and(leftConstraint, rightConstraint);
            }
          }
          case _ => LogicalConstraintFactory.and(leftConstraint, rightConstraint);
        }
      }
      case agg: Aggregate => {
        val childConstraint = resolvePlan(agg.child);
        for (i <- 0 to agg.output.length - 1) {
          //these constraints hold globally, i.e., must be satisfied by the final valuation
          createUnaryVariable(agg.aggregateExpressions(i), agg.output(i), agg);
        }
        childConstraint;
      }
      case project: Project => {
        val childConstraint = resolvePlan(project.child);
        for (i <- 0 to plan.output.length - 1) {
          createUnaryVariable(project.projectList(i), project.output(i), project);
        }
        childConstraint;
      }
      case binary: BinaryNode => {
        val lefts = new ListBuffer[Constraint];
        val rights = new ListBuffer[Constraint];

        lefts.append(resolvePlan(binary.left));
        rights.append(resolvePlan(binary.right));
        for (i <- 0 to binary.output.length - 1) {
          val seq = createBinaryConstraint(binary.left.output(i), binary.right.output(i), binary.output(i), binary, lefts, rights);
        }
        val leftConstraint = LogicalConstraintFactory.and(lefts: _*);

        binary match {
          case union: Union => {
            val rightConstraint = LogicalConstraintFactory.and(rights: _*);
            LogicalConstraintFactory.or(leftConstraint, rightConstraint);
          }
          case intersect: Intersect => {
            val rightConstraint = LogicalConstraintFactory.and(rights: _*);
            LogicalConstraintFactory.and(leftConstraint, rightConstraint);
          }
          case except: Except => {
            val rightConstraint = LogicalConstraintFactory.and(LogicalConstraintFactory.not(rights.head),
              LogicalConstraintFactory.and(rights.tail: _*));
            LogicalConstraintFactory.and(leftConstraint, rightConstraint);
          }
        }
      }
      case leaf: LeafNode => {
        leaf.output.foreach(initializeVariable(_, leaf));
        solver.TRUE;

      }
      case _ => LogicalConstraintFactory.and(plan.children.map(resolvePlan(_)): _*);
    }
  }

  /**
   * create a variable for each stored attribute
   */
  private def initializeVariable(attr: Attribute, plan: LeafNode) {
    val attrStr = getAttributeString(attr, plan);
    val label = plan.projectLabels.getOrElse(attr, null).asInstanceOf[ColumnLabel];
    if (attr.dataType.isPrimitive) {
      if (!predFilter.effective(attr, plan)) {
        return ;
      }
      val range = infos.getByAttribute(label.database, label.table, attrStr);
      val attrVar = VariableFactory.bounded(attrStr, range.low, range.up, solver);
      model.addAttrVariable(attrStr, attrVar);
    } else {
      val subs = attributeSubs.getOrElse(attrStr, null);
      if (subs == null) {
        return ;
      }

      //create a variable for each subtype
      subs.foreach(sub => {
        val range = infos.getByAttribute(label.database, label.table, sub);
        val attrVar = VariableFactory.bounded(sub, range.low, range.up, solver);
        model.addAttrVariable(sub, attrVar);
      });
    }
  }

  private def createUnaryVariable(expr: Expression, output: Attribute, plan: UnaryNode) {
    val outStr = getAttributeString(output, plan);

    if (output.dataType.isPrimitive) {
      if (!predFilter.effective(output, plan)) {
        return ;
      }
      val mark = model.markConstraint;
      val attrVar = resolveTerm(expr, plan);
      if (attrVar != null) {
        model.addAttrVariable(outStr, attrVar);
        model.commitConstraint;
      } else {
        model.rollbackConstraint(mark);
      }
    } else {
      val types = attributeSubs.getOrElse(outStr, null);
      if (types == null) {
        return ;
      }
      //only direct access on complex types is supported
      val exprStr = getAttributeString(expr, plan);
      if (exprStr == null) {
        return ;
      }
      types.foreach(outType => {
        val subtypes = getComplexSubtypes(outType);
        val exprTypes = concatComplexAttribute(exprStr, subtypes);
        val exprVar = model.getVariable(exprTypes);
        if (exprVar != null) {
          model.addAttrVariable(outType, exprVar);
        }
      });

    }
  }

  /**
   * TODO luochen the semantics for binary operators should be reconsidered
   */
  private def createBinaryConstraint(left: Attribute, right: Attribute, output: Attribute, plan: BinaryNode, lefts: Buffer[Constraint], rights: Buffer[Constraint]) {
    def binaryConstraintHelper(leftStr: String, rightStr: String, outStr: String, rangeFunc: ((Int, Int), (Int, Int)) => (Int, Int)) {
      val leftVar = model.getVariable(leftStr);
      val rightVar = model.getVariable(rightStr);
      if (leftVar == null || rightVar == null) {
        return ;
      }
      val range = rangeFunc((leftVar.getLB, leftVar.getUB), (rightVar.getLB, rightVar.getUB));
      val outVar = VariableFactory.bounded(s"$outStr#${nextId}", range._1, range._2, solver);
      model.addAttrVariable(outStr, outVar);
      lefts.append(IntConstraintFactory.arithm(outVar, "=", leftVar));
      rights.append(IntConstraintFactory.arithm(outVar, "=", rightVar));

    }

    def binaryHelper(rangeFunc: ((Int, Int), (Int, Int)) => (Int, Int)) {
      val leftStr = getAttributeString(left, plan);
      val rightStr = getAttributeString(right, plan);
      val outStr = getAttributeString(output, plan);
      if (output.dataType.isPrimitive) {
        if (!predFilter.effective(left, plan)) {
          return ;
        }
        binaryConstraintHelper(leftStr, rightStr, outStr, rangeFunc);
      } else {
        val set = attributeSubs.getOrElse(outStr, null);
        if (set == null) {
          return ;
        }
        //create variable for each subtype
        val results = new ListBuffer[(Constraint, Constraint)];
        set.foreach(sub => {
          val types = getComplexSubtypes(sub);
          val leftSub = concatComplexAttribute(leftStr, types);
          val rightSub = concatComplexAttribute(rightStr, types);
          binaryConstraintHelper(leftSub, rightSub, outStr, rangeFunc);
        });
      }
    }

    plan match {
      case union: Union => {
        binaryHelper(rangeUnion(_, _));
      }
      case intersect: Intersect => {
        binaryHelper(rangeIntersect(_, _));
      }
      case except: Except => {
        binaryHelper(rangeExcept(_, _));
      }
    }

  }

  private def resolveExpression(cond: Expression, plan: LogicalPlan): Constraint = {

    cond match {
      case and: And => {
        val list = collect(and, classOf[And]).map(resolveExpression(_, plan)).filter(_ != null);
        list.size match {
          case 0 => null;
          case 1 => list(0);
          case _ => LogicalConstraintFactory.and(list: _*);
        }
      }
      case or: Or => {
        val mark = model.markConstraint;
        val list = collect(or, classOf[Or]).map(resolveExpression(_, plan));
        if (list.exists(_ == null)) {
          model.rollbackConstraint(mark);
          return null;
        } else {
          return LogicalConstraintFactory.or(list: _*);
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
    def binaryPredicateHelper(binary: BinaryComparison, consFunc: (IntVar, IntVar) => Constraint): Constraint = {
      try {
        val var1 = resolveTerm(binary.left, plan);
        val var2 = resolveTerm(binary.right, plan);
        if (var1 == null || var2 == null) {
          return null;
        }
        return consFunc(var1, var2);
      } catch {
        case e: Exception => {
          logWarning(s"${e.getMessage}");
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
          return LogicalConstraintFactory.or(constraints: _*);
        }
      }
      case inSet: InSet => {
        val left = resolveTerm(inSet.value, plan);
        if (left == null) {
          return null;
        } else {
          val constraints = inSet.hset.map(v => IntConstraintFactory.arithm(left, "=", VariableFactory.fixed(v, solver))).toArray;
          return LogicalConstraintFactory.or(constraints: _*);
        }
      }
      case _ => null
    }
  }

  private def resolveTerm(term: Expression, plan: LogicalPlan): IntVar = {
    term match {
      case attr if (isAttribute(attr)) => {
        resolveAttributeVar(attr, plan);
      }
      case alias: Alias => {
        resolveTerm(alias.child, plan);
      }
      case cast: Cast => {
        resolveTerm(cast.child, plan);
      }

      case literal: Literal => {
        val value = anyToInt(literal.value);
        if (value != null) {
          VariableFactory.fixed(value, solver);
        } else {
          null;
        }
      }
      case mutable: MutableLiteral => {
        val value = anyToInt(mutable.value);
        if (value != null) {
          VariableFactory.fixed(value, solver);
        } else {
          null;
        }
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
    val str = getAttributeString(attr, plan);
    return model.getVariable(str);
  }

  private def getId(): Int = {
    nextId += 1;
    nextId;
  }

  private def newTmpVar(min: Int, max: Int): IntVar = {
    VariableFactory.bounded(s"t_$getId", Math.max(min, VariableFactory.MIN_INT_BOUND), Math.min(max, VariableFactory.MAX_INT_BOUND), solver);
  }

  implicit private def anyToInt(any: Any): Int = {
    any match {
      case long: Long => long.toInt;
      case int: Int => int;
      case double: Double => double.toInt;
      case float: Float => float.toInt;
      case short: Short => short.toInt;
      case big: BigDecimal => big.toInt;
      case null => 0;
      case _ => null.asInstanceOf[Int];
    }
  }

}