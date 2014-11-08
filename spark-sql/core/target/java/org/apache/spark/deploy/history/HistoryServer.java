package org.apache.spark.deploy.history;
/**
 * A web server that renders SparkUIs of completed applications.
 * <p>
 * For the standalone mode, MasterWebUI already achieves this functionality. Thus, the
 * main use case of the HistoryServer is in other deploy modes (e.g. Yarn or Mesos).
 * <p>
 * The logging directory structure is as follows: Within the given base directory, each
 * application's event logs are maintained in the application's own sub-directory. This
 * is the same structure as maintained in the event log write code path in
 * EventLoggingListener.
 */
public  class HistoryServer extends org.apache.spark.ui.WebUI implements org.apache.spark.Logging {
  static private  org.apache.spark.SparkConf conf () { throw new RuntimeException(); }
  static public  java.lang.String UI_PATH_PREFIX () { throw new RuntimeException(); }
  static public  void main (java.lang.String[] argStrings) { throw new RuntimeException(); }
  static public  void initSecurity () { throw new RuntimeException(); }
  public   HistoryServer (org.apache.spark.SparkConf conf, org.apache.spark.deploy.history.ApplicationHistoryProvider provider, org.apache.spark.SecurityManager securityManager, int port) { throw new RuntimeException(); }
  private  int retainedApplications () { throw new RuntimeException(); }
  private  com.google.common.cache.CacheLoader<java.lang.String, org.apache.spark.ui.SparkUI> appLoader () { throw new RuntimeException(); }
  private  com.google.common.cache.LoadingCache<java.lang.String, org.apache.spark.ui.SparkUI> appCache () { throw new RuntimeException(); }
  private  javax.servlet.http.HttpServlet loaderServlet () { throw new RuntimeException(); }
  /**
   * Initialize the history server.
   * <p>
   * This starts a background thread that periodically synchronizes information displayed on
   * this UI with the event logs in the provided base directory.
   */
  public  void initialize () { throw new RuntimeException(); }
  /** Bind to the HTTP server behind this web interface. */
  public  void bind () { throw new RuntimeException(); }
  /** Stop the server and close the file system. */
  public  void stop () { throw new RuntimeException(); }
  /** Attach a reconstructed UI to this server. Only valid after bind(). */
  private  void attachSparkUI (org.apache.spark.ui.SparkUI ui) { throw new RuntimeException(); }
  /** Detach a reconstructed UI from this server. Only valid after bind(). */
  private  void detachSparkUI (org.apache.spark.ui.SparkUI ui) { throw new RuntimeException(); }
  /**
   * Returns a list of available applications, in descending order according to their end time.
   * <p>
   * @return List of all known applications.
   */
  public  scala.collection.Seq<org.apache.spark.deploy.history.ApplicationHistoryInfo> getApplicationList () { throw new RuntimeException(); }
  /**
   * Returns the provider configuration to show in the listing page.
   * <p>
   * @return A map with the provider's configuration.
   */
  public  scala.collection.immutable.Map<java.lang.String, java.lang.String> getProviderConfig () { throw new RuntimeException(); }
}
