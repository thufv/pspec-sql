package org.apache.spark.sql.catalyst.plans.physical;
public  class UnknownPartitioning implements org.apache.spark.sql.catalyst.plans.physical.Partitioning, scala.Product, scala.Serializable {
  public  int numPartitions () { throw new RuntimeException(); }
  // not preceding
  public   UnknownPartitioning (int numPartitions) { throw new RuntimeException(); }
  public  boolean satisfies (org.apache.spark.sql.catalyst.plans.physical.Distribution required) { throw new RuntimeException(); }
  public  boolean compatibleWith (org.apache.spark.sql.catalyst.plans.physical.Partitioning other) { throw new RuntimeException(); }
}
