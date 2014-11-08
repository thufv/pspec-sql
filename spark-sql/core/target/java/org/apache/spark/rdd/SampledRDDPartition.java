package org.apache.spark.rdd;
private  class SampledRDDPartition implements org.apache.spark.Partition, scala.Serializable {
  public  org.apache.spark.Partition prev () { throw new RuntimeException(); }
  public  int seed () { throw new RuntimeException(); }
  // not preceding
  public   SampledRDDPartition (org.apache.spark.Partition prev, int seed) { throw new RuntimeException(); }
  public  int index () { throw new RuntimeException(); }
}
