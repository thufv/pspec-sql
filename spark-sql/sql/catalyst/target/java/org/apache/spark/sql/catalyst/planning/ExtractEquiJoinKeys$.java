package org.apache.spark.sql.catalyst.planning;
// no position
/**
 * A pattern that finds joins with equality conditions that can be evaluated using equi-join.
 */
public  class ExtractEquiJoinKeys$ implements org.apache.spark.Logging, org.apache.spark.sql.catalyst.expressions.PredicateHelper {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final ExtractEquiJoinKeys$ MODULE$ = null;
  public   ExtractEquiJoinKeys$ () { throw new RuntimeException(); }
  public  scala.Option<scala.Tuple6<org.apache.spark.sql.catalyst.plans.JoinType, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression>, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression>, scala.Option<org.apache.spark.sql.catalyst.expressions.Expression>, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan>> unapply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
}
