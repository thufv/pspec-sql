package org.apache.spark;
/** Class to test whether RDDs, shuffles, etc. have been successfully cleaned. */
public  class CleanerTester implements org.apache.spark.Logging {
  public   CleanerTester (org.apache.spark.SparkContext sc, scala.collection.Seq<java.lang.Object> rddIds, scala.collection.Seq<java.lang.Object> shuffleIds, scala.collection.Seq<java.lang.Object> broadcastIds) { throw new RuntimeException(); }
  public  scala.collection.mutable.HashSet<java.lang.Object> toBeCleanedRDDIds () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashSet<java.lang.Object> toBeCleanedShuffleIds () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashSet<java.lang.Object> toBeCleanedBroadcstIds () { throw new RuntimeException(); }
  public  boolean isDistributed () { throw new RuntimeException(); }
  public  java.lang.Object cleanerListener () { throw new RuntimeException(); }
  public  int MAX_VALIDATION_ATTEMPTS () { throw new RuntimeException(); }
  public  int VALIDATION_ATTEMPT_INTERVAL () { throw new RuntimeException(); }
  /** Assert that all the stuff has been cleaned up */
  public  void assertCleanup (org.scalatest.concurrent.PatienceConfiguration.Timeout waitTimeout) { throw new RuntimeException(); }
  /** Verify that RDDs, shuffles, etc. occupy resources */
  private  void preCleanupValidate () { throw new RuntimeException(); }
  /**
   * Verify that RDDs, shuffles, etc. do not occupy resources. Tests multiple times as there is
   * as there is not guarantee on how long it will take clean up the resources.
   */
  private  void postCleanupValidate () { throw new RuntimeException(); }
  private  java.lang.String uncleanedResourcesToString () { throw new RuntimeException(); }
  private  boolean isAllCleanedUp () { throw new RuntimeException(); }
  private  scala.collection.Seq<org.apache.spark.storage.BlockId> getRDDBlocks (int rddId) { throw new RuntimeException(); }
  private  scala.collection.Seq<org.apache.spark.storage.BlockId> getShuffleBlocks (int shuffleId) { throw new RuntimeException(); }
  private  scala.collection.Seq<org.apache.spark.storage.BlockId> getBroadcastBlocks (long broadcastId) { throw new RuntimeException(); }
  private  org.apache.spark.storage.BlockManager blockManager () { throw new RuntimeException(); }
  private  org.apache.spark.MapOutputTrackerMaster mapOutputTrackerMaster () { throw new RuntimeException(); }
}
