package org.apache.spark.util;
public  class FakeClock implements org.apache.spark.util.Clock {
  public   FakeClock () { throw new RuntimeException(); }
  private  long time () { throw new RuntimeException(); }
  public  void advance (long millis) { throw new RuntimeException(); }
  public  long getTime () { throw new RuntimeException(); }
}
