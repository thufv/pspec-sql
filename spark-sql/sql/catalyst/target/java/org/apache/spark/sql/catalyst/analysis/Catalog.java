package org.apache.spark.sql.catalyst.analysis;
/**
 * An interface for looking up relations by name.  Used by an {@link Analyzer}.
 */
public abstract interface Catalog {
  public abstract  boolean caseSensitive () ;
  public abstract  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan lookupRelation (scala.Option<java.lang.String> databaseName, java.lang.String tableName, scala.Option<java.lang.String> alias) ;
  public abstract  void registerTable (scala.Option<java.lang.String> databaseName, java.lang.String tableName, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) ;
  public abstract  void unregisterTable (scala.Option<java.lang.String> databaseName, java.lang.String tableName) ;
  public abstract  void unregisterAllTables () ;
  protected  scala.Tuple2<scala.Option<java.lang.String>, java.lang.String> processDatabaseAndTableName (scala.Option<java.lang.String> databaseName, java.lang.String tableName) ;
  protected  scala.Tuple2<java.lang.String, java.lang.String> processDatabaseAndTableName (java.lang.String databaseName, java.lang.String tableName) ;
}
