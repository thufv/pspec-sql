package org.apache.spark.storage;
// no position
private  class BlockManager$ implements org.apache.spark.Logging {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final BlockManager$ MODULE$ = null;
  public   BlockManager$ () { throw new RuntimeException(); }
  private  org.apache.spark.util.IdGenerator ID_GENERATOR () { throw new RuntimeException(); }
  /** Return the total amount of storage memory available. */
  private  long getMaxMemory (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  /**
   * Attempt to clean up a ByteBuffer if it is memory-mapped. This uses an *unsafe* Sun API that
   * might cause errors if one attempts to read from the unmapped buffer, but it's better than
   * waiting for the GC to find it because that could lead to huge numbers of open files. There's
   * unfortunately no standard API to do this.
   */
  public  void dispose (java.nio.ByteBuffer buffer) { throw new RuntimeException(); }
  public  scala.collection.immutable.Map<org.apache.spark.storage.BlockId, scala.collection.Seq<org.apache.spark.storage.BlockManagerId>> blockIdsToBlockManagers (org.apache.spark.storage.BlockId[] blockIds, org.apache.spark.SparkEnv env, org.apache.spark.storage.BlockManagerMaster blockManagerMaster) { throw new RuntimeException(); }
  public  scala.collection.immutable.Map<org.apache.spark.storage.BlockId, scala.collection.Seq<java.lang.String>> blockIdsToExecutorIds (org.apache.spark.storage.BlockId[] blockIds, org.apache.spark.SparkEnv env, org.apache.spark.storage.BlockManagerMaster blockManagerMaster) { throw new RuntimeException(); }
  public  scala.collection.immutable.Map<org.apache.spark.storage.BlockId, scala.collection.Seq<java.lang.String>> blockIdsToHosts (org.apache.spark.storage.BlockId[] blockIds, org.apache.spark.SparkEnv env, org.apache.spark.storage.BlockManagerMaster blockManagerMaster) { throw new RuntimeException(); }
}
