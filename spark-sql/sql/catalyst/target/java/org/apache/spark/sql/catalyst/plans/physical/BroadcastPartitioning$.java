package org.apache.spark.sql.catalyst.plans.physical;
// no position
public  class BroadcastPartitioning$ implements org.apache.spark.sql.catalyst.plans.physical.Partitioning, scala.Product, scala.Serializable {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final BroadcastPartitioning$ MODULE$ = null;
  public   BroadcastPartitioning$ () { throw new RuntimeException(); }
  public  int numPartitions () { throw new RuntimeException(); }
  public  boolean satisfies (org.apache.spark.sql.catalyst.plans.physical.Distribution required) { throw new RuntimeException(); }
  public  boolean compatibleWith (org.apache.spark.sql.catalyst.plans.physical.Partitioning other) { throw new RuntimeException(); }
}
