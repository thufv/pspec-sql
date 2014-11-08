package org.apache.spark.scheduler;
/**
 * Low-level task scheduler interface, currently implemented exclusively by TaskSchedulerImpl.
 * This interface allows plugging in different task schedulers. Each TaskScheduler schedulers tasks
 * for a single SparkContext. These schedulers get sets of tasks submitted to them from the
 * DAGScheduler for each stage, and are responsible for sending the tasks to the cluster, running
 * them, retrying if there are failures, and mitigating stragglers. They return events to the
 * DAGScheduler.
 */
private abstract interface TaskScheduler {
  public abstract  org.apache.spark.scheduler.Pool rootPool () ;
  public abstract  scala.Enumeration.Value schedulingMode () ;
  public abstract  void start () ;
  public  void postStartHook () ;
  public abstract  void stop () ;
  public abstract  void submitTasks (org.apache.spark.scheduler.TaskSet taskSet) ;
  public abstract  void cancelTasks (int stageId, boolean interruptThread) ;
  public abstract  void setDAGScheduler (org.apache.spark.scheduler.DAGScheduler dagScheduler) ;
  public abstract  int defaultParallelism () ;
  /**
   * Update metrics for in-progress tasks and let the master know that the BlockManager is still
   * alive. Return true if the driver knows about the given block manager. Otherwise, return false,
   * indicating that the block manager should re-register.
   */
  public abstract  boolean executorHeartbeatReceived (java.lang.String execId, scala.Tuple2<java.lang.Object, org.apache.spark.executor.TaskMetrics>[] taskMetrics, org.apache.spark.storage.BlockManagerId blockManagerId) ;
}
