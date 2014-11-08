package org.apache.spark.sql.catalyst.optimizer;
// no position
/**
 * Removes filters that can be evaluated trivially.  This is done either by eliding the filter for
 * cases where it will always evaluate to <code>true</code>, or substituting a dummy empty relation when the
 * filter will always evaluate to <code>false</code>.
 */
public  class SimplifyFilters extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
  static public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
}
