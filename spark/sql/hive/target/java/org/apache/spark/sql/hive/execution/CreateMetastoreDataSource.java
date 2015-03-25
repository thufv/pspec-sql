package org.apache.spark.sql.hive.execution;
public  class CreateMetastoreDataSource extends org.apache.spark.sql.catalyst.plans.logical.Command implements org.apache.spark.sql.execution.RunnableCommand, scala.Product, scala.Serializable {
  public  java.lang.String tableName () { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.sql.types.StructType> userSpecifiedSchema () { throw new RuntimeException(); }
  public  java.lang.String provider () { throw new RuntimeException(); }
  public  scala.collection.immutable.Map<java.lang.String, java.lang.String> options () { throw new RuntimeException(); }
  public  boolean allowExisting () { throw new RuntimeException(); }
  public  boolean managedIfNoPath () { throw new RuntimeException(); }
  // not preceding
  public   CreateMetastoreDataSource (java.lang.String tableName, scala.Option<org.apache.spark.sql.types.StructType> userSpecifiedSchema, java.lang.String provider, scala.collection.immutable.Map<java.lang.String, java.lang.String> options, boolean allowExisting, boolean managedIfNoPath) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.Row> run (org.apache.spark.sql.SQLContext sqlContext) { throw new RuntimeException(); }
}
