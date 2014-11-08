package org.apache.spark.ui.exec;
/** Summary information about an executor to display in the UI. */
private  class ExecutorSummaryInfo implements scala.Product, scala.Serializable {
  public  java.lang.String id () { throw new RuntimeException(); }
  public  java.lang.String hostPort () { throw new RuntimeException(); }
  public  int rddBlocks () { throw new RuntimeException(); }
  public  long memoryUsed () { throw new RuntimeException(); }
  public  long diskUsed () { throw new RuntimeException(); }
  public  int activeTasks () { throw new RuntimeException(); }
  public  int failedTasks () { throw new RuntimeException(); }
  public  int completedTasks () { throw new RuntimeException(); }
  public  int totalTasks () { throw new RuntimeException(); }
  public  long totalDuration () { throw new RuntimeException(); }
  public  long totalInputBytes () { throw new RuntimeException(); }
  public  long totalShuffleRead () { throw new RuntimeException(); }
  public  long totalShuffleWrite () { throw new RuntimeException(); }
  public  long maxMemory () { throw new RuntimeException(); }
  // not preceding
  public   ExecutorSummaryInfo (java.lang.String id, java.lang.String hostPort, int rddBlocks, long memoryUsed, long diskUsed, int activeTasks, int failedTasks, int completedTasks, int totalTasks, long totalDuration, long totalInputBytes, long totalShuffleRead, long totalShuffleWrite, long maxMemory) { throw new RuntimeException(); }
}
