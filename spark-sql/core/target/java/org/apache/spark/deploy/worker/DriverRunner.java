package org.apache.spark.deploy.worker;
/**
 * Manages the execution of one driver, including automatically restarting the driver on failure.
 * This is currently only used in standalone cluster deploy mode.
 */
private  class DriverRunner implements org.apache.spark.Logging {
  public  java.lang.String driverId () { throw new RuntimeException(); }
  public  java.io.File workDir () { throw new RuntimeException(); }
  public  java.io.File sparkHome () { throw new RuntimeException(); }
  public  org.apache.spark.deploy.DriverDescription driverDesc () { throw new RuntimeException(); }
  public  akka.actor.ActorRef worker () { throw new RuntimeException(); }
  public  java.lang.String workerUrl () { throw new RuntimeException(); }
  // not preceding
  public   DriverRunner (java.lang.String driverId, java.io.File workDir, java.io.File sparkHome, org.apache.spark.deploy.DriverDescription driverDesc, akka.actor.ActorRef worker, java.lang.String workerUrl) { throw new RuntimeException(); }
  public  scala.Option<java.lang.Process> process () { throw new RuntimeException(); }
  public  boolean killed () { throw new RuntimeException(); }
  public  scala.Option<scala.Enumeration.Value> finalState () { throw new RuntimeException(); }
  public  scala.Option<java.lang.Exception> finalException () { throw new RuntimeException(); }
  public  scala.Option<java.lang.Object> finalExitCode () { throw new RuntimeException(); }
  private  void setClock (org.apache.spark.deploy.worker.Clock _clock) { throw new RuntimeException(); }
  private  void setSleeper (org.apache.spark.deploy.worker.Sleeper _sleeper) { throw new RuntimeException(); }
  private  java.lang.Object clock () { throw new RuntimeException(); }
  private  java.lang.Object sleeper () { throw new RuntimeException(); }
  /** Starts a thread to run and manage the driver. */
  public  void start () { throw new RuntimeException(); }
  /** Terminate this driver (or prevent it from ever starting if not yet started) */
  public  void kill () { throw new RuntimeException(); }
  /** Replace variables in a command argument passed to us */
  private  java.lang.String substituteVariables (java.lang.String argument) { throw new RuntimeException(); }
  /**
   * Creates the working directory for this driver.
   * Will throw an exception if there are errors preparing the directory.
   */
  private  java.io.File createWorkingDirectory () { throw new RuntimeException(); }
  /**
   * Download the user jar into the supplied directory and return its local path.
   * Will throw an exception if there are errors downloading the jar.
   */
  private  java.lang.String downloadUserJar (java.io.File driverDir) { throw new RuntimeException(); }
  private  void launchDriver (scala.collection.Seq<java.lang.String> command, scala.collection.Map<java.lang.String, java.lang.String> envVars, java.io.File baseDir, boolean supervise) { throw new RuntimeException(); }
  private  void runCommandWithRetry (org.apache.spark.deploy.worker.ProcessBuilderLike command, scala.Function1<java.lang.Process, scala.runtime.BoxedUnit> initialize, boolean supervise) { throw new RuntimeException(); }
}
