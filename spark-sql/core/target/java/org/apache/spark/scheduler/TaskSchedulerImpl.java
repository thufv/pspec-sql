package org.apache.spark.scheduler;
/**
 * Schedules tasks for multiple types of clusters by acting through a SchedulerBackend.
 * It can also work with a local setup by using a LocalBackend and setting isLocal to true.
 * It handles common logic, like determining a scheduling order across jobs, waking up to launch
 * speculative tasks, etc.
 * <p>
 * Clients should first call initialize() and start(), then submit task sets through the
 * runTasks method.
 * <p>
 * THREADING: SchedulerBackends and task-submitting clients can call this class from multiple
 * threads, so it needs locks in public API methods to maintain its state. In addition, some
 * SchedulerBackends synchronize on themselves when they want to send events here, and then
 * acquire a lock on us, so we need to make sure that we don't try to lock the backend while
 * we are holding a lock on ourselves.
 */
private  class TaskSchedulerImpl implements org.apache.spark.scheduler.TaskScheduler, org.apache.spark.Logging {
  /**
   * Used to balance containers across hosts.
   * <p>
   * Accepts a map of hosts to resource offers for that host, and returns a prioritized list of
   * resource offers representing the order in which the offers should be used.  The resource
   * offers are ordered such that we'll allocate one container on each host before allocating a
   * second container on any host, and so on, in order to reduce the damage if a host fails.
   * <p>
   * For example, given <h1, [o1, o2, o3]>, <h2, [o4]>, <h1, [o5, o6]>, returns
   * [o1, o5, o4, 02, o6, o3]
   */
  static public <K extends java.lang.Object, T extends java.lang.Object> scala.collection.immutable.List<T> prioritizeContainers (scala.collection.mutable.HashMap<K, scala.collection.mutable.ArrayBuffer<T>> map) { throw new RuntimeException(); }
  public  org.apache.spark.SparkContext sc () { throw new RuntimeException(); }
  public  int maxTaskFailures () { throw new RuntimeException(); }
  // not preceding
  public   TaskSchedulerImpl (org.apache.spark.SparkContext sc, int maxTaskFailures, boolean isLocal) { throw new RuntimeException(); }
  public   TaskSchedulerImpl (org.apache.spark.SparkContext sc) { throw new RuntimeException(); }
  public  org.apache.spark.SparkConf conf () { throw new RuntimeException(); }
  public  long SPECULATION_INTERVAL () { throw new RuntimeException(); }
  public  long STARVATION_TIMEOUT () { throw new RuntimeException(); }
  public  int CPUS_PER_TASK () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<java.lang.String, org.apache.spark.scheduler.TaskSetManager> activeTaskSets () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<java.lang.Object, java.lang.String> taskIdToTaskSetId () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<java.lang.Object, java.lang.String> taskIdToExecutorId () { throw new RuntimeException(); }
  private  boolean hasReceivedTask () { throw new RuntimeException(); }
  private  boolean hasLaunchedTask () { throw new RuntimeException(); }
  private  java.util.Timer starvationTimer () { throw new RuntimeException(); }
  public  java.util.concurrent.atomic.AtomicLong nextTaskId () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashSet<java.lang.String> activeExecutorIds () { throw new RuntimeException(); }
  protected  scala.collection.mutable.HashMap<java.lang.String, scala.collection.mutable.HashSet<java.lang.String>> executorsByHost () { throw new RuntimeException(); }
  protected  scala.collection.mutable.HashMap<java.lang.String, scala.collection.mutable.HashSet<java.lang.String>> hostsByRack () { throw new RuntimeException(); }
  protected  scala.collection.mutable.HashMap<java.lang.String, java.lang.String> executorIdToHost () { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.DAGScheduler dagScheduler () { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.SchedulerBackend backend () { throw new RuntimeException(); }
  public  org.apache.spark.MapOutputTracker mapOutputTracker () { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.SchedulableBuilder schedulableBuilder () { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.Pool rootPool () { throw new RuntimeException(); }
  private  java.lang.String schedulingModeConf () { throw new RuntimeException(); }
  public  scala.Enumeration.Value schedulingMode () { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.TaskResultGetter taskResultGetter () { throw new RuntimeException(); }
  public  void setDAGScheduler (org.apache.spark.scheduler.DAGScheduler dagScheduler) { throw new RuntimeException(); }
  public  void initialize (org.apache.spark.scheduler.SchedulerBackend backend) { throw new RuntimeException(); }
  public  long newTaskId () { throw new RuntimeException(); }
  public  void start () { throw new RuntimeException(); }
  public  void postStartHook () { throw new RuntimeException(); }
  public  void submitTasks (org.apache.spark.scheduler.TaskSet taskSet) { throw new RuntimeException(); }
  public  void cancelTasks (int stageId, boolean interruptThread) { throw new RuntimeException(); }
  /**
   * Called to indicate that all task attempts (including speculated tasks) associated with the
   * given TaskSetManager have completed, so state associated with the TaskSetManager should be
   * cleaned up.
   */
  public  void taskSetFinished (org.apache.spark.scheduler.TaskSetManager manager) { throw new RuntimeException(); }
  /**
   * Called by cluster manager to offer resources on slaves. We respond by asking our active task
   * sets for tasks in order of priority. We fill each node with tasks in a round-robin manner so
   * that tasks are balanced across the cluster.
   */
  public  scala.collection.Seq<scala.collection.Seq<org.apache.spark.scheduler.TaskDescription>> resourceOffers (scala.collection.Seq<org.apache.spark.scheduler.WorkerOffer> offers) { throw new RuntimeException(); }
  public  void statusUpdate (long tid, scala.Enumeration.Value state, java.nio.ByteBuffer serializedData) { throw new RuntimeException(); }
  /**
   * Update metrics for in-progress tasks and let the master know that the BlockManager is still
   * alive. Return true if the driver knows about the given block manager. Otherwise, return false,
   * indicating that the block manager should re-register.
   */
  public  boolean executorHeartbeatReceived (java.lang.String execId, scala.Tuple2<java.lang.Object, org.apache.spark.executor.TaskMetrics>[] taskMetrics, org.apache.spark.storage.BlockManagerId blockManagerId) { throw new RuntimeException(); }
  public  void handleTaskGettingResult (org.apache.spark.scheduler.TaskSetManager taskSetManager, long tid) { throw new RuntimeException(); }
  public  void handleSuccessfulTask (org.apache.spark.scheduler.TaskSetManager taskSetManager, long tid, org.apache.spark.scheduler.DirectTaskResult<?> taskResult) { throw new RuntimeException(); }
  public  void handleFailedTask (org.apache.spark.scheduler.TaskSetManager taskSetManager, long tid, scala.Enumeration.Value taskState, org.apache.spark.TaskEndReason reason) { throw new RuntimeException(); }
  public  void error (java.lang.String message) { throw new RuntimeException(); }
  public  void stop () { throw new RuntimeException(); }
  public  int defaultParallelism () { throw new RuntimeException(); }
  public  void checkSpeculatableTasks () { throw new RuntimeException(); }
  public  void executorLost (java.lang.String executorId, org.apache.spark.scheduler.ExecutorLossReason reason) { throw new RuntimeException(); }
  /** Remove an executor from all our data structures and mark it as lost */
  private  void removeExecutor (java.lang.String executorId) { throw new RuntimeException(); }
  public  void executorAdded (java.lang.String execId, java.lang.String host) { throw new RuntimeException(); }
  public  scala.Option<scala.collection.immutable.Set<java.lang.String>> getExecutorsAliveOnHost (java.lang.String host) { throw new RuntimeException(); }
  public  boolean hasExecutorsAliveOnHost (java.lang.String host) { throw new RuntimeException(); }
  public  boolean hasHostAliveOnRack (java.lang.String rack) { throw new RuntimeException(); }
  public  boolean isExecutorAlive (java.lang.String execId) { throw new RuntimeException(); }
  public  scala.Option<java.lang.String> getRackForHost (java.lang.String value) { throw new RuntimeException(); }
  private  void waitBackendReady () { throw new RuntimeException(); }
}
