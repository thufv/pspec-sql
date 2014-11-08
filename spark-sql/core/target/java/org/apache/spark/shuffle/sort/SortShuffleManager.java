package org.apache.spark.shuffle.sort;
private  class SortShuffleManager implements org.apache.spark.shuffle.ShuffleManager {
  public   SortShuffleManager () { throw new RuntimeException(); }
  /**
   * Register a shuffle with the manager and obtain a handle for it to pass to tasks.
   */
  public <K extends java.lang.Object, V extends java.lang.Object, C extends java.lang.Object> org.apache.spark.shuffle.ShuffleHandle registerShuffle (int shuffleId, int numMaps, org.apache.spark.ShuffleDependency<K, V, C> dependency) { throw new RuntimeException(); }
  /**
   * Get a reader for a range of reduce partitions (startPartition to endPartition-1, inclusive).
   * Called on executors by reduce tasks.
   */
  public <K extends java.lang.Object, C extends java.lang.Object> org.apache.spark.shuffle.ShuffleReader<K, C> getReader (org.apache.spark.shuffle.ShuffleHandle handle, int startPartition, int endPartition, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
  /** Get a writer for a given partition. Called on executors by map tasks. */
  public <K extends java.lang.Object, V extends java.lang.Object> org.apache.spark.shuffle.ShuffleWriter<K, V> getWriter (org.apache.spark.shuffle.ShuffleHandle handle, int mapId, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
  /** Remove a shuffle's metadata from the ShuffleManager. */
  public  void unregisterShuffle (int shuffleId) { throw new RuntimeException(); }
  /** Shut down this ShuffleManager. */
  public  void stop () { throw new RuntimeException(); }
  /** Get the location of a block in a map output file. Uses the index file we create for it. */
  public  org.apache.spark.storage.FileSegment getBlockLocation (org.apache.spark.storage.ShuffleBlockId blockId, org.apache.spark.storage.DiskBlockManager diskManager) { throw new RuntimeException(); }
}
