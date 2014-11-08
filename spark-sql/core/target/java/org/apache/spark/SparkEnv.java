package org.apache.spark;
/**
 * :: DeveloperApi ::
 * Holds all the runtime environment objects for a running Spark instance (either master or worker),
 * including the serializer, Akka actor system, block manager, map output tracker, etc. Currently
 * Spark code finds the SparkEnv through a thread-local variable, so each thread that accesses these
 * objects needs to have the right SparkEnv set. You can get the current environment with
 * SparkEnv.get (e.g. after creating a SparkContext) and set it with SparkEnv.set.
 * <p>
 * NOTE: This is not intended for external use. This is exposed for Shark and may be made private
 *       in a future release.
 */
public  class SparkEnv implements org.apache.spark.Logging {
  static private  java.lang.ThreadLocal<org.apache.spark.SparkEnv> env () { throw new RuntimeException(); }
  static private  org.apache.spark.SparkEnv lastSetSparkEnv () { throw new RuntimeException(); }
  static public  java.lang.String driverActorSystemName () { throw new RuntimeException(); }
  static public  java.lang.String executorActorSystemName () { throw new RuntimeException(); }
  static public  void set (org.apache.spark.SparkEnv e) { throw new RuntimeException(); }
  /**
   * Returns the ThreadLocal SparkEnv, if non-null. Else returns the SparkEnv
   * previously set in any thread.
   */
  static public  org.apache.spark.SparkEnv get () { throw new RuntimeException(); }
  /**
   * Returns the ThreadLocal SparkEnv.
   */
  static public  org.apache.spark.SparkEnv getThreadLocal () { throw new RuntimeException(); }
  static private  org.apache.spark.SparkEnv create (org.apache.spark.SparkConf conf, java.lang.String executorId, java.lang.String hostname, int port, boolean isDriver, boolean isLocal, org.apache.spark.scheduler.LiveListenerBus listenerBus) { throw new RuntimeException(); }
  /**
   * Return a map representation of jvm information, Spark properties, system properties, and
   * class paths. Map keys define the category, and map values represent the corresponding
   * attributes as a sequence of KV pairs. This is used mainly for SparkListenerEnvironmentUpdate.
   */
  static private  scala.collection.immutable.Map<java.lang.String, scala.collection.Seq<scala.Tuple2<java.lang.String, java.lang.String>>> environmentDetails (org.apache.spark.SparkConf conf, java.lang.String schedulingMode, scala.collection.Seq<java.lang.String> addedJars, scala.collection.Seq<java.lang.String> addedFiles) { throw new RuntimeException(); }
  public  java.lang.String executorId () { throw new RuntimeException(); }
  public  akka.actor.ActorSystem actorSystem () { throw new RuntimeException(); }
  public  org.apache.spark.serializer.Serializer serializer () { throw new RuntimeException(); }
  public  org.apache.spark.serializer.Serializer closureSerializer () { throw new RuntimeException(); }
  public  org.apache.spark.CacheManager cacheManager () { throw new RuntimeException(); }
  public  org.apache.spark.MapOutputTracker mapOutputTracker () { throw new RuntimeException(); }
  public  org.apache.spark.shuffle.ShuffleManager shuffleManager () { throw new RuntimeException(); }
  public  org.apache.spark.broadcast.BroadcastManager broadcastManager () { throw new RuntimeException(); }
  public  org.apache.spark.storage.BlockManager blockManager () { throw new RuntimeException(); }
  public  org.apache.spark.network.ConnectionManager connectionManager () { throw new RuntimeException(); }
  public  org.apache.spark.SecurityManager securityManager () { throw new RuntimeException(); }
  public  org.apache.spark.HttpFileServer httpFileServer () { throw new RuntimeException(); }
  public  java.lang.String sparkFilesDir () { throw new RuntimeException(); }
  public  org.apache.spark.metrics.MetricsSystem metricsSystem () { throw new RuntimeException(); }
  public  org.apache.spark.shuffle.ShuffleMemoryManager shuffleMemoryManager () { throw new RuntimeException(); }
  public  org.apache.spark.SparkConf conf () { throw new RuntimeException(); }
  // not preceding
  public   SparkEnv (java.lang.String executorId, akka.actor.ActorSystem actorSystem, org.apache.spark.serializer.Serializer serializer, org.apache.spark.serializer.Serializer closureSerializer, org.apache.spark.CacheManager cacheManager, org.apache.spark.MapOutputTracker mapOutputTracker, org.apache.spark.shuffle.ShuffleManager shuffleManager, org.apache.spark.broadcast.BroadcastManager broadcastManager, org.apache.spark.storage.BlockManager blockManager, org.apache.spark.network.ConnectionManager connectionManager, org.apache.spark.SecurityManager securityManager, org.apache.spark.HttpFileServer httpFileServer, java.lang.String sparkFilesDir, org.apache.spark.metrics.MetricsSystem metricsSystem, org.apache.spark.shuffle.ShuffleMemoryManager shuffleMemoryManager, org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  private  scala.collection.mutable.HashMap<scala.Tuple2<java.lang.String, scala.collection.immutable.Map<java.lang.String, java.lang.String>>, org.apache.spark.api.python.PythonWorkerFactory> pythonWorkers () { throw new RuntimeException(); }
  public  java.util.concurrent.ConcurrentMap<java.lang.String, java.lang.Object> hadoopJobMetadata () { throw new RuntimeException(); }
  private  void stop () { throw new RuntimeException(); }
  private  java.net.Socket createPythonWorker (java.lang.String pythonExec, scala.collection.immutable.Map<java.lang.String, java.lang.String> envVars) { throw new RuntimeException(); }
  private  void destroyPythonWorker (java.lang.String pythonExec, scala.collection.immutable.Map<java.lang.String, java.lang.String> envVars, java.net.Socket worker) { throw new RuntimeException(); }
}
