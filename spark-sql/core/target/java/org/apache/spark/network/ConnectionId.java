package org.apache.spark.network;
private  class ConnectionId implements scala.Product, scala.Serializable {
  static public  org.apache.spark.network.ConnectionId createConnectionIdFromString (java.lang.String connectionIdString) { throw new RuntimeException(); }
  public  org.apache.spark.network.ConnectionManagerId connectionManagerId () { throw new RuntimeException(); }
  public  int uniqId () { throw new RuntimeException(); }
  // not preceding
  public   ConnectionId (org.apache.spark.network.ConnectionManagerId connectionManagerId, int uniqId) { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
