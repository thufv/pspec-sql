package org.apache.spark.storage;
private  class BlockInfo {
  static private  java.util.concurrent.ConcurrentHashMap<org.apache.spark.storage.BlockInfo, java.lang.Thread> blockInfoInitThreads () { throw new RuntimeException(); }
  static private  long BLOCK_PENDING () { throw new RuntimeException(); }
  static private  long BLOCK_FAILED () { throw new RuntimeException(); }
  public  org.apache.spark.storage.StorageLevel level () { throw new RuntimeException(); }
  public  boolean tellMaster () { throw new RuntimeException(); }
  // not preceding
  public   BlockInfo (org.apache.spark.storage.StorageLevel level, boolean tellMaster) { throw new RuntimeException(); }
  public  long size () { throw new RuntimeException(); }
  private  boolean pending () { throw new RuntimeException(); }
  private  boolean failed () { throw new RuntimeException(); }
  private  java.lang.Thread initThread () { throw new RuntimeException(); }
  private  void setInitThread () { throw new RuntimeException(); }
  /**
   * Wait for this BlockInfo to be marked as ready (i.e. block is finished writing).
   * Return true if the block is available, false otherwise.
   */
  public  boolean waitForReady () { throw new RuntimeException(); }
  /** Mark this BlockInfo as ready (i.e. block is finished writing) */
  public  void markReady (long sizeInBytes) { throw new RuntimeException(); }
  /** Mark this BlockInfo as ready but failed */
  public  void markFailure () { throw new RuntimeException(); }
}
