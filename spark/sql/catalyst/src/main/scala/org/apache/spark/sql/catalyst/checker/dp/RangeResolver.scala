package org.apache.spark.sql.catalyst.checker.dp

import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import org.apache.spark.sql.catalyst.plans.logical.Filter
import scala.collection.mutable.HashMap
import org.apache.spark.sql.catalyst.plans.logical.UnaryNode
import org.apache.spark.sql.catalyst.plans.logical.LeafNode
import org.apache.spark.sql.catalyst.plans.logical.BinaryNode
import org.apache.spark.sql.catalyst.checker.util.TypeUtil._
import org.apache.spark.sql.catalyst.expressions._
import org.apache.spark.sql.types.IntegralType
import org.apache.spark.sql.catalyst.checker.util.CheckerUtil._
import org.apache.spark.sql.types.FractionalType
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.DataType
import org.apache.spark.sql.types.NumericType
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import org.apache.spark.sql.types.DataType
import org.apache.ivy.core.resolve.ResolveProcessException
import scala.collection.mutable.Buffer

/**
 * given a query, calculate a conservative range for each column
 */
class RangeResolver {

  private def support(dataType: DataType): Boolean = {
    return dataType.isInstanceOf[NumericType] || dataType.isInstanceOf[StringType];
  }

  def resolve(plan: Aggregate, columnAttrs: Map[String, Seq[String]]): Map[String, Range] = {
    val attrRanges = resolveRange(plan.child);

    val columnRanges = new HashMap[String, Range];
    columnAttrs.foreach(t => {
      val column = t._1;
      val attrs = t._2;
      val ranges = attrs.map(attrRanges.getOrElse(_, null));
      if (ranges.forall(_ != null)) {
        columnRanges.put(column, ranges.reduce(_.union(_)));
      }
    });
    return columnRanges;
  }

  private def resolveRange(plan: LogicalPlan): Map[String, Range] = {
    plan match {
      case filter: Filter => {
        val child = resolveRange(filter.child);
        val range = resolveCondition(filter.condition, plan);
        conjuncate(child, range);
      }
      case leaf: LeafNode => {
        Map.empty;
      }
      case unary: UnaryNode => {
        resolveRange(unary.child);
      }
      case binary: BinaryNode => {
        resolveRange(binary.left) ++ resolveRange(binary.right);
      }
    }
  }

  private def resolveCondition(expr: Expression, plan: LogicalPlan): Map[String, Range] = {
    val simplified = simplify(expr, plan);
    if (simplified == null) {
      return Map.empty;
    }
    val dnfs = DNFRewriter.apply(simplified);

    val dnfRanges = dnfs.map(resolveConjunction(_, plan));
    val ranges = dnfRanges.reduce(disjuncate(_, _));
    return ranges;
  }

  private def simplify(expr: Expression, plan: LogicalPlan): Expression = {
    expr match {
      case and: And => {
        val left = simplify(and.left, plan);
        val right = simplify(and.right, plan);
        if (left == null) {
          return right;
        } else if (right == null) {
          return left;
        }
        return And(left, right);
      }
      case or: Or => {
        val left = simplify(or.left, plan);
        val right = simplify(or.right, plan);
        if (left == null || right == null) {
          return null;
        } else {
          return Or(left, right);
        }
      }
      case not: Not => {
        val child = simplify(not.child, plan);
        if (child == null) {
          return null;
        } else {
          return Not(child);
        }
      }
      case _ => {
        return simplifyPredicate(expr, plan);
      }
    }
  }

  private def simplifyPredicate(expr: Expression, plan: LogicalPlan): Expression = {
    expr match {
      case binary: BinaryComparison => {
        if (isStoredAttribute(binary.left, plan) && support(binary.left.dataType) && resolveLiteral(binary.right) != null) {
          return binary;
        } else if (resolveLiteral(binary.left) != null && isStoredAttribute(binary.right, plan) && support(binary.right.dataType)) {
          return reverse(binary);
        } else {
          return null;
        }
      }

      case in: InSet => {
        val left = resolveSimpleAttribute(in.value);
        if (isStoredAttribute(left, plan)) {
          return in;
        } else {
          return null;
        }
      }
    }
  }

  private def reverse(binary: BinaryComparison): BinaryComparison = {
    binary match {
      case EqualTo(left, right) => EqualTo(right, left);
      case EqualNullSafe(left, right) => EqualNullSafe(right, left);
      case LessThan(left, right) => GreaterThan(right, left);
      case LessThanOrEqual(left, right) => GreaterThanOrEqual(right, left);
      case GreaterThan(left, right) => LessThan(right, left);
      case GreaterThanOrEqual(left, right) => LessThanOrEqual(right, left);
    }
  }

  private def resolveConjunction(expr: Expression, plan: LogicalPlan): Map[String, Range] = {
    val map = new HashMap[String, Range];
    resolveConjunction(expr, plan, map);
    return map;
  }

  private def resolveConjunction(expr: Expression, plan: LogicalPlan, ranges: Map[String, Range]) {
    expr match {
      case and: And => {
        resolveConjunction(and.left, plan, ranges);
        resolveConjunction(and.right, plan, ranges);
      }
      case not: Not => {
        val (attr, range) = resolvePredicate(expr, plan);
        val notRange = range.not;
        addRange(attr, notRange, ranges);
      }
      case _ => {
        val (attr, range) = resolvePredicate(expr, plan);
        addRange(attr, range, ranges);
      }
    }
  }

  private def resolvePredicate(expression: Expression, plan: LogicalPlan): (String, Range) = {
    expression match {
      case binary: BinaryComparison => {
        return resolveComparison(binary, plan);
      }
      case in: InSet => {
        val attr = resolveSimpleAttribute(in.value);
        val attrStr = getAttributeString(attr, plan);
        val ranges = in.hset.map(item => buildRange(attr.dataType, item,
          value => Interval.newInstance(value, true, value, true, false),
          value => Interval.newInstance(value, true, value, true, true),
          value => CategoricalRange(true).addValue(value)));
        val result = ranges.reduce((range1, range2) => range1.union(range2));
        return (attrStr, result);
      }
    }
  }

  private def resolveComparison(binary: BinaryComparison, plan: LogicalPlan): (String, Range) = {
    val attr = resolveSimpleAttribute(binary.left);
    val attrStr = getAttributeString(attr, plan);
    val range =
      binary match {
        case _: EqualTo | _: EqualNullSafe => {
          buildRange(attr.dataType, resolveLiteral(binary.right).value, value => Interval.newInstance(value, true, value, true, false),
            value => Interval.newInstance(value, true, value, true, true),
            value => CategoricalRange(true).addValue(value));
        }
        case LessThan(left, right) => {
          buildRange(attr.dataType, resolveLiteral(binary.right).value, value => Interval.newEndBounded(value, false, false),
            value => Interval.newEndBounded(value, false, true),
            _ => null);
        }
        case LessThanOrEqual(left, right) => {
          buildRange(attr.dataType, resolveLiteral(binary.right).value, value => Interval.newEndBounded(value, true, false),
            value => Interval.newEndBounded(value, true, true),
            _ => null);
        }
        case GreaterThan(left, right) => {
          buildRange(attr.dataType, resolveLiteral(binary.right).value, value => Interval.newStartBounded(value, false, false),
            value => Interval.newStartBounded(value, false, true),
            _ => null);
        }
        case GreaterThanOrEqual(left, right) => {
          buildRange(attr.dataType, resolveLiteral(binary.right).value, value => Interval.newStartBounded(value, true, false),
            value => Interval.newStartBounded(value, true, true),
            _ => null);
        }
      }
    return (attrStr, range);
  }

  private def buildRange(dataType: DataType, value: Any, ifunc: Double => Interval, ffunc: Double => Interval, sfunc: String => Range): Range = {
    dataType match {
      case intType: IntegralType => {
        val int = toInt(value);
        val interval = ifunc(int);
        return NumericalRange(Seq(interval), false);
      }
      case fracType: FractionalType => {
        val double = toDouble(value);
        val interval = ffunc(double);
        return NumericalRange(Seq(interval), true);
      }
      case strType: StringType => {
        val string = value.toString;
        return sfunc(string);
      }
    }
  }

  private def addRange(attr: String, range: Range, ranges: Map[String, Range]) {
    val prevRange = ranges.getOrElse(attr, null);
    if (prevRange == null) {
      ranges.put(attr, range);
    } else {
      ranges.put(attr, prevRange.intersect(range));
    }
  }

}