package org.apache.spark;
public  class NotSerializableExn extends java.lang.Throwable {
  public  org.apache.spark.NotSerializableClass notSer () { throw new RuntimeException(); }
  // not preceding
  public   NotSerializableExn (org.apache.spark.NotSerializableClass notSer) { throw new RuntimeException(); }
}
