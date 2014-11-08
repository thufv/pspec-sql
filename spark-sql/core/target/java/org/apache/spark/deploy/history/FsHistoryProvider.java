package org.apache.spark.deploy.history;
private  class FsHistoryProvider extends org.apache.spark.deploy.history.ApplicationHistoryProvider implements org.apache.spark.Logging {
  public   FsHistoryProvider (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  private  int UPDATE_INTERVAL_MS () { throw new RuntimeException(); }
  private  java.lang.String logDir () { throw new RuntimeException(); }
  private  java.net.URI resolvedLogDir () { throw new RuntimeException(); }
  private  org.apache.hadoop.fs.FileSystem fs () { throw new RuntimeException(); }
  private  long lastLogCheckTimeMs () { throw new RuntimeException(); }
  private  scala.collection.Seq<org.apache.spark.deploy.history.ApplicationHistoryInfo> appList () { throw new RuntimeException(); }
  /**
   * A background thread that periodically checks for event log updates on disk.
   * <p>
   * If a log check is invoked manually in the middle of a period, this thread re-adjusts the
   * time at which it performs the next log check to maintain the same period as before.
   * <p>
   * TODO: Add a mechanism to update manually.
   */
  private  java.lang.Thread logCheckingThread () { throw new RuntimeException(); }
  private  void initialize () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.deploy.history.ApplicationHistoryInfo> getListing () { throw new RuntimeException(); }
  public  org.apache.spark.ui.SparkUI getAppUI (java.lang.String appId) { throw new RuntimeException(); }
  public  scala.collection.immutable.Map<java.lang.String, java.lang.String> getConfig () { throw new RuntimeException(); }
  /**
   * Builds the application list based on the current contents of the log directory.
   * Tries to reuse as much of the data already in memory as possible, by not reading
   * applications that haven't been updated since last time the logs were checked.
   */
  private  void checkForLogs () { throw new RuntimeException(); }
  /**
   * Parse the application's logs to find out the information we need to build the
   * listing page.
   * <p>
   * When creating the listing of available apps, there is no need to load the whole UI for the
   * application. The UI is requested by the HistoryServer (by calling getAppInfo()) when the user
   * clicks on a specific application.
   * <p>
   * @param logDir Directory with application's log files.
   * @param renderUI Whether to create the SparkUI for the application.
   * @return A 2-tuple <code>(app info, ui)</code>. <code>ui</code> will be null if <code>renderUI</code> is false.
   */
  private  scala.Tuple2<org.apache.spark.deploy.history.ApplicationHistoryInfo, org.apache.spark.ui.SparkUI> loadAppInfo (org.apache.hadoop.fs.FileStatus logDir, boolean renderUI) { throw new RuntimeException(); }
  /** Return when this directory was last modified. */
  private  long getModificationTime (org.apache.hadoop.fs.FileStatus dir) { throw new RuntimeException(); }
  /** Returns the system's mononotically increasing time. */
  private  long getMonotonicTimeMs () { throw new RuntimeException(); }
}
