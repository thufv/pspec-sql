package org.apache.spark.ui.storage;
/**
 * :: DeveloperApi ::
 * A SparkListener that prepares information to be displayed on the BlockManagerUI.
 */
public  class StorageListener implements org.apache.spark.scheduler.SparkListener {
  public   StorageListener (org.apache.spark.storage.StorageStatusListener storageStatusListener) { throw new RuntimeException(); }
  public  scala.collection.mutable.Map<java.lang.Object, org.apache.spark.storage.RDDInfo> _rddInfoMap () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.storage.StorageStatus> storageStatusList () { throw new RuntimeException(); }
  /** Filter RDD info to include only those with cached partitions */
  public  scala.collection.Seq<org.apache.spark.storage.RDDInfo> rddInfoList () { throw new RuntimeException(); }
  /** Update the storage info of the RDDs whose blocks are among the given updated blocks */
  private  void updateRDDInfo (scala.collection.Seq<scala.Tuple2<org.apache.spark.storage.BlockId, org.apache.spark.storage.BlockStatus>> updatedBlocks) { throw new RuntimeException(); }
  /**
   * Assumes the storage status list is fully up-to-date. This implies the corresponding
   * StorageStatusSparkListener must process the SparkListenerTaskEnd event before this listener.
   */
  public  void onTaskEnd (org.apache.spark.scheduler.SparkListenerTaskEnd taskEnd) { throw new RuntimeException(); }
  public  void onStageSubmitted (org.apache.spark.scheduler.SparkListenerStageSubmitted stageSubmitted) { throw new RuntimeException(); }
  public  void onStageCompleted (org.apache.spark.scheduler.SparkListenerStageCompleted stageCompleted) { throw new RuntimeException(); }
  public  void onUnpersistRDD (org.apache.spark.scheduler.SparkListenerUnpersistRDD unpersistRDD) { throw new RuntimeException(); }
}
