package org.apache.spark.deploy;
/**
 * Command-line parser for the driver client.
 */
private  class ClientArguments {
  static public  boolean isValidJarUrl (java.lang.String s) { throw new RuntimeException(); }
  public   ClientArguments (java.lang.String[] args) { throw new RuntimeException(); }
  public  int defaultCores () { throw new RuntimeException(); }
  public  int defaultMemory () { throw new RuntimeException(); }
  public  java.lang.String cmd () { throw new RuntimeException(); }
  public  org.apache.log4j.Level logLevel () { throw new RuntimeException(); }
  public  java.lang.String master () { throw new RuntimeException(); }
  public  java.lang.String jarUrl () { throw new RuntimeException(); }
  public  java.lang.String mainClass () { throw new RuntimeException(); }
  public  boolean supervise () { throw new RuntimeException(); }
  public  int memory () { throw new RuntimeException(); }
  public  int cores () { throw new RuntimeException(); }
  private  scala.collection.mutable.ListBuffer<java.lang.String> _driverOptions () { throw new RuntimeException(); }
  public  scala.collection.Seq<java.lang.String> driverOptions () { throw new RuntimeException(); }
  public  java.lang.String driverId () { throw new RuntimeException(); }
  public  void parse (scala.collection.immutable.List<java.lang.String> args) { throw new RuntimeException(); }
  /**
   * Print usage and exit JVM with the given exit code.
   */
  public  void printUsageAndExit (int exitCode) { throw new RuntimeException(); }
}
