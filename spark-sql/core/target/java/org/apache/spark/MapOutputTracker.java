package org.apache.spark;
/**
 * Class that keeps track of the location of the map output of
 * a stage. This is abstract because different versions of MapOutputTracker
 * (driver and worker) use different HashMap to store its metadata.
 */
private abstract class MapOutputTracker implements org.apache.spark.Logging {
  static private  double LOG_BASE () { throw new RuntimeException(); }
  static public  byte[] serializeMapStatuses (org.apache.spark.scheduler.MapStatus[] statuses) { throw new RuntimeException(); }
  static public  org.apache.spark.scheduler.MapStatus[] deserializeMapStatuses (byte[] bytes) { throw new RuntimeException(); }
  static private  scala.Tuple2<org.apache.spark.storage.BlockManagerId, java.lang.Object>[] convertMapStatuses (int shuffleId, int reduceId, org.apache.spark.scheduler.MapStatus[] statuses) { throw new RuntimeException(); }
  /**
   * Compress a size in bytes to 8 bits for efficient reporting of map output sizes.
   * We do this by encoding the log base 1.1 of the size as an integer, which can support
   * sizes up to 35 GB with at most 10% error.
   */
  static public  byte compressSize (long size) { throw new RuntimeException(); }
  /**
   * Decompress an 8-bit encoded block size, using the reverse operation of compressSize.
   */
  static public  long decompressSize (byte compressedSize) { throw new RuntimeException(); }
  public   MapOutputTracker (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  private  scala.concurrent.duration.FiniteDuration timeout () { throw new RuntimeException(); }
  /** Set to the MapOutputTrackerActor living on the driver. */
  public  akka.actor.ActorRef trackerActor () { throw new RuntimeException(); }
  /**
   * This HashMap has different behavior for the master and the workers.
   * <p>
   * On the master, it serves as the source of map outputs recorded from ShuffleMapTasks.
   * On the workers, it simply serves as a cache, in which a miss triggers a fetch from the
   * master's corresponding HashMap.
   */
  protected abstract  scala.collection.mutable.Map<java.lang.Object, org.apache.spark.scheduler.MapStatus[]> mapStatuses () ;
  /**
   * Incremented every time a fetch fails so that client nodes know to clear
   * their cache of map output locations if this happens.
   */
  protected  long epoch () { throw new RuntimeException(); }
  protected  java.lang.Object epochLock () { throw new RuntimeException(); }
  /** Remembers which map output locations are currently being fetched on a worker. */
  private  scala.collection.mutable.HashSet<java.lang.Object> fetching () { throw new RuntimeException(); }
  /**
   * Send a message to the trackerActor and get its result within a default timeout, or
   * throw a SparkException if this fails.
   */
  protected  Object askTracker (Object message) { throw new RuntimeException(); }
  /** Send a one-way message to the trackerActor, to which we expect it to reply with true. */
  protected  void sendTracker (Object message) { throw new RuntimeException(); }
  /**
   * Called from executors to get the server URIs and output sizes of the map outputs of
   * a given shuffle.
   */
  public  scala.Tuple2<org.apache.spark.storage.BlockManagerId, java.lang.Object>[] getServerStatuses (int shuffleId, int reduceId) { throw new RuntimeException(); }
  /** Called to get current epoch number. */
  public  long getEpoch () { throw new RuntimeException(); }
  /**
   * Called from executors to update the epoch number, potentially clearing old outputs
   * because of a fetch failure. Each worker task calls this with the latest epoch
   * number on the master at the time it was created.
   */
  public  void updateEpoch (long newEpoch) { throw new RuntimeException(); }
  /** Unregister shuffle data. */
  public  void unregisterShuffle (int shuffleId) { throw new RuntimeException(); }
  /** Stop the tracker. */
  public  void stop () { throw new RuntimeException(); }
}
