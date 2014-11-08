package org.apache.spark.serializer;
/**
 * :: DeveloperApi ::
 * An instance of a serializer, for use by one thread at a time.
 */
public abstract class SerializerInstance {
  public   SerializerInstance () { throw new RuntimeException(); }
  public abstract <T extends java.lang.Object> java.nio.ByteBuffer serialize (T t, scala.reflect.ClassTag<T> evidence$1) ;
  public abstract <T extends java.lang.Object> T deserialize (java.nio.ByteBuffer bytes, scala.reflect.ClassTag<T> evidence$2) ;
  public abstract <T extends java.lang.Object> T deserialize (java.nio.ByteBuffer bytes, java.lang.ClassLoader loader, scala.reflect.ClassTag<T> evidence$3) ;
  public abstract  org.apache.spark.serializer.SerializationStream serializeStream (java.io.OutputStream s) ;
  public abstract  org.apache.spark.serializer.DeserializationStream deserializeStream (java.io.InputStream s) ;
}
