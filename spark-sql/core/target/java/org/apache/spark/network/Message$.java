package org.apache.spark.network;
// no position
private  class Message$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final Message$ MODULE$ = null;
  public   Message$ () { throw new RuntimeException(); }
  public  long BUFFER_MESSAGE () { throw new RuntimeException(); }
  public  int lastId () { throw new RuntimeException(); }
  public  int getNewId () { throw new RuntimeException(); }
  public  org.apache.spark.network.BufferMessage createBufferMessage (scala.collection.Seq<java.nio.ByteBuffer> dataBuffers, int ackId) { throw new RuntimeException(); }
  public  org.apache.spark.network.BufferMessage createBufferMessage (scala.collection.Seq<java.nio.ByteBuffer> dataBuffers) { throw new RuntimeException(); }
  public  org.apache.spark.network.BufferMessage createBufferMessage (java.nio.ByteBuffer dataBuffer, int ackId) { throw new RuntimeException(); }
  public  org.apache.spark.network.BufferMessage createBufferMessage (java.nio.ByteBuffer dataBuffer) { throw new RuntimeException(); }
  public  org.apache.spark.network.BufferMessage createBufferMessage (int ackId) { throw new RuntimeException(); }
  public  org.apache.spark.network.Message create (org.apache.spark.network.MessageChunkHeader header) { throw new RuntimeException(); }
}
