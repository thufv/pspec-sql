package org.apache.spark.network;
/**
 * SecurityMessage is class that contains the connectionId and sasl token
 * used in SASL negotiation. SecurityMessage has routines for converting
 * it to and from a BufferMessage so that it can be sent by the ConnectionManager
 * and easily consumed by users when received.
 * The api was modeled after BlockMessage.
 * <p>
 * The connectionId is the connectionId of the client side. Since
 * message passing is asynchronous and its possible for the server side (receiving)
 * to get multiple different types of messages on the same connection the connectionId
 * is used to know which connnection the security message is intended for.
 * <p>
 * For instance, lets say we are node_0. We need to send data to node_1. The node_0 side
 * is acting as a client and connecting to node_1. SASL negotiation has to occur
 * between node_0 and node_1 before node_1 trusts node_0 so node_0 sends a security message.
 * node_1 receives the message from node_0 but before it can process it and send a response,
 * some thread on node_1 decides it needs to send data to node_0 so it connects to node_0
 * and sends a security message of its own to authenticate as a client. Now node_0 gets
 * the message and it needs to decide if this message is in response to it being a client
 * (from the first send) or if its just node_1 trying to connect to it to send data.  This
 * is where the connectionId field is used. node_0 can lookup the connectionId to see if
 * it is in response to it being a client or if its in response to someone sending other data.
 * <p>
 * The format of a SecurityMessage as its sent is:
 *   - Length of the ConnectionId
 *   - ConnectionId
 *   - Length of the token
 *   - Token
 */
private  class SecurityMessage implements org.apache.spark.Logging {
  /**
   * Convert the given BufferMessage to a SecurityMessage by parsing the contents
   * of the BufferMessage and populating the SecurityMessage fields.
   * @param bufferMessage is a BufferMessage that was received
   * @return new SecurityMessage
   */
  static public  org.apache.spark.network.SecurityMessage fromBufferMessage (org.apache.spark.network.BufferMessage bufferMessage) { throw new RuntimeException(); }
  /**
   * Create a SecurityMessage to send from a given saslResponse.
   * @param response is the response to a challenge from the SaslClient or Saslserver
   * @param connectionId the client connectionId we are negotiation authentication for
   * @return a new SecurityMessage
   */
  static public  org.apache.spark.network.SecurityMessage fromResponse (byte[] response, java.lang.String connectionId) { throw new RuntimeException(); }
  public   SecurityMessage () { throw new RuntimeException(); }
  private  java.lang.String connectionId () { throw new RuntimeException(); }
  private  byte[] token () { throw new RuntimeException(); }
  public  void set (byte[] byteArr, java.lang.String newconnectionId) { throw new RuntimeException(); }
  /**
   * Read the given buffer and set the members of this class.
   */
  public  void set (java.nio.ByteBuffer buffer) { throw new RuntimeException(); }
  public  void set (org.apache.spark.network.BufferMessage bufferMsg) { throw new RuntimeException(); }
  public  java.lang.String getConnectionId () { throw new RuntimeException(); }
  public  byte[] getToken () { throw new RuntimeException(); }
  /**
   * Create a BufferMessage that can be sent by the ConnectionManager containing
   * the security information from this class.
   * @return BufferMessage
   */
  public  org.apache.spark.network.BufferMessage toBufferMessage () { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
