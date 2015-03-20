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

/**
 * enforce dp for a query logical plan
 * should be in the last phase of query checking
 */
class DPEnforcer {

  def enforce(plan: LogicalPlan) {
    plan match {
      case agg: Aggregate => {
        agg.aggregateExpressions.foreach(enforce(_));
      }
      case _ =>
    }
  }

  private def enforce(expression: Expression) {
    expression match {
      case alias: Alias => enforce(alias.child);
      case sum: Sum => enableDP(sum);
      case sum: SumDistinct => enableDP(sum);
      case count: Count => enableDP(count);
      case count: CountDistinct => enableDP(count);
      case avg: Average => enableDP(avg);
      case min: Min => enableDP(min);
      case max: Max => enableDP(max);
      case unary: UnaryExpression => enforce(unary.child);
      case binary: BinaryExpression => {
        enforce(binary.left);
        enforce(binary.right);
      }
      case _=>
    }
  }

  private def enableDP(agg: AggregateExpression) {
    agg.enableDP = true;
  }
}