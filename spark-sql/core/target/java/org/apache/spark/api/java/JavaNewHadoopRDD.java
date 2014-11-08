package org.apache.spark.api.java;
public  class JavaNewHadoopRDD<K extends java.lang.Object, V extends java.lang.Object> extends org.apache.spark.api.java.JavaPairRDD<K, V> {
  public  scala.reflect.ClassTag<K> kClassTag () { throw new RuntimeException(); }
  public  scala.reflect.ClassTag<V> vClassTag () { throw new RuntimeException(); }
  // not preceding
  public   JavaNewHadoopRDD (org.apache.spark.rdd.NewHadoopRDD<K, V> rdd, scala.reflect.ClassTag<K> kClassTag, scala.reflect.ClassTag<V> vClassTag) { throw new RuntimeException(); }
  /** Maps over a partition, providing the InputSplit that was used as the base of the partition. */
  public <R extends java.lang.Object> org.apache.spark.api.java.JavaRDD<R> mapPartitionsWithInputSplit (org.apache.spark.api.java.function.Function2<org.apache.hadoop.mapreduce.InputSplit, java.util.Iterator<scala.Tuple2<K, V>>, java.util.Iterator<R>> f, boolean preservesPartitioning) { throw new RuntimeException(); }
}
