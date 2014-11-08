package org.apache.spark.deploy.master;
// no position
public  class SparkCuratorUtil$ implements org.apache.spark.Logging {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final SparkCuratorUtil$ MODULE$ = null;
  public   SparkCuratorUtil$ () { throw new RuntimeException(); }
  public  int ZK_CONNECTION_TIMEOUT_MILLIS () { throw new RuntimeException(); }
  public  int ZK_SESSION_TIMEOUT_MILLIS () { throw new RuntimeException(); }
  public  int RETRY_WAIT_MILLIS () { throw new RuntimeException(); }
  public  int MAX_RECONNECT_ATTEMPTS () { throw new RuntimeException(); }
  public  org.apache.curator.framework.CuratorFramework newClient (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  public  void mkdir (org.apache.curator.framework.CuratorFramework zk, java.lang.String path) { throw new RuntimeException(); }
  public  void deleteRecursive (org.apache.curator.framework.CuratorFramework zk, java.lang.String path) { throw new RuntimeException(); }
}
