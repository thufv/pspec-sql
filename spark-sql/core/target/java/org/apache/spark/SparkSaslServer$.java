package org.apache.spark;
// no position
private  class SparkSaslServer$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final SparkSaslServer$ MODULE$ = null;
  public   SparkSaslServer$ () { throw new RuntimeException(); }
  /**
   * This is passed as the server name when creating the sasl client/server.
   * This could be changed to be configurable in the future.
   */
  public  java.lang.String SASL_DEFAULT_REALM () { throw new RuntimeException(); }
  /**
   * The authentication mechanism used here is DIGEST-MD5. This could be changed to be
   * configurable in the future.
   */
  public  java.lang.String DIGEST () { throw new RuntimeException(); }
  /**
   * The quality of protection is just "auth". This means that we are doing
   * authentication only, we are not supporting integrity or privacy protection of the
   * communication channel after authentication. This could be changed to be configurable
   * in the future.
   */
  public  scala.collection.immutable.Map<java.lang.String, java.lang.String> SASL_PROPS () { throw new RuntimeException(); }
  /**
   * Encode a byte[] identifier as a Base64-encoded string.
   * <p>
   * @param identifier identifier to encode
   * @return Base64-encoded string
   */
  public  java.lang.String encodeIdentifier (byte[] identifier) { throw new RuntimeException(); }
  /**
   * Encode a password as a base64-encoded char[] array.
   * @param password as a byte array.
   * @return password as a char array.
   */
  public  char[] encodePassword (byte[] password) { throw new RuntimeException(); }
}
