package org.apache.spark.deploy.master;
// no position
public  class SparkCuratorUtil implements org.apache.spark.Logging {
  static public  int ZK_CONNECTION_TIMEOUT_MILLIS () { throw new RuntimeException(); }
  static public  int ZK_SESSION_TIMEOUT_MILLIS () { throw new RuntimeException(); }
  static public  int RETRY_WAIT_MILLIS () { throw new RuntimeException(); }
  static public  int MAX_RECONNECT_ATTEMPTS () { throw new RuntimeException(); }
  static public  org.apache.curator.framework.CuratorFramework newClient (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  static public  void mkdir (org.apache.curator.framework.CuratorFramework zk, java.lang.String path) { throw new RuntimeException(); }
  static public  void deleteRecursive (org.apache.curator.framework.CuratorFramework zk, java.lang.String path) { throw new RuntimeException(); }
}
