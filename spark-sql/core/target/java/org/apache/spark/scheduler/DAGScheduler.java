package org.apache.spark.scheduler;
/**
 * The high-level scheduling layer that implements stage-oriented scheduling. It computes a DAG of
 * stages for each job, keeps track of which RDDs and stage outputs are materialized, and finds a
 * minimal schedule to run the job. It then submits stages as TaskSets to an underlying
 * TaskScheduler implementation that runs them on the cluster.
 * <p>
 * In addition to coming up with a DAG of stages, this class also determines the preferred
 * locations to run each task on, based on the current cache status, and passes these to the
 * low-level TaskScheduler. Furthermore, it handles failures due to shuffle output files being
 * lost, in which case old stages may need to be resubmitted. Failures *within* a stage that are
 * not caused by shuffle file loss are handled by the TaskScheduler, which will retry each task
 * a small number of times before cancelling the whole stage.
 * <p>
 */
private  class DAGScheduler implements org.apache.spark.Logging {
  static public  scala.concurrent.duration.FiniteDuration RESUBMIT_TIMEOUT () { throw new RuntimeException(); }
  static public  long POLL_TIMEOUT () { throw new RuntimeException(); }
  public  org.apache.spark.SparkContext sc () { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.TaskScheduler taskScheduler () { throw new RuntimeException(); }
  // not preceding
  public   DAGScheduler (org.apache.spark.SparkContext sc, org.apache.spark.scheduler.TaskScheduler taskScheduler, org.apache.spark.scheduler.LiveListenerBus listenerBus, org.apache.spark.MapOutputTrackerMaster mapOutputTracker, org.apache.spark.storage.BlockManagerMaster blockManagerMaster, org.apache.spark.SparkEnv env, org.apache.spark.util.Clock clock) { throw new RuntimeException(); }
  public   DAGScheduler (org.apache.spark.SparkContext sc, org.apache.spark.scheduler.TaskScheduler taskScheduler) { throw new RuntimeException(); }
  public   DAGScheduler (org.apache.spark.SparkContext sc) { throw new RuntimeException(); }
  public  java.util.concurrent.atomic.AtomicInteger nextJobId () { throw new RuntimeException(); }
  private  int numTotalJobs () { throw new RuntimeException(); }
  private  java.util.concurrent.atomic.AtomicInteger nextStageId () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<java.lang.Object, scala.collection.mutable.HashSet<java.lang.Object>> jobIdToStageIds () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<java.lang.Object, org.apache.spark.scheduler.Stage> stageIdToStage () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<java.lang.Object, org.apache.spark.scheduler.Stage> shuffleToMapStage () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<java.lang.Object, org.apache.spark.scheduler.ActiveJob> jobIdToActiveJob () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashSet<org.apache.spark.scheduler.Stage> waitingStages () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashSet<org.apache.spark.scheduler.Stage> runningStages () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashSet<org.apache.spark.scheduler.Stage> failedStages () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashSet<org.apache.spark.scheduler.ActiveJob> activeJobs () { throw new RuntimeException(); }
  private  scala.collection.mutable.HashMap<java.lang.Object, scala.collection.Seq<org.apache.spark.scheduler.TaskLocation>[]> cacheLocs () { throw new RuntimeException(); }
  private  scala.collection.mutable.HashMap<java.lang.String, java.lang.Object> failedEpoch () { throw new RuntimeException(); }
  private  akka.actor.ActorRef dagSchedulerActorSupervisor () { throw new RuntimeException(); }
  private  org.apache.spark.serializer.SerializerInstance closureSerializer () { throw new RuntimeException(); }
  public  akka.actor.ActorRef eventProcessActor () { throw new RuntimeException(); }
  /** If enabled, we may run certain actions like take() and first() locally. */
  private  boolean localExecutionEnabled () { throw new RuntimeException(); }
  private  void initializeEventProcessActor () { throw new RuntimeException(); }
  public  void taskStarted (org.apache.spark.scheduler.Task<?> task, org.apache.spark.scheduler.TaskInfo taskInfo) { throw new RuntimeException(); }
  public  void taskGettingResult (org.apache.spark.scheduler.TaskInfo taskInfo) { throw new RuntimeException(); }
  public  void taskEnded (org.apache.spark.scheduler.Task<?> task, org.apache.spark.TaskEndReason reason, Object result, scala.collection.mutable.Map<java.lang.Object, java.lang.Object> accumUpdates, org.apache.spark.scheduler.TaskInfo taskInfo, org.apache.spark.executor.TaskMetrics taskMetrics) { throw new RuntimeException(); }
  /**
   * Update metrics for in-progress tasks and let the master know that the BlockManager is still
   * alive. Return true if the driver knows about the given block manager. Otherwise, return false,
   * indicating that the block manager should re-register.
   */
  public  boolean executorHeartbeatReceived (java.lang.String execId, scala.Tuple4<java.lang.Object, java.lang.Object, java.lang.Object, org.apache.spark.executor.TaskMetrics>[] taskMetrics, org.apache.spark.storage.BlockManagerId blockManagerId) { throw new RuntimeException(); }
  public  void executorLost (java.lang.String execId) { throw new RuntimeException(); }
  public  void executorAdded (java.lang.String execId, java.lang.String host) { throw new RuntimeException(); }
  public  void taskSetFailed (org.apache.spark.scheduler.TaskSet taskSet, java.lang.String reason) { throw new RuntimeException(); }
  private  scala.collection.Seq<org.apache.spark.scheduler.TaskLocation>[] getCacheLocs (org.apache.spark.rdd.RDD<?> rdd) { throw new RuntimeException(); }
  private  void clearCacheLocs () { throw new RuntimeException(); }
  /**
   * Get or create a shuffle map stage for the given shuffle dependency's map side.
   * The jobId value passed in will be used if the stage doesn't already exist with
   * a lower jobId (jobId always increases across jobs.)
   */
  private  org.apache.spark.scheduler.Stage getShuffleMapStage (org.apache.spark.ShuffleDependency<?, ?, ?> shuffleDep, int jobId) { throw new RuntimeException(); }
  /**
   * Create a Stage -- either directly for use as a result stage, or as part of the (re)-creation
   * of a shuffle map stage in newOrUsedStage.  The stage will be associated with the provided
   * jobId. Production of shuffle map stages should always use newOrUsedStage, not newStage
   * directly.
   */
  private  org.apache.spark.scheduler.Stage newStage (org.apache.spark.rdd.RDD<?> rdd, int numTasks, scala.Option<org.apache.spark.ShuffleDependency<?, ?, ?>> shuffleDep, int jobId, org.apache.spark.util.CallSite callSite) { throw new RuntimeException(); }
  /**
   * Create a shuffle map Stage for the given RDD.  The stage will also be associated with the
   * provided jobId.  If a stage for the shuffleId existed previously so that the shuffleId is
   * present in the MapOutputTracker, then the number and location of available outputs are
   * recovered from the MapOutputTracker
   */
  private  org.apache.spark.scheduler.Stage newOrUsedStage (org.apache.spark.rdd.RDD<?> rdd, int numTasks, org.apache.spark.ShuffleDependency<?, ?, ?> shuffleDep, int jobId, org.apache.spark.util.CallSite callSite) { throw new RuntimeException(); }
  /**
   * Get or create the list of parent stages for a given RDD. The stages will be assigned the
   * provided jobId if they haven't already been created with a lower jobId.
   */
  private  scala.collection.immutable.List<org.apache.spark.scheduler.Stage> getParentStages (org.apache.spark.rdd.RDD<?> rdd, int jobId) { throw new RuntimeException(); }
  private  void registerShuffleDependencies (org.apache.spark.ShuffleDependency<?, ?, ?> shuffleDep, int jobId) { throw new RuntimeException(); }
  private  scala.collection.mutable.Stack<org.apache.spark.ShuffleDependency<?, ?, ?>> getAncestorShuffleDependencies (org.apache.spark.rdd.RDD<?> rdd) { throw new RuntimeException(); }
  private  scala.collection.immutable.List<org.apache.spark.scheduler.Stage> getMissingParentStages (org.apache.spark.scheduler.Stage stage) { throw new RuntimeException(); }
  /**
   * Registers the given jobId among the jobs that need the given stage and
   * all of that stage's ancestors.
   */
  private  void updateJobIdStageIdMaps (int jobId, org.apache.spark.scheduler.Stage stage) { throw new RuntimeException(); }
  /**
   * Removes state for job and any stages that are not needed by any other job.  Does not
   * handle cancelling tasks or notifying the SparkListener about finished jobs/stages/tasks.
   * <p>
   * @param job The job whose state to cleanup.
   */
  private  void cleanupStateForJobAndIndependentStages (org.apache.spark.scheduler.ActiveJob job) { throw new RuntimeException(); }
  /**
   * Submit a job to the job scheduler and get a JobWaiter object back. The JobWaiter object
   * can be used to block until the the job finishes executing or can be used to cancel the job.
   */
  public <T extends java.lang.Object, U extends java.lang.Object> org.apache.spark.scheduler.JobWaiter<U> submitJob (org.apache.spark.rdd.RDD<T> rdd, scala.Function2<org.apache.spark.TaskContext, scala.collection.Iterator<T>, U> func, scala.collection.Seq<java.lang.Object> partitions, org.apache.spark.util.CallSite callSite, boolean allowLocal, scala.Function2<java.lang.Object, U, scala.runtime.BoxedUnit> resultHandler, java.util.Properties properties) { throw new RuntimeException(); }
  public <T extends java.lang.Object, U extends java.lang.Object> void runJob (org.apache.spark.rdd.RDD<T> rdd, scala.Function2<org.apache.spark.TaskContext, scala.collection.Iterator<T>, U> func, scala.collection.Seq<java.lang.Object> partitions, org.apache.spark.util.CallSite callSite, boolean allowLocal, scala.Function2<java.lang.Object, U, scala.runtime.BoxedUnit> resultHandler, java.util.Properties properties, scala.reflect.ClassTag<U> evidence$1) { throw new RuntimeException(); }
  public <T extends java.lang.Object, U extends java.lang.Object, R extends java.lang.Object> org.apache.spark.partial.PartialResult<R> runApproximateJob (org.apache.spark.rdd.RDD<T> rdd, scala.Function2<org.apache.spark.TaskContext, scala.collection.Iterator<T>, U> func, org.apache.spark.partial.ApproximateEvaluator<U, R> evaluator, org.apache.spark.util.CallSite callSite, long timeout, java.util.Properties properties) { throw new RuntimeException(); }
  /**
   * Cancel a job that is running or waiting in the queue.
   */
  public  void cancelJob (int jobId) { throw new RuntimeException(); }
  public  void cancelJobGroup (java.lang.String groupId) { throw new RuntimeException(); }
  /**
   * Cancel all jobs that are running or waiting in the queue.
   */
  public  void cancelAllJobs () { throw new RuntimeException(); }
  private  void doCancelAllJobs () { throw new RuntimeException(); }
  /**
   * Cancel all jobs associated with a running or scheduled stage.
   */
  public  void cancelStage (int stageId) { throw new RuntimeException(); }
  /**
   * Resubmit any failed stages. Ordinarily called after a small amount of time has passed since
   * the last fetch failure.
   */
  private  void resubmitFailedStages () { throw new RuntimeException(); }
  /**
   * Check for waiting or failed stages which are now eligible for resubmission.
   * Ordinarily run on every iteration of the event loop.
   */
  private  void submitWaitingStages () { throw new RuntimeException(); }
  /**
   * Run a job on an RDD locally, assuming it has only a single partition and no dependencies.
   * We run the operation in a separate thread just in case it takes a bunch of time, so that we
   * don't block the DAGScheduler event loop or other concurrent jobs.
   */
  protected  void runLocally (org.apache.spark.scheduler.ActiveJob job) { throw new RuntimeException(); }
  protected  void runLocallyWithinThread (org.apache.spark.scheduler.ActiveJob job) { throw new RuntimeException(); }
  /** Finds the earliest-created active job that needs the stage */
  private  scala.Option<java.lang.Object> activeJobForStage (org.apache.spark.scheduler.Stage stage) { throw new RuntimeException(); }
  private  void handleJobGroupCancelled (java.lang.String groupId) { throw new RuntimeException(); }
  private  void handleBeginEvent (org.apache.spark.scheduler.Task<?> task, org.apache.spark.scheduler.TaskInfo taskInfo) { throw new RuntimeException(); }
  private  void handleTaskSetFailed (org.apache.spark.scheduler.TaskSet taskSet, java.lang.String reason) { throw new RuntimeException(); }
  private  void cleanUpAfterSchedulerStop () { throw new RuntimeException(); }
  private  void handleGetTaskResult (org.apache.spark.scheduler.TaskInfo taskInfo) { throw new RuntimeException(); }
  private  void handleJobSubmitted (int jobId, org.apache.spark.rdd.RDD<?> finalRDD, scala.Function2<org.apache.spark.TaskContext, scala.collection.Iterator<java.lang.Object>, ?> func, int[] partitions, boolean allowLocal, org.apache.spark.util.CallSite callSite, org.apache.spark.scheduler.JobListener listener, java.util.Properties properties) { throw new RuntimeException(); }
  /** Submits stage, but first recursively submits any missing parents. */
  private  void submitStage (org.apache.spark.scheduler.Stage stage) { throw new RuntimeException(); }
  /** Called when stage's parents are available and we can now do its task. */
  private  void submitMissingTasks (org.apache.spark.scheduler.Stage stage, int jobId) { throw new RuntimeException(); }
  /**
   * Responds to a task finishing. This is called inside the event loop so it assumes that it can
   * modify the scheduler's internal state. Use taskEnded() to post a task end event from outside.
   */
  private  void handleTaskCompletion (org.apache.spark.scheduler.CompletionEvent event) { throw new RuntimeException(); }
  /**
   * Responds to an executor being lost. This is called inside the event loop, so it assumes it can
   * modify the scheduler's internal state. Use executorLost() to post a loss event from outside.
   * <p>
   * Optionally the epoch during which the failure was caught can be passed to avoid allowing
   * stray fetch failures from possibly retriggering the detection of a node as lost.
   */
  private  void handleExecutorLost (java.lang.String execId, scala.Option<java.lang.Object> maybeEpoch) { throw new RuntimeException(); }
  private  void handleExecutorAdded (java.lang.String execId, java.lang.String host) { throw new RuntimeException(); }
  private  void handleStageCancellation (int stageId) { throw new RuntimeException(); }
  private  void handleJobCancellation (int jobId, java.lang.String reason) { throw new RuntimeException(); }
  /**
   * Aborts all jobs depending on a particular Stage. This is called in response to a task set
   * being canceled by the TaskScheduler. Use taskSetFailed() to inject this event from outside.
   */
  private  void abortStage (org.apache.spark.scheduler.Stage failedStage, java.lang.String reason) { throw new RuntimeException(); }
  /**
   * Fails a job and all stages that are only used by that job, and cleans up relevant state.
   */
  private  void failJobAndIndependentStages (org.apache.spark.scheduler.ActiveJob job, java.lang.String failureReason) { throw new RuntimeException(); }
  /**
   * Return true if one of stage's ancestors is target.
   */
  private  boolean stageDependsOn (org.apache.spark.scheduler.Stage stage, org.apache.spark.scheduler.Stage target) { throw new RuntimeException(); }
  /**
   * Synchronized method that might be called from other threads.
   * @param rdd whose partitions are to be looked at
   * @param partition to lookup locality information for
   * @return list of machines that are preferred by the partition
   */
  private  scala.collection.Seq<org.apache.spark.scheduler.TaskLocation> getPreferredLocs (org.apache.spark.rdd.RDD<?> rdd, int partition) { throw new RuntimeException(); }
  /** Recursive implementation for getPreferredLocs. */
  private  scala.collection.Seq<org.apache.spark.scheduler.TaskLocation> getPreferredLocsInternal (org.apache.spark.rdd.RDD<?> rdd, int partition, scala.collection.mutable.HashSet<scala.Tuple2<org.apache.spark.rdd.RDD<?>, java.lang.Object>> visited) { throw new RuntimeException(); }
  public  void stop () { throw new RuntimeException(); }
}
