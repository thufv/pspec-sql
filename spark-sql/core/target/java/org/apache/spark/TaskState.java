package org.apache.spark;
// no position
private  class TaskState extends scala.Enumeration {
  static public  scala.Enumeration.Value LAUNCHING () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value RUNNING () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value FINISHED () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value FAILED () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value KILLED () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value LOST () { throw new RuntimeException(); }
  static public  scala.collection.immutable.Set<scala.Enumeration.Value> FINISHED_STATES () { throw new RuntimeException(); }
  static public  boolean isFinished (scala.Enumeration.Value state) { throw new RuntimeException(); }
  static public  org.apache.mesos.Protos.TaskState toMesos (scala.Enumeration.Value state) { throw new RuntimeException(); }
  static public  scala.Enumeration.Value fromMesos (org.apache.mesos.Protos.TaskState mesosState) { throw new RuntimeException(); }
}
