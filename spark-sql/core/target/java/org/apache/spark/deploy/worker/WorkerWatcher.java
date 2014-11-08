package org.apache.spark.deploy.worker;
/**
 * Actor which connects to a worker process and terminates the JVM if the connection is severed.
 * Provides fate sharing between a worker and its associated child processes.
 */
private  class WorkerWatcher implements akka.actor.Actor, org.apache.spark.util.ActorLogReceive, org.apache.spark.Logging {
  public   WorkerWatcher (java.lang.String workerUrl) { throw new RuntimeException(); }
  public  void preStart () { throw new RuntimeException(); }
  public  boolean isShutDown () { throw new RuntimeException(); }
  private  void setTesting (boolean testing) { throw new RuntimeException(); }
  private  boolean isTesting () { throw new RuntimeException(); }
  private  java.lang.String expectedHostPort () { throw new RuntimeException(); }
  private  boolean isWorker (akka.actor.Address address) { throw new RuntimeException(); }
  public  void exitNonZero () { throw new RuntimeException(); }
  public  scala.PartialFunction<java.lang.Object, scala.runtime.BoxedUnit> receiveWithLogging () { throw new RuntimeException(); }
}
