package org.apache.spark.deploy;
/**
 * Contains util methods to interact with Hadoop from Spark.
 */
public  class SparkHadoopUtil implements org.apache.spark.Logging {
  static private  org.apache.spark.deploy.SparkHadoopUtil hadoop () { throw new RuntimeException(); }
  static public  org.apache.spark.deploy.SparkHadoopUtil get () { throw new RuntimeException(); }
  public   SparkHadoopUtil () { throw new RuntimeException(); }
  public  org.apache.hadoop.conf.Configuration conf () { throw new RuntimeException(); }
  /**
   * Runs the given function with a Hadoop UserGroupInformation as a thread local variable
   * (distributed to child threads), used for authenticating HDFS and YARN calls.
   * <p>
   * IMPORTANT NOTE: If this function is going to be called repeated in the same process
   * you need to look https://issues.apache.org/jira/browse/HDFS-3545 and possibly
   * do a FileSystem.closeAllForUGI in order to avoid leaking Filesystems
   */
  public  void runAsSparkUser (scala.Function0<scala.runtime.BoxedUnit> func) { throw new RuntimeException(); }
  public  void transferCredentials (org.apache.hadoop.security.UserGroupInformation source, org.apache.hadoop.security.UserGroupInformation dest) { throw new RuntimeException(); }
  /**
   * Return an appropriate (subclass) of Configuration. Creating config can initializes some Hadoop
   * subsystems.
   */
  public  org.apache.hadoop.conf.Configuration newConfiguration () { throw new RuntimeException(); }
  /**
   * Add any user credentials to the job conf which are necessary for running on a secure Hadoop
   * cluster.
   */
  public  void addCredentials (org.apache.hadoop.mapred.JobConf conf) { throw new RuntimeException(); }
  public  boolean isYarnMode () { throw new RuntimeException(); }
  public  org.apache.hadoop.security.Credentials getCurrentUserCredentials () { throw new RuntimeException(); }
  public  void addCurrentUserCredentials (org.apache.hadoop.security.Credentials creds) { throw new RuntimeException(); }
  public  void addSecretKeyToUserCredentials (java.lang.String key, java.lang.String secret) { throw new RuntimeException(); }
  public  byte[] getSecretKeyFromUserCredentials (java.lang.String key) { throw new RuntimeException(); }
  public  void loginUserFromKeytab (java.lang.String principalName, java.lang.String keytabFilename) { throw new RuntimeException(); }
}
