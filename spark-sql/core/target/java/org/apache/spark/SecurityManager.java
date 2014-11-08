package org.apache.spark;
/**
 * Spark class responsible for security.
 * <p>
 * In general this class should be instantiated by the SparkEnv and most components
 * should access it from that. There are some cases where the SparkEnv hasn't been
 * initialized yet and this class must be instantiated directly.
 * <p>
 * Spark currently supports authentication via a shared secret.
 * Authentication can be configured to be on via the 'spark.authenticate' configuration
 * parameter. This parameter controls whether the Spark communication protocols do
 * authentication using the shared secret. This authentication is a basic handshake to
 * make sure both sides have the same shared secret and are allowed to communicate.
 * If the shared secret is not identical they will not be allowed to communicate.
 * <p>
 * The Spark UI can also be secured by using javax servlet filters. A user may want to
 * secure the UI if it has data that other users should not be allowed to see. The javax
 * servlet filter specified by the user can authenticate the user and then once the user
 * is logged in, Spark can compare that user versus the view acls to make sure they are
 * authorized to view the UI. The configs 'spark.acls.enable' and 'spark.ui.view.acls'
 * control the behavior of the acls. Note that the person who started the application
 * always has view access to the UI.
 * <p>
 * Spark has a set of modify acls (<code>spark.modify.acls</code>) that controls which users have permission
 * to  modify a single application. This would include things like killing the application. By
 * default the person who started the application has modify access. For modify access through
 * the UI, you must have a filter that does authentication in place for the modify acls to work
 * properly.
 * <p>
 * Spark also has a set of admin acls (<code>spark.admin.acls</code>) which is a set of users/administrators
 * who always have permission to view or modify the Spark application.
 * <p>
 * Spark does not currently support encryption after authentication.
 * <p>
 * At this point spark has multiple communication protocols that need to be secured and
 * different underlying mechanisms are used depending on the protocol:
 * <p>
 *  - Akka -> The only option here is to use the Akka Remote secure-cookie functionality.
 *            Akka remoting allows you to specify a secure cookie that will be exchanged
 *            and ensured to be identical in the connection handshake between the client
 *            and the server. If they are not identical then the client will be refused
 *            to connect to the server. There is no control of the underlying
 *            authentication mechanism so its not clear if the password is passed in
 *            plaintext or uses DIGEST-MD5 or some other mechanism.
 *            Akka also has an option to turn on SSL, this option is not currently supported
 *            but we could add a configuration option in the future.
 * <p>
 *  - HTTP for broadcast and file server (via HttpServer) ->  Spark currently uses Jetty
 *            for the HttpServer. Jetty supports multiple authentication mechanisms -
 *            Basic, Digest, Form, Spengo, etc. It also supports multiple different login
 *            services - Hash, JAAS, Spnego, JDBC, etc.  Spark currently uses the HashLoginService
 *            to authenticate using DIGEST-MD5 via a single user and the shared secret.
 *            Since we are using DIGEST-MD5, the shared secret is not passed on the wire
 *            in plaintext.
 *            We currently do not support SSL (https), but Jetty can be configured to use it
 *            so we could add a configuration option for this in the future.
 * <p>
 *            The Spark HttpServer installs the HashLoginServer and configures it to DIGEST-MD5.
 *            Any clients must specify the user and password. There is a default
 *            Authenticator installed in the SecurityManager to how it does the authentication
 *            and in this case gets the user name and password from the request.
 * <p>
 *  - ConnectionManager -> The Spark ConnectionManager uses java nio to asynchronously
 *            exchange messages.  For this we use the Java SASL
 *            (Simple Authentication and Security Layer) API and again use DIGEST-MD5
 *            as the authentication mechanism. This means the shared secret is not passed
 *            over the wire in plaintext.
 *            Note that SASL is pluggable as to what mechanism it uses.  We currently use
 *            DIGEST-MD5 but this could be changed to use Kerberos or other in the future.
 *            Spark currently supports "auth" for the quality of protection, which means
 *            the connection is not supporting integrity or privacy protection (encryption)
 *            after authentication. SASL also supports "auth-int" and "auth-conf" which
 *            SPARK could be support in the future to allow the user to specify the quality
 *            of protection they want. If we support those, the messages will also have to
 *            be wrapped and unwrapped via the SaslServer/SaslClient.wrap/unwrap API's.
 * <p>
 *            Since the connectionManager does asynchronous messages passing, the SASL
 *            authentication is a bit more complex. A ConnectionManager can be both a client
 *            and a Server, so for a particular connection is has to determine what to do.
 *            A ConnectionId was added to be able to track connections and is used to
 *            match up incoming messages with connections waiting for authentication.
 *            If its acting as a client and trying to send a message to another ConnectionManager,
 *            it blocks the thread calling sendMessage until the SASL negotiation has occurred.
 *            The ConnectionManager tracks all the sendingConnections using the ConnectionId
 *            and waits for the response from the server and does the handshake.
 * <p>
 *  - HTTP for the Spark UI -> the UI was changed to use servlets so that javax servlet filters
 *            can be used. Yarn requires a specific AmIpFilter be installed for security to work
 *            properly. For non-Yarn deployments, users can write a filter to go through a
 *            companies normal login service. If an authentication filter is in place then the
 *            SparkUI can be configured to check the logged in user against the list of users who
 *            have view acls to see if that user is authorized.
 *            The filters can also be used for many different purposes. For instance filters
 *            could be used for logging, encryption, or compression.
 * <p>
 *  The exact mechanisms used to generate/distributed the shared secret is deployment specific.
 * <p>
 *  For Yarn deployments, the secret is automatically generated using the Akka remote
 *  Crypt.generateSecureCookie() API. The secret is placed in the Hadoop UGI which gets passed
 *  around via the Hadoop RPC mechanism. Hadoop RPC can be configured to support different levels
 *  of protection. See the Hadoop documentation for more details. Each Spark application on Yarn
 *  gets a different shared secret. On Yarn, the Spark UI gets configured to use the Hadoop Yarn
 *  AmIpFilter which requires the user to go through the ResourceManager Proxy. That Proxy is there
 *  to reduce the possibility of web based attacks through YARN. Hadoop can be configured to use
 *  filters to do authentication. That authentication then happens via the ResourceManager Proxy
 *  and Spark will use that to do authorization against the view acls.
 * <p>
 *  For other Spark deployments, the shared secret must be specified via the
 *  spark.authenticate.secret config.
 *  All the nodes (Master and Workers) and the applications need to have the same shared secret.
 *  This again is not ideal as one user could potentially affect another users application.
 *  This should be enhanced in the future to provide better protection.
 *  If the UI needs to be secured the user needs to install a javax servlet filter to do the
 *  authentication. Spark will then use that user to compare against the view acls to do
 *  authorization. If not filter is in place the user is generally null and no authorization
 *  can take place.
 */
private  class SecurityManager implements org.apache.spark.Logging {
  public   SecurityManager (org.apache.spark.SparkConf sparkConf) { throw new RuntimeException(); }
  private  java.lang.String sparkSecretLookupKey () { throw new RuntimeException(); }
  private  boolean authOn () { throw new RuntimeException(); }
  private  boolean aclsOn () { throw new RuntimeException(); }
  private  scala.collection.immutable.Set<java.lang.String> adminAcls () { throw new RuntimeException(); }
  private  scala.collection.immutable.Set<java.lang.String> viewAcls () { throw new RuntimeException(); }
  private  scala.collection.immutable.Set<java.lang.String> modifyAcls () { throw new RuntimeException(); }
  private  scala.collection.immutable.Set<java.lang.String> defaultAclUsers () { throw new RuntimeException(); }
  private  java.lang.String secretKey () { throw new RuntimeException(); }
  /**
   * Split a comma separated String, filter out any empty items, and return a Set of strings
   */
  private  scala.collection.immutable.Set<java.lang.String> stringToSet (java.lang.String list) { throw new RuntimeException(); }
  /**
   * Admin acls should be set before the view or modify acls.  If you modify the admin
   * acls you should also set the view and modify acls again to pick up the changes.
   */
  public  void setViewAcls (scala.collection.immutable.Set<java.lang.String> defaultUsers, java.lang.String allowedUsers) { throw new RuntimeException(); }
  public  void setViewAcls (java.lang.String defaultUser, java.lang.String allowedUsers) { throw new RuntimeException(); }
  public  java.lang.String getViewAcls () { throw new RuntimeException(); }
  /**
   * Admin acls should be set before the view or modify acls.  If you modify the admin
   * acls you should also set the view and modify acls again to pick up the changes.
   */
  public  void setModifyAcls (scala.collection.immutable.Set<java.lang.String> defaultUsers, java.lang.String allowedUsers) { throw new RuntimeException(); }
  public  java.lang.String getModifyAcls () { throw new RuntimeException(); }
  /**
   * Admin acls should be set before the view or modify acls.  If you modify the admin
   * acls you should also set the view and modify acls again to pick up the changes.
   */
  public  void setAdminAcls (java.lang.String adminUsers) { throw new RuntimeException(); }
  public  void setAcls (boolean aclSetting) { throw new RuntimeException(); }
  /**
   * Generates or looks up the secret key.
   * <p>
   * The way the key is stored depends on the Spark deployment mode. Yarn
   * uses the Hadoop UGI.
   * <p>
   * For non-Yarn deployments, If the config variable is not set
   * we throw an exception.
   */
  private  java.lang.String generateSecretKey () { throw new RuntimeException(); }
  /**
   * Check to see if Acls for the UI are enabled
   * @return true if UI authentication is enabled, otherwise false
   */
  public  boolean aclsEnabled () { throw new RuntimeException(); }
  /**
   * Checks the given user against the view acl list to see if they have
   * authorization to view the UI. If the UI acls are disabled
   * via spark.acls.enable, all users have view access. If the user is null
   * it is assumed authentication is off and all users have access.
   * <p>
   * @param user to see if is authorized
   * @return true is the user has permission, otherwise false
   */
  public  boolean checkUIViewPermissions (java.lang.String user) { throw new RuntimeException(); }
  /**
   * Checks the given user against the modify acl list to see if they have
   * authorization to modify the application. If the UI acls are disabled
   * via spark.acls.enable, all users have modify access. If the user is null
   * it is assumed authentication isn't turned on and all users have access.
   * <p>
   * @param user to see if is authorized
   * @return true is the user has permission, otherwise false
   */
  public  boolean checkModifyPermissions (java.lang.String user) { throw new RuntimeException(); }
  /**
   * Check to see if authentication for the Spark communication protocols is enabled
   * @return true if authentication is enabled, otherwise false
   */
  public  boolean isAuthenticationEnabled () { throw new RuntimeException(); }
  /**
   * Gets the user used for authenticating HTTP connections.
   * For now use a single hardcoded user.
   * @return the HTTP user as a String
   */
  public  java.lang.String getHttpUser () { throw new RuntimeException(); }
  /**
   * Gets the user used for authenticating SASL connections.
   * For now use a single hardcoded user.
   * @return the SASL user as a String
   */
  public  java.lang.String getSaslUser () { throw new RuntimeException(); }
  /**
   * Gets the secret key.
   * @return the secret key as a String if authentication is enabled, otherwise returns null
   */
  public  java.lang.String getSecretKey () { throw new RuntimeException(); }
}
