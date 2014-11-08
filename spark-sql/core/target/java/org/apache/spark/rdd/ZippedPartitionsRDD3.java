package org.apache.spark.rdd;
private  class ZippedPartitionsRDD3<A extends java.lang.Object, B extends java.lang.Object, C extends java.lang.Object, V extends java.lang.Object> extends org.apache.spark.rdd.ZippedPartitionsBaseRDD<V> {
  public  org.apache.spark.rdd.RDD<A> rdd1 () { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<B> rdd2 () { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<C> rdd3 () { throw new RuntimeException(); }
  // not preceding
  public   ZippedPartitionsRDD3 (org.apache.spark.SparkContext sc, scala.Function3<scala.collection.Iterator<A>, scala.collection.Iterator<B>, scala.collection.Iterator<C>, scala.collection.Iterator<V>> f, org.apache.spark.rdd.RDD<A> rdd1, org.apache.spark.rdd.RDD<B> rdd2, org.apache.spark.rdd.RDD<C> rdd3, boolean preservesPartitioning, scala.reflect.ClassTag<A> evidence$5, scala.reflect.ClassTag<B> evidence$6, scala.reflect.ClassTag<C> evidence$7, scala.reflect.ClassTag<V> evidence$8) { throw new RuntimeException(); }
  public  scala.collection.Iterator<V> compute (org.apache.spark.Partition s, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
  public  void clearDependencies () { throw new RuntimeException(); }
}
