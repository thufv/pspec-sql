package org.apache.spark.rdd;
private  class ZippedWithIndexRDDPartition implements org.apache.spark.Partition, scala.Serializable {
  public  org.apache.spark.Partition prev () { throw new RuntimeException(); }
  public  long startIndex () { throw new RuntimeException(); }
  // not preceding
  public   ZippedWithIndexRDDPartition (org.apache.spark.Partition prev, long startIndex) { throw new RuntimeException(); }
  public  int index () { throw new RuntimeException(); }
}
