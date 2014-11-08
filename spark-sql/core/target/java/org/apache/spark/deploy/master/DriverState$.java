package org.apache.spark.deploy.master;
// no position
private  class DriverState$ extends scala.Enumeration {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final DriverState$ MODULE$ = null;
  public   DriverState$ () { throw new RuntimeException(); }
  public  scala.Enumeration.Value SUBMITTED () { throw new RuntimeException(); }
  public  scala.Enumeration.Value RUNNING () { throw new RuntimeException(); }
  public  scala.Enumeration.Value FINISHED () { throw new RuntimeException(); }
  public  scala.Enumeration.Value RELAUNCHING () { throw new RuntimeException(); }
  public  scala.Enumeration.Value UNKNOWN () { throw new RuntimeException(); }
  public  scala.Enumeration.Value KILLED () { throw new RuntimeException(); }
  public  scala.Enumeration.Value FAILED () { throw new RuntimeException(); }
  public  scala.Enumeration.Value ERROR () { throw new RuntimeException(); }
}
