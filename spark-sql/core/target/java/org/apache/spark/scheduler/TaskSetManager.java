package org.apache.spark.scheduler;
/**
 * Schedules the tasks within a single TaskSet in the TaskSchedulerImpl. This class keeps track of
 * each task, retries tasks if they fail (up to a limited number of times), and
 * handles locality-aware scheduling for this TaskSet via delay scheduling. The main interfaces
 * to it are resourceOffer, which asks the TaskSet whether it wants to run a task on one node,
 * and statusUpdate, which tells it that one of its tasks changed state (e.g. finished).
 * <p>
 * THREADING: This class is designed to only be called from code with a lock on the
 * TaskScheduler (e.g. its event handlers). It should not be called from other threads.
 * <p>
 * @param sched           the TaskSchedulerImpl associated with the TaskSetManager
 * @param taskSet         the TaskSet to manage scheduling for
 * @param maxTaskFailures if any particular task fails more than this number of times, the entire
 *                        task set will be aborted
 */
private  class TaskSetManager implements org.apache.spark.scheduler.Schedulable, org.apache.spark.Logging {
  static public  int TASK_SIZE_TO_WARN_KB () { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.TaskSet taskSet () { throw new RuntimeException(); }
  public  int maxTaskFailures () { throw new RuntimeException(); }
  // not preceding
  public   TaskSetManager (org.apache.spark.scheduler.TaskSchedulerImpl sched, org.apache.spark.scheduler.TaskSet taskSet, int maxTaskFailures, org.apache.spark.util.Clock clock) { throw new RuntimeException(); }
  public  org.apache.spark.SparkConf conf () { throw new RuntimeException(); }
  private  long EXECUTOR_TASK_BLACKLIST_TIMEOUT () { throw new RuntimeException(); }
  public  double SPECULATION_QUANTILE () { throw new RuntimeException(); }
  public  double SPECULATION_MULTIPLIER () { throw new RuntimeException(); }
  public  org.apache.spark.SparkEnv env () { throw new RuntimeException(); }
  public  org.apache.spark.serializer.SerializerInstance ser () { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.Task<?>[] tasks () { throw new RuntimeException(); }
  public  int numTasks () { throw new RuntimeException(); }
  public  int[] copiesRunning () { throw new RuntimeException(); }
  public  boolean[] successful () { throw new RuntimeException(); }
  private  int[] numFailures () { throw new RuntimeException(); }
  private  scala.collection.mutable.HashMap<java.lang.Object, scala.collection.mutable.HashMap<java.lang.String, java.lang.Object>> failedExecutors () { throw new RuntimeException(); }
  public  scala.collection.immutable.List<org.apache.spark.scheduler.TaskInfo>[] taskAttempts () { throw new RuntimeException(); }
  public  int tasksSuccessful () { throw new RuntimeException(); }
  public  int weight () { throw new RuntimeException(); }
  public  int minShare () { throw new RuntimeException(); }
  public  int priority () { throw new RuntimeException(); }
  public  int stageId () { throw new RuntimeException(); }
  public  java.lang.String name () { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.Pool parent () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashSet<java.lang.Object> runningTasksSet () { throw new RuntimeException(); }
  public  int runningTasks () { throw new RuntimeException(); }
  public  boolean isZombie () { throw new RuntimeException(); }
  private  scala.collection.mutable.HashMap<java.lang.String, scala.collection.mutable.ArrayBuffer<java.lang.Object>> pendingTasksForExecutor () { throw new RuntimeException(); }
  private  scala.collection.mutable.HashMap<java.lang.String, scala.collection.mutable.ArrayBuffer<java.lang.Object>> pendingTasksForHost () { throw new RuntimeException(); }
  private  scala.collection.mutable.HashMap<java.lang.String, scala.collection.mutable.ArrayBuffer<java.lang.Object>> pendingTasksForRack () { throw new RuntimeException(); }
  public  scala.collection.mutable.ArrayBuffer<java.lang.Object> pendingTasksWithNoPrefs () { throw new RuntimeException(); }
  public  scala.collection.mutable.ArrayBuffer<java.lang.Object> allPendingTasks () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashSet<java.lang.Object> speculatableTasks () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<java.lang.Object, org.apache.spark.scheduler.TaskInfo> taskInfos () { throw new RuntimeException(); }
  public  long EXCEPTION_PRINT_INTERVAL () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<java.lang.String, scala.Tuple2<java.lang.Object, java.lang.Object>> recentExceptions () { throw new RuntimeException(); }
  public  long epoch () { throw new RuntimeException(); }
  public  scala.Enumeration.Value[] myLocalityLevels () { throw new RuntimeException(); }
  public  long[] localityWaits () { throw new RuntimeException(); }
  public  int currentLocalityIndex () { throw new RuntimeException(); }
  public  long lastLaunchTime () { throw new RuntimeException(); }
  public  scala.Null schedulableQueue () { throw new RuntimeException(); }
  public  scala.Enumeration.Value schedulingMode () { throw new RuntimeException(); }
  public  boolean emittedTaskSizeWarning () { throw new RuntimeException(); }
  /**
   * Add a task to all the pending-task lists that it should be on. If readding is set, we are
   * re-adding the task so only include it in each list if it's not already there.
   */
  private  void addPendingTask (int index, boolean readding) { throw new RuntimeException(); }
  /**
   * Return the pending tasks list for a given executor ID, or an empty list if
   * there is no map entry for that host
   */
  private  scala.collection.mutable.ArrayBuffer<java.lang.Object> getPendingTasksForExecutor (java.lang.String executorId) { throw new RuntimeException(); }
  /**
   * Return the pending tasks list for a given host, or an empty list if
   * there is no map entry for that host
   */
  private  scala.collection.mutable.ArrayBuffer<java.lang.Object> getPendingTasksForHost (java.lang.String host) { throw new RuntimeException(); }
  /**
   * Return the pending rack-local task list for a given rack, or an empty list if
   * there is no map entry for that rack
   */
  private  scala.collection.mutable.ArrayBuffer<java.lang.Object> getPendingTasksForRack (java.lang.String rack) { throw new RuntimeException(); }
  /**
   * Dequeue a pending task from the given list and return its index.
   * Return None if the list is empty.
   * This method also cleans up any tasks in the list that have already
   * been launched, since we want that to happen lazily.
   */
  private  scala.Option<java.lang.Object> findTaskFromList (java.lang.String execId, scala.collection.mutable.ArrayBuffer<java.lang.Object> list) { throw new RuntimeException(); }
  /** Check whether a task is currently running an attempt on a given host */
  private  boolean hasAttemptOnHost (int taskIndex, java.lang.String host) { throw new RuntimeException(); }
  /**
   * Is this re-execution of a failed task on an executor it already failed in before
   * EXECUTOR_TASK_BLACKLIST_TIMEOUT has elapsed ?
   */
  private  boolean executorIsBlacklisted (java.lang.String execId, int taskId) { throw new RuntimeException(); }
  /**
   * Return a speculative task for a given executor if any are available. The task should not have
   * an attempt running on this host, in case the host is slow. In addition, the task should meet
   * the given locality constraint.
   */
  private  scala.Option<scala.Tuple2<java.lang.Object, scala.Enumeration.Value>> findSpeculativeTask (java.lang.String execId, java.lang.String host, scala.Enumeration.Value locality) { throw new RuntimeException(); }
  /**
   * Dequeue a pending task for a given node and return its index and locality level.
   * Only search for tasks matching the given locality constraint.
   * <p>
   * @return An option containing (task index within the task set, locality, is speculative?)
   */
  private  scala.Option<scala.Tuple3<java.lang.Object, scala.Enumeration.Value, java.lang.Object>> findTask (java.lang.String execId, java.lang.String host, scala.Enumeration.Value maxLocality) { throw new RuntimeException(); }
  /**
   * Respond to an offer of a single executor from the scheduler by finding a task
   * <p>
   * NOTE: this function is either called with a maxLocality which
   * would be adjusted by delay scheduling algorithm or it will be with a special
   * NO_PREF locality which will be not modified
   * <p>
   * @param execId the executor Id of the offered resource
   * @param host  the host Id of the offered resource
   * @param maxLocality the maximum locality we want to schedule the tasks at
   */
  public  scala.Option<org.apache.spark.scheduler.TaskDescription> resourceOffer (java.lang.String execId, java.lang.String host, scala.Enumeration.Value maxLocality) { throw new RuntimeException(); }
  private  void maybeFinishTaskSet () { throw new RuntimeException(); }
  /**
   * Get the level we can launch tasks according to delay scheduling, based on current wait time.
   */
  private  scala.Enumeration.Value getAllowedLocalityLevel (long curTime) { throw new RuntimeException(); }
  /**
   * Find the index in myLocalityLevels for a given locality. This is also designed to work with
   * localities that are not in myLocalityLevels (in case we somehow get those) by returning the
   * next-biggest level we have. Uses the fact that the last value in myLocalityLevels is ANY.
   */
  public  int getLocalityIndex (scala.Enumeration.Value locality) { throw new RuntimeException(); }
  public  void handleTaskGettingResult (long tid) { throw new RuntimeException(); }
  /**
   * Marks the task as successful and notifies the DAGScheduler that a task has ended.
   */
  public  void handleSuccessfulTask (long tid, org.apache.spark.scheduler.DirectTaskResult<?> result) { throw new RuntimeException(); }
  /**
   * Marks the task as failed, re-adds it to the list of pending tasks, and notifies the
   * DAG Scheduler.
   */
  public  void handleFailedTask (long tid, scala.Enumeration.Value state, org.apache.spark.TaskEndReason reason) { throw new RuntimeException(); }
  public  void abort (java.lang.String message) { throw new RuntimeException(); }
  /** If the given task ID is not in the set of running tasks, adds it.
   * <p>
   * Used to keep track of the number of running tasks, for enforcing scheduling policies.
   */
  public  void addRunningTask (long tid) { throw new RuntimeException(); }
  /** If the given task ID is in the set of running tasks, removes it. */
  public  void removeRunningTask (long tid) { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.Schedulable getSchedulableByName (java.lang.String name) { throw new RuntimeException(); }
  public  void addSchedulable (org.apache.spark.scheduler.Schedulable schedulable) { throw new RuntimeException(); }
  public  void removeSchedulable (org.apache.spark.scheduler.Schedulable schedulable) { throw new RuntimeException(); }
  public  scala.collection.mutable.ArrayBuffer<org.apache.spark.scheduler.TaskSetManager> getSortedTaskSetQueue () { throw new RuntimeException(); }
  /** Called by TaskScheduler when an executor is lost so we can re-enqueue our tasks */
  public  void executorLost (java.lang.String execId, java.lang.String host) { throw new RuntimeException(); }
  /**
   * Check for tasks to be speculated and return true if there are any. This is called periodically
   * by the TaskScheduler.
   * <p>
   * TODO: To make this scale to large jobs, we need to maintain a list of running tasks, so that
   * we don't scan the whole task set. It might also help to make this sorted by launch time.
   */
  public  boolean checkSpeculatableTasks () { throw new RuntimeException(); }
  private  long getLocalityWait (scala.Enumeration.Value level) { throw new RuntimeException(); }
  /**
   * Compute the locality levels used in this TaskSet. Assumes that all tasks have already been
   * added to queues using addPendingTask.
   * <p>
   */
  private  scala.Enumeration.Value[] computeValidLocalityLevels () { throw new RuntimeException(); }
  public  void recomputeLocality () { throw new RuntimeException(); }
  public  void executorAdded () { throw new RuntimeException(); }
}
