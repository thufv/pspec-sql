package org.apache.spark.scheduler;
/**
 * Runs a thread pool that deserializes and remotely fetches (if necessary) task results.
 */
private  class TaskResultGetter implements org.apache.spark.Logging {
  public   TaskResultGetter (org.apache.spark.SparkEnv sparkEnv, org.apache.spark.scheduler.TaskSchedulerImpl scheduler) { throw new RuntimeException(); }
  private  int THREADS () { throw new RuntimeException(); }
  private  java.util.concurrent.ThreadPoolExecutor getTaskResultExecutor () { throw new RuntimeException(); }
  protected  java.lang.ThreadLocal<org.apache.spark.serializer.SerializerInstance> serializer () { throw new RuntimeException(); }
  public  void enqueueSuccessfulTask (org.apache.spark.scheduler.TaskSetManager taskSetManager, long tid, java.nio.ByteBuffer serializedData) { throw new RuntimeException(); }
  public  void enqueueFailedTask (org.apache.spark.scheduler.TaskSetManager taskSetManager, long tid, scala.Enumeration.Value taskState, java.nio.ByteBuffer serializedData) { throw new RuntimeException(); }
  public  void stop () { throw new RuntimeException(); }
}
