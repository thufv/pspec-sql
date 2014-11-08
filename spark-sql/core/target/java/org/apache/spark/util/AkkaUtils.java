package org.apache.spark.util;
// no position
/**
 * Various utility classes for working with Akka.
 */
private  class AkkaUtils implements org.apache.spark.Logging {
  /**
   * Creates an ActorSystem ready for remoting, with various Spark features. Returns both the
   * ActorSystem itself and its port (which is hard to get from Akka).
   * <p>
   * Note: the <code>name</code> parameter is important, as even if a client sends a message to right
   * host + port, if the system name is incorrect, Akka will drop the message.
   * <p>
   * If indestructible is set to true, the Actor System will continue running in the event
   * of a fatal exception. This is used by {@link org.apache.spark.executor.Executor}.
   */
  static public  scala.Tuple2<akka.actor.ActorSystem, java.lang.Object> createActorSystem (java.lang.String name, java.lang.String host, int port, org.apache.spark.SparkConf conf, org.apache.spark.SecurityManager securityManager) { throw new RuntimeException(); }
  static private  scala.Tuple2<akka.actor.ActorSystem, java.lang.Object> doCreateActorSystem (java.lang.String name, java.lang.String host, int port, org.apache.spark.SparkConf conf, org.apache.spark.SecurityManager securityManager) { throw new RuntimeException(); }
  /** Returns the default Spark timeout to use for Akka ask operations. */
  static public  scala.concurrent.duration.FiniteDuration askTimeout (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  /** Returns the default Spark timeout to use for Akka remote actor lookup. */
  static public  scala.concurrent.duration.FiniteDuration lookupTimeout (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  /** Returns the configured max frame size for Akka messages in bytes. */
  static public  int maxFrameSizeBytes (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  /** Space reserved for extra data in an Akka message besides serialized task or task result. */
  static public  int reservedSizeBytes () { throw new RuntimeException(); }
  /** Returns the configured number of times to retry connecting */
  static public  int numRetries (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  /** Returns the configured number of milliseconds to wait on each retry */
  static public  int retryWaitMs (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  /**
   * Send a message to the given actor and get its result within a default timeout, or
   * throw a SparkException if this fails.
   */
  static public <T extends java.lang.Object> T askWithReply (Object message, akka.actor.ActorRef actor, int retryAttempts, int retryInterval, scala.concurrent.duration.FiniteDuration timeout) { throw new RuntimeException(); }
  static public  akka.actor.ActorRef makeDriverRef (java.lang.String name, org.apache.spark.SparkConf conf, akka.actor.ActorSystem actorSystem) { throw new RuntimeException(); }
}
