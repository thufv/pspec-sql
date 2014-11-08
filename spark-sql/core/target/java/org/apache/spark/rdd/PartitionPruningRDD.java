package org.apache.spark.rdd;
/**
 * :: DeveloperApi ::
 * A RDD used to prune RDD partitions/partitions so we can avoid launching tasks on
 * all partitions. An example use case: If we know the RDD is partitioned by range,
 * and the execution DAG has a filter on the key, we can avoid launching tasks
 * on partitions that don't have the range covering the key.
 */
public  class PartitionPruningRDD<T extends java.lang.Object> extends org.apache.spark.rdd.RDD<T> {
  /**
   * Create a PartitionPruningRDD. This function can be used to create the PartitionPruningRDD
   * when its type T is not known at compile time.
   */
  static public <T extends java.lang.Object> org.apache.spark.rdd.PartitionPruningRDD<T> create (org.apache.spark.rdd.RDD<T> rdd, scala.Function1<java.lang.Object, java.lang.Object> partitionFilterFunc) { throw new RuntimeException(); }
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   PartitionPruningRDD (org.apache.spark.rdd.RDD<T> prev, scala.Function1<java.lang.Object, java.lang.Object> partitionFilterFunc, scala.reflect.ClassTag<T> evidence$1) { throw new RuntimeException(); }
  public  scala.collection.Iterator<T> compute (org.apache.spark.Partition split, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
  protected  org.apache.spark.Partition[] getPartitions () { throw new RuntimeException(); }
}
