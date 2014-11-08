package org.apache.spark.rdd;
private  class NarrowCoGroupSplitDep implements org.apache.spark.rdd.CoGroupSplitDep, scala.Product, scala.Serializable {
  public  Object rdd () { throw new RuntimeException(); }
  public  int splitIndex () { throw new RuntimeException(); }
  public  org.apache.spark.Partition split () { throw new RuntimeException(); }
  // not preceding
  public   NarrowCoGroupSplitDep (org.apache.spark.rdd.RDD<?> rdd, int splitIndex, org.apache.spark.Partition split) { throw new RuntimeException(); }
  private  void writeObject (java.io.ObjectOutputStream oos) { throw new RuntimeException(); }
}
