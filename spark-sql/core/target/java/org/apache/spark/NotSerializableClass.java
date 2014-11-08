package org.apache.spark;
public  class NotSerializableClass {
  public   NotSerializableClass () { throw new RuntimeException(); }
}
