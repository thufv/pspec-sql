package org.apache.spark.storage;
/**
 * Abstract class to store blocks.
 */
private abstract class BlockStore implements org.apache.spark.Logging {
  public  org.apache.spark.storage.BlockManager blockManager () { throw new RuntimeException(); }
  // not preceding
  public   BlockStore (org.apache.spark.storage.BlockManager blockManager) { throw new RuntimeException(); }
  public abstract  org.apache.spark.storage.PutResult putBytes (org.apache.spark.storage.BlockId blockId, java.nio.ByteBuffer bytes, org.apache.spark.storage.StorageLevel level) ;
  /**
   * Put in a block and, possibly, also return its content as either bytes or another Iterator.
   * This is used to efficiently write the values to multiple locations (e.g. for replication).
   * <p>
   * @return a PutResult that contains the size of the data, as well as the values put if
   *         returnValues is true (if not, the result's data field can be null)
   */
  public abstract  org.apache.spark.storage.PutResult putIterator (org.apache.spark.storage.BlockId blockId, scala.collection.Iterator<java.lang.Object> values, org.apache.spark.storage.StorageLevel level, boolean returnValues) ;
  public abstract  org.apache.spark.storage.PutResult putArray (org.apache.spark.storage.BlockId blockId, java.lang.Object[] values, org.apache.spark.storage.StorageLevel level, boolean returnValues) ;
  /**
   * Return the size of a block in bytes.
   */
  public abstract  long getSize (org.apache.spark.storage.BlockId blockId) ;
  public abstract  scala.Option<java.nio.ByteBuffer> getBytes (org.apache.spark.storage.BlockId blockId) ;
  public abstract  scala.Option<scala.collection.Iterator<java.lang.Object>> getValues (org.apache.spark.storage.BlockId blockId) ;
  /**
   * Remove a block, if it exists.
   * @param blockId the block to remove.
   * @return True if the block was found and removed, False otherwise.
   */
  public abstract  boolean remove (org.apache.spark.storage.BlockId blockId) ;
  public abstract  boolean contains (org.apache.spark.storage.BlockId blockId) ;
  public  void clear () { throw new RuntimeException(); }
}
