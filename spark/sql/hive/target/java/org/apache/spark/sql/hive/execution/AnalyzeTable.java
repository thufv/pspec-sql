package org.apache.spark.sql.hive.execution;
/**
 * Analyzes the given table in the current database to generate statistics, which will be
 * used in query optimizations.
 * <p>
 * Right now, it only supports Hive tables and it only updates the size of a Hive table
 * in the Hive metastore.
 */
public  class AnalyzeTable extends org.apache.spark.sql.catalyst.plans.logical.Command implements org.apache.spark.sql.execution.RunnableCommand, scala.Product, scala.Serializable {
  public  java.lang.String tableName () { throw new RuntimeException(); }
  // not preceding
  public   AnalyzeTable (java.lang.String tableName) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.Row> run (org.apache.spark.sql.SQLContext sqlContext) { throw new RuntimeException(); }
}
