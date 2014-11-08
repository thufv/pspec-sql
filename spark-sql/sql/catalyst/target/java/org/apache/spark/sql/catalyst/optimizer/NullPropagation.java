package org.apache.spark.sql.catalyst.optimizer;
// no position
/**
 * Replaces {@link Expression Expressions} that can be statically evaluated with
 * equivalent {@link Literal} values. This rule is more specific with
 * Null value propagation from bottom to top of the expression tree.
 */
public  class NullPropagation extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
  static public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
}
