package org.apache.spark.sql.catalyst.plans.logical;
/**
 * A logical plan node with single child.
 */
public abstract class UnaryNode extends org.apache.spark.sql.catalyst.plans.logical.LogicalPlan implements org.apache.spark.sql.catalyst.trees.UnaryNode<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
  public   UnaryNode () { throw new RuntimeException(); }
}
