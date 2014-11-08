package org.apache.spark.scheduler;
/** A TaskResult that contains the task's return value and accumulator updates. */
private  class DirectTaskResult<T extends java.lang.Object> implements org.apache.spark.scheduler.TaskResult<T>, java.io.Externalizable {
  public  java.nio.ByteBuffer valueBytes () { throw new RuntimeException(); }
  public  scala.collection.mutable.Map<java.lang.Object, java.lang.Object> accumUpdates () { throw new RuntimeException(); }
  public  org.apache.spark.executor.TaskMetrics metrics () { throw new RuntimeException(); }
  // not preceding
  public   DirectTaskResult (java.nio.ByteBuffer valueBytes, scala.collection.mutable.Map<java.lang.Object, java.lang.Object> accumUpdates, org.apache.spark.executor.TaskMetrics metrics) { throw new RuntimeException(); }
  public   DirectTaskResult () { throw new RuntimeException(); }
  public  void writeExternal (java.io.ObjectOutput out) { throw new RuntimeException(); }
  public  void readExternal (java.io.ObjectInput in) { throw new RuntimeException(); }
  public  T value () { throw new RuntimeException(); }
}
