package org.apache.spark.network.netty.client;
/**
 * Handler that processes server responses. It uses the protocol documented in
 * {@link org.apache.spark.network.netty.server.BlockServer}.
 * <p>
 * Concurrency: thread safe and can be called from multiple threads.
 */
private  class BlockFetchingClientHandler extends io.netty.channel.SimpleChannelInboundHandler<io.netty.buffer.ByteBuf> implements org.apache.spark.Logging {
  public   BlockFetchingClientHandler () { throw new RuntimeException(); }
  /** Tracks the list of outstanding requests and their listeners on success/failure. */
  private  java.util.Map<java.lang.String, org.apache.spark.network.netty.client.BlockClientListener> outstandingRequests () { throw new RuntimeException(); }
  public  void addRequest (java.lang.String blockId, org.apache.spark.network.netty.client.BlockClientListener listener) { throw new RuntimeException(); }
  public  void removeRequest (java.lang.String blockId) { throw new RuntimeException(); }
  public  void exceptionCaught (io.netty.channel.ChannelHandlerContext ctx, java.lang.Throwable cause) { throw new RuntimeException(); }
  public  void channelRead0 (io.netty.channel.ChannelHandlerContext ctx, io.netty.buffer.ByteBuf in) { throw new RuntimeException(); }
}
