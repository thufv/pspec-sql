package org.apache.spark.deploy.master;
// no position
private  class DriverState extends scala.Enumeration {
  static public  scala.Enumeration.Value SUBMITTED () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value RUNNING () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value FINISHED () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value RELAUNCHING () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value UNKNOWN () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value KILLED () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value FAILED () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value ERROR () { throw new RuntimeException(); }
}
