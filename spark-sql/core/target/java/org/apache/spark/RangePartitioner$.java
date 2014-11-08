package org.apache.spark;
// no position
private  class RangePartitioner$ implements scala.Serializable {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final RangePartitioner$ MODULE$ = null;
  public   RangePartitioner$ () { throw new RuntimeException(); }
  /**
   * Sketches the input RDD via reservoir sampling on each partition.
   * <p>
   * @param rdd the input RDD to sketch
   * @param sampleSizePerPartition max sample size per partition
   * @return (total number of items, an array of (partitionId, number of items, sample))
   */
  public <K extends java.lang.Object> scala.Tuple2<java.lang.Object, scala.Tuple3<java.lang.Object, java.lang.Object, java.lang.Object>[]> sketch (org.apache.spark.rdd.RDD<K> rdd, int sampleSizePerPartition, scala.reflect.ClassTag<K> evidence$3) { throw new RuntimeException(); }
  /**
   * Determines the bounds for range partitioning from candidates with weights indicating how many
   * items each represents. Usually this is 1 over the probability used to sample this candidate.
   * <p>
   * @param candidates unordered candidates with weights
   * @param partitions number of partitions
   * @return selected bounds
   */
  public <K extends java.lang.Object> java.lang.Object determineBounds (scala.collection.mutable.ArrayBuffer<scala.Tuple2<K, java.lang.Object>> candidates, int partitions, scala.math.Ordering<K> evidence$4, scala.reflect.ClassTag<K> evidence$5) { throw new RuntimeException(); }
}
