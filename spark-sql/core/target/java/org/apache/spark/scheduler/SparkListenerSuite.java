package org.apache.spark.scheduler;
public  class SparkListenerSuite extends org.scalatest.FunSuite implements org.apache.spark.LocalSparkContext, org.scalatest.Matchers, org.scalatest.BeforeAndAfter, org.scalatest.BeforeAndAfterAll {
  public   SparkListenerSuite () { throw new RuntimeException(); }
  /** Length of time to wait while draining listener events. */
  public  int WAIT_TIMEOUT_MILLIS () { throw new RuntimeException(); }
  public  void afterAll () { throw new RuntimeException(); }
  /**
   * Assert that the given list of numbers has an average that is greater than zero.
   */
  private  void checkNonZeroAvg (scala.collection.Traversable<java.lang.Object> m, java.lang.String msg) { throw new RuntimeException(); }
  /**
   * A simple listener that counts the number of jobs observed.
   */
  private  class BasicJobCounter implements org.apache.spark.scheduler.SparkListener {
    public   BasicJobCounter () { throw new RuntimeException(); }
    public  int count () { throw new RuntimeException(); }
    public  void onJobEnd (org.apache.spark.scheduler.SparkListenerJobEnd job) { throw new RuntimeException(); }
  }
  /**
   * A simple listener that saves all task infos and task metrics.
   */
  private  class SaveStageAndTaskInfo implements org.apache.spark.scheduler.SparkListener {
    public   SaveStageAndTaskInfo () { throw new RuntimeException(); }
    public  scala.collection.mutable.Map<org.apache.spark.scheduler.StageInfo, scala.collection.Seq<scala.Tuple2<org.apache.spark.scheduler.TaskInfo, org.apache.spark.executor.TaskMetrics>>> stageInfos () { throw new RuntimeException(); }
    public  scala.collection.mutable.Buffer<scala.Tuple2<org.apache.spark.scheduler.TaskInfo, org.apache.spark.executor.TaskMetrics>> taskInfoMetrics () { throw new RuntimeException(); }
    public  void onTaskEnd (org.apache.spark.scheduler.SparkListenerTaskEnd task) { throw new RuntimeException(); }
    public  void onStageCompleted (org.apache.spark.scheduler.SparkListenerStageCompleted stage) { throw new RuntimeException(); }
  }
  /**
   * A simple listener that saves the task indices for all task events.
   */
  private  class SaveTaskEvents implements org.apache.spark.scheduler.SparkListener {
    public   SaveTaskEvents () { throw new RuntimeException(); }
    public  scala.collection.mutable.HashSet<java.lang.Object> startedTasks () { throw new RuntimeException(); }
    public  scala.collection.mutable.HashSet<java.lang.Object> startedGettingResultTasks () { throw new RuntimeException(); }
    public  scala.collection.mutable.HashSet<java.lang.Object> endedTasks () { throw new RuntimeException(); }
    public  void onTaskStart (org.apache.spark.scheduler.SparkListenerTaskStart taskStart) { throw new RuntimeException(); }
    public  void onTaskEnd (org.apache.spark.scheduler.SparkListenerTaskEnd taskEnd) { throw new RuntimeException(); }
    public  void onTaskGettingResult (org.apache.spark.scheduler.SparkListenerTaskGettingResult taskGettingResult) { throw new RuntimeException(); }
  }
  /**
   * A simple listener that throws an exception on job end.
   */
  private  class BadListener implements org.apache.spark.scheduler.SparkListener {
    public   BadListener () { throw new RuntimeException(); }
    public  scala.Nothing onJobEnd (org.apache.spark.scheduler.SparkListenerJobEnd jobEnd) { throw new RuntimeException(); }
  }
}
