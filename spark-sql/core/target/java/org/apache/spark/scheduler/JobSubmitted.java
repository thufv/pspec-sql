package org.apache.spark.scheduler;
private  class JobSubmitted implements org.apache.spark.scheduler.DAGSchedulerEvent, scala.Product, scala.Serializable {
  public  int jobId () { throw new RuntimeException(); }
  public  Object finalRDD () { throw new RuntimeException(); }
  public  Object func () { throw new RuntimeException(); }
  public  int[] partitions () { throw new RuntimeException(); }
  public  boolean allowLocal () { throw new RuntimeException(); }
  public  org.apache.spark.util.CallSite callSite () { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.JobListener listener () { throw new RuntimeException(); }
  public  java.util.Properties properties () { throw new RuntimeException(); }
  // not preceding
  public   JobSubmitted (int jobId, org.apache.spark.rdd.RDD<?> finalRDD, scala.Function2<org.apache.spark.TaskContext, scala.collection.Iterator<java.lang.Object>, ?> func, int[] partitions, boolean allowLocal, org.apache.spark.util.CallSite callSite, org.apache.spark.scheduler.JobListener listener, java.util.Properties properties) { throw new RuntimeException(); }
}
