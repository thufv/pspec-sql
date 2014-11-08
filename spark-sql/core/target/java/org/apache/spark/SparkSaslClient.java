package org.apache.spark;
/**
 * Implements SASL Client logic for Spark
 */
private  class SparkSaslClient implements org.apache.spark.Logging {
  public   SparkSaslClient (org.apache.spark.SecurityManager securityMgr) { throw new RuntimeException(); }
  /**
   * Used to respond to server's counterpart, SaslServer with SASL tokens
   * represented as byte arrays.
   * <p>
   * The authentication mechanism used here is DIGEST-MD5. This could be changed to be
   * configurable in the future.
   */
  private  javax.security.sasl.SaslClient saslClient () { throw new RuntimeException(); }
  /**
   * Used to initiate SASL handshake with server.
   * @return response to challenge if needed
   */
  public  byte[] firstToken () { throw new RuntimeException(); }
  /**
   * Determines whether the authentication exchange has completed.
   * @return true is complete, otherwise false
   */
  public  boolean isComplete () { throw new RuntimeException(); }
  /**
   * Respond to server's SASL token.
   * @param saslTokenMessage contains server's SASL token
   * @return client's response SASL token
   */
  public  byte[] saslResponse (byte[] saslTokenMessage) { throw new RuntimeException(); }
  /**
   * Disposes of any system resources or security-sensitive information the
   * SaslClient might be using.
   */
  public  void dispose () { throw new RuntimeException(); }
  /**
   * Implementation of javax.security.auth.callback.CallbackHandler
   * that works with share secrets.
   */
  private  class SparkSaslClientCallbackHandler implements javax.security.auth.callback.CallbackHandler {
    public   SparkSaslClientCallbackHandler (org.apache.spark.SecurityManager securityMgr) { throw new RuntimeException(); }
    private  java.lang.String userName () { throw new RuntimeException(); }
    private  java.lang.String secretKey () { throw new RuntimeException(); }
    private  char[] userPassword () { throw new RuntimeException(); }
    /**
     * Implementation used to respond to SASL request from the server.
     * <p>
     * @param callbacks objects that indicate what credential information the
     *                  server's SaslServer requires from the client.
     */
    public  void handle (javax.security.auth.callback.Callback[] callbacks) { throw new RuntimeException(); }
  }
}
