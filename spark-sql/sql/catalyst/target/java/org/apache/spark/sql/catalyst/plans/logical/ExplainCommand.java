package org.apache.spark.sql.catalyst.plans.logical;
/**
 * Returned by a parser when the users only wants to see what query plan would be executed, without
 * actually performing the execution.
 */
public  class ExplainCommand extends org.apache.spark.sql.catalyst.plans.logical.Command implements scala.Product, scala.Serializable {
  public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan () { throw new RuntimeException(); }
  public  boolean extended () { throw new RuntimeException(); }
  // not preceding
  public   ExplainCommand (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan, boolean extended) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.AttributeReference> output () { throw new RuntimeException(); }
}
