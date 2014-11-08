package org.apache.spark.ui;
/**
 * The top level component of the UI hierarchy that contains the server.
 * <p>
 * Each WebUI represents a collection of tabs, each of which in turn represents a collection of
 * pages. The use of tabs is optional, however; a WebUI may choose to include pages directly.
 */
private abstract class WebUI implements org.apache.spark.Logging {
  public   WebUI (org.apache.spark.SecurityManager securityManager, int port, org.apache.spark.SparkConf conf, java.lang.String basePath, java.lang.String name) { throw new RuntimeException(); }
  protected  scala.collection.mutable.ArrayBuffer<org.apache.spark.ui.WebUITab> tabs () { throw new RuntimeException(); }
  protected  scala.collection.mutable.ArrayBuffer<org.eclipse.jetty.servlet.ServletContextHandler> handlers () { throw new RuntimeException(); }
  protected  scala.Option<org.apache.spark.ui.ServerInfo> serverInfo () { throw new RuntimeException(); }
  protected  java.lang.String localHostName () { throw new RuntimeException(); }
  protected  java.lang.String publicHostName () { throw new RuntimeException(); }
  private  java.lang.String className () { throw new RuntimeException(); }
  public  java.lang.String getBasePath () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.ui.WebUITab> getTabs () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.eclipse.jetty.servlet.ServletContextHandler> getHandlers () { throw new RuntimeException(); }
  public  org.apache.spark.SecurityManager getSecurityManager () { throw new RuntimeException(); }
  /** Attach a tab to this UI, along with all of its attached pages. */
  public  void attachTab (org.apache.spark.ui.WebUITab tab) { throw new RuntimeException(); }
  /** Attach a page to this UI. */
  public  void attachPage (org.apache.spark.ui.WebUIPage page) { throw new RuntimeException(); }
  /** Attach a handler to this UI. */
  public  void attachHandler (org.eclipse.jetty.servlet.ServletContextHandler handler) { throw new RuntimeException(); }
  /** Detach a handler from this UI. */
  public  void detachHandler (org.eclipse.jetty.servlet.ServletContextHandler handler) { throw new RuntimeException(); }
  /** Initialize all components of the server. */
  public abstract  void initialize () ;
  /** Bind to the HTTP server behind this web interface. */
  public  void bind () { throw new RuntimeException(); }
  /** Return the actual port to which this server is bound. Only valid after bind(). */
  public  int boundPort () { throw new RuntimeException(); }
  /** Stop the server behind this web interface. Only valid after bind(). */
  public  void stop () { throw new RuntimeException(); }
}
