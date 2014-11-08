package org.apache.spark.scheduler;
/**
 * Test whether EventLoggingListener logs events properly.
 * <p>
 * This tests whether EventLoggingListener actually creates special files while logging events,
 * whether the parsing of these special files is correct, and whether the logged events can be
 * read and deserialized into actual SparkListenerEvents.
 */
public  class EventLoggingListenerSuite extends org.scalatest.FunSuite implements org.scalatest.BeforeAndAfter {
  /**
   * A listener that asserts certain events are logged by the given EventLoggingListener.
   * This is necessary because events are posted asynchronously in a different thread.
   */
  private  class EventExistenceListener implements org.apache.spark.scheduler.SparkListener {
    public   EventExistenceListener (org.apache.spark.scheduler.EventLoggingListener eventLogger) { throw new RuntimeException(); }
    public  boolean jobStarted () { throw new RuntimeException(); }
    public  boolean jobEnded () { throw new RuntimeException(); }
    public  boolean appEnded () { throw new RuntimeException(); }
    public  void onJobStart (org.apache.spark.scheduler.SparkListenerJobStart jobStart) { throw new RuntimeException(); }
    public  void onJobEnd (org.apache.spark.scheduler.SparkListenerJobEnd jobEnd) { throw new RuntimeException(); }
    public  void onApplicationEnd (org.apache.spark.scheduler.SparkListenerApplicationEnd applicationEnd) { throw new RuntimeException(); }
    public  void assertAllCallbacksInvoked () { throw new RuntimeException(); }
  }
  /** Get a SparkConf with event logging enabled. */
  static public  org.apache.spark.SparkConf getLoggingConf (org.apache.hadoop.fs.Path logDir, scala.Option<java.lang.String> compressionCodec) { throw new RuntimeException(); }
  public   EventLoggingListenerSuite () { throw new RuntimeException(); }
  private  org.apache.hadoop.fs.FileSystem fileSystem () { throw new RuntimeException(); }
  private  scala.collection.Seq<java.lang.String> allCompressionCodecs () { throw new RuntimeException(); }
  private  java.io.File testDir () { throw new RuntimeException(); }
  private  org.apache.hadoop.fs.Path logDirPath () { throw new RuntimeException(); }
  /**
   * Test whether names of special files are correctly identified and parsed.
   */
  private  void testParsingFileName () { throw new RuntimeException(); }
  /**
   * Test whether the special files produced by EventLoggingListener exist.
   * <p>
   * There should be exactly one event log and one spark version file throughout the entire
   * execution. If a compression codec is specified, then the compression codec file should
   * also exist. Only after the application has completed does the test expect the application
   * completed file to be present.
   */
  private  void testSpecialFilesExist (scala.Option<java.lang.String> compressionCodec) { throw new RuntimeException(); }
  /**
   * Test whether EventLoggingListener correctly parses the correct information from the logs.
   * <p>
   * This includes whether it returns the correct Spark version, compression codec (if any),
   * and the application's completion status.
   */
  private  void testParsingLogInfo (scala.Option<java.lang.String> compressionCodec) { throw new RuntimeException(); }
  /**
   * Test basic event logging functionality.
   * <p>
   * This creates two simple events, posts them to the EventLoggingListener, and verifies that
   * exactly these two events are logged in the expected file.
   */
  private  void testEventLogging (scala.Option<java.lang.String> compressionCodec) { throw new RuntimeException(); }
  /**
   * Test end-to-end event logging functionality in an application.
   * This runs a simple Spark job and asserts that the expected events are logged when expected.
   */
  private  void testApplicationEventLogging (scala.Option<java.lang.String> compressionCodec) { throw new RuntimeException(); }
  /**
   * Assert that all of the specified events are logged by the given EventLoggingListener.
   */
  private  void assertEventsExist (org.apache.spark.scheduler.EventLoggingListener eventLogger, scala.collection.Seq<java.lang.String> events) { throw new RuntimeException(); }
  /**
   * Read all lines from the file specified by the given path.
   * If a compression codec is specified, use it to read the file.
   */
  private  scala.collection.Seq<java.lang.String> readFileLines (org.apache.hadoop.fs.Path filePath, scala.Option<org.apache.spark.io.CompressionCodec> compressionCodec) { throw new RuntimeException(); }
  private  boolean eventLogsExist (org.apache.hadoop.fs.FileStatus[] logFiles) { throw new RuntimeException(); }
  private  boolean sparkVersionExists (org.apache.hadoop.fs.FileStatus[] logFiles) { throw new RuntimeException(); }
  private  boolean compressionCodecExists (org.apache.hadoop.fs.FileStatus[] logFiles) { throw new RuntimeException(); }
  private  boolean applicationCompleteExists (org.apache.hadoop.fs.FileStatus[] logFiles) { throw new RuntimeException(); }
  private  void assertSparkVersionIsValid (org.apache.hadoop.fs.FileStatus[] logFiles) { throw new RuntimeException(); }
  private  void assertCompressionCodecIsValid (org.apache.hadoop.fs.FileStatus[] logFiles, java.lang.String compressionCodec) { throw new RuntimeException(); }
}
