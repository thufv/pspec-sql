package org.apache.spark.deploy.worker;
// no position
/**
 ** Utilities for running commands with the spark classpath.
 */
private  class CommandUtils implements org.apache.spark.Logging {
  static public  scala.collection.Seq<java.lang.String> buildCommandSeq (org.apache.spark.deploy.Command command, int memory, java.lang.String sparkHome) { throw new RuntimeException(); }
  static private  scala.Option<java.lang.String> getEnv (java.lang.String key, org.apache.spark.deploy.Command command) { throw new RuntimeException(); }
  /**
   * Attention: this must always be aligned with the environment variables in the run scripts and
   * the way the JAVA_OPTS are assembled there.
   */
  static public  scala.collection.Seq<java.lang.String> buildJavaOpts (org.apache.spark.deploy.Command command, int memory, java.lang.String sparkHome) { throw new RuntimeException(); }
  /** Spawn a thread that will redirect a given stream to a file */
  static public  void redirectStream (java.io.InputStream in, java.io.File file) { throw new RuntimeException(); }
}
