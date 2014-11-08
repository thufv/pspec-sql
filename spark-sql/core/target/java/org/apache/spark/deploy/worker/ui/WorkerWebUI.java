package org.apache.spark.deploy.worker.ui;
/**
 * Web UI server for the standalone worker.
 */
private  class WorkerWebUI extends org.apache.spark.ui.WebUI implements org.apache.spark.Logging {
  static public  int DEFAULT_PORT () { throw new RuntimeException(); }
  static public  java.lang.String STATIC_RESOURCE_BASE () { throw new RuntimeException(); }
  static public  int getUIPort (scala.Option<java.lang.Object> requestedPort, org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  public  org.apache.spark.deploy.worker.Worker worker () { throw new RuntimeException(); }
  public  java.io.File workDir () { throw new RuntimeException(); }
  // not preceding
  public   WorkerWebUI (org.apache.spark.deploy.worker.Worker worker, java.io.File workDir, scala.Option<java.lang.Object> port) { throw new RuntimeException(); }
  public  scala.concurrent.duration.FiniteDuration timeout () { throw new RuntimeException(); }
  /** Initialize all components of the server. */
  public  void initialize () { throw new RuntimeException(); }
}
