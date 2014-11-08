package org.apache.spark;
/**
 * Configuration for a Spark application. Used to set various Spark parameters as key-value pairs.
 * <p>
 * Most of the time, you would create a SparkConf object with <code>new SparkConf()</code>, which will load
 * values from any <code>spark.*</code> Java system properties set in your application as well. In this case,
 * parameters you set directly on the <code>SparkConf</code> object take priority over system properties.
 * <p>
 * For unit tests, you can also call <code>new SparkConf(false)</code> to skip loading external settings and
 * get the same configuration no matter what the system properties are.
 * <p>
 * All setter methods in this class support chaining. For example, you can write
 * <code>new SparkConf().setMaster("local").setAppName("My app")</code>.
 * <p>
 * Note that once a SparkConf object is passed to Spark, it is cloned and can no longer be modified
 * by the user. Spark does not support modifying the configuration at runtime.
 * <p>
 * @param loadDefaults whether to also load values from Java system properties
 */
public  class SparkConf implements scala.Cloneable, org.apache.spark.Logging {
  /**
   * Return whether the given config is an akka config (e.g. akka.actor.provider).
   * Note that this does not include spark-specific akka configs (e.g. spark.akka.timeout).
   */
  static public  boolean isAkkaConf (java.lang.String name) { throw new RuntimeException(); }
  /**
   * Return whether the given config should be passed to an executor on start-up.
   * <p>
   * Certain akka and authentication configs are required of the executor when it connects to
   * the scheduler, while the rest of the spark configs can be inherited from the driver later.
   */
  static public  boolean isExecutorStartupConf (java.lang.String name) { throw new RuntimeException(); }
  /**
   * Return whether the given config is a Spark port config.
   */
  static public  boolean isSparkPortConf (java.lang.String name) { throw new RuntimeException(); }
  public   SparkConf (boolean loadDefaults) { throw new RuntimeException(); }
  /** Create a SparkConf that loads defaults from system properties and the classpath */
  public   SparkConf () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<java.lang.String, java.lang.String> settings () { throw new RuntimeException(); }
  /** Set a configuration variable. */
  public  org.apache.spark.SparkConf set (java.lang.String key, java.lang.String value) { throw new RuntimeException(); }
  /**
   * The master URL to connect to, such as "local" to run locally with one thread, "local[4]" to
   * run locally with 4 cores, or "spark://master:7077" to run on a Spark standalone cluster.
   */
  public  org.apache.spark.SparkConf setMaster (java.lang.String master) { throw new RuntimeException(); }
  /** Set a name for your application. Shown in the Spark web UI. */
  public  org.apache.spark.SparkConf setAppName (java.lang.String name) { throw new RuntimeException(); }
  /** Set JAR files to distribute to the cluster. */
  public  org.apache.spark.SparkConf setJars (scala.collection.Seq<java.lang.String> jars) { throw new RuntimeException(); }
  /** Set JAR files to distribute to the cluster. (Java-friendly version.) */
  public  org.apache.spark.SparkConf setJars (java.lang.String[] jars) { throw new RuntimeException(); }
  /**
   * Set an environment variable to be used when launching executors for this application.
   * These variables are stored as properties of the form spark.executorEnv.VAR_NAME
   * (for example spark.executorEnv.PATH) but this method makes them easier to set.
   */
  public  org.apache.spark.SparkConf setExecutorEnv (java.lang.String variable, java.lang.String value) { throw new RuntimeException(); }
  /**
   * Set multiple environment variables to be used when launching executors.
   * These variables are stored as properties of the form spark.executorEnv.VAR_NAME
   * (for example spark.executorEnv.PATH) but this method makes them easier to set.
   */
  public  org.apache.spark.SparkConf setExecutorEnv (scala.collection.Seq<scala.Tuple2<java.lang.String, java.lang.String>> variables) { throw new RuntimeException(); }
  /**
   * Set multiple environment variables to be used when launching executors.
   * (Java-friendly version.)
   */
  public  org.apache.spark.SparkConf setExecutorEnv (scala.Tuple2<java.lang.String, java.lang.String>[] variables) { throw new RuntimeException(); }
  /**
   * Set the location where Spark is installed on worker nodes.
   */
  public  org.apache.spark.SparkConf setSparkHome (java.lang.String home) { throw new RuntimeException(); }
  /** Set multiple parameters together */
  public  org.apache.spark.SparkConf setAll (scala.collection.Traversable<scala.Tuple2<java.lang.String, java.lang.String>> settings) { throw new RuntimeException(); }
  /** Set a parameter if it isn't already configured */
  public  org.apache.spark.SparkConf setIfMissing (java.lang.String key, java.lang.String value) { throw new RuntimeException(); }
  /** Remove a parameter from the configuration */
  public  org.apache.spark.SparkConf remove (java.lang.String key) { throw new RuntimeException(); }
  /** Get a parameter; throws a NoSuchElementException if it's not set */
  public  java.lang.String get (java.lang.String key) { throw new RuntimeException(); }
  /** Get a parameter, falling back to a default if not set */
  public  java.lang.String get (java.lang.String key, java.lang.String defaultValue) { throw new RuntimeException(); }
  /** Get a parameter as an Option */
  public  scala.Option<java.lang.String> getOption (java.lang.String key) { throw new RuntimeException(); }
  /** Get all parameters as a list of pairs */
  public  scala.Tuple2<java.lang.String, java.lang.String>[] getAll () { throw new RuntimeException(); }
  /** Get a parameter as an integer, falling back to a default if not set */
  public  int getInt (java.lang.String key, int defaultValue) { throw new RuntimeException(); }
  /** Get a parameter as a long, falling back to a default if not set */
  public  long getLong (java.lang.String key, long defaultValue) { throw new RuntimeException(); }
  /** Get a parameter as a double, falling back to a default if not set */
  public  double getDouble (java.lang.String key, double defaultValue) { throw new RuntimeException(); }
  /** Get a parameter as a boolean, falling back to a default if not set */
  public  boolean getBoolean (java.lang.String key, boolean defaultValue) { throw new RuntimeException(); }
  /** Get all executor environment variables set on this SparkConf */
  public  scala.collection.Seq<scala.Tuple2<java.lang.String, java.lang.String>> getExecutorEnv () { throw new RuntimeException(); }
  /** Get all akka conf variables set on this SparkConf */
  public  scala.collection.Seq<scala.Tuple2<java.lang.String, java.lang.String>> getAkkaConf () { throw new RuntimeException(); }
  /** Does the configuration contain a given parameter? */
  public  boolean contains (java.lang.String key) { throw new RuntimeException(); }
  /** Copy this object */
  public  org.apache.spark.SparkConf clone () { throw new RuntimeException(); }
  /**
   * By using this instead of System.getenv(), environment variables can be mocked
   * in unit tests.
   */
  private  java.lang.String getenv (java.lang.String name) { throw new RuntimeException(); }
  /** Checks for illegal or deprecated config settings. Throws an exception for the former. Not
   * idempotent - may mutate this conf object to convert deprecated settings to supported ones. */
  private  void validateSettings () { throw new RuntimeException(); }
  /**
   * Return a string listing all keys and values, one per line. This is useful to print the
   * configuration out for debugging.
   */
  public  java.lang.String toDebugString () { throw new RuntimeException(); }
}
