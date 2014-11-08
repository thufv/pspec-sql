package org.apache.spark.sql.catalyst.optimizer;
// no position
/**
 * Attempts to eliminate the reading of unneeded columns from the query plan using the following
 * transformations:
 * <p>
 *  - Inserting Projections beneath the following operators:
 *   - Aggregate
 *   - Project <- Join
 *   - LeftSemiJoin
 *  - Collapse adjacent projections, performing alias substitution.
 */
public  class ColumnPruning$ extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final ColumnPruning$ MODULE$ = null;
  public   ColumnPruning$ () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  /** Applies a projection only when the child is producing unnecessary attributes */
  private  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan prunedChild (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan c, org.apache.spark.sql.catalyst.expressions.AttributeSet allReferences) { throw new RuntimeException(); }
}
