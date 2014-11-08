package org.apache.spark.scheduler;
/**
 * Tracks information about an active job in the DAGScheduler.
 */
private  class ActiveJob {
  public  int jobId () { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.Stage finalStage () { throw new RuntimeException(); }
  public  Object func () { throw new RuntimeException(); }
  public  int[] partitions () { throw new RuntimeException(); }
  public  org.apache.spark.util.CallSite callSite () { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.JobListener listener () { throw new RuntimeException(); }
  public  java.util.Properties properties () { throw new RuntimeException(); }
  // not preceding
  public   ActiveJob (int jobId, org.apache.spark.scheduler.Stage finalStage, scala.Function2<org.apache.spark.TaskContext, scala.collection.Iterator<java.lang.Object>, ?> func, int[] partitions, org.apache.spark.util.CallSite callSite, org.apache.spark.scheduler.JobListener listener, java.util.Properties properties) { throw new RuntimeException(); }
  public  int numPartitions () { throw new RuntimeException(); }
  public  boolean[] finished () { throw new RuntimeException(); }
  public  int numFinished () { throw new RuntimeException(); }
}
