package org.apache.spark.rdd;
/**
 * A RDD sampled from its parent RDD partition-wise. For each partition of the parent RDD,
 * a user-specified {@link org.apache.spark.util.random.RandomSampler} instance is used to obtain
 * a random sample of the records in the partition. The random seeds assigned to the samplers
 * are guaranteed to have different values.
 * <p>
 * @param prev RDD to be sampled
 * @param sampler a random sampler
 * @param preservesPartitioning whether the sampler preserves the partitioner of the parent RDD
 * @param seed random seed
 * @tparam T input RDD item type
 * @tparam U sampled RDD item type
 */
private  class PartitionwiseSampledRDD<T extends java.lang.Object, U extends java.lang.Object> extends org.apache.spark.rdd.RDD<U> {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   PartitionwiseSampledRDD (org.apache.spark.rdd.RDD<T> prev, org.apache.spark.util.random.RandomSampler<T, U> sampler, boolean preservesPartitioning, long seed, scala.reflect.ClassTag<T> evidence$1, scala.reflect.ClassTag<U> evidence$2) { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.Partitioner> partitioner () { throw new RuntimeException(); }
  public  org.apache.spark.Partition[] getPartitions () { throw new RuntimeException(); }
  public  scala.collection.Seq<java.lang.String> getPreferredLocations (org.apache.spark.Partition split) { throw new RuntimeException(); }
  public  scala.collection.Iterator<U> compute (org.apache.spark.Partition splitIn, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
}
