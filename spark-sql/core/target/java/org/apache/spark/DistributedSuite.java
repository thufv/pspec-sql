package org.apache.spark;
public  class DistributedSuite extends org.scalatest.FunSuite implements org.scalatest.Matchers, org.scalatest.BeforeAndAfter, org.apache.spark.LocalSparkContext {
  static public  boolean mark () { throw new RuntimeException(); }
  static public  boolean amMaster () { throw new RuntimeException(); }
  static public  boolean markNodeIfIdentity (boolean item) { throw new RuntimeException(); }
  static public  boolean failOnMarkedIdentity (boolean item) { throw new RuntimeException(); }
  public   DistributedSuite () { throw new RuntimeException(); }
  public  java.lang.String clusterUrl () { throw new RuntimeException(); }
}
