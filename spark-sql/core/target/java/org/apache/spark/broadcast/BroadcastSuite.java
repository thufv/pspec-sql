package org.apache.spark.broadcast;
public  class BroadcastSuite extends org.scalatest.FunSuite implements org.apache.spark.LocalSparkContext {
  public   BroadcastSuite () { throw new RuntimeException(); }
  private  org.apache.spark.SparkConf httpConf () { throw new RuntimeException(); }
  private  org.apache.spark.SparkConf torrentConf () { throw new RuntimeException(); }
  /**
   * Verify the persistence of state associated with an HttpBroadcast in either local mode or
   * local-cluster mode (when distributed = true).
   * <p>
   * This test creates a broadcast variable, uses it on all executors, and then unpersists it.
   * In between each step, this test verifies that the broadcast blocks and the broadcast file
   * are present only on the expected nodes.
   */
  private  void testUnpersistHttpBroadcast (boolean distributed, boolean removeFromDriver) { throw new RuntimeException(); }
  /**
   * Verify the persistence of state associated with an TorrentBroadcast in a local-cluster.
   * <p>
   * This test creates a broadcast variable, uses it on all executors, and then unpersists it.
   * In between each step, this test verifies that the broadcast blocks are present only on the
   * expected nodes.
   */
  private  void testUnpersistTorrentBroadcast (boolean distributed, boolean removeFromDriver) { throw new RuntimeException(); }
  /**
   * This test runs in 4 steps:
   * <p>
   * 1) Create broadcast variable, and verify that all state is persisted on the driver.
   * 2) Use the broadcast variable on all executors, and verify that all state is persisted
   *    on both the driver and the executors.
   * 3) Unpersist the broadcast, and verify that all state is removed where they should be.
   * 4) [Optional] If removeFromDriver is false, we verify that the broadcast is re-usable.
   */
  private  void testUnpersistBroadcast (boolean distributed, int numSlaves, org.apache.spark.SparkConf broadcastConf, scala.Function2<java.lang.Object, org.apache.spark.storage.BlockManagerMaster, scala.runtime.BoxedUnit> afterCreation, scala.Function2<java.lang.Object, org.apache.spark.storage.BlockManagerMaster, scala.runtime.BoxedUnit> afterUsingBroadcast, scala.Function2<java.lang.Object, org.apache.spark.storage.BlockManagerMaster, scala.runtime.BoxedUnit> afterUnpersist, boolean removeFromDriver) { throw new RuntimeException(); }
  /** Helper method to create a SparkConf that uses the given broadcast factory. */
  private  org.apache.spark.SparkConf broadcastConf (java.lang.String factoryName) { throw new RuntimeException(); }
}
