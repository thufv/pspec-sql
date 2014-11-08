package org.apache.spark.serializer;
/**
 * :: DeveloperApi ::
 * A stream for reading serialized objects.
 */
public abstract class DeserializationStream {
  public   DeserializationStream () { throw new RuntimeException(); }
  public abstract <T extends java.lang.Object> T readObject (scala.reflect.ClassTag<T> evidence$6) ;
  public abstract  void close () ;
  /**
   * Read the elements of this stream through an iterator. This can only be called once, as
   * reading each element will consume data from the input source.
   */
  public  scala.collection.Iterator<java.lang.Object> asIterator () { throw new RuntimeException(); }
}
