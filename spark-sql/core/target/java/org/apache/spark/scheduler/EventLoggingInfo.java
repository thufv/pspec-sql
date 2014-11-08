package org.apache.spark.scheduler;
/**
 * Information needed to process the event logs associated with an application.
 */
private  class EventLoggingInfo implements scala.Product, scala.Serializable {
  static public  org.apache.spark.scheduler.EventLoggingInfo empty () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.hadoop.fs.Path> logPaths () { throw new RuntimeException(); }
  public  java.lang.String sparkVersion () { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.io.CompressionCodec> compressionCodec () { throw new RuntimeException(); }
  public  boolean applicationComplete () { throw new RuntimeException(); }
  // not preceding
  public   EventLoggingInfo (scala.collection.Seq<org.apache.hadoop.fs.Path> logPaths, java.lang.String sparkVersion, scala.Option<org.apache.spark.io.CompressionCodec> compressionCodec, boolean applicationComplete) { throw new RuntimeException(); }
}
