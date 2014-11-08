package org.apache.spark.api.python;
private  class PythonWorkerFactory implements org.apache.spark.Logging {
  static public  int PROCESS_WAIT_TIMEOUT_MS () { throw new RuntimeException(); }
  public   PythonWorkerFactory (java.lang.String pythonExec, scala.collection.immutable.Map<java.lang.String, java.lang.String> envVars) { throw new RuntimeException(); }
  public  boolean useDaemon () { throw new RuntimeException(); }
  public  java.lang.Process daemon () { throw new RuntimeException(); }
  public  java.net.InetAddress daemonHost () { throw new RuntimeException(); }
  public  int daemonPort () { throw new RuntimeException(); }
  public  scala.collection.mutable.WeakHashMap<java.net.Socket, java.lang.Object> daemonWorkers () { throw new RuntimeException(); }
  public  scala.collection.mutable.WeakHashMap<java.net.Socket, java.lang.Process> simpleWorkers () { throw new RuntimeException(); }
  public  java.lang.String pythonPath () { throw new RuntimeException(); }
  public  java.net.Socket create () { throw new RuntimeException(); }
  /**
   * Connect to a worker launched through pyspark/daemon.py, which forks python processes itself
   * to avoid the high cost of forking from Java. This currently only works on UNIX-based systems.
   */
  private  java.net.Socket createThroughDaemon () { throw new RuntimeException(); }
  /**
   * Launch a worker by executing worker.py directly and telling it to connect to us.
   */
  private  java.net.Socket createSimpleWorker () { throw new RuntimeException(); }
  private  void startDaemon () { throw new RuntimeException(); }
  /**
   * Redirect the given streams to our stderr in separate threads.
   */
  private  void redirectStreamsToStderr (java.io.InputStream stdout, java.io.InputStream stderr) { throw new RuntimeException(); }
  private  void stopDaemon () { throw new RuntimeException(); }
  public  void stop () { throw new RuntimeException(); }
  public  void stopWorker (java.net.Socket worker) { throw new RuntimeException(); }
}
