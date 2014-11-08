package org.apache.spark.network;
private  class ConnectionManager implements org.apache.spark.Logging {
  /**
   * Used by sendMessageReliably to track messages being sent.
   * @param message the message that was sent
   * @param connectionManagerId the connection manager that sent this message
   * @param completionHandler callback that's invoked when the send has completed or failed
   */
  public  class MessageStatus {
    public  org.apache.spark.network.Message message () { throw new RuntimeException(); }
    public  org.apache.spark.network.ConnectionManagerId connectionManagerId () { throw new RuntimeException(); }
    // not preceding
    public   MessageStatus (org.apache.spark.network.Message message, org.apache.spark.network.ConnectionManagerId connectionManagerId, scala.Function1<org.apache.spark.network.ConnectionManager.MessageStatus, scala.runtime.BoxedUnit> completionHandler) { throw new RuntimeException(); }
    /** This is non-None if message has been ack'd */
    public  scala.Option<org.apache.spark.network.Message> ackMessage () { throw new RuntimeException(); }
    public  void markDone (scala.Option<org.apache.spark.network.Message> ackMessage) { throw new RuntimeException(); }
  }
  static public  void main (java.lang.String[] args) { throw new RuntimeException(); }
  static public  void testSequentialSending (org.apache.spark.network.ConnectionManager manager) { throw new RuntimeException(); }
  static public  void testParallelSending (org.apache.spark.network.ConnectionManager manager) { throw new RuntimeException(); }
  static public  void testParallelDecreasingSending (org.apache.spark.network.ConnectionManager manager) { throw new RuntimeException(); }
  static public  void testContinuousSending (org.apache.spark.network.ConnectionManager manager) { throw new RuntimeException(); }
  public   ConnectionManager (int port, org.apache.spark.SparkConf conf, org.apache.spark.SecurityManager securityManager, java.lang.String name) { throw new RuntimeException(); }
  private  java.nio.channels.spi.AbstractSelector selector () { throw new RuntimeException(); }
  private  java.util.Timer ackTimeoutMonitor () { throw new RuntimeException(); }
  private  int authTimeout () { throw new RuntimeException(); }
  private  int ackTimeout () { throw new RuntimeException(); }
  private  java.util.concurrent.ThreadPoolExecutor handleMessageExecutor () { throw new RuntimeException(); }
  private  java.util.concurrent.ThreadPoolExecutor handleReadWriteExecutor () { throw new RuntimeException(); }
  private  java.util.concurrent.ThreadPoolExecutor handleConnectExecutor () { throw new RuntimeException(); }
  private  java.nio.channels.ServerSocketChannel serverChannel () { throw new RuntimeException(); }
  private  scala.collection.mutable.HashMap<org.apache.spark.network.ConnectionId, org.apache.spark.network.SendingConnection> connectionsAwaitingSasl () { throw new RuntimeException(); }
  private  scala.collection.mutable.HashMap<java.nio.channels.SelectionKey, org.apache.spark.network.Connection> connectionsByKey () { throw new RuntimeException(); }
  private  scala.collection.mutable.HashMap<org.apache.spark.network.ConnectionManagerId, org.apache.spark.network.SendingConnection> connectionsById () { throw new RuntimeException(); }
  private  scala.collection.mutable.HashMap<java.lang.Object, org.apache.spark.network.ConnectionManager.MessageStatus> messageStatuses () { throw new RuntimeException(); }
  private  scala.collection.mutable.SynchronizedQueue<scala.Tuple2<java.nio.channels.SelectionKey, java.lang.Object>> keyInterestChangeRequests () { throw new RuntimeException(); }
  private  scala.collection.mutable.SynchronizedQueue<org.apache.spark.network.SendingConnection> registerRequests () { throw new RuntimeException(); }
  public  scala.concurrent.ExecutionContextExecutor futureExecContext () { throw new RuntimeException(); }
  private  scala.Function2<org.apache.spark.network.BufferMessage, org.apache.spark.network.ConnectionManagerId, scala.Option<org.apache.spark.network.Message>> onReceiveCallback () { throw new RuntimeException(); }
  private  boolean authEnabled () { throw new RuntimeException(); }
  private  scala.Tuple2<java.nio.channels.ServerSocketChannel, java.lang.Object> startService (int port) { throw new RuntimeException(); }
  public  org.apache.spark.network.ConnectionManagerId id () { throw new RuntimeException(); }
  private  java.util.concurrent.atomic.AtomicInteger idCount () { throw new RuntimeException(); }
  private  java.lang.Thread selectorThread () { throw new RuntimeException(); }
  private  scala.collection.mutable.HashSet<java.nio.channels.SelectionKey> writeRunnableStarted () { throw new RuntimeException(); }
  private  void triggerWrite (java.nio.channels.SelectionKey key) { throw new RuntimeException(); }
  private  scala.collection.mutable.HashSet<java.nio.channels.SelectionKey> readRunnableStarted () { throw new RuntimeException(); }
  private  void triggerRead (java.nio.channels.SelectionKey key) { throw new RuntimeException(); }
  private  void triggerConnect (java.nio.channels.SelectionKey key) { throw new RuntimeException(); }
  private  void triggerForceCloseByException (java.nio.channels.SelectionKey key, java.lang.Exception e) { throw new RuntimeException(); }
  public  void run () { throw new RuntimeException(); }
  public  void acceptConnection (java.nio.channels.SelectionKey key) { throw new RuntimeException(); }
  private  void addListeners (org.apache.spark.network.Connection connection) { throw new RuntimeException(); }
  public  void addConnection (org.apache.spark.network.Connection connection) { throw new RuntimeException(); }
  public  void removeConnection (org.apache.spark.network.Connection connection) { throw new RuntimeException(); }
  public  void handleConnectionError (org.apache.spark.network.Connection connection, java.lang.Exception e) { throw new RuntimeException(); }
  public  void changeConnectionKeyInterest (org.apache.spark.network.Connection connection, int ops) { throw new RuntimeException(); }
  public  void receiveMessage (org.apache.spark.network.Connection connection, org.apache.spark.network.Message message) { throw new RuntimeException(); }
  private  void handleClientAuthentication (org.apache.spark.network.SendingConnection waitingConn, org.apache.spark.network.SecurityMessage securityMsg, org.apache.spark.network.ConnectionId connectionId) { throw new RuntimeException(); }
  private  void handleServerAuthentication (org.apache.spark.network.Connection connection, org.apache.spark.network.SecurityMessage securityMsg, org.apache.spark.network.ConnectionId connectionId) { throw new RuntimeException(); }
  private  boolean handleAuthentication (org.apache.spark.network.Connection conn, org.apache.spark.network.BufferMessage bufferMessage) { throw new RuntimeException(); }
  private  void handleMessage (org.apache.spark.network.ConnectionManagerId connectionManagerId, org.apache.spark.network.Message message, org.apache.spark.network.Connection connection) { throw new RuntimeException(); }
  private  void checkSendAuthFirst (org.apache.spark.network.ConnectionManagerId connManagerId, org.apache.spark.network.SendingConnection conn) { throw new RuntimeException(); }
  private  void sendSecurityMessage (org.apache.spark.network.ConnectionManagerId connManagerId, org.apache.spark.network.Message message) { throw new RuntimeException(); }
  private  void sendMessage (org.apache.spark.network.ConnectionManagerId connectionManagerId, org.apache.spark.network.Message message) { throw new RuntimeException(); }
  private  void wakeupSelector () { throw new RuntimeException(); }
  /**
   * Send a message and block until an acknowldgment is received or an error occurs.
   * @param connectionManagerId the message's destination
   * @param message the message being sent
   * @return a Future that either returns the acknowledgment message or captures an exception.
   */
  public  scala.concurrent.Future<org.apache.spark.network.Message> sendMessageReliably (org.apache.spark.network.ConnectionManagerId connectionManagerId, org.apache.spark.network.Message message) { throw new RuntimeException(); }
  public  void onReceiveMessage (scala.Function2<org.apache.spark.network.Message, org.apache.spark.network.ConnectionManagerId, scala.Option<org.apache.spark.network.Message>> callback) { throw new RuntimeException(); }
  public  void stop () { throw new RuntimeException(); }
}
