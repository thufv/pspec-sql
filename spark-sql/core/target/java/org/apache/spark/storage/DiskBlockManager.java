package org.apache.spark.storage;
/**
 * Creates and maintains the logical mapping between logical blocks and physical on-disk
 * locations. By default, one block is mapped to one file with a name given by its BlockId.
 * However, it is also possible to have a block map to only a segment of a file, by calling
 * mapBlockToFileSegment().
 * <p>
 * Block files are hashed among the directories listed in spark.local.dir (or in
 * SPARK_LOCAL_DIRS, if it's set).
 */
private  class DiskBlockManager implements org.apache.spark.network.netty.PathResolver, org.apache.spark.Logging {
  public   DiskBlockManager (org.apache.spark.storage.ShuffleBlockManager shuffleBlockManager, org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  private  int MAX_DIR_CREATION_ATTEMPTS () { throw new RuntimeException(); }
  private  int subDirsPerLocalDir () { throw new RuntimeException(); }
  public  java.io.File[] localDirs () { throw new RuntimeException(); }
  private  java.io.File[][] subDirs () { throw new RuntimeException(); }
  /**
   * Returns the physical file segment in which the given BlockId is located. If the BlockId has
   * been mapped to a specific FileSegment by the shuffle layer, that will be returned.
   * Otherwise, we assume the Block is mapped to the whole file identified by the BlockId.
   */
  public  org.apache.spark.storage.FileSegment getBlockLocation (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
  public  java.io.File getFile (java.lang.String filename) { throw new RuntimeException(); }
  public  java.io.File getFile (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
  /** Check if disk block manager has a block. */
  public  boolean containsBlock (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
  /** List all the files currently stored on disk by the disk manager. */
  public  scala.collection.Seq<java.io.File> getAllFiles () { throw new RuntimeException(); }
  /** List all the blocks currently stored on disk by the disk manager. */
  public  scala.collection.Seq<org.apache.spark.storage.BlockId> getAllBlocks () { throw new RuntimeException(); }
  /** Produces a unique block id and File suitable for intermediate results. */
  public  scala.Tuple2<org.apache.spark.storage.TempBlockId, java.io.File> createTempBlock () { throw new RuntimeException(); }
  private  java.io.File[] createLocalDirs (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  private  void addShutdownHook () { throw new RuntimeException(); }
  /** Cleanup local dirs and stop shuffle sender. */
  private  void stop () { throw new RuntimeException(); }
}
