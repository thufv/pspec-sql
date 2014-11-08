package org.apache.spark;
/**
 * MapOutputTracker for the driver. This uses TimeStampedHashMap to keep track of map
 * output information, which allows old output information based on a TTL.
 */
private  class MapOutputTrackerMaster extends org.apache.spark.MapOutputTracker {
  public   MapOutputTrackerMaster (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  /** Cache a serialized version of the output statuses for each shuffle to send them out faster */
  private  long cacheEpoch () { throw new RuntimeException(); }
  /**
   * Timestamp based HashMap for storing mapStatuses and cached serialized statuses in the master,
   * so that statuses are dropped only by explicit de-registering or by TTL-based cleaning (if set).
   * Other than these two scenarios, nothing should be dropped from this HashMap.
   */
  protected  org.apache.spark.util.TimeStampedHashMap<java.lang.Object, org.apache.spark.scheduler.MapStatus[]> mapStatuses () { throw new RuntimeException(); }
  private  org.apache.spark.util.TimeStampedHashMap<java.lang.Object, byte[]> cachedSerializedStatuses () { throw new RuntimeException(); }
  private  org.apache.spark.util.MetadataCleaner metadataCleaner () { throw new RuntimeException(); }
  public  void registerShuffle (int shuffleId, int numMaps) { throw new RuntimeException(); }
  public  void registerMapOutput (int shuffleId, int mapId, org.apache.spark.scheduler.MapStatus status) { throw new RuntimeException(); }
  /** Register multiple map output information for the given shuffle */
  public  void registerMapOutputs (int shuffleId, org.apache.spark.scheduler.MapStatus[] statuses, boolean changeEpoch) { throw new RuntimeException(); }
  /** Unregister map output information of the given shuffle, mapper and block manager */
  public  void unregisterMapOutput (int shuffleId, int mapId, org.apache.spark.storage.BlockManagerId bmAddress) { throw new RuntimeException(); }
  /** Unregister shuffle data */
  public  void unregisterShuffle (int shuffleId) { throw new RuntimeException(); }
  /** Check if the given shuffle is being tracked */
  public  boolean containsShuffle (int shuffleId) { throw new RuntimeException(); }
  public  void incrementEpoch () { throw new RuntimeException(); }
  public  byte[] getSerializedMapOutputStatuses (int shuffleId) { throw new RuntimeException(); }
  public  void stop () { throw new RuntimeException(); }
  private  void cleanup (long cleanupTime) { throw new RuntimeException(); }
}
