package org.apache.spark.sql.catalyst.checker.util

import org.apache.spark.sql.catalyst.checker.LabelConstants._
import org.apache.spark.sql.catalyst.expressions.Alias
import org.apache.spark.sql.catalyst.expressions.Attribute
import org.apache.spark.sql.catalyst.expressions.Cast
import org.apache.spark.sql.catalyst.expressions.Expression
import org.apache.spark.sql.catalyst.expressions.Literal
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.apache.spark.sql.catalyst.trees.TreeNode
import edu.thu.ss.spec.meta.BaseType
import edu.thu.ss.spec.meta.CompositeType
import edu.thu.ss.spec.meta.StructType
import edu.thu.ss.spec.meta.ArrayType
import edu.thu.ss.spec.meta.MapType
import org.apache.spark.sql.catalyst.expressions.GetItem
import org.apache.spark.sql.catalyst.expressions.GetField
import scala.collection.mutable.ListBuffer
import org.apache.spark.sql.catalyst.expressions.GetItem
import org.apache.spark.sql.catalyst.expressions.BinaryArithmetic
import org.apache.spark.sql.catalyst.expressions.UnaryMinus
import org.apache.spark.sql.catalyst.expressions.Abs
import org.apache.spark.sql.catalyst.expressions.BinaryComparison
import org.apache.spark.sql.catalyst.expressions.In
import org.apache.spark.sql.catalyst.expressions.InSet
import org.apache.spark.sql.catalyst.expressions.GetItem
import org.apache.spark.sql.catalyst.checker.ExpressionRegistry
import org.apache.spark.sql.catalyst.expressions.Abs
import org.apache.spark.sql.catalyst.expressions.BinaryArithmetic
import org.apache.spark.sql.catalyst.expressions.BinaryComparison
import org.apache.spark.sql.catalyst.expressions.In
import org.apache.spark.sql.catalyst.expressions.InSet
import org.apache.spark.sql.catalyst.expressions.UnaryMinus
import org.apache.spark.sql.catalyst.checker.DataLabel
import org.apache.spark.sql.catalyst.checker.Insensitive
import org.apache.spark.sql.catalyst.checker.ConditionalLabel
import org.apache.spark.sql.catalyst.checker.ConstantLabel
import org.apache.spark.sql.catalyst.checker.Label
import org.apache.spark.sql.catalyst.checker.FunctionLabel

object AggregateType extends Enumeration {
  type AggregateType = Value
  val Direct_Aggregate, Derived_Aggregate, Invalid_Aggregate, Insensitive_Aggregate = Value
}

object CheckerUtil {
  def resolveAggregateType(expr: Expression, plan: Aggregate): AggregateType.Value = {
    expr match {
      case attr: Attribute => {
        val label = plan.childLabel(attr);
        if (!label.sensitive) {
          return AggregateType.Insensitive_Aggregate;
        } else {
          return checkLabelType(label);
        }
      }
      case cast: Cast => {
        return resolveAggregateType(cast.child, plan);
      }
      case literal: Literal => {
        if (plan.child.projectLabels.values.exists(_.sensitive) || plan.condLabels.exists(_.sensitive)) {
          return AggregateType.Direct_Aggregate;
        } else {
          return AggregateType.Insensitive_Aggregate;
        }
      }
      case alias: Alias => {
        return resolveAggregateType(alias.child, plan);
      }
      case _ => {
        val types = expr.children.map(resolveAggregateType(_, plan));
        if (types.exists(t => t == AggregateType.Invalid_Aggregate || t == AggregateType.Direct_Aggregate)) {
          return AggregateType.Invalid_Aggregate;
        } else if (types.exists(_ == AggregateType.Derived_Aggregate)) {
          return AggregateType.Derived_Aggregate;
        } else {
          return AggregateType.Insensitive_Aggregate;
        }
      }
    }
  }

  def checkLabelType(label: Label): AggregateType.Value = {
    label match {
      case data: DataLabel => AggregateType.Direct_Aggregate;
      case cons: ConstantLabel => AggregateType.Direct_Aggregate;
      case in: Insensitive => AggregateType.Direct_Aggregate;

      case cond: ConditionalLabel => AggregateType.Direct_Aggregate;

      case func: FunctionLabel => {
        val types = func.children.map(checkLabelType(_));
        if (types.exists(_ == AggregateType.Invalid_Aggregate)) {
          return AggregateType.Invalid_Aggregate;
        }
        func.transform match {
          case Func_Cast => {
            return checkLabelType(func.children(0));
          }
          case Func_Union | Func_Except | Func_Intersect => {
            if (types.forall(_ == AggregateType.Direct_Aggregate)) {
              return AggregateType.Direct_Aggregate;
            } else {
              return AggregateType.Derived_Aggregate;
            }
          }
          case Func_Sum | Func_Count | Func_Min | Func_Max | Func_Avg => {
            return AggregateType.Derived_Aggregate;
          }
          case _ => {
            if (types.exists(_ != AggregateType.Derived_Aggregate)) {
              return AggregateType.Invalid_Aggregate;
            } else {
              return AggregateType.Derived_Aggregate;
            }
          }
        }
      }
      case _ => null;
    }
  }

  def exists[T <: TreeNode[T]](node: TreeNode[T], clazz: Class[_ <: T]): Boolean = {
    if (clazz.isInstance(node)) {
      return true;
    }
    return node.children.exists(exists(_, clazz));
  }

  def collect(expr: Expression, clazz: Class[_ <: Expression]): Seq[Expression] = {
    if (clazz.isInstance(expr)) {
      expr.children.flatMap(collect(_, clazz));
    } else {
      List(expr);
    }
  }

  def collect(label: Label, transform: String): Seq[Label] = {
    label match {
      case func: FunctionLabel if (func.transform == transform) => {
        func.children.flatMap(collect(_, transform));
      }
      case _ => List(label);
    }
  }

  private val SupportedArithms = List(classOf[BinaryArithmetic], classOf[UnaryMinus], classOf[Abs]);

  private val SupportedPredicates = List(classOf[BinaryComparison], classOf[In], classOf[InSet]);

  def supportArithmetic(expr: Expression) = SupportedArithms.exists(_.isInstance(expr));

  def supportPredicate(expr: Expression) = SupportedPredicates.exists(_.isInstance(expr));

  def asType[T](value: Any, clazz: Class[T]): T = {
    if (value != null && clazz.isInstance(value)) {
      return clazz.cast(value);
    } else {
      return null.asInstanceOf[T];
    }

  }

}