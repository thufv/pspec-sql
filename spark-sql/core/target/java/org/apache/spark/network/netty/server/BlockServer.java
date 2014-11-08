package org.apache.spark.network.netty.server;
/**
 * Server for serving Spark data blocks.
 * This should be used together with {@link org.apache.spark.network.netty.client.BlockFetchingClient}.
 * <p>
 * Protocol for requesting blocks (client to server):
 *   One block id per line, e.g. to request 3 blocks: "block1\nblock2\nblock3\n"
 * <p>
 * Protocol for sending blocks (server to client):
 *   frame-length (4 bytes), block-id-length (4 bytes), block-id, block-data.
 * <p>
 *   frame-length should not include the length of itself.
 *   If block-id-length is negative, then this is an error message rather than block-data. The real
 *   length is the absolute value of the frame-length.
 * <p>
 */
private  class BlockServer implements org.apache.spark.Logging {
  public   BlockServer (org.apache.spark.network.netty.NettyConfig conf, org.apache.spark.storage.BlockDataProvider dataProvider) { throw new RuntimeException(); }
  public   BlockServer (org.apache.spark.SparkConf sparkConf, org.apache.spark.storage.BlockDataProvider dataProvider) { throw new RuntimeException(); }
  public  int port () { throw new RuntimeException(); }
  public  java.lang.String hostName () { throw new RuntimeException(); }
  private  int _port () { throw new RuntimeException(); }
  private  java.lang.String _hostName () { throw new RuntimeException(); }
  private  io.netty.bootstrap.ServerBootstrap bootstrap () { throw new RuntimeException(); }
  private  io.netty.channel.ChannelFuture channelFuture () { throw new RuntimeException(); }
  /** Initialize the server. */
  private  void init () { throw new RuntimeException(); }
  /** Shutdown the server. */
  public  void stop () { throw new RuntimeException(); }
}
