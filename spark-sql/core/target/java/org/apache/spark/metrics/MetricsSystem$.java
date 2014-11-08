package org.apache.spark.metrics;
// no position
private  class MetricsSystem$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final MetricsSystem$ MODULE$ = null;
  public   MetricsSystem$ () { throw new RuntimeException(); }
  public  scala.util.matching.Regex SINK_REGEX () { throw new RuntimeException(); }
  public  scala.util.matching.Regex SOURCE_REGEX () { throw new RuntimeException(); }
  public  java.util.concurrent.TimeUnit MINIMAL_POLL_UNIT () { throw new RuntimeException(); }
  public  int MINIMAL_POLL_PERIOD () { throw new RuntimeException(); }
  public  void checkMinimalPollingPeriod (java.util.concurrent.TimeUnit pollUnit, int pollPeriod) { throw new RuntimeException(); }
  public  org.apache.spark.metrics.MetricsSystem createMetricsSystem (java.lang.String instance, org.apache.spark.SparkConf conf, org.apache.spark.SecurityManager securityMgr) { throw new RuntimeException(); }
}
