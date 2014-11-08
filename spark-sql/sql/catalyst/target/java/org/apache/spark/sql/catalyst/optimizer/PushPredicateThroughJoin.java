package org.apache.spark.sql.catalyst.optimizer;
// no position
/**
 * Pushes down {@link Filter} operators where the <code>condition</code> can be
 * evaluated using only the attributes of the left or right side of a join.  Other
 * {@link Filter} conditions are moved into the <code>condition</code> of the {@link Join}.
 * <p>
 * And also Pushes down the join filter, where the <code>condition</code> can be evaluated using only the 
 * attributes of the left or right side of sub query when applicable. 
 * <p>
 * Check https://cwiki.apache.org/confluence/display/Hive/OuterJoinBehavior for more details
 */
public  class PushPredicateThroughJoin extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> implements org.apache.spark.sql.catalyst.expressions.PredicateHelper {
  /**
   * Splits join condition expressions into three categories based on the attributes required
   * to evaluate them.
   * @return (canEvaluateInLeft, canEvaluateInRight, haveToEvaluateInBoth)
   */
  static private  scala.Tuple3<scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression>, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression>, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression>> split (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> condition, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan left, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan right) { throw new RuntimeException(); }
  static public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
}
