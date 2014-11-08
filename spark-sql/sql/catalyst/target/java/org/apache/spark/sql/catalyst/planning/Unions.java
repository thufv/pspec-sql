package org.apache.spark.sql.catalyst.planning;
// no position
/**
 * A pattern that collects all adjacent unions and returns their children as a Seq.
 */
public  class Unions {
  static public  scala.Option<scala.collection.Seq<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan>> unapply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  static private  scala.collection.Seq<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> collectUnionChildren (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
}
