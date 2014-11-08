package org.apache.spark.network;
private  class ConnectionManagerId implements scala.Product, scala.Serializable {
  static public  org.apache.spark.network.ConnectionManagerId fromSocketAddress (java.net.InetSocketAddress socketAddress) { throw new RuntimeException(); }
  public  java.lang.String host () { throw new RuntimeException(); }
  public  int port () { throw new RuntimeException(); }
  // not preceding
  public   ConnectionManagerId (java.lang.String host, int port) { throw new RuntimeException(); }
  public  java.net.InetSocketAddress toSocketAddress () { throw new RuntimeException(); }
}
