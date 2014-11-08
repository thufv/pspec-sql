package org.apache.spark.rdd;
/**
 * A Resilient Distributed Dataset (RDD), the basic abstraction in Spark. Represents an immutable,
 * partitioned collection of elements that can be operated on in parallel. This class contains the
 * basic operations available on all RDDs, such as <code>map</code>, <code>filter</code>, and <code>persist</code>. In addition,
 * {@link org.apache.spark.rdd.PairRDDFunctions} contains operations available only on RDDs of key-value
 * pairs, such as <code>groupByKey</code> and <code>join</code>;
 * {@link org.apache.spark.rdd.DoubleRDDFunctions} contains operations available only on RDDs of
 * Doubles; and
 * {@link org.apache.spark.rdd.SequenceFileRDDFunctions} contains operations available on RDDs that
 * can be saved as SequenceFiles.
 * These operations are automatically available on any RDD of the right type (e.g. RDD[(Int, Int)]
 * through implicit conversions when you <code>import org.apache.spark.SparkContext._</code>.
 * <p>
 * Internally, each RDD is characterized by five main properties:
 * <p>
 *  - A list of partitions
 *  - A function for computing each split
 *  - A list of dependencies on other RDDs
 *  - Optionally, a Partitioner for key-value RDDs (e.g. to say that the RDD is hash-partitioned)
 *  - Optionally, a list of preferred locations to compute each split on (e.g. block locations for
 *    an HDFS file)
 * <p>
 * All of the scheduling and execution in Spark is done based on these methods, allowing each RDD
 * to implement its own way of computing itself. Indeed, users can implement custom RDDs (e.g. for
 * reading data from a new storage system) by overriding these functions. Please refer to the
 * {@link http://www.cs.berkeley.edu/~matei/papers/2012/nsdi_spark.pdf Spark paper} for more details
 * on RDD internals.
 */
public abstract class RDD<T extends java.lang.Object> implements scala.Serializable, org.apache.spark.Logging {
  private  org.apache.spark.SparkContext sc () { throw new RuntimeException(); }
  private  scala.collection.Seq<org.apache.spark.Dependency<?>> deps () { throw new RuntimeException(); }
  // not preceding
  public   RDD (org.apache.spark.SparkContext sc, scala.collection.Seq<org.apache.spark.Dependency<?>> deps, scala.reflect.ClassTag<T> evidence$1) { throw new RuntimeException(); }
  /** Construct an RDD with just a one-to-one dependency on one parent */
  public   RDD (org.apache.spark.rdd.RDD<?> oneParent, scala.reflect.ClassTag<T> evidence$2) { throw new RuntimeException(); }
  private  org.apache.spark.SparkConf conf () { throw new RuntimeException(); }
  /**
   * :: DeveloperApi ::
   * Implemented by subclasses to compute a given partition.
   */
  public abstract  scala.collection.Iterator<T> compute (org.apache.spark.Partition split, org.apache.spark.TaskContext context) ;
  /**
   * Implemented by subclasses to return the set of partitions in this RDD. This method will only
   * be called once, so it is safe to implement a time-consuming computation in it.
   */
  protected abstract  org.apache.spark.Partition[] getPartitions () ;
  /**
   * Implemented by subclasses to return how this RDD depends on parent RDDs. This method will only
   * be called once, so it is safe to implement a time-consuming computation in it.
   */
  protected  scala.collection.Seq<org.apache.spark.Dependency<?>> getDependencies () { throw new RuntimeException(); }
  /**
   * Optionally overridden by subclasses to specify placement preferences.
   */
  protected  scala.collection.Seq<java.lang.String> getPreferredLocations (org.apache.spark.Partition split) { throw new RuntimeException(); }
  /** Optionally overridden by subclasses to specify how they are partitioned. */
  public  scala.Option<org.apache.spark.Partitioner> partitioner () { throw new RuntimeException(); }
  /** The SparkContext that created this RDD. */
  public  org.apache.spark.SparkContext sparkContext () { throw new RuntimeException(); }
  /** A unique ID for this RDD (within its SparkContext). */
  public  int id () { throw new RuntimeException(); }
  /** A friendly name for this RDD */
  public  java.lang.String name () { throw new RuntimeException(); }
  /** Assign a name to this RDD */
  public  org.apache.spark.rdd.RDD<T> setName (java.lang.String _name) { throw new RuntimeException(); }
  /**
   * Set this RDD's storage level to persist its values across operations after the first time
   * it is computed. This can only be used to assign a new storage level if the RDD does not
   * have a storage level set yet..
   */
  public  org.apache.spark.rdd.RDD<T> persist (org.apache.spark.storage.StorageLevel newLevel) { throw new RuntimeException(); }
  /** Persist this RDD with the default storage level (`MEMORY_ONLY`). */
  public  org.apache.spark.rdd.RDD<T> persist () { throw new RuntimeException(); }
  /** Persist this RDD with the default storage level (`MEMORY_ONLY`). */
  public  org.apache.spark.rdd.RDD<T> cache () { throw new RuntimeException(); }
  /**
   * Mark the RDD as non-persistent, and remove all blocks for it from memory and disk.
   * <p>
   * @param blocking Whether to block until all blocks are deleted.
   * @return This RDD.
   */
  public  org.apache.spark.rdd.RDD<T> unpersist (boolean blocking) { throw new RuntimeException(); }
  /** Get the RDD's current storage level, or StorageLevel.NONE if none is set. */
  public  org.apache.spark.storage.StorageLevel getStorageLevel () { throw new RuntimeException(); }
  private  scala.collection.Seq<org.apache.spark.Dependency<?>> dependencies_ () { throw new RuntimeException(); }
  private  org.apache.spark.Partition[] partitions_ () { throw new RuntimeException(); }
  /** An Option holding our checkpoint RDD, if we are checkpointed */
  private  scala.Option<org.apache.spark.rdd.RDD<T>> checkpointRDD () { throw new RuntimeException(); }
  /**
   * Get the list of dependencies of this RDD, taking into account whether the
   * RDD is checkpointed or not.
   */
  public final  scala.collection.Seq<org.apache.spark.Dependency<?>> dependencies () { throw new RuntimeException(); }
  /**
   * Get the array of partitions of this RDD, taking into account whether the
   * RDD is checkpointed or not.
   */
  public final  org.apache.spark.Partition[] partitions () { throw new RuntimeException(); }
  /**
   * Get the preferred locations of a partition (as hostnames), taking into account whether the
   * RDD is checkpointed.
   */
  public final  scala.collection.Seq<java.lang.String> preferredLocations (org.apache.spark.Partition split) { throw new RuntimeException(); }
  /**
   * Internal method to this RDD; will read from cache if applicable, or otherwise compute it.
   * This should ''not'' be called by users directly, but is available for implementors of custom
   * subclasses of RDD.
   */
  public final  scala.collection.Iterator<T> iterator (org.apache.spark.Partition split, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
  /**
   * Return the ancestors of the given RDD that are related to it only through a sequence of
   * narrow dependencies. This traverses the given RDD's dependency tree using DFS, but maintains
   * no ordering on the RDDs returned.
   */
  private  scala.collection.Seq<org.apache.spark.rdd.RDD<?>> getNarrowAncestors () { throw new RuntimeException(); }
  /**
   * Compute an RDD partition or read it from a checkpoint if the RDD is checkpointing.
   */
  private  scala.collection.Iterator<T> computeOrReadCheckpoint (org.apache.spark.Partition split, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
  /**
   * Return a new RDD by applying a function to all elements of this RDD.
   */
  public <U extends java.lang.Object> org.apache.spark.rdd.RDD<U> map (scala.Function1<T, U> f, scala.reflect.ClassTag<U> evidence$3) { throw new RuntimeException(); }
  /**
   *  Return a new RDD by first applying a function to all elements of this
   *  RDD, and then flattening the results.
   */
  public <U extends java.lang.Object> org.apache.spark.rdd.RDD<U> flatMap (scala.Function1<T, scala.collection.TraversableOnce<U>> f, scala.reflect.ClassTag<U> evidence$4) { throw new RuntimeException(); }
  /**
   * Return a new RDD containing only the elements that satisfy a predicate.
   */
  public  org.apache.spark.rdd.RDD<T> filter (scala.Function1<T, java.lang.Object> f) { throw new RuntimeException(); }
  /**
   * Return a new RDD containing the distinct elements in this RDD.
   */
  public  org.apache.spark.rdd.RDD<T> distinct (int numPartitions, scala.math.Ordering<T> ord) { throw new RuntimeException(); }
  /**
   * Return a new RDD containing the distinct elements in this RDD.
   */
  public  org.apache.spark.rdd.RDD<T> distinct () { throw new RuntimeException(); }
  /**
   * Return a new RDD that has exactly numPartitions partitions.
   * <p>
   * Can increase or decrease the level of parallelism in this RDD. Internally, this uses
   * a shuffle to redistribute data.
   * <p>
   * If you are decreasing the number of partitions in this RDD, consider using <code>coalesce</code>,
   * which can avoid performing a shuffle.
   */
  public  org.apache.spark.rdd.RDD<T> repartition (int numPartitions, scala.math.Ordering<T> ord) { throw new RuntimeException(); }
  /**
   * Return a new RDD that is reduced into <code>numPartitions</code> partitions.
   * <p>
   * This results in a narrow dependency, e.g. if you go from 1000 partitions
   * to 100 partitions, there will not be a shuffle, instead each of the 100
   * new partitions will claim 10 of the current partitions.
   * <p>
   * However, if you're doing a drastic coalesce, e.g. to numPartitions = 1,
   * this may result in your computation taking place on fewer nodes than
   * you like (e.g. one node in the case of numPartitions = 1). To avoid this,
   * you can pass shuffle = true. This will add a shuffle step, but means the
   * current upstream partitions will be executed in parallel (per whatever
   * the current partitioning is).
   * <p>
   * Note: With shuffle = true, you can actually coalesce to a larger number
   * of partitions. This is useful if you have a small number of partitions,
   * say 100, potentially with a few partitions being abnormally large. Calling
   * coalesce(1000, shuffle = true) will result in 1000 partitions with the
   * data distributed using a hash partitioner.
   */
  public  org.apache.spark.rdd.RDD<T> coalesce (int numPartitions, boolean shuffle, scala.math.Ordering<T> ord) { throw new RuntimeException(); }
  /**
   * Return a sampled subset of this RDD.
   */
  public  org.apache.spark.rdd.RDD<T> sample (boolean withReplacement, double fraction, long seed) { throw new RuntimeException(); }
  /**
   * Randomly splits this RDD with the provided weights.
   * <p>
   * @param weights weights for splits, will be normalized if they don't sum to 1
   * @param seed random seed
   * <p>
   * @return split RDDs in an array
   */
  public  org.apache.spark.rdd.RDD<T>[] randomSplit (double[] weights, long seed) { throw new RuntimeException(); }
  /**
   * Return a fixed-size sampled subset of this RDD in an array
   * <p>
   * @param withReplacement whether sampling is done with replacement
   * @param num size of the returned sample
   * @param seed seed for the random number generator
   * @return sample of specified size in an array
   */
  public  java.lang.Object takeSample (boolean withReplacement, int num, long seed) { throw new RuntimeException(); }
  /**
   * Return the union of this RDD and another one. Any identical elements will appear multiple
   * times (use <code>.distinct()</code> to eliminate them).
   */
  public  org.apache.spark.rdd.RDD<T> union (org.apache.spark.rdd.RDD<T> other) { throw new RuntimeException(); }
  /**
   * Return this RDD sorted by the given key function.
   */
  public <K extends java.lang.Object> org.apache.spark.rdd.RDD<T> sortBy (scala.Function1<T, K> f, boolean ascending, int numPartitions, scala.math.Ordering<K> ord, scala.reflect.ClassTag<K> ctag) { throw new RuntimeException(); }
  /**
   * Return the intersection of this RDD and another one. The output will not contain any duplicate
   * elements, even if the input RDDs did.
   * <p>
   * Note that this method performs a shuffle internally.
   */
  public  org.apache.spark.rdd.RDD<T> intersection (org.apache.spark.rdd.RDD<T> other) { throw new RuntimeException(); }
  /**
   * Return the intersection of this RDD and another one. The output will not contain any duplicate
   * elements, even if the input RDDs did.
   * <p>
   * Note that this method performs a shuffle internally.
   * <p>
   * @param partitioner Partitioner to use for the resulting RDD
   */
  public  org.apache.spark.rdd.RDD<T> intersection (org.apache.spark.rdd.RDD<T> other, org.apache.spark.Partitioner partitioner, scala.math.Ordering<T> ord) { throw new RuntimeException(); }
  /**
   * Return the intersection of this RDD and another one. The output will not contain any duplicate
   * elements, even if the input RDDs did.  Performs a hash partition across the cluster
   * <p>
   * Note that this method performs a shuffle internally.
   * <p>
   * @param numPartitions How many partitions to use in the resulting RDD
   */
  public  org.apache.spark.rdd.RDD<T> intersection (org.apache.spark.rdd.RDD<T> other, int numPartitions) { throw new RuntimeException(); }
  /**
   * Return an RDD created by coalescing all elements within each partition into an array.
   */
  public  org.apache.spark.rdd.RDD<java.lang.Object> glom () { throw new RuntimeException(); }
  /**
   * Return the Cartesian product of this RDD and another one, that is, the RDD of all pairs of
   * elements (a, b) where a is in <code>this</code> and b is in <code>other</code>.
   */
  public <U extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<T, U>> cartesian (org.apache.spark.rdd.RDD<U> other, scala.reflect.ClassTag<U> evidence$5) { throw new RuntimeException(); }
  /**
   * Return an RDD of grouped items. Each group consists of a key and a sequence of elements
   * mapping to that key.
   * <p>
   * Note: This operation may be very expensive. If you are grouping in order to perform an
   * aggregation (such as a sum or average) over each key, using {@link PairRDDFunctions.aggregateByKey}
   * or {@link PairRDDFunctions.reduceByKey} will provide much better performance.
   */
  public <K extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.collection.Iterable<T>>> groupBy (scala.Function1<T, K> f, scala.reflect.ClassTag<K> kt) { throw new RuntimeException(); }
  /**
   * Return an RDD of grouped elements. Each group consists of a key and a sequence of elements
   * mapping to that key.
   * <p>
   * Note: This operation may be very expensive. If you are grouping in order to perform an
   * aggregation (such as a sum or average) over each key, using {@link PairRDDFunctions.aggregateByKey}
   * or {@link PairRDDFunctions.reduceByKey} will provide much better performance.
   */
  public <K extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.collection.Iterable<T>>> groupBy (scala.Function1<T, K> f, int numPartitions, scala.reflect.ClassTag<K> kt) { throw new RuntimeException(); }
  /**
   * Return an RDD of grouped items. Each group consists of a key and a sequence of elements
   * mapping to that key.
   * <p>
   * Note: This operation may be very expensive. If you are grouping in order to perform an
   * aggregation (such as a sum or average) over each key, using {@link PairRDDFunctions.aggregateByKey}
   * or {@link PairRDDFunctions.reduceByKey} will provide much better performance.
   */
  public <K extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.collection.Iterable<T>>> groupBy (scala.Function1<T, K> f, org.apache.spark.Partitioner p, scala.reflect.ClassTag<K> kt, scala.math.Ordering<K> ord) { throw new RuntimeException(); }
  /**
   * Return an RDD created by piping elements to a forked external process.
   */
  public  org.apache.spark.rdd.RDD<java.lang.String> pipe (java.lang.String command) { throw new RuntimeException(); }
  /**
   * Return an RDD created by piping elements to a forked external process.
   */
  public  org.apache.spark.rdd.RDD<java.lang.String> pipe (java.lang.String command, scala.collection.Map<java.lang.String, java.lang.String> env) { throw new RuntimeException(); }
  /**
   * Return an RDD created by piping elements to a forked external process.
   * The print behavior can be customized by providing two functions.
   * <p>
   * @param command command to run in forked process.
   * @param env environment variables to set.
   * @param printPipeContext Before piping elements, this function is called as an oppotunity
   *                         to pipe context data. Print line function (like out.println) will be
   *                         passed as printPipeContext's parameter.
   * @param printRDDElement Use this function to customize how to pipe elements. This function
   *                        will be called with each RDD element as the 1st parameter, and the
   *                        print line function (like out.println()) as the 2nd parameter.
   *                        An example of pipe the RDD data of groupBy() in a streaming way,
   *                        instead of constructing a huge String to concat all the elements:
   *                        def printRDDElement(record:(String, Seq[String]), f:String=>Unit) =
   *                          for (e <- record._2){f(e)}
   * @param separateWorkingDir Use separate working directories for each task.
   * @return the result RDD
   */
  public  org.apache.spark.rdd.RDD<java.lang.String> pipe (scala.collection.Seq<java.lang.String> command, scala.collection.Map<java.lang.String, java.lang.String> env, scala.Function1<scala.Function1<java.lang.String, scala.runtime.BoxedUnit>, scala.runtime.BoxedUnit> printPipeContext, scala.Function2<T, scala.Function1<java.lang.String, scala.runtime.BoxedUnit>, scala.runtime.BoxedUnit> printRDDElement, boolean separateWorkingDir) { throw new RuntimeException(); }
  /**
   * Return a new RDD by applying a function to each partition of this RDD.
   * <p>
   * <code>preservesPartitioning</code> indicates whether the input function preserves the partitioner, which
   * should be <code>false</code> unless this is a pair RDD and the input function doesn't modify the keys.
   */
  public <U extends java.lang.Object> org.apache.spark.rdd.RDD<U> mapPartitions (scala.Function1<scala.collection.Iterator<T>, scala.collection.Iterator<U>> f, boolean preservesPartitioning, scala.reflect.ClassTag<U> evidence$6) { throw new RuntimeException(); }
  /**
   * Return a new RDD by applying a function to each partition of this RDD, while tracking the index
   * of the original partition.
   * <p>
   * <code>preservesPartitioning</code> indicates whether the input function preserves the partitioner, which
   * should be <code>false</code> unless this is a pair RDD and the input function doesn't modify the keys.
   */
  public <U extends java.lang.Object> org.apache.spark.rdd.RDD<U> mapPartitionsWithIndex (scala.Function2<java.lang.Object, scala.collection.Iterator<T>, scala.collection.Iterator<U>> f, boolean preservesPartitioning, scala.reflect.ClassTag<U> evidence$7) { throw new RuntimeException(); }
  /**
   * :: DeveloperApi ::
   * Return a new RDD by applying a function to each partition of this RDD. This is a variant of
   * mapPartitions that also passes the TaskContext into the closure.
   * <p>
   * <code>preservesPartitioning</code> indicates whether the input function preserves the partitioner, which
   * should be <code>false</code> unless this is a pair RDD and the input function doesn't modify the keys.
   */
  public <U extends java.lang.Object> org.apache.spark.rdd.RDD<U> mapPartitionsWithContext (scala.Function2<org.apache.spark.TaskContext, scala.collection.Iterator<T>, scala.collection.Iterator<U>> f, boolean preservesPartitioning, scala.reflect.ClassTag<U> evidence$8) { throw new RuntimeException(); }
  /**
   * Return a new RDD by applying a function to each partition of this RDD, while tracking the index
   * of the original partition.
   */
  public <U extends java.lang.Object> org.apache.spark.rdd.RDD<U> mapPartitionsWithSplit (scala.Function2<java.lang.Object, scala.collection.Iterator<T>, scala.collection.Iterator<U>> f, boolean preservesPartitioning, scala.reflect.ClassTag<U> evidence$9) { throw new RuntimeException(); }
  /**
   * Maps f over this RDD, where f takes an additional parameter of type A.  This
   * additional parameter is produced by constructA, which is called in each
   * partition with the index of that partition.
   */
  public <A extends java.lang.Object, U extends java.lang.Object> org.apache.spark.rdd.RDD<U> mapWith (scala.Function1<java.lang.Object, A> constructA, boolean preservesPartitioning, scala.Function2<T, A, U> f, scala.reflect.ClassTag<U> evidence$10) { throw new RuntimeException(); }
  /**
   * FlatMaps f over this RDD, where f takes an additional parameter of type A.  This
   * additional parameter is produced by constructA, which is called in each
   * partition with the index of that partition.
   */
  public <A extends java.lang.Object, U extends java.lang.Object> org.apache.spark.rdd.RDD<U> flatMapWith (scala.Function1<java.lang.Object, A> constructA, boolean preservesPartitioning, scala.Function2<T, A, scala.collection.Seq<U>> f, scala.reflect.ClassTag<U> evidence$11) { throw new RuntimeException(); }
  /**
   * Applies f to each element of this RDD, where f takes an additional parameter of type A.
   * This additional parameter is produced by constructA, which is called in each
   * partition with the index of that partition.
   */
  public <A extends java.lang.Object> void foreachWith (scala.Function1<java.lang.Object, A> constructA, scala.Function2<T, A, scala.runtime.BoxedUnit> f) { throw new RuntimeException(); }
  /**
   * Filters this RDD with p, where p takes an additional parameter of type A.  This
   * additional parameter is produced by constructA, which is called in each
   * partition with the index of that partition.
   */
  public <A extends java.lang.Object> org.apache.spark.rdd.RDD<T> filterWith (scala.Function1<java.lang.Object, A> constructA, scala.Function2<T, A, java.lang.Object> p) { throw new RuntimeException(); }
  /**
   * Zips this RDD with another one, returning key-value pairs with the first element in each RDD,
   * second element in each RDD, etc. Assumes that the two RDDs have the *same number of
   * partitions* and the *same number of elements in each partition* (e.g. one was made through
   * a map on the other).
   */
  public <U extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<T, U>> zip (org.apache.spark.rdd.RDD<U> other, scala.reflect.ClassTag<U> evidence$12) { throw new RuntimeException(); }
  /**
   * Zip this RDD's partitions with one (or more) RDD(s) and return a new RDD by
   * applying a function to the zipped partitions. Assumes that all the RDDs have the
   * *same number of partitions*, but does *not* require them to have the same number
   * of elements in each partition.
   */
  public <B extends java.lang.Object, V extends java.lang.Object> org.apache.spark.rdd.RDD<V> zipPartitions (org.apache.spark.rdd.RDD<B> rdd2, boolean preservesPartitioning, scala.Function2<scala.collection.Iterator<T>, scala.collection.Iterator<B>, scala.collection.Iterator<V>> f, scala.reflect.ClassTag<B> evidence$13, scala.reflect.ClassTag<V> evidence$14) { throw new RuntimeException(); }
  public <B extends java.lang.Object, V extends java.lang.Object> org.apache.spark.rdd.RDD<V> zipPartitions (org.apache.spark.rdd.RDD<B> rdd2, scala.Function2<scala.collection.Iterator<T>, scala.collection.Iterator<B>, scala.collection.Iterator<V>> f, scala.reflect.ClassTag<B> evidence$15, scala.reflect.ClassTag<V> evidence$16) { throw new RuntimeException(); }
  public <B extends java.lang.Object, C extends java.lang.Object, V extends java.lang.Object> org.apache.spark.rdd.RDD<V> zipPartitions (org.apache.spark.rdd.RDD<B> rdd2, org.apache.spark.rdd.RDD<C> rdd3, boolean preservesPartitioning, scala.Function3<scala.collection.Iterator<T>, scala.collection.Iterator<B>, scala.collection.Iterator<C>, scala.collection.Iterator<V>> f, scala.reflect.ClassTag<B> evidence$17, scala.reflect.ClassTag<C> evidence$18, scala.reflect.ClassTag<V> evidence$19) { throw new RuntimeException(); }
  public <B extends java.lang.Object, C extends java.lang.Object, V extends java.lang.Object> org.apache.spark.rdd.RDD<V> zipPartitions (org.apache.spark.rdd.RDD<B> rdd2, org.apache.spark.rdd.RDD<C> rdd3, scala.Function3<scala.collection.Iterator<T>, scala.collection.Iterator<B>, scala.collection.Iterator<C>, scala.collection.Iterator<V>> f, scala.reflect.ClassTag<B> evidence$20, scala.reflect.ClassTag<C> evidence$21, scala.reflect.ClassTag<V> evidence$22) { throw new RuntimeException(); }
  public <B extends java.lang.Object, C extends java.lang.Object, D extends java.lang.Object, V extends java.lang.Object> org.apache.spark.rdd.RDD<V> zipPartitions (org.apache.spark.rdd.RDD<B> rdd2, org.apache.spark.rdd.RDD<C> rdd3, org.apache.spark.rdd.RDD<D> rdd4, boolean preservesPartitioning, scala.Function4<scala.collection.Iterator<T>, scala.collection.Iterator<B>, scala.collection.Iterator<C>, scala.collection.Iterator<D>, scala.collection.Iterator<V>> f, scala.reflect.ClassTag<B> evidence$23, scala.reflect.ClassTag<C> evidence$24, scala.reflect.ClassTag<D> evidence$25, scala.reflect.ClassTag<V> evidence$26) { throw new RuntimeException(); }
  public <B extends java.lang.Object, C extends java.lang.Object, D extends java.lang.Object, V extends java.lang.Object> org.apache.spark.rdd.RDD<V> zipPartitions (org.apache.spark.rdd.RDD<B> rdd2, org.apache.spark.rdd.RDD<C> rdd3, org.apache.spark.rdd.RDD<D> rdd4, scala.Function4<scala.collection.Iterator<T>, scala.collection.Iterator<B>, scala.collection.Iterator<C>, scala.collection.Iterator<D>, scala.collection.Iterator<V>> f, scala.reflect.ClassTag<B> evidence$27, scala.reflect.ClassTag<C> evidence$28, scala.reflect.ClassTag<D> evidence$29, scala.reflect.ClassTag<V> evidence$30) { throw new RuntimeException(); }
  /**
   * Applies a function f to all elements of this RDD.
   */
  public  void foreach (scala.Function1<T, scala.runtime.BoxedUnit> f) { throw new RuntimeException(); }
  /**
   * Applies a function f to each partition of this RDD.
   */
  public  void foreachPartition (scala.Function1<scala.collection.Iterator<T>, scala.runtime.BoxedUnit> f) { throw new RuntimeException(); }
  /**
   * Return an array that contains all of the elements in this RDD.
   */
  public  java.lang.Object collect () { throw new RuntimeException(); }
  /**
   * Return an iterator that contains all of the elements in this RDD.
   * <p>
   * The iterator will consume as much memory as the largest partition in this RDD.
   */
  public  scala.collection.Iterator<T> toLocalIterator () { throw new RuntimeException(); }
  /**
   * Return an array that contains all of the elements in this RDD.
   */
  public  java.lang.Object toArray () { throw new RuntimeException(); }
  /**
   * Return an RDD that contains all matching values by applying <code>f</code>.
   */
  public <U extends java.lang.Object> org.apache.spark.rdd.RDD<U> collect (scala.PartialFunction<T, U> f, scala.reflect.ClassTag<U> evidence$31) { throw new RuntimeException(); }
  /**
   * Return an RDD with the elements from <code>this</code> that are not in <code>other</code>.
   * <p>
   * Uses <code>this</code> partitioner/partition size, because even if <code>other</code> is huge, the resulting
   * RDD will be <= us.
   */
  public  org.apache.spark.rdd.RDD<T> subtract (org.apache.spark.rdd.RDD<T> other) { throw new RuntimeException(); }
  /**
   * Return an RDD with the elements from <code>this</code> that are not in <code>other</code>.
   */
  public  org.apache.spark.rdd.RDD<T> subtract (org.apache.spark.rdd.RDD<T> other, int numPartitions) { throw new RuntimeException(); }
  /**
   * Return an RDD with the elements from <code>this</code> that are not in <code>other</code>.
   */
  public  org.apache.spark.rdd.RDD<T> subtract (org.apache.spark.rdd.RDD<T> other, org.apache.spark.Partitioner p, scala.math.Ordering<T> ord) { throw new RuntimeException(); }
  /**
   * Reduces the elements of this RDD using the specified commutative and
   * associative binary operator.
   */
  public  T reduce (scala.Function2<T, T, T> f) { throw new RuntimeException(); }
  /**
   * Aggregate the elements of each partition, and then the results for all the partitions, using a
   * given associative function and a neutral "zero value". The function op(t1, t2) is allowed to
   * modify t1 and return it as its result value to avoid object allocation; however, it should not
   * modify t2.
   */
  public  T fold (T zeroValue, scala.Function2<T, T, T> op) { throw new RuntimeException(); }
  /**
   * Aggregate the elements of each partition, and then the results for all the partitions, using
   * given combine functions and a neutral "zero value". This function can return a different result
   * type, U, than the type of this RDD, T. Thus, we need one operation for merging a T into an U
   * and one operation for merging two U's, as in scala.TraversableOnce. Both of these functions are
   * allowed to modify and return their first argument instead of creating a new U to avoid memory
   * allocation.
   */
  public <U extends java.lang.Object> U aggregate (U zeroValue, scala.Function2<U, T, U> seqOp, scala.Function2<U, U, U> combOp, scala.reflect.ClassTag<U> evidence$32) { throw new RuntimeException(); }
  /**
   * Return the number of elements in the RDD.
   */
  public  long count () { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Approximate version of count() that returns a potentially incomplete result
   * within a timeout, even if not all tasks have finished.
   */
  public  org.apache.spark.partial.PartialResult<org.apache.spark.partial.BoundedDouble> countApprox (long timeout, double confidence) { throw new RuntimeException(); }
  /**
   * Return the count of each unique value in this RDD as a map of (value, count) pairs. The final
   * combine step happens locally on the master, equivalent to running a single reduce task.
   */
  public  scala.collection.Map<T, java.lang.Object> countByValue (scala.math.Ordering<T> ord) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Approximate version of countByValue().
   */
  public  org.apache.spark.partial.PartialResult<scala.collection.Map<T, org.apache.spark.partial.BoundedDouble>> countByValueApprox (long timeout, double confidence, scala.math.Ordering<T> ord) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Return approximate number of distinct elements in the RDD.
   * <p>
   * The algorithm used is based on streamlib's implementation of "HyperLogLog in Practice:
   * Algorithmic Engineering of a State of The Art Cardinality Estimation Algorithm", available
   * <a href="http://dx.doi.org/10.1145/2452376.2452456">here</a>.
   * <p>
   * The relative accuracy is approximately <code>1.054 / sqrt(2^p)</code>. Setting a nonzero <code>sp > p</code>
   * would trigger sparse representation of registers, which may reduce the memory consumption
   * and increase accuracy when the cardinality is small.
   * <p>
   * @param p The precision value for the normal set.
   *          <code>p</code> must be a value between 4 and <code>sp</code> if <code>sp</code> is not zero (32 max).
   * @param sp The precision value for the sparse set, between 0 and 32.
   *           If <code>sp</code> equals 0, the sparse representation is skipped.
   */
  public  long countApproxDistinct (int p, int sp) { throw new RuntimeException(); }
  /**
   * Return approximate number of distinct elements in the RDD.
   * <p>
   * The algorithm used is based on streamlib's implementation of "HyperLogLog in Practice:
   * Algorithmic Engineering of a State of The Art Cardinality Estimation Algorithm", available
   * <a href="http://dx.doi.org/10.1145/2452376.2452456">here</a>.
   * <p>
   * @param relativeSD Relative accuracy. Smaller values create counters that require more space.
   *                   It must be greater than 0.000017.
   */
  public  long countApproxDistinct (double relativeSD) { throw new RuntimeException(); }
  /**
   * Zips this RDD with its element indices. The ordering is first based on the partition index
   * and then the ordering of items within each partition. So the first item in the first
   * partition gets index 0, and the last item in the last partition receives the largest index.
   * This is similar to Scala's zipWithIndex but it uses Long instead of Int as the index type.
   * This method needs to trigger a spark job when this RDD contains more than one partitions.
   */
  public  org.apache.spark.rdd.RDD<scala.Tuple2<T, java.lang.Object>> zipWithIndex () { throw new RuntimeException(); }
  /**
   * Zips this RDD with generated unique Long ids. Items in the kth partition will get ids k, n+k,
   * 2*n+k, ..., where n is the number of partitions. So there may exist gaps, but this method
   * won't trigger a spark job, which is different from {@link org.apache.spark.rdd.RDD#zipWithIndex}.
   */
  public  org.apache.spark.rdd.RDD<scala.Tuple2<T, java.lang.Object>> zipWithUniqueId () { throw new RuntimeException(); }
  /**
   * Take the first num elements of the RDD. It works by first scanning one partition, and use the
   * results from that partition to estimate the number of additional partitions needed to satisfy
   * the limit.
   */
  public  java.lang.Object take (int num) { throw new RuntimeException(); }
  /**
   * Return the first element in this RDD.
   */
  public  T first () { throw new RuntimeException(); }
  // not preceding
  public  java.lang.Object top (int num, scala.math.Ordering<T> ord) { throw new RuntimeException(); }
  /**
   * Returns the first K (smallest) elements from this RDD as defined by the specified
   * implicit Ordering[T] and maintains the ordering. This does the opposite of {@link top}.
   * For example:
   * <pre><code>
   *   sc.parallelize(Seq(10, 4, 2, 12, 3)).takeOrdered(1)
   *   // returns Array(2)
   *
   *   sc.parallelize(Seq(2, 3, 4, 5, 6)).takeOrdered(2)
   *   // returns Array(2, 3)
   * </code></pre>
   * <p>
   * @param num the number of top elements to return
   * @param ord the implicit ordering for T
   * @return an array of top elements
   */
  public  java.lang.Object takeOrdered (int num, scala.math.Ordering<T> ord) { throw new RuntimeException(); }
  /**
   * Returns the max of this RDD as defined by the implicit Ordering[T].
   * @return the maximum element of the RDD
   * */
  public  T max (scala.math.Ordering<T> ord) { throw new RuntimeException(); }
  /**
   * Returns the min of this RDD as defined by the implicit Ordering[T].
   * @return the minimum element of the RDD
   * */
  public  T min (scala.math.Ordering<T> ord) { throw new RuntimeException(); }
  /**
   * Save this RDD as a text file, using string representations of elements.
   */
  public  void saveAsTextFile (java.lang.String path) { throw new RuntimeException(); }
  /**
   * Save this RDD as a compressed text file, using string representations of elements.
   */
  public  void saveAsTextFile (java.lang.String path, java.lang.Class<? extends org.apache.hadoop.io.compress.CompressionCodec> codec) { throw new RuntimeException(); }
  /**
   * Save this RDD as a SequenceFile of serialized objects.
   */
  public  void saveAsObjectFile (java.lang.String path) { throw new RuntimeException(); }
  /**
   * Creates tuples of the elements in this RDD by applying <code>f</code>.
   */
  public <K extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, T>> keyBy (scala.Function1<T, K> f) { throw new RuntimeException(); }
  /** A private method for tests, to look at the contents of each partition */
  private  java.lang.Object[] collectPartitions () { throw new RuntimeException(); }
  /**
   * Mark this RDD for checkpointing. It will be saved to a file inside the checkpoint
   * directory set with SparkContext.setCheckpointDir() and all references to its parent
   * RDDs will be removed. This function must be called before any job has been
   * executed on this RDD. It is strongly recommended that this RDD is persisted in
   * memory, otherwise saving it on a file will require recomputation.
   */
  public  void checkpoint () { throw new RuntimeException(); }
  /**
   * Return whether this RDD has been checkpointed or not
   */
  public  boolean isCheckpointed () { throw new RuntimeException(); }
  /**
   * Gets the name of the file to which this RDD was checkpointed
   */
  public  scala.Option<java.lang.String> getCheckpointFile () { throw new RuntimeException(); }
  private  org.apache.spark.storage.StorageLevel storageLevel () { throw new RuntimeException(); }
  /** User code that created this RDD (e.g. `textFile`, `parallelize`). */
  public  org.apache.spark.util.CallSite creationSite () { throw new RuntimeException(); }
  private  java.lang.String getCreationSite () { throw new RuntimeException(); }
  private  scala.reflect.ClassTag<T> elementClassTag () { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.rdd.RDDCheckpointData<T>> checkpointData () { throw new RuntimeException(); }
  /** Returns the first parent RDD */
  protected <U extends java.lang.Object> org.apache.spark.rdd.RDD<U> firstParent (scala.reflect.ClassTag<U> evidence$33) { throw new RuntimeException(); }
  /** The {@link org.apache.spark.SparkContext} that this RDD was created on. */
  public  org.apache.spark.SparkContext context () { throw new RuntimeException(); }
  /**
   * Private API for changing an RDD's ClassTag.
   * Used for internal Java <-> Scala API compatibility.
   */
  private  org.apache.spark.rdd.RDD<T> retag (java.lang.Class<T> cls) { throw new RuntimeException(); }
  /**
   * Private API for changing an RDD's ClassTag.
   * Used for internal Java <-> Scala API compatibility.
   */
  private  org.apache.spark.rdd.RDD<T> retag (scala.reflect.ClassTag<T> classTag) { throw new RuntimeException(); }
  private  boolean doCheckpointCalled () { throw new RuntimeException(); }
  /**
   * Performs the checkpointing of this RDD by saving this. It is called after a job using this RDD
   * has completed (therefore the RDD has been materialized and potentially stored in memory).
   * doCheckpoint() is called recursively on the parent RDDs.
   */
  private  void doCheckpoint () { throw new RuntimeException(); }
  /**
   * Changes the dependencies of this RDD from its original parents to a new RDD (<code>newRDD</code>)
   * created from the checkpoint file, and forget its old dependencies and partitions.
   */
  private  void markCheckpointed (org.apache.spark.rdd.RDD<?> checkpointRDD) { throw new RuntimeException(); }
  /**
   * Clears the dependencies of this RDD. This method must ensure that all references
   * to the original parent RDDs is removed to enable the parent RDDs to be garbage
   * collected. Subclasses of RDD may override this method for implementing their own cleaning
   * logic. See {@link org.apache.spark.rdd.UnionRDD} for an example.
   */
  protected  void clearDependencies () { throw new RuntimeException(); }
  /** A description of this RDD and its recursive dependencies for debugging. */
  public  java.lang.String toDebugString () { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
  public  org.apache.spark.api.java.JavaRDD<T> toJavaRDD () { throw new RuntimeException(); }
}
