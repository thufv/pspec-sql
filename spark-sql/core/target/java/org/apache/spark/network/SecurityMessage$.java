package org.apache.spark.network;
// no position
private  class SecurityMessage$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final SecurityMessage$ MODULE$ = null;
  public   SecurityMessage$ () { throw new RuntimeException(); }
  /**
   * Convert the given BufferMessage to a SecurityMessage by parsing the contents
   * of the BufferMessage and populating the SecurityMessage fields.
   * @param bufferMessage is a BufferMessage that was received
   * @return new SecurityMessage
   */
  public  org.apache.spark.network.SecurityMessage fromBufferMessage (org.apache.spark.network.BufferMessage bufferMessage) { throw new RuntimeException(); }
  /**
   * Create a SecurityMessage to send from a given saslResponse.
   * @param response is the response to a challenge from the SaslClient or Saslserver
   * @param connectionId the client connectionId we are negotiation authentication for
   * @return a new SecurityMessage
   */
  public  org.apache.spark.network.SecurityMessage fromResponse (byte[] response, java.lang.String connectionId) { throw new RuntimeException(); }
}
