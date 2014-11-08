package org.apache.spark.sql.catalyst.analysis;
// no position
/**
 * A trivial catalog that returns an error when a relation is requested.  Used for testing when all
 * relations are already filled in and the analyser needs only to resolve attribute references.
 */
public  class EmptyCatalog implements org.apache.spark.sql.catalyst.analysis.Catalog {
  static public  boolean caseSensitive () { throw new RuntimeException(); }
  static public  scala.Nothing lookupRelation (scala.Option<java.lang.String> databaseName, java.lang.String tableName, scala.Option<java.lang.String> alias) { throw new RuntimeException(); }
  static public  void registerTable (scala.Option<java.lang.String> databaseName, java.lang.String tableName, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  static public  void unregisterTable (scala.Option<java.lang.String> databaseName, java.lang.String tableName) { throw new RuntimeException(); }
  static public  void unregisterAllTables () { throw new RuntimeException(); }
}
