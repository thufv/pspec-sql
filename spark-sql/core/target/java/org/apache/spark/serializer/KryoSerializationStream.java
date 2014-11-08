package org.apache.spark.serializer;
private  class KryoSerializationStream extends org.apache.spark.serializer.SerializationStream {
  public   KryoSerializationStream (com.esotericsoftware.kryo.Kryo kryo, java.io.OutputStream outStream) { throw new RuntimeException(); }
  public  com.esotericsoftware.kryo.io.Output output () { throw new RuntimeException(); }
  public <T extends java.lang.Object> org.apache.spark.serializer.SerializationStream writeObject (T t, scala.reflect.ClassTag<T> evidence$1) { throw new RuntimeException(); }
  public  void flush () { throw new RuntimeException(); }
  public  void close () { throw new RuntimeException(); }
}
