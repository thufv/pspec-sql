package org.apache.spark.network.netty.server;
/**
 * Header describing a block. This is used only in the server pipeline.
 * <p>
 * {@link BlockServerHandler} creates this, and {@link BlockHeaderEncoder} encodes it.
 * <p>
 * @param blockSize length of the block content, excluding the length itself.
 *                 If positive, this is the header for a block (not part of the header).
 *                 If negative, this is the header and content for an error message.
 * @param blockId block id
 * @param error some error message from reading the block
 */
private  class BlockHeader {
  public  int blockSize () { throw new RuntimeException(); }
  public  java.lang.String blockId () { throw new RuntimeException(); }
  public  scala.Option<java.lang.String> error () { throw new RuntimeException(); }
  // not preceding
  public   BlockHeader (int blockSize, java.lang.String blockId, scala.Option<java.lang.String> error) { throw new RuntimeException(); }
}
