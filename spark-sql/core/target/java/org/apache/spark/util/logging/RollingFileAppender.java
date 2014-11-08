package org.apache.spark.util.logging;
/**
 * Continuously appends data from input stream into the given file, and rolls
 * over the file after the given interval. The rolled over files are named
 * based on the given pattern.
 * <p>
 * @param inputStream             Input stream to read data from
 * @param activeFile              File to write data to
 * @param rollingPolicy           Policy based on which files will be rolled over.
 * @param conf                    SparkConf that is used to pass on extra configurations
 * @param bufferSize              Optional buffer size. Used mainly for testing.
 */
private  class RollingFileAppender extends org.apache.spark.util.logging.FileAppender {
  static public  java.lang.String STRATEGY_PROPERTY () { throw new RuntimeException(); }
  static public  java.lang.String STRATEGY_DEFAULT () { throw new RuntimeException(); }
  static public  java.lang.String INTERVAL_PROPERTY () { throw new RuntimeException(); }
  static public  java.lang.String INTERVAL_DEFAULT () { throw new RuntimeException(); }
  static public  java.lang.String SIZE_PROPERTY () { throw new RuntimeException(); }
  static public  java.lang.String SIZE_DEFAULT () { throw new RuntimeException(); }
  static public  java.lang.String RETAINED_FILES_PROPERTY () { throw new RuntimeException(); }
  static public  int DEFAULT_BUFFER_SIZE () { throw new RuntimeException(); }
  /**
   * Get the sorted list of rolled over files. This assumes that the all the rolled
   * over file names are prefixed with the <code>activeFileName</code>, and the active file
   * name has the latest logs. So it sorts all the rolled over logs (that are
   * prefixed with <code>activeFileName</code>) and appends the active file
   */
  static public  scala.collection.Seq<java.io.File> getSortedRolledOverFiles (java.lang.String directory, java.lang.String activeFileName) { throw new RuntimeException(); }
  public  org.apache.spark.util.logging.RollingPolicy rollingPolicy () { throw new RuntimeException(); }
  // not preceding
  public   RollingFileAppender (java.io.InputStream inputStream, java.io.File activeFile, org.apache.spark.util.logging.RollingPolicy rollingPolicy, org.apache.spark.SparkConf conf, int bufferSize) { throw new RuntimeException(); }
  private  int maxRetainedFiles () { throw new RuntimeException(); }
  /** Stop the appender */
  public  void stop () { throw new RuntimeException(); }
  /** Append bytes to file after rolling over is necessary */
  protected  void appendToFile (byte[] bytes, int len) { throw new RuntimeException(); }
  /** Rollover the file, by closing the output stream and moving it over */
  private  void rollover () { throw new RuntimeException(); }
  /** Move the active log file to a new rollover file */
  private  void moveFile () { throw new RuntimeException(); }
  /** Retain only last few files */
  private  void deleteOldFiles () { throw new RuntimeException(); }
}
