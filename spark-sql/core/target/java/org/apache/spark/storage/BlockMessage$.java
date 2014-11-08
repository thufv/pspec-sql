package org.apache.spark.storage;
// no position
private  class BlockMessage$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final BlockMessage$ MODULE$ = null;
  public   BlockMessage$ () { throw new RuntimeException(); }
  public  int TYPE_NON_INITIALIZED () { throw new RuntimeException(); }
  public  int TYPE_GET_BLOCK () { throw new RuntimeException(); }
  public  int TYPE_GOT_BLOCK () { throw new RuntimeException(); }
  public  int TYPE_PUT_BLOCK () { throw new RuntimeException(); }
  public  org.apache.spark.storage.BlockMessage fromBufferMessage (org.apache.spark.network.BufferMessage bufferMessage) { throw new RuntimeException(); }
  public  org.apache.spark.storage.BlockMessage fromByteBuffer (java.nio.ByteBuffer buffer) { throw new RuntimeException(); }
  public  org.apache.spark.storage.BlockMessage fromGetBlock (org.apache.spark.storage.GetBlock getBlock) { throw new RuntimeException(); }
  public  org.apache.spark.storage.BlockMessage fromGotBlock (org.apache.spark.storage.GotBlock gotBlock) { throw new RuntimeException(); }
  public  org.apache.spark.storage.BlockMessage fromPutBlock (org.apache.spark.storage.PutBlock putBlock) { throw new RuntimeException(); }
  public  void main (java.lang.String[] args) { throw new RuntimeException(); }
}
