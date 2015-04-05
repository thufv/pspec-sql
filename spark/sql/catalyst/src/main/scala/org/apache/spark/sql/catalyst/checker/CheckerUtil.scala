package org.apache.spark.sql.catalyst.checker

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

  def resolveAttribute(expr: Expression): Attribute = {
    expr match {
      case attr: Attribute => attr;
      case alias: Alias => {
        resolveAttribute(alias.child);
      }
      case cast: Cast => {
        resolveAttribute(cast.child);
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

  def collect(label: Label, transform: String): Seq[Label] = {
    label match {
      case func: Function if (func.transform == transform) => {
        func.children.flatMap(collect(_, transform));
      }
      case _ => List(label);
    }
  }

  def resolveType(t: BaseType, func: Function): Seq[BaseType] = {
    val transform = func.transform;
    if (ignorable(transform)) {
      return Seq(t);
    }
    t match {
      case comp: CompositeType => {
        val subType = comp.getExtractOperation(transform);
        if (subType != null) {
          return Seq(subType.getType());
        } else {
          return comp.toPrimitives();
        }
      }
      case struct: StructType => {
        if (isGetField(transform)) {
          val field = getSubType(transform);
          val subType = struct.getField(field);
          if (subType != null) {
            Seq(subType.getType());
          } else {
            Nil;
          }
        } else {
          return struct.toPrimitives();
        }
      }
      case array: ArrayType => {
        if (isGetItem(transform)) {
          return Seq(array.getItemType());
        } else {
          return array.toPrimitives();
        }
      }
      case map: MapType => {
        if (isGetEntry(transform)) {
          val key = getSubType(transform);
          val subType = map.getEntry(key);
          if (subType != null) {
            Seq(subType.getType);
          } else {
            Nil;
          }
        } else {
          return map.toPrimitives();
        }
      }
      case _ => Seq(t);
    }
  }

}