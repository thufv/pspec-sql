package org.apache.spark.ui.jobs;
// no position
private  class UIData {
  /**
   * These are kept mutable and reused throughout a task's lifetime to avoid excessive reallocation.
   */
  static public  class TaskUIData implements scala.Product, scala.Serializable {
    public  org.apache.spark.scheduler.TaskInfo taskInfo () { throw new RuntimeException(); }
    public  scala.Option<org.apache.spark.executor.TaskMetrics> taskMetrics () { throw new RuntimeException(); }
    public  scala.Option<java.lang.String> errorMessage () { throw new RuntimeException(); }
    // not preceding
    public   TaskUIData (org.apache.spark.scheduler.TaskInfo taskInfo, scala.Option<org.apache.spark.executor.TaskMetrics> taskMetrics, scala.Option<java.lang.String> errorMessage) { throw new RuntimeException(); }
  }
  // no position
  static public  class TaskUIData$ extends scala.runtime.AbstractFunction3<org.apache.spark.scheduler.TaskInfo, scala.Option<org.apache.spark.executor.TaskMetrics>, scala.Option<java.lang.String>, org.apache.spark.ui.jobs.UIData.TaskUIData> implements scala.Serializable {
    public   TaskUIData$ () { throw new RuntimeException(); }
  }
  static public  class ExecutorSummary {
    public   ExecutorSummary () { throw new RuntimeException(); }
    public  long taskTime () { throw new RuntimeException(); }
    public  int failedTasks () { throw new RuntimeException(); }
    public  int succeededTasks () { throw new RuntimeException(); }
    public  long inputBytes () { throw new RuntimeException(); }
    public  long shuffleRead () { throw new RuntimeException(); }
    public  long shuffleWrite () { throw new RuntimeException(); }
    public  long memoryBytesSpilled () { throw new RuntimeException(); }
    public  long diskBytesSpilled () { throw new RuntimeException(); }
  }
  static public  class StageUIData {
    public   StageUIData () { throw new RuntimeException(); }
    public  int numActiveTasks () { throw new RuntimeException(); }
    public  int numCompleteTasks () { throw new RuntimeException(); }
    public  org.apache.spark.util.collection.OpenHashSet<java.lang.Object> completedIndices () { throw new RuntimeException(); }
    public  int numFailedTasks () { throw new RuntimeException(); }
    public  long executorRunTime () { throw new RuntimeException(); }
    public  long inputBytes () { throw new RuntimeException(); }
    public  long shuffleReadBytes () { throw new RuntimeException(); }
    public  long shuffleWriteBytes () { throw new RuntimeException(); }
    public  long memoryBytesSpilled () { throw new RuntimeException(); }
    public  long diskBytesSpilled () { throw new RuntimeException(); }
    public  java.lang.String schedulingPool () { throw new RuntimeException(); }
    public  scala.Option<java.lang.String> description () { throw new RuntimeException(); }
    public  scala.collection.mutable.HashMap<java.lang.Object, org.apache.spark.scheduler.AccumulableInfo> accumulables () { throw new RuntimeException(); }
    public  scala.collection.mutable.HashMap<java.lang.Object, org.apache.spark.ui.jobs.UIData.TaskUIData> taskData () { throw new RuntimeException(); }
    public  scala.collection.mutable.HashMap<java.lang.String, org.apache.spark.ui.jobs.UIData.ExecutorSummary> executorSummary () { throw new RuntimeException(); }
  }
}
