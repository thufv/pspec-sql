package org.apache.spark.scheduler;
public  class SparkListenerTaskEnd implements org.apache.spark.scheduler.SparkListenerEvent, scala.Product, scala.Serializable {
  public  int stageId () { throw new RuntimeException(); }
  public  int stageAttemptId () { throw new RuntimeException(); }
  public  java.lang.String taskType () { throw new RuntimeException(); }
  public  org.apache.spark.TaskEndReason reason () { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.TaskInfo taskInfo () { throw new RuntimeException(); }
  public  org.apache.spark.executor.TaskMetrics taskMetrics () { throw new RuntimeException(); }
  // not preceding
  public   SparkListenerTaskEnd (int stageId, int stageAttemptId, java.lang.String taskType, org.apache.spark.TaskEndReason reason, org.apache.spark.scheduler.TaskInfo taskInfo, org.apache.spark.executor.TaskMetrics taskMetrics) { throw new RuntimeException(); }
}
