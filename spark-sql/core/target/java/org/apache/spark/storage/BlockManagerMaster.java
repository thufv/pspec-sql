package org.apache.spark.storage;
private  class BlockManagerMaster implements org.apache.spark.Logging {
  public  akka.actor.ActorRef driverActor () { throw new RuntimeException(); }
  // not preceding
  public   BlockManagerMaster (akka.actor.ActorRef driverActor, org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  private  int AKKA_RETRY_ATTEMPTS () { throw new RuntimeException(); }
  private  int AKKA_RETRY_INTERVAL_MS () { throw new RuntimeException(); }
  public  java.lang.String DRIVER_AKKA_ACTOR_NAME () { throw new RuntimeException(); }
  public  scala.concurrent.duration.FiniteDuration timeout () { throw new RuntimeException(); }
  /** Remove a dead executor from the driver actor. This is only called on the driver side. */
  public  void removeExecutor (java.lang.String execId) { throw new RuntimeException(); }
  /** Register the BlockManager's id with the driver. */
  public  void registerBlockManager (org.apache.spark.storage.BlockManagerId blockManagerId, long maxMemSize, akka.actor.ActorRef slaveActor) { throw new RuntimeException(); }
  public  boolean updateBlockInfo (org.apache.spark.storage.BlockManagerId blockManagerId, org.apache.spark.storage.BlockId blockId, org.apache.spark.storage.StorageLevel storageLevel, long memSize, long diskSize, long tachyonSize) { throw new RuntimeException(); }
  /** Get locations of the blockId from the driver */
  public  scala.collection.Seq<org.apache.spark.storage.BlockManagerId> getLocations (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
  /** Get locations of multiple blockIds from the driver */
  public  scala.collection.Seq<scala.collection.Seq<org.apache.spark.storage.BlockManagerId>> getLocations (org.apache.spark.storage.BlockId[] blockIds) { throw new RuntimeException(); }
  /**
   * Check if block manager master has a block. Note that this can be used to check for only
   * those blocks that are reported to block manager master.
   */
  public  boolean contains (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
  /** Get ids of other nodes in the cluster from the driver */
  public  scala.collection.Seq<org.apache.spark.storage.BlockManagerId> getPeers (org.apache.spark.storage.BlockManagerId blockManagerId, int numPeers) { throw new RuntimeException(); }
  /**
   * Remove a block from the slaves that have it. This can only be used to remove
   * blocks that the driver knows about.
   */
  public  void removeBlock (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
  /** Remove all blocks belonging to the given RDD. */
  public  void removeRdd (int rddId, boolean blocking) { throw new RuntimeException(); }
  /** Remove all blocks belonging to the given shuffle. */
  public  void removeShuffle (int shuffleId, boolean blocking) { throw new RuntimeException(); }
  /** Remove all blocks belonging to the given broadcast. */
  public  void removeBroadcast (long broadcastId, boolean removeFromMaster, boolean blocking) { throw new RuntimeException(); }
  /**
   * Return the memory status for each block manager, in the form of a map from
   * the block manager's id to two long values. The first value is the maximum
   * amount of memory allocated for the block manager, while the second is the
   * amount of remaining memory.
   */
  public  scala.collection.immutable.Map<org.apache.spark.storage.BlockManagerId, scala.Tuple2<java.lang.Object, java.lang.Object>> getMemoryStatus () { throw new RuntimeException(); }
  public  org.apache.spark.storage.StorageStatus[] getStorageStatus () { throw new RuntimeException(); }
  /**
   * Return the block's status on all block managers, if any. NOTE: This is a
   * potentially expensive operation and should only be used for testing.
   * <p>
   * If askSlaves is true, this invokes the master to query each block manager for the most
   * updated block statuses. This is useful when the master is not informed of the given block
   * by all block managers.
   */
  public  scala.collection.immutable.Map<org.apache.spark.storage.BlockManagerId, org.apache.spark.storage.BlockStatus> getBlockStatus (org.apache.spark.storage.BlockId blockId, boolean askSlaves) { throw new RuntimeException(); }
  /**
   * Return a list of ids of existing blocks such that the ids match the given filter. NOTE: This
   * is a potentially expensive operation and should only be used for testing.
   * <p>
   * If askSlaves is true, this invokes the master to query each block manager for the most
   * updated block statuses. This is useful when the master is not informed of the given block
   * by all block managers.
   */
  public  scala.collection.Seq<org.apache.spark.storage.BlockId> getMatchingBlockIds (scala.Function1<org.apache.spark.storage.BlockId, java.lang.Object> filter, boolean askSlaves) { throw new RuntimeException(); }
  /** Stop the driver actor, called only on the Spark driver node */
  public  void stop () { throw new RuntimeException(); }
  /** Send a one-way message to the master actor, to which we expect it to reply with true. */
  private  void tell (Object message) { throw new RuntimeException(); }
  /**
   * Send a message to the driver actor and get its result within a default timeout, or
   * throw a SparkException if this fails.
   */
  private <T extends java.lang.Object> T askDriverWithReply (Object message) { throw new RuntimeException(); }
}
