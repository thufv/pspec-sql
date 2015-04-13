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

object DPQuery {
  private var nextId = 0;

  def getId(): Int = {
    nextId += 1;
    nextId;
  }
}

class DPQuery(val range: BoolExpr, val aggregate: AggregateExpression, val plan: Aggregate) extends Equals {
  val dpId = DPQuery.getId;

  aggregate.dpId = dpId;

  def canEqual(other: Any) = {
    other.isInstanceOf[org.apache.spark.sql.catalyst.checker.dp.DPQuery]
  }

  override def equals(other: Any) = {
    other match {
      case that: org.apache.spark.sql.catalyst.checker.dp.DPQuery => that.canEqual(DPQuery.this) && dpId == that.dpId;
      case _ => false
    }
  }

  override def hashCode() = {
    val prime = 41
    prime + dpId.hashCode;
  }
}

abstract class DPPartition(val context: Context, val budget: DPBudgetManager) {
  private val queries = new ArrayBuffer[DPQuery];

  private var ranges: BoolExpr = context.mkFalse();

  def add(query: DPQuery) {
    queries.append(query);
    ranges = context.mkOr(ranges, query.range);
    updateBudget(query);
  }

  def disjoint(query: DPQuery): Boolean = {
    val cond = context.mkAnd(ranges, query.range);
    return !satisfiable(cond, context);
  }

  def updateBudget(query: DPQuery);

}

private class GlobalPartition(context: Context, budget: DPBudgetManager) extends DPPartition(context, budget) {

  private var maximum = 0.0;

  def updateBudget(query: DPQuery) {
    if (query.aggregate.epsilon > maximum) {
      budget.consume(null, query.aggregate.epsilon - maximum);
      maximum = query.aggregate.epsilon;
    }
  }
}

private class FinePartition(context: Context, budget: DPBudgetManager) extends DPPartition(context, budget) {
  private val maximum = new HashMap[DataCategory, Double];

  def updateBudget(query: DPQuery) {
    val epsilon = query.aggregate.epsilon;

    val set = new HashSet[DataCategory];
    val attr = resolveSimpleAttribute(query.aggregate.children(0));
    set ++= query.plan.childLabel(attr).getDatas;
    query.plan.condLabels.foreach(set ++= _.getDatas);

    set.foreach(data => {
      val consumed = maximum.getOrElse(data, 0.0);
      if (consumed < epsilon) {
        budget.consume(data, epsilon - consumed);
        maximum.put(data, epsilon);
      }
    });

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

  def newQueryTracker(budget: DPBudgetManager): DPQueryTracker[_] = {
    budget match {
      case fine: FineBudgetManager => new DPQueryTracker[FinePartition](fine, new FinePartition(_, budget));
      case global: GlobalBudgetManager => new DPQueryTracker[GlobalPartition](global, new GlobalPartition(_, budget));
    }
  }

}

/**
 * tracking submitted dp-enabled queries for parallel composition theorem
 */
class DPQueryTracker[T <: DPPartition] private (val budget: DPBudgetManager, partitionBuilder: (Context) => T) extends Logging {

  private class TypeResolver {

    private val deriveGraph = new DirectedPseudograph[String, DefaultEdge](classOf[DefaultEdge]);

    private val complexAttributes = new HashMap[String, DataType];

    private val attributeSubs = new HashMap[String, Map[String, DataType]];

    private val supportedTypes = Seq(classOf[NumericType], classOf[StringType], classOf[BooleanType]);

    def get(attr: String): Map[String, DataType] = attributeSubs.getOrElse(attr, null);

    def initialize(plan: Aggregate) {
      buildGraph(plan);

      complexAttributes.foreach(t => {
        val attr = t._1;
        val pre = getComplexAttribute(attr);
        val map = attributeSubs.getOrElseUpdate(pre, new HashMap[String, DataType]);
        map.put(attr, t._2);
      });
    }

    def effective(attr: Attribute): Boolean = {
      return supportedTypes.exists(_.isInstance(attr.dataType));
    }

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
      complexAttributes.foreach(t => queue.enqueue(t._1));
      while (!queue.isEmpty) {
        val attr = queue.dequeue;
        val dataType = complexAttributes.getOrElse(attr, null);
        assert(dataType != null);
        val pre = getComplexAttribute(attr);
        val subtypes = getComplexSubtypes(attr);
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
        val attr = resolveSimpleAttribute(expr);
        if (attr != null) {
          val str1 = getAttributeString(output, plan);
          val str2 = getAttributeString(attr, plan);
          deriveGraph.addVertex(str1);
          deriveGraph.addVertex(str2);
          deriveGraph.addEdge(str1, str2);
        }
      }
    }

  }

  private class SMTModel {
    private val attrVars = new HashMap[String, Expr];

    val varIndex = new HashMap[Expr, String];

    /**
     * key: table.column, value: a set of variables
     */
    val columnVars = new HashMap[String, Buffer[Expr]];

    def getVariable(attr: String): Expr = {
      return attrVars.getOrElse(attr, null);
    }
    def initAttrVariable(attr: String, table: String, variable: Expr) {
      attrVars.put(attr, variable);
      val column = s"$table.${getColumnString(attr)}";

      val buffer = columnVars.getOrElseUpdate(column, new ArrayBuffer[Expr]);
      buffer.append(variable);

      varIndex.put(variable, column);
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
    private val typeResolver = new TypeResolver;
    private val model = new SMTModel;
    private val defaultSort = context.getRealSort();

    private var id = 0;

    def buildSMT(plan: Aggregate): BoolExpr = {
      typeResolver.initialize(plan);

      val expression = resolvePlan(plan.child);

      val activated = new HashSet[String];

      resolveActiveColumns(expression, activated);
      val replaces = activated.map(column => {
        val columnVar = context.mkConst(column, defaultSort);
        val variables = model.columnVars.getOrElse(column, null);
        val list = variables.map(v => {
          context.mkEq(columnVar, v);
        });
        disjuncate(list);
      }).toList;
      return conjuncate(expression :: replaces);
    }

    private def resolveActiveColumns(expression: Expr, set: HashSet[String]) {
      expression match {
        case int: IntExpr => {
          val column = model.varIndex.get(int);
          if (column.isDefined) {
            set.add(column.get);
          }
        }
        case real: RealExpr => {
          val column = model.varIndex.get(real);
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
        val leftVar = model.getVariable(getAttributeString(left, plan));
        val rightVar = model.getVariable(getAttributeString(right, plan));
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
          val leftSub = concatComplexAttribute(leftStr, types);
          val rightSub = concatComplexAttribute(rightStr, types);
          val result = binaryConstraintHelper(leftSub, rightSub);
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

  private val context = new Context;

  private val partitions = new ArrayBuffer[T];

  private val tmpQueries = new ArrayBuffer[DPQuery];

  def track(plan: Aggregate) {
    val builder = new SMTBuilder;
    val constraint = builder.buildSMT(plan);
    //check satisfiability
    if (!satisfiable(constraint, context)) {
      logWarning("Range constraint unsatisfiable, ignore query");
      return ;
    }

    //decompose the aggregate query
    plan.aggregateExpressions.foreach(agg => agg.foreach(expr => {
      expr match {
        case a: AggregateExpression if (a.enableDP && a.sensitivity > 0) => tmpQueries.append(new DPQuery(constraint, a, plan));
        case _ =>
      }
    }));
  }

  def commit(failed: Set[Int]) {
    //put queries into partitions
    var pi = 0;
    tmpQueries.withFilter(q => !failed.contains(q.dpId)).foreach(query => {
      var found = false;
      while (!found && pi < partitions.length) {
        val partition = partitions(pi);
        if (partition.disjoint(query)) {
          found = true;
          partition.add(query);
        }
        pi += 1;
      }
      if (!found) {
        val partition = partitionBuilder(context);
        partition.add(query);
        partitions.append(partition);
      }
    });

    tmpQueries.clear;
    budget.show;
  }

  def testBudget() {
    val copy = budget.copy;
    tmpQueries.foreach(copy.consume(_));
  }

}