package org.apache.spark.scheduler;
// no position
private  class EventLoggingListener$ implements org.apache.spark.Logging {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final EventLoggingListener$ MODULE$ = null;
  public   EventLoggingListener$ () { throw new RuntimeException(); }
  public  java.lang.String DEFAULT_LOG_DIR () { throw new RuntimeException(); }
  public  java.lang.String LOG_PREFIX () { throw new RuntimeException(); }
  public  java.lang.String SPARK_VERSION_PREFIX () { throw new RuntimeException(); }
  public  java.lang.String COMPRESSION_CODEC_PREFIX () { throw new RuntimeException(); }
  public  java.lang.String APPLICATION_COMPLETE () { throw new RuntimeException(); }
  public  org.apache.hadoop.fs.permission.FsPermission LOG_FILE_PERMISSIONS () { throw new RuntimeException(); }
  private  scala.collection.mutable.HashMap<java.lang.String, org.apache.spark.io.CompressionCodec> codecMap () { throw new RuntimeException(); }
  public  boolean isEventLogFile (java.lang.String fileName) { throw new RuntimeException(); }
  public  boolean isSparkVersionFile (java.lang.String fileName) { throw new RuntimeException(); }
  public  boolean isCompressionCodecFile (java.lang.String fileName) { throw new RuntimeException(); }
  public  boolean isApplicationCompleteFile (java.lang.String fileName) { throw new RuntimeException(); }
  public  java.lang.String parseSparkVersion (java.lang.String fileName) { throw new RuntimeException(); }
  public  java.lang.String parseCompressionCodec (java.lang.String fileName) { throw new RuntimeException(); }
  /**
   * Parse the event logging information associated with the logs in the given directory.
   * <p>
   * Specifically, this looks for event log files, the Spark version file, the compression
   * codec file (if event logs are compressed), and the application completion file (if the
   * application has run to completion).
   */
  public  org.apache.spark.scheduler.EventLoggingInfo parseLoggingInfo (org.apache.hadoop.fs.Path logDir, org.apache.hadoop.fs.FileSystem fileSystem) { throw new RuntimeException(); }
  /**
   * Parse the event logging information associated with the logs in the given directory.
   */
  public  org.apache.spark.scheduler.EventLoggingInfo parseLoggingInfo (java.lang.String logDir, org.apache.hadoop.fs.FileSystem fileSystem) { throw new RuntimeException(); }
}
