package org.apache.spark.network.netty.client;
/**
 * Client for fetching data blocks from {@link org.apache.spark.network.netty.server.BlockServer}.
 * Use {@link BlockFetchingClientFactory} to instantiate this client.
 * <p>
 * The constructor blocks until a connection is successfully established.
 * <p>
 * See {@link org.apache.spark.network.netty.server.BlockServer} for client/server protocol.
 * <p>
 * Concurrency: thread safe and can be called from multiple threads.
 */
private  class BlockFetchingClient implements org.apache.spark.Logging {
  public   BlockFetchingClient (org.apache.spark.network.netty.client.BlockFetchingClientFactory factory, java.lang.String hostname, int port) { throw new RuntimeException(); }
  private  org.apache.spark.network.netty.client.BlockFetchingClientHandler handler () { throw new RuntimeException(); }
  /** Netty Bootstrap for creating the TCP connection. */
  private  io.netty.bootstrap.Bootstrap bootstrap () { throw new RuntimeException(); }
  /** Netty ChannelFuture for the connection. */
  private  io.netty.channel.ChannelFuture cf () { throw new RuntimeException(); }
  /**
   * Ask the remote server for a sequence of blocks, and execute the callback.
   * <p>
   * Note that this is asynchronous and returns immediately. Upstream caller should throttle the
   * rate of fetching; otherwise we could run out of memory.
   * <p>
   * @param blockIds sequence of block ids to fetch.
   * @param listener callback to fire on fetch success / failure.
   */
  public  void fetchBlocks (scala.collection.Seq<java.lang.String> blockIds, org.apache.spark.network.netty.client.BlockClientListener listener) { throw new RuntimeException(); }
  public  void waitForClose () { throw new RuntimeException(); }
  public  void close () { throw new RuntimeException(); }
}
