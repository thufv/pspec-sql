package org.apache.spark;
// no position
private  class SparkConf$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final SparkConf$ MODULE$ = null;
  public   SparkConf$ () { throw new RuntimeException(); }
  /**
   * Return whether the given config is an akka config (e.g. akka.actor.provider).
   * Note that this does not include spark-specific akka configs (e.g. spark.akka.timeout).
   */
  public  boolean isAkkaConf (java.lang.String name) { throw new RuntimeException(); }
  /**
   * Return whether the given config should be passed to an executor on start-up.
   * <p>
   * Certain akka and authentication configs are required of the executor when it connects to
   * the scheduler, while the rest of the spark configs can be inherited from the driver later.
   */
  public  boolean isExecutorStartupConf (java.lang.String name) { throw new RuntimeException(); }
  /**
   * Return whether the given config is a Spark port config.
   */
  public  boolean isSparkPortConf (java.lang.String name) { throw new RuntimeException(); }
}
