package org.apache.spark.broadcast;
/**
 * A {@link org.apache.spark.broadcast.Broadcast} implementation that uses HTTP server
 * as a broadcast mechanism. The first time a HTTP broadcast variable (sent as part of a
 * task) is deserialized in the executor, the broadcasted data is fetched from the driver
 * (through a HTTP server running at the driver) and stored in the BlockManager of the
 * executor to speed up future accesses.
 */
private  class HttpBroadcast<T extends java.lang.Object> extends org.apache.spark.broadcast.Broadcast<T> implements org.apache.spark.Logging, scala.Serializable {
  static private  boolean initialized () { throw new RuntimeException(); }
  static private  java.io.File broadcastDir () { throw new RuntimeException(); }
  static private  boolean compress () { throw new RuntimeException(); }
  static private  int bufferSize () { throw new RuntimeException(); }
  static private  java.lang.String serverUri () { throw new RuntimeException(); }
  static private  org.apache.spark.HttpServer server () { throw new RuntimeException(); }
  static private  org.apache.spark.SecurityManager securityManager () { throw new RuntimeException(); }
  static private  org.apache.spark.util.TimeStampedHashSet<java.io.File> files () { throw new RuntimeException(); }
  static private  int httpReadTimeout () { throw new RuntimeException(); }
  static private  org.apache.spark.io.CompressionCodec compressionCodec () { throw new RuntimeException(); }
  static private  org.apache.spark.util.MetadataCleaner cleaner () { throw new RuntimeException(); }
  static public  void initialize (boolean isDriver, org.apache.spark.SparkConf conf, org.apache.spark.SecurityManager securityMgr) { throw new RuntimeException(); }
  static public  void stop () { throw new RuntimeException(); }
  static private  void createServer (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  static public  java.io.File getFile (long id) { throw new RuntimeException(); }
  static private  void write (long id, Object value) { throw new RuntimeException(); }
  static private <T extends java.lang.Object> T read (long id, scala.reflect.ClassTag<T> evidence$2) { throw new RuntimeException(); }
  /**
   * Remove all persisted blocks associated with this HTTP broadcast on the executors.
   * If removeFromDriver is true, also remove these persisted blocks on the driver
   * and delete the associated broadcast file.
   */
  static public  void unpersist (long id, boolean removeFromDriver, boolean blocking) { throw new RuntimeException(); }
  /**
   * Periodically clean up old broadcasts by removing the associated map entries and
   * deleting the associated files.
   */
  static private  void cleanup (long cleanupTime) { throw new RuntimeException(); }
  static private  void deleteBroadcastFile (java.io.File file) { throw new RuntimeException(); }
  // not preceding
  public  T value_ () { throw new RuntimeException(); }
  // not preceding
  public   HttpBroadcast (T value_, boolean isLocal, long id, scala.reflect.ClassTag<T> evidence$1) { throw new RuntimeException(); }
  protected  T getValue () { throw new RuntimeException(); }
  private  org.apache.spark.storage.BroadcastBlockId blockId () { throw new RuntimeException(); }
  /**
   * Remove all persisted state associated with this HTTP broadcast on the executors.
   */
  protected  void doUnpersist (boolean blocking) { throw new RuntimeException(); }
  /**
   * Remove all persisted state associated with this HTTP broadcast on the executors and driver.
   */
  protected  void doDestroy (boolean blocking) { throw new RuntimeException(); }
  /** Used by the JVM when serializing this object. */
  private  void writeObject (java.io.ObjectOutputStream out) { throw new RuntimeException(); }
  /** Used by the JVM when deserializing this object. */
  private  void readObject (java.io.ObjectInputStream in) { throw new RuntimeException(); }
}
