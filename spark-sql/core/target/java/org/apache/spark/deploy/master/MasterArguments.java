package org.apache.spark.deploy.master;
/**
 * Command-line parser for the master.
 */
private  class MasterArguments {
  public   MasterArguments (java.lang.String[] args, org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  public  java.lang.String host () { throw new RuntimeException(); }
  public  int port () { throw new RuntimeException(); }
  public  int webUiPort () { throw new RuntimeException(); }
  public  void parse (scala.collection.immutable.List<java.lang.String> args) { throw new RuntimeException(); }
  /**
   * Print usage and exit JVM with the given exit code.
   */
  public  void printUsageAndExit (int exitCode) { throw new RuntimeException(); }
}
