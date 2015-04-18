package org.apache.spark.sql.catalyst.checker.dp

import scala.collection.JavaConversions._
import com.microsoft.z3.BoolExpr
import org.apache.spark.sql.catalyst.expressions.UnaryExpression
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.catalyst.expressions.Alias
import org.apache.spark.sql.catalyst.expressions.GreaterThan
import org.apache.spark.sql.catalyst.expressions.UnaryMinus
import org.apache.spark.sql.catalyst.checker.ColumnLabel
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
import org.apache.spark.sql.catalyst.checker.util.TypeUtil._
import org.apache.spark.sql.catalyst.checker.util.CheckerUtil._
import com.microsoft.z3.Context
import org.apache.spark.Logging
import org.jgrapht.graph.Pseudograph
import org.apache.spark.sql.types.NumericType
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.alg.ConnectivityInspector
import scala.collection.mutable.Queue

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

case class AttributeObject(val column: String, val attribute: String, val variable: Expr);

case class ObjectIndex {

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

  private val objects = new ArrayBuffer[AttributeObject];

  val storedAttributeIndex = new ObjectIndex;

  val attributeIndex = new ObjectIndex;

  private val columnIndex = new HashMap[String, Buffer[AttributeObject]];

  /**
   * key: table, value: a list of table instances (each of which is a list of attributes)
   */
  val tables = new HashMap[String, Buffer[Seq[String]]];

  def getAttributesByColumn(column: String): Seq[String] = {
    val result = columnIndex.get(column);
    result match {
      case Some(l) => l.map(_.attribute);
      case None => Nil;
    }
  }

  def initAttrVariable(attr: String, table: String, variable: Expr) {
    val column = s"$table.${getColumnString(attr)}";

    val attributeObject = AttributeObject(column, attr, variable);
    objects.append(attributeObject);

    storedAttributeIndex.add(attributeObject);
    attributeIndex.add(attributeObject);

    val buffer = columnIndex.getOrElseUpdate(column, new ArrayBuffer);
    buffer.append(attributeObject);
  }

  def addAttrVariable(attr: String, variable: Expr) {
    val attributeObject = AttributeObject(null, attr, variable);
    objects.append(attributeObject);
    attributeIndex.add(attributeObject);

  }

  def addTable(table: String, attributes: Seq[String]) {
    val buffer = tables.getOrElseUpdate(table, new ArrayBuffer);
    buffer.append(attributes);
  }

  def isStoredVariable(attr: String, variable: Expr): Boolean = {
    return storedAttributeIndex.getVariableByAttribute(attr) == variable;

  }
}

/**
 * Transform a query (plan) into a SMT equation, which represents the column ranges
 */
private class SMTBuilder(val context: Context) extends Logging {

  val model = new SMTModel;

  private val typeResolver = new TypeResolver;

  private var id = 0;

  def buildSMT(plan: Aggregate): BoolExpr = {
    typeResolver.initialize(plan);

    val constraint = resolvePlan(plan.child);

    val activated = new HashSet[String];

    collectVariables(constraint).foreach(v => {
      val column = model.storedAttributeIndex.getColumnByVariable(v);
      if (column != null) {
        activated.add(column);
      }
    });

    val replaces = new ListBuffer[BoolExpr];
    replaces.append(constraint);
    model.tables.values.foreach(buffer => {
      val transformed = buffer.map(attributes => {
        val filtered = attributes.map(attr => {
          val column = model.storedAttributeIndex.getColumnByAttribute(attr);
          if (column != null && activated.contains(column)) {
            val attrVar = model.storedAttributeIndex.getVariableByAttribute(attr);
            val columnVar = context.mkConst(column, attrVar.getSort());
            context.mkEq(columnVar, attrVar);
          } else {
            null;
          }
        }).filter(_ != null);
        conjuncate(filtered);
      })
      replaces.append(disjuncate(transformed));
    });

    return conjuncate(replaces).asInstanceOf[BoolExpr];
  }

  private def collectVariables(expression: Expr): Set[Expr] = {
    def collectHelper(expr: Expr, set: Set[Expr]) {
      expr match {
        case v if (v.isConst()) => {
          set.add(v);
        }
        case leaf if (leaf.getNumArgs() == 0) =>
        case _ => expr.getArgs().foreach(collectHelper(_, set));
      }
    }
    val result = new HashSet[Expr];
    collectHelper(expression, result);
    return result;
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
        val rightConstraint = resolvePlan(binary.right);

        binary match {
          case union: Union => {
            val unionConstraint = createBinaryConstraint(union, (left, right) => s"union($left,$right)", context.mkOr(_, _));
            context.mkAnd(leftConstraint, rightConstraint, unionConstraint);
          }
          case intersect: Intersect => {
            val intersectConstraint = createBinaryConstraint(intersect, (left, right) => s"intersect($left,$right)", context.mkAnd(_, _));
            context.mkAnd(leftConstraint, rightConstraint, intersectConstraint);
          }
          case except: Except => {
            val exceptConstraint = createExceptConstraint(except, leftConstraint);
            context.mkAnd(leftConstraint, rightConstraint, exceptConstraint);
          }
        }
      }
      case leaf: LeafNode => {
        if (leaf.output.headOption.isDefined) {
          val label = leaf.projectLabels.getOrElse(leaf.output.head, null).asInstanceOf[ColumnLabel];
          val table = label.table;
          val attributes = leaf.output.flatMap(attr => {
            val string = getAttributeString(attr, leaf);
            if (attr.dataType.isPrimitive) {
              Seq(string);
            } else {
              val map = typeResolver.get(string);
              if (map != null) {
                map.keys;
              } else {
                Nil;
              }
            }
          });
          leaf.output.map(getAttributeString(_, leaf));
          model.addTable(table, attributes);
          leaf.output.foreach(initializeVariable(_, leaf, table));
        }
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
  private def initializeVariable(attr: Attribute, plan: LeafNode, table: String) {
    val attrStr = getAttributeString(attr, plan);
    if (attr.dataType.isPrimitive) {
      if (!typeResolver.effective(attr)) {
        return ;
      }
      val attrVar = createVariable(attrStr, attr.dataType);
      model.initAttrVariable(attrStr, table, attrVar);
    } else {
      val subs = typeResolver.get(attrStr);
      if (subs == null) {
        return ;
      }
      //create a variable for each subtype
      subs.foreach(sub => {
        val attrVar = createVariable(sub._1, sub._2);
        model.initAttrVariable(sub._1, table, attrVar);
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
        val exprVar = model.attributeIndex.getVariableByAttribute(exprTypes);
        if (exprVar != null) {
          model.addAttrVariable(outType, exprVar);
        }
      });
    }
  }

  private def createBinaryConstraint(plan: BinaryNode, f: (String, String) => String, g: (BoolExpr, BoolExpr) => BoolExpr): BoolExpr = {
    def binaryHelper(left: String, right: String, output: String, lefts: Buffer[BoolExpr], rights: Buffer[BoolExpr]) {
      val leftVar = model.attributeIndex.getVariableByAttribute(left);
      val rightVar = model.attributeIndex.getVariableByAttribute(right);
      if (leftVar == null || rightVar == null) {
        return ;
      }
      val outputVar = context.mkConst(f(left, right), leftVar.getSort());
      model.addAttrVariable(output, outputVar);

      lefts.append(context.mkEq(outputVar, leftVar));
      rights.append(context.mkEq(outputVar, rightVar));
    }

    val leftAttributes = plan.left.output;
    val rightAttributes = plan.right.output;
    val outputAttributes = plan.output;

    val leftConstraints = new ArrayBuffer[BoolExpr]();
    val rightConstraints = new ArrayBuffer[BoolExpr]();

    for (i <- 0 to outputAttributes.length - 1) {
      val leftAttr = leftAttributes(i);
      val rightAttr = rightAttributes(i);
      val outputAttr = outputAttributes(i);

      val leftStr = getAttributeString(leftAttr, plan);
      val rightStr = getAttributeString(rightAttr, plan);
      val outputStr = getAttributeString(outputAttr, plan);

      if (outputAttr.dataType.isPrimitive) {
        binaryHelper(leftStr, rightStr, outputStr, leftConstraints, rightConstraints);
      } else {
        val set = typeResolver.get(leftStr);
        if (set != null) {
          //create variable for each subtype
          set.keys.foreach(sub => {
            val types = getComplexSubtypes(sub);
            val rightSub = concatComplexAttribute(rightStr, types);
            val outputSub = concatComplexAttribute(outputStr, types);
            binaryHelper(sub, rightSub, outputSub, leftConstraints, rightConstraints);
          });
        }
      }
    }
    val leftConstraint = conjuncate(leftConstraints);
    val rightConstraint = conjuncate(rightConstraints);

    g(leftConstraint, rightConstraint);

  }

  private def createExceptConstraint(except: Except, constraint: BoolExpr): BoolExpr = {
    return substitute(constraint, except.left.output, except.right.output, except);
  }

  private def substitute(constraint: BoolExpr, leftOutput: Seq[Attribute], rightOutput: Seq[Attribute], plan: LogicalPlan): BoolExpr = {

    val variableMap = new HashMap[Expr, Expr];
    val derivedAttrMap = new HashMap[String, String];

    def substituteHelper(left: String, right: String) {
      val leftVar = model.attributeIndex.getVariableByAttribute(left);
      val rightVar = model.attributeIndex.getVariableByAttribute(right);

      if (model.isStoredVariable(left, leftVar) && leftVar.isConst()
        && model.isStoredVariable(right, rightVar) && rightVar.isConst()) {
        variableMap.put(leftVar, rightVar);
      } else {
        derivedAttrMap.put(left, right);
      }
    }

    for (i <- 0 to leftOutput.length - 1) {
      val left = leftOutput(i);
      val right = rightOutput(i);
      val leftStr = getAttributeString(leftOutput(i), plan);
      val rightStr = getAttributeString(rightOutput(i), plan);

      if (left.dataType.isPrimitive) {
        substituteHelper(leftStr, rightStr);
      } else {
        val set = typeResolver.get(leftStr);
        if (set != null) {
          //create variable for each subtype
          set.keys.foreach(sub => {
            val types = getComplexSubtypes(sub);
            val rightSub = concatComplexAttribute(rightStr, types);
            substituteHelper(sub, rightSub);
          });
        }
      }
    }

    val set = collectVariables(constraint);
    val sourceVariables = new Array[Expr](set.size);
    set.copyToArray(sourceVariables);
    val destVariables = new Array[Expr](set.size);
    for (i <- 0 to sourceVariables.length - 1) {
      val source = sourceVariables(i);
      destVariables(i) = variableMap.getOrElseUpdate(source, createTmpVariable(source.getSort()));
    }

    val substituted = constraint.substitute(sourceVariables, destVariables).asInstanceOf[BoolExpr];
    if (derivedAttrMap.size > 0) {
      //now we should add eq constraints for derived attributes
      val list = new ListBuffer[BoolExpr];
      list.append(substituted);
      derivedAttrMap.foreach(t => {
        val source = t._1;
        val dest = t._2

        val sourceExpr = model.attributeIndex.getVariableByAttribute(source);
        val destExpr = model.attributeIndex.getVariableByAttribute(dest);

        val set = collectVariables(sourceExpr);
        val variables = new Array[Expr](set.size);
        set.copyToArray(variables);

        val subsVariables = variables.map(variableMap.getOrElse(_, null));
        val subsSource = sourceExpr.substitute(variables, subsVariables);
        list.append(context.mkEq(subsSource, destExpr));
      });
      return conjuncate(list);

    } else {
      return substituted;
    }
  }

  private def resolveExpression(cond: Expression, plan: LogicalPlan): BoolExpr = {
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
    return model.attributeIndex.getVariableByAttribute(str);
  }

  def createConstant(value: Any, dataType: DataType): Expr = {
    dataType match {
      case i: IntegralType => context.mkInt(anyToInt(value));
      case f: FractionalType => context.mkReal(toReal(value));
      case s: StringType => context.mkInt(DPQueryTracker.getConstant(value.asInstanceOf[String]));
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