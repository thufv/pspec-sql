package org.apache.spark.deploy.history;
// no position
/**
 * The recommended way of starting and stopping a HistoryServer is through the scripts
 * start-history-server.sh and stop-history-server.sh. The path to a base log directory
 * is must be specified, while the requested UI port is optional. For example:
 * <p>
 *   ./sbin/spark-history-server.sh /tmp/spark-events
 *   ./sbin/spark-history-server.sh hdfs://1.2.3.4:9000/spark-events
 * <p>
 * This launches the HistoryServer as a Spark daemon.
 */
public  class HistoryServer$ implements org.apache.spark.Logging {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final HistoryServer$ MODULE$ = null;
  public   HistoryServer$ () { throw new RuntimeException(); }
  private  org.apache.spark.SparkConf conf () { throw new RuntimeException(); }
  public  java.lang.String UI_PATH_PREFIX () { throw new RuntimeException(); }
  public  void main (java.lang.String[] argStrings) { throw new RuntimeException(); }
  public  void initSecurity () { throw new RuntimeException(); }
}
