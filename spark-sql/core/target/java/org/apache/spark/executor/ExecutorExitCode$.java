package org.apache.spark.executor;
// no position
/**
 * These are exit codes that executors should use to provide the master with information about
 * executor failures assuming that cluster management framework can capture the exit codes (but
 * perhaps not log files). The exit code constants here are chosen to be unlikely to conflict
 * with "natural" exit statuses that may be caused by the JVM or user code. In particular,
 * exit codes 128+ arise on some Unix-likes as a result of signals, and it appears that the
 * OpenJDK JVM may use exit code 1 in some of its own "last chance" code.
 */
private  class ExecutorExitCode$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final ExecutorExitCode$ MODULE$ = null;
  public   ExecutorExitCode$ () { throw new RuntimeException(); }
  /** The default uncaught exception handler was reached. */
  public  int UNCAUGHT_EXCEPTION () { throw new RuntimeException(); }
  /** The default uncaught exception handler was called and an exception was encountered while
   logging the exception. */
  public  int UNCAUGHT_EXCEPTION_TWICE () { throw new RuntimeException(); }
  /** The default uncaught exception handler was reached, and the uncaught exception was an
   OutOfMemoryError. */
  public  int OOM () { throw new RuntimeException(); }
  /** DiskStore failed to create a local temporary directory after many attempts. */
  public  int DISK_STORE_FAILED_TO_CREATE_DIR () { throw new RuntimeException(); }
  /** TachyonStore failed to initialize after many attempts. */
  public  int TACHYON_STORE_FAILED_TO_INITIALIZE () { throw new RuntimeException(); }
  /** TachyonStore failed to create a local temporary directory after many attempts. */
  public  int TACHYON_STORE_FAILED_TO_CREATE_DIR () { throw new RuntimeException(); }
  public  java.lang.String explainExitCode (int exitCode) { throw new RuntimeException(); }
}
