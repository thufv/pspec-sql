package org.apache.spark.serializer;
private  class KryoSerializerInstance extends org.apache.spark.serializer.SerializerInstance {
  public   KryoSerializerInstance (org.apache.spark.serializer.KryoSerializer ks) { throw new RuntimeException(); }
  private  com.esotericsoftware.kryo.Kryo kryo () { throw new RuntimeException(); }
  private  com.esotericsoftware.kryo.io.Output output () { throw new RuntimeException(); }
  private  com.esotericsoftware.kryo.io.Input input () { throw new RuntimeException(); }
  public <T extends java.lang.Object> java.nio.ByteBuffer serialize (T t, scala.reflect.ClassTag<T> evidence$3) { throw new RuntimeException(); }
  public <T extends java.lang.Object> T deserialize (java.nio.ByteBuffer bytes, scala.reflect.ClassTag<T> evidence$4) { throw new RuntimeException(); }
  public <T extends java.lang.Object> T deserialize (java.nio.ByteBuffer bytes, java.lang.ClassLoader loader, scala.reflect.ClassTag<T> evidence$5) { throw new RuntimeException(); }
  public  org.apache.spark.serializer.SerializationStream serializeStream (java.io.OutputStream s) { throw new RuntimeException(); }
  public  org.apache.spark.serializer.DeserializationStream deserializeStream (java.io.InputStream s) { throw new RuntimeException(); }
}
