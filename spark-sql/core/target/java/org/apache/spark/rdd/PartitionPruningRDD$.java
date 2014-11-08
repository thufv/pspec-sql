package org.apache.spark.rdd;
// no position
public  class PartitionPruningRDD$ implements scala.Serializable {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final PartitionPruningRDD$ MODULE$ = null;
  public   PartitionPruningRDD$ () { throw new RuntimeException(); }
  /**
   * Create a PartitionPruningRDD. This function can be used to create the PartitionPruningRDD
   * when its type T is not known at compile time.
   */
  public <T extends java.lang.Object> org.apache.spark.rdd.PartitionPruningRDD<T> create (org.apache.spark.rdd.RDD<T> rdd, scala.Function1<java.lang.Object, java.lang.Object> partitionFilterFunc) { throw new RuntimeException(); }
}
