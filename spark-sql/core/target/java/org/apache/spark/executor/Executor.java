package org.apache.spark.executor;
/**
 * Spark executor used with Mesos, YARN, and the standalone scheduler.
 */
private  class Executor implements org.apache.spark.Logging {
  public  class TaskRunner implements java.lang.Runnable {
    public  long taskId () { throw new RuntimeException(); }
    // not preceding
    public   TaskRunner (org.apache.spark.executor.ExecutorBackend execBackend, long taskId, java.lang.String taskName, java.nio.ByteBuffer serializedTask) { throw new RuntimeException(); }
    private  boolean killed () { throw new RuntimeException(); }
    public  org.apache.spark.scheduler.Task<java.lang.Object> task () { throw new RuntimeException(); }
    public  scala.Option<org.apache.spark.scheduler.Task<java.lang.Object>> attemptedTask () { throw new RuntimeException(); }
    public  void kill (boolean interruptThread) { throw new RuntimeException(); }
    public  void run () { throw new RuntimeException(); }
  }
  public   Executor (java.lang.String executorId, java.lang.String slaveHostname, scala.collection.Seq<scala.Tuple2<java.lang.String, java.lang.String>> properties, boolean isLocal) { throw new RuntimeException(); }
  private  scala.collection.mutable.HashMap<java.lang.String, java.lang.Object> currentFiles () { throw new RuntimeException(); }
  private  scala.collection.mutable.HashMap<java.lang.String, java.lang.Object> currentJars () { throw new RuntimeException(); }
  private  java.nio.ByteBuffer EMPTY_BYTE_BUFFER () { throw new RuntimeException(); }
  private  boolean isStopped () { throw new RuntimeException(); }
  public  org.apache.spark.SparkConf conf () { throw new RuntimeException(); }
  public  org.apache.spark.executor.ExecutorSource executorSource () { throw new RuntimeException(); }
  private  org.apache.spark.SparkEnv env () { throw new RuntimeException(); }
  private  org.apache.spark.executor.MutableURLClassLoader urlClassLoader () { throw new RuntimeException(); }
  private  java.lang.ClassLoader replClassLoader () { throw new RuntimeException(); }
  private  int akkaFrameSize () { throw new RuntimeException(); }
  public  java.util.concurrent.ThreadPoolExecutor threadPool () { throw new RuntimeException(); }
  private  java.util.concurrent.ConcurrentHashMap<java.lang.Object, org.apache.spark.executor.Executor.TaskRunner> runningTasks () { throw new RuntimeException(); }
  public  void launchTask (org.apache.spark.executor.ExecutorBackend context, long taskId, java.lang.String taskName, java.nio.ByteBuffer serializedTask) { throw new RuntimeException(); }
  public  void killTask (long taskId, boolean interruptThread) { throw new RuntimeException(); }
  public  void stop () { throw new RuntimeException(); }
  /**
   * Create a ClassLoader for use in tasks, adding any JARs specified by the user or any classes
   * created by the interpreter to the search path
   */
  private  org.apache.spark.executor.MutableURLClassLoader createClassLoader () { throw new RuntimeException(); }
  /**
   * If the REPL is in use, add another ClassLoader that will read
   * new classes defined by the REPL as the user types code
   */
  private  java.lang.ClassLoader addReplClassLoaderIfNeeded (java.lang.ClassLoader parent) { throw new RuntimeException(); }
  /**
   * Download any missing dependencies if we receive a new set of files and JARs from the
   * SparkContext. Also adds any new JARs we fetched to the class loader.
   */
  private  void updateDependencies (scala.collection.mutable.HashMap<java.lang.String, java.lang.Object> newFiles, scala.collection.mutable.HashMap<java.lang.String, java.lang.Object> newJars) { throw new RuntimeException(); }
  public  void startDriverHeartbeater () { throw new RuntimeException(); }
}
