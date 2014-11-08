package org.apache.spark.broadcast;
/**
 * A {@link org.apache.spark.broadcast.Broadcast} implementation that uses a BitTorrent-like
 * protocol to do a distributed transfer of the broadcasted data to the executors. Refer to
 * {@link org.apache.spark.broadcast.TorrentBroadcast} for more details.
 */
public  class TorrentBroadcastFactory implements org.apache.spark.broadcast.BroadcastFactory {
  public   TorrentBroadcastFactory () { throw new RuntimeException(); }
  public  void initialize (boolean isDriver, org.apache.spark.SparkConf conf, org.apache.spark.SecurityManager securityMgr) { throw new RuntimeException(); }
  public <T extends java.lang.Object> org.apache.spark.broadcast.TorrentBroadcast<T> newBroadcast (T value_, boolean isLocal, long id, scala.reflect.ClassTag<T> evidence$1) { throw new RuntimeException(); }
  public  void stop () { throw new RuntimeException(); }
  /**
   * Remove all persisted state associated with the torrent broadcast with the given ID.
   * @param removeFromDriver Whether to remove state from the driver.
   * @param blocking Whether to block until unbroadcasted
   */
  public  void unbroadcast (long id, boolean removeFromDriver, boolean blocking) { throw new RuntimeException(); }
}
