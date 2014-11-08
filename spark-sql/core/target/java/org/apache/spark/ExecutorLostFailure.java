package org.apache.spark;
// no position
/**
 * :: DeveloperApi ::
 * The task failed because the executor that it was running on was lost. This may happen because
 * the task crashed the JVM.
 */
public  class ExecutorLostFailure implements org.apache.spark.TaskFailedReason, scala.Product, scala.Serializable {
  static public  java.lang.String toErrorString () { throw new RuntimeException(); }
}
