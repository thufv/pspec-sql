package org.apache.spark.scheduler;
/**
 * A location where a task should run. This can either be a host or a (host, executorID) pair.
 * In the latter case, we will prefer to launch the task on that executorID, but our next level
 * of preference will be executors on the same host if this is not possible.
 */
private  class TaskLocation implements scala.Serializable {
  static public  org.apache.spark.scheduler.TaskLocation apply (java.lang.String host, java.lang.String executorId) { throw new RuntimeException(); }
  static public  org.apache.spark.scheduler.TaskLocation apply (java.lang.String host) { throw new RuntimeException(); }
  public  java.lang.String host () { throw new RuntimeException(); }
  public  scala.Option<java.lang.String> executorId () { throw new RuntimeException(); }
  // not preceding
  private   TaskLocation (java.lang.String host, scala.Option<java.lang.String> executorId) { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
