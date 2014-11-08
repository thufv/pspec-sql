package org.apache.spark;
// no position
/**
 * :: DeveloperApi ::
 * We don't know why the task ended -- for example, because of a ClassNotFound exception when
 * deserializing the task result.
 */
public  class UnknownReason$ implements org.apache.spark.TaskFailedReason, scala.Product, scala.Serializable {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final UnknownReason$ MODULE$ = null;
  public   UnknownReason$ () { throw new RuntimeException(); }
  public  java.lang.String toErrorString () { throw new RuntimeException(); }
}
