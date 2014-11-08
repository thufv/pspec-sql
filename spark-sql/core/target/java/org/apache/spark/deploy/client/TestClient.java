package org.apache.spark.deploy.client;
// no position
private  class TestClient {
  static public  class TestListener implements org.apache.spark.deploy.client.AppClientListener, org.apache.spark.Logging {
    public   TestListener () { throw new RuntimeException(); }
    public  void connected (java.lang.String id) { throw new RuntimeException(); }
    public  void disconnected () { throw new RuntimeException(); }
    public  void dead (java.lang.String reason) { throw new RuntimeException(); }
    public  void executorAdded (java.lang.String id, java.lang.String workerId, java.lang.String hostPort, int cores, int memory) { throw new RuntimeException(); }
    public  void executorRemoved (java.lang.String id, java.lang.String message, scala.Option<java.lang.Object> exitStatus) { throw new RuntimeException(); }
  }
  static public  void main (java.lang.String[] args) { throw new RuntimeException(); }
}
