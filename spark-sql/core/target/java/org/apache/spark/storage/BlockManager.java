package org.apache.spark.storage;
private  class BlockManager implements org.apache.spark.storage.BlockDataProvider, org.apache.spark.Logging {
  static private  org.apache.spark.util.IdGenerator ID_GENERATOR () { throw new RuntimeException(); }
  /** Return the total amount of storage memory available. */
  static private  long getMaxMemory (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  /**
   * Attempt to clean up a ByteBuffer if it is memory-mapped. This uses an *unsafe* Sun API that
   * might cause errors if one attempts to read from the unmapped buffer, but it's better than
   * waiting for the GC to find it because that could lead to huge numbers of open files. There's
   * unfortunately no standard API to do this.
   */
  static public  void dispose (java.nio.ByteBuffer buffer) { throw new RuntimeException(); }
  static public  scala.collection.immutable.Map<org.apache.spark.storage.BlockId, scala.collection.Seq<org.apache.spark.storage.BlockManagerId>> blockIdsToBlockManagers (org.apache.spark.storage.BlockId[] blockIds, org.apache.spark.SparkEnv env, org.apache.spark.storage.BlockManagerMaster blockManagerMaster) { throw new RuntimeException(); }
  static public  scala.collection.immutable.Map<org.apache.spark.storage.BlockId, scala.collection.Seq<java.lang.String>> blockIdsToExecutorIds (org.apache.spark.storage.BlockId[] blockIds, org.apache.spark.SparkEnv env, org.apache.spark.storage.BlockManagerMaster blockManagerMaster) { throw new RuntimeException(); }
  static public  scala.collection.immutable.Map<org.apache.spark.storage.BlockId, scala.collection.Seq<java.lang.String>> blockIdsToHosts (org.apache.spark.storage.BlockId[] blockIds, org.apache.spark.SparkEnv env, org.apache.spark.storage.BlockManagerMaster blockManagerMaster) { throw new RuntimeException(); }
  public  org.apache.spark.storage.BlockManagerMaster master () { throw new RuntimeException(); }
  public  org.apache.spark.SparkConf conf () { throw new RuntimeException(); }
  // not preceding
  public   BlockManager (java.lang.String executorId, akka.actor.ActorSystem actorSystem, org.apache.spark.storage.BlockManagerMaster master, org.apache.spark.serializer.Serializer defaultSerializer, long maxMemory, org.apache.spark.SparkConf conf, org.apache.spark.SecurityManager securityManager, org.apache.spark.MapOutputTracker mapOutputTracker, org.apache.spark.shuffle.ShuffleManager shuffleManager) { throw new RuntimeException(); }
  private  int port () { throw new RuntimeException(); }
  public  org.apache.spark.storage.ShuffleBlockManager shuffleBlockManager () { throw new RuntimeException(); }
  public  org.apache.spark.storage.DiskBlockManager diskBlockManager () { throw new RuntimeException(); }
  public  org.apache.spark.network.ConnectionManager connectionManager () { throw new RuntimeException(); }
  public  scala.concurrent.ExecutionContextExecutor futureExecContext () { throw new RuntimeException(); }
  private  org.apache.spark.util.TimeStampedHashMap<org.apache.spark.storage.BlockId, org.apache.spark.storage.BlockInfo> blockInfo () { throw new RuntimeException(); }
  private  boolean tachyonInitialized () { throw new RuntimeException(); }
  public  org.apache.spark.storage.MemoryStore memoryStore () { throw new RuntimeException(); }
  public  org.apache.spark.storage.DiskStore diskStore () { throw new RuntimeException(); }
  public  org.apache.spark.storage.TachyonStore tachyonStore () { throw new RuntimeException(); }
  private  boolean useNetty () { throw new RuntimeException(); }
  public  org.apache.spark.network.netty.client.BlockFetchingClientFactory nettyBlockClientFactory () { throw new RuntimeException(); }
  private  org.apache.spark.network.netty.server.BlockServer nettyBlockServer () { throw new RuntimeException(); }
  private  int nettyPort () { throw new RuntimeException(); }
  public  org.apache.spark.storage.BlockManagerId blockManagerId () { throw new RuntimeException(); }
  public  long maxBytesInFlight () { throw new RuntimeException(); }
  private  boolean compressBroadcast () { throw new RuntimeException(); }
  private  boolean compressShuffle () { throw new RuntimeException(); }
  private  boolean compressRdds () { throw new RuntimeException(); }
  private  boolean compressShuffleSpill () { throw new RuntimeException(); }
  private  akka.actor.ActorRef slaveActor () { throw new RuntimeException(); }
  private  scala.concurrent.Future<scala.runtime.BoxedUnit> asyncReregisterTask () { throw new RuntimeException(); }
  private  java.lang.Object asyncReregisterLock () { throw new RuntimeException(); }
  private  org.apache.spark.util.MetadataCleaner metadataCleaner () { throw new RuntimeException(); }
  private  org.apache.spark.util.MetadataCleaner broadcastCleaner () { throw new RuntimeException(); }
  private  org.apache.spark.io.CompressionCodec compressionCodec () { throw new RuntimeException(); }
  /**
   * Construct a BlockManager with a memory limit set based on system properties.
   */
  public   BlockManager (java.lang.String execId, akka.actor.ActorSystem actorSystem, org.apache.spark.storage.BlockManagerMaster master, org.apache.spark.serializer.Serializer serializer, org.apache.spark.SparkConf conf, org.apache.spark.SecurityManager securityManager, org.apache.spark.MapOutputTracker mapOutputTracker, org.apache.spark.shuffle.ShuffleManager shuffleManager) { throw new RuntimeException(); }
  /**
   * Initialize the BlockManager. Register to the BlockManagerMaster, and start the
   * BlockManagerWorker actor.
   */
  private  void initialize () { throw new RuntimeException(); }
  /**
   * Report all blocks to the BlockManager again. This may be necessary if we are dropped
   * by the BlockManager and come back or if we become capable of recovering blocks on disk after
   * an executor crash.
   * <p>
   * This function deliberately fails silently if the master returns false (indicating that
   * the slave needs to re-register). The error condition will be detected again by the next
   * heart beat attempt or new block registration and another try to re-register all blocks
   * will be made then.
   */
  private  void reportAllBlocks () { throw new RuntimeException(); }
  /**
   * Re-register with the master and report all blocks to it. This will be called by the heart beat
   * thread if our heartbeat to the block manager indicates that we were not registered.
   * <p>
   * Note that this method must be called without any BlockInfo locks held.
   */
  public  void reregister () { throw new RuntimeException(); }
  /**
   * Re-register with the master sometime soon.
   */
  private  void asyncReregister () { throw new RuntimeException(); }
  /**
   * For testing. Wait for any pending asynchronous re-registration; otherwise, do nothing.
   */
  public  void waitForAsyncReregister () { throw new RuntimeException(); }
  public  scala.util.Either<org.apache.spark.storage.FileSegment, java.nio.ByteBuffer> getBlockData (java.lang.String blockId) { throw new RuntimeException(); }
  /**
   * Get the BlockStatus for the block identified by the given ID, if it exists.
   * NOTE: This is mainly for testing, and it doesn't fetch information from Tachyon.
   */
  public  scala.Option<org.apache.spark.storage.BlockStatus> getStatus (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
  /**
   * Get the ids of existing blocks that match the given filter. Note that this will
   * query the blocks stored in the disk block manager (that the block manager
   * may not know of).
   */
  public  scala.collection.Seq<org.apache.spark.storage.BlockId> getMatchingBlockIds (scala.Function1<org.apache.spark.storage.BlockId, java.lang.Object> filter) { throw new RuntimeException(); }
  /**
   * Tell the master about the current storage status of a block. This will send a block update
   * message reflecting the current status, *not* the desired storage level in its block info.
   * For example, a block with MEMORY_AND_DISK set might have fallen out to be only on disk.
   * <p>
   * droppedMemorySize exists to account for when the block is dropped from memory to disk (so
   * it is still valid). This ensures that update in master will compensate for the increase in
   * memory on slave.
   */
  private  void reportBlockStatus (org.apache.spark.storage.BlockId blockId, org.apache.spark.storage.BlockInfo info, org.apache.spark.storage.BlockStatus status, long droppedMemorySize) { throw new RuntimeException(); }
  /**
   * Actually send a UpdateBlockInfo message. Returns the master's response,
   * which will be true if the block was successfully recorded and false if
   * the slave needs to re-register.
   */
  private  boolean tryToReportBlockStatus (org.apache.spark.storage.BlockId blockId, org.apache.spark.storage.BlockInfo info, org.apache.spark.storage.BlockStatus status, long droppedMemorySize) { throw new RuntimeException(); }
  /**
   * Return the updated storage status of the block with the given ID. More specifically, if
   * the block is dropped from memory and possibly added to disk, return the new storage level
   * and the updated in-memory and on-disk sizes.
   */
  private  org.apache.spark.storage.BlockStatus getCurrentBlockStatus (org.apache.spark.storage.BlockId blockId, org.apache.spark.storage.BlockInfo info) { throw new RuntimeException(); }
  /**
   * Get locations of an array of blocks.
   */
  private  scala.collection.Seq<org.apache.spark.storage.BlockManagerId>[] getLocationBlockIds (org.apache.spark.storage.BlockId[] blockIds) { throw new RuntimeException(); }
  /**
   * A short-circuited method to get blocks directly from disk. This is used for getting
   * shuffle blocks. It is safe to do so without a lock on block info since disk store
   * never deletes (recent) items.
   */
  public  scala.Option<scala.collection.Iterator<java.lang.Object>> getLocalFromDisk (org.apache.spark.storage.BlockId blockId, org.apache.spark.serializer.Serializer serializer) { throw new RuntimeException(); }
  /**
   * Get block from local block manager.
   */
  public  scala.Option<org.apache.spark.storage.BlockResult> getLocal (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
  /**
   * Get block from the local block manager as serialized bytes.
   */
  public  scala.Option<java.nio.ByteBuffer> getLocalBytes (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
  private  scala.Option<java.lang.Object> doGetLocal (org.apache.spark.storage.BlockId blockId, boolean asBlockResult) { throw new RuntimeException(); }
  /**
   * Get block from remote block managers.
   */
  public  scala.Option<org.apache.spark.storage.BlockResult> getRemote (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
  /**
   * Get block from remote block managers as serialized bytes.
   */
  public  scala.Option<java.nio.ByteBuffer> getRemoteBytes (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
  private  scala.Option<java.lang.Object> doGetRemote (org.apache.spark.storage.BlockId blockId, boolean asBlockResult) { throw new RuntimeException(); }
  /**
   * Get a block from the block manager (either local or remote).
   */
  public  scala.Option<org.apache.spark.storage.BlockResult> get (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
  /**
   * Get multiple blocks from local and remote block manager using their BlockManagerIds. Returns
   * an Iterator of (block ID, value) pairs so that clients may handle blocks in a pipelined
   * fashion as they're received. Expects a size in bytes to be provided for each block fetched,
   * so that we can control the maxMegabytesInFlight for the fetch.
   */
  public  org.apache.spark.storage.BlockFetcherIterator getMultiple (scala.collection.Seq<scala.Tuple2<org.apache.spark.storage.BlockManagerId, scala.collection.Seq<scala.Tuple2<org.apache.spark.storage.BlockId, java.lang.Object>>>> blocksByAddress, org.apache.spark.serializer.Serializer serializer, org.apache.spark.executor.ShuffleReadMetrics readMetrics) { throw new RuntimeException(); }
  public  scala.collection.Seq<scala.Tuple2<org.apache.spark.storage.BlockId, org.apache.spark.storage.BlockStatus>> putIterator (org.apache.spark.storage.BlockId blockId, scala.collection.Iterator<java.lang.Object> values, org.apache.spark.storage.StorageLevel level, boolean tellMaster, scala.Option<org.apache.spark.storage.StorageLevel> effectiveStorageLevel) { throw new RuntimeException(); }
  /**
   * A short circuited method to get a block writer that can write data directly to disk.
   * The Block will be appended to the File specified by filename. Callers should handle error
   * cases.
   */
  public  org.apache.spark.storage.BlockObjectWriter getDiskWriter (org.apache.spark.storage.BlockId blockId, java.io.File file, org.apache.spark.serializer.Serializer serializer, int bufferSize, org.apache.spark.executor.ShuffleWriteMetrics writeMetrics) { throw new RuntimeException(); }
  /**
   * Put a new block of values to the block manager.
   * Return a list of blocks updated as a result of this put.
   */
  public  scala.collection.Seq<scala.Tuple2<org.apache.spark.storage.BlockId, org.apache.spark.storage.BlockStatus>> putArray (org.apache.spark.storage.BlockId blockId, java.lang.Object[] values, org.apache.spark.storage.StorageLevel level, boolean tellMaster, scala.Option<org.apache.spark.storage.StorageLevel> effectiveStorageLevel) { throw new RuntimeException(); }
  /**
   * Put a new block of serialized bytes to the block manager.
   * Return a list of blocks updated as a result of this put.
   */
  public  scala.collection.Seq<scala.Tuple2<org.apache.spark.storage.BlockId, org.apache.spark.storage.BlockStatus>> putBytes (org.apache.spark.storage.BlockId blockId, java.nio.ByteBuffer bytes, org.apache.spark.storage.StorageLevel level, boolean tellMaster, scala.Option<org.apache.spark.storage.StorageLevel> effectiveStorageLevel) { throw new RuntimeException(); }
  /**
   * Put the given block according to the given level in one of the block stores, replicating
   * the values if necessary.
   * <p>
   * The effective storage level refers to the level according to which the block will actually be
   * handled. This allows the caller to specify an alternate behavior of doPut while preserving
   * the original level specified by the user.
   */
  private  scala.collection.Seq<scala.Tuple2<org.apache.spark.storage.BlockId, org.apache.spark.storage.BlockStatus>> doPut (org.apache.spark.storage.BlockId blockId, org.apache.spark.storage.BlockValues data, org.apache.spark.storage.StorageLevel level, boolean tellMaster, scala.Option<org.apache.spark.storage.StorageLevel> effectiveStorageLevel) { throw new RuntimeException(); }
  /**
   * Replicate block to another node.
   */
  public  scala.collection.Seq<org.apache.spark.storage.BlockManagerId> cachedPeers () { throw new RuntimeException(); }
  private  void replicate (org.apache.spark.storage.BlockId blockId, java.nio.ByteBuffer data, org.apache.spark.storage.StorageLevel level) { throw new RuntimeException(); }
  /**
   * Read a block consisting of a single object.
   */
  public  scala.Option<java.lang.Object> getSingle (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
  /**
   * Write a block consisting of a single object.
   */
  public  scala.collection.Seq<scala.Tuple2<org.apache.spark.storage.BlockId, org.apache.spark.storage.BlockStatus>> putSingle (org.apache.spark.storage.BlockId blockId, Object value, org.apache.spark.storage.StorageLevel level, boolean tellMaster) { throw new RuntimeException(); }
  /**
   * Drop a block from memory, possibly putting it on disk if applicable. Called when the memory
   * store reaches its limit and needs to free up space.
   * <p>
   * Return the block status if the given block has been updated, else None.
   */
  public  scala.Option<org.apache.spark.storage.BlockStatus> dropFromMemory (org.apache.spark.storage.BlockId blockId, scala.util.Either<java.lang.Object[], java.nio.ByteBuffer> data) { throw new RuntimeException(); }
  /**
   * Remove all blocks belonging to the given RDD.
   * @return The number of blocks removed.
   */
  public  int removeRdd (int rddId) { throw new RuntimeException(); }
  /**
   * Remove all blocks belonging to the given broadcast.
   */
  public  int removeBroadcast (long broadcastId, boolean tellMaster) { throw new RuntimeException(); }
  /**
   * Remove a block from both memory and disk.
   */
  public  void removeBlock (org.apache.spark.storage.BlockId blockId, boolean tellMaster) { throw new RuntimeException(); }
  private  void dropOldNonBroadcastBlocks (long cleanupTime) { throw new RuntimeException(); }
  private  void dropOldBroadcastBlocks (long cleanupTime) { throw new RuntimeException(); }
  private  void dropOldBlocks (long cleanupTime, scala.Function1<org.apache.spark.storage.BlockId, java.lang.Object> shouldDrop) { throw new RuntimeException(); }
  private  boolean shouldCompress (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
  /**
   * Wrap an output stream for compression if block compression is enabled for its block type
   */
  public  java.io.OutputStream wrapForCompression (org.apache.spark.storage.BlockId blockId, java.io.OutputStream s) { throw new RuntimeException(); }
  /**
   * Wrap an input stream for compression if block compression is enabled for its block type
   */
  public  java.io.InputStream wrapForCompression (org.apache.spark.storage.BlockId blockId, java.io.InputStream s) { throw new RuntimeException(); }
  /** Serializes into a stream. */
  public  void dataSerializeStream (org.apache.spark.storage.BlockId blockId, java.io.OutputStream outputStream, scala.collection.Iterator<java.lang.Object> values, org.apache.spark.serializer.Serializer serializer) { throw new RuntimeException(); }
  /** Serializes into a byte buffer. */
  public  java.nio.ByteBuffer dataSerialize (org.apache.spark.storage.BlockId blockId, scala.collection.Iterator<java.lang.Object> values, org.apache.spark.serializer.Serializer serializer) { throw new RuntimeException(); }
  /**
   * Deserializes a ByteBuffer into an iterator of values and disposes of it when the end of
   * the iterator is reached.
   */
  public  scala.collection.Iterator<java.lang.Object> dataDeserialize (org.apache.spark.storage.BlockId blockId, java.nio.ByteBuffer bytes, org.apache.spark.serializer.Serializer serializer) { throw new RuntimeException(); }
  public  void stop () { throw new RuntimeException(); }
}
