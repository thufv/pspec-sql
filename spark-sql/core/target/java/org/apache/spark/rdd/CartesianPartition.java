package org.apache.spark.rdd;
private  class CartesianPartition implements org.apache.spark.Partition {
  public   CartesianPartition (int idx, org.apache.spark.rdd.RDD<?> rdd1, org.apache.spark.rdd.RDD<?> rdd2, int s1Index, int s2Index) { throw new RuntimeException(); }
  public  org.apache.spark.Partition s1 () { throw new RuntimeException(); }
  public  org.apache.spark.Partition s2 () { throw new RuntimeException(); }
  public  int index () { throw new RuntimeException(); }
  private  void writeObject (java.io.ObjectOutputStream oos) { throw new RuntimeException(); }
}
