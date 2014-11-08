package org.apache.spark.deploy;
/**
 * Proxy that relays messages to the driver.
 */
private  class ClientActor implements akka.actor.Actor, org.apache.spark.util.ActorLogReceive, org.apache.spark.Logging {
  public   ClientActor (org.apache.spark.deploy.ClientArguments driverArgs, org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  public  akka.actor.ActorSelection masterActor () { throw new RuntimeException(); }
  public  scala.concurrent.duration.FiniteDuration timeout () { throw new RuntimeException(); }
  public  void preStart () { throw new RuntimeException(); }
  public  void pollAndReportStatus (java.lang.String driverId) { throw new RuntimeException(); }
  public  scala.PartialFunction<java.lang.Object, scala.runtime.BoxedUnit> receiveWithLogging () { throw new RuntimeException(); }
}
