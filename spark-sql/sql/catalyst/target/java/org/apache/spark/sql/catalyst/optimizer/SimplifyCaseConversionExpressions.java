package org.apache.spark.sql.catalyst.optimizer;
// no position
/**
 * Removes the inner {@link CaseConversionExpression} that are unnecessary because
 * the inner conversion is overwritten by the outer one.
 */
public  class SimplifyCaseConversionExpressions extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
  static public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
}
