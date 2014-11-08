package org.apache.spark.sql.catalyst.analysis;
// no position
/**
 * Removes {@link catalyst.plans.logical.Subquery Subquery} operators from the plan.  Subqueries are
 * only required to provide scoping information for attributes and can be removed once analysis is
 * complete.  Similarly, this node also removes
 * {@link catalyst.plans.logical.LowerCaseSchema LowerCaseSchema} operators.
 */
public  class EliminateAnalysisOperators extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
  static public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
}
