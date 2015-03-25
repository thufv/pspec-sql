package org.apache.spark.sql.execution;
/**
 * :: DeveloperApi ::
 */
public  class Exchange extends org.apache.spark.sql.execution.SparkPlan implements org.apache.spark.sql.execution.UnaryNode, scala.Product, scala.Serializable {
  public  org.apache.spark.sql.catalyst.plans.physical.Partitioning newPartitioning () { throw new RuntimeException(); }
  public  org.apache.spark.sql.execution.SparkPlan child () { throw new RuntimeException(); }
  // not preceding
  public   Exchange (org.apache.spark.sql.catalyst.plans.physical.Partitioning newPartitioning, org.apache.spark.sql.execution.SparkPlan child) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.plans.physical.Partitioning outputPartitioning () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () { throw new RuntimeException(); }
  /** We must copy rows when sort based shuffle is on */
  protected  boolean sortBasedShuffleOn () { throw new RuntimeException(); }
  private  int bypassMergeThreshold () { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<org.apache.spark.sql.Row> execute () { throw new RuntimeException(); }
}
