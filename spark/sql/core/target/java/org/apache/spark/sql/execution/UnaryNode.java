package org.apache.spark.sql.execution;
public  interface UnaryNode extends org.apache.spark.sql.catalyst.trees.UnaryNode<org.apache.spark.sql.execution.SparkPlan> {
  public  org.apache.spark.sql.catalyst.plans.physical.Partitioning outputPartitioning () ;
}
