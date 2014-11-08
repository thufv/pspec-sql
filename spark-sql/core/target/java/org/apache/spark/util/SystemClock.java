package org.apache.spark.util;
// no position
private  class SystemClock implements org.apache.spark.util.Clock {
  static public  long getTime () { throw new RuntimeException(); }
}
