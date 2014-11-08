package org.apache.spark.sql.catalyst.expressions;
/**
 * An {@link AggregateExpression} that can be partially computed without seeing all relevant tuples.
 * These partial evaluations can then be combined to compute the actual answer.
 */
public abstract class PartialAggregate extends org.apache.spark.sql.catalyst.expressions.AggregateExpression {
  public   PartialAggregate () { throw new RuntimeException(); }
  /**
   * Returns a {@link SplitEvaluation} that computes this aggregation using partial aggregation.
   */
  public abstract  org.apache.spark.sql.catalyst.expressions.SplitEvaluation asPartial () ;
}
