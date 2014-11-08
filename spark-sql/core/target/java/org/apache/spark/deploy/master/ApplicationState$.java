package org.apache.spark.deploy.master;
// no position
private  class ApplicationState$ extends scala.Enumeration {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final ApplicationState$ MODULE$ = null;
  public   ApplicationState$ () { throw new RuntimeException(); }
  public  scala.Enumeration.Value WAITING () { throw new RuntimeException(); }
  public  scala.Enumeration.Value RUNNING () { throw new RuntimeException(); }
  public  scala.Enumeration.Value FINISHED () { throw new RuntimeException(); }
  public  scala.Enumeration.Value FAILED () { throw new RuntimeException(); }
  public  scala.Enumeration.Value UNKNOWN () { throw new RuntimeException(); }
  public  int MAX_NUM_RETRY () { throw new RuntimeException(); }
}
