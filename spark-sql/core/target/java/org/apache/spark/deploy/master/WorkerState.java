package org.apache.spark.deploy.master;
// no position
private  class WorkerState extends scala.Enumeration {
  static public  scala.Enumeration.Value ALIVE () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value DEAD () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value DECOMMISSIONED () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value UNKNOWN () { throw new RuntimeException(); }
}
