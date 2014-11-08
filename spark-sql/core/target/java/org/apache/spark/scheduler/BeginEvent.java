package org.apache.spark.scheduler;
private  class BeginEvent implements org.apache.spark.scheduler.DAGSchedulerEvent, scala.Product, scala.Serializable {
  public  Object task () { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.TaskInfo taskInfo () { throw new RuntimeException(); }
  // not preceding
  public   BeginEvent (org.apache.spark.scheduler.Task<?> task, org.apache.spark.scheduler.TaskInfo taskInfo) { throw new RuntimeException(); }
}
