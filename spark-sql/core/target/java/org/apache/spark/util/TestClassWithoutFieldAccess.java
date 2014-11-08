package org.apache.spark.util;
public  class TestClassWithoutFieldAccess {
  public   TestClassWithoutFieldAccess () { throw new RuntimeException(); }
  public  org.apache.spark.util.NonSerializable nonSer () { throw new RuntimeException(); }
  public  int run () { throw new RuntimeException(); }
}
