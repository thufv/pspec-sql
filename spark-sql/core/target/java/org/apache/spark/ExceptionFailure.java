package org.apache.spark;
/**
 * :: DeveloperApi ::
 * Task failed due to a runtime exception. This is the most common failure case and also captures
 * user program exceptions.
 */
public  class ExceptionFailure implements org.apache.spark.TaskFailedReason, scala.Product, scala.Serializable {
  public  java.lang.String className () { throw new RuntimeException(); }
  public  java.lang.String description () { throw new RuntimeException(); }
  public  java.lang.StackTraceElement[] stackTrace () { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.executor.TaskMetrics> metrics () { throw new RuntimeException(); }
  // not preceding
  public   ExceptionFailure (java.lang.String className, java.lang.String description, java.lang.StackTraceElement[] stackTrace, scala.Option<org.apache.spark.executor.TaskMetrics> metrics) { throw new RuntimeException(); }
  public  java.lang.String toErrorString () { throw new RuntimeException(); }
}
