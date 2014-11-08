package org.apache.spark.deploy.master.ui;
/**
 * Web UI server for the standalone master.
 */
private  class MasterWebUI extends org.apache.spark.ui.WebUI implements org.apache.spark.Logging {
  static public  java.lang.String STATIC_RESOURCE_DIR () { throw new RuntimeException(); }
  public  org.apache.spark.deploy.master.Master master () { throw new RuntimeException(); }
  // not preceding
  public   MasterWebUI (org.apache.spark.deploy.master.Master master, int requestedPort) { throw new RuntimeException(); }
  public  akka.actor.ActorRef masterActorRef () { throw new RuntimeException(); }
  public  scala.concurrent.duration.FiniteDuration timeout () { throw new RuntimeException(); }
  /** Initialize all components of the server. */
  public  void initialize () { throw new RuntimeException(); }
  /** Attach a reconstructed UI to this Master UI. Only valid after bind(). */
  public  void attachSparkUI (org.apache.spark.ui.SparkUI ui) { throw new RuntimeException(); }
  /** Detach a reconstructed UI from this Master UI. Only valid after bind(). */
  public  void detachSparkUI (org.apache.spark.ui.SparkUI ui) { throw new RuntimeException(); }
}
