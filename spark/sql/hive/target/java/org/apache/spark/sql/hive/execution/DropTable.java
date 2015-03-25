package org.apache.spark.sql.hive.execution;
/**
 * Drops a table from the metastore and removes it if it is cached.
 */
public  class DropTable extends org.apache.spark.sql.catalyst.plans.logical.Command implements org.apache.spark.sql.execution.RunnableCommand, scala.Product, scala.Serializable {
  public  java.lang.String tableName () { throw new RuntimeException(); }
  public  boolean ifExists () { throw new RuntimeException(); }
  // not preceding
  public   DropTable (java.lang.String tableName, boolean ifExists) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.Row> run (org.apache.spark.sql.SQLContext sqlContext) { throw new RuntimeException(); }
}
