package org.apache.spark.sql.catalyst.analysis;
public  class SimpleCatalog implements org.apache.spark.sql.catalyst.analysis.Catalog {
  public  boolean caseSensitive () { throw new RuntimeException(); }
  // not preceding
  public   SimpleCatalog (boolean caseSensitive) { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<java.lang.String, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> tables () { throw new RuntimeException(); }
  public  void registerTable (scala.Option<java.lang.String> databaseName, java.lang.String tableName, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  public  void unregisterTable (scala.Option<java.lang.String> databaseName, java.lang.String tableName) { throw new RuntimeException(); }
  public  void unregisterAllTables () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan lookupRelation (scala.Option<java.lang.String> databaseName, java.lang.String tableName, scala.Option<java.lang.String> alias) { throw new RuntimeException(); }
}
