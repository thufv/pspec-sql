package org.apache.spark;
/**
 * Test suite for cancelling running jobs. We run the cancellation tasks for single job action
 * (e.g. count) as well as multi-job action (e.g. take). We test the local and cluster schedulers
 * in both FIFO and fair scheduling modes.
 */
public  class JobCancellationSuite extends org.scalatest.FunSuite implements org.scalatest.Matchers, org.scalatest.BeforeAndAfter, org.apache.spark.LocalSparkContext {
  static public  java.util.concurrent.Semaphore taskStartedSemaphore () { throw new RuntimeException(); }
  static public  java.util.concurrent.Semaphore taskCancelledSemaphore () { throw new RuntimeException(); }
  public   JobCancellationSuite () { throw new RuntimeException(); }
  public  void afterEach () { throw new RuntimeException(); }
  public  void testCount () { throw new RuntimeException(); }
  public  void testTake () { throw new RuntimeException(); }
}
