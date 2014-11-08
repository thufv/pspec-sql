package org.apache.spark;
/**
 * An object that defines how the elements in a key-value pair RDD are partitioned by key.
 * Maps each key to a partition ID, from 0 to <code>numPartitions - 1</code>.
 */
public abstract class Partitioner implements scala.Serializable {
  /**
   * Choose a partitioner to use for a cogroup-like operation between a number of RDDs.
   * <p>
   * If any of the RDDs already has a partitioner, choose that one.
   * <p>
   * Otherwise, we use a default HashPartitioner. For the number of partitions, if
   * spark.default.parallelism is set, then we'll use the value from SparkContext
   * defaultParallelism, otherwise we'll use the max number of upstream partitions.
   * <p>
   * Unless spark.default.parallelism is set, the number of partitions will be the
   * same as the number of partitions in the largest upstream RDD, as this should
   * be least likely to cause out-of-memory errors.
   * <p>
   * We use two method parameters (rdd, others) to enforce callers passing at least 1 RDD.
   */
  static public  org.apache.spark.Partitioner defaultPartitioner (org.apache.spark.rdd.RDD<?> rdd, scala.collection.Seq<org.apache.spark.rdd.RDD<?>> others) { throw new RuntimeException(); }
  public   Partitioner () { throw new RuntimeException(); }
  public abstract  int numPartitions () ;
  public abstract  int getPartition (Object key) ;
}
