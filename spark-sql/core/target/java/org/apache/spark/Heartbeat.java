package org.apache.spark;
/**
 * A heartbeat from executors to the driver. This is a shared message used by several internal
 * components to convey liveness or execution information for in-progress tasks.
 */
private  class Heartbeat implements scala.Product, scala.Serializable {
  public  java.lang.String executorId () { throw new RuntimeException(); }
  public  scala.Tuple2<java.lang.Object, org.apache.spark.executor.TaskMetrics>[] taskMetrics () { throw new RuntimeException(); }
  public  org.apache.spark.storage.BlockManagerId blockManagerId () { throw new RuntimeException(); }
  // not preceding
  public   Heartbeat (java.lang.String executorId, scala.Tuple2<java.lang.Object, org.apache.spark.executor.TaskMetrics>[] taskMetrics, org.apache.spark.storage.BlockManagerId blockManagerId) { throw new RuntimeException(); }
}
