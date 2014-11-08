package org.apache.spark.scheduler;
/**
 * A SparkListener that logs events to persistent storage.
 * <p>
 * Event logging is specified by the following configurable parameters:
 *   spark.eventLog.enabled - Whether event logging is enabled.
 *   spark.eventLog.compress - Whether to compress logged events
 *   spark.eventLog.overwrite - Whether to overwrite any existing files.
 *   spark.eventLog.dir - Path to the directory in which events are logged.
 *   spark.eventLog.buffer.kb - Buffer size to use when writing to output streams
 */
private  class EventLoggingListener implements org.apache.spark.scheduler.SparkListener, org.apache.spark.Logging {
  static public  java.lang.String DEFAULT_LOG_DIR () { throw new RuntimeException(); }
  static public  java.lang.String LOG_PREFIX () { throw new RuntimeException(); }
  static public  java.lang.String SPARK_VERSION_PREFIX () { throw new RuntimeException(); }
  static public  java.lang.String COMPRESSION_CODEC_PREFIX () { throw new RuntimeException(); }
  static public  java.lang.String APPLICATION_COMPLETE () { throw new RuntimeException(); }
  static public  org.apache.hadoop.fs.permission.FsPermission LOG_FILE_PERMISSIONS () { throw new RuntimeException(); }
  static private  scala.collection.mutable.HashMap<java.lang.String, org.apache.spark.io.CompressionCodec> codecMap () { throw new RuntimeException(); }
  static public  boolean isEventLogFile (java.lang.String fileName) { throw new RuntimeException(); }
  static public  boolean isSparkVersionFile (java.lang.String fileName) { throw new RuntimeException(); }
  static public  boolean isCompressionCodecFile (java.lang.String fileName) { throw new RuntimeException(); }
  static public  boolean isApplicationCompleteFile (java.lang.String fileName) { throw new RuntimeException(); }
  static public  java.lang.String parseSparkVersion (java.lang.String fileName) { throw new RuntimeException(); }
  static public  java.lang.String parseCompressionCodec (java.lang.String fileName) { throw new RuntimeException(); }
  /**
   * Parse the event logging information associated with the logs in the given directory.
   * <p>
   * Specifically, this looks for event log files, the Spark version file, the compression
   * codec file (if event logs are compressed), and the application completion file (if the
   * application has run to completion).
   */
  static public  org.apache.spark.scheduler.EventLoggingInfo parseLoggingInfo (org.apache.hadoop.fs.Path logDir, org.apache.hadoop.fs.FileSystem fileSystem) { throw new RuntimeException(); }
  /**
   * Parse the event logging information associated with the logs in the given directory.
   */
  static public  org.apache.spark.scheduler.EventLoggingInfo parseLoggingInfo (java.lang.String logDir, org.apache.hadoop.fs.FileSystem fileSystem) { throw new RuntimeException(); }
  public   EventLoggingListener (java.lang.String appName, org.apache.spark.SparkConf sparkConf, org.apache.hadoop.conf.Configuration hadoopConf) { throw new RuntimeException(); }
  private  boolean shouldCompress () { throw new RuntimeException(); }
  private  boolean shouldOverwrite () { throw new RuntimeException(); }
  private  boolean testing () { throw new RuntimeException(); }
  private  int outputBufferSize () { throw new RuntimeException(); }
  private  java.lang.String logBaseDir () { throw new RuntimeException(); }
  private  java.lang.String name () { throw new RuntimeException(); }
  public  java.lang.String logDir () { throw new RuntimeException(); }
  protected  org.apache.spark.util.FileLogger logger () { throw new RuntimeException(); }
  public  scala.collection.mutable.ArrayBuffer<org.json4s.JsonAST.JValue> loggedEvents () { throw new RuntimeException(); }
  /**
   * Return only the unique application directory without the base directory.
   */
  public  java.lang.String getApplicationLogDir () { throw new RuntimeException(); }
  /**
   * Begin logging events.
   * If compression is used, log a file that indicates which compression library is used.
   */
  public  void start () { throw new RuntimeException(); }
  /** Log the event as JSON. */
  private  void logEvent (org.apache.spark.scheduler.SparkListenerEvent event, boolean flushLogger) { throw new RuntimeException(); }
  public  void onStageSubmitted (org.apache.spark.scheduler.SparkListenerStageSubmitted event) { throw new RuntimeException(); }
  public  void onTaskStart (org.apache.spark.scheduler.SparkListenerTaskStart event) { throw new RuntimeException(); }
  public  void onTaskGettingResult (org.apache.spark.scheduler.SparkListenerTaskGettingResult event) { throw new RuntimeException(); }
  public  void onTaskEnd (org.apache.spark.scheduler.SparkListenerTaskEnd event) { throw new RuntimeException(); }
  public  void onEnvironmentUpdate (org.apache.spark.scheduler.SparkListenerEnvironmentUpdate event) { throw new RuntimeException(); }
  public  void onStageCompleted (org.apache.spark.scheduler.SparkListenerStageCompleted event) { throw new RuntimeException(); }
  public  void onJobStart (org.apache.spark.scheduler.SparkListenerJobStart event) { throw new RuntimeException(); }
  public  void onJobEnd (org.apache.spark.scheduler.SparkListenerJobEnd event) { throw new RuntimeException(); }
  public  void onBlockManagerAdded (org.apache.spark.scheduler.SparkListenerBlockManagerAdded event) { throw new RuntimeException(); }
  public  void onBlockManagerRemoved (org.apache.spark.scheduler.SparkListenerBlockManagerRemoved event) { throw new RuntimeException(); }
  public  void onUnpersistRDD (org.apache.spark.scheduler.SparkListenerUnpersistRDD event) { throw new RuntimeException(); }
  public  void onApplicationStart (org.apache.spark.scheduler.SparkListenerApplicationStart event) { throw new RuntimeException(); }
  public  void onApplicationEnd (org.apache.spark.scheduler.SparkListenerApplicationEnd event) { throw new RuntimeException(); }
  public  void onExecutorMetricsUpdate (org.apache.spark.scheduler.SparkListenerExecutorMetricsUpdate event) { throw new RuntimeException(); }
  /**
   * Stop logging events.
   * In addition, create an empty special file to indicate application completion.
   */
  public  void stop () { throw new RuntimeException(); }
}
