package org.apache.spark;
/** RDD partition that has large serialized size. */
public  class FatPartition implements org.apache.spark.Partition {
  public  org.apache.spark.Partition partition () { throw new RuntimeException(); }
  // not preceding
  public   FatPartition (org.apache.spark.Partition partition) { throw new RuntimeException(); }
  public  byte[] bigData () { throw new RuntimeException(); }
  public  int index () { throw new RuntimeException(); }
}
