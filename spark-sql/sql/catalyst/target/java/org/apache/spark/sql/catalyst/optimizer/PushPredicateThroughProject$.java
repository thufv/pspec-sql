package org.apache.spark.sql.catalyst.optimizer;
// no position
/**
 * Pushes {@link Filter} operators through {@link Project} operators, in-lining any {@link Alias Aliases}
 * that were defined in the projection.
 * <p>
 * This heuristic is valid assuming the expression evaluation cost is minimal.
 */
public  class PushPredicateThroughProject$ extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final PushPredicateThroughProject$ MODULE$ = null;
  public   PushPredicateThroughProject$ () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Expression replaceAlias (org.apache.spark.sql.catalyst.expressions.Expression condition, scala.collection.immutable.Map<org.apache.spark.sql.catalyst.expressions.Attribute, org.apache.spark.sql.catalyst.expressions.Expression> sourceAliases) { throw new RuntimeException(); }
}
