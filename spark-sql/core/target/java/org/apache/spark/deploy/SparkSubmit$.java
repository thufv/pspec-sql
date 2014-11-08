package org.apache.spark.deploy;
// no position
/**
 * Main gateway of launching a Spark application.
 * <p>
 * This program handles setting up the classpath with relevant Spark dependencies and provides
 * a layer over the different cluster managers and deploy modes that Spark supports.
 */
public  class SparkSubmit$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final SparkSubmit$ MODULE$ = null;
  public   SparkSubmit$ () { throw new RuntimeException(); }
  private  int YARN () { throw new RuntimeException(); }
  private  int STANDALONE () { throw new RuntimeException(); }
  private  int MESOS () { throw new RuntimeException(); }
  private  int LOCAL () { throw new RuntimeException(); }
  private  int ALL_CLUSTER_MGRS () { throw new RuntimeException(); }
  private  int CLIENT () { throw new RuntimeException(); }
  private  int CLUSTER () { throw new RuntimeException(); }
  private  int ALL_DEPLOY_MODES () { throw new RuntimeException(); }
  private  java.lang.String SPARK_INTERNAL () { throw new RuntimeException(); }
  private  java.lang.String SPARK_SHELL () { throw new RuntimeException(); }
  private  java.lang.String PYSPARK_SHELL () { throw new RuntimeException(); }
  private  int CLASS_NOT_FOUND_EXIT_STATUS () { throw new RuntimeException(); }
  public  scala.Function0<scala.runtime.BoxedUnit> exitFn () { throw new RuntimeException(); }
  public  java.io.PrintStream printStream () { throw new RuntimeException(); }
  private  void printWarning (java.lang.String str) { throw new RuntimeException(); }
  private  void printErrorAndExit (java.lang.String str) { throw new RuntimeException(); }
  public  void main (java.lang.String[] args) { throw new RuntimeException(); }
  /**
   * @return a tuple containing
   *           (1) the arguments for the child process,
   *           (2) a list of classpath entries for the child,
   *           (3) a list of system properties and env vars, and
   *           (4) the main class for the child
   */
  private  scala.Tuple4<scala.collection.mutable.ArrayBuffer<java.lang.String>, scala.collection.mutable.ArrayBuffer<java.lang.String>, scala.collection.mutable.Map<java.lang.String, java.lang.String>, java.lang.String> createLaunchEnv (org.apache.spark.deploy.SparkSubmitArguments args) { throw new RuntimeException(); }
  private  void launch (scala.collection.mutable.ArrayBuffer<java.lang.String> childArgs, scala.collection.mutable.ArrayBuffer<java.lang.String> childClasspath, scala.collection.mutable.Map<java.lang.String, java.lang.String> sysProps, java.lang.String childMainClass, boolean verbose) { throw new RuntimeException(); }
  private  void addJarToClasspath (java.lang.String localJar, org.apache.spark.executor.ExecutorURLClassLoader loader) { throw new RuntimeException(); }
  /**
   * Return whether the given primary resource represents a user jar.
   */
  private  boolean isUserJar (java.lang.String primaryResource) { throw new RuntimeException(); }
  /**
   * Return whether the given primary resource represents a shell.
   */
  private  boolean isShell (java.lang.String primaryResource) { throw new RuntimeException(); }
  /**
   * Return whether the given primary resource requires running python.
   */
  private  boolean isPython (java.lang.String primaryResource) { throw new RuntimeException(); }
  private  boolean isInternal (java.lang.String primaryResource) { throw new RuntimeException(); }
  /**
   * Merge a sequence of comma-separated file lists, some of which may be null to indicate
   * no files, into a single comma-separated string.
   */
  private  java.lang.String mergeFileLists (scala.collection.Seq<java.lang.String> lists) { throw new RuntimeException(); }
}
