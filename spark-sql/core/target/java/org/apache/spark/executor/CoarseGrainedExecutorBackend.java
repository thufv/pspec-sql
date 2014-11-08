package org.apache.spark.executor;
private  class CoarseGrainedExecutorBackend implements akka.actor.Actor, org.apache.spark.util.ActorLogReceive, org.apache.spark.executor.ExecutorBackend, org.apache.spark.Logging {
  static private  void run (java.lang.String driverUrl, java.lang.String executorId, java.lang.String hostname, int cores, scala.Option<java.lang.String> workerUrl) { throw new RuntimeException(); }
  static public  void main (java.lang.String[] args) { throw new RuntimeException(); }
  public   CoarseGrainedExecutorBackend (java.lang.String driverUrl, java.lang.String executorId, java.lang.String hostPort, int cores, scala.collection.Seq<scala.Tuple2<java.lang.String, java.lang.String>> sparkProperties) { throw new RuntimeException(); }
  public  org.apache.spark.executor.Executor executor () { throw new RuntimeException(); }
  public  akka.actor.ActorSelection driver () { throw new RuntimeException(); }
  public  void preStart () { throw new RuntimeException(); }
  public  scala.PartialFunction<java.lang.Object, scala.runtime.BoxedUnit> receiveWithLogging () { throw new RuntimeException(); }
  public  void statusUpdate (long taskId, scala.Enumeration.Value state, java.nio.ByteBuffer data) { throw new RuntimeException(); }
}
