package org.apache.spark.util;
/**
 * Test writing files through the FileLogger.
 */
public  class FileLoggerSuite extends org.scalatest.FunSuite implements org.scalatest.BeforeAndAfter {
  public   FileLoggerSuite () { throw new RuntimeException(); }
  private  org.apache.hadoop.fs.FileSystem fileSystem () { throw new RuntimeException(); }
  private  scala.collection.Seq<java.lang.String> allCompressionCodecs () { throw new RuntimeException(); }
  private  java.io.File testDir () { throw new RuntimeException(); }
  private  org.apache.hadoop.fs.Path logDirPath () { throw new RuntimeException(); }
  private  java.lang.String logDirPathString () { throw new RuntimeException(); }
  /**
   * Test logging to a single file.
   */
  private  void testSingleFile (scala.Option<java.lang.String> codecName) { throw new RuntimeException(); }
  /**
   * Test logging to multiple files.
   */
  private  void testMultipleFiles (scala.Option<java.lang.String> codecName) { throw new RuntimeException(); }
  /**
   * Read the content of the file specified by the given path.
   * If a compression codec is specified, use it to read the file.
   */
  private  java.lang.String readFileContent (org.apache.hadoop.fs.Path logPath, scala.Option<org.apache.spark.io.CompressionCodec> codec) { throw new RuntimeException(); }
  private  org.apache.spark.SparkConf getLoggingConf (scala.Option<java.lang.String> codecName) { throw new RuntimeException(); }
}
