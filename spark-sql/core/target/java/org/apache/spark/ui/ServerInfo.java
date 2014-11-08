package org.apache.spark.ui;
private  class ServerInfo implements scala.Product, scala.Serializable {
  public  org.eclipse.jetty.server.Server server () { throw new RuntimeException(); }
  public  int boundPort () { throw new RuntimeException(); }
  public  org.eclipse.jetty.server.handler.ContextHandlerCollection rootHandler () { throw new RuntimeException(); }
  // not preceding
  public   ServerInfo (org.eclipse.jetty.server.Server server, int boundPort, org.eclipse.jetty.server.handler.ContextHandlerCollection rootHandler) { throw new RuntimeException(); }
}
