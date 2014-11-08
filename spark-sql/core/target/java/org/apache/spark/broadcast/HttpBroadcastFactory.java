package org.apache.spark.broadcast;
/**
 * A {@link org.apache.spark.broadcast.BroadcastFactory} implementation that uses a
 * HTTP server as the broadcast mechanism. Refer to
 * {@link org.apache.spark.broadcast.HttpBroadcast} for more details about this mechanism.
 */
public  class HttpBroadcastFactory implements org.apache.spark.broadcast.BroadcastFactory {
  public   HttpBroadcastFactory () { throw new RuntimeException(); }
  public  void initialize (boolean isDriver, org.apache.spark.SparkConf conf, org.apache.spark.SecurityManager securityMgr) { throw new RuntimeException(); }
  public <T extends java.lang.Object> org.apache.spark.broadcast.HttpBroadcast<T> newBroadcast (T value_, boolean isLocal, long id, scala.reflect.ClassTag<T> evidence$1) { throw new RuntimeException(); }
  public  void stop () { throw new RuntimeException(); }
  /**
   * Remove all persisted state associated with the HTTP broadcast with the given ID.
   * @param removeFromDriver Whether to remove state from the driver
   * @param blocking Whether to block until unbroadcasted
   */
  public  void unbroadcast (long id, boolean removeFromDriver, boolean blocking) { throw new RuntimeException(); }
}
