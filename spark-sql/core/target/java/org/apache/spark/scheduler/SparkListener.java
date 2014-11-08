package org.apache.spark.scheduler;
/**
 * :: DeveloperApi ::
 * Interface for listening to events from the Spark scheduler. Note that this is an internal
 * interface which might change in different Spark releases.
 */
public abstract interface SparkListener {
  /**
   * Called when a stage completes successfully or fails, with information on the completed stage.
   */
  public  void onStageCompleted (org.apache.spark.scheduler.SparkListenerStageCompleted stageCompleted) ;
  /**
   * Called when a stage is submitted
   */
  public  void onStageSubmitted (org.apache.spark.scheduler.SparkListenerStageSubmitted stageSubmitted) ;
  /**
   * Called when a task starts
   */
  public  void onTaskStart (org.apache.spark.scheduler.SparkListenerTaskStart taskStart) ;
  /**
   * Called when a task begins remotely fetching its result (will not be called for tasks that do
   * not need to fetch the result remotely).
   */
  public  void onTaskGettingResult (org.apache.spark.scheduler.SparkListenerTaskGettingResult taskGettingResult) ;
  /**
   * Called when a task ends
   */
  public  void onTaskEnd (org.apache.spark.scheduler.SparkListenerTaskEnd taskEnd) ;
  /**
   * Called when a job starts
   */
  public  void onJobStart (org.apache.spark.scheduler.SparkListenerJobStart jobStart) ;
  /**
   * Called when a job ends
   */
  public  void onJobEnd (org.apache.spark.scheduler.SparkListenerJobEnd jobEnd) ;
  /**
   * Called when environment properties have been updated
   */
  public  void onEnvironmentUpdate (org.apache.spark.scheduler.SparkListenerEnvironmentUpdate environmentUpdate) ;
  /**
   * Called when a new block manager has joined
   */
  public  void onBlockManagerAdded (org.apache.spark.scheduler.SparkListenerBlockManagerAdded blockManagerAdded) ;
  /**
   * Called when an existing block manager has been removed
   */
  public  void onBlockManagerRemoved (org.apache.spark.scheduler.SparkListenerBlockManagerRemoved blockManagerRemoved) ;
  /**
   * Called when an RDD is manually unpersisted by the application
   */
  public  void onUnpersistRDD (org.apache.spark.scheduler.SparkListenerUnpersistRDD unpersistRDD) ;
  /**
   * Called when the application starts
   */
  public  void onApplicationStart (org.apache.spark.scheduler.SparkListenerApplicationStart applicationStart) ;
  /**
   * Called when the application ends
   */
  public  void onApplicationEnd (org.apache.spark.scheduler.SparkListenerApplicationEnd applicationEnd) ;
  /**
   * Called when the driver receives task metrics from an executor in a heartbeat.
   */
  public  void onExecutorMetricsUpdate (org.apache.spark.scheduler.SparkListenerExecutorMetricsUpdate executorMetricsUpdate) ;
}
