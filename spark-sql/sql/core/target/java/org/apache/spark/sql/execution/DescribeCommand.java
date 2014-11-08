package org.apache.spark.sql.execution;
/**
 * :: DeveloperApi ::
 */
public  class DescribeCommand extends org.apache.spark.sql.execution.SparkPlan implements org.apache.spark.sql.execution.LeafNode, org.apache.spark.sql.execution.Command, scala.Product, scala.Serializable {
  public  org.apache.spark.sql.execution.SparkPlan child () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () { throw new RuntimeException(); }
  // not preceding
  public   DescribeCommand (org.apache.spark.sql.execution.SparkPlan child, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output, org.apache.spark.sql.SQLContext context) { throw new RuntimeException(); }
  protected  scala.collection.Seq<scala.Tuple3<java.lang.String, java.lang.String, java.lang.String>> sideEffectResult () { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> execute () { throw new RuntimeException(); }
}
