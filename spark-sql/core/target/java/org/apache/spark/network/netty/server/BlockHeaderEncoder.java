package org.apache.spark.network.netty.server;
/**
 * A simple encoder for BlockHeader. See {@link BlockServer} for the server to client protocol.
 */
private  class BlockHeaderEncoder extends io.netty.handler.codec.MessageToByteEncoder<org.apache.spark.network.netty.server.BlockHeader> {
  public   BlockHeaderEncoder () { throw new RuntimeException(); }
  public  void encode (io.netty.channel.ChannelHandlerContext ctx, org.apache.spark.network.netty.server.BlockHeader msg, io.netty.buffer.ByteBuf out) { throw new RuntimeException(); }
}
