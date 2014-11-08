package org.apache.spark.api.java;
public  class JavaPairRDD<K extends java.lang.Object, V extends java.lang.Object> implements org.apache.spark.api.java.JavaRDDLike<scala.Tuple2<K, V>, org.apache.spark.api.java.JavaPairRDD<K, V>> {
  static private <K extends java.lang.Object, T extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, java.lang.Iterable<T>>> groupByResultToJava (org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.collection.Iterable<T>>> rdd, scala.reflect.ClassTag<K> evidence$1) { throw new RuntimeException(); }
  static private <K extends java.lang.Object, V extends java.lang.Object, W extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple2<java.lang.Iterable<V>, java.lang.Iterable<W>>>> cogroupResultToJava (org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple2<scala.collection.Iterable<V>, scala.collection.Iterable<W>>>> rdd, scala.reflect.ClassTag<K> evidence$2) { throw new RuntimeException(); }
  static private <K extends java.lang.Object, V extends java.lang.Object, W1 extends java.lang.Object, W2 extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple3<java.lang.Iterable<V>, java.lang.Iterable<W1>, java.lang.Iterable<W2>>>> cogroupResult2ToJava (org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple3<scala.collection.Iterable<V>, scala.collection.Iterable<W1>, scala.collection.Iterable<W2>>>> rdd, scala.reflect.ClassTag<K> evidence$3) { throw new RuntimeException(); }
  static private <K extends java.lang.Object, V extends java.lang.Object, W1 extends java.lang.Object, W2 extends java.lang.Object, W3 extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple4<java.lang.Iterable<V>, java.lang.Iterable<W1>, java.lang.Iterable<W2>, java.lang.Iterable<W3>>>> cogroupResult3ToJava (org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple4<scala.collection.Iterable<V>, scala.collection.Iterable<W1>, scala.collection.Iterable<W2>, scala.collection.Iterable<W3>>>> rdd, scala.reflect.ClassTag<K> evidence$4) { throw new RuntimeException(); }
  static public <K extends java.lang.Object, V extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, V> fromRDD (org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> rdd, scala.reflect.ClassTag<K> evidence$5, scala.reflect.ClassTag<V> evidence$6) { throw new RuntimeException(); }
  static public <K extends java.lang.Object, V extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> toRDD (org.apache.spark.api.java.JavaPairRDD<K, V> rdd) { throw new RuntimeException(); }
  static private <T1 extends java.lang.Object, T2 extends java.lang.Object, R extends java.lang.Object> scala.Function2<T1, T2, R> toScalaFunction2 (org.apache.spark.api.java.function.Function2<T1, T2, R> fun) { throw new RuntimeException(); }
  static private <T extends java.lang.Object, R extends java.lang.Object> scala.Function1<T, R> toScalaFunction (org.apache.spark.api.java.function.Function<T, R> fun) { throw new RuntimeException(); }
  static private <A extends java.lang.Object, B extends java.lang.Object, C extends java.lang.Object> scala.Function1<A, scala.Tuple2<B, C>> pairFunToScalaFun (org.apache.spark.api.java.function.PairFunction<A, B, C> x) { throw new RuntimeException(); }
  /** Convert a JavaRDD of key-value pairs to JavaPairRDD. */
  static public <K extends java.lang.Object, V extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, V> fromJavaRDD (org.apache.spark.api.java.JavaRDD<scala.Tuple2<K, V>> rdd) { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> rdd () { throw new RuntimeException(); }
  public  scala.reflect.ClassTag<K> kClassTag () { throw new RuntimeException(); }
  public  scala.reflect.ClassTag<V> vClassTag () { throw new RuntimeException(); }
  // not preceding
  public   JavaPairRDD (org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> rdd, scala.reflect.ClassTag<K> kClassTag, scala.reflect.ClassTag<V> vClassTag) { throw new RuntimeException(); }
  public  org.apache.spark.api.java.JavaPairRDD<K, V> wrapRDD (org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> rdd) { throw new RuntimeException(); }
  public  scala.reflect.ClassTag<scala.Tuple2<K, V>> classTag () { throw new RuntimeException(); }
  /** Persist this RDD with the default storage level (`MEMORY_ONLY`). */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> cache () { throw new RuntimeException(); }
  /**
   * Set this RDD's storage level to persist its values across operations after the first time
   * it is computed. Can only be called once on each RDD.
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> persist (org.apache.spark.storage.StorageLevel newLevel) { throw new RuntimeException(); }
  /**
   * Mark the RDD as non-persistent, and remove all blocks for it from memory and disk.
   * This method blocks until all blocks are deleted.
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> unpersist () { throw new RuntimeException(); }
  /**
   * Mark the RDD as non-persistent, and remove all blocks for it from memory and disk.
   * <p>
   * @param blocking Whether to block until all blocks are deleted.
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> unpersist (boolean blocking) { throw new RuntimeException(); }
  /**
   * Return a new RDD containing the distinct elements in this RDD.
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> distinct () { throw new RuntimeException(); }
  /**
   * Return a new RDD containing the distinct elements in this RDD.
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> distinct (int numPartitions) { throw new RuntimeException(); }
  /**
   * Return a new RDD containing only the elements that satisfy a predicate.
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> filter (org.apache.spark.api.java.function.Function<scala.Tuple2<K, V>, java.lang.Boolean> f) { throw new RuntimeException(); }
  /**
   * Return a new RDD that is reduced into <code>numPartitions</code> partitions.
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> coalesce (int numPartitions) { throw new RuntimeException(); }
  /**
   * Return a new RDD that is reduced into <code>numPartitions</code> partitions.
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> coalesce (int numPartitions, boolean shuffle) { throw new RuntimeException(); }
  /**
   * Return a new RDD that has exactly numPartitions partitions.
   * <p>
   * Can increase or decrease the level of parallelism in this RDD. Internally, this uses
   * a shuffle to redistribute data.
   * <p>
   * If you are decreasing the number of partitions in this RDD, consider using <code>coalesce</code>,
   * which can avoid performing a shuffle.
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> repartition (int numPartitions) { throw new RuntimeException(); }
  /**
   * Return a sampled subset of this RDD.
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> sample (boolean withReplacement, double fraction) { throw new RuntimeException(); }
  /**
   * Return a sampled subset of this RDD.
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> sample (boolean withReplacement, double fraction, long seed) { throw new RuntimeException(); }
  /**
   * Return a subset of this RDD sampled by key (via stratified sampling).
   * <p>
   * Create a sample of this RDD using variable sampling rates for different keys as specified by
   * <code>fractions</code>, a key to sampling rate map, via simple random sampling with one pass over the
   * RDD, to produce a sample of size that's approximately equal to the sum of
   * math.ceil(numItems * samplingRate) over all key values.
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> sampleByKey (boolean withReplacement, java.util.Map<K, java.lang.Object> fractions, long seed) { throw new RuntimeException(); }
  /**
   * Return a subset of this RDD sampled by key (via stratified sampling).
   * <p>
   * Create a sample of this RDD using variable sampling rates for different keys as specified by
   * <code>fractions</code>, a key to sampling rate map, via simple random sampling with one pass over the
   * RDD, to produce a sample of size that's approximately equal to the sum of
   * math.ceil(numItems * samplingRate) over all key values.
   * <p>
   * Use Utils.random.nextLong as the default seed for the random number generator.
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> sampleByKey (boolean withReplacement, java.util.Map<K, java.lang.Object> fractions) { throw new RuntimeException(); }
  /**
   * ::Experimental::
   * Return a subset of this RDD sampled by key (via stratified sampling) containing exactly
   * math.ceil(numItems * samplingRate) for each stratum (group of pairs with the same key).
   * <p>
   * This method differs from {@link sampleByKey} in that we make additional passes over the RDD to
   * create a sample size that's exactly equal to the sum of math.ceil(numItems * samplingRate)
   * over all key values with a 99.99% confidence. When sampling without replacement, we need one
   * additional pass over the RDD to guarantee sample size; when sampling with replacement, we need
   * two additional passes.
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> sampleByKeyExact (boolean withReplacement, java.util.Map<K, java.lang.Object> fractions, long seed) { throw new RuntimeException(); }
  /**
   * ::Experimental::
   * Return a subset of this RDD sampled by key (via stratified sampling) containing exactly
   * math.ceil(numItems * samplingRate) for each stratum (group of pairs with the same key).
   * <p>
   * This method differs from {@link sampleByKey} in that we make additional passes over the RDD to
   * create a sample size that's exactly equal to the sum of math.ceil(numItems * samplingRate)
   * over all key values with a 99.99% confidence. When sampling without replacement, we need one
   * additional pass over the RDD to guarantee sample size; when sampling with replacement, we need
   * two additional passes.
   * <p>
   * Use Utils.random.nextLong as the default seed for the random number generator.
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> sampleByKeyExact (boolean withReplacement, java.util.Map<K, java.lang.Object> fractions) { throw new RuntimeException(); }
  /**
   * Return the union of this RDD and another one. Any identical elements will appear multiple
   * times (use <code>.distinct()</code> to eliminate them).
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> union (org.apache.spark.api.java.JavaPairRDD<K, V> other) { throw new RuntimeException(); }
  /**
   * Return the intersection of this RDD and another one. The output will not contain any duplicate
   * elements, even if the input RDDs did.
   * <p>
   * Note that this method performs a shuffle internally.
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> intersection (org.apache.spark.api.java.JavaPairRDD<K, V> other) { throw new RuntimeException(); }
  public  scala.Tuple2<K, V> first () { throw new RuntimeException(); }
  /**
   * Generic function to combine the elements for each key using a custom set of aggregation
   * functions. Turns a JavaPairRDD[(K, V)] into a result of type JavaPairRDD[(K, C)], for a
   * "combined type" C * Note that V and C can be different -- for example, one might group an
   * RDD of type (Int, Int) into an RDD of type (Int, List[Int]). Users provide three
   * functions:
   * <p>
   * - <code>createCombiner</code>, which turns a V into a C (e.g., creates a one-element list)
   * - <code>mergeValue</code>, to merge a V into a C (e.g., adds it to the end of a list)
   * - <code>mergeCombiners</code>, to combine two C's into a single one.
   * <p>
   * In addition, users can control the partitioning of the output RDD, and whether to perform
   * map-side aggregation (if a mapper can produce multiple items with the same key).
   */
  public <C extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, C> combineByKey (org.apache.spark.api.java.function.Function<V, C> createCombiner, org.apache.spark.api.java.function.Function2<C, V, C> mergeValue, org.apache.spark.api.java.function.Function2<C, C, C> mergeCombiners, org.apache.spark.Partitioner partitioner) { throw new RuntimeException(); }
  /**
   * Simplified version of combineByKey that hash-partitions the output RDD.
   */
  public <C extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, C> combineByKey (org.apache.spark.api.java.function.Function<V, C> createCombiner, org.apache.spark.api.java.function.Function2<C, V, C> mergeValue, org.apache.spark.api.java.function.Function2<C, C, C> mergeCombiners, int numPartitions) { throw new RuntimeException(); }
  /**
   * Merge the values for each key using an associative reduce function. This will also perform
   * the merging locally on each mapper before sending results to a reducer, similarly to a
   * "combiner" in MapReduce.
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> reduceByKey (org.apache.spark.Partitioner partitioner, org.apache.spark.api.java.function.Function2<V, V, V> func) { throw new RuntimeException(); }
  /**
   * Merge the values for each key using an associative reduce function, but return the results
   * immediately to the master as a Map. This will also perform the merging locally on each mapper
   * before sending results to a reducer, similarly to a "combiner" in MapReduce.
   */
  public  java.util.Map<K, V> reduceByKeyLocally (org.apache.spark.api.java.function.Function2<V, V, V> func) { throw new RuntimeException(); }
  /** Count the number of elements for each key, and return the result to the master as a Map. */
  public  java.util.Map<K, java.lang.Object> countByKey () { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Approximate version of countByKey that can return a partial result if it does
   * not finish within a timeout.
   */
  public  org.apache.spark.partial.PartialResult<java.util.Map<K, org.apache.spark.partial.BoundedDouble>> countByKeyApprox (long timeout) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Approximate version of countByKey that can return a partial result if it does
   * not finish within a timeout.
   */
  public  org.apache.spark.partial.PartialResult<java.util.Map<K, org.apache.spark.partial.BoundedDouble>> countByKeyApprox (long timeout, double confidence) { throw new RuntimeException(); }
  /**
   * Aggregate the values of each key, using given combine functions and a neutral "zero value".
   * This function can return a different result type, U, than the type of the values in this RDD,
   * V. Thus, we need one operation for merging a V into a U and one operation for merging two U's,
   * as in scala.TraversableOnce. The former operation is used for merging values within a
   * partition, and the latter is used for merging values between partitions. To avoid memory
   * allocation, both of these functions are allowed to modify and return their first argument
   * instead of creating a new U.
   */
  public <U extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, U> aggregateByKey (U zeroValue, org.apache.spark.Partitioner partitioner, org.apache.spark.api.java.function.Function2<U, V, U> seqFunc, org.apache.spark.api.java.function.Function2<U, U, U> combFunc) { throw new RuntimeException(); }
  /**
   * Aggregate the values of each key, using given combine functions and a neutral "zero value".
   * This function can return a different result type, U, than the type of the values in this RDD,
   * V. Thus, we need one operation for merging a V into a U and one operation for merging two U's,
   * as in scala.TraversableOnce. The former operation is used for merging values within a
   * partition, and the latter is used for merging values between partitions. To avoid memory
   * allocation, both of these functions are allowed to modify and return their first argument
   * instead of creating a new U.
   */
  public <U extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, U> aggregateByKey (U zeroValue, int numPartitions, org.apache.spark.api.java.function.Function2<U, V, U> seqFunc, org.apache.spark.api.java.function.Function2<U, U, U> combFunc) { throw new RuntimeException(); }
  /**
   * Aggregate the values of each key, using given combine functions and a neutral "zero value".
   * This function can return a different result type, U, than the type of the values in this RDD,
   * V. Thus, we need one operation for merging a V into a U and one operation for merging two U's.
   * The former operation is used for merging values within a partition, and the latter is used for
   * merging values between partitions. To avoid memory allocation, both of these functions are
   * allowed to modify and return their first argument instead of creating a new U.
   */
  public <U extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, U> aggregateByKey (U zeroValue, org.apache.spark.api.java.function.Function2<U, V, U> seqFunc, org.apache.spark.api.java.function.Function2<U, U, U> combFunc) { throw new RuntimeException(); }
  /**
   * Merge the values for each key using an associative function and a neutral "zero value" which
   * may be added to the result an arbitrary number of times, and must not change the result
   * (e.g ., Nil for list concatenation, 0 for addition, or 1 for multiplication.).
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> foldByKey (V zeroValue, org.apache.spark.Partitioner partitioner, org.apache.spark.api.java.function.Function2<V, V, V> func) { throw new RuntimeException(); }
  /**
   * Merge the values for each key using an associative function and a neutral "zero value" which
   * may be added to the result an arbitrary number of times, and must not change the result
   * (e.g ., Nil for list concatenation, 0 for addition, or 1 for multiplication.).
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> foldByKey (V zeroValue, int numPartitions, org.apache.spark.api.java.function.Function2<V, V, V> func) { throw new RuntimeException(); }
  /**
   * Merge the values for each key using an associative function and a neutral "zero value"
   * which may be added to the result an arbitrary number of times, and must not change the result
   * (e.g., Nil for list concatenation, 0 for addition, or 1 for multiplication.).
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> foldByKey (V zeroValue, org.apache.spark.api.java.function.Function2<V, V, V> func) { throw new RuntimeException(); }
  /**
   * Merge the values for each key using an associative reduce function. This will also perform
   * the merging locally on each mapper before sending results to a reducer, similarly to a
   * "combiner" in MapReduce. Output will be hash-partitioned with numPartitions partitions.
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> reduceByKey (org.apache.spark.api.java.function.Function2<V, V, V> func, int numPartitions) { throw new RuntimeException(); }
  /**
   * Group the values for each key in the RDD into a single sequence. Allows controlling the
   * partitioning of the resulting key-value pair RDD by passing a Partitioner.
   * <p>
   * Note: If you are grouping in order to perform an aggregation (such as a sum or average) over
   * each key, using {@link JavaPairRDD.reduceByKey} or {@link JavaPairRDD.combineByKey}
   * will provide much better performance.
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, java.lang.Iterable<V>> groupByKey (org.apache.spark.Partitioner partitioner) { throw new RuntimeException(); }
  /**
   * Group the values for each key in the RDD into a single sequence. Hash-partitions the
   * resulting RDD with into <code>numPartitions</code> partitions.
   * <p>
   * Note: If you are grouping in order to perform an aggregation (such as a sum or average) over
   * each key, using {@link JavaPairRDD.reduceByKey} or {@link JavaPairRDD.combineByKey}
   * will provide much better performance.
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, java.lang.Iterable<V>> groupByKey (int numPartitions) { throw new RuntimeException(); }
  /**
   * Return an RDD with the elements from <code>this</code> that are not in <code>other</code>.
   * <p>
   * Uses <code>this</code> partitioner/partition size, because even if <code>other</code> is huge, the resulting
   * RDD will be <= us.
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> subtract (org.apache.spark.api.java.JavaPairRDD<K, V> other) { throw new RuntimeException(); }
  /**
   * Return an RDD with the elements from <code>this</code> that are not in <code>other</code>.
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> subtract (org.apache.spark.api.java.JavaPairRDD<K, V> other, int numPartitions) { throw new RuntimeException(); }
  /**
   * Return an RDD with the elements from <code>this</code> that are not in <code>other</code>.
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> subtract (org.apache.spark.api.java.JavaPairRDD<K, V> other, org.apache.spark.Partitioner p) { throw new RuntimeException(); }
  /**
   * Return an RDD with the pairs from <code>this</code> whose keys are not in <code>other</code>.
   * <p>
   * Uses <code>this</code> partitioner/partition size, because even if <code>other</code> is huge, the resulting
   * RDD will be <= us.
   */
  public <W extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, V> subtractByKey (org.apache.spark.api.java.JavaPairRDD<K, W> other) { throw new RuntimeException(); }
  /** Return an RDD with the pairs from `this` whose keys are not in `other`. */
  public <W extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, V> subtractByKey (org.apache.spark.api.java.JavaPairRDD<K, W> other, int numPartitions) { throw new RuntimeException(); }
  /** Return an RDD with the pairs from `this` whose keys are not in `other`. */
  public <W extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, V> subtractByKey (org.apache.spark.api.java.JavaPairRDD<K, W> other, org.apache.spark.Partitioner p) { throw new RuntimeException(); }
  /**
   * Return a copy of the RDD partitioned using the specified partitioner.
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> partitionBy (org.apache.spark.Partitioner partitioner) { throw new RuntimeException(); }
  /**
   * Merge the values for each key using an associative reduce function. This will also perform
   * the merging locally on each mapper before sending results to a reducer, similarly to a
   * "combiner" in MapReduce.
   */
  public <W extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, scala.Tuple2<V, W>> join (org.apache.spark.api.java.JavaPairRDD<K, W> other, org.apache.spark.Partitioner partitioner) { throw new RuntimeException(); }
  /**
   * Perform a left outer join of <code>this</code> and <code>other</code>. For each element (k, v) in <code>this</code>, the
   * resulting RDD will either contain all pairs (k, (v, Some(w))) for w in <code>other</code>, or the
   * pair (k, (v, None)) if no elements in <code>other</code> have key k. Uses the given Partitioner to
   * partition the output RDD.
   */
  public <W extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, scala.Tuple2<V, com.google.common.base.Optional<W>>> leftOuterJoin (org.apache.spark.api.java.JavaPairRDD<K, W> other, org.apache.spark.Partitioner partitioner) { throw new RuntimeException(); }
  /**
   * Perform a right outer join of <code>this</code> and <code>other</code>. For each element (k, w) in <code>other</code>, the
   * resulting RDD will either contain all pairs (k, (Some(v), w)) for v in <code>this</code>, or the
   * pair (k, (None, w)) if no elements in <code>this</code> have key k. Uses the given Partitioner to
   * partition the output RDD.
   */
  public <W extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, scala.Tuple2<com.google.common.base.Optional<V>, W>> rightOuterJoin (org.apache.spark.api.java.JavaPairRDD<K, W> other, org.apache.spark.Partitioner partitioner) { throw new RuntimeException(); }
  /**
   * Simplified version of combineByKey that hash-partitions the resulting RDD using the existing
   * partitioner/parallelism level.
   */
  public <C extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, C> combineByKey (org.apache.spark.api.java.function.Function<V, C> createCombiner, org.apache.spark.api.java.function.Function2<C, V, C> mergeValue, org.apache.spark.api.java.function.Function2<C, C, C> mergeCombiners) { throw new RuntimeException(); }
  /**
   * Merge the values for each key using an associative reduce function. This will also perform
   * the merging locally on each mapper before sending results to a reducer, similarly to a
   * "combiner" in MapReduce. Output will be hash-partitioned with the existing partitioner/
   * parallelism level.
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> reduceByKey (org.apache.spark.api.java.function.Function2<V, V, V> func) { throw new RuntimeException(); }
  /**
   * Group the values for each key in the RDD into a single sequence. Hash-partitions the
   * resulting RDD with the existing partitioner/parallelism level.
   * <p>
   * Note: If you are grouping in order to perform an aggregation (such as a sum or average) over
   * each key, using {@link JavaPairRDD.reduceByKey} or {@link JavaPairRDD.combineByKey}
   * will provide much better performance.
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, java.lang.Iterable<V>> groupByKey () { throw new RuntimeException(); }
  /**
   * Return an RDD containing all pairs of elements with matching keys in <code>this</code> and <code>other</code>. Each
   * pair of elements will be returned as a (k, (v1, v2)) tuple, where (k, v1) is in <code>this</code> and
   * (k, v2) is in <code>other</code>. Performs a hash join across the cluster.
   */
  public <W extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, scala.Tuple2<V, W>> join (org.apache.spark.api.java.JavaPairRDD<K, W> other) { throw new RuntimeException(); }
  /**
   * Return an RDD containing all pairs of elements with matching keys in <code>this</code> and <code>other</code>. Each
   * pair of elements will be returned as a (k, (v1, v2)) tuple, where (k, v1) is in <code>this</code> and
   * (k, v2) is in <code>other</code>. Performs a hash join across the cluster.
   */
  public <W extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, scala.Tuple2<V, W>> join (org.apache.spark.api.java.JavaPairRDD<K, W> other, int numPartitions) { throw new RuntimeException(); }
  /**
   * Perform a left outer join of <code>this</code> and <code>other</code>. For each element (k, v) in <code>this</code>, the
   * resulting RDD will either contain all pairs (k, (v, Some(w))) for w in <code>other</code>, or the
   * pair (k, (v, None)) if no elements in <code>other</code> have key k. Hash-partitions the output
   * using the existing partitioner/parallelism level.
   */
  public <W extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, scala.Tuple2<V, com.google.common.base.Optional<W>>> leftOuterJoin (org.apache.spark.api.java.JavaPairRDD<K, W> other) { throw new RuntimeException(); }
  /**
   * Perform a left outer join of <code>this</code> and <code>other</code>. For each element (k, v) in <code>this</code>, the
   * resulting RDD will either contain all pairs (k, (v, Some(w))) for w in <code>other</code>, or the
   * pair (k, (v, None)) if no elements in <code>other</code> have key k. Hash-partitions the output
   * into <code>numPartitions</code> partitions.
   */
  public <W extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, scala.Tuple2<V, com.google.common.base.Optional<W>>> leftOuterJoin (org.apache.spark.api.java.JavaPairRDD<K, W> other, int numPartitions) { throw new RuntimeException(); }
  /**
   * Perform a right outer join of <code>this</code> and <code>other</code>. For each element (k, w) in <code>other</code>, the
   * resulting RDD will either contain all pairs (k, (Some(v), w)) for v in <code>this</code>, or the
   * pair (k, (None, w)) if no elements in <code>this</code> have key k. Hash-partitions the resulting
   * RDD using the existing partitioner/parallelism level.
   */
  public <W extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, scala.Tuple2<com.google.common.base.Optional<V>, W>> rightOuterJoin (org.apache.spark.api.java.JavaPairRDD<K, W> other) { throw new RuntimeException(); }
  /**
   * Perform a right outer join of <code>this</code> and <code>other</code>. For each element (k, w) in <code>other</code>, the
   * resulting RDD will either contain all pairs (k, (Some(v), w)) for v in <code>this</code>, or the
   * pair (k, (None, w)) if no elements in <code>this</code> have key k. Hash-partitions the resulting
   * RDD into the given number of partitions.
   */
  public <W extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, scala.Tuple2<com.google.common.base.Optional<V>, W>> rightOuterJoin (org.apache.spark.api.java.JavaPairRDD<K, W> other, int numPartitions) { throw new RuntimeException(); }
  /**
   * Return the key-value pairs in this RDD to the master as a Map.
   */
  public  java.util.Map<K, V> collectAsMap () { throw new RuntimeException(); }
  /**
   * Pass each value in the key-value pair RDD through a map function without changing the keys;
   * this also retains the original RDD's partitioning.
   */
  public <U extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, U> mapValues (org.apache.spark.api.java.function.Function<V, U> f) { throw new RuntimeException(); }
  /**
   * Pass each value in the key-value pair RDD through a flatMap function without changing the
   * keys; this also retains the original RDD's partitioning.
   */
  public <U extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, U> flatMapValues (org.apache.spark.api.java.function.Function<V, java.lang.Iterable<U>> f) { throw new RuntimeException(); }
  /**
   * For each key k in <code>this</code> or <code>other</code>, return a resulting RDD that contains a tuple with the
   * list of values for that key in <code>this</code> as well as <code>other</code>.
   */
  public <W extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, scala.Tuple2<java.lang.Iterable<V>, java.lang.Iterable<W>>> cogroup (org.apache.spark.api.java.JavaPairRDD<K, W> other, org.apache.spark.Partitioner partitioner) { throw new RuntimeException(); }
  /**
   * For each key k in <code>this</code> or <code>other1</code> or <code>other2</code>, return a resulting RDD that contains a
   * tuple with the list of values for that key in <code>this</code>, <code>other1</code> and <code>other2</code>.
   */
  public <W1 extends java.lang.Object, W2 extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, scala.Tuple3<java.lang.Iterable<V>, java.lang.Iterable<W1>, java.lang.Iterable<W2>>> cogroup (org.apache.spark.api.java.JavaPairRDD<K, W1> other1, org.apache.spark.api.java.JavaPairRDD<K, W2> other2, org.apache.spark.Partitioner partitioner) { throw new RuntimeException(); }
  /**
   * For each key k in <code>this</code> or <code>other1</code> or <code>other2</code> or <code>other3</code>,
   * return a resulting RDD that contains a tuple with the list of values
   * for that key in <code>this</code>, <code>other1</code>, <code>other2</code> and <code>other3</code>.
   */
  public <W1 extends java.lang.Object, W2 extends java.lang.Object, W3 extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, scala.Tuple4<java.lang.Iterable<V>, java.lang.Iterable<W1>, java.lang.Iterable<W2>, java.lang.Iterable<W3>>> cogroup (org.apache.spark.api.java.JavaPairRDD<K, W1> other1, org.apache.spark.api.java.JavaPairRDD<K, W2> other2, org.apache.spark.api.java.JavaPairRDD<K, W3> other3, org.apache.spark.Partitioner partitioner) { throw new RuntimeException(); }
  /**
   * For each key k in <code>this</code> or <code>other</code>, return a resulting RDD that contains a tuple with the
   * list of values for that key in <code>this</code> as well as <code>other</code>.
   */
  public <W extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, scala.Tuple2<java.lang.Iterable<V>, java.lang.Iterable<W>>> cogroup (org.apache.spark.api.java.JavaPairRDD<K, W> other) { throw new RuntimeException(); }
  /**
   * For each key k in <code>this</code> or <code>other1</code> or <code>other2</code>, return a resulting RDD that contains a
   * tuple with the list of values for that key in <code>this</code>, <code>other1</code> and <code>other2</code>.
   */
  public <W1 extends java.lang.Object, W2 extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, scala.Tuple3<java.lang.Iterable<V>, java.lang.Iterable<W1>, java.lang.Iterable<W2>>> cogroup (org.apache.spark.api.java.JavaPairRDD<K, W1> other1, org.apache.spark.api.java.JavaPairRDD<K, W2> other2) { throw new RuntimeException(); }
  /**
   * For each key k in <code>this</code> or <code>other1</code> or <code>other2</code> or <code>other3</code>,
   * return a resulting RDD that contains a tuple with the list of values
   * for that key in <code>this</code>, <code>other1</code>, <code>other2</code> and <code>other3</code>.
   */
  public <W1 extends java.lang.Object, W2 extends java.lang.Object, W3 extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, scala.Tuple4<java.lang.Iterable<V>, java.lang.Iterable<W1>, java.lang.Iterable<W2>, java.lang.Iterable<W3>>> cogroup (org.apache.spark.api.java.JavaPairRDD<K, W1> other1, org.apache.spark.api.java.JavaPairRDD<K, W2> other2, org.apache.spark.api.java.JavaPairRDD<K, W3> other3) { throw new RuntimeException(); }
  /**
   * For each key k in <code>this</code> or <code>other</code>, return a resulting RDD that contains a tuple with the
   * list of values for that key in <code>this</code> as well as <code>other</code>.
   */
  public <W extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, scala.Tuple2<java.lang.Iterable<V>, java.lang.Iterable<W>>> cogroup (org.apache.spark.api.java.JavaPairRDD<K, W> other, int numPartitions) { throw new RuntimeException(); }
  /**
   * For each key k in <code>this</code> or <code>other1</code> or <code>other2</code>, return a resulting RDD that contains a
   * tuple with the list of values for that key in <code>this</code>, <code>other1</code> and <code>other2</code>.
   */
  public <W1 extends java.lang.Object, W2 extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, scala.Tuple3<java.lang.Iterable<V>, java.lang.Iterable<W1>, java.lang.Iterable<W2>>> cogroup (org.apache.spark.api.java.JavaPairRDD<K, W1> other1, org.apache.spark.api.java.JavaPairRDD<K, W2> other2, int numPartitions) { throw new RuntimeException(); }
  /**
   * For each key k in <code>this</code> or <code>other1</code> or <code>other2</code> or <code>other3</code>,
   * return a resulting RDD that contains a tuple with the list of values
   * for that key in <code>this</code>, <code>other1</code>, <code>other2</code> and <code>other3</code>.
   */
  public <W1 extends java.lang.Object, W2 extends java.lang.Object, W3 extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, scala.Tuple4<java.lang.Iterable<V>, java.lang.Iterable<W1>, java.lang.Iterable<W2>, java.lang.Iterable<W3>>> cogroup (org.apache.spark.api.java.JavaPairRDD<K, W1> other1, org.apache.spark.api.java.JavaPairRDD<K, W2> other2, org.apache.spark.api.java.JavaPairRDD<K, W3> other3, int numPartitions) { throw new RuntimeException(); }
  /** Alias for cogroup. */
  public <W extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, scala.Tuple2<java.lang.Iterable<V>, java.lang.Iterable<W>>> groupWith (org.apache.spark.api.java.JavaPairRDD<K, W> other) { throw new RuntimeException(); }
  /** Alias for cogroup. */
  public <W1 extends java.lang.Object, W2 extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, scala.Tuple3<java.lang.Iterable<V>, java.lang.Iterable<W1>, java.lang.Iterable<W2>>> groupWith (org.apache.spark.api.java.JavaPairRDD<K, W1> other1, org.apache.spark.api.java.JavaPairRDD<K, W2> other2) { throw new RuntimeException(); }
  /** Alias for cogroup. */
  public <W1 extends java.lang.Object, W2 extends java.lang.Object, W3 extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, scala.Tuple4<java.lang.Iterable<V>, java.lang.Iterable<W1>, java.lang.Iterable<W2>, java.lang.Iterable<W3>>> groupWith (org.apache.spark.api.java.JavaPairRDD<K, W1> other1, org.apache.spark.api.java.JavaPairRDD<K, W2> other2, org.apache.spark.api.java.JavaPairRDD<K, W3> other3) { throw new RuntimeException(); }
  /**
   * Return the list of values in the RDD for key <code>key</code>. This operation is done efficiently if the
   * RDD has a known partitioner by only searching the partition that the key maps to.
   */
  public  java.util.List<V> lookup (K key) { throw new RuntimeException(); }
  /** Output the RDD to any Hadoop-supported file system. */
  public <F extends org.apache.hadoop.mapred.OutputFormat<?, ?>> void saveAsHadoopFile (java.lang.String path, java.lang.Class<?> keyClass, java.lang.Class<?> valueClass, java.lang.Class<F> outputFormatClass, org.apache.hadoop.mapred.JobConf conf) { throw new RuntimeException(); }
  /** Output the RDD to any Hadoop-supported file system. */
  public <F extends org.apache.hadoop.mapred.OutputFormat<?, ?>> void saveAsHadoopFile (java.lang.String path, java.lang.Class<?> keyClass, java.lang.Class<?> valueClass, java.lang.Class<F> outputFormatClass) { throw new RuntimeException(); }
  /** Output the RDD to any Hadoop-supported file system, compressing with the supplied codec. */
  public <F extends org.apache.hadoop.mapred.OutputFormat<?, ?>> void saveAsHadoopFile (java.lang.String path, java.lang.Class<?> keyClass, java.lang.Class<?> valueClass, java.lang.Class<F> outputFormatClass, java.lang.Class<? extends org.apache.hadoop.io.compress.CompressionCodec> codec) { throw new RuntimeException(); }
  /** Output the RDD to any Hadoop-supported file system. */
  public <F extends org.apache.hadoop.mapreduce.OutputFormat<?, ?>> void saveAsNewAPIHadoopFile (java.lang.String path, java.lang.Class<?> keyClass, java.lang.Class<?> valueClass, java.lang.Class<F> outputFormatClass, org.apache.hadoop.conf.Configuration conf) { throw new RuntimeException(); }
  /**
   * Output the RDD to any Hadoop-supported storage system, using
   * a Configuration object for that storage system.
   */
  public  void saveAsNewAPIHadoopDataset (org.apache.hadoop.conf.Configuration conf) { throw new RuntimeException(); }
  /** Output the RDD to any Hadoop-supported file system. */
  public <F extends org.apache.hadoop.mapreduce.OutputFormat<?, ?>> void saveAsNewAPIHadoopFile (java.lang.String path, java.lang.Class<?> keyClass, java.lang.Class<?> valueClass, java.lang.Class<F> outputFormatClass) { throw new RuntimeException(); }
  /**
   * Output the RDD to any Hadoop-supported storage system, using a Hadoop JobConf object for
   * that storage system. The JobConf should set an OutputFormat and any output paths required
   * (e.g. a table name to write to) in the same way as it would be configured for a Hadoop
   * MapReduce job.
   */
  public  void saveAsHadoopDataset (org.apache.hadoop.mapred.JobConf conf) { throw new RuntimeException(); }
  /**
   * Sort the RDD by key, so that each partition contains a sorted range of the elements in
   * ascending order. Calling <code>collect</code> or <code>save</code> on the resulting RDD will return or output an
   * ordered list of records (in the <code>save</code> case, they will be written to multiple <code>part-X</code> files
   * in the filesystem, in order of the keys).
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> sortByKey () { throw new RuntimeException(); }
  /**
   * Sort the RDD by key, so that each partition contains a sorted range of the elements. Calling
   * <code>collect</code> or <code>save</code> on the resulting RDD will return or output an ordered list of records
   * (in the <code>save</code> case, they will be written to multiple <code>part-X</code> files in the filesystem, in
   * order of the keys).
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> sortByKey (boolean ascending) { throw new RuntimeException(); }
  /**
   * Sort the RDD by key, so that each partition contains a sorted range of the elements. Calling
   * <code>collect</code> or <code>save</code> on the resulting RDD will return or output an ordered list of records
   * (in the <code>save</code> case, they will be written to multiple <code>part-X</code> files in the filesystem, in
   * order of the keys).
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> sortByKey (boolean ascending, int numPartitions) { throw new RuntimeException(); }
  /**
   * Sort the RDD by key, so that each partition contains a sorted range of the elements. Calling
   * <code>collect</code> or <code>save</code> on the resulting RDD will return or output an ordered list of records
   * (in the <code>save</code> case, they will be written to multiple <code>part-X</code> files in the filesystem, in
   * order of the keys).
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> sortByKey (java.util.Comparator<K> comp) { throw new RuntimeException(); }
  /**
   * Sort the RDD by key, so that each partition contains a sorted range of the elements. Calling
   * <code>collect</code> or <code>save</code> on the resulting RDD will return or output an ordered list of records
   * (in the <code>save</code> case, they will be written to multiple <code>part-X</code> files in the filesystem, in
   * order of the keys).
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> sortByKey (java.util.Comparator<K> comp, boolean ascending) { throw new RuntimeException(); }
  /**
   * Sort the RDD by key, so that each partition contains a sorted range of the elements. Calling
   * <code>collect</code> or <code>save</code> on the resulting RDD will return or output an ordered list of records
   * (in the <code>save</code> case, they will be written to multiple <code>part-X</code> files in the filesystem, in
   * order of the keys).
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> sortByKey (java.util.Comparator<K> comp, boolean ascending, int numPartitions) { throw new RuntimeException(); }
  /**
   * Return an RDD with the keys of each tuple.
   */
  public  org.apache.spark.api.java.JavaRDD<K> keys () { throw new RuntimeException(); }
  /**
   * Return an RDD with the values of each tuple.
   */
  public  org.apache.spark.api.java.JavaRDD<V> values () { throw new RuntimeException(); }
  /**
   * Return approximate number of distinct values for each key in this RDD.
   * <p>
   * The algorithm used is based on streamlib's implementation of "HyperLogLog in Practice:
   * Algorithmic Engineering of a State of The Art Cardinality Estimation Algorithm", available
   * <a href="http://dx.doi.org/10.1145/2452376.2452456">here</a>.
   * <p>
   * @param relativeSD Relative accuracy. Smaller values create counters that require more space.
   *                   It must be greater than 0.000017.
   * @param partitioner partitioner of the resulting RDD.
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, java.lang.Object> countApproxDistinctByKey (double relativeSD, org.apache.spark.Partitioner partitioner) { throw new RuntimeException(); }
  /**
   * Return approximate number of distinct values for each key in this RDD.
   * <p>
   * The algorithm used is based on streamlib's implementation of "HyperLogLog in Practice:
   * Algorithmic Engineering of a State of The Art Cardinality Estimation Algorithm", available
   * <a href="http://dx.doi.org/10.1145/2452376.2452456">here</a>.
   * <p>
   * @param relativeSD Relative accuracy. Smaller values create counters that require more space.
   *                   It must be greater than 0.000017.
   * @param numPartitions number of partitions of the resulting RDD.
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, java.lang.Object> countApproxDistinctByKey (double relativeSD, int numPartitions) { throw new RuntimeException(); }
  /**
   * Return approximate number of distinct values for each key in this RDD.
   * <p>
   * The algorithm used is based on streamlib's implementation of "HyperLogLog in Practice:
   * Algorithmic Engineering of a State of The Art Cardinality Estimation Algorithm", available
   * <a href="http://dx.doi.org/10.1145/2452376.2452456">here</a>.
   * <p>
   * @param relativeSD Relative accuracy. Smaller values create counters that require more space.
   *                   It must be greater than 0.000017.
   */
  public  org.apache.spark.api.java.JavaPairRDD<K, java.lang.Object> countApproxDistinctByKey (double relativeSD) { throw new RuntimeException(); }
  /** Assign a name to this RDD */
  public  org.apache.spark.api.java.JavaPairRDD<K, V> setName (java.lang.String name) { throw new RuntimeException(); }
}
