package org.apache.spark.sql.catalyst.analysis;
// no position
/**
 * A trivial catalog that returns an error when a relation is requested.  Used for testing when all
 * relations are already filled in and the analyser needs only to resolve attribute references.
 */
public  class EmptyCatalog$ implements org.apache.spark.sql.catalyst.analysis.Catalog {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final EmptyCatalog$ MODULE$ = null;
  public   EmptyCatalog$ () { throw new RuntimeException(); }
  public  boolean caseSensitive () { throw new RuntimeException(); }
  public  scala.Nothing lookupRelation (scala.Option<java.lang.String> databaseName, java.lang.String tableName, scala.Option<java.lang.String> alias) { throw new RuntimeException(); }
  public  void registerTable (scala.Option<java.lang.String> databaseName, java.lang.String tableName, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  public  void unregisterTable (scala.Option<java.lang.String> databaseName, java.lang.String tableName) { throw new RuntimeException(); }
  public  void unregisterAllTables () { throw new RuntimeException(); }
}
