package org.apache.spark;
/**
 * :: DeveloperApi ::
 * Various possible reasons why a task failed.
 */
public  interface TaskFailedReason extends org.apache.spark.TaskEndReason {
  /** Error message displayed in the web UI. */
  public abstract  java.lang.String toErrorString () ;
}
