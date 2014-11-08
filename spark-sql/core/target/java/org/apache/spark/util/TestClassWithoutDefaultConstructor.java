package org.apache.spark.util;
public  class TestClassWithoutDefaultConstructor implements scala.Serializable {
  public   TestClassWithoutDefaultConstructor (int x) { throw new RuntimeException(); }
  public  int getX () { throw new RuntimeException(); }
  public  int run () { throw new RuntimeException(); }
}
