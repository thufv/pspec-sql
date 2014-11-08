package org.apache.spark.serializer;
/**
 * :: DeveloperApi ::
 * A stream for writing serialized objects.
 */
public abstract class SerializationStream {
  public   SerializationStream () { throw new RuntimeException(); }
  public abstract <T extends java.lang.Object> org.apache.spark.serializer.SerializationStream writeObject (T t, scala.reflect.ClassTag<T> evidence$4) ;
  public abstract  void flush () ;
  public abstract  void close () ;
  public <T extends java.lang.Object> org.apache.spark.serializer.SerializationStream writeAll (scala.collection.Iterator<T> iter, scala.reflect.ClassTag<T> evidence$5) { throw new RuntimeException(); }
}
