package org.apache.spark.scheduler;
/**
 * :: DeveloperApi ::
 * A logger class to record runtime information for jobs in Spark. This class outputs one log file
 * for each Spark job, containing tasks start/stop and shuffle information. JobLogger is a subclass
 * of SparkListener, use addSparkListener to add JobLogger to a SparkContext after the SparkContext
 * is created. Note that each JobLogger only works for one SparkContext
 * <p>
 * NOTE: The functionality of this class is heavily stripped down to accommodate for a general
 * refactor of the SparkListener interface. In its place, the EventLoggingListener is introduced
 * to log application information as SparkListenerEvents. To enable this functionality, set
 * spark.eventLog.enabled to true.
 */
public  class JobLogger implements org.apache.spark.scheduler.SparkListener, org.apache.spark.Logging {
  public  java.lang.String user () { throw new RuntimeException(); }
  public  java.lang.String logDirName () { throw new RuntimeException(); }
  // not preceding
  public   JobLogger (java.lang.String user, java.lang.String logDirName) { throw new RuntimeException(); }
  public   JobLogger () { throw new RuntimeException(); }
  private  java.lang.String logDir () { throw new RuntimeException(); }
  private  scala.collection.mutable.HashMap<java.lang.Object, java.io.PrintWriter> jobIdToPrintWriter () { throw new RuntimeException(); }
  private  scala.collection.mutable.HashMap<java.lang.Object, java.lang.Object> stageIdToJobId () { throw new RuntimeException(); }
  private  scala.collection.mutable.HashMap<java.lang.Object, scala.collection.Seq<java.lang.Object>> jobIdToStageIds () { throw new RuntimeException(); }
  private  java.lang.ThreadLocal<java.text.SimpleDateFormat> dateFormat () { throw new RuntimeException(); }
  private  java.util.concurrent.LinkedBlockingQueue<org.apache.spark.scheduler.SparkListenerEvent> eventQueue () { throw new RuntimeException(); }
  private  java.lang.String getLogDir () { throw new RuntimeException(); }
  private  scala.collection.mutable.HashMap<java.lang.Object, java.io.PrintWriter> getJobIdToPrintWriter () { throw new RuntimeException(); }
  private  scala.collection.mutable.HashMap<java.lang.Object, java.lang.Object> getStageIdToJobId () { throw new RuntimeException(); }
  private  scala.collection.mutable.HashMap<java.lang.Object, scala.collection.Seq<java.lang.Object>> getJobIdToStageIds () { throw new RuntimeException(); }
  private  java.util.concurrent.LinkedBlockingQueue<org.apache.spark.scheduler.SparkListenerEvent> getEventQueue () { throw new RuntimeException(); }
  /** Create a folder for log files, the folder's name is the creation time of jobLogger */
  protected  void createLogDir () { throw new RuntimeException(); }
  /**
   * Create a log file for one job
   * @param jobId ID of the job
   * @throws FileNotFoundException Fail to create log file
   */
  protected  void createLogWriter (int jobId) { throw new RuntimeException(); }
  /**
   * Close log file, and clean the stage relationship in stageIdToJobId
   * @param jobId ID of the job
   */
  protected  void closeLogWriter (int jobId) { throw new RuntimeException(); }
  /**
   * Build up the maps that represent stage-job relationships
   * @param jobId ID of the job
   * @param stageIds IDs of the associated stages
   */
  protected  void buildJobStageDependencies (int jobId, scala.collection.Seq<java.lang.Object> stageIds) { throw new RuntimeException(); }
  /**
   * Write info into log file
   * @param jobId ID of the job
   * @param info Info to be recorded
   * @param withTime Controls whether to record time stamp before the info, default is true
   */
  protected  void jobLogInfo (int jobId, java.lang.String info, boolean withTime) { throw new RuntimeException(); }
  /**
   * Write info into log file
   * @param stageId ID of the stage
   * @param info Info to be recorded
   * @param withTime Controls whether to record time stamp before the info, default is true
   */
  protected  void stageLogInfo (int stageId, java.lang.String info, boolean withTime) { throw new RuntimeException(); }
  /**
   * Record task metrics into job log files, including execution info and shuffle metrics
   * @param stageId Stage ID of the task
   * @param status Status info of the task
   * @param taskInfo Task description info
   * @param taskMetrics Task running metrics
   */
  protected  void recordTaskMetrics (int stageId, java.lang.String status, org.apache.spark.scheduler.TaskInfo taskInfo, org.apache.spark.executor.TaskMetrics taskMetrics) { throw new RuntimeException(); }
  /**
   * When stage is submitted, record stage submit info
   * @param stageSubmitted Stage submitted event
   */
  public  void onStageSubmitted (org.apache.spark.scheduler.SparkListenerStageSubmitted stageSubmitted) { throw new RuntimeException(); }
  /**
   * When stage is completed, record stage completion status
   * @param stageCompleted Stage completed event
   */
  public  void onStageCompleted (org.apache.spark.scheduler.SparkListenerStageCompleted stageCompleted) { throw new RuntimeException(); }
  /**
   * When task ends, record task completion status and metrics
   * @param taskEnd Task end event
   */
  public  void onTaskEnd (org.apache.spark.scheduler.SparkListenerTaskEnd taskEnd) { throw new RuntimeException(); }
  /**
   * When job ends, recording job completion status and close log file
   * @param jobEnd Job end event
   */
  public  void onJobEnd (org.apache.spark.scheduler.SparkListenerJobEnd jobEnd) { throw new RuntimeException(); }
  /**
   * Record job properties into job log file
   * @param jobId ID of the job
   * @param properties Properties of the job
   */
  protected  void recordJobProperties (int jobId, java.util.Properties properties) { throw new RuntimeException(); }
  /**
   * When job starts, record job property and stage graph
   * @param jobStart Job start event
   */
  public  void onJobStart (org.apache.spark.scheduler.SparkListenerJobStart jobStart) { throw new RuntimeException(); }
}
