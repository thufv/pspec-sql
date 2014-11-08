package org.apache.spark.deploy.master;
// no position
private  class RecoveryState extends scala.Enumeration {
  static public  scala.Enumeration.Value STANDBY () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value ALIVE () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value RECOVERING () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value COMPLETING_RECOVERY () { throw new RuntimeException(); }
}
