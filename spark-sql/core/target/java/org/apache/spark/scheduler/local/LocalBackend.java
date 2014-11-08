package org.apache.spark.scheduler.local;
/**
 * LocalBackend is used when running a local version of Spark where the executor, backend, and
 * master all run in the same JVM. It sits behind a TaskSchedulerImpl and handles launching tasks
 * on a single Executor (created by the LocalBackend) running locally.
 */
private  class LocalBackend implements org.apache.spark.scheduler.SchedulerBackend, org.apache.spark.executor.ExecutorBackend {
  public  int totalCores () { throw new RuntimeException(); }
  // not preceding
  public   LocalBackend (org.apache.spark.scheduler.TaskSchedulerImpl scheduler, int totalCores) { throw new RuntimeException(); }
  public  akka.actor.ActorRef localActor () { throw new RuntimeException(); }
  public  void start () { throw new RuntimeException(); }
  public  void stop () { throw new RuntimeException(); }
  public  void reviveOffers () { throw new RuntimeException(); }
  public  int defaultParallelism () { throw new RuntimeException(); }
  public  void killTask (long taskId, java.lang.String executorId, boolean interruptThread) { throw new RuntimeException(); }
  public  void statusUpdate (long taskId, scala.Enumeration.Value state, java.nio.ByteBuffer serializedData) { throw new RuntimeException(); }
}
