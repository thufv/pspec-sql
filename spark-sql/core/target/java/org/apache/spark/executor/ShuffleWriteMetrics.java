package org.apache.spark.executor;
/**
 * :: DeveloperApi ::
 * Metrics pertaining to shuffle data written in a given task.
 */
public  class ShuffleWriteMetrics implements scala.Serializable {
  public   ShuffleWriteMetrics () { throw new RuntimeException(); }
  /**
   * Number of bytes written for the shuffle by this task
   */
  public  long shuffleBytesWritten () { throw new RuntimeException(); }
  /**
   * Time the task spent blocking on writes to disk or buffer cache, in nanoseconds
   */
  public  long shuffleWriteTime () { throw new RuntimeException(); }
}
