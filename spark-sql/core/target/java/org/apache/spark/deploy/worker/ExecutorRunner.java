package org.apache.spark.deploy.worker;
/**
 * Manages the execution of one executor process.
 * This is currently only used in standalone mode.
 */
private  class ExecutorRunner implements org.apache.spark.Logging {
  public  java.lang.String appId () { throw new RuntimeException(); }
  public  int execId () { throw new RuntimeException(); }
  public  org.apache.spark.deploy.ApplicationDescription appDesc () { throw new RuntimeException(); }
  public  int cores () { throw new RuntimeException(); }
  public  int memory () { throw new RuntimeException(); }
  public  akka.actor.ActorRef worker () { throw new RuntimeException(); }
  public  java.lang.String workerId () { throw new RuntimeException(); }
  public  java.lang.String host () { throw new RuntimeException(); }
  public  java.io.File sparkHome () { throw new RuntimeException(); }
  public  java.io.File workDir () { throw new RuntimeException(); }
  public  java.lang.String workerUrl () { throw new RuntimeException(); }
  public  org.apache.spark.SparkConf conf () { throw new RuntimeException(); }
  public  scala.Enumeration.Value state () { throw new RuntimeException(); }
  // not preceding
  public   ExecutorRunner (java.lang.String appId, int execId, org.apache.spark.deploy.ApplicationDescription appDesc, int cores, int memory, akka.actor.ActorRef worker, java.lang.String workerId, java.lang.String host, java.io.File sparkHome, java.io.File workDir, java.lang.String workerUrl, org.apache.spark.SparkConf conf, scala.Enumeration.Value state) { throw new RuntimeException(); }
  public  java.lang.String fullId () { throw new RuntimeException(); }
  public  java.lang.Thread workerThread () { throw new RuntimeException(); }
  public  java.lang.Process process () { throw new RuntimeException(); }
  public  org.apache.spark.util.logging.FileAppender stdoutAppender () { throw new RuntimeException(); }
  public  org.apache.spark.util.logging.FileAppender stderrAppender () { throw new RuntimeException(); }
  public  java.lang.Thread shutdownHook () { throw new RuntimeException(); }
  public  void start () { throw new RuntimeException(); }
  /**
   * Kill executor process, wait for exit and notify worker to update resource status.
   * <p>
   * @param message the exception message which caused the executor's death 
   */
  private  void killProcess (scala.Option<java.lang.String> message) { throw new RuntimeException(); }
  /** Stop this executor runner, including killing the process it launched */
  public  void kill () { throw new RuntimeException(); }
  /** Replace variables such as {{EXECUTOR_ID}} and {{CORES}} in a command argument passed to us */
  public  java.lang.String substituteVariables (java.lang.String argument) { throw new RuntimeException(); }
  public  scala.collection.Seq<java.lang.String> getCommandSeq () { throw new RuntimeException(); }
  /**
   * Download and run the executor described in our ApplicationDescription
   */
  public  void fetchAndRunExecutor () { throw new RuntimeException(); }
}
