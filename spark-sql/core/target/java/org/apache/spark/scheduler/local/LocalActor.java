package org.apache.spark.scheduler.local;
/**
 * Calls to LocalBackend are all serialized through LocalActor. Using an actor makes the calls on
 * LocalBackend asynchronous, which is necessary to prevent deadlock between LocalBackend
 * and the TaskSchedulerImpl.
 */
private  class LocalActor implements akka.actor.Actor, org.apache.spark.util.ActorLogReceive, org.apache.spark.Logging {
  private  int totalCores () { throw new RuntimeException(); }
  // not preceding
  public   LocalActor (org.apache.spark.scheduler.TaskSchedulerImpl scheduler, org.apache.spark.scheduler.local.LocalBackend executorBackend, int totalCores) { throw new RuntimeException(); }
  private  int freeCores () { throw new RuntimeException(); }
  private  java.lang.String localExecutorId () { throw new RuntimeException(); }
  private  java.lang.String localExecutorHostname () { throw new RuntimeException(); }
  public  org.apache.spark.executor.Executor executor () { throw new RuntimeException(); }
  public  scala.PartialFunction<java.lang.Object, scala.runtime.BoxedUnit> receiveWithLogging () { throw new RuntimeException(); }
  public  void reviveOffers () { throw new RuntimeException(); }
}
