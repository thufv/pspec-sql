package org.apache.spark.scheduler;
private  class CompletionEvent implements org.apache.spark.scheduler.DAGSchedulerEvent, scala.Product, scala.Serializable {
  public  Object task () { throw new RuntimeException(); }
  public  org.apache.spark.TaskEndReason reason () { throw new RuntimeException(); }
  public  Object result () { throw new RuntimeException(); }
  public  scala.collection.mutable.Map<java.lang.Object, java.lang.Object> accumUpdates () { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.TaskInfo taskInfo () { throw new RuntimeException(); }
  public  org.apache.spark.executor.TaskMetrics taskMetrics () { throw new RuntimeException(); }
  // not preceding
  public   CompletionEvent (org.apache.spark.scheduler.Task<?> task, org.apache.spark.TaskEndReason reason, Object result, scala.collection.mutable.Map<java.lang.Object, java.lang.Object> accumUpdates, org.apache.spark.scheduler.TaskInfo taskInfo, org.apache.spark.executor.TaskMetrics taskMetrics) { throw new RuntimeException(); }
}
