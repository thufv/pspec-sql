package org.apache.spark.rdd;
/**
 * Class that captures a coalesced RDD by essentially keeping track of parent partitions
 * @param index of this coalesced partition
 * @param rdd which it belongs to
 * @param parentsIndices list of indices in the parent that have been coalesced into this partition
 * @param preferredLocation the preferred location for this partition
 */
private  class CoalescedRDDPartition implements org.apache.spark.Partition, scala.Product, scala.Serializable {
  public  int index () { throw new RuntimeException(); }
  public  Object rdd () { throw new RuntimeException(); }
  public  int[] parentsIndices () { throw new RuntimeException(); }
  public  java.lang.String preferredLocation () { throw new RuntimeException(); }
  // not preceding
  public   CoalescedRDDPartition (int index, org.apache.spark.rdd.RDD<?> rdd, int[] parentsIndices, java.lang.String preferredLocation) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.Partition> parents () { throw new RuntimeException(); }
  private  void writeObject (java.io.ObjectOutputStream oos) { throw new RuntimeException(); }
  /**
   * Computes the fraction of the parents' partitions containing preferredLocation within
   * their getPreferredLocs.
   * @return locality of this coalesced partition between 0 and 1
   */
  public  double localFraction () { throw new RuntimeException(); }
}
