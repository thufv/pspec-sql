package org.apache.spark.sql.catalyst.analysis;
/**
 * A trait that can be mixed in with other Catalogs allowing specific tables to be overridden with
 * new logical plans.  This can be used to bind query result to virtual tables, or replace tables
 * with in-memory cached versions.  Note that the set of overrides is stored in memory and thus
 * lost when the JVM exits.
 */
public abstract interface OverrideCatalog extends org.apache.spark.sql.catalyst.analysis.Catalog {
  public  scala.collection.mutable.HashMap<scala.Tuple2<scala.Option<java.lang.String>, java.lang.String>, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> overrides () ;
  public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan lookupRelation (scala.Option<java.lang.String> databaseName, java.lang.String tableName, scala.Option<java.lang.String> alias) ;
  public  void registerTable (scala.Option<java.lang.String> databaseName, java.lang.String tableName, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) ;
  public  void unregisterTable (scala.Option<java.lang.String> databaseName, java.lang.String tableName) ;
  public  void unregisterAllTables () ;
}
