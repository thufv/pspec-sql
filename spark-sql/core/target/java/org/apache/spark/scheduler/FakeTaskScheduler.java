package org.apache.spark.scheduler;
/**
 * A mock TaskSchedulerImpl implementation that just remembers information about tasks started and
 * feedback received from the TaskSetManagers. Note that it's important to initialize this with
 * a list of "live" executors and their hostnames for isExecutorAlive and hasExecutorsAliveOnHost
 * to work, and these are required for locality in TaskSetManager.
 */
public  class FakeTaskScheduler extends org.apache.spark.scheduler.TaskSchedulerImpl {
  public   FakeTaskScheduler (org.apache.spark.SparkContext sc, scala.collection.Seq<scala.Tuple2<java.lang.String, java.lang.String>> liveExecutors) { throw new RuntimeException(); }
  public  scala.collection.mutable.ArrayBuffer<java.lang.Object> startedTasks () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<java.lang.Object, org.apache.spark.TaskEndReason> endedTasks () { throw new RuntimeException(); }
  public  scala.collection.mutable.ArrayBuffer<org.apache.spark.scheduler.TaskSetManager> finishedManagers () { throw new RuntimeException(); }
  public  scala.collection.mutable.ArrayBuffer<java.lang.String> taskSetsFailed () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<java.lang.String, java.lang.String> executors () { throw new RuntimeException(); }
  public  void removeExecutor (java.lang.String execId) { throw new RuntimeException(); }
  public  void taskSetFinished (org.apache.spark.scheduler.TaskSetManager manager) { throw new RuntimeException(); }
  public  boolean isExecutorAlive (java.lang.String execId) { throw new RuntimeException(); }
  public  boolean hasExecutorsAliveOnHost (java.lang.String host) { throw new RuntimeException(); }
  public  boolean hasHostAliveOnRack (java.lang.String rack) { throw new RuntimeException(); }
  public  void addExecutor (java.lang.String execId, java.lang.String host) { throw new RuntimeException(); }
  public  scala.Option<java.lang.String> getRackForHost (java.lang.String value) { throw new RuntimeException(); }
}
