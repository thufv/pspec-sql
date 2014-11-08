package org.apache.spark.network;
private abstract class Connection implements org.apache.spark.Logging {
  public  java.nio.channels.SocketChannel channel () { throw new RuntimeException(); }
  public  java.nio.channels.Selector selector () { throw new RuntimeException(); }
  public  org.apache.spark.network.ConnectionManagerId socketRemoteConnectionManagerId () { throw new RuntimeException(); }
  public  org.apache.spark.network.ConnectionId connectionId () { throw new RuntimeException(); }
  // not preceding
  public   Connection (java.nio.channels.SocketChannel channel, java.nio.channels.Selector selector, org.apache.spark.network.ConnectionManagerId socketRemoteConnectionManagerId, org.apache.spark.network.ConnectionId connectionId) { throw new RuntimeException(); }
  public  org.apache.spark.SparkSaslServer sparkSaslServer () { throw new RuntimeException(); }
  public  org.apache.spark.SparkSaslClient sparkSaslClient () { throw new RuntimeException(); }
  public   Connection (java.nio.channels.SocketChannel channel_, java.nio.channels.Selector selector_, org.apache.spark.network.ConnectionId id_) { throw new RuntimeException(); }
  private  boolean closed () { throw new RuntimeException(); }
  public  scala.Function1<org.apache.spark.network.Connection, scala.runtime.BoxedUnit> onCloseCallback () { throw new RuntimeException(); }
  public  scala.Function2<org.apache.spark.network.Connection, java.lang.Exception, scala.runtime.BoxedUnit> onExceptionCallback () { throw new RuntimeException(); }
  public  scala.Function2<org.apache.spark.network.Connection, java.lang.Object, scala.runtime.BoxedUnit> onKeyInterestChangeCallback () { throw new RuntimeException(); }
  public  java.net.InetSocketAddress remoteAddress () { throw new RuntimeException(); }
  /**
   * Used to synchronize client requests: client's work-related requests must
   * wait until SASL authentication completes.
   */
  private  java.lang.Object authenticated () { throw new RuntimeException(); }
  public  java.lang.Object getAuthenticated () { throw new RuntimeException(); }
  public abstract  boolean isSaslComplete () ;
  public abstract  boolean resetForceReregister () ;
  public abstract  void registerInterest () ;
  public abstract  void unregisterInterest () ;
  public abstract  boolean changeInterestForRead () ;
  private  void disposeSasl () { throw new RuntimeException(); }
  public abstract  boolean changeInterestForWrite () ;
  public  org.apache.spark.network.ConnectionManagerId getRemoteConnectionManagerId () { throw new RuntimeException(); }
  public  java.nio.channels.SelectionKey key () { throw new RuntimeException(); }
  public  java.net.InetSocketAddress getRemoteAddress () { throw new RuntimeException(); }
  public  boolean read () { throw new RuntimeException(); }
  public  boolean write () { throw new RuntimeException(); }
  public  void close () { throw new RuntimeException(); }
  protected  boolean isClosed () { throw new RuntimeException(); }
  public  void onClose (scala.Function1<org.apache.spark.network.Connection, scala.runtime.BoxedUnit> callback) { throw new RuntimeException(); }
  public  void onException (scala.Function2<org.apache.spark.network.Connection, java.lang.Exception, scala.runtime.BoxedUnit> callback) { throw new RuntimeException(); }
  public  void onKeyInterestChange (scala.Function2<org.apache.spark.network.Connection, java.lang.Object, scala.runtime.BoxedUnit> callback) { throw new RuntimeException(); }
  public  void callOnExceptionCallback (java.lang.Exception e) { throw new RuntimeException(); }
  public  void callOnCloseCallback () { throw new RuntimeException(); }
  public  void changeConnectionKeyInterest (int ops) { throw new RuntimeException(); }
  public  void printRemainingBuffer (java.nio.ByteBuffer buffer) { throw new RuntimeException(); }
  public  void printBuffer (java.nio.ByteBuffer buffer, int position, int length) { throw new RuntimeException(); }
}
