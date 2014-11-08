package org.apache.spark;
// no position
/**
 * :: DeveloperApi ::
 * The task finished successfully, but the result was lost from the executor's block manager before
 * it was fetched.
 */
public  class TaskResultLost implements org.apache.spark.TaskFailedReason, scala.Product, scala.Serializable {
  static public  java.lang.String toErrorString () { throw new RuntimeException(); }
}
