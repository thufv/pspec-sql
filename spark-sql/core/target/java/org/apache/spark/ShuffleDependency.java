package org.apache.spark;
/**
 * :: DeveloperApi ::
 * Represents a dependency on the output of a shuffle stage. Note that in the case of shuffle,
 * the RDD is transient since we don't need it on the executor side.
 * <p>
 * @param _rdd the parent RDD
 * @param partitioner partitioner used to partition the shuffle output
 * @param serializer {@link org.apache.spark.serializer.Serializer Serializer} to use. If set to None,
 *                   the default serializer, as specified by <code>spark.serializer</code> config option, will
 *                   be used.
 */
public  class ShuffleDependency<K extends java.lang.Object, V extends java.lang.Object, C extends java.lang.Object> extends org.apache.spark.Dependency<scala.Product2<K, V>> {
  public  org.apache.spark.Partitioner partitioner () { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.serializer.Serializer> serializer () { throw new RuntimeException(); }
  public  scala.Option<scala.math.Ordering<K>> keyOrdering () { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.Aggregator<K, V, C>> aggregator () { throw new RuntimeException(); }
  public  boolean mapSideCombine () { throw new RuntimeException(); }
  // not preceding
  public   ShuffleDependency (org.apache.spark.rdd.RDD<? extends scala.Product2<K, V>> _rdd, org.apache.spark.Partitioner partitioner, scala.Option<org.apache.spark.serializer.Serializer> serializer, scala.Option<scala.math.Ordering<K>> keyOrdering, scala.Option<org.apache.spark.Aggregator<K, V, C>> aggregator, boolean mapSideCombine) { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<scala.Product2<K, V>> rdd () { throw new RuntimeException(); }
  public  int shuffleId () { throw new RuntimeException(); }
  public  org.apache.spark.shuffle.ShuffleHandle shuffleHandle () { throw new RuntimeException(); }
}
