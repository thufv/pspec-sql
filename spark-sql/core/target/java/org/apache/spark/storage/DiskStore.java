package org.apache.spark.storage;
/**
 * Stores BlockManager blocks on disk.
 */
private  class DiskStore extends org.apache.spark.storage.BlockStore implements org.apache.spark.Logging {
  // not preceding
  public   DiskStore (org.apache.spark.storage.BlockManager blockManager, org.apache.spark.storage.DiskBlockManager diskManager) { throw new RuntimeException(); }
  public  long minMemoryMapBytes () { throw new RuntimeException(); }
  public  long getSize (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
  public  org.apache.spark.storage.PutResult putBytes (org.apache.spark.storage.BlockId blockId, java.nio.ByteBuffer _bytes, org.apache.spark.storage.StorageLevel level) { throw new RuntimeException(); }
  public  org.apache.spark.storage.PutResult putArray (org.apache.spark.storage.BlockId blockId, java.lang.Object[] values, org.apache.spark.storage.StorageLevel level, boolean returnValues) { throw new RuntimeException(); }
  public  org.apache.spark.storage.PutResult putIterator (org.apache.spark.storage.BlockId blockId, scala.collection.Iterator<java.lang.Object> values, org.apache.spark.storage.StorageLevel level, boolean returnValues) { throw new RuntimeException(); }
  public  scala.Option<java.nio.ByteBuffer> getBytes (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
  public  scala.Option<scala.collection.Iterator<java.lang.Object>> getValues (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
  /**
   * A version of getValues that allows a custom serializer. This is used as part of the
   * shuffle short-circuit code.
   */
  public  scala.Option<scala.collection.Iterator<java.lang.Object>> getValues (org.apache.spark.storage.BlockId blockId, org.apache.spark.serializer.Serializer serializer) { throw new RuntimeException(); }
  public  boolean remove (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
  public  boolean contains (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
}
