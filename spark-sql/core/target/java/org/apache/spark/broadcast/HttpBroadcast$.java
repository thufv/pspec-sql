package org.apache.spark.broadcast;
// no position
private  class HttpBroadcast$ implements org.apache.spark.Logging, scala.Serializable {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final HttpBroadcast$ MODULE$ = null;
  public   HttpBroadcast$ () { throw new RuntimeException(); }
  private  boolean initialized () { throw new RuntimeException(); }
  private  java.io.File broadcastDir () { throw new RuntimeException(); }
  private  boolean compress () { throw new RuntimeException(); }
  private  int bufferSize () { throw new RuntimeException(); }
  private  java.lang.String serverUri () { throw new RuntimeException(); }
  private  org.apache.spark.HttpServer server () { throw new RuntimeException(); }
  private  org.apache.spark.SecurityManager securityManager () { throw new RuntimeException(); }
  private  org.apache.spark.util.TimeStampedHashSet<java.io.File> files () { throw new RuntimeException(); }
  private  int httpReadTimeout () { throw new RuntimeException(); }
  private  org.apache.spark.io.CompressionCodec compressionCodec () { throw new RuntimeException(); }
  private  org.apache.spark.util.MetadataCleaner cleaner () { throw new RuntimeException(); }
  public  void initialize (boolean isDriver, org.apache.spark.SparkConf conf, org.apache.spark.SecurityManager securityMgr) { throw new RuntimeException(); }
  public  void stop () { throw new RuntimeException(); }
  private  void createServer (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  public  java.io.File getFile (long id) { throw new RuntimeException(); }
  private  void write (long id, Object value) { throw new RuntimeException(); }
  private <T extends java.lang.Object> T read (long id, scala.reflect.ClassTag<T> evidence$2) { throw new RuntimeException(); }
  /**
   * Remove all persisted blocks associated with this HTTP broadcast on the executors.
   * If removeFromDriver is true, also remove these persisted blocks on the driver
   * and delete the associated broadcast file.
   */
  public  void unpersist (long id, boolean removeFromDriver, boolean blocking) { throw new RuntimeException(); }
  /**
   * Periodically clean up old broadcasts by removing the associated map entries and
   * deleting the associated files.
   */
  private  void cleanup (long cleanupTime) { throw new RuntimeException(); }
  private  void deleteBroadcastFile (java.io.File file) { throw new RuntimeException(); }
}
