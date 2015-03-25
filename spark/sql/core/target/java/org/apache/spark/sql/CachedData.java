package org.apache.spark.sql;
/** Holds a cached logical plan and its data */
public  class CachedData implements scala.Product, scala.Serializable {
  public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan () { throw new RuntimeException(); }
  public  org.apache.spark.sql.columnar.InMemoryRelation cachedRepresentation () { throw new RuntimeException(); }
  // not preceding
  public   CachedData (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan, org.apache.spark.sql.columnar.InMemoryRelation cachedRepresentation) { throw new RuntimeException(); }
}
