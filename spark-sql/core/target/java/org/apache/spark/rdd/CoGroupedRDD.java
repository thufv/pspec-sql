package org.apache.spark.rdd;
/**
 * :: DeveloperApi ::
 * A RDD that cogroups its parents. For each key k in parent RDDs, the resulting RDD contains a
 * tuple with the list of values for that key.
 * <p>
 * Note: This is an internal API. We recommend users use RDD.coGroup(...) instead of
 * instantiating this directly.
 * <p>
 * @param rdds parent RDDs.
 * @param part partitioner used to partition the shuffle output
 */
public  class CoGroupedRDD<K extends java.lang.Object> extends org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.collection.Iterable<java.lang.Object>[]>> {
  public  scala.collection.Seq<org.apache.spark.rdd.RDD<? extends scala.Product2<K, ?>>> rdds () { throw new RuntimeException(); }
  // not preceding
  public   CoGroupedRDD (scala.collection.Seq<org.apache.spark.rdd.RDD<? extends scala.Product2<K, ?>>> rdds, org.apache.spark.Partitioner part) { throw new RuntimeException(); }
  private  scala.Option<org.apache.spark.serializer.Serializer> serializer () { throw new RuntimeException(); }
  /** Set a serializer for this RDD's shuffle, or null to use the default (spark.serializer) */
  public  org.apache.spark.rdd.CoGroupedRDD<K> setSerializer (org.apache.spark.serializer.Serializer serializer) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.Dependency<?>> getDependencies () { throw new RuntimeException(); }
  public  org.apache.spark.Partition[] getPartitions () { throw new RuntimeException(); }
  public  scala.Some<org.apache.spark.Partitioner> partitioner () { throw new RuntimeException(); }
  public  scala.collection.Iterator<scala.Tuple2<K, scala.collection.Iterable<java.lang.Object>[]>> compute (org.apache.spark.Partition s, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
  private  org.apache.spark.util.collection.ExternalAppendOnlyMap<K, scala.Tuple2<java.lang.Object, java.lang.Object>, org.apache.spark.util.collection.CompactBuffer<java.lang.Object>[]> createExternalMap (int numRdds) { throw new RuntimeException(); }
  public  void clearDependencies () { throw new RuntimeException(); }
}
