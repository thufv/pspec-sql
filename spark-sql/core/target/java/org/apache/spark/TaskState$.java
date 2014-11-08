package org.apache.spark;
// no position
private  class TaskState$ extends scala.Enumeration {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final TaskState$ MODULE$ = null;
  public   TaskState$ () { throw new RuntimeException(); }
  public  scala.Enumeration.Value LAUNCHING () { throw new RuntimeException(); }
  public  scala.Enumeration.Value RUNNING () { throw new RuntimeException(); }
  public  scala.Enumeration.Value FINISHED () { throw new RuntimeException(); }
  public  scala.Enumeration.Value FAILED () { throw new RuntimeException(); }
  public  scala.Enumeration.Value KILLED () { throw new RuntimeException(); }
  public  scala.Enumeration.Value LOST () { throw new RuntimeException(); }
  public  scala.collection.immutable.Set<scala.Enumeration.Value> FINISHED_STATES () { throw new RuntimeException(); }
  public  boolean isFinished (scala.Enumeration.Value state) { throw new RuntimeException(); }
  public  org.apache.mesos.Protos.TaskState toMesos (scala.Enumeration.Value state) { throw new RuntimeException(); }
  public  scala.Enumeration.Value fromMesos (org.apache.mesos.Protos.TaskState mesosState) { throw new RuntimeException(); }
}
