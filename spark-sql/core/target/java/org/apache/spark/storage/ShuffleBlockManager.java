package org.apache.spark.storage;
/**
 * Manages assigning disk-based block writers to shuffle tasks. Each shuffle task gets one file
 * per reducer (this set of files is called a ShuffleFileGroup).
 * <p>
 * As an optimization to reduce the number of physical shuffle files produced, multiple shuffle
 * blocks are aggregated into the same file. There is one "combined shuffle file" per reducer
 * per concurrently executing shuffle task. As soon as a task finishes writing to its shuffle
 * files, it releases them for another task.
 * Regarding the implementation of this feature, shuffle files are identified by a 3-tuple:
 *   - shuffleId: The unique id given to the entire shuffle stage.
 *   - bucketId: The id of the output partition (i.e., reducer id)
 *   - fileId: The unique id identifying a group of "combined shuffle files." Only one task at a
 *       time owns a particular fileId, and this id is returned to a pool when the task finishes.
 * Each shuffle file is then mapped to a FileSegment, which is a 3-tuple (file, offset, length)
 * that specifies where in a given file the actual block data is located.
 * <p>
 * Shuffle file metadata is stored in a space-efficient manner. Rather than simply mapping
 * ShuffleBlockIds directly to FileSegments, each ShuffleFileGroup maintains a list of offsets for
 * each block stored in each file. In order to find the location of a shuffle block, we search the
 * files within a ShuffleFileGroups associated with the block's reducer.
 */
private  class ShuffleBlockManager implements org.apache.spark.Logging {
  /**
   * Contains all the state related to a particular shuffle. This includes a pool of unused
   * ShuffleFileGroups, as well as all ShuffleFileGroups that have been created for the shuffle.
   */
  private  class ShuffleState {
    public  int numBuckets () { throw new RuntimeException(); }
    // not preceding
    public   ShuffleState (int numBuckets) { throw new RuntimeException(); }
    public  java.util.concurrent.atomic.AtomicInteger nextFileId () { throw new RuntimeException(); }
    public  java.util.concurrent.ConcurrentLinkedQueue<org.apache.spark.storage.ShuffleBlockManager.ShuffleFileGroup> unusedFileGroups () { throw new RuntimeException(); }
    public  java.util.concurrent.ConcurrentLinkedQueue<org.apache.spark.storage.ShuffleBlockManager.ShuffleFileGroup> allFileGroups () { throw new RuntimeException(); }
    /**
     * The mapIds of all map tasks completed on this Executor for this shuffle.
     * NB: This is only populated if consolidateShuffleFiles is FALSE. We don't need it otherwise.
     */
    public  java.util.concurrent.ConcurrentLinkedQueue<java.lang.Object> completedMapTasks () { throw new RuntimeException(); }
  }
  /**
   * A group of shuffle files, one per reducer.
   * A particular mapper will be assigned a single ShuffleFileGroup to write its output to.
   */
  static private  class ShuffleFileGroup {
    public  int shuffleId () { throw new RuntimeException(); }
    public  int fileId () { throw new RuntimeException(); }
    public  java.io.File[] files () { throw new RuntimeException(); }
    // not preceding
    public   ShuffleFileGroup (int shuffleId, int fileId, java.io.File[] files) { throw new RuntimeException(); }
    private  int numBlocks () { throw new RuntimeException(); }
    /**
     * Stores the absolute index of each mapId in the files of this group. For instance,
     * if mapId 5 is the first block in each file, mapIdToIndex(5) = 0.
     */
    private  org.apache.spark.util.collection.PrimitiveKeyOpenHashMap<java.lang.Object, java.lang.Object> mapIdToIndex () { throw new RuntimeException(); }
    /**
     * Stores consecutive offsets and lengths of blocks into each reducer file, ordered by
     * position in the file.
     * Note: mapIdToIndex(mapId) returns the index of the mapper into the vector for every
     * reducer.
     */
    private  org.apache.spark.util.collection.PrimitiveVector<java.lang.Object>[] blockOffsetsByReducer () { throw new RuntimeException(); }
    private  org.apache.spark.util.collection.PrimitiveVector<java.lang.Object>[] blockLengthsByReducer () { throw new RuntimeException(); }
    public  java.io.File apply (int bucketId) { throw new RuntimeException(); }
    public  void recordMapOutput (int mapId, long[] offsets, long[] lengths) { throw new RuntimeException(); }
    /** Returns the FileSegment associated with the given map task, or None if no entry exists. */
    public  scala.Option<org.apache.spark.storage.FileSegment> getFileSegmentFor (int mapId, int reducerId) { throw new RuntimeException(); }
  }
  public   ShuffleBlockManager (org.apache.spark.storage.BlockManager blockManager, org.apache.spark.shuffle.ShuffleManager shuffleManager) { throw new RuntimeException(); }
  public  org.apache.spark.SparkConf conf () { throw new RuntimeException(); }
  public  boolean consolidateShuffleFiles () { throw new RuntimeException(); }
  public  boolean sortBasedShuffle () { throw new RuntimeException(); }
  private  int bufferSize () { throw new RuntimeException(); }
  private  org.apache.spark.util.TimeStampedHashMap<java.lang.Object, org.apache.spark.storage.ShuffleBlockManager.ShuffleState> shuffleStates () { throw new RuntimeException(); }
  private  org.apache.spark.util.MetadataCleaner metadataCleaner () { throw new RuntimeException(); }
  /**
   * Register a completed map without getting a ShuffleWriterGroup. Used by sort-based shuffle
   * because it just writes a single file by itself.
   */
  public  void addCompletedMap (int shuffleId, int mapId, int numBuckets) { throw new RuntimeException(); }
  /**
   * Get a ShuffleWriterGroup for the given map task, which will register it as complete
   * when the writers are closed successfully
   */
  public  java.lang.Object forMapTask (int shuffleId, int mapId, int numBuckets, org.apache.spark.serializer.Serializer serializer, org.apache.spark.executor.ShuffleWriteMetrics writeMetrics) { throw new RuntimeException(); }
  /**
   * Returns the physical file segment in which the given BlockId is located.
   * This function should only be called if shuffle file consolidation is enabled, as it is
   * an error condition if we don't find the expected block.
   */
  public  org.apache.spark.storage.FileSegment getBlockLocation (org.apache.spark.storage.ShuffleBlockId id) { throw new RuntimeException(); }
  /** Remove all the blocks / files and metadata related to a particular shuffle. */
  public  boolean removeShuffle (int shuffleId) { throw new RuntimeException(); }
  /** Remove all the blocks / files related to a particular shuffle. */
  private  boolean removeShuffleBlocks (int shuffleId) { throw new RuntimeException(); }
  private  java.lang.String physicalFileName (int shuffleId, int bucketId, int fileId) { throw new RuntimeException(); }
  private  void cleanup (long cleanupTime) { throw new RuntimeException(); }
  public  void stop () { throw new RuntimeException(); }
}
