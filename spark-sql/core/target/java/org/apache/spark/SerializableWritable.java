package org.apache.spark;
public  class SerializableWritable<T extends org.apache.hadoop.io.Writable> implements java.io.Serializable {
  public  T t () { throw new RuntimeException(); }
  // not preceding
  public   SerializableWritable (T t) { throw new RuntimeException(); }
  public  T value () { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
  private  void writeObject (java.io.ObjectOutputStream out) { throw new RuntimeException(); }
  private  void readObject (java.io.ObjectInputStream in) { throw new RuntimeException(); }
}
