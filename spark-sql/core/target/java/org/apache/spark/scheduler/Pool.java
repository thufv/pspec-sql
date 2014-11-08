package org.apache.spark.scheduler;
/**
 * An Schedulable entity that represent collection of Pools or TaskSetManagers
 */
private  class Pool implements org.apache.spark.scheduler.Schedulable, org.apache.spark.Logging {
  public  java.lang.String poolName () { throw new RuntimeException(); }
  public  scala.Enumeration.Value schedulingMode () { throw new RuntimeException(); }
  // not preceding
  public   Pool (java.lang.String poolName, scala.Enumeration.Value schedulingMode, int initMinShare, int initWeight) { throw new RuntimeException(); }
  public  java.util.concurrent.ConcurrentLinkedQueue<org.apache.spark.scheduler.Schedulable> schedulableQueue () { throw new RuntimeException(); }
  public  java.util.concurrent.ConcurrentHashMap<java.lang.String, org.apache.spark.scheduler.Schedulable> schedulableNameToSchedulable () { throw new RuntimeException(); }
  public  int weight () { throw new RuntimeException(); }
  public  int minShare () { throw new RuntimeException(); }
  public  int runningTasks () { throw new RuntimeException(); }
  public  int priority () { throw new RuntimeException(); }
  public  int stageId () { throw new RuntimeException(); }
  public  java.lang.String name () { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.Pool parent () { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.SchedulingAlgorithm taskSetSchedulingAlgorithm () { throw new RuntimeException(); }
  public  void addSchedulable (org.apache.spark.scheduler.Schedulable schedulable) { throw new RuntimeException(); }
  public  void removeSchedulable (org.apache.spark.scheduler.Schedulable schedulable) { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.Schedulable getSchedulableByName (java.lang.String schedulableName) { throw new RuntimeException(); }
  public  void executorLost (java.lang.String executorId, java.lang.String host) { throw new RuntimeException(); }
  public  boolean checkSpeculatableTasks () { throw new RuntimeException(); }
  public  scala.collection.mutable.ArrayBuffer<org.apache.spark.scheduler.TaskSetManager> getSortedTaskSetQueue () { throw new RuntimeException(); }
  public  void increaseRunningTasks (int taskNum) { throw new RuntimeException(); }
  public  void decreaseRunningTasks (int taskNum) { throw new RuntimeException(); }
}
