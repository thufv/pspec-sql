package org.apache.spark.deploy.history;
private abstract class ApplicationHistoryProvider {
  public   ApplicationHistoryProvider () { throw new RuntimeException(); }
  /**
   * Returns a list of applications available for the history server to show.
   * <p>
   * @return List of all know applications.
   */
  public abstract  scala.collection.Seq<org.apache.spark.deploy.history.ApplicationHistoryInfo> getListing () ;
  /**
   * Returns the Spark UI for a specific application.
   * <p>
   * @param appId The application ID.
   * @return The application's UI, or null if application is not found.
   */
  public abstract  org.apache.spark.ui.SparkUI getAppUI (java.lang.String appId) ;
  /**
   * Called when the server is shutting down.
   */
  public  void stop () { throw new RuntimeException(); }
  /**
   * Returns configuration data to be shown in the History Server home page.
   * <p>
   * @return A map with the configuration data. Data is show in the order returned by the map.
   */
  public  scala.collection.immutable.Map<java.lang.String, java.lang.String> getConfig () { throw new RuntimeException(); }
}
