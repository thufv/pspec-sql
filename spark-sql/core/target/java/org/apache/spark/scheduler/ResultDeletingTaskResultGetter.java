package org.apache.spark.scheduler;
/**
 * Removes the TaskResult from the BlockManager before delegating to a normal TaskResultGetter.
 * <p>
 * Used to test the case where a BlockManager evicts the task result (or dies) before the
 * TaskResult is retrieved.
 */
public  class ResultDeletingTaskResultGetter extends org.apache.spark.scheduler.TaskResultGetter {
  public   ResultDeletingTaskResultGetter (org.apache.spark.SparkEnv sparkEnv, org.apache.spark.scheduler.TaskSchedulerImpl scheduler) { throw new RuntimeException(); }
  public  boolean removedResult () { throw new RuntimeException(); }
  public  void enqueueSuccessfulTask (org.apache.spark.scheduler.TaskSetManager taskSetManager, long tid, java.nio.ByteBuffer serializedData) { throw new RuntimeException(); }
}
