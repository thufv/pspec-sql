package org.apache.spark;
// no position
/**
 * :: DeveloperApi ::
 * We don't know why the task ended -- for example, because of a ClassNotFound exception when
 * deserializing the task result.
 */
public  class UnknownReason implements org.apache.spark.TaskFailedReason, scala.Product, scala.Serializable {
  static public  java.lang.String toErrorString () { throw new RuntimeException(); }
}
