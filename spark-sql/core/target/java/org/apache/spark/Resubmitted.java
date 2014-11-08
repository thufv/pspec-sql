package org.apache.spark;
// no position
/**
 * :: DeveloperApi ::
 * A {@link org.apache.spark.scheduler.ShuffleMapTask} that completed successfully earlier, but we
 * lost the executor before the stage completed. This means Spark needs to reschedule the task
 * to be re-executed on a different executor.
 */
public  class Resubmitted implements org.apache.spark.TaskFailedReason, scala.Product, scala.Serializable {
  static public  java.lang.String toErrorString () { throw new RuntimeException(); }
}
