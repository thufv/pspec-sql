package org.apache.spark.deploy;
public  class SparkSubmitSuite extends org.scalatest.FunSuite implements org.scalatest.Matchers {
  public   SparkSubmitSuite () { throw new RuntimeException(); }
  public  void beforeAll () { throw new RuntimeException(); }
  public  java.io.OutputStream noOpOutputStream () { throw new RuntimeException(); }
  /** Simple PrintStream that reads data into a buffer */
  public  class BufferPrintStream extends java.io.PrintStream {
    public   BufferPrintStream () { throw new RuntimeException(); }
    public  scala.collection.mutable.ArrayBuffer<java.lang.String> lineBuffer () { throw new RuntimeException(); }
    public  void println (java.lang.String line) { throw new RuntimeException(); }
  }
  /** Returns true if the script exits and the given search string is printed. */
  public  void testPrematureExit (java.lang.String[] input, java.lang.String searchString) { throw new RuntimeException(); }
  public  java.lang.String runSparkSubmit (scala.collection.Seq<java.lang.String> args) { throw new RuntimeException(); }
}
