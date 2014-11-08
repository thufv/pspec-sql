package org.apache.spark.rdd;
private abstract class ZippedPartitionsBaseRDD<V extends java.lang.Object> extends org.apache.spark.rdd.RDD<V> {
  public  scala.collection.Seq<org.apache.spark.rdd.RDD<?>> rdds () { throw new RuntimeException(); }
  // not preceding
  public   ZippedPartitionsBaseRDD (org.apache.spark.SparkContext sc, scala.collection.Seq<org.apache.spark.rdd.RDD<?>> rdds, boolean preservesPartitioning, scala.reflect.ClassTag<V> evidence$1) { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.Partitioner> partitioner () { throw new RuntimeException(); }
  public  org.apache.spark.Partition[] getPartitions () { throw new RuntimeException(); }
  public  scala.collection.Seq<java.lang.String> getPreferredLocations (org.apache.spark.Partition s) { throw new RuntimeException(); }
  public  void clearDependencies () { throw new RuntimeException(); }
}
