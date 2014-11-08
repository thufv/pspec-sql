package org.apache.spark.deploy.master;
// no position
private  class ApplicationState extends scala.Enumeration {
  static public  scala.Enumeration.Value WAITING () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value RUNNING () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value FINISHED () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value FAILED () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value UNKNOWN () { throw new RuntimeException(); }
  static public  int MAX_NUM_RETRY () { throw new RuntimeException(); }
}
