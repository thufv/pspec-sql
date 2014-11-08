package org.apache.spark.util;
public  class DummyClass4 {
  public  org.apache.spark.util.DummyClass3 d () { throw new RuntimeException(); }
  // not preceding
  public   DummyClass4 (org.apache.spark.util.DummyClass3 d) { throw new RuntimeException(); }
  public  int x () { throw new RuntimeException(); }
}
