package org.apache.spark.rdd;
/**
 * Represents a coalesced RDD that has fewer partitions than its parent RDD
 * This class uses the PartitionCoalescer class to find a good partitioning of the parent RDD
 * so that each new partition has roughly the same number of parent partitions and that
 * the preferred location of each new partition overlaps with as many preferred locations of its
 * parent partitions
 * @param prev RDD to be coalesced
 * @param maxPartitions number of desired partitions in the coalesced RDD
 * @param balanceSlack used to trade-off balance and locality. 1.0 is all locality, 0 is all balance
 */
private  class CoalescedRDD<T extends java.lang.Object> extends org.apache.spark.rdd.RDD<T> {
  public  org.apache.spark.rdd.RDD<T> prev () { throw new RuntimeException(); }
  // not preceding
  public   CoalescedRDD (org.apache.spark.rdd.RDD<T> prev, int maxPartitions, double balanceSlack, scala.reflect.ClassTag<T> evidence$1) { throw new RuntimeException(); }
  public  org.apache.spark.Partition[] getPartitions () { throw new RuntimeException(); }
  public  scala.collection.Iterator<T> compute (org.apache.spark.Partition partition, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.Dependency<?>> getDependencies () { throw new RuntimeException(); }
  public  void clearDependencies () { throw new RuntimeException(); }
  /**
   * Returns the preferred machine for the partition. If split is of type CoalescedRDDPartition,
   * then the preferred machine will be one which most parent splits prefer too.
   * @param partition
   * @return the machine most preferred by split
   */
  public  scala.collection.Seq<java.lang.String> getPreferredLocations (org.apache.spark.Partition partition) { throw new RuntimeException(); }
}
