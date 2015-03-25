package org.apache.spark.sql.sources;
public  class RefreshTable extends org.apache.spark.sql.catalyst.plans.logical.Command implements org.apache.spark.sql.execution.RunnableCommand, scala.Product, scala.Serializable {
  public  java.lang.String databaseName () { throw new RuntimeException(); }
  public  java.lang.String tableName () { throw new RuntimeException(); }
  // not preceding
  public   RefreshTable (java.lang.String databaseName, java.lang.String tableName) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.Row> run (org.apache.spark.sql.SQLContext sqlContext) { throw new RuntimeException(); }
}
