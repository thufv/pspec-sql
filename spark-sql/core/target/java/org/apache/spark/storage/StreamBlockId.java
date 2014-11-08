package org.apache.spark.storage;
public  class StreamBlockId extends org.apache.spark.storage.BlockId implements scala.Product, scala.Serializable {
  public  int streamId () { throw new RuntimeException(); }
  public  long uniqueId () { throw new RuntimeException(); }
  // not preceding
  public   StreamBlockId (int streamId, long uniqueId) { throw new RuntimeException(); }
  public  java.lang.String name () { throw new RuntimeException(); }
}
