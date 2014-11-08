package org.apache.spark.deploy;
private  class TestMasterInfo implements org.apache.spark.Logging {
  public  java.lang.String ip () { throw new RuntimeException(); }
  public  org.apache.spark.deploy.DockerId dockerId () { throw new RuntimeException(); }
  public  java.io.File logFile () { throw new RuntimeException(); }
  // not preceding
  public   TestMasterInfo (java.lang.String ip, org.apache.spark.deploy.DockerId dockerId, java.io.File logFile) { throw new RuntimeException(); }
  public  org.json4s.DefaultFormats$ formats () { throw new RuntimeException(); }
  public  scala.Enumeration.Value state () { throw new RuntimeException(); }
  public  scala.collection.immutable.List<java.lang.String> liveWorkerIPs () { throw new RuntimeException(); }
  public  int numLiveApps () { throw new RuntimeException(); }
  public  void readState () { throw new RuntimeException(); }
  public  void kill () { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
