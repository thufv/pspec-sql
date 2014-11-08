package org.apache.spark.deploy;
/**
 * Parses and encapsulates arguments from the spark-submit script.
 */
private  class SparkSubmitArguments {
  /** Load properties present in the given file. */
  static public  scala.collection.Seq<scala.Tuple2<java.lang.String, java.lang.String>> getPropertiesFromFile (java.io.File file) { throw new RuntimeException(); }
  public   SparkSubmitArguments (scala.collection.Seq<java.lang.String> args) { throw new RuntimeException(); }
  public  java.lang.String master () { throw new RuntimeException(); }
  public  java.lang.String deployMode () { throw new RuntimeException(); }
  public  java.lang.String executorMemory () { throw new RuntimeException(); }
  public  java.lang.String executorCores () { throw new RuntimeException(); }
  public  java.lang.String totalExecutorCores () { throw new RuntimeException(); }
  public  java.lang.String propertiesFile () { throw new RuntimeException(); }
  public  java.lang.String driverMemory () { throw new RuntimeException(); }
  public  java.lang.String driverExtraClassPath () { throw new RuntimeException(); }
  public  java.lang.String driverExtraLibraryPath () { throw new RuntimeException(); }
  public  java.lang.String driverExtraJavaOptions () { throw new RuntimeException(); }
  public  java.lang.String driverCores () { throw new RuntimeException(); }
  public  boolean supervise () { throw new RuntimeException(); }
  public  java.lang.String queue () { throw new RuntimeException(); }
  public  java.lang.String numExecutors () { throw new RuntimeException(); }
  public  java.lang.String files () { throw new RuntimeException(); }
  public  java.lang.String archives () { throw new RuntimeException(); }
  public  java.lang.String mainClass () { throw new RuntimeException(); }
  public  java.lang.String primaryResource () { throw new RuntimeException(); }
  public  java.lang.String name () { throw new RuntimeException(); }
  public  scala.collection.mutable.ArrayBuffer<java.lang.String> childArgs () { throw new RuntimeException(); }
  public  java.lang.String jars () { throw new RuntimeException(); }
  public  boolean verbose () { throw new RuntimeException(); }
  public  boolean isPython () { throw new RuntimeException(); }
  public  java.lang.String pyFiles () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<java.lang.String, java.lang.String> sparkProperties () { throw new RuntimeException(); }
  /** Return default present in the currently defined defaults file. */
  public  scala.collection.mutable.HashMap<java.lang.String, java.lang.String> getDefaultSparkProperties () { throw new RuntimeException(); }
  /**
   * Fill in any undefined values based on the default properties file or options passed in through
   * the '--conf' flag.
   */
  private  void mergeSparkProperties () { throw new RuntimeException(); }
  /** Ensure that required fields exists. Call this only once all defaults are loaded. */
  private  void checkRequiredArguments () { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
  /** Fill in values by parsing user options. */
  private  void parseOpts (scala.collection.Seq<java.lang.String> opts) { throw new RuntimeException(); }
  // not preceding
  private  void printUsageAndExit (int exitCode, Object unknownParam) { throw new RuntimeException(); }
}
