package org.apache.spark.sql;
/**
 * An internal interface defining the RDD-like methods for {@link DataFrame}.
 * Please use {@link DataFrame} directly, and do NOT use this.
 */
public  interface RDDApi<T extends java.lang.Object> {
  public  org.apache.spark.sql.RDDApi<T> cache () ;
  public  org.apache.spark.sql.RDDApi<T> persist () ;
  public  org.apache.spark.sql.RDDApi<T> persist (org.apache.spark.storage.StorageLevel newLevel) ;
  public  org.apache.spark.sql.RDDApi<T> unpersist () ;
  public  org.apache.spark.sql.RDDApi<T> unpersist (boolean blocking) ;
  public <R extends java.lang.Object> org.apache.spark.rdd.RDD<R> map (scala.Function1<T, R> f, scala.reflect.ClassTag<R> evidence$1) ;
  public <R extends java.lang.Object> org.apache.spark.rdd.RDD<R> flatMap (scala.Function1<T, scala.collection.TraversableOnce<R>> f, scala.reflect.ClassTag<R> evidence$2) ;
  public <R extends java.lang.Object> org.apache.spark.rdd.RDD<R> mapPartitions (scala.Function1<scala.collection.Iterator<T>, scala.collection.Iterator<R>> f, scala.reflect.ClassTag<R> evidence$3) ;
  public  void foreach (scala.Function1<T, scala.runtime.BoxedUnit> f) ;
  public  void foreachPartition (scala.Function1<scala.collection.Iterator<T>, scala.runtime.BoxedUnit> f) ;
  public  java.lang.Object take (int n) ;
  public  java.lang.Object collect () ;
  public  java.util.List<T> collectAsList () ;
  public  long count () ;
  public  T first () ;
  public  org.apache.spark.sql.DataFrame repartition (int numPartitions) ;
  public  org.apache.spark.sql.DataFrame distinct () ;
}
