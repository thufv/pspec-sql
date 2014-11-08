package org.apache.spark;
/**
 * An HTTP server for static content used to allow worker nodes to access JARs added to SparkContext
 * as well as classes created by the interpreter when the user types in code. This is just a wrapper
 * around a Jetty server.
 */
private  class HttpServer implements org.apache.spark.Logging {
  public   HttpServer (java.io.File resourceBase, org.apache.spark.SecurityManager securityManager, int requestedPort, java.lang.String serverName) { throw new RuntimeException(); }
  private  org.eclipse.jetty.server.Server server () { throw new RuntimeException(); }
  private  int port () { throw new RuntimeException(); }
  public  void start () { throw new RuntimeException(); }
  /**
   * Actually start the HTTP server on the given port.
   * <p>
   * Note that this is only best effort in the sense that we may end up binding to a nearby port
   * in the event of port collision. Return the bound server and the actual port used.
   */
  private  scala.Tuple2<org.eclipse.jetty.server.Server, java.lang.Object> doStart (int startPort) { throw new RuntimeException(); }
  /**
   * Setup Jetty to the HashLoginService using a single user with our
   * shared secret. Configure it to use DIGEST-MD5 authentication so that the password
   * isn't passed in plaintext.
   */
  private  org.eclipse.jetty.security.ConstraintSecurityHandler setupSecurityHandler (org.apache.spark.SecurityManager securityMgr) { throw new RuntimeException(); }
  public  void stop () { throw new RuntimeException(); }
  /**
   * Get the URI of this HTTP server (http://host:port)
   */
  public  java.lang.String uri () { throw new RuntimeException(); }
}
