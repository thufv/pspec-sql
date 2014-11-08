package org.apache.spark.api.java;
// no position
public  class JavaPairRDD$ implements scala.Serializable {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final JavaPairRDD$ MODULE$ = null;
  public   JavaPairRDD$ () { throw new RuntimeException(); }
  private <K extends java.lang.Object, T extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, java.lang.Iterable<T>>> groupByResultToJava (org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.collection.Iterable<T>>> rdd, scala.reflect.ClassTag<K> evidence$1) { throw new RuntimeException(); }
  private <K extends java.lang.Object, V extends java.lang.Object, W extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple2<java.lang.Iterable<V>, java.lang.Iterable<W>>>> cogroupResultToJava (org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple2<scala.collection.Iterable<V>, scala.collection.Iterable<W>>>> rdd, scala.reflect.ClassTag<K> evidence$2) { throw new RuntimeException(); }
  private <K extends java.lang.Object, V extends java.lang.Object, W1 extends java.lang.Object, W2 extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple3<java.lang.Iterable<V>, java.lang.Iterable<W1>, java.lang.Iterable<W2>>>> cogroupResult2ToJava (org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple3<scala.collection.Iterable<V>, scala.collection.Iterable<W1>, scala.collection.Iterable<W2>>>> rdd, scala.reflect.ClassTag<K> evidence$3) { throw new RuntimeException(); }
  private <K extends java.lang.Object, V extends java.lang.Object, W1 extends java.lang.Object, W2 extends java.lang.Object, W3 extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple4<java.lang.Iterable<V>, java.lang.Iterable<W1>, java.lang.Iterable<W2>, java.lang.Iterable<W3>>>> cogroupResult3ToJava (org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple4<scala.collection.Iterable<V>, scala.collection.Iterable<W1>, scala.collection.Iterable<W2>, scala.collection.Iterable<W3>>>> rdd, scala.reflect.ClassTag<K> evidence$4) { throw new RuntimeException(); }
  public <K extends java.lang.Object, V extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, V> fromRDD (org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> rdd, scala.reflect.ClassTag<K> evidence$5, scala.reflect.ClassTag<V> evidence$6) { throw new RuntimeException(); }
  public <K extends java.lang.Object, V extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> toRDD (org.apache.spark.api.java.JavaPairRDD<K, V> rdd) { throw new RuntimeException(); }
  private <T1 extends java.lang.Object, T2 extends java.lang.Object, R extends java.lang.Object> scala.Function2<T1, T2, R> toScalaFunction2 (org.apache.spark.api.java.function.Function2<T1, T2, R> fun) { throw new RuntimeException(); }
  private <T extends java.lang.Object, R extends java.lang.Object> scala.Function1<T, R> toScalaFunction (org.apache.spark.api.java.function.Function<T, R> fun) { throw new RuntimeException(); }
  private <A extends java.lang.Object, B extends java.lang.Object, C extends java.lang.Object> scala.Function1<A, scala.Tuple2<B, C>> pairFunToScalaFun (org.apache.spark.api.java.function.PairFunction<A, B, C> x) { throw new RuntimeException(); }
  /** Convert a JavaRDD of key-value pairs to JavaPairRDD. */
  public <K extends java.lang.Object, V extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, V> fromJavaRDD (org.apache.spark.api.java.JavaRDD<scala.Tuple2<K, V>> rdd) { throw new RuntimeException(); }
}
