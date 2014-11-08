package org.apache.spark.network.netty;
/**
 * A central location that tracks all the settings we exposed to users.
 */
private  class NettyConfig {
  public   NettyConfig (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  /** Port the server listens on. Default to a random port. */
  public  int serverPort () { throw new RuntimeException(); }
  /** IO mode: nio, oio, epoll, or auto (try epoll first and then nio). */
  public  java.lang.String ioMode () { throw new RuntimeException(); }
  /** Connect timeout in secs. Default 60 secs. */
  public  int connectTimeoutMs () { throw new RuntimeException(); }
  /**
   * Percentage of the desired amount of time spent for I/O in the child event loops.
   * Only applicable in nio and epoll.
   */
  public  int ioRatio () { throw new RuntimeException(); }
  /** Requested maximum length of the queue of incoming connections. */
  public  scala.Option<java.lang.Object> backLog () { throw new RuntimeException(); }
  /**
   * Receive buffer size (SO_RCVBUF).
   * Note: the optimal size for receive buffer and send buffer should be
   *  latency * network_bandwidth.
   * Assuming latency = 1ms, network_bandwidth = 10Gbps
   *  buffer size should be ~ 1.25MB
   */
  public  scala.Option<java.lang.Object> receiveBuf () { throw new RuntimeException(); }
  /** Send buffer size (SO_SNDBUF). */
  public  scala.Option<java.lang.Object> sendBuf () { throw new RuntimeException(); }
}
