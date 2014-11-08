package org.apache.spark.deploy;
// no position
/**
 * Main gateway of launching a Spark application.
 * <p>
 * This program handles setting up the classpath with relevant Spark dependencies and provides
 * a layer over the different cluster managers and deploy modes that Spark supports.
 */
public  class SparkSubmit {
  static private  int YARN () { throw new RuntimeException(); }
  static private  int STANDALONE () { throw new RuntimeException(); }
  static private  int MESOS () { throw new RuntimeException(); }
  static private  int LOCAL () { throw new RuntimeException(); }
  static private  int ALL_CLUSTER_MGRS () { throw new RuntimeException(); }
  static private  int CLIENT () { throw new RuntimeException(); }
  static private  int CLUSTER () { throw new RuntimeException(); }
  static private  int ALL_DEPLOY_MODES () { throw new RuntimeException(); }
  static private  java.lang.String SPARK_INTERNAL () { throw new RuntimeException(); }
  static private  java.lang.String SPARK_SHELL () { throw new RuntimeException(); }
  static private  java.lang.String PYSPARK_SHELL () { throw new RuntimeException(); }
  static private  int CLASS_NOT_FOUND_EXIT_STATUS () { throw new RuntimeException(); }
  static public  scala.Function0<scala.runtime.BoxedUnit> exitFn () { throw new RuntimeException(); }
  static public  java.io.PrintStream printStream () { throw new RuntimeException(); }
  static private  void printWarning (java.lang.String str) { throw new RuntimeException(); }
  static private  void printErrorAndExit (java.lang.String str) { throw new RuntimeException(); }
  static public  void main (java.lang.String[] args) { throw new RuntimeException(); }
  /**
   * @return a tuple containing
   *           (1) the arguments for the child process,
   *           (2) a list of classpath entries for the child,
   *           (3) a list of system properties and env vars, and
   *           (4) the main class for the child
   */
  static private  scala.Tuple4<scala.collection.mutable.ArrayBuffer<java.lang.String>, scala.collection.mutable.ArrayBuffer<java.lang.String>, scala.collection.mutable.Map<java.lang.String, java.lang.String>, java.lang.String> createLaunchEnv (org.apache.spark.deploy.SparkSubmitArguments args) { throw new RuntimeException(); }
  static private  void launch (scala.collection.mutable.ArrayBuffer<java.lang.String> childArgs, scala.collection.mutable.ArrayBuffer<java.lang.String> childClasspath, scala.collection.mutable.Map<java.lang.String, java.lang.String> sysProps, java.lang.String childMainClass, boolean verbose) { throw new RuntimeException(); }
  static private  void addJarToClasspath (java.lang.String localJar, org.apache.spark.executor.ExecutorURLClassLoader loader) { throw new RuntimeException(); }
  /**
   * Return whether the given primary resource represents a user jar.
   */
  static private  boolean isUserJar (java.lang.String primaryResource) { throw new RuntimeException(); }
  /**
   * Return whether the given primary resource represents a shell.
   */
  static private  boolean isShell (java.lang.String primaryResource) { throw new RuntimeException(); }
  /**
   * Return whether the given primary resource requires running python.
   */
  static private  boolean isPython (java.lang.String primaryResource) { throw new RuntimeException(); }
  static private  boolean isInternal (java.lang.String primaryResource) { throw new RuntimeException(); }
  /**
   * Merge a sequence of comma-separated file lists, some of which may be null to indicate
   * no files, into a single comma-separated string.
   */
  static private  java.lang.String mergeFileLists (scala.collection.Seq<java.lang.String> lists) { throw new RuntimeException(); }
}
