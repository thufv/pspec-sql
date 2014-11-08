package org.apache.spark.util.collection;
public  class SizeTrackerSuite extends org.scalatest.FunSuite {
  static private  class LargeDummyClass {
    public   LargeDummyClass () { throw new RuntimeException(); }
    public  int[] arr () { throw new RuntimeException(); }
  }
  /**
   * Run speed tests for size tracking collections.
   */
  static public  void main (java.lang.String[] args) { throw new RuntimeException(); }
  /**
   * Speed test for SizeTrackingVector.
   * <p>
   * Results for 100000 elements (possibly non-deterministic):
   *   PrimitiveVector  15 ms
   *   SizeTracker      51 ms
   *   SizeEstimator    2000 ms
   */
  static public  void vectorSpeedTest (int numElements) { throw new RuntimeException(); }
  /**
   * Speed test for SizeTrackingAppendOnlyMap.
   * <p>
   * Results for 100000 elements (possibly non-deterministic):
   *   AppendOnlyMap  30 ms
   *   SizeTracker    41 ms
   *   SizeEstimator  1666 ms
   */
  static public  void mapSpeedTest (int numElements) { throw new RuntimeException(); }
  static public  void printSpeedTestResult (java.lang.String testName, scala.collection.Seq<java.lang.Object> baseTimes, scala.collection.Seq<java.lang.Object> sampledTimes, scala.collection.Seq<java.lang.Object> unsampledTimes) { throw new RuntimeException(); }
  static public  long time (scala.Function0<scala.runtime.BoxedUnit> f) { throw new RuntimeException(); }
  static public  long averageTime (scala.collection.Seq<java.lang.Object> v) { throw new RuntimeException(); }
  public   SizeTrackerSuite () { throw new RuntimeException(); }
  public  double NORMAL_ERROR () { throw new RuntimeException(); }
  public  double HIGH_ERROR () { throw new RuntimeException(); }
  public <T extends java.lang.Object> void testVector (int numElements, scala.Function1<java.lang.Object, T> makeElement, scala.reflect.ClassTag<T> evidence$1) { throw new RuntimeException(); }
  public <K extends java.lang.Object, V extends java.lang.Object> void testMap (int numElements, scala.Function1<java.lang.Object, scala.Tuple2<K, V>> makeElement) { throw new RuntimeException(); }
  public  void expectWithinError (java.lang.Object obj, long estimatedSize, double error) { throw new RuntimeException(); }
}
