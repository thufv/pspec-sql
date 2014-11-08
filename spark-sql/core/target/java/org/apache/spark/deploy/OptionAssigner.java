package org.apache.spark.deploy;
/**
 * Provides an indirection layer for passing arguments as system properties or flags to
 * the user's driver program or to downstream launcher tools.
 */
private  class OptionAssigner implements scala.Product, scala.Serializable {
  public  java.lang.String value () { throw new RuntimeException(); }
  public  int clusterManager () { throw new RuntimeException(); }
  public  int deployMode () { throw new RuntimeException(); }
  public  java.lang.String clOption () { throw new RuntimeException(); }
  public  java.lang.String sysProp () { throw new RuntimeException(); }
  // not preceding
  public   OptionAssigner (java.lang.String value, int clusterManager, int deployMode, java.lang.String clOption, java.lang.String sysProp) { throw new RuntimeException(); }
}
