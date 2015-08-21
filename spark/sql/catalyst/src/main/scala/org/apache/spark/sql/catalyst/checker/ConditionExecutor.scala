package org.apache.spark.sql.catalyst.checker

import scala.collection.JavaConversions.asScalaBuffer
import scala.collection.JavaConversions.asScalaSet
import scala.collection.JavaConversions.seqAsJavaList
import scala.collection.mutable.HashMap
import scala.collection.mutable.HashSet

import org.apache.spark.Logging
import org.apache.spark.sql.catalyst.expressions.Add
import org.apache.spark.sql.catalyst.expressions.And
import org.apache.spark.sql.catalyst.expressions.Attribute
import org.apache.spark.sql.catalyst.expressions.AttributeMap
import org.apache.spark.sql.catalyst.expressions.AttributeReference
import org.apache.spark.sql.catalyst.expressions.Divide
import org.apache.spark.sql.catalyst.expressions.EqualTo
import org.apache.spark.sql.catalyst.expressions.Expression
import org.apache.spark.sql.catalyst.expressions.GreaterThan
import org.apache.spark.sql.catalyst.expressions.GreaterThanOrEqual
import org.apache.spark.sql.catalyst.expressions.LessThan
import org.apache.spark.sql.catalyst.expressions.LessThanOrEqual
import org.apache.spark.sql.catalyst.expressions.Literal
import org.apache.spark.sql.catalyst.expressions.Multiply
import org.apache.spark.sql.catalyst.expressions.Not
import org.apache.spark.sql.catalyst.expressions.Or
import org.apache.spark.sql.catalyst.expressions.Remainder
import org.apache.spark.sql.catalyst.expressions.Subtract
import org.apache.spark.sql.catalyst.plans.logical.Filter
import org.apache.spark.sql.catalyst.plans.logical.Join
import org.apache.spark.sql.catalyst.plans.logical.LeafNode
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import org.apache.spark.sql.types.DataTypes

import edu.thu.ss.spec.lang.{ expression => PSpecExpression }
import edu.thu.ss.spec.lang.pojo.Condition
import edu.thu.ss.spec.lang.pojo.DataCategory
import edu.thu.ss.spec.lang.pojo.ExpandedRule
import edu.thu.ss.spec.lang.pojo.Policy

/**
 * modify logicalPlan given filter rules
 * used After Analyzer and LabelPropagator
 */
class ConditionExecutor(plan: LogicalPlan) extends Logging {

  def execute(policies: Set[Policy]) {
    policies.foreach(p => p.getExpandedRules().foreach(executeRule));
  }

  private def executeRule(rule: ExpandedRule) {
    if (!rule.isFilter()) {
      return ;
    }

    //check scope
    val inputData = getInputDataCategories(plan)
    if (rule.isSingle()) {
      val dataRef = rule.getDataRef()
      val isMatch = !inputData.filter(category => dataRef.contains(category)).isEmpty
      if (!isMatch) {
        return ;
      }
    } else if (rule.isAssociation()) {
      val dataRefs = rule.getAssociation().getDataRefs()
      val isMatch = !dataRefs.map(dataRef => inputData.filter(category => dataRef.contains(category))).contains(false)
      if (!isMatch) {
        return ;
      }
    }

    val condition: Condition = rule.getCondition();
    condition.getExpression().split().foreach(expression => {
      val executor = new ExpressionExecutor(plan, expression)
      executor.execute
    })
  }

  private def getInputDataCategories(
    plan: LogicalPlan): Set[DataCategory] = {
    val categories: Set[DataCategory] = new HashSet
    plan match {
      case leaf: LeafNode => {
        val keys = leaf.projectLabels.keySet
        keys.filter(key => leaf.projectLabels.get(key).isDefined)
          .foreach(key => categories ++ leaf.projectLabels.get(key).getOrElse(null).getDatas)
      }
      case _ => categories ++ plan.children.flatMap(getInputDataCategories)
    }
    categories
  }
}

class ExpressionExecutor(
  plan: LogicalPlan,
  expression: PSpecExpression.Expression[DataCategory]) extends Logging {

  val attributeMap: Map[Attribute, AttributeReference] = new HashMap

  private def addToAttributeMap(map: AttributeMap[AttributeReference]) {
    map.keySet.filter(key => map.get(key).isDefined)
      .foreach(key => attributeMap.put(key, map.get(key).getOrElse(null)))
  }

  def execute() {
    expression match {
      case _: PSpecExpression.Predicate => {
        execute(plan, null)
      }
      case _ => logError("Invalid filter expression")
    }

  }

  private def execute(
    plan: LogicalPlan,
    parent: LogicalPlan): Boolean = {
    val isExecuteInChildren = plan.children.map(execute(_, plan)).contains(true)
    if (!isExecuteInChildren) {
      plan match {
        case leaf: LeafNode => {
          //Suppose the plan is MetastoreRelation
          addToAttributeMap(leaf.getAttributeMap)
          executeExpression(leaf, parent)
        }
        case join: Join => executeExpression(join, parent)
        case _ => false
      }
    }
    true
  }

  private def executeExpression(
    plan: LogicalPlan,
    parent: LogicalPlan): Boolean = {
    if (parent == null) {
      logError("Parent TreeNode cannot be null")
    }
    val dataMap: Map[DataCategory, Attribute] = new HashMap
    val condition = buildExpression(plan, dataMap, 0)
    if (condition == null) {
      false
    }
    val filter = new Filter(condition, plan)
    parent.children.remove(plan)
    parent.children.add(filter)
    true
  }

  private def buildExpression(
    relation: LogicalPlan,
    dataMap: Map[DataCategory, Attribute],
    index: Int): Expression = {
    if (expression.getDataSet().size() == index) {
      return buildExpression(expression, dataMap)
    }
    val dataCategory = expression.getDataSet().toSeq.get(index)
    val candidates = getCandidateAttributes(relation, dataCategory)
    if (candidates.isEmpty) {
      null
    }
    val expressions: Set[Expression] = new HashSet;
    candidates.foreach(candidate => {
      dataMap.put(dataCategory, candidate)
      expressions.add(buildExpression(relation, dataMap, index + 1))
    })
    
    expressions.tail.foldLeft(expressions.head) {
      (expr1: Expression, expr2: Expression) => new And(expr1, expr2)
    }
  }

  private def getCandidateAttributes(
    relation: LogicalPlan,
    dataCategory: DataCategory): Seq[Attribute] = {
    val input: Seq[Attribute] = relation.inputSet.toSeq
    input.filter(attr => {
      val label = relation.projectLabels.get(attr).getOrElse(null)
      if (label == null) {
        false
      }
      label.getDatas.contains(dataCategory)
    })
  }

  private def buildExpression(
    expression: PSpecExpression.Expression[DataCategory],
    dataMap: Map[DataCategory, Attribute]): Expression = {
    expression match {
      case bp: PSpecExpression.Predicate => buildPredicate(bp, dataMap)
      case bc: PSpecExpression.Comparison => buildComparison(bc, dataMap)
      case fc: PSpecExpression.Function => buildFunction(fc, dataMap)
      case tm: PSpecExpression.Term => buildTerm(tm, dataMap)
      case _ => {
        logError("Invalid filter expression")
        null
      }
    }
  }

  private def buildPredicate(
    expression: PSpecExpression.Predicate,
    dataMap: Map[DataCategory, Attribute]): Expression = {
    expression match {
      case or: PSpecExpression.Or => {
        val list = or.getExpressions().map(buildExpression(_, dataMap))
        list.tail.foldLeft(list.head) {
          (expr1: Expression, expr2: Expression) => new Or(expr1, expr2)
        }
      }
      case and: PSpecExpression.And => {
        val list = and.getExpressions().map(buildExpression(_, dataMap))
        list.tail.foldLeft(list.head) {
          (expr1: Expression, expr2: Expression) => new And(expr1, expr2)
        }
      }
      case not: PSpecExpression.Not => new Not(buildExpression(not.getExpression(), dataMap))
      case _ => {
        logError("Unexpected Predicate: $expression, must be AND, OR or NOT")
        null
      }
    }
  }

  private def buildComparison(
    expression: PSpecExpression.Comparison,
    dataMap: Map[DataCategory, Attribute]): Expression = {
    val left = buildExpression(expression.getLeftExpression(), dataMap)
    val right = buildExpression(expression.getRightExpression(), dataMap)
    expression match {
      case _: PSpecExpression.EqualTo => new EqualTo(left, right)
      case _: PSpecExpression.GreaterThan => new GreaterThan(left, right)
      case _: PSpecExpression.GreaterThanOrEqual => new GreaterThanOrEqual(left, right)
      case _: PSpecExpression.LessThan => new LessThan(left, right)
      case _: PSpecExpression.LessThanOrEqual => new LessThanOrEqual(left, right)
      case _ => {
        logError("Invalid Comparison: $expression")
        null
      }
    }
  }

  private def buildFunction(
    expression: PSpecExpression.Function,
    dataMap: Map[DataCategory, Attribute]): Expression = {
    val left = buildExpression(expression.getLeftExpression(), dataMap)
    val right = buildExpression(expression.getRightExpression(), dataMap)
    expression match {
      case _: PSpecExpression.Add => new Add(left, right)
      case _: PSpecExpression.Subtract => new Subtract(left, right)
      case _: PSpecExpression.Multiply => new Multiply(left, right)
      case _: PSpecExpression.Divide => new Divide(left, right)
      case _: PSpecExpression.Remainder => new Remainder(left, right)
      case _ => {
        logError("Invalid Function: $expression")
        null
      }
    }

  }

  private def buildTerm(
    expression: PSpecExpression.Term,
    dataMap: Map[DataCategory, Attribute]): Expression = {
    if (expression.isDataCategory()) {
      val dataCategory = expression.getDataCategory()
      val attribute = dataMap.get(dataCategory).getOrElse(null)
      if (attribute == null) {
        logError("Cannot find attribute for DataCategory: $dataCategory")
      }
      attributeMap.get(attribute).getOrElse(null)
    } else {
      new Literal(expression.getData(), DataTypes.IntegerType)
    }
  }

}