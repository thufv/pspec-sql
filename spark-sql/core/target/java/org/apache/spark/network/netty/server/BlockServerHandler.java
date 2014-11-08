package org.apache.spark.network.netty.server;
/**
 * A handler that processes requests from clients and writes block data back.
 * <p>
 * The messages should have been processed by a LineBasedFrameDecoder and a StringDecoder first
 * so channelRead0 is called once per line (i.e. per block id).
 */
private  class BlockServerHandler extends io.netty.channel.SimpleChannelInboundHandler<java.lang.String> implements org.apache.spark.Logging {
  public   BlockServerHandler (org.apache.spark.storage.BlockDataProvider dataProvider) { throw new RuntimeException(); }
  public  void exceptionCaught (io.netty.channel.ChannelHandlerContext ctx, java.lang.Throwable cause) { throw new RuntimeException(); }
  public  void channelRead0 (io.netty.channel.ChannelHandlerContext ctx, java.lang.String blockId) { throw new RuntimeException(); }
}
