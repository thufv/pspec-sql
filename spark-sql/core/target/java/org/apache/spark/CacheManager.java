package org.apache.spark;
/**
 * Spark class responsible for passing RDDs partition contents to the BlockManager and making
 * sure a node doesn't load two copies of an RDD at once.
 */
private  class CacheManager implements org.apache.spark.Logging {
  public   CacheManager (org.apache.spark.storage.BlockManager blockManager) { throw new RuntimeException(); }
  /** Keys of RDD partitions that are being computed/loaded. */
  private  scala.collection.mutable.HashSet<org.apache.spark.storage.RDDBlockId> loading () { throw new RuntimeException(); }
  /** Gets or computes an RDD partition. Used by RDD.iterator() when an RDD is cached. */
  public <T extends java.lang.Object> scala.collection.Iterator<T> getOrCompute (org.apache.spark.rdd.RDD<T> rdd, org.apache.spark.Partition partition, org.apache.spark.TaskContext context, org.apache.spark.storage.StorageLevel storageLevel) { throw new RuntimeException(); }
  /**
   * Acquire a loading lock for the partition identified by the given block ID.
   * <p>
   * If the lock is free, just acquire it and return None. Otherwise, another thread is already
   * loading the partition, so we wait for it to finish and return the values loaded by the thread.
   */
  private <T extends java.lang.Object> scala.Option<scala.collection.Iterator<T>> acquireLockForPartition (org.apache.spark.storage.RDDBlockId id) { throw new RuntimeException(); }
  /**
   * Cache the values of a partition, keeping track of any updates in the storage statuses of
   * other blocks along the way.
   * <p>
   * The effective storage level refers to the level that actually specifies BlockManager put
   * behavior, not the level originally specified by the user. This is mainly for forcing a
   * MEMORY_AND_DISK partition to disk if there is not enough room to unroll the partition,
   * while preserving the the original semantics of the RDD as specified by the application.
   */
  private <T extends java.lang.Object> scala.collection.Iterator<T> putInBlockManager (org.apache.spark.storage.BlockId key, scala.collection.Iterator<T> values, org.apache.spark.storage.StorageLevel level, scala.collection.mutable.ArrayBuffer<scala.Tuple2<org.apache.spark.storage.BlockId, org.apache.spark.storage.BlockStatus>> updatedBlocks, scala.Option<org.apache.spark.storage.StorageLevel> effectiveStorageLevel) { throw new RuntimeException(); }
}
