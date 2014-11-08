package org.apache.spark.rdd;
public  class UnionRDD<T extends java.lang.Object> extends org.apache.spark.rdd.RDD<T> {
  public  scala.collection.Seq<org.apache.spark.rdd.RDD<T>> rdds () { throw new RuntimeException(); }
  // not preceding
  public   UnionRDD (org.apache.spark.SparkContext sc, scala.collection.Seq<org.apache.spark.rdd.RDD<T>> rdds, scala.reflect.ClassTag<T> evidence$2) { throw new RuntimeException(); }
  public  org.apache.spark.Partition[] getPartitions () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.Dependency<?>> getDependencies () { throw new RuntimeException(); }
  public  scala.collection.Iterator<T> compute (org.apache.spark.Partition s, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
  public  scala.collection.Seq<java.lang.String> getPreferredLocations (org.apache.spark.Partition s) { throw new RuntimeException(); }
  public  void clearDependencies () { throw new RuntimeException(); }
}
