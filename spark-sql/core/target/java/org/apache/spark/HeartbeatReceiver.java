package org.apache.spark;
/**
 * Lives in the driver to receive heartbeats from executors..
 */
private  class HeartbeatReceiver implements akka.actor.Actor, org.apache.spark.util.ActorLogReceive, org.apache.spark.Logging {
  public   HeartbeatReceiver (org.apache.spark.scheduler.TaskScheduler scheduler) { throw new RuntimeException(); }
  public  scala.PartialFunction<java.lang.Object, scala.runtime.BoxedUnit> receiveWithLogging () { throw new RuntimeException(); }
}
