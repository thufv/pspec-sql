package org.apache.spark;
/** RDD that has large serialized size. */
public  class FatRDD extends org.apache.spark.rdd.RDD<java.lang.Object> {
  public   FatRDD (org.apache.spark.rdd.RDD<java.lang.Object> parent) { throw new RuntimeException(); }
  public  byte[] bigData () { throw new RuntimeException(); }
  protected  org.apache.spark.Partition[] getPartitions () { throw new RuntimeException(); }
  public  scala.collection.Iterator<java.lang.Object> compute (org.apache.spark.Partition split, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
}
