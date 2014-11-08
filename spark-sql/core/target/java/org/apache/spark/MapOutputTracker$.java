package org.apache.spark;
// no position
private  class MapOutputTracker$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final MapOutputTracker$ MODULE$ = null;
  public   MapOutputTracker$ () { throw new RuntimeException(); }
  private  double LOG_BASE () { throw new RuntimeException(); }
  public  byte[] serializeMapStatuses (org.apache.spark.scheduler.MapStatus[] statuses) { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.MapStatus[] deserializeMapStatuses (byte[] bytes) { throw new RuntimeException(); }
  private  scala.Tuple2<org.apache.spark.storage.BlockManagerId, java.lang.Object>[] convertMapStatuses (int shuffleId, int reduceId, org.apache.spark.scheduler.MapStatus[] statuses) { throw new RuntimeException(); }
  /**
   * Compress a size in bytes to 8 bits for efficient reporting of map output sizes.
   * We do this by encoding the log base 1.1 of the size as an integer, which can support
   * sizes up to 35 GB with at most 10% error.
   */
  public  byte compressSize (long size) { throw new RuntimeException(); }
  /**
   * Decompress an 8-bit encoded block size, using the reverse operation of compressSize.
   */
  public  long decompressSize (byte compressedSize) { throw new RuntimeException(); }
}
