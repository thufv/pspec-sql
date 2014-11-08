package org.apache.spark.sql.execution;
/**
 * :: DeveloperApi ::
 */
public  class CacheCommand extends org.apache.spark.sql.execution.SparkPlan implements org.apache.spark.sql.execution.LeafNode, org.apache.spark.sql.execution.Command, scala.Product, scala.Serializable {
  public  java.lang.String tableName () { throw new RuntimeException(); }
  public  boolean doCache () { throw new RuntimeException(); }
  // not preceding
  public   CacheCommand (java.lang.String tableName, boolean doCache, org.apache.spark.sql.SQLContext context) { throw new RuntimeException(); }
  protected  scala.collection.Seq<java.lang.Object> sideEffectResult () { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> execute () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () { throw new RuntimeException(); }
}
