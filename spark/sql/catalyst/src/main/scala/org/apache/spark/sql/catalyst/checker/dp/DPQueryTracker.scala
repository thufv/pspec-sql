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

/**
 * tracking submitted dp-enabled queries for parallel composition theorem
 */
class DPQueryTracker extends Logging {

  private class TypeResolver {

    private val deriveGraph = new DirectedPseudograph[String, DefaultEdge](classOf[DefaultEdge]);

    private val complexAttributes = new HashSet[String];

    private val attributeSubs = new HashMap[String, Set[String]];

    def get(attr: String): Set[String] = attributeSubs.getOrElse(attr, null);

    def initialize(plan: Aggregate) {
      buildGraph(plan);

      complexAttributes.foreach(attr => {
        val pre = getComplexAttribute(attr);
        val set = attributeSubs.getOrElseUpdate(pre, new HashSet[String]);
        set.add(attr);
      });
    }

    def effective(attr: Attribute): Boolean = {
      return attr.dataType.isInstanceOf[NumericType];
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
      complexAttributes.foreach(queue.enqueue(_));
      while (!queue.isEmpty) {
        val attr = queue.dequeue;
        val pre = getComplexAttribute(attr);
        val subtypes = getComplexSubtypes(attr);
        if (deriveGraph.containsVertex(pre)) {
          val equivalents = alg.connectedSetOf(pre);
          for (equi <- equivalents) {
            val equiAttr = concatComplexAttribute(equi, subtypes);
            if (!complexAttributes.contains(equiAttr)) {
              queue.enqueue(equiAttr);
              complexAttributes.add(equiAttr);
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
            complexAttributes.add(str);
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
    private val attrVars = new HashMap[String, ArithExpr];

    val varIndex = new HashMap[ArithExpr, String];

    /**
     * key: table.column, value: a set of variables
     */
    val columnVars = new HashMap[String, Buffer[ArithExpr]];

    def getVariable(attr: String): ArithExpr = {
      return attrVars.getOrElse(attr, null);
    }
    def createAttrVariable(attr: String, table: String, variable: ArithExpr) {
      attrVars.put(attr, variable);
      val column = s"$table.${getColumnString(attr)}";
      
      val buffer = columnVars.getOrElseUpdate(column, new ArrayBuffer[ArithExpr]);
      buffer.append(variable);

      varIndex.put(variable, column);
    }

    def addAttrVariable(attr: String, variable: ArithExpr) {
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
        val attrVar = context.mkConst(attrStr, defaultSort).asInstanceOf[ArithExpr];
        model.createAttrVariable(attrStr, label.table, attrVar);
      } else {
        val subs = typeResolver.get(attrStr);
        if (subs == null) {
          return ;
        }
        //create a variable for each subtype
        subs.foreach(sub => {
          val attrVar = context.mkConst(sub, defaultSort).asInstanceOf[ArithExpr];
          model.createAttrVariable(sub, label.table, attrVar);
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
        set.foreach(sub => {
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
      def binaryPredicateHelper(binary: BinaryComparison, consFunc: (ArithExpr, ArithExpr) => BoolExpr): BoolExpr = {
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
      pred match {
        case equal: EqualTo => {
          binaryPredicateHelper(equal, context.mkEq(_, _));
        }
        case equal: EqualNullSafe => {
          binaryPredicateHelper(equal, context.mkEq(_, _));
        }
        case lt: LessThan => {
          binaryPredicateHelper(lt, context.mkLt(_, _));
        }
        case lte: LessThanOrEqual => {
          binaryPredicateHelper(lte, context.mkLe(_, _));
        }
        case gt: GreaterThan => {
          binaryPredicateHelper(gt, context.mkGt(_, _));
        }
        case gte: GreaterThanOrEqual => {
          binaryPredicateHelper(gte, context.mkGe(_, _));
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

    private def resolveTerm(term: Expression, plan: LogicalPlan): ArithExpr = {
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
          val value = toReal(literal.value);
          if (value != null) {
            context.mkReal(value);
          } else {
            null;
          }
        }
        case mutable: MutableLiteral => {
          val value = toReal(mutable.value);
          if (value != null) {
            context.mkReal(value);
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

    private def resolveBinaryArithmetic(binary: BinaryArithmetic, plan: LogicalPlan): ArithExpr = {

      def binaryHelper(transFunc: (ArithExpr, ArithExpr) => ArithExpr): ArithExpr = {
        val left = resolveTerm(binary.left, plan);
        val right = resolveTerm(binary.right, plan);
        if (left == null || right == null) {
          return null;
        } else {
          return transFunc(left, right);
        }
      }

      def binaryMultiHealper(transFunc: (Seq[ArithExpr]) => ArithExpr): ArithExpr = {
        val list = collect(binary, binary.getClass()).map(resolveTerm(_, plan));
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
        val child = resolveTerm(unary.child, plan);
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

    private def resolveAttributeVar(attr: Expression, plan: LogicalPlan): ArithExpr = {
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

  def track(plan: Aggregate) {
    val builder = new SMTBuilder;
    val constraint = builder.buildSMT(plan);
    println(constraint);
  }

}