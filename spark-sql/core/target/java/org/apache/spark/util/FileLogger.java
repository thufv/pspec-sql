package org.apache.spark.util;
/**
 * A generic class for logging information to file.
 * <p>
 * @param logDir Path to the directory in which files are logged
 * @param outputBufferSize The buffer size to use when writing to an output stream in bytes
 * @param compress Whether to compress output
 * @param overwrite Whether to overwrite existing files
 */
private  class FileLogger implements org.apache.spark.Logging {
  public   FileLogger (java.lang.String logDir, org.apache.spark.SparkConf sparkConf, org.apache.hadoop.conf.Configuration hadoopConf, int outputBufferSize, boolean compress, boolean overwrite, scala.Option<org.apache.hadoop.fs.permission.FsPermission> dirPermissions) { throw new RuntimeException(); }
  private  java.lang.ThreadLocal<java.text.SimpleDateFormat> dateFormat () { throw new RuntimeException(); }
  /**
   * To avoid effects of FileSystem#close or FileSystem.closeAll called from other modules,
   * create unique FileSystem instance only for FileLogger
   */
  private  org.apache.hadoop.fs.FileSystem fileSystem () { throw new RuntimeException(); }
  public  int fileIndex () { throw new RuntimeException(); }
  private  org.apache.spark.io.CompressionCodec compressionCodec () { throw new RuntimeException(); }
  private  scala.Option<org.apache.hadoop.fs.FSDataOutputStream> hadoopDataStream () { throw new RuntimeException(); }
  private  java.lang.reflect.Method hadoopFlushMethod () { throw new RuntimeException(); }
  private  scala.Option<java.io.PrintWriter> writer () { throw new RuntimeException(); }
  /**
   * Start this logger by creating the logging directory.
   */
  public  void start () { throw new RuntimeException(); }
  /**
   * Create a logging directory with the given path.
   */
  private  void createLogDir () { throw new RuntimeException(); }
  /**
   * Create a new writer for the file identified by the given path.
   * If the permissions are not passed in, it will default to use the permissions
   * (dirPermissions) used when class was instantiated.
   */
  private  java.io.PrintWriter createWriter (java.lang.String fileName, scala.Option<org.apache.hadoop.fs.permission.FsPermission> perms) { throw new RuntimeException(); }
  /**
   * Log the message to the given writer.
   * @param msg The message to be logged
   * @param withTime Whether to prepend message with a timestamp
   */
  public  void log (java.lang.String msg, boolean withTime) { throw new RuntimeException(); }
  /**
   * Log the message to the given writer as a new line.
   * @param msg The message to be logged
   * @param withTime Whether to prepend message with a timestamp
   */
  public  void logLine (java.lang.String msg, boolean withTime) { throw new RuntimeException(); }
  /**
   * Flush the writer to disk manually.
   * <p>
   * When using a Hadoop filesystem, we need to invoke the hflush or sync
   * method. In HDFS, hflush guarantees that the data gets to all the
   * DataNodes.
   */
  public  void flush () { throw new RuntimeException(); }
  /**
   * Close the writer. Any subsequent calls to log or flush will have no effect.
   */
  public  void close () { throw new RuntimeException(); }
  /**
   * Start a writer for a new file, closing the existing one if it exists.
   * @param fileName Name of the new file, defaulting to the file index if not provided.
   * @param perms Permissions to put on the new file.
   */
  public  void newFile (java.lang.String fileName, scala.Option<org.apache.hadoop.fs.permission.FsPermission> perms) { throw new RuntimeException(); }
  /**
   * Close all open writers, streams, and file systems. Any subsequent uses of this FileLogger
   * instance will throw exceptions.
   */
  public  void stop () { throw new RuntimeException(); }
}
