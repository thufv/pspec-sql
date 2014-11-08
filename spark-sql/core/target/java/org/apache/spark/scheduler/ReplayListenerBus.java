package org.apache.spark.scheduler;
/**
 * A SparkListenerBus that replays logged events from persisted storage.
 * <p>
 * This assumes the given paths are valid log files, where each line can be deserialized into
 * exactly one SparkListenerEvent.
 */
private  class ReplayListenerBus implements org.apache.spark.scheduler.SparkListenerBus, org.apache.spark.Logging {
  public   ReplayListenerBus (scala.collection.Seq<org.apache.hadoop.fs.Path> logPaths, org.apache.hadoop.fs.FileSystem fileSystem, scala.Option<org.apache.spark.io.CompressionCodec> compressionCodec) { throw new RuntimeException(); }
  private  boolean replayed () { throw new RuntimeException(); }
  /**
   * Replay each event in the order maintained in the given logs.
   * This should only be called exactly once.
   */
  public  void replay () { throw new RuntimeException(); }
  /** If a compression codec is specified, wrap the given stream in a compression stream. */
  private  java.io.InputStream wrapForCompression (java.io.InputStream stream) { throw new RuntimeException(); }
}
