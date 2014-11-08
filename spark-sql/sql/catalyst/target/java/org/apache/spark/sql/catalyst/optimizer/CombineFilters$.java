package org.apache.spark.sql.catalyst.optimizer;
// no position
/**
 * Combines two adjacent {@link Filter} operators into one, merging the
 * conditions into one conjunctive predicate.
 */
public  class CombineFilters$ extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final CombineFilters$ MODULE$ = null;
  public   CombineFilters$ () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
}
