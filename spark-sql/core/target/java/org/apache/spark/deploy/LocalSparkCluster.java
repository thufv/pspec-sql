package org.apache.spark.deploy;
/**
 * Testing class that creates a Spark standalone process in-cluster (that is, running the
 * spark.deploy.master.Master and spark.deploy.worker.Workers in the same JVMs). Executors launched
 * by the Workers still run in separate JVMs. This can be used to test distributed operation and
 * fault recovery without spinning up a lot of processes.
 */
private  class LocalSparkCluster implements org.apache.spark.Logging {
  public   LocalSparkCluster (int numWorkers, int coresPerWorker, int memoryPerWorker) { throw new RuntimeException(); }
  private  java.lang.String localHostname () { throw new RuntimeException(); }
  private  scala.collection.mutable.ArrayBuffer<akka.actor.ActorSystem> masterActorSystems () { throw new RuntimeException(); }
  private  scala.collection.mutable.ArrayBuffer<akka.actor.ActorSystem> workerActorSystems () { throw new RuntimeException(); }
  public  java.lang.String[] start () { throw new RuntimeException(); }
  public  void stop () { throw new RuntimeException(); }
}
