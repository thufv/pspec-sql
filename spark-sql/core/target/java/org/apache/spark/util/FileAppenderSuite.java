package org.apache.spark.util;
public  class FileAppenderSuite extends org.scalatest.FunSuite implements org.scalatest.BeforeAndAfter, org.apache.spark.Logging {
  public   FileAppenderSuite () { throw new RuntimeException(); }
  public  java.io.File testFile () { throw new RuntimeException(); }
  /**
   * Run the rolling file appender with data and see whether all the data was written correctly
   * across rolled over files.
   */
  public  scala.collection.Seq<java.io.File> testRolling (org.apache.spark.util.logging.FileAppender appender, java.io.OutputStream outputStream, scala.collection.Seq<java.lang.String> textToAppend, long sleepTimeBetweenTexts) { throw new RuntimeException(); }
  /** Delete all the generated rolledover files */
  public  void cleanup () { throw new RuntimeException(); }
}
