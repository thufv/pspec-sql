package org.apache.spark.ui;
/**
 * Top level user interface for a Spark application.
 */
private  class SparkUI extends org.apache.spark.ui.WebUI implements org.apache.spark.Logging {
  static public  int DEFAULT_PORT () { throw new RuntimeException(); }
  static public  java.lang.String STATIC_RESOURCE_DIR () { throw new RuntimeException(); }
  static public  int getUIPort (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  public  org.apache.spark.SparkContext sc () { throw new RuntimeException(); }
  public  org.apache.spark.SparkConf conf () { throw new RuntimeException(); }
  public  org.apache.spark.SecurityManager securityManager () { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.SparkListenerBus listenerBus () { throw new RuntimeException(); }
  public  java.lang.String appName () { throw new RuntimeException(); }
  public  java.lang.String basePath () { throw new RuntimeException(); }
  // not preceding
  public   SparkUI (org.apache.spark.SparkContext sc, org.apache.spark.SparkConf conf, org.apache.spark.SecurityManager securityManager, org.apache.spark.scheduler.SparkListenerBus listenerBus, java.lang.String appName, java.lang.String basePath) { throw new RuntimeException(); }
  public   SparkUI (org.apache.spark.SparkContext sc) { throw new RuntimeException(); }
  public   SparkUI (org.apache.spark.SparkConf conf, org.apache.spark.scheduler.SparkListenerBus listenerBus, java.lang.String appName, java.lang.String basePath) { throw new RuntimeException(); }
  public   SparkUI (org.apache.spark.SparkConf conf, org.apache.spark.SecurityManager securityManager, org.apache.spark.scheduler.SparkListenerBus listenerBus, java.lang.String appName, java.lang.String basePath) { throw new RuntimeException(); }
  public  boolean live () { throw new RuntimeException(); }
  public  org.apache.spark.storage.StorageStatusListener storageStatusListener () { throw new RuntimeException(); }
  /** Initialize all components of the server. */
  public  void initialize () { throw new RuntimeException(); }
  public  java.lang.String getAppName () { throw new RuntimeException(); }
  /** Set the app name for this UI. */
  public  void setAppName (java.lang.String name) { throw new RuntimeException(); }
  /** Register the given listener with the listener bus. */
  public  void registerListener (org.apache.spark.scheduler.SparkListener listener) { throw new RuntimeException(); }
  /** Stop the server behind this web interface. Only valid after bind(). */
  public  void stop () { throw new RuntimeException(); }
  /**
   * Return the application UI host:port. This does not include the scheme (http://).
   */
  private  java.lang.String appUIHostPort () { throw new RuntimeException(); }
  private  java.lang.String appUIAddress () { throw new RuntimeException(); }
}
