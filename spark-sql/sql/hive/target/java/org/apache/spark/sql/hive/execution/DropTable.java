package org.apache.spark.sql.hive.execution;
/**
 * :: DeveloperApi ::
 * Drops a table from the metastore and removes it if it is cached.
 */
public  class DropTable extends org.apache.spark.sql.execution.SparkPlan implements org.apache.spark.sql.execution.LeafNode, org.apache.spark.sql.execution.Command, scala.Product, scala.Serializable {
  public  java.lang.String tableName () { throw new RuntimeException(); }
  public  boolean ifExists () { throw new RuntimeException(); }
  // not preceding
  public   DropTable (java.lang.String tableName, boolean ifExists) { throw new RuntimeException(); }
  public  org.apache.spark.sql.hive.HiveContext hiveContext () { throw new RuntimeException(); }
  public  scala.collection.Seq<scala.runtime.Nothing$> output () { throw new RuntimeException(); }
  protected  scala.collection.Seq<java.lang.Object> sideEffectResult () { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> execute () { throw new RuntimeException(); }
}
