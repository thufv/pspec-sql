package org.apache.spark.scheduler;
/**
 * An object that waits for a DAGScheduler job to complete. As tasks finish, it passes their
 * results to the given handler function.
 */
private  class JobWaiter<T extends java.lang.Object> implements org.apache.spark.scheduler.JobListener {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   JobWaiter (org.apache.spark.scheduler.DAGScheduler dagScheduler, int jobId, int totalTasks, scala.Function2<java.lang.Object, T, scala.runtime.BoxedUnit> resultHandler) { throw new RuntimeException(); }
  private  int finishedTasks () { throw new RuntimeException(); }
  private  boolean _jobFinished () { throw new RuntimeException(); }
  public  boolean jobFinished () { throw new RuntimeException(); }
  private  org.apache.spark.scheduler.JobResult jobResult () { throw new RuntimeException(); }
  /**
   * Sends a signal to the DAGScheduler to cancel the job. The cancellation itself is handled
   * asynchronously. After the low level scheduler cancels all the tasks belonging to this job, it
   * will fail this job with a SparkException.
   */
  public  void cancel () { throw new RuntimeException(); }
  public  void taskSucceeded (int index, Object result) { throw new RuntimeException(); }
  public  void jobFailed (java.lang.Exception exception) { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.JobResult awaitResult () { throw new RuntimeException(); }
}
