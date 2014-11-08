package org.apache.spark;
/**
 * :: DeveloperApi ::
 * Task failed to fetch shuffle data from a remote node. Probably means we have lost the remote
 * executors the task is trying to fetch from, and thus need to rerun the previous stage.
 */
public  class FetchFailed implements org.apache.spark.TaskFailedReason, scala.Product, scala.Serializable {
  public  org.apache.spark.storage.BlockManagerId bmAddress () { throw new RuntimeException(); }
  public  int shuffleId () { throw new RuntimeException(); }
  public  int mapId () { throw new RuntimeException(); }
  public  int reduceId () { throw new RuntimeException(); }
  // not preceding
  public   FetchFailed (org.apache.spark.storage.BlockManagerId bmAddress, int shuffleId, int mapId, int reduceId) { throw new RuntimeException(); }
  public  java.lang.String toErrorString () { throw new RuntimeException(); }
}
