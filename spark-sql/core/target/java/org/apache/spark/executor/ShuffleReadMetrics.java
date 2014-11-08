package org.apache.spark.executor;
/**
 * :: DeveloperApi ::
 * Metrics pertaining to shuffle data read in a given task.
 */
public  class ShuffleReadMetrics implements scala.Serializable {
  public   ShuffleReadMetrics () { throw new RuntimeException(); }
  /**
   * Absolute time when this task finished reading shuffle data
   */
  public  long shuffleFinishTime () { throw new RuntimeException(); }
  /**
   * Number of blocks fetched in this shuffle by this task (remote or local)
   */
  public  int totalBlocksFetched () { throw new RuntimeException(); }
  /**
   * Number of remote blocks fetched in this shuffle by this task
   */
  public  int remoteBlocksFetched () { throw new RuntimeException(); }
  /**
   * Number of local blocks fetched in this shuffle by this task
   */
  public  int localBlocksFetched () { throw new RuntimeException(); }
  /**
   * Time the task spent waiting for remote shuffle blocks. This only includes the time
   * blocking on shuffle input data. For instance if block B is being fetched while the task is
   * still not finished processing block A, it is not considered to be blocking on block B.
   */
  public  long fetchWaitTime () { throw new RuntimeException(); }
  /**
   * Total number of remote bytes read from the shuffle by this task
   */
  public  long remoteBytesRead () { throw new RuntimeException(); }
}
