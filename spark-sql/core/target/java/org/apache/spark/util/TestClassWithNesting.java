package org.apache.spark.util;
public  class TestClassWithNesting implements scala.Serializable {
  public  int y () { throw new RuntimeException(); }
  // not preceding
  public   TestClassWithNesting (int y) { throw new RuntimeException(); }
  public  int getY () { throw new RuntimeException(); }
  public  int run () { throw new RuntimeException(); }
}
