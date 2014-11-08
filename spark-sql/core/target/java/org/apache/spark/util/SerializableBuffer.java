package org.apache.spark.util;
/**
 * A wrapper around a java.nio.ByteBuffer that is serializable through Java serialization, to make
 * it easier to pass ByteBuffers in case class messages.
 */
private  class SerializableBuffer implements scala.Serializable {
  public  java.nio.ByteBuffer buffer () { throw new RuntimeException(); }
  // not preceding
  public   SerializableBuffer (java.nio.ByteBuffer buffer) { throw new RuntimeException(); }
  public  java.nio.ByteBuffer value () { throw new RuntimeException(); }
  private  void readObject (java.io.ObjectInputStream in) { throw new RuntimeException(); }
  private  void writeObject (java.io.ObjectOutputStream out) { throw new RuntimeException(); }
}
