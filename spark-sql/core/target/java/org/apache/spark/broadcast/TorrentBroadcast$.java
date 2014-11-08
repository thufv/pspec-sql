package org.apache.spark.broadcast;
// no position
private  class TorrentBroadcast$ implements org.apache.spark.Logging, scala.Serializable {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final TorrentBroadcast$ MODULE$ = null;
  public   TorrentBroadcast$ () { throw new RuntimeException(); }
  /** Size of each block. Default value is 4MB. */
  private  int BLOCK_SIZE () { throw new RuntimeException(); }
  private  boolean initialized () { throw new RuntimeException(); }
  private  org.apache.spark.SparkConf conf () { throw new RuntimeException(); }
  private  boolean compress () { throw new RuntimeException(); }
  private  org.apache.spark.io.CompressionCodec compressionCodec () { throw new RuntimeException(); }
  public  void initialize (boolean _isDriver, org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  public  void stop () { throw new RuntimeException(); }
  public <T extends java.lang.Object> java.nio.ByteBuffer[] blockifyObject (T obj, scala.reflect.ClassTag<T> evidence$2) { throw new RuntimeException(); }
  public <T extends java.lang.Object> T unBlockifyObject (java.nio.ByteBuffer[] blocks, scala.reflect.ClassTag<T> evidence$3) { throw new RuntimeException(); }
  /**
   * Remove all persisted blocks associated with this torrent broadcast on the executors.
   * If removeFromDriver is true, also remove these persisted blocks on the driver.
   */
  public  void unpersist (long id, boolean removeFromDriver, boolean blocking) { throw new RuntimeException(); }
}
