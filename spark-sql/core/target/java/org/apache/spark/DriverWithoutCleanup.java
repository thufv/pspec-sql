package org.apache.spark;
// no position
/**
 * Program that creates a Spark driver but doesn't call SparkContext.stop() or
 * Sys.exit() after finishing.
 */
public  class DriverWithoutCleanup {
  static public  void main (java.lang.String[] args) { throw new RuntimeException(); }
}
