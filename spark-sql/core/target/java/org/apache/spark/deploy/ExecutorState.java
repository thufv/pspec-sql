package org.apache.spark.deploy;
// no position
private  class ExecutorState extends scala.Enumeration {
  static public  scala.Enumeration.Value LAUNCHING () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value LOADING () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value RUNNING () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value KILLED () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value FAILED () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value LOST () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value EXITED () { throw new RuntimeException(); }
  static public  boolean isFinished (scala.Enumeration.Value state) { throw new RuntimeException(); }
}
