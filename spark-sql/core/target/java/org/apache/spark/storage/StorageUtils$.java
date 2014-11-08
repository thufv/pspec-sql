package org.apache.spark.storage;
// no position
/** Helper methods for storage-related objects. */
private  class StorageUtils$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final StorageUtils$ MODULE$ = null;
  public   StorageUtils$ () { throw new RuntimeException(); }
  /**
   * Update the given list of RDDInfo with the given list of storage statuses.
   * This method overwrites the old values stored in the RDDInfo's.
   */
  public  void updateRddInfo (scala.collection.Seq<org.apache.spark.storage.RDDInfo> rddInfos, scala.collection.Seq<org.apache.spark.storage.StorageStatus> statuses) { throw new RuntimeException(); }
  /**
   * Return a mapping from block ID to its locations for each block that belongs to the given RDD.
   */
  public  scala.collection.Map<org.apache.spark.storage.BlockId, scala.collection.Seq<java.lang.String>> getRddBlockLocations (int rddId, scala.collection.Seq<org.apache.spark.storage.StorageStatus> statuses) { throw new RuntimeException(); }
}
