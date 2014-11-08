package org.apache.spark.scheduler;
/**
 * Periodic updates from executors.
 * @param execId executor id
 * @param taskMetrics sequence of (task id, stage id, stage attempt, metrics)
 */
public  class SparkListenerExecutorMetricsUpdate implements org.apache.spark.scheduler.SparkListenerEvent, scala.Product, scala.Serializable {
  public  java.lang.String execId () { throw new RuntimeException(); }
  public  scala.collection.Seq<scala.Tuple4<java.lang.Object, java.lang.Object, java.lang.Object, org.apache.spark.executor.TaskMetrics>> taskMetrics () { throw new RuntimeException(); }
  // not preceding
  public   SparkListenerExecutorMetricsUpdate (java.lang.String execId, scala.collection.Seq<scala.Tuple4<java.lang.Object, java.lang.Object, java.lang.Object, org.apache.spark.executor.TaskMetrics>> taskMetrics) { throw new RuntimeException(); }
}
