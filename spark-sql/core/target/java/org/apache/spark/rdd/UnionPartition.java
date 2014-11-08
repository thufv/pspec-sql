package org.apache.spark.rdd;
/**
 * Partition for UnionRDD.
 * <p>
 * @param idx index of the partition
 * @param rdd the parent RDD this partition refers to
 * @param parentRddIndex index of the parent RDD this partition refers to
 * @param parentRddPartitionIndex index of the partition within the parent RDD
 *                                this partition refers to
 */
private  class UnionPartition<T extends java.lang.Object> implements org.apache.spark.Partition {
  public  int parentRddIndex () { throw new RuntimeException(); }
  // not preceding
  public   UnionPartition (int idx, org.apache.spark.rdd.RDD<T> rdd, int parentRddIndex, int parentRddPartitionIndex, scala.reflect.ClassTag<T> evidence$1) { throw new RuntimeException(); }
  public  org.apache.spark.Partition parentPartition () { throw new RuntimeException(); }
  public  scala.collection.Seq<java.lang.String> preferredLocations () { throw new RuntimeException(); }
  public  int index () { throw new RuntimeException(); }
  private  void writeObject (java.io.ObjectOutputStream oos) { throw new RuntimeException(); }
}
