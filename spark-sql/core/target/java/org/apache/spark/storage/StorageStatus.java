package org.apache.spark.storage;
/**
 * :: DeveloperApi ::
 * Storage information for each BlockManager.
 * <p>
 * This class assumes BlockId and BlockStatus are immutable, such that the consumers of this
 * class cannot mutate the source of the information. Accesses are not thread-safe.
 */
public  class StorageStatus {
  public  org.apache.spark.storage.BlockManagerId blockManagerId () { throw new RuntimeException(); }
  public  long maxMem () { throw new RuntimeException(); }
  // not preceding
  public   StorageStatus (org.apache.spark.storage.BlockManagerId blockManagerId, long maxMem) { throw new RuntimeException(); }
  /**
   * Internal representation of the blocks stored in this block manager.
   * <p>
   * We store RDD blocks and non-RDD blocks separately to allow quick retrievals of RDD blocks.
   * These collections should only be mutated through the add/update/removeBlock methods.
   */
  private  scala.collection.mutable.HashMap<java.lang.Object, scala.collection.mutable.Map<org.apache.spark.storage.BlockId, org.apache.spark.storage.BlockStatus>> _rddBlocks () { throw new RuntimeException(); }
  private  scala.collection.mutable.HashMap<org.apache.spark.storage.BlockId, org.apache.spark.storage.BlockStatus> _nonRddBlocks () { throw new RuntimeException(); }
  /**
   * Storage information of the blocks that entails memory, disk, and off-heap memory usage.
   * <p>
   * As with the block maps, we store the storage information separately for RDD blocks and
   * non-RDD blocks for the same reason. In particular, RDD storage information is stored
   * in a map indexed by the RDD ID to the following 4-tuple:
   * <p>
   *   (memory size, disk size, off-heap size, storage level)
   * <p>
   * We assume that all the blocks that belong to the same RDD have the same storage level.
   * This field is not relevant to non-RDD blocks, however, so the storage information for
   * non-RDD blocks contains only the first 3 fields (in the same order).
   */
  private  scala.collection.mutable.HashMap<java.lang.Object, scala.Tuple4<java.lang.Object, java.lang.Object, java.lang.Object, org.apache.spark.storage.StorageLevel>> _rddStorageInfo () { throw new RuntimeException(); }
  private  scala.Tuple3<java.lang.Object, java.lang.Object, java.lang.Object> _nonRddStorageInfo () { throw new RuntimeException(); }
  /** Create a storage status with an initial set of blocks, leaving the source unmodified. */
  public   StorageStatus (org.apache.spark.storage.BlockManagerId bmid, long maxMem, scala.collection.Map<org.apache.spark.storage.BlockId, org.apache.spark.storage.BlockStatus> initialBlocks) { throw new RuntimeException(); }
  /**
   * Return the blocks stored in this block manager.
   * <p>
   * Note that this is somewhat expensive, as it involves cloning the underlying maps and then
   * concatenating them together. Much faster alternatives exist for common operations such as
   * contains, get, and size.
   */
  public  scala.collection.Map<org.apache.spark.storage.BlockId, org.apache.spark.storage.BlockStatus> blocks () { throw new RuntimeException(); }
  /**
   * Return the RDD blocks stored in this block manager.
   * <p>
   * Note that this is somewhat expensive, as it involves cloning the underlying maps and then
   * concatenating them together. Much faster alternatives exist for common operations such as
   * getting the memory, disk, and off-heap memory sizes occupied by this RDD.
   */
  public  scala.collection.Map<org.apache.spark.storage.BlockId, org.apache.spark.storage.BlockStatus> rddBlocks () { throw new RuntimeException(); }
  /** Return the blocks that belong to the given RDD stored in this block manager. */
  public  scala.collection.Map<org.apache.spark.storage.BlockId, org.apache.spark.storage.BlockStatus> rddBlocksById (int rddId) { throw new RuntimeException(); }
  /** Add the given block to this storage status. If it already exists, overwrite it. */
  private  void addBlock (org.apache.spark.storage.BlockId blockId, org.apache.spark.storage.BlockStatus blockStatus) { throw new RuntimeException(); }
  /** Update the given block in this storage status. If it doesn't already exist, add it. */
  private  void updateBlock (org.apache.spark.storage.BlockId blockId, org.apache.spark.storage.BlockStatus blockStatus) { throw new RuntimeException(); }
  /** Remove the given block from this storage status. */
  private  scala.Option<org.apache.spark.storage.BlockStatus> removeBlock (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
  /**
   * Return whether the given block is stored in this block manager in O(1) time.
   * Note that this is much faster than <code>this.blocks.contains</code>, which is O(blocks) time.
   */
  public  boolean containsBlock (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
  /**
   * Return the given block stored in this block manager in O(1) time.
   * Note that this is much faster than <code>this.blocks.get</code>, which is O(blocks) time.
   */
  public  scala.Option<org.apache.spark.storage.BlockStatus> getBlock (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
  /**
   * Return the number of blocks stored in this block manager in O(RDDs) time.
   * Note that this is much faster than <code>this.blocks.size</code>, which is O(blocks) time.
   */
  public  int numBlocks () { throw new RuntimeException(); }
  /**
   * Return the number of RDD blocks stored in this block manager in O(RDDs) time.
   * Note that this is much faster than <code>this.rddBlocks.size</code>, which is O(RDD blocks) time.
   */
  public  int numRddBlocks () { throw new RuntimeException(); }
  /**
   * Return the number of blocks that belong to the given RDD in O(1) time.
   * Note that this is much faster than <code>this.rddBlocksById(rddId).size</code>, which is
   * O(blocks in this RDD) time.
   */
  public  int numRddBlocksById (int rddId) { throw new RuntimeException(); }
  /** Return the memory remaining in this block manager. */
  public  long memRemaining () { throw new RuntimeException(); }
  /** Return the memory used by this block manager. */
  public  long memUsed () { throw new RuntimeException(); }
  /** Return the disk space used by this block manager. */
  public  long diskUsed () { throw new RuntimeException(); }
  /** Return the off-heap space used by this block manager. */
  public  long offHeapUsed () { throw new RuntimeException(); }
  /** Return the memory used by the given RDD in this block manager in O(1) time. */
  public  long memUsedByRdd (int rddId) { throw new RuntimeException(); }
  /** Return the disk space used by the given RDD in this block manager in O(1) time. */
  public  long diskUsedByRdd (int rddId) { throw new RuntimeException(); }
  /** Return the off-heap space used by the given RDD in this block manager in O(1) time. */
  public  long offHeapUsedByRdd (int rddId) { throw new RuntimeException(); }
  /** Return the storage level, if any, used by the given RDD in this block manager. */
  public  scala.Option<org.apache.spark.storage.StorageLevel> rddStorageLevel (int rddId) { throw new RuntimeException(); }
  /**
   * Update the relevant storage info, taking into account any existing status for this block.
   */
  private  void updateStorageInfo (org.apache.spark.storage.BlockId blockId, org.apache.spark.storage.BlockStatus newBlockStatus) { throw new RuntimeException(); }
}
