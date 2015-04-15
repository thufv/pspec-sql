package org.apache.spark.sql.catalyst.checker.dp

import scala.collection.mutable.ListBuffer
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.HashMap
import scala.collection.JavaConversions._
import org.apache.spark.sql.catalyst.plans.logical._
import org.apache.spark.sql.catalyst.expressions.Expression
import scala.collection.mutable.HashSet
import org.jgrapht.graph.DirectedPseudograph
import org.jgrapht.graph.DefaultEdge
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.jgrapht.alg.ConnectivityInspector
import org.apache.spark.sql.catalyst.checker.LabelConstants._
import org.apache.spark.sql.catalyst.checker.util.TypeUtil._
import org.apache.spark.sql.catalyst.checker.util.CheckerUtil._
import org.apache.spark.sql.catalyst.plans.logical.Join
import org.apache.spark.sql.catalyst.expressions.Attribute
import org.apache.spark.sql.catalyst.plans.logical.Project
import scala.collection.mutable.Queue
import org.apache.spark.sql.catalyst.expressions._
import com.microsoft.z3.ArithExpr
import scala.collection.mutable.Stack
import com.microsoft.z3.BoolExpr
import solver.variables.IntVar
import com.microsoft.z3.Context
import org.apache.spark.sql.catalyst.checker.ColumnLabel
import org.apache.spark.sql.types.DataType
import org.apache.spark.sql.types.NumericType
import org.apache.spark.sql.catalyst.expressions.Predicate
import org.apache.spark.Logging
import scala.collection.mutable.Buffer
import com.microsoft.z3.RealExpr
import com.microsoft.z3.Expr
import com.microsoft.z3.IntExpr
import com.microsoft.z3.Status
import edu.thu.ss.spec.lang.pojo.DataCategory
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.NumericType
import org.apache.spark.sql.types.BooleanType
import scala.collection.mutable.HashMap
import org.apache.spark.sql.types.DataType
import com.microsoft.z3.ArithExpr
import org.apache.spark.sql.types.NumericType
import org.apache.spark.sql.types.DataType
import org.apache.spark.sql.types.NumericType
import com.microsoft.z3.ArithExpr
import org.apache.spark.sql.types.BooleanType
import com.microsoft.z3.ArithExpr
import org.apache.spark.sql.types.DataType
import org.apache.spark.sql.types.DataType
import org.apache.spark.sql.types.DataType
import org.jgrapht.graph.Pseudograph
import org.apache.spark.sql.catalyst.checker.Label
import org.apache.spark.sql.catalyst.checker.FunctionLabel
import org.apache.spark.sql.catalyst.structure.RedBlackBST
import org.apache.spark.sql.catalyst.structure.RedBlackBST.Node

object QueryTracker {

  def newQueryTracker(budget: DPBudgetManager): DPQueryTracker[_] = {
    budget match {
      case fine: FineBudgetManager => new DPQueryTracker[FinePartition](fine, new FinePartition(_, budget));
      case global: GlobalBudgetManager => new DPQueryTracker[GlobalPartition](global, new GlobalPartition(_, budget));
    }
  }

}

abstract class QueryTracker(val budget: DPBudgetManager) {
  protected val tmpQueries = new ArrayBuffer[DPQuery];

  def track(plan: Aggregate, ranges: Map[Expression, (Int, Int)]);

  def commit(failed: Set[Int]);

  def testBudget() {
    val copy = budget.copy;
    tmpQueries.foreach(copy.consume(_));
  }

  protected def collectDPQueries(plan: Aggregate, constraint: BoolExpr, ranges: Map[String, Interval]) {
    //decompose the aggregate query
    plan.aggregateExpressions.foreach(agg => agg.foreach(expr => {
      expr match {
        case a: AggregateExpression if (a.enableDP && a.sensitivity > 0) => tmpQueries.append(new DPQuery(constraint, a, plan, ranges));
        case _ =>
      }
    }));
  }

}

object DPQueryTracker {

  private val constants = new HashMap[String, Int];

  private var constantId = 0;

  def getConstant(string: String): Int = {
    val result = constants.get(string);
    result match {
      case Some(i) => i;
      case None => {
        val id = constantId;
        constants.put(string, id);
        constantId += 1;
        return id;
      }
    }
  }
}

/**
 * tracking submitted dp-enabled queries for parallel composition theorem
 */
class DPQueryTracker[T <: DPPartition] private[dp] (budget: DPBudgetManager, partitionBuilder: (Context) => T) extends QueryTracker(budget) with Logging {

  private class TypeResolver {

    private val deriveGraph = new Pseudograph[String, DefaultEdge](classOf[DefaultEdge]);

    private val complexAttributes = new HashMap[String, DataType];

    private val attributeSubs = new HashMap[String, Map[String, DataType]];

    private val supportedTypes = Seq(classOf[NumericType], classOf[StringType], classOf[BooleanType]);

    def get(attr: String): Map[String, DataType] = attributeSubs.getOrElse(attr, null);

    def initialize(plan: Aggregate) {
      buildGraph(plan);

      complexAttributes.withFilter(t => effective(t._2)).foreach(t => {
        val attr = t._1;
        val pre = getComplexAttribute(attr);
        val map = attributeSubs.getOrElseUpdate(pre, new HashMap[String, DataType]);
        map.put(attr, t._2);
      });
    }

    def effective(dataType: DataType): Boolean = {
      return supportedTypes.exists(_.isInstance(dataType));
    }

    def effective(attr: Attribute): Boolean = effective(attr.dataType);

    private def buildGraph(plan: Aggregate) {
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
              // connectOutput(left, attr, binary);
              // connectOutput(right, attr, binary);
            }
          }
          case _ =>
        }
      });

      //post processing, create nodes and edges for complex types
      val alg = new ConnectivityInspector[String, DefaultEdge](deriveGraph);

      val queue = new Queue[String];
      complexAttributes.foreach(t => queue.enqueue(t._1));
      while (!queue.isEmpty) {
        val attr = queue.dequeue;
        val dataType = complexAttributes.getOrElse(attr, null);
        assert(dataType != null);
        val splits = splitComplexAttribute(attr);
        splits.foreach(t => {
          val pre = t._1;
          val subtypes = t._2;
          if (deriveGraph.containsVertex(pre)) {
            val equivalents = alg.connectedSetOf(pre);
            for (equi <- equivalents) {
              val equiAttr = concatComplexAttribute(equi, subtypes);
              if (!complexAttributes.contains(equiAttr)) {
                queue.enqueue(equiAttr);
                complexAttributes.put(equiAttr, dataType);
              }
            }
          }
        });

      }
    }

    private def resolveExpression(expr: Expression, plan: LogicalPlan) {
      expr match {
        case _: And | _: Or | _: Not => {
          expr.children.foreach(resolveExpression(_, plan));
        }
        case _: BinaryComparison | _: In | _: InSet => {
          resolveAttributes(expr, plan);
        }
        case _ =>
      }
    }

    private def resolveAttributes(expr: Expression, plan: LogicalPlan) {
      expr match {
        case a if (isAttribute(a)) => {
          val str = getAttributeString(a, plan);
          if (str != null && isComplexAttribute(str)) {
            complexAttributes.put(str, a.dataType);
          }
        }
        case cast: Cast => {
          resolveAttributes(cast.child, plan);
        }
        case alias: Alias => {
          resolveAttributes(alias.child, plan);
        }
        case e if (supportArithmetic(e) || supportPredicate(e)) => {
          e.children.foreach(resolveAttributes(_, plan));
        }
        case _ =>
      }
    }

    private def connectOutput(expr: Expression, output: Attribute, plan: LogicalPlan) {
      resolveAttributes(expr, plan);
      if (!output.dataType.isPrimitive) {
        val outStr = getAttributeString(output, plan);
        complexAttributes.put(outStr, output.dataType);
        val attr = resolveSimpleAttribute(expr);
        if (attr != null) {
          val attrStr = getAttributeString(attr, plan);
          deriveGraph.addVertex(outStr);
          deriveGraph.addVertex(attrStr);
          deriveGraph.addEdge(outStr, attrStr);
        }
      }
    }

  }

  private class SMTModel {
    private val attrVars = new HashMap[String, Expr];

    val attrIndex = new HashMap[Expr, String];

    /**
     * key: table.column, value: a list of attributes
     */
    val columnVars = new HashMap[String, Buffer[String]];

    def getVariable(attr: String): Expr = {
      return attrVars.getOrElse(attr, null);
    }
    def initAttrVariable(attr: String, table: String, variable: Expr) {
      attrVars.put(attr, variable);

      val column = s"$table.${getColumnString(attr)}";
      val buffer = columnVars.getOrElseUpdate(column, new ArrayBuffer[String]);
      buffer.append(attr);
      attrIndex.put(variable, column);

    }

    def addAttrVariable(attr: String, variable: Expr) {
      attrVars.put(attr, variable);
      val column = getColumnString(attr);
    }

  }

  /**
   * Transform a query (plan) into a SMT equation, which represents the column ranges
   */
  private class SMTBuilder {
    val model = new SMTModel;

    private val typeResolver = new TypeResolver;
    private val defaultSort = context.getRealSort();

    private var id = 0;

    def buildSMT(plan: Aggregate): BoolExpr = {
      typeResolver.initialize(plan);

      val expression = resolvePlan(plan.child);

      val activated = new HashSet[String];

      resolveActiveColumns(expression, activated);
      val replaces = activated.map(column => {
        val columnVar = context.mkConst(column, defaultSort);
        val attributes = model.columnVars.getOrElse(column, null);
        val list = attributes.map(attr => {
          val variable = model.getVariable(attr);
          context.mkEq(columnVar, variable);
        });
        disjuncate(list);
      }).toList;

      return conjuncate(expression :: replaces).simplify().asInstanceOf[BoolExpr];
    }

    private def resolveActiveColumns(expression: Expr, set: HashSet[String]) {
      expression match {
        case int: IntExpr => {
          val column = model.attrIndex.get(int);
          if (column.isDefined) {
            set.add(column.get);
          }
        }
        case real: RealExpr => {
          val column = model.attrIndex.get(real);
          if (column.isDefined) {
            set.add(column.get);
          }
        }
        case _ => expression.getArgs().foreach(resolveActiveColumns(_, set));
      }

    }

    private def resolvePlan(plan: LogicalPlan): BoolExpr = {
      //initialize
      plan match {
        case filter: Filter => {
          val childConstraint = resolvePlan(filter.child);
          val constraint = resolveExpression(filter.condition, plan);
          if (constraint != null) {
            context.mkAnd(childConstraint, constraint);

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
                context.mkAnd(leftConstraint, rightConstraint, constraint);
              } else {
                context.mkAnd(leftConstraint, rightConstraint);
              }
            }
            case _ => context.mkAnd(leftConstraint, rightConstraint);
          }
        }
        case agg: Aggregate => {
          val childConstraint = resolvePlan(agg.child);
          for (i <- 0 to agg.output.length - 1) {
            //these constraints hold globally, i.e., must be satisfied by the final valuation
            createUnaryVariable(agg.aggregateExpressions(i), agg.output(i), agg);
          }
          return childConstraint;
        }
        case project: Project => {
          val childConstraint = resolvePlan(project.child);
          for (i <- 0 to plan.output.length - 1) {
            createUnaryVariable(project.projectList(i), project.output(i), project);
          }
          return childConstraint;
        }
        case binary: BinaryNode => {
          val leftConstraint = resolvePlan(binary.left);

          val rights = new ArrayBuffer[BoolExpr];
          rights.append(resolvePlan(binary.right));

          for (i <- 0 to binary.output.length - 1) {
            createBinaryConstraint(binary.left.output(i), binary.right.output(i), binary, rights);
          }
          binary match {
            case union: Union => {
              val rightConstraint = conjuncate(rights);
              context.mkOr(leftConstraint, rightConstraint);
            }
            case intersect: Intersect => {
              rights.prepend(leftConstraint);
              conjuncate(rights);
            }
            case except: Except => {
              val rightConstraint = context.mkNot(rights.remove(0));
              rights.prepend(leftConstraint, rightConstraint);
              conjuncate(rights);
            }
          }
        }
        case leaf: LeafNode => {
          leaf.output.foreach(initializeVariable(_, leaf));
          context.mkTrue();
        }
        case _ => {
          conjuncate(plan.children.map(resolvePlan(_)));
        }
      }
    }

    /**
     * create a variable for each stored attribute
     */
    private def initializeVariable(attr: Attribute, plan: LeafNode) {
      val attrStr = getAttributeString(attr, plan);
      val label = plan.projectLabels.getOrElse(attr, null).asInstanceOf[ColumnLabel];
      if (attr.dataType.isPrimitive) {
        if (!typeResolver.effective(attr)) {
          return ;
        }
        val attrVar = createVariable(attrStr, attr.dataType);
        model.initAttrVariable(attrStr, label.table, attrVar);
      } else {
        val subs = typeResolver.get(attrStr);
        if (subs == null) {
          return ;
        }
        //create a variable for each subtype
        subs.foreach(sub => {
          val attrVar = createVariable(sub._1, sub._2);
          model.initAttrVariable(sub._1, label.table, attrVar);
        });
      }
    }

    private def createUnaryVariable(expr: Expression, output: Attribute, plan: UnaryNode) {
      val outStr = getAttributeString(output, plan);

      if (output.dataType.isPrimitive) {
        val attrVar = resolveTerm(expr, plan);
        if (attrVar != null) {
          model.addAttrVariable(outStr, attrVar);
        }
      } else {
        val types = typeResolver.get(outStr);
        if (types == null) {
          return ;
        }
        //only direct access on complex types is supported
        val exprStr = getAttributeString(expr, plan);
        if (exprStr == null) {
          return ;
        }
        types.keys.foreach(outType => {
          val subtypes = getComplexSubtypes(outType);
          val exprTypes = concatComplexAttribute(exprStr, subtypes);
          val exprVar = model.getVariable(exprTypes);
          if (exprVar != null) {
            model.addAttrVariable(outType, exprVar);
          }
        });
      }
    }

    private def createBinaryConstraint(left: Attribute, right: Attribute, plan: BinaryNode, list: ArrayBuffer[BoolExpr]) {
      def binaryConstraintHelper(leftStr: String, rightStr: String): BoolExpr = {
        val leftVar = model.getVariable(leftStr);
        val rightVar = model.getVariable(rightStr);
        if (leftVar == null || rightVar == null) {
          return null;
        }
        context.mkEq(leftVar, rightVar);
      }

      val leftStr = getAttributeString(left, plan);
      val rightStr = getAttributeString(right, plan);
      if (left.dataType.isPrimitive) {
        val result = binaryConstraintHelper(leftStr, rightStr);
        if (result != null) {
          list.append(result);
        }
      } else {
        val set = typeResolver.get(leftStr);
        if (set == null) {
          return ;
        }
        //create variable for each subtype
        set.keys.foreach(sub => {
          val types = getComplexSubtypes(sub);
          val rightSub = concatComplexAttribute(rightStr, types);
          val result = binaryConstraintHelper(sub, rightSub);
          if (result != null) {
            list.append(result);
          }
        });
      }

    }

    def resolveExpression(cond: Expression, plan: LogicalPlan): BoolExpr = {
      cond match {
        case and: And => {
          val list = collect(and, classOf[And]).map(resolveExpression(_, plan)).filter(_ != null);
          conjuncate(list);
        }
        case or: Or => {
          val list = collect(or, classOf[Or]).map(resolveExpression(_, plan));
          if (list.exists(_ == null)) {
            return null;
          } else {
            return disjuncate(list);
          }
        }
        case n: Not => {
          val child = resolveExpression(n.child, plan);
          return not(child);
        }

        case pred: Predicate => {
          resolvePredicate(pred, plan);
        }

        case _ => null;
      }
    }

    private def resolvePredicate(pred: Predicate, plan: LogicalPlan): BoolExpr = {
      def binaryPredicateHelper(binary: BinaryComparison, consFunc: (Expr, Expr) => BoolExpr): BoolExpr = {
        try {
          val var1 = resolveTerm(binary.left, plan);
          if (var1 == null) {
            return null;
          }
          val var2 = resolveTerm(binary.right, plan);
          if (var2 == null) {
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
      pred match {
        case equal: EqualTo => {
          binaryPredicateHelper(equal, context.mkEq(_, _));
        }
        case equal: EqualNullSafe => {
          binaryPredicateHelper(equal, context.mkEq(_, _));
        }
        case lt: LessThan => {
          binaryPredicateHelper(lt, (l, r) => context.mkLt(l.asInstanceOf[ArithExpr], r.asInstanceOf[ArithExpr]));
        }
        case lte: LessThanOrEqual => {
          binaryPredicateHelper(lte, (l, r) => context.mkLe(l.asInstanceOf[ArithExpr], r.asInstanceOf[ArithExpr]));
        }
        case gt: GreaterThan => {
          binaryPredicateHelper(gt, (l, r) => context.mkGt(l.asInstanceOf[ArithExpr], r.asInstanceOf[ArithExpr]));
        }
        case gte: GreaterThanOrEqual => {
          binaryPredicateHelper(gte, (l, r) => context.mkGe(l.asInstanceOf[ArithExpr], r.asInstanceOf[ArithExpr]));
        }
        case in: In => {
          val left = resolveTerm(in.value, plan);
          val rights = in.list.map(resolveTerm(_, plan));
          if (left == null || rights.exists(_ == null)) {
            return null;
          } else {
            val constraints = rights.map(right => context.mkEq(left, right));
            return disjuncate(constraints);
          }
        }
        case inSet: InSet => {
          val left = resolveTerm(inSet.value, plan);
          if (left == null) {
            return null;
          } else {
            val constraints = inSet.hset.map(v => context.mkEq(left, context.mkReal(toReal(v)))).toSeq;
            return disjuncate(constraints);
          }
        }
        case _ => null
      }
    }

    private def resolveTerm(term: Expression, plan: LogicalPlan): Expr = {

      term match {
        //add support for complex data types
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
          createConstant(literal.value, literal.dataType);
        }
        case mutable: MutableLiteral => {
          createConstant(mutable.value, mutable.dataType);
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

    private def resolveBinaryArithmetic(binary: BinaryArithmetic, plan: LogicalPlan): ArithExpr = {

      def binaryHelper(transFunc: (ArithExpr, ArithExpr) => ArithExpr): ArithExpr = {
        val left = resolveTerm(binary.left, plan).asInstanceOf[ArithExpr];
        if (left == null) {
          return null;
        }
        val right = resolveTerm(binary.right, plan).asInstanceOf[ArithExpr];
        if (right == null) {
          return null;
        } else {
          return transFunc(left, right);
        }
      }

      def binaryMultiHealper(transFunc: (Seq[ArithExpr]) => ArithExpr): ArithExpr = {
        val list = collect(binary, binary.getClass()).map(resolveTerm(_, plan).asInstanceOf[ArithExpr]);
        if (list.exists(_ == null)) {
          return null;
        }
        return transFunc(list);
      }
      binary match {
        case add: Add => {
          binaryMultiHealper(context.mkAdd(_: _*));
        }
        case subtract: Subtract => {
          binaryMultiHealper(context.mkSub(_: _*));
        }
        case multiply: Multiply => {
          binaryMultiHealper(context.mkMul(_: _*));
        }
        case divide: Divide => {
          binaryHelper(context.mkDiv(_, _));
        }
        case remainder: Remainder => {
          null;
          // binaryHelper(context.mkMod(_, _));
        }
        case _ => null;
      }
    }

    private def resolveUnaryArithmetic(unary: UnaryExpression, plan: LogicalPlan): ArithExpr = {

      def unaryArithmHelper(transFunc: (ArithExpr) => ArithExpr): ArithExpr = {
        val child = resolveTerm(unary.child, plan).asInstanceOf[ArithExpr];
        if (child == null) {
          return null;
        } else {
          return transFunc(child);
        }
      }

      unary match {
        case minus: UnaryMinus => {
          unaryArithmHelper(context.mkUnaryMinus(_));
        }
        case abs: Abs => {
          //unaryArithmHelper(context.mk);
          null;
        }
        case _ => null;

      }
    }

    private def resolveAttributeVar(attr: Expression, plan: LogicalPlan): Expr = {
      val str = getAttributeString(attr, plan);
      return model.getVariable(str);
    }

    private def toReal(v: Any): String = {
      val string = v.toString;
      try {
        val result = string.toDouble;
        return string;
      } catch {
        case t: NumberFormatException => return null;
      }
    }

    def createConstant(value: Any, dataType: DataType): Expr = {
      dataType match {
        case n: NumericType => context.mkReal(toReal(value));
        case s: StringType => context.mkInt(DPQueryTracker.getConstant(value.asInstanceOf[String]));
        case b: BooleanType => context.mkBool(value.asInstanceOf[Boolean]);
        case _ => null;
      }
    }

    def createVariable(name: String, dataType: DataType) = {
      dataType match {
        case n: NumericType => context.mkConst(name, context.mkRealSort());
        case s: StringType => context.mkConst(name, context.mkIntSort());
        case b: BooleanType => context.mkConst(name, context.mkBoolSort());
        case _ => null;
      }

    }

    private def conjuncate(exprs: Seq[BoolExpr]): BoolExpr = {
      exprs.length match {
        case 0 => null;
        case 1 => exprs(0);
        case _ => context.mkAnd(exprs: _*);
      }
    }

    private def disjuncate(exprs: Seq[BoolExpr]): BoolExpr = {
      exprs.length match {
        case 0 => null;
        case 1 => exprs(0);
        case _ => context.mkOr(exprs: _*);
      }
    }

    private def not(expr: BoolExpr): BoolExpr = {
      if (expr == null) {
        return null;
      } else {
        context.mkNot(expr);
      }
    }

    private def nextId(): Int = {
      id += 1;
      id;
    }
  }

  private case class IntWrapper(val value: Int) extends Comparable[IntWrapper] {
    def compareTo(other: IntWrapper): Int = {
      return Integer.compare(this.value, other.value);
    }
  }

  private class PartitionIndex(val column: String) {
    val startTree = new RedBlackBST[IntWrapper, Set[T]];
    val endTree = new RedBlackBST[IntWrapper, Set[T]];

    def addPartition(partition: T) {
      val intervals = partition.ranges.getOrElse(column, null);
      if (intervals != null) {
        intervals.foreach(addPartition(_, partition));
      }
    }

    def addPartition(interval: Interval, partition: T) {
      def add(point: Int, partition: T, tree: RedBlackBST[IntWrapper, Set[T]]) {
        val set = tree.get(IntWrapper(point));
        if (set != null) {
          set.add(partition);
        } else {
          val set = new HashSet[T];
          tree.put(IntWrapper(point), set);
        }
      }

      add(interval.start, partition, startTree);
      add(interval.end, partition, endTree);
    }

    def removePartition(partition: T) {
      val intervals = partition.ranges.getOrElse(column, null);
      intervals.foreach(removePartition(_, partition));
    }

    def removePartition(interval: Interval, partition: T) {
      def remove(point: Int, partition: T, tree: RedBlackBST[IntWrapper, Set[T]]) {
        val set = tree.get(IntWrapper(point));
        assert(set != null);
        set.remove(partition);
        if (set == null) {
          tree.delete(IntWrapper(point));
        }
      }
      remove(interval.start, partition, startTree);
      remove(interval.end, partition, endTree);
    }

    def lookupDisjoint(interval: Interval): T = {
      val start = interval.start;
      val end = interval.end;

      val left = lookupByStart(end, interval, startTree.root);
      if (left != null) {
        return left;
      }
      return lookupByEnd(start, interval, endTree.root);
    }

    /**
     * lookup by the start tree, only consider nodes > point
     */
    private def lookupByStart(point: Int, interval: Interval, node: Node[IntWrapper, Set[T]]): T = {
      if (node == null) {
        return null.asInstanceOf[T];
      }
      val start = node.key;
      if (start.value > point) {
        val current = checkPartitions(interval, node.value);
        if (current != null) {
          return current;
        }
        val t = lookupByStart(point, interval, node.left);
        if (t != null) {
          return t;
        } else {
          return lookupByStart(point, interval, node.right);
        }
      } else {
        return lookupByStart(point, interval, node.right);
      }
    }

    private def lookupByEnd(point: Int, interval: Interval, node: Node[IntWrapper, Set[T]]): T = {
      if (node == null) {
        return null.asInstanceOf[T];
      }
      val end = node.key;
      if (end.value < point) {
        val current = checkPartitions(interval, node.value);
        if (current != null) {
          return current;
        }
        val t = lookupByEnd(point, interval, node.right);
        if (t != null) {
          return t;
        } else {
          return lookupByEnd(point, interval, node.left);
        }
      } else {
        return lookupByEnd(point, interval, node.left);
      }
    }

    private def checkPartitions(interval: Interval, partitions: Set[T]): T = {
      partitions.foreach(p => {
        if (p.disjoint(column, interval)) {
          return p;
        }
      });
      return null.asInstanceOf[T];
    }

  }

  private val context = new Context;

  private val partitions = new ArrayBuffer[T];

  private val partitionIndex = new HashMap[String, PartitionIndex];

  def track(plan: Aggregate, ranges: Map[Expression, (Int, Int)]) {
    val builder = new SMTBuilder;
    val constraint = builder.buildSMT(plan);
    //check satisfiability
    if (!satisfiable(constraint, context)) {
      logWarning("Range constraint unsatisfiable, ignore query");
      return ;
    }
    //check soundness of the estimated intervals
    val checkedRanges = new HashMap[String, Interval];
    ranges.foreach(t => {
      val attr = t._1;
      val range = t._2;
      //ensure no join table appear for each attribute
      val left = new HashSet[String];
      val right = new HashSet[String];
      val label = plan.childLabel(attr);
      collectAttributes(transformComplexLabel(label), left, right, plan, builder.model);
      if (left.size >= right.size) {
        //sound
        val column = getColumnString(getAttributeString(attr, plan));
        checkedRanges.put(column, Interval(range._1, range._2));
      }
    });

    collectDPQueries(plan, constraint, checkedRanges);
  }

  private def collectAttributes(label: Label, left: Set[String], right: Set[String], plan: LogicalPlan, model: SMTModel) {
    def addAttribute(attr: Expression, table: String) {
      val str = getAttributeString(attr, plan);
      left.add(str);
      val column = getColumnString(str);
      val set = model.columnVars.getOrElse(column, null);
      if (set != null) {
        right ++= set;
      }
    }

    label match {
      case column: ColumnLabel => {
        addAttribute(column.attr, column.table);
      }
      case func: FunctionLabel => {
        func.transform match {
          case Func_Union | Func_Except | Func_Intersect => func.children.foreach(collectAttributes(_, left, right, plan, model));
          case get if (isSubtypeOperation(get)) => {
            val attr = getAttributeString(func.expression, plan);
            addAttribute(func.expression, func.getTables().head);
          }
        }
      }
      case _ =>

    }
  }

  def commit(failed: Set[Int]) {
    //put queries into partitions
    var success = true;
    var pi = partitions.length - 1;
    tmpQueries.withFilter(q => !failed.contains(q.dpId)).foreach(query => {
      if (success) {
        success = commitQueryByIndex(query);
      }
      if (!success) {
        var found = false;
        while (!found && pi >= 0) {
          val partition = partitions(pi);
          if (partition.disjoint(query)) {
            found = true;
            updatePartition(partition, query);
          }
          pi -= 1;
        }
        if (!found) {
          createPartition(query);
        }
      }
    });

    tmpQueries.foreach(_.clear);
    tmpQueries.clear;
    budget.show;
  }

  private def createPartition(query: DPQuery) {
    val partition = partitionBuilder(context);
    partition.init(query);

    partition.ranges.foreach(t => {
      val column = t._1;
      val index = partitionIndex.getOrElseUpdate(column, new PartitionIndex(column));
      index.addPartition(partition);
    });

    this.partitions.append(partition);
    logWarning("fail to locate a disjoint partition, create a new one");
  }

  private def updatePartition(partition: T, query: DPQuery) {
    partition.add(query);
    //
    val toRemove = new ArrayBuffer[String];
    partition.ranges.foreach(t => {
      val column = t._1;
      if (!query.ranges.contains(t._1)) {
        toRemove.append(column);
        val index = partitionIndex.getOrElse(column, null);
        //the column range is no longer invalid, remove all intervals
        if (index != null) {
          index.removePartition(partition);
        }
      }
    });
    toRemove.foreach(partition.ranges.remove(_));

    partition.ranges.foreach(t => {
      val column = t._1;
      val list = t._2;
      val interval = query.ranges.getOrElse(column, null);
      assert(interval != null);
      //union the list and interval
      val index = partitionIndex.getOrElse(column, null);

      val firstIndex = list.indexWhere(_.joint(interval));
      val lastIndex = list.lastIndexWhere(_.joint(interval));

      if (firstIndex >= 0) {
        //union takes effective
        val start = Math.min(interval.start, list(firstIndex).start);
        val end = Math.max(interval.end, list(lastIndex).end);
        val newInterval = Interval(start, end);
        for (i <- firstIndex to lastIndex) {
          index.removePartition(list(i), partition);
        }
        list.remove(firstIndex, lastIndex - firstIndex + 1);

        list.insert(firstIndex, newInterval);
        index.addPartition(newInterval, partition);
      } else {
        val i = list.indexWhere(_.start > interval.start);
        if (i >= 0) {
          list.insert(i, interval);
        } else {
          list.append(interval);
        }
        index.addPartition(interval, partition);

      }
    });

  }

  /**
   * lookup the index to commit the query
   */
  private def commitQueryByIndex(query: DPQuery): Boolean = {
    val ranges = query.ranges;
    ranges.foreach(t => {
      val column = t._1;
      val interval = t._2;
      val index = partitionIndex.getOrElse(column, null);
      if (index != null) {
        val partition = index.lookupDisjoint(interval);
        if (partition != null) {
          logWarning("find disjoint partition with index, no SMT solving needed");

          updatePartition(partition, query);
          return true;
        }
      }
    });

    return false;
  }
}

class DummyQueryTracker(budget: DPBudgetManager) extends QueryTracker(budget) {

  def track(plan: Aggregate, ranges: Map[Expression, (Int, Int)]) {
    collectDPQueries(plan, null, null);
  }

  def commit(failed: Set[Int]) {
    tmpQueries.foreach(budget.consume(_));
    tmpQueries.clear;
    budget.show;
  }
}