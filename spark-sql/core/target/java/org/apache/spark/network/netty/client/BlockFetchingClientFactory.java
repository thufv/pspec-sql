package org.apache.spark.network.netty.client;
/**
 * Factory for creating {@link BlockFetchingClient} by using createClient. This factory reuses
 * the worker thread pool for Netty.
 * <p>
 * Concurrency: createClient is safe to be called from multiple threads concurrently.
 */
private  class BlockFetchingClientFactory {
  public  org.apache.spark.network.netty.NettyConfig conf () { throw new RuntimeException(); }
  // not preceding
  public   BlockFetchingClientFactory (org.apache.spark.network.netty.NettyConfig conf) { throw new RuntimeException(); }
  public   BlockFetchingClientFactory (org.apache.spark.SparkConf sparkConf) { throw new RuntimeException(); }
  /** A thread factory so the threads are named (for debugging). */
  public  java.util.concurrent.ThreadFactory threadFactory () { throw new RuntimeException(); }
  /** The following two are instantiated by the {@link init} method, depending ioMode. */
  public  Object socketChannelClass () { throw new RuntimeException(); }
  public  io.netty.channel.EventLoopGroup workerGroup () { throw new RuntimeException(); }
  /** Initialize {@link socketChannelClass} and {@link workerGroup} based on ioMode. */
  private  void init () { throw new RuntimeException(); }
  /**
   * Create a new BlockFetchingClient connecting to the given remote host / port.
   * <p>
   * This blocks until a connection is successfully established.
   * <p>
   * Concurrency: This method is safe to call from multiple threads.
   */
  public  org.apache.spark.network.netty.client.BlockFetchingClient createClient (java.lang.String remoteHost, int remotePort) { throw new RuntimeException(); }
  public  void stop () { throw new RuntimeException(); }
}
