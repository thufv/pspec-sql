package org.apache.spark.ui;
// no position
/**
 * Utilities for launching a web server using Jetty's HTTP Server class
 */
private  class JettyUtils implements org.apache.spark.Logging {
  static public  class ServletParams<T extends java.lang.Object> {
    public  scala.Function1<javax.servlet.http.HttpServletRequest, T> responder () { throw new RuntimeException(); }
    public  java.lang.String contentType () { throw new RuntimeException(); }
    public  scala.Function1<T, java.lang.String> extractFn () { throw new RuntimeException(); }
    // not preceding
    public   ServletParams (scala.Function1<javax.servlet.http.HttpServletRequest, T> responder, java.lang.String contentType, scala.Function1<T, java.lang.String> extractFn, scala.Function1<T, java.lang.Object> evidence$1) { throw new RuntimeException(); }
  }
  // no position
  static public  class ServletParams$ {
    public   ServletParams$ () { throw new RuntimeException(); }
  }
  static public  org.apache.spark.ui.JettyUtils.ServletParams<org.json4s.JsonAST.JValue> jsonResponderToServlet (scala.Function1<javax.servlet.http.HttpServletRequest, org.json4s.JsonAST.JValue> responder) { throw new RuntimeException(); }
  static public  org.apache.spark.ui.JettyUtils.ServletParams<scala.collection.Seq<scala.xml.Node>> htmlResponderToServlet (scala.Function1<javax.servlet.http.HttpServletRequest, scala.collection.Seq<scala.xml.Node>> responder) { throw new RuntimeException(); }
  static public  org.apache.spark.ui.JettyUtils.ServletParams<java.lang.String> textResponderToServlet (scala.Function1<javax.servlet.http.HttpServletRequest, java.lang.String> responder) { throw new RuntimeException(); }
  static public <T extends java.lang.Object> javax.servlet.http.HttpServlet createServlet (org.apache.spark.ui.JettyUtils.ServletParams<T> servletParams, org.apache.spark.SecurityManager securityMgr, scala.Function1<T, java.lang.Object> evidence$2) { throw new RuntimeException(); }
  /** Create a context handler that responds to a request with the given path prefix */
  static public <T extends java.lang.Object> org.eclipse.jetty.servlet.ServletContextHandler createServletHandler (java.lang.String path, org.apache.spark.ui.JettyUtils.ServletParams<T> servletParams, org.apache.spark.SecurityManager securityMgr, java.lang.String basePath, scala.Function1<T, java.lang.Object> evidence$3) { throw new RuntimeException(); }
  /** Create a context handler that responds to a request with the given path prefix */
  static public  org.eclipse.jetty.servlet.ServletContextHandler createServletHandler (java.lang.String path, javax.servlet.http.HttpServlet servlet, java.lang.String basePath) { throw new RuntimeException(); }
  /** Create a handler that always redirects the user to the given path */
  static public  org.eclipse.jetty.servlet.ServletContextHandler createRedirectHandler (java.lang.String srcPath, java.lang.String destPath, scala.Function1<javax.servlet.http.HttpServletRequest, scala.runtime.BoxedUnit> beforeRedirect, java.lang.String basePath) { throw new RuntimeException(); }
  /** Create a handler for serving files from a static directory */
  static public  org.eclipse.jetty.servlet.ServletContextHandler createStaticHandler (java.lang.String resourceBase, java.lang.String path) { throw new RuntimeException(); }
  /** Add filters, if any, to the given list of ServletContextHandlers */
  static public  void addFilters (scala.collection.Seq<org.eclipse.jetty.servlet.ServletContextHandler> handlers, org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  /**
   * Attempt to start a Jetty server bound to the supplied hostName:port using the given
   * context handlers.
   * <p>
   * If the desired port number is contended, continues incrementing ports until a free port is
   * found. Return the jetty Server object, the chosen port, and a mutable collection of handlers.
   */
  static public  org.apache.spark.ui.ServerInfo startJettyServer (java.lang.String hostName, int port, scala.collection.Seq<org.eclipse.jetty.servlet.ServletContextHandler> handlers, org.apache.spark.SparkConf conf, java.lang.String serverName) { throw new RuntimeException(); }
  /** Attach a prefix to the given path, but avoid returning an empty path */
  static private  java.lang.String attachPrefix (java.lang.String basePath, java.lang.String relativePath) { throw new RuntimeException(); }
}
