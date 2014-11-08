package org.apache.spark.sql.catalyst.optimizer;
// no position
/**
 * Simplifies LIKE expressions that do not need full regular expressions to evaluate the condition.
 * For example, when the expression is just checking to see if a string starts with a given
 * pattern.
 */
public  class LikeSimplification$ extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final LikeSimplification$ MODULE$ = null;
  public   LikeSimplification$ () { throw new RuntimeException(); }
  public  scala.util.matching.Regex startsWith () { throw new RuntimeException(); }
  public  scala.util.matching.Regex endsWith () { throw new RuntimeException(); }
  public  scala.util.matching.Regex contains () { throw new RuntimeException(); }
  public  scala.util.matching.Regex equalTo () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
}
