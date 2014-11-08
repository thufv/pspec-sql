package org.apache.spark.deploy.master;
// no position
private  class RecoveryState$ extends scala.Enumeration {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final RecoveryState$ MODULE$ = null;
  public   RecoveryState$ () { throw new RuntimeException(); }
  public  scala.Enumeration.Value STANDBY () { throw new RuntimeException(); }
  public  scala.Enumeration.Value ALIVE () { throw new RuntimeException(); }
  public  scala.Enumeration.Value RECOVERING () { throw new RuntimeException(); }
  public  scala.Enumeration.Value COMPLETING_RECOVERY () { throw new RuntimeException(); }
}
