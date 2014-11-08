package org.apache.spark.util;
private  class TimeStampedValue<V extends java.lang.Object> implements scala.Product, scala.Serializable {
  public  V value () { throw new RuntimeException(); }
  public  long timestamp () { throw new RuntimeException(); }
  // not preceding
  public   TimeStampedValue (V value, long timestamp) { throw new RuntimeException(); }
}
