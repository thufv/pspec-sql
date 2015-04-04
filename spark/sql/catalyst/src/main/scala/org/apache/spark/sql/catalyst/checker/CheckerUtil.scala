package org.apache.spark.sql.catalyst.checker

import org.apache.spark.sql.catalyst.checker.LabelConstants.Func_Avg
import org.apache.spark.sql.catalyst.checker.LabelConstants.Func_Cast
import org.apache.spark.sql.catalyst.checker.LabelConstants.Func_Count
import org.apache.spark.sql.catalyst.checker.LabelConstants.Func_Except
import org.apache.spark.sql.catalyst.checker.LabelConstants.Func_Intersect
import org.apache.spark.sql.catalyst.checker.LabelConstants.Func_Max
import org.apache.spark.sql.catalyst.checker.LabelConstants.Func_Min
import org.apache.spark.sql.catalyst.checker.LabelConstants.Func_Sum
import org.apache.spark.sql.catalyst.checker.LabelConstants.Func_Union
import org.apache.spark.sql.catalyst.expressions.Alias
import org.apache.spark.sql.catalyst.expressions.Attribute
import org.apache.spark.sql.catalyst.expressions.Cast
import org.apache.spark.sql.catalyst.expressions.Expression
import org.apache.spark.sql.catalyst.expressions.Literal
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.apache.spark.sql.catalyst.trees.TreeNode

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
      case cons: Constant => AggregateType.Direct_Aggregate;
      case in: Insensitive => AggregateType.Direct_Aggregate;

      case cond: ConditionalLabel => AggregateType.Direct_Aggregate;

      case func: Function => {
        val types = func.children.map(checkLabelType(_));
        if (types.exists(_ == AggregateType.Invalid_Aggregate)) {
          return AggregateType.Invalid_Aggregate;
        }
        func.udf match {
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

  def resolveAttribute(expr: Expression): Attribute = {
    expr match {
      case attr: Attribute => attr;
      case alias: Alias => {
        val child = alias.child;
        child match {
          case cast: Cast => resolveAttribute(expr);
          case _ => alias.toAttribute;
        }
      }
      case _ => expr.asInstanceOf[Attribute];
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

  def collect(label: Label, udf: String): Seq[Label] = {
    label match {
      case func: Function if (func.udf == udf) => {
        func.children.flatMap(collect(_, udf));
      }
      case _ => List(label);
    }
  }

}