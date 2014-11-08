package org.apache.spark;
/**
 * A class encapsulating how to convert some type T to Writable. It stores both the Writable class
 * corresponding to T (e.g. IntWritable for Int) and a function for doing the conversion.
 * The getter for the writable class takes a ClassTag[T] in case this is a generic object
 * that doesn't know the type of T when it is created. This sounds strange but is necessary to
 * support converting subclasses of Writable to themselves (writableWritableConverter).
 */
private  class WritableConverter<T extends java.lang.Object> implements java.io.Serializable {
  public  scala.Function1<scala.reflect.ClassTag<T>, java.lang.Class<? extends org.apache.hadoop.io.Writable>> writableClass () { throw new RuntimeException(); }
  public  scala.Function1<org.apache.hadoop.io.Writable, T> convert () { throw new RuntimeException(); }
  // not preceding
  public   WritableConverter (scala.Function1<scala.reflect.ClassTag<T>, java.lang.Class<? extends org.apache.hadoop.io.Writable>> writableClass, scala.Function1<org.apache.hadoop.io.Writable, T> convert) { throw new RuntimeException(); }
}
