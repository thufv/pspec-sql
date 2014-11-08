package org.apache.spark.scheduler;
/**
 * Represents free resources available on an executor.
 */
private  class WorkerOffer implements scala.Product, scala.Serializable {
  public  java.lang.String executorId () { throw new RuntimeException(); }
  public  java.lang.String host () { throw new RuntimeException(); }
  public  int cores () { throw new RuntimeException(); }
  // not preceding
  public   WorkerOffer (java.lang.String executorId, java.lang.String host, int cores) { throw new RuntimeException(); }
}
