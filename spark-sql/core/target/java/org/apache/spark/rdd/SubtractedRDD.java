package org.apache.spark.rdd;
/**
 * An optimized version of cogroup for set difference/subtraction.
 * <p>
 * It is possible to implement this operation with just <code>cogroup</code>, but
 * that is less efficient because all of the entries from <code>rdd2</code>, for
 * both matching and non-matching values in <code>rdd1</code>, are kept in the
 * JHashMap until the end.
 * <p>
 * With this implementation, only the entries from <code>rdd1</code> are kept in-memory,
 * and the entries from <code>rdd2</code> are essentially streamed, as we only need to
 * touch each once to decide if the value needs to be removed.
 * <p>
 * This is particularly helpful when <code>rdd1</code> is much smaller than <code>rdd2</code>, as
 * you can use <code>rdd1</code>'s partitioner/partition size and not worry about running
 * out of memory because of the size of <code>rdd2</code>.
 */
private  class SubtractedRDD<K extends java.lang.Object, V extends java.lang.Object, W extends java.lang.Object> extends org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> {
  public  Object rdd1 () { throw new RuntimeException(); }
  public  Object rdd2 () { throw new RuntimeException(); }
  // not preceding
  public   SubtractedRDD (org.apache.spark.rdd.RDD<? extends scala.Product2<K, V>> rdd1, org.apache.spark.rdd.RDD<? extends scala.Product2<K, W>> rdd2, org.apache.spark.Partitioner part, scala.reflect.ClassTag<K> evidence$1, scala.reflect.ClassTag<V> evidence$2, scala.reflect.ClassTag<W> evidence$3) { throw new RuntimeException(); }
  private  scala.Option<org.apache.spark.serializer.Serializer> serializer () { throw new RuntimeException(); }
  /** Set a serializer for this RDD's shuffle, or null to use the default (spark.serializer) */
  public  org.apache.spark.rdd.SubtractedRDD<K, V, W> setSerializer (org.apache.spark.serializer.Serializer serializer) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.Dependency<?>> getDependencies () { throw new RuntimeException(); }
  public  org.apache.spark.Partition[] getPartitions () { throw new RuntimeException(); }
  public  scala.Some<org.apache.spark.Partitioner> partitioner () { throw new RuntimeException(); }
  public  scala.collection.Iterator<scala.Tuple2<K, V>> compute (org.apache.spark.Partition p, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
  public  void clearDependencies () { throw new RuntimeException(); }
}
