package org.apache.spark.util.collection;
// no position
private  class SizeTrackerSuite$ implements scala.Serializable {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final SizeTrackerSuite$ MODULE$ = null;
  public   SizeTrackerSuite$ () { throw new RuntimeException(); }
  /**
   * Run speed tests for size tracking collections.
   */
  public  void main (java.lang.String[] args) { throw new RuntimeException(); }
  /**
   * Speed test for SizeTrackingVector.
   * <p>
   * Results for 100000 elements (possibly non-deterministic):
   *   PrimitiveVector  15 ms
   *   SizeTracker      51 ms
   *   SizeEstimator    2000 ms
   */
  public  void vectorSpeedTest (int numElements) { throw new RuntimeException(); }
  /**
   * Speed test for SizeTrackingAppendOnlyMap.
   * <p>
   * Results for 100000 elements (possibly non-deterministic):
   *   AppendOnlyMap  30 ms
   *   SizeTracker    41 ms
   *   SizeEstimator  1666 ms
   */
  public  void mapSpeedTest (int numElements) { throw new RuntimeException(); }
  public  void printSpeedTestResult (java.lang.String testName, scala.collection.Seq<java.lang.Object> baseTimes, scala.collection.Seq<java.lang.Object> sampledTimes, scala.collection.Seq<java.lang.Object> unsampledTimes) { throw new RuntimeException(); }
  public  long time (scala.Function0<scala.runtime.BoxedUnit> f) { throw new RuntimeException(); }
  public  long averageTime (scala.collection.Seq<java.lang.Object> v) { throw new RuntimeException(); }
}
