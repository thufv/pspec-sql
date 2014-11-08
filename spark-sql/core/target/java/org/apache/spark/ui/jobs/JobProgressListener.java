package org.apache.spark.ui.jobs;
/**
 * :: DeveloperApi ::
 * Tracks task-level information to be displayed in the UI.
 * <p>
 * All access to the data structures in this class must be synchronized on the
 * class, since the UI thread and the EventBus loop may otherwise be reading and
 * updating the internal data structures concurrently.
 */
public  class JobProgressListener implements org.apache.spark.scheduler.SparkListener, org.apache.spark.Logging {
  static public  java.lang.String DEFAULT_POOL_NAME () { throw new RuntimeException(); }
  static public  int DEFAULT_RETAINED_STAGES () { throw new RuntimeException(); }
  public   JobProgressListener (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  public  int retainedStages () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<java.lang.Object, org.apache.spark.scheduler.StageInfo> activeStages () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<scala.Tuple2<java.lang.Object, java.lang.Object>, org.apache.spark.ui.jobs.UIData.StageUIData> stageIdToData () { throw new RuntimeException(); }
  public  scala.collection.mutable.ListBuffer<org.apache.spark.scheduler.StageInfo> completedStages () { throw new RuntimeException(); }
  public  scala.collection.mutable.ListBuffer<org.apache.spark.scheduler.StageInfo> failedStages () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<java.lang.String, scala.collection.mutable.HashMap<java.lang.Object, org.apache.spark.scheduler.StageInfo>> poolToActiveStages () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<java.lang.String, org.apache.spark.storage.BlockManagerId> executorIdToBlockManagerId () { throw new RuntimeException(); }
  public  scala.Option<scala.Enumeration.Value> schedulingMode () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.storage.BlockManagerId> blockManagerIds () { throw new RuntimeException(); }
  public  void onStageCompleted (org.apache.spark.scheduler.SparkListenerStageCompleted stageCompleted) { throw new RuntimeException(); }
  /** If stages is too large, remove and garbage collect old stages */
  private  void trimIfNecessary (scala.collection.mutable.ListBuffer<org.apache.spark.scheduler.StageInfo> stages) { throw new RuntimeException(); }
  /** For FIFO, all stages are contained by "default" pool but "default" pool here is meaningless */
  public  void onStageSubmitted (org.apache.spark.scheduler.SparkListenerStageSubmitted stageSubmitted) { throw new RuntimeException(); }
  public  void onTaskStart (org.apache.spark.scheduler.SparkListenerTaskStart taskStart) { throw new RuntimeException(); }
  public  void onTaskGettingResult (org.apache.spark.scheduler.SparkListenerTaskGettingResult taskGettingResult) { throw new RuntimeException(); }
  public  void onTaskEnd (org.apache.spark.scheduler.SparkListenerTaskEnd taskEnd) { throw new RuntimeException(); }
  /**
   * Upon receiving new metrics for a task, updates the per-stage and per-executor-per-stage
   * aggregate metrics by calculating deltas between the currently recorded metrics and the new
   * metrics.
   */
  public  void updateAggregateMetrics (org.apache.spark.ui.jobs.UIData.StageUIData stageData, java.lang.String execId, org.apache.spark.executor.TaskMetrics taskMetrics, scala.Option<org.apache.spark.executor.TaskMetrics> oldMetrics) { throw new RuntimeException(); }
  public  void onExecutorMetricsUpdate (org.apache.spark.scheduler.SparkListenerExecutorMetricsUpdate executorMetricsUpdate) { throw new RuntimeException(); }
  public  void onEnvironmentUpdate (org.apache.spark.scheduler.SparkListenerEnvironmentUpdate environmentUpdate) { throw new RuntimeException(); }
  public  void onBlockManagerAdded (org.apache.spark.scheduler.SparkListenerBlockManagerAdded blockManagerAdded) { throw new RuntimeException(); }
  public  void onBlockManagerRemoved (org.apache.spark.scheduler.SparkListenerBlockManagerRemoved blockManagerRemoved) { throw new RuntimeException(); }
}
