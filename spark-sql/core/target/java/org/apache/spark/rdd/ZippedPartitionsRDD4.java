package org.apache.spark.rdd;
private  class ZippedPartitionsRDD4<A extends java.lang.Object, B extends java.lang.Object, C extends java.lang.Object, D extends java.lang.Object, V extends java.lang.Object> extends org.apache.spark.rdd.ZippedPartitionsBaseRDD<V> {
  public  org.apache.spark.rdd.RDD<A> rdd1 () { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<B> rdd2 () { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<C> rdd3 () { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<D> rdd4 () { throw new RuntimeException(); }
  // not preceding
  public   ZippedPartitionsRDD4 (org.apache.spark.SparkContext sc, scala.Function4<scala.collection.Iterator<A>, scala.collection.Iterator<B>, scala.collection.Iterator<C>, scala.collection.Iterator<D>, scala.collection.Iterator<V>> f, org.apache.spark.rdd.RDD<A> rdd1, org.apache.spark.rdd.RDD<B> rdd2, org.apache.spark.rdd.RDD<C> rdd3, org.apache.spark.rdd.RDD<D> rdd4, boolean preservesPartitioning, scala.reflect.ClassTag<A> evidence$9, scala.reflect.ClassTag<B> evidence$10, scala.reflect.ClassTag<C> evidence$11, scala.reflect.ClassTag<D> evidence$12, scala.reflect.ClassTag<V> evidence$13) { throw new RuntimeException(); }
  public  scala.collection.Iterator<V> compute (org.apache.spark.Partition s, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
  public  void clearDependencies () { throw new RuntimeException(); }
}
