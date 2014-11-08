package org.apache.spark.rdd;
/**
 * This class contains all the information related to RDD checkpointing. Each instance of this
 * class is associated with a RDD. It manages process of checkpointing of the associated RDD,
 * as well as, manages the post-checkpoint state by providing the updated partitions,
 * iterator and preferred locations of the checkpointed RDD.
 */
private  class RDDCheckpointData<T extends java.lang.Object> implements org.apache.spark.Logging, scala.Serializable {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   RDDCheckpointData (org.apache.spark.rdd.RDD<T> rdd, scala.reflect.ClassTag<T> evidence$1) { throw new RuntimeException(); }
  public  scala.Enumeration.Value cpState () { throw new RuntimeException(); }
  public  scala.Option<java.lang.String> cpFile () { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.rdd.RDD<T>> cpRDD () { throw new RuntimeException(); }
  public  void markForCheckpoint () { throw new RuntimeException(); }
  public  boolean isCheckpointed () { throw new RuntimeException(); }
  public  scala.Option<java.lang.String> getCheckpointFile () { throw new RuntimeException(); }
  public  void doCheckpoint () { throw new RuntimeException(); }
  public  scala.collection.Seq<java.lang.String> getPreferredLocations (org.apache.spark.Partition split) { throw new RuntimeException(); }
  public  org.apache.spark.Partition[] getPartitions () { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.rdd.RDD<T>> checkpointRDD () { throw new RuntimeException(); }
}
