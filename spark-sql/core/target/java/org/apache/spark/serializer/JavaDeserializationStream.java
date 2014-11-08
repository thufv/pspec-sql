package org.apache.spark.serializer;
private  class JavaDeserializationStream extends org.apache.spark.serializer.DeserializationStream {
  public   JavaDeserializationStream (java.io.InputStream in, java.lang.ClassLoader loader) { throw new RuntimeException(); }
  private  java.io.ObjectInputStream objIn () { throw new RuntimeException(); }
  public <T extends java.lang.Object> T readObject (scala.reflect.ClassTag<T> evidence$2) { throw new RuntimeException(); }
  public  void close () { throw new RuntimeException(); }
}
