package org.apache.spark.api.java;
public  class JavaRDD<T extends java.lang.Object> implements org.apache.spark.api.java.JavaRDDLike<T, org.apache.spark.api.java.JavaRDD<T>> {
  static public <T extends java.lang.Object> org.apache.spark.api.java.JavaRDD<T> fromRDD (org.apache.spark.rdd.RDD<T> rdd, scala.reflect.ClassTag<T> evidence$1) { throw new RuntimeException(); }
  static public <T extends java.lang.Object> org.apache.spark.rdd.RDD<T> toRDD (org.apache.spark.api.java.JavaRDD<T> rdd) { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<T> rdd () { throw new RuntimeException(); }
  public  scala.reflect.ClassTag<T> classTag () { throw new RuntimeException(); }
  // not preceding
  public   JavaRDD (org.apache.spark.rdd.RDD<T> rdd, scala.reflect.ClassTag<T> classTag) { throw new RuntimeException(); }
  public  org.apache.spark.api.java.JavaRDD<T> wrapRDD (org.apache.spark.rdd.RDD<T> rdd) { throw new RuntimeException(); }
  /** Persist this RDD with the default storage level (`MEMORY_ONLY`). */
  public  org.apache.spark.api.java.JavaRDD<T> cache () { throw new RuntimeException(); }
  /**
   * Set this RDD's storage level to persist its values across operations after the first time
   * it is computed. This can only be used to assign a new storage level if the RDD does not
   * have a storage level set yet..
   */
  public  org.apache.spark.api.java.JavaRDD<T> persist (org.apache.spark.storage.StorageLevel newLevel) { throw new RuntimeException(); }
  /**
   * Mark the RDD as non-persistent, and remove all blocks for it from memory and disk.
   * This method blocks until all blocks are deleted.
   */
  public  org.apache.spark.api.java.JavaRDD<T> unpersist () { throw new RuntimeException(); }
  /**
   * Mark the RDD as non-persistent, and remove all blocks for it from memory and disk.
   * <p>
   * @param blocking Whether to block until all blocks are deleted.
   */
  public  org.apache.spark.api.java.JavaRDD<T> unpersist (boolean blocking) { throw new RuntimeException(); }
  /**
   * Return a new RDD containing the distinct elements in this RDD.
   */
  public  org.apache.spark.api.java.JavaRDD<T> distinct () { throw new RuntimeException(); }
  /**
   * Return a new RDD containing the distinct elements in this RDD.
   */
  public  org.apache.spark.api.java.JavaRDD<T> distinct (int numPartitions) { throw new RuntimeException(); }
  /**
   * Return a new RDD containing only the elements that satisfy a predicate.
   */
  public  org.apache.spark.api.java.JavaRDD<T> filter (org.apache.spark.api.java.function.Function<T, java.lang.Boolean> f) { throw new RuntimeException(); }
  /**
   * Return a new RDD that is reduced into <code>numPartitions</code> partitions.
   */
  public  org.apache.spark.api.java.JavaRDD<T> coalesce (int numPartitions) { throw new RuntimeException(); }
  /**
   * Return a new RDD that is reduced into <code>numPartitions</code> partitions.
   */
  public  org.apache.spark.api.java.JavaRDD<T> coalesce (int numPartitions, boolean shuffle) { throw new RuntimeException(); }
  /**
   * Return a new RDD that has exactly numPartitions partitions.
   * <p>
   * Can increase or decrease the level of parallelism in this RDD. Internally, this uses
   * a shuffle to redistribute data.
   * <p>
   * If you are decreasing the number of partitions in this RDD, consider using <code>coalesce</code>,
   * which can avoid performing a shuffle.
   */
  public  org.apache.spark.api.java.JavaRDD<T> repartition (int numPartitions) { throw new RuntimeException(); }
  /**
   * Return a sampled subset of this RDD.
   */
  public  org.apache.spark.api.java.JavaRDD<T> sample (boolean withReplacement, double fraction) { throw new RuntimeException(); }
  /**
   * Return a sampled subset of this RDD.
   */
  public  org.apache.spark.api.java.JavaRDD<T> sample (boolean withReplacement, double fraction, long seed) { throw new RuntimeException(); }
  /**
   * Randomly splits this RDD with the provided weights.
   * <p>
   * @param weights weights for splits, will be normalized if they don't sum to 1
   * <p>
   * @return split RDDs in an array
   */
  public  org.apache.spark.api.java.JavaRDD<T>[] randomSplit (double[] weights) { throw new RuntimeException(); }
  /**
   * Randomly splits this RDD with the provided weights.
   * <p>
   * @param weights weights for splits, will be normalized if they don't sum to 1
   * @param seed random seed
   * <p>
   * @return split RDDs in an array
   */
  public  org.apache.spark.api.java.JavaRDD<T>[] randomSplit (double[] weights, long seed) { throw new RuntimeException(); }
  /**
   * Return the union of this RDD and another one. Any identical elements will appear multiple
   * times (use <code>.distinct()</code> to eliminate them).
   */
  public  org.apache.spark.api.java.JavaRDD<T> union (org.apache.spark.api.java.JavaRDD<T> other) { throw new RuntimeException(); }
  /**
   * Return the intersection of this RDD and another one. The output will not contain any duplicate
   * elements, even if the input RDDs did.
   * <p>
   * Note that this method performs a shuffle internally.
   */
  public  org.apache.spark.api.java.JavaRDD<T> intersection (org.apache.spark.api.java.JavaRDD<T> other) { throw new RuntimeException(); }
  /**
   * Return an RDD with the elements from <code>this</code> that are not in <code>other</code>.
   * <p>
   * Uses <code>this</code> partitioner/partition size, because even if <code>other</code> is huge, the resulting
   * RDD will be <= us.
   */
  public  org.apache.spark.api.java.JavaRDD<T> subtract (org.apache.spark.api.java.JavaRDD<T> other) { throw new RuntimeException(); }
  /**
   * Return an RDD with the elements from <code>this</code> that are not in <code>other</code>.
   */
  public  org.apache.spark.api.java.JavaRDD<T> subtract (org.apache.spark.api.java.JavaRDD<T> other, int numPartitions) { throw new RuntimeException(); }
  /**
   * Return an RDD with the elements from <code>this</code> that are not in <code>other</code>.
   */
  public  org.apache.spark.api.java.JavaRDD<T> subtract (org.apache.spark.api.java.JavaRDD<T> other, org.apache.spark.Partitioner p) { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
  /** Assign a name to this RDD */
  public  org.apache.spark.api.java.JavaRDD<T> setName (java.lang.String name) { throw new RuntimeException(); }
  /**
   * Return this RDD sorted by the given key function.
   */
  public <S extends java.lang.Object> org.apache.spark.api.java.JavaRDD<T> sortBy (org.apache.spark.api.java.function.Function<T, S> f, boolean ascending, int numPartitions) { throw new RuntimeException(); }
}
