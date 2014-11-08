package org.apache.spark.executor;
// no position
/**
 * The default uncaught exception handler for Executors terminates the whole process, to avoid
 * getting into a bad state indefinitely. Since Executors are relatively lightweight, it's better
 * to fail fast when things go wrong.
 */
private  class ExecutorUncaughtExceptionHandler$ implements java.lang.Thread.UncaughtExceptionHandler, org.apache.spark.Logging {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final ExecutorUncaughtExceptionHandler$ MODULE$ = null;
  public   ExecutorUncaughtExceptionHandler$ () { throw new RuntimeException(); }
  public  void uncaughtException (java.lang.Thread thread, java.lang.Throwable exception) { throw new RuntimeException(); }
  public  void uncaughtException (java.lang.Throwable exception) { throw new RuntimeException(); }
}
