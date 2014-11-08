package org.apache.spark.sql.hive.execution;
/**
 * :: DeveloperApi ::
 * Analyzes the given table in the current database to generate statistics, which will be
 * used in query optimizations.
 * <p>
 * Right now, it only supports Hive tables and it only updates the size of a Hive table
 * in the Hive metastore.
 */
public  class AnalyzeTable extends org.apache.spark.sql.execution.SparkPlan implements org.apache.spark.sql.execution.LeafNode, org.apache.spark.sql.execution.Command, scala.Product, scala.Serializable {
  public  java.lang.String tableName () { throw new RuntimeException(); }
  // not preceding
  public   AnalyzeTable (java.lang.String tableName) { throw new RuntimeException(); }
  public  org.apache.spark.sql.hive.HiveContext hiveContext () { throw new RuntimeException(); }
  public  scala.collection.Seq<scala.runtime.Nothing$> output () { throw new RuntimeException(); }
  protected  scala.collection.Seq<java.lang.Object> sideEffectResult () { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> execute () { throw new RuntimeException(); }
}
