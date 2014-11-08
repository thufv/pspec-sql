package org.apache.spark.network;
private  class BufferMessage extends org.apache.spark.network.Message {
  public  scala.collection.mutable.ArrayBuffer<java.nio.ByteBuffer> buffers () { throw new RuntimeException(); }
  public  int ackId () { throw new RuntimeException(); }
  // not preceding
  public   BufferMessage (int id_, scala.collection.mutable.ArrayBuffer<java.nio.ByteBuffer> buffers, int ackId) { throw new RuntimeException(); }
  public  int initialSize () { throw new RuntimeException(); }
  public  boolean gotChunkForSendingOnce () { throw new RuntimeException(); }
  public  int size () { throw new RuntimeException(); }
  public  int currentSize () { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.network.MessageChunk> getChunkForSending (int maxChunkSize) { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.network.MessageChunk> getChunkForReceiving (int chunkSize) { throw new RuntimeException(); }
  public  void flip () { throw new RuntimeException(); }
  public  boolean hasAckId () { throw new RuntimeException(); }
  public  boolean isCompletelyReceived () { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
