package org.apache.spark.sql.hive.execution;
/**
 * :: DeveloperApi ::
 */
public  class NativeCommand extends org.apache.spark.sql.execution.SparkPlan implements org.apache.spark.sql.execution.LeafNode, org.apache.spark.sql.execution.Command, scala.Product, scala.Serializable {
  public  java.lang.String sql () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () { throw new RuntimeException(); }
  // not preceding
  public   NativeCommand (java.lang.String sql, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output, org.apache.spark.sql.hive.HiveContext context) { throw new RuntimeException(); }
  protected  scala.collection.Seq<java.lang.String> sideEffectResult () { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> execute () { throw new RuntimeException(); }
  public  scala.collection.immutable.List<org.apache.spark.sql.hive.HiveContext> otherCopyArgs () { throw new RuntimeException(); }
}
