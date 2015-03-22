package org.apache.spark.sql.catalyst.checker

import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.apache.spark.sql.catalyst.expressions.Expression
import org.apache.spark.sql.catalyst.expressions.Alias
import org.apache.spark.sql.catalyst.expressions.BinaryExpression
import org.apache.spark.sql.catalyst.expressions.UnaryExpression
import org.apache.spark.sql.catalyst.expressions.Sum
import org.apache.spark.sql.catalyst.expressions.Average
import org.apache.spark.sql.catalyst.expressions.SumDistinct
import org.apache.spark.sql.catalyst.expressions.CountDistinct
import org.apache.spark.sql.catalyst.expressions.Min
import org.apache.spark.sql.catalyst.expressions.Max
import org.apache.spark.sql.catalyst.expressions.Count
import org.apache.spark.sql.catalyst.expressions.AggregateExpression
import org.apache.spark.sql.catalyst.plans.logical.BinaryNode
import org.apache.spark.sql.catalyst.plans.logical.LeafNode
import org.apache.spark.sql.catalyst.plans.logical.Project
import org.apache.spark.sql.catalyst.plans.logical.Generate
import org.apache.spark.sql.catalyst.plans.logical.Join
import org.apache.spark.sql.catalyst.plans.logical.Union
import org.apache.spark.sql.catalyst.plans.logical.Intersect
import org.apache.spark.sql.catalyst.plans.logical.Except
import org.apache.spark.sql.catalyst.expressions.Attribute
import org.apache.spark.sql.catalyst.plans.logical.UnaryNode
import org.apache.spark.sql.catalyst.trees
import org.apache.spark.sql.catalyst.expressions.AttributeReference
import org.apache.spark.sql.catalyst.checker.LabelConstants._
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.apache.spark.sql.catalyst.expressions.Cast

object AggregateType extends Enumeration {
  type AggregateType = Value
  val Direct_Aggregate = Value("Direct")
  val Derived_Aggregate = Value("Derived")
  val Invalid_Aggregate = Value("Invalid")
  val Insensitive_Aggregate = Value("Insensitive")

}
/**
 * enforce dp for a query logical plan
 * should be in the last phase of query checking
 */
class DPEnforcer(val stat: TableStat, val budget: DPBudget) {

  private val msg = "no expression/derived attribute should appear in aggregate function.";

  def enforce(plan: LogicalPlan): Int = {
    plan match {
      case unary: UnaryNode => {
        val scale = enforce(unary.child);
        enforceUnary(unary, scale);
      }
      case binary: BinaryNode => {
        val scale1 = enforce(binary.left);
        val scale2 = enforce(binary.right);
        enforceBinary(binary, scale1, scale2);
      }
      case leaf: LeafNode => {
        enforceLeaf(leaf);
      }
      case _ => 1
    }
  }

  private def enforceUnary(unary: UnaryNode, scale: Int): Int = {
    unary match {
      case agg: Aggregate => {
        agg.aggregateExpressions.foreach(enforceExpr(_, agg, scale));
        return scale;
      }
      case generate: Generate => {
        //TODO
        return scale;
      }
      case _ => return scale;
    }

  }

  private def enforceBinary(binary: BinaryNode, scale1: Int, scale2: Int): Int = {
    binary match {
      case join: Join => {
        //TODO
        //find join key key1 and key2, the scale should be max(|key1|* scale1), max(|key2| * scale2), where |key| denotes the maximum number of records for each value
        1
      }
      case union: Union => {
        Math.max(scale1, scale2);
      }
      case intersect: Intersect => {
        Math.max(scale1, scale2);
      }
      case except: Except => {
        Math.max(scale1, scale2);
      }

    }
  }

  private def enforceLeaf(leaf: LeafNode): Int = {
    1
  }

  private def enforceExpr(expression: Expression, agg: Aggregate, scale: Int) {
    expression match {
      case alias: Alias => enforceExpr(alias.child, agg, scale);
      //estimate sensitivity based on range for each functions
      case sum: Sum => {
        checkDP(sum, agg, scale, (min, max) => max - min);
      }
      case sum: SumDistinct => {
        checkDP(sum, agg, scale, (min, max) => max - min);
      }
      case count: Count => {
        checkDP(count, agg, scale, (min, max) => 1);
      }
      case count: CountDistinct => {
        checkDP(count, agg, scale, (min, max) => 1);
      }
      case avg: Average => {
        checkDP(avg, agg, scale, (min, max) => max - min);
      }
      case min: Min => {
        checkDP(min, agg, scale, (min, max) => max - min);
      }
      case max: Max => {
        checkDP(max, agg, scale, (min, max) => max - min);
      }
      case _ => expression.children.foreach(enforceExpr(_, agg, scale));
    }
  }

  private def checkDP(agg: AggregateExpression, plan: Aggregate, scale: Int, func: (Double, Double) => Double) {
    val types = agg.children.map(checkAggType(_, plan));
    if (types.exists(_ == AggregateType.Invalid_Aggregate)) {
      throw new PrivacyException(msg);
    } else if (types.exists(_ == AggregateType.Direct_Aggregate)) {
      val range = resolveAttributeRange(agg.children(0), plan);
      if (range == null) {
        agg.sensitivity = 0;
      } else {
        agg.sensitivity = func(DPHelper.toDouble(range.low), DPHelper.toDouble(range.up));
      }
      //TODO
      budget.consume(0.1);
      agg.enableDP = true;

    }
  }

  private def checkAggType(expr: Expression, plan: Aggregate): AggregateType.Value = {
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
        return checkAggType(cast.child, plan);
      }
      case alias: Alias => {
        return checkAggType(alias.child, plan);
      }
      case unary: UnaryExpression => {
        val aggType = checkAggType(unary.child, plan);
        if (aggType == AggregateType.Invalid_Aggregate || aggType == AggregateType.Insensitive_Aggregate || aggType == AggregateType.Derived_Aggregate) {
          return aggType;
        } else {
          return AggregateType.Invalid_Aggregate;
        }
      }
      case binary: BinaryExpression => {
        val types = binary.children.map(checkAggType(_, plan));
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

  private def checkLabelType(label: Label): AggregateType.Value = {
    label match {
      case data: DataLabel => AggregateType.Direct_Aggregate;
      case cons: Constant => AggregateType.Direct_Aggregate;
      case in: Insensitive => AggregateType.Direct_Aggregate;
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
      case _ => throw new RuntimeException("should not reach here");
    }
  }

  private def resolveAttributeRange(expr: Expression, agg: Aggregate): Range = {
    expr match {
      case attr: AttributeReference => {
        val label = agg.childLabel(attr);
        resolveLabelRange(label);
      }
      case alias: Alias => {
        resolveAttributeRange(alias.child, agg);
      }
      case cast: Cast => {
        resolveAttributeRange(cast.child, agg);
      }
      case _ => throw new RuntimeException("should not reach here.");
    }
  }

  private def resolveLabelRange(label: Label): Range = {
    label match {
      case data: DataLabel => {
        stat.get(data.database, data.table, data.attr.name);
      }
      case func: Function => {
        func.udf match {
          case Func_Union => {
            resolveFuncRange(func, (r1: Range, r2: Range) => {
              if (r1 == null) {
                r2
              } else {
                r1.union(r2)
              }
            });
          }
          case Func_Intersect => {
            resolveFuncRange(func, (r1: Range, r2: Range) => {
              if (r1 == null) {
                r2
              } else {
                r1.intersect(r2)
              }
            });
          }
          case Func_Except => {
            resolveFuncRange(func, (r1: Range, r2: Range) => {
              if (r1 == null) {
                r2
              } else {
                r1.except(r2);
              }
            });
          }
        }
      }
      case _ => null;
    }
  }

  private def resolveFuncRange(label: Function, func: (Range, Range) => Range): Range = {
    val r1 = resolveLabelRange(label.children(0));
    val r2 = resolveLabelRange(label.children(1));
    func(r1, r2);
  }

}