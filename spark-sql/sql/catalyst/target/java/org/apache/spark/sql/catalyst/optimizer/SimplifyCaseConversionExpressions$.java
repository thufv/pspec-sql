package org.apache.spark.sql.catalyst.optimizer;
// no position
/**
 * Removes the inner {@link CaseConversionExpression} that are unnecessary because
 * the inner conversion is overwritten by the outer one.
 */
public  class SimplifyCaseConversionExpressions$ extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final SimplifyCaseConversionExpressions$ MODULE$ = null;
  public   SimplifyCaseConversionExpressions$ () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
}
