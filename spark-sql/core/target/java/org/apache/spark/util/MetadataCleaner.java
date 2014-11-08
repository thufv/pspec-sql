package org.apache.spark.util;
/**
 * Runs a timer task to periodically clean up metadata (e.g. old files or hashtable entries)
 */
private  class MetadataCleaner implements org.apache.spark.Logging {
  static public  int getDelaySeconds (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  static public  int getDelaySeconds (org.apache.spark.SparkConf conf, scala.Enumeration.Value cleanerType) { throw new RuntimeException(); }
  static public  void setDelaySeconds (org.apache.spark.SparkConf conf, scala.Enumeration.Value cleanerType, int delay) { throw new RuntimeException(); }
  /**
   * Set the default delay time (in seconds).
   * @param conf SparkConf instance
   * @param delay default delay time to set
   * @param resetAll whether to reset all to default
   */
  static public  void setDelaySeconds (org.apache.spark.SparkConf conf, int delay, boolean resetAll) { throw new RuntimeException(); }
  public   MetadataCleaner (scala.Enumeration.Value cleanerType, scala.Function1<java.lang.Object, scala.runtime.BoxedUnit> cleanupFunc, org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  public  java.lang.String name () { throw new RuntimeException(); }
  private  int delaySeconds () { throw new RuntimeException(); }
  private  int periodSeconds () { throw new RuntimeException(); }
  private  java.util.Timer timer () { throw new RuntimeException(); }
  private  java.util.TimerTask task () { throw new RuntimeException(); }
  public  void cancel () { throw new RuntimeException(); }
}
