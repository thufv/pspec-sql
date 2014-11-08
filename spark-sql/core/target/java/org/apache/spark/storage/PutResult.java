package org.apache.spark.storage;
/**
 * Result of adding a block into a BlockStore. This case class contains a few things:
 *   (1) The estimated size of the put,
 *   (2) The values put if the caller asked for them to be returned (e.g. for chaining
 *       replication), and
 *   (3) A list of blocks dropped as a result of this put. This is always empty for DiskStore.
 */
private  class PutResult implements scala.Product, scala.Serializable {
  public  long size () { throw new RuntimeException(); }
  public  scala.util.Either<scala.collection.Iterator<java.lang.Object>, java.nio.ByteBuffer> data () { throw new RuntimeException(); }
  public  scala.collection.Seq<scala.Tuple2<org.apache.spark.storage.BlockId, org.apache.spark.storage.BlockStatus>> droppedBlocks () { throw new RuntimeException(); }
  // not preceding
  public   PutResult (long size, scala.util.Either<scala.collection.Iterator<java.lang.Object>, java.nio.ByteBuffer> data, scala.collection.Seq<scala.Tuple2<org.apache.spark.storage.BlockId, org.apache.spark.storage.BlockStatus>> droppedBlocks) { throw new RuntimeException(); }
}
