package org.apache.spark.storage;
/**
 * BlockManagerMasterActor is an actor on the master node to track statuses of
 * all slaves' block managers.
 */
private  class BlockManagerMasterActor implements akka.actor.Actor, org.apache.spark.util.ActorLogReceive, org.apache.spark.Logging {
  public  boolean isLocal () { throw new RuntimeException(); }
  // not preceding
  public   BlockManagerMasterActor (boolean isLocal, org.apache.spark.SparkConf conf, org.apache.spark.scheduler.LiveListenerBus listenerBus) { throw new RuntimeException(); }
  private  scala.collection.mutable.HashMap<org.apache.spark.storage.BlockManagerId, org.apache.spark.storage.BlockManagerInfo> blockManagerInfo () { throw new RuntimeException(); }
  private  scala.collection.mutable.HashMap<java.lang.String, org.apache.spark.storage.BlockManagerId> blockManagerIdByExecutor () { throw new RuntimeException(); }
  private  java.util.HashMap<org.apache.spark.storage.BlockId, scala.collection.mutable.HashSet<org.apache.spark.storage.BlockManagerId>> blockLocations () { throw new RuntimeException(); }
  private  scala.concurrent.duration.FiniteDuration akkaTimeout () { throw new RuntimeException(); }
  public  long slaveTimeout () { throw new RuntimeException(); }
  public  long checkTimeoutInterval () { throw new RuntimeException(); }
  public  akka.actor.Cancellable timeoutCheckingTask () { throw new RuntimeException(); }
  public  void preStart () { throw new RuntimeException(); }
  public  scala.PartialFunction<java.lang.Object, scala.runtime.BoxedUnit> receiveWithLogging () { throw new RuntimeException(); }
  private  scala.concurrent.Future<scala.collection.Seq<java.lang.Object>> removeRdd (int rddId) { throw new RuntimeException(); }
  private  scala.concurrent.Future<scala.collection.Seq<java.lang.Object>> removeShuffle (int shuffleId) { throw new RuntimeException(); }
  /**
   * Delegate RemoveBroadcast messages to each BlockManager because the master may not notified
   * of all broadcast blocks. If removeFromDriver is false, broadcast blocks are only removed
   * from the executors, but not from the driver.
   */
  private  scala.concurrent.Future<scala.collection.Seq<java.lang.Object>> removeBroadcast (long broadcastId, boolean removeFromDriver) { throw new RuntimeException(); }
  private  void removeBlockManager (org.apache.spark.storage.BlockManagerId blockManagerId) { throw new RuntimeException(); }
  private  void expireDeadHosts () { throw new RuntimeException(); }
  private  void removeExecutor (java.lang.String execId) { throw new RuntimeException(); }
  /**
   * Return true if the driver knows about the given block manager. Otherwise, return false,
   * indicating that the block manager should re-register.
   */
  private  boolean heartbeatReceived (org.apache.spark.storage.BlockManagerId blockManagerId) { throw new RuntimeException(); }
  private  void removeBlockFromWorkers (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
  private  scala.collection.immutable.Map<org.apache.spark.storage.BlockManagerId, scala.Tuple2<java.lang.Object, java.lang.Object>> memoryStatus () { throw new RuntimeException(); }
  private  org.apache.spark.storage.StorageStatus[] storageStatus () { throw new RuntimeException(); }
  /**
   * Return the block's status for all block managers, if any. NOTE: This is a
   * potentially expensive operation and should only be used for testing.
   * <p>
   * If askSlaves is true, the master queries each block manager for the most updated block
   * statuses. This is useful when the master is not informed of the given block by all block
   * managers.
   */
  private  scala.collection.immutable.Map<org.apache.spark.storage.BlockManagerId, scala.concurrent.Future<scala.Option<org.apache.spark.storage.BlockStatus>>> blockStatus (org.apache.spark.storage.BlockId blockId, boolean askSlaves) { throw new RuntimeException(); }
  /**
   * Return the ids of blocks present in all the block managers that match the given filter.
   * NOTE: This is a potentially expensive operation and should only be used for testing.
   * <p>
   * If askSlaves is true, the master queries each block manager for the most updated block
   * statuses. This is useful when the master is not informed of the given block by all block
   * managers.
   */
  private  scala.concurrent.Future<scala.collection.Seq<org.apache.spark.storage.BlockId>> getMatchingBlockIds (scala.Function1<org.apache.spark.storage.BlockId, java.lang.Object> filter, boolean askSlaves) { throw new RuntimeException(); }
  private  void register (org.apache.spark.storage.BlockManagerId id, long maxMemSize, akka.actor.ActorRef slaveActor) { throw new RuntimeException(); }
  private  void updateBlockInfo (org.apache.spark.storage.BlockManagerId blockManagerId, org.apache.spark.storage.BlockId blockId, org.apache.spark.storage.StorageLevel storageLevel, long memSize, long diskSize, long tachyonSize) { throw new RuntimeException(); }
  private  scala.collection.Seq<org.apache.spark.storage.BlockManagerId> getLocations (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
  private  scala.collection.Seq<scala.collection.Seq<org.apache.spark.storage.BlockManagerId>> getLocationsMultipleBlockIds (org.apache.spark.storage.BlockId[] blockIds) { throw new RuntimeException(); }
  private  scala.collection.Seq<org.apache.spark.storage.BlockManagerId> getPeers (org.apache.spark.storage.BlockManagerId blockManagerId, int size) { throw new RuntimeException(); }
}
