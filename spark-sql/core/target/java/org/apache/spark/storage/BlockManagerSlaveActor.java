package org.apache.spark.storage;
/**
 * An actor to take commands from the master to execute options. For example,
 * this is used to remove blocks from the slave's BlockManager.
 */
private  class BlockManagerSlaveActor implements akka.actor.Actor, org.apache.spark.util.ActorLogReceive, org.apache.spark.Logging {
  public   BlockManagerSlaveActor (org.apache.spark.storage.BlockManager blockManager, org.apache.spark.MapOutputTracker mapOutputTracker) { throw new RuntimeException(); }
  public  scala.PartialFunction<java.lang.Object, scala.runtime.BoxedUnit> receiveWithLogging () { throw new RuntimeException(); }
  private <T extends java.lang.Object> void doAsync (java.lang.String actionMessage, akka.actor.ActorRef responseActor, scala.Function0<T> body) { throw new RuntimeException(); }
}
