package org.apache.spark.sql.sources;
/**
 * A rule to do various checks before inserting into or writing to a data source table.
 */
public  class PreWriteCheck implements scala.Function1<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan, scala.runtime.BoxedUnit>, scala.Product, scala.Serializable {
  public  org.apache.spark.sql.catalyst.analysis.Catalog catalog () { throw new RuntimeException(); }
  // not preceding
  public   PreWriteCheck (org.apache.spark.sql.catalyst.analysis.Catalog catalog) { throw new RuntimeException(); }
  public  scala.runtime.Nothing$ failAnalysis (java.lang.String msg) { throw new RuntimeException(); }
  public  void apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
}
