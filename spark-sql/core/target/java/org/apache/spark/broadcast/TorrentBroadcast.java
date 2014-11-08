package org.apache.spark.broadcast;
/**
 * A BitTorrent-like implementation of {@link org.apache.spark.broadcast.Broadcast}.
 * <p>
 * The mechanism is as follows:
 * <p>
 * The driver divides the serialized object into small chunks and
 * stores those chunks in the BlockManager of the driver.
 * <p>
 * On each executor, the executor first attempts to fetch the object from its BlockManager. If
 * it does not exist, it then uses remote fetches to fetch the small chunks from the driver and/or
 * other executors if available. Once it gets the chunks, it puts the chunks in its own
 * BlockManager, ready for other executors to fetch from.
 * <p>
 * This prevents the driver from being the bottleneck in sending out multiple copies of the
 * broadcast data (one per executor) as done by the {@link org.apache.spark.broadcast.HttpBroadcast}.
 * <p>
 * @param obj object to broadcast
 * @param isLocal whether Spark is running in local mode (single JVM process).
 * @param id A unique identifier for the broadcast variable.
 */
private  class TorrentBroadcast<T extends java.lang.Object> extends org.apache.spark.broadcast.Broadcast<T> implements org.apache.spark.Logging, java.io.Serializable {
  /** Size of each block. Default value is 4MB. */
  static private  int BLOCK_SIZE () { throw new RuntimeException(); }
  static private  boolean initialized () { throw new RuntimeException(); }
  static private  org.apache.spark.SparkConf conf () { throw new RuntimeException(); }
  static private  boolean compress () { throw new RuntimeException(); }
  static private  org.apache.spark.io.CompressionCodec compressionCodec () { throw new RuntimeException(); }
  static public  void initialize (boolean _isDriver, org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  static public  void stop () { throw new RuntimeException(); }
  static public <T extends java.lang.Object> java.nio.ByteBuffer[] blockifyObject (T obj, scala.reflect.ClassTag<T> evidence$2) { throw new RuntimeException(); }
  static public <T extends java.lang.Object> T unBlockifyObject (java.nio.ByteBuffer[] blocks, scala.reflect.ClassTag<T> evidence$3) { throw new RuntimeException(); }
  /**
   * Remove all persisted blocks associated with this torrent broadcast on the executors.
   * If removeFromDriver is true, also remove these persisted blocks on the driver.
   */
  static public  void unpersist (long id, boolean removeFromDriver, boolean blocking) { throw new RuntimeException(); }
  // not preceding
  private  boolean isLocal () { throw new RuntimeException(); }
  // not preceding
  public   TorrentBroadcast (T obj, boolean isLocal, long id, scala.reflect.ClassTag<T> evidence$1) { throw new RuntimeException(); }
  /**
   * Value of the broadcast object. On driver, this is set directly by the constructor.
   * On executors, this is reconstructed by {@link readObject}, which builds this value by reading
   * blocks from the driver and/or other executors.
   */
  private  T _value () { throw new RuntimeException(); }
  private  org.apache.spark.storage.BroadcastBlockId broadcastId () { throw new RuntimeException(); }
  /** Total number of blocks this broadcast variable contains. */
  private  int numBlocks () { throw new RuntimeException(); }
  protected  T getValue () { throw new RuntimeException(); }
  /**
   * Divide the object into multiple blocks and put those blocks in the block manager.
   * <p>
   * @return number of blocks this broadcast variable is divided into
   */
  private  int writeBlocks () { throw new RuntimeException(); }
  /** Fetch torrent blocks from the driver and/or other executors. */
  private  java.nio.ByteBuffer[] readBlocks () { throw new RuntimeException(); }
  /**
   * Remove all persisted state associated with this Torrent broadcast on the executors.
   */
  protected  void doUnpersist (boolean blocking) { throw new RuntimeException(); }
  /**
   * Remove all persisted state associated with this Torrent broadcast on the executors
   * and driver.
   */
  protected  void doDestroy (boolean blocking) { throw new RuntimeException(); }
  /** Used by the JVM when serializing this object. */
  private  void writeObject (java.io.ObjectOutputStream out) { throw new RuntimeException(); }
  /** Used by the JVM when deserializing this object. */
  private  void readObject (java.io.ObjectInputStream in) { throw new RuntimeException(); }
}
