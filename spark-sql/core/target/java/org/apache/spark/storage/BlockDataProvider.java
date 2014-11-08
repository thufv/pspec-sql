package org.apache.spark.storage;
/**
 * An interface for providing data for blocks.
 * <p>
 * getBlockData returns either a FileSegment (for zero-copy send), or a ByteBuffer.
 * <p>
 * Aside from unit tests, {@link BlockManager} is the main class that implements this.
 */
private  interface BlockDataProvider {
  public abstract  scala.util.Either<org.apache.spark.storage.FileSegment, java.nio.ByteBuffer> getBlockData (java.lang.String blockId) ;
}
