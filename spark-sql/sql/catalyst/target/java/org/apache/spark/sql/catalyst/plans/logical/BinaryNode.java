package org.apache.spark.sql.catalyst.plans.logical;
/**
 * A logical plan node with a left and right child.
 */
public abstract class BinaryNode extends org.apache.spark.sql.catalyst.plans.logical.LogicalPlan implements org.apache.spark.sql.catalyst.trees.BinaryNode<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
  public   BinaryNode () { throw new RuntimeException(); }
}
