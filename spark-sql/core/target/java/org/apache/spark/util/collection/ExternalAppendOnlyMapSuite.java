package org.apache.spark.util.collection;
public  class ExternalAppendOnlyMapSuite extends org.scalatest.FunSuite implements org.apache.spark.LocalSparkContext {
  public   ExternalAppendOnlyMapSuite () { throw new RuntimeException(); }
  private  scala.collection.Seq<java.lang.String> allCompressionCodecs () { throw new RuntimeException(); }
  private <T extends java.lang.Object> scala.collection.mutable.ArrayBuffer<T> createCombiner (T i) { throw new RuntimeException(); }
  private <T extends java.lang.Object> scala.collection.mutable.ArrayBuffer<T> mergeValue (scala.collection.mutable.ArrayBuffer<T> buffer, T i) { throw new RuntimeException(); }
  private <T extends java.lang.Object> scala.collection.mutable.ArrayBuffer<T> mergeCombiners (scala.collection.mutable.ArrayBuffer<T> buf1, scala.collection.mutable.ArrayBuffer<T> buf2) { throw new RuntimeException(); }
  private <T extends java.lang.Object> org.apache.spark.util.collection.ExternalAppendOnlyMap<T, T, scala.collection.mutable.ArrayBuffer<T>> createExternalMap () { throw new RuntimeException(); }
  private  org.apache.spark.SparkConf createSparkConf (boolean loadDefaults, scala.Option<java.lang.String> codec) { throw new RuntimeException(); }
  /**
   * Test spilling through simple aggregations and cogroups.
   * If a compression codec is provided, use it. Otherwise, do not compress spills.
   */
  private  void testSimpleSpilling (scala.Option<java.lang.String> codec) { throw new RuntimeException(); }
}
