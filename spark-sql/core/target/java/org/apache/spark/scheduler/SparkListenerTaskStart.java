package org.apache.spark.scheduler;
public  class SparkListenerTaskStart implements org.apache.spark.scheduler.SparkListenerEvent, scala.Product, scala.Serializable {
  public  int stageId () { throw new RuntimeException(); }
  public  int stageAttemptId () { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.TaskInfo taskInfo () { throw new RuntimeException(); }
  // not preceding
  public   SparkListenerTaskStart (int stageId, int stageAttemptId, org.apache.spark.scheduler.TaskInfo taskInfo) { throw new RuntimeException(); }
}
