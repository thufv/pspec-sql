package org.apache.spark.rdd;
/**
 * :: DeveloperApi ::
 * The resulting RDD from a shuffle (e.g. repartitioning of data).
 * @param prev the parent RDD.
 * @param part the partitioner used to partition the RDD
 * @tparam K the key class.
 * @tparam V the value class.
 * @tparam C the combiner class.
 */
public  class ShuffledRDD<K extends java.lang.Object, V extends java.lang.Object, C extends java.lang.Object> extends org.apache.spark.rdd.RDD<scala.Tuple2<K, C>> {
  public  Object prev () { throw new RuntimeException(); }
  // not preceding
  public   ShuffledRDD (org.apache.spark.rdd.RDD<? extends scala.Product2<K, V>> prev, org.apache.spark.Partitioner part) { throw new RuntimeException(); }
  private  scala.Option<org.apache.spark.serializer.Serializer> serializer () { throw new RuntimeException(); }
  private  scala.Option<scala.math.Ordering<K>> keyOrdering () { throw new RuntimeException(); }
  private  scala.Option<org.apache.spark.Aggregator<K, V, C>> aggregator () { throw new RuntimeException(); }
  private  boolean mapSideCombine () { throw new RuntimeException(); }
  /** Set a serializer for this RDD's shuffle, or null to use the default (spark.serializer) */
  public  org.apache.spark.rdd.ShuffledRDD<K, V, C> setSerializer (org.apache.spark.serializer.Serializer serializer) { throw new RuntimeException(); }
  /** Set key ordering for RDD's shuffle. */
  public  org.apache.spark.rdd.ShuffledRDD<K, V, C> setKeyOrdering (scala.math.Ordering<K> keyOrdering) { throw new RuntimeException(); }
  /** Set aggregator for RDD's shuffle. */
  public  org.apache.spark.rdd.ShuffledRDD<K, V, C> setAggregator (org.apache.spark.Aggregator<K, V, C> aggregator) { throw new RuntimeException(); }
  /** Set mapSideCombine flag for RDD's shuffle. */
  public  org.apache.spark.rdd.ShuffledRDD<K, V, C> setMapSideCombine (boolean mapSideCombine) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.Dependency<?>> getDependencies () { throw new RuntimeException(); }
  public  scala.Some<org.apache.spark.Partitioner> partitioner () { throw new RuntimeException(); }
  public  org.apache.spark.Partition[] getPartitions () { throw new RuntimeException(); }
  public  scala.collection.Iterator<scala.Tuple2<K, C>> compute (org.apache.spark.Partition split, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
  public  void clearDependencies () { throw new RuntimeException(); }
}
