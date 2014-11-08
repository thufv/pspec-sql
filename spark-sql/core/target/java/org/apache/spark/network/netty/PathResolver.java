package org.apache.spark.network.netty;
public  interface PathResolver {
  /** Get the file segment in which the given block resides. */
  public abstract  org.apache.spark.storage.FileSegment getBlockLocation (org.apache.spark.storage.BlockId blockId) ;
}
