package org.apache.spark.api.python;
/**
 * A class to test Pyrolite serialization on the Scala side, that will be deserialized
 * in Python
 * @param str
 * @param int
 * @param double
 */
public  class TestWritable implements org.apache.hadoop.io.Writable, scala.Product, scala.Serializable {
  public  java.lang.String str () { throw new RuntimeException(); }
  public  int int () { throw new RuntimeException(); }
  public  double double () { throw new RuntimeException(); }
  // not preceding
  public   TestWritable (java.lang.String str, int int, double double) { throw new RuntimeException(); }
  public   TestWritable () { throw new RuntimeException(); }
  public  java.lang.String getStr () { throw new RuntimeException(); }
  public  void setStr (java.lang.String str) { throw new RuntimeException(); }
  public  int getInt () { throw new RuntimeException(); }
  public  void setInt (int int) { throw new RuntimeException(); }
  public  double getDouble () { throw new RuntimeException(); }
  public  void setDouble (double double) { throw new RuntimeException(); }
  public  void write (java.io.DataOutput out) { throw new RuntimeException(); }
  public  void readFields (java.io.DataInput in) { throw new RuntimeException(); }
}
