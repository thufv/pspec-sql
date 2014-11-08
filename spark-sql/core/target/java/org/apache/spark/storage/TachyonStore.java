package org.apache.spark.storage;
/**
 * Stores BlockManager blocks on Tachyon.
 */
private  class TachyonStore extends org.apache.spark.storage.BlockStore implements org.apache.spark.Logging {
  public   TachyonStore (org.apache.spark.storage.BlockManager blockManager, org.apache.spark.storage.TachyonBlockManager tachyonManager) { throw new RuntimeException(); }
  public  long getSize (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
  public  org.apache.spark.storage.PutResult putBytes (org.apache.spark.storage.BlockId blockId, java.nio.ByteBuffer bytes, org.apache.spark.storage.StorageLevel level) { throw new RuntimeException(); }
  public  org.apache.spark.storage.PutResult putArray (org.apache.spark.storage.BlockId blockId, java.lang.Object[] values, org.apache.spark.storage.StorageLevel level, boolean returnValues) { throw new RuntimeException(); }
  public  org.apache.spark.storage.PutResult putIterator (org.apache.spark.storage.BlockId blockId, scala.collection.Iterator<java.lang.Object> values, org.apache.spark.storage.StorageLevel level, boolean returnValues) { throw new RuntimeException(); }
  private  org.apache.spark.storage.PutResult putIntoTachyonStore (org.apache.spark.storage.BlockId blockId, java.nio.ByteBuffer bytes, boolean returnValues) { throw new RuntimeException(); }
  public  boolean remove (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
  public  scala.Option<scala.collection.Iterator<java.lang.Object>> getValues (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
  public  scala.Option<java.nio.ByteBuffer> getBytes (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
  public  boolean contains (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
}
