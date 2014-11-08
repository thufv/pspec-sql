package org.apache.spark.scheduler;
/**
 * An interface for schedulable entities.
 * there are two type of Schedulable entities(Pools and TaskSetManagers)
 */
private  interface Schedulable {
  public abstract  org.apache.spark.scheduler.Pool parent () ;
  public abstract  java.util.concurrent.ConcurrentLinkedQueue<org.apache.spark.scheduler.Schedulable> schedulableQueue () ;
  public abstract  scala.Enumeration.Value schedulingMode () ;
  public abstract  int weight () ;
  public abstract  int minShare () ;
  public abstract  int runningTasks () ;
  public abstract  int priority () ;
  public abstract  int stageId () ;
  public abstract  java.lang.String name () ;
  public abstract  void addSchedulable (org.apache.spark.scheduler.Schedulable schedulable) ;
  public abstract  void removeSchedulable (org.apache.spark.scheduler.Schedulable schedulable) ;
  public abstract  org.apache.spark.scheduler.Schedulable getSchedulableByName (java.lang.String name) ;
  public abstract  void executorLost (java.lang.String executorId, java.lang.String host) ;
  public abstract  boolean checkSpeculatableTasks () ;
  public abstract  scala.collection.mutable.ArrayBuffer<org.apache.spark.scheduler.TaskSetManager> getSortedTaskSetQueue () ;
}
