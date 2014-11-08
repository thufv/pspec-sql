package org.apache.spark;
/**
 * Encapsulates SASL server logic
 */
private  class SparkSaslServer implements org.apache.spark.Logging {
  /**
   * Implementation of javax.security.auth.callback.CallbackHandler
   * for SASL DIGEST-MD5 mechanism
   */
  private  class SparkSaslDigestCallbackHandler implements javax.security.auth.callback.CallbackHandler {
    public   SparkSaslDigestCallbackHandler (org.apache.spark.SecurityManager securityMgr) { throw new RuntimeException(); }
    private  java.lang.String userName () { throw new RuntimeException(); }
    public  void handle (javax.security.auth.callback.Callback[] callbacks) { throw new RuntimeException(); }
  }
  /**
   * This is passed as the server name when creating the sasl client/server.
   * This could be changed to be configurable in the future.
   */
  static public  java.lang.String SASL_DEFAULT_REALM () { throw new RuntimeException(); }
  /**
   * The authentication mechanism used here is DIGEST-MD5. This could be changed to be
   * configurable in the future.
   */
  static public  java.lang.String DIGEST () { throw new RuntimeException(); }
  /**
   * The quality of protection is just "auth". This means that we are doing
   * authentication only, we are not supporting integrity or privacy protection of the
   * communication channel after authentication. This could be changed to be configurable
   * in the future.
   */
  static public  scala.collection.immutable.Map<java.lang.String, java.lang.String> SASL_PROPS () { throw new RuntimeException(); }
  /**
   * Encode a byte[] identifier as a Base64-encoded string.
   * <p>
   * @param identifier identifier to encode
   * @return Base64-encoded string
   */
  static public  java.lang.String encodeIdentifier (byte[] identifier) { throw new RuntimeException(); }
  /**
   * Encode a password as a base64-encoded char[] array.
   * @param password as a byte array.
   * @return password as a char array.
   */
  static public  char[] encodePassword (byte[] password) { throw new RuntimeException(); }
  public   SparkSaslServer (org.apache.spark.SecurityManager securityMgr) { throw new RuntimeException(); }
  /**
   * Actual SASL work done by this object from javax.security.sasl.
   */
  private  javax.security.sasl.SaslServer saslServer () { throw new RuntimeException(); }
  /**
   * Determines whether the authentication exchange has completed.
   * @return true is complete, otherwise false
   */
  public  boolean isComplete () { throw new RuntimeException(); }
  /**
   * Used to respond to server SASL tokens.
   * @param token Server's SASL token
   * @return response to send back to the server.
   */
  public  byte[] response (byte[] token) { throw new RuntimeException(); }
  /**
   * Disposes of any system resources or security-sensitive information the
   * SaslServer might be using.
   */
  public  void dispose () { throw new RuntimeException(); }
}
