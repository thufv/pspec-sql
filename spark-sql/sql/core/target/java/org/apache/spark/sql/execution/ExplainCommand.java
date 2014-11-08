package org.apache.spark.sql.execution;
/**
 * An explain command for users to see how a command will be executed.
 * <p>
 * Note that this command takes in a logical plan, runs the optimizer on the logical plan
 * (but do NOT actually execute it).
 * <p>
 * :: DeveloperApi ::
 */
public  class ExplainCommand extends org.apache.spark.sql.execution.SparkPlan implements org.apache.spark.sql.execution.LeafNode, org.apache.spark.sql.execution.Command, scala.Product, scala.Serializable {
  public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan logicalPlan () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () { throw new RuntimeException(); }
  public  boolean extended () { throw new RuntimeException(); }
  // not preceding
  public   ExplainCommand (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan logicalPlan, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output, boolean extended, org.apache.spark.sql.SQLContext context) { throw new RuntimeException(); }
  protected  scala.collection.Seq<java.lang.String> sideEffectResult () { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> execute () { throw new RuntimeException(); }
  public  scala.collection.immutable.List<org.apache.spark.sql.SQLContext> otherCopyArgs () { throw new RuntimeException(); }
}
