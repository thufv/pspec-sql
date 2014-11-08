package org.apache.spark.scheduler;
private  class GettingResultEvent implements org.apache.spark.scheduler.DAGSchedulerEvent, scala.Product, scala.Serializable {
  public  org.apache.spark.scheduler.TaskInfo taskInfo () { throw new RuntimeException(); }
  // not preceding
  public   GettingResultEvent (org.apache.spark.scheduler.TaskInfo taskInfo) { throw new RuntimeException(); }
}
