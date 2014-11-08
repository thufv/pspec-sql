package org.apache.spark.scheduler;
// no position
public  class TaskLocality extends scala.Enumeration {
  static public  scala.Enumeration.Value PROCESS_LOCAL () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value NODE_LOCAL () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value NO_PREF () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value RACK_LOCAL () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value ANY () { throw new RuntimeException(); }
  static public  boolean isAllowed (scala.Enumeration.Value constraint, scala.Enumeration.Value condition) { throw new RuntimeException(); }
}
