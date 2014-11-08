package org.apache.spark;
public  class FailureSuite extends org.scalatest.FunSuite implements org.apache.spark.LocalSparkContext {
  public   FailureSuite () { throw new RuntimeException(); }
}
