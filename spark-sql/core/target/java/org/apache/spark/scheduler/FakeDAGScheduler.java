package org.apache.spark.scheduler;
public  class FakeDAGScheduler extends org.apache.spark.scheduler.DAGScheduler {
  public   FakeDAGScheduler (org.apache.spark.SparkContext sc, org.apache.spark.scheduler.FakeTaskScheduler taskScheduler) { throw new RuntimeException(); }
  public  void taskStarted (org.apache.spark.scheduler.Task<?> task, org.apache.spark.scheduler.TaskInfo taskInfo) { throw new RuntimeException(); }
  public  void taskEnded (org.apache.spark.scheduler.Task<?> task, org.apache.spark.TaskEndReason reason, Object result, scala.collection.mutable.Map<java.lang.Object, java.lang.Object> accumUpdates, org.apache.spark.scheduler.TaskInfo taskInfo, org.apache.spark.executor.TaskMetrics taskMetrics) { throw new RuntimeException(); }
  public  void executorAdded (java.lang.String execId, java.lang.String host) { throw new RuntimeException(); }
  public  void executorLost (java.lang.String execId) { throw new RuntimeException(); }
  public  void taskSetFailed (org.apache.spark.scheduler.TaskSet taskSet, java.lang.String reason) { throw new RuntimeException(); }
}
