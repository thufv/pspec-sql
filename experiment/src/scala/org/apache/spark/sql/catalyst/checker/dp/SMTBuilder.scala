package org.apache.spark.sql.catalyst.checker.dp

import scala.collection.JavaConversions._
import com.microsoft.z3.BoolExpr
import org.apache.spark.sql.catalyst.expressions.UnaryExpression
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.catalyst.expressions.Alias
import org.apache.spark.sql.catalyst.expressions.GreaterThan
import org.apache.spark.sql.catalyst.expressions.UnaryMinus
import org.apache.spark.sql.catalyst.expressions.In
import scala.collection.mutable.ArrayBuffer
import org.apache.spark.sql.catalyst.expressions.EqualNullSafe
import org.apache.spark.sql.catalyst.expressions.Remainder
import org.apache.spark.sql.catalyst.plans.logical.Intersect
import org.apache.spark.sql.catalyst.expressions.GreaterThanOrEqual
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import org.apache.spark.sql.catalyst.expressions.LessThan
import org.apache.spark.sql.catalyst.expressions.MutableLiteral
import org.apache.spark.sql.catalyst.expressions.BinaryComparison
import org.apache.spark.sql.catalyst.plans.logical.Project
import org.apache.spark.sql.catalyst.expressions.LessThanOrEqual
import org.apache.spark.sql.catalyst.plans.logical.Except
import org.apache.spark.sql.catalyst.expressions.Cast
import org.apache.spark.sql.catalyst.plans.logical.Join
import scala.collection.mutable.ListBuffer
import com.microsoft.z3.ArithExpr
import org.apache.spark.sql.catalyst.plans.logical.Union
import org.apache.spark.sql.catalyst.expressions.EqualTo
import org.apache.spark.sql.types.BooleanType
import org.apache.spark.sql.catalyst.expressions.BinaryArithmetic
import org.apache.spark.sql.catalyst.expressions.InSet
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import scala.collection.mutable.HashMap
import scala.collection.mutable.HashSet
import org.apache.spark.sql.catalyst.expressions.Add
import scala.collection.mutable.Buffer
import org.apache.spark.sql.types.FractionalType
import com.microsoft.z3.Expr
import org.apache.spark.sql.catalyst.plans.logical.BinaryNode
import org.apache.spark.sql.catalyst.expressions.Divide
import org.apache.spark.sql.catalyst.plans.logical.UnaryNode
import org.apache.spark.sql.catalyst.expressions.Predicate
import org.apache.spark.sql.catalyst.expressions.And
import org.apache.spark.sql.catalyst.expressions.Abs
import org.apache.spark.sql.catalyst.expressions.Literal
import org.apache.spark.sql.catalyst.expressions.Expression
import org.apache.spark.sql.catalyst.expressions.Multiply
import org.apache.spark.sql.types.IntegralType
import org.apache.spark.sql.catalyst.plans.logical.LeafNode
import org.apache.spark.sql.catalyst.expressions.Not
import org.apache.spark.sql.catalyst.expressions.Subtract
import org.apache.spark.sql.catalyst.expressions.Attribute
import org.apache.spark.sql.catalyst.plans.logical.Filter
import com.microsoft.z3.Sort
import org.apache.spark.sql.types.DataType
import org.apache.spark.sql.catalyst.expressions.Or
import com.microsoft.z3.Context
import org.apache.spark.Logging
import org.apache.spark.sql.types.NumericType
import scala.collection.mutable.Queue
import org.apache.spark.sql.catalyst.checker.util.TypeUtil
import org.apache.spark.sql.sources.LogicalRDD
import org.apache.spark.sql.catalyst.checker.util.CheckerUtil

case class AttributeObject(val column: String, val attribute: String, val variable: Expr);

class ObjectIndex() {

  private val attributeIndex = new HashMap[String, AttributeObject];

  private val variableIndex = new HashMap[Expr, AttributeObject];

  def add(ao: AttributeObject) {
    attributeIndex.put(ao.attribute, ao);
    variableIndex.put(ao.variable, ao);

  }

  private def get[T, U](key: T, index: Map[T, AttributeObject], f: (AttributeObject) => U): U = {
    val result = index.get(key);
    result match {
      case Some(o) => f(o);
      case None => null.asInstanceOf[U];
    }
  }

  def getVariableByAttribute(attr: String): Expr = {
    get(attr, attributeIndex, _.variable);
  }

  def getColumnByAttribute(attr: String): String = {
    get(attr, attributeIndex, _.column);
  }

  def getColumnByVariable(variable: Expr): String = {
    get(variable, variableIndex, _.column);
  }
}

class SMTModel {

  private val variables = new HashMap[String, Expr];

  def addAttrVariable(attr: String, variable: Expr) {
    variables.put(attr, variable);
  }

  def getVariableByAttribute(attr: String) = {
    variables.getOrElse(attr, null);
  }

}

private object SMTBuilder {
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
 * Transform a query (plan) into a SMT equation, which represents the column ranges
 */
private class SMTBuilder(val context: Context) extends Logging {

  val model = new SMTModel;

  private var id = 0;

  def buildSMT(plan: Aggregate, activated: Set[String]): BoolExpr = {
    val constraint = resolvePlan(plan.child);
    collectColumns(constraint, activated);
    return constraint.simplify().asInstanceOf[BoolExpr];
  }

  private def collectColumns(expr: Expr, columns: Set[String]) {
    expr match {
      case v if (v.isConst() && !(v.isTrue() || v.isFalse())) => {
        columns.add(v.getSExpr());
      }
      case leaf if (leaf.getNumArgs() == 0) =>
      case _ => expr.getArgs().foreach(collectColumns(_, columns));
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

      case leaf: LogicalRDD => {
        val table = leaf.name;
        leaf.output.foreach(initializeVariable(_, leaf, table));
        context.mkTrue();
      }
      case _ => {
        conjuncate(plan.children.map(resolvePlan(_)));
      }
    }
  }

  private def createTmpVariable(sort: Sort): Expr = {
    context.mkFreshConst("tmp", sort);
  }

  /**
   * create a variable for each stored attribute
   */
  private def initializeVariable(attr: Attribute, plan: LogicalPlan, table: String) {
    val attrStr = attr.name;
    val attrVar = createVariable(attrStr, attr.dataType);
    model.addAttrVariable(attrStr, attrVar);
  }

  private def createUnaryVariable(expr: Expression, output: Attribute, plan: UnaryNode) {
    val outStr = output.name;
    val attrVar = resolveTerm(expr, plan);
    if (attrVar != null) {
      model.addAttrVariable(outStr, attrVar);
    }
  }

  private def resolveExpression(cond: Expression, plan: LogicalPlan): BoolExpr = {
    cond match {
      case and: And => {
        val list = CheckerUtil.collect(and, classOf[And]).map(resolveExpression(_, plan)).filter(_ != null);
        conjuncate(list);
      }
      case or: Or => {
        val list = CheckerUtil.collect(or, classOf[Or]).map(resolveExpression(_, plan));
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
          val constraints = inSet.hset.map(v => {
            context.mkEq(left, context.mkInt(SMTBuilder.getConstant(v.toString)));
          }).toSeq;
          return disjuncate(constraints);
        }
      }
      case _ => null
    }
  }

  private def resolveTerm(term: Expression, plan: LogicalPlan): Expr = {
    term match {
      //add support for complex data types
      case attr if (TypeUtil.isAttribute(attr)) => {
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
      val list = CheckerUtil.collect(binary, binary.getClass()).map(resolveTerm(_, plan).asInstanceOf[ArithExpr]);
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
    val str = attr.asInstanceOf[Attribute].name;
    return model.getVariableByAttribute(str);
  }

  def createConstant(value: Any, dataType: DataType): Expr = {
    dataType match {
      case i: IntegralType => context.mkInt(anyToInt(value));
      case f: FractionalType => context.mkReal(toReal(value));
      case s: StringType => context.mkInt(SMTBuilder.getConstant(value.asInstanceOf[String]));
      case b: BooleanType => context.mkBool(value.asInstanceOf[Boolean]);
      case _ => null;
    }
  }

  def createVariable(name: String, dataType: DataType) = {
    dataType match {
      case i: IntegralType => context.mkConst(name, context.mkIntSort());
      case f: FractionalType => context.mkConst(name, context.mkRealSort());
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
  private def toReal(v: Any): String = {
    val string = v.toString;
    try {
      val result = string.toDouble;
      return string;
    } catch {
      case t: NumberFormatException => return null;
    }
  }
  private def anyToInt(any: Any): Int = {
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

  private def nextId(): Int = {
    id += 1;
    id;
  }
}