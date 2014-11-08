package org.apache.spark.scheduler;
public  class FakeTaskSetManager extends org.apache.spark.scheduler.TaskSetManager {
  public   FakeTaskSetManager (int initPriority, int initStageId, int initNumTasks, org.apache.spark.scheduler.TaskSchedulerImpl taskScheduler, org.apache.spark.scheduler.TaskSet taskSet) { throw new RuntimeException(); }
  public  int numTasks () { throw new RuntimeException(); }
  public  int numRunningTasks () { throw new RuntimeException(); }
  public  int runningTasks () { throw new RuntimeException(); }
  public  void increaseRunningTasks (int taskNum) { throw new RuntimeException(); }
  public  void decreaseRunningTasks (int taskNum) { throw new RuntimeException(); }
  public  void addSchedulable (org.apache.spark.scheduler.Schedulable schedulable) { throw new RuntimeException(); }
  public  void removeSchedulable (org.apache.spark.scheduler.Schedulable schedulable) { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.Schedulable getSchedulableByName (java.lang.String name) { throw new RuntimeException(); }
  public  void executorLost (java.lang.String executorId, java.lang.String host) { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.scheduler.TaskDescription> resourceOffer (java.lang.String execId, java.lang.String host, scala.Enumeration.Value maxLocality) { throw new RuntimeException(); }
  public  boolean checkSpeculatableTasks () { throw new RuntimeException(); }
  public  void taskFinished () { throw new RuntimeException(); }
  public  void abort () { throw new RuntimeException(); }
}
