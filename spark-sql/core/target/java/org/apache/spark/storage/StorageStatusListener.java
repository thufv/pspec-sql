package org.apache.spark.storage;
/**
 * :: DeveloperApi ::
 * A SparkListener that maintains executor storage status.
 */
public  class StorageStatusListener implements org.apache.spark.scheduler.SparkListener {
  public   StorageStatusListener () { throw new RuntimeException(); }
  public  scala.collection.mutable.Map<java.lang.String, org.apache.spark.storage.StorageStatus> executorIdToStorageStatus () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.storage.StorageStatus> storageStatusList () { throw new RuntimeException(); }
  /** Update storage status list to reflect updated block statuses */
  private  void updateStorageStatus (java.lang.String execId, scala.collection.Seq<scala.Tuple2<org.apache.spark.storage.BlockId, org.apache.spark.storage.BlockStatus>> updatedBlocks) { throw new RuntimeException(); }
  /** Update storage status list to reflect the removal of an RDD from the cache */
  private  void updateStorageStatus (int unpersistedRDDId) { throw new RuntimeException(); }
  public  void onTaskEnd (org.apache.spark.scheduler.SparkListenerTaskEnd taskEnd) { throw new RuntimeException(); }
  public  void onUnpersistRDD (org.apache.spark.scheduler.SparkListenerUnpersistRDD unpersistRDD) { throw new RuntimeException(); }
  public  void onBlockManagerAdded (org.apache.spark.scheduler.SparkListenerBlockManagerAdded blockManagerAdded) { throw new RuntimeException(); }
  public  void onBlockManagerRemoved (org.apache.spark.scheduler.SparkListenerBlockManagerRemoved blockManagerRemoved) { throw new RuntimeException(); }
  /**
   * In the local mode, there is a discrepancy between the executor ID according to the
   * task ("localhost") and that according to SparkEnv ("<driver>"). In the UI, this
   * results in duplicate rows for the same executor. Thus, in this mode, we aggregate
   * these two rows and use the executor ID of "<driver>" to be consistent.
   */
  public  java.lang.String formatExecutorId (java.lang.String execId) { throw new RuntimeException(); }
}
