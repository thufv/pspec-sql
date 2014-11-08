package org.apache.spark.sql.catalyst.plans.logical;
/**
 * Returned for the "CACHE TABLE tableName" and "UNCACHE TABLE tableName" command.
 */
public  class CacheCommand extends org.apache.spark.sql.catalyst.plans.logical.Command implements scala.Product, scala.Serializable {
  public  java.lang.String tableName () { throw new RuntimeException(); }
  public  boolean doCache () { throw new RuntimeException(); }
  // not preceding
  public   CacheCommand (java.lang.String tableName, boolean doCache) { throw new RuntimeException(); }
}
