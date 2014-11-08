package org.apache.spark.rdd;
/**
 * Extra functions available on RDDs of (key, value) pairs through an implicit conversion.
 * Import <code>org.apache.spark.SparkContext._</code> at the top of your program to use these functions.
 */
public  class PairRDDFunctions<K extends java.lang.Object, V extends java.lang.Object> implements org.apache.spark.Logging, org.apache.hadoop.mapreduce.SparkHadoopMapReduceUtil, scala.Serializable {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   PairRDDFunctions (org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> self, scala.reflect.ClassTag<K> kt, scala.reflect.ClassTag<V> vt, scala.math.Ordering<K> ord) { throw new RuntimeException(); }
  /**
   * Generic function to combine the elements for each key using a custom set of aggregation
   * functions. Turns an RDD[(K, V)] into a result of type RDD[(K, C)], for a "combined type" C
   * Note that V and C can be different -- for example, one might group an RDD of type
   * (Int, Int) into an RDD of type (Int, Seq[Int]). Users provide three functions:
   * <p>
   * - <code>createCombiner</code>, which turns a V into a C (e.g., creates a one-element list)
   * - <code>mergeValue</code>, to merge a V into a C (e.g., adds it to the end of a list)
   * - <code>mergeCombiners</code>, to combine two C's into a single one.
   * <p>
   * In addition, users can control the partitioning of the output RDD, and whether to perform
   * map-side aggregation (if a mapper can produce multiple items with the same key).
   */
  public <C extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, C>> combineByKey (scala.Function1<V, C> createCombiner, scala.Function2<C, V, C> mergeValue, scala.Function2<C, C, C> mergeCombiners, org.apache.spark.Partitioner partitioner, boolean mapSideCombine, org.apache.spark.serializer.Serializer serializer) { throw new RuntimeException(); }
  /**
   * Simplified version of combineByKey that hash-partitions the output RDD.
   */
  public <C extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, C>> combineByKey (scala.Function1<V, C> createCombiner, scala.Function2<C, V, C> mergeValue, scala.Function2<C, C, C> mergeCombiners, int numPartitions) { throw new RuntimeException(); }
  /**
   * Aggregate the values of each key, using given combine functions and a neutral "zero value".
   * This function can return a different result type, U, than the type of the values in this RDD,
   * V. Thus, we need one operation for merging a V into a U and one operation for merging two U's,
   * as in scala.TraversableOnce. The former operation is used for merging values within a
   * partition, and the latter is used for merging values between partitions. To avoid memory
   * allocation, both of these functions are allowed to modify and return their first argument
   * instead of creating a new U.
   */
  public <U extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, U>> aggregateByKey (U zeroValue, org.apache.spark.Partitioner partitioner, scala.Function2<U, V, U> seqOp, scala.Function2<U, U, U> combOp, scala.reflect.ClassTag<U> evidence$1) { throw new RuntimeException(); }
  /**
   * Aggregate the values of each key, using given combine functions and a neutral "zero value".
   * This function can return a different result type, U, than the type of the values in this RDD,
   * V. Thus, we need one operation for merging a V into a U and one operation for merging two U's,
   * as in scala.TraversableOnce. The former operation is used for merging values within a
   * partition, and the latter is used for merging values between partitions. To avoid memory
   * allocation, both of these functions are allowed to modify and return their first argument
   * instead of creating a new U.
   */
  public <U extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, U>> aggregateByKey (U zeroValue, int numPartitions, scala.Function2<U, V, U> seqOp, scala.Function2<U, U, U> combOp, scala.reflect.ClassTag<U> evidence$2) { throw new RuntimeException(); }
  /**
   * Aggregate the values of each key, using given combine functions and a neutral "zero value".
   * This function can return a different result type, U, than the type of the values in this RDD,
   * V. Thus, we need one operation for merging a V into a U and one operation for merging two U's,
   * as in scala.TraversableOnce. The former operation is used for merging values within a
   * partition, and the latter is used for merging values between partitions. To avoid memory
   * allocation, both of these functions are allowed to modify and return their first argument
   * instead of creating a new U.
   */
  public <U extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, U>> aggregateByKey (U zeroValue, scala.Function2<U, V, U> seqOp, scala.Function2<U, U, U> combOp, scala.reflect.ClassTag<U> evidence$3) { throw new RuntimeException(); }
  /**
   * Merge the values for each key using an associative function and a neutral "zero value" which
   * may be added to the result an arbitrary number of times, and must not change the result
   * (e.g., Nil for list concatenation, 0 for addition, or 1 for multiplication.).
   */
  public  org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> foldByKey (V zeroValue, org.apache.spark.Partitioner partitioner, scala.Function2<V, V, V> func) { throw new RuntimeException(); }
  /**
   * Merge the values for each key using an associative function and a neutral "zero value" which
   * may be added to the result an arbitrary number of times, and must not change the result
   * (e.g., Nil for list concatenation, 0 for addition, or 1 for multiplication.).
   */
  public  org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> foldByKey (V zeroValue, int numPartitions, scala.Function2<V, V, V> func) { throw new RuntimeException(); }
  /**
   * Merge the values for each key using an associative function and a neutral "zero value" which
   * may be added to the result an arbitrary number of times, and must not change the result
   * (e.g., Nil for list concatenation, 0 for addition, or 1 for multiplication.).
   */
  public  org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> foldByKey (V zeroValue, scala.Function2<V, V, V> func) { throw new RuntimeException(); }
  /**
   * Return a subset of this RDD sampled by key (via stratified sampling).
   * <p>
   * Create a sample of this RDD using variable sampling rates for different keys as specified by
   * <code>fractions</code>, a key to sampling rate map, via simple random sampling with one pass over the
   * RDD, to produce a sample of size that's approximately equal to the sum of
   * math.ceil(numItems * samplingRate) over all key values.
   * <p>
   * @param withReplacement whether to sample with or without replacement
   * @param fractions map of specific keys to sampling rates
   * @param seed seed for the random number generator
   * @return RDD containing the sampled subset
   */
  public  org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> sampleByKey (boolean withReplacement, scala.collection.Map<K, java.lang.Object> fractions, long seed) { throw new RuntimeException(); }
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
   * @param withReplacement whether to sample with or without replacement
   * @param fractions map of specific keys to sampling rates
   * @param seed seed for the random number generator
   * @return RDD containing the sampled subset
   */
  public  org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> sampleByKeyExact (boolean withReplacement, scala.collection.Map<K, java.lang.Object> fractions, long seed) { throw new RuntimeException(); }
  /**
   * Merge the values for each key using an associative reduce function. This will also perform
   * the merging locally on each mapper before sending results to a reducer, similarly to a
   * "combiner" in MapReduce.
   */
  public  org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> reduceByKey (org.apache.spark.Partitioner partitioner, scala.Function2<V, V, V> func) { throw new RuntimeException(); }
  /**
   * Merge the values for each key using an associative reduce function. This will also perform
   * the merging locally on each mapper before sending results to a reducer, similarly to a
   * "combiner" in MapReduce. Output will be hash-partitioned with numPartitions partitions.
   */
  public  org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> reduceByKey (scala.Function2<V, V, V> func, int numPartitions) { throw new RuntimeException(); }
  /**
   * Merge the values for each key using an associative reduce function. This will also perform
   * the merging locally on each mapper before sending results to a reducer, similarly to a
   * "combiner" in MapReduce. Output will be hash-partitioned with the existing partitioner/
   * parallelism level.
   */
  public  org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> reduceByKey (scala.Function2<V, V, V> func) { throw new RuntimeException(); }
  /**
   * Merge the values for each key using an associative reduce function, but return the results
   * immediately to the master as a Map. This will also perform the merging locally on each mapper
   * before sending results to a reducer, similarly to a "combiner" in MapReduce.
   */
  public  scala.collection.Map<K, V> reduceByKeyLocally (scala.Function2<V, V, V> func) { throw new RuntimeException(); }
  /** Alias for reduceByKeyLocally */
  public  scala.collection.Map<K, V> reduceByKeyToDriver (scala.Function2<V, V, V> func) { throw new RuntimeException(); }
  /** Count the number of elements for each key, and return the result to the master as a Map. */
  public  scala.collection.Map<K, java.lang.Object> countByKey () { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Approximate version of countByKey that can return a partial result if it does
   * not finish within a timeout.
   */
  public  org.apache.spark.partial.PartialResult<scala.collection.Map<K, org.apache.spark.partial.BoundedDouble>> countByKeyApprox (long timeout, double confidence) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * <p>
   * Return approximate number of distinct values for each key in this RDD.
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
   * @param partitioner Partitioner to use for the resulting RDD.
   */
  public  org.apache.spark.rdd.RDD<scala.Tuple2<K, java.lang.Object>> countApproxDistinctByKey (int p, int sp, org.apache.spark.Partitioner partitioner) { throw new RuntimeException(); }
  /**
   * Return approximate number of distinct values for each key in this RDD.
   * <p>
   * The algorithm used is based on streamlib's implementation of "HyperLogLog in Practice:
   * Algorithmic Engineering of a State of The Art Cardinality Estimation Algorithm", available
   * <a href="http://dx.doi.org/10.1145/2452376.2452456">here</a>.
   * <p>
   * @param relativeSD Relative accuracy. Smaller values create counters that require more space.
   *                   It must be greater than 0.000017.
   * @param partitioner partitioner of the resulting RDD
   */
  public  org.apache.spark.rdd.RDD<scala.Tuple2<K, java.lang.Object>> countApproxDistinctByKey (double relativeSD, org.apache.spark.Partitioner partitioner) { throw new RuntimeException(); }
  /**
   * Return approximate number of distinct values for each key in this RDD.
   * <p>
   * The algorithm used is based on streamlib's implementation of "HyperLogLog in Practice:
   * Algorithmic Engineering of a State of The Art Cardinality Estimation Algorithm", available
   * <a href="http://dx.doi.org/10.1145/2452376.2452456">here</a>.
   * <p>
   * @param relativeSD Relative accuracy. Smaller values create counters that require more space.
   *                   It must be greater than 0.000017.
   * @param numPartitions number of partitions of the resulting RDD
   */
  public  org.apache.spark.rdd.RDD<scala.Tuple2<K, java.lang.Object>> countApproxDistinctByKey (double relativeSD, int numPartitions) { throw new RuntimeException(); }
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
  public  org.apache.spark.rdd.RDD<scala.Tuple2<K, java.lang.Object>> countApproxDistinctByKey (double relativeSD) { throw new RuntimeException(); }
  /**
   * Group the values for each key in the RDD into a single sequence. Allows controlling the
   * partitioning of the resulting key-value pair RDD by passing a Partitioner.
   * <p>
   * Note: This operation may be very expensive. If you are grouping in order to perform an
   * aggregation (such as a sum or average) over each key, using {@link PairRDDFunctions.aggregateByKey}
   * or {@link PairRDDFunctions.reduceByKey} will provide much better performance.
   */
  public  org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.collection.Iterable<V>>> groupByKey (org.apache.spark.Partitioner partitioner) { throw new RuntimeException(); }
  /**
   * Group the values for each key in the RDD into a single sequence. Hash-partitions the
   * resulting RDD with into <code>numPartitions</code> partitions.
   * <p>
   * Note: This operation may be very expensive. If you are grouping in order to perform an
   * aggregation (such as a sum or average) over each key, using {@link PairRDDFunctions.aggregateByKey}
   * or {@link PairRDDFunctions.reduceByKey} will provide much better performance.
   */
  public  org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.collection.Iterable<V>>> groupByKey (int numPartitions) { throw new RuntimeException(); }
  /**
   * Return a copy of the RDD partitioned using the specified partitioner.
   */
  public  org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> partitionBy (org.apache.spark.Partitioner partitioner) { throw new RuntimeException(); }
  /**
   * Return an RDD containing all pairs of elements with matching keys in <code>this</code> and <code>other</code>. Each
   * pair of elements will be returned as a (k, (v1, v2)) tuple, where (k, v1) is in <code>this</code> and
   * (k, v2) is in <code>other</code>. Uses the given Partitioner to partition the output RDD.
   */
  public <W extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple2<V, W>>> join (org.apache.spark.rdd.RDD<scala.Tuple2<K, W>> other, org.apache.spark.Partitioner partitioner) { throw new RuntimeException(); }
  /**
   * Perform a left outer join of <code>this</code> and <code>other</code>. For each element (k, v) in <code>this</code>, the
   * resulting RDD will either contain all pairs (k, (v, Some(w))) for w in <code>other</code>, or the
   * pair (k, (v, None)) if no elements in <code>other</code> have key k. Uses the given Partitioner to
   * partition the output RDD.
   */
  public <W extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple2<V, scala.Option<W>>>> leftOuterJoin (org.apache.spark.rdd.RDD<scala.Tuple2<K, W>> other, org.apache.spark.Partitioner partitioner) { throw new RuntimeException(); }
  /**
   * Perform a right outer join of <code>this</code> and <code>other</code>. For each element (k, w) in <code>other</code>, the
   * resulting RDD will either contain all pairs (k, (Some(v), w)) for v in <code>this</code>, or the
   * pair (k, (None, w)) if no elements in <code>this</code> have key k. Uses the given Partitioner to
   * partition the output RDD.
   */
  public <W extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple2<scala.Option<V>, W>>> rightOuterJoin (org.apache.spark.rdd.RDD<scala.Tuple2<K, W>> other, org.apache.spark.Partitioner partitioner) { throw new RuntimeException(); }
  /**
   * Simplified version of combineByKey that hash-partitions the resulting RDD using the
   * existing partitioner/parallelism level.
   */
  public <C extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, C>> combineByKey (scala.Function1<V, C> createCombiner, scala.Function2<C, V, C> mergeValue, scala.Function2<C, C, C> mergeCombiners) { throw new RuntimeException(); }
  /**
   * Group the values for each key in the RDD into a single sequence. Hash-partitions the
   * resulting RDD with the existing partitioner/parallelism level.
   * <p>
   * Note: This operation may be very expensive. If you are grouping in order to perform an
   * aggregation (such as a sum or average) over each key, using {@link PairRDDFunctions.aggregateByKey}
   * or {@link PairRDDFunctions.reduceByKey} will provide much better performance.
   */
  public  org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.collection.Iterable<V>>> groupByKey () { throw new RuntimeException(); }
  /**
   * Return an RDD containing all pairs of elements with matching keys in <code>this</code> and <code>other</code>. Each
   * pair of elements will be returned as a (k, (v1, v2)) tuple, where (k, v1) is in <code>this</code> and
   * (k, v2) is in <code>other</code>. Performs a hash join across the cluster.
   */
  public <W extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple2<V, W>>> join (org.apache.spark.rdd.RDD<scala.Tuple2<K, W>> other) { throw new RuntimeException(); }
  /**
   * Return an RDD containing all pairs of elements with matching keys in <code>this</code> and <code>other</code>. Each
   * pair of elements will be returned as a (k, (v1, v2)) tuple, where (k, v1) is in <code>this</code> and
   * (k, v2) is in <code>other</code>. Performs a hash join across the cluster.
   */
  public <W extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple2<V, W>>> join (org.apache.spark.rdd.RDD<scala.Tuple2<K, W>> other, int numPartitions) { throw new RuntimeException(); }
  /**
   * Perform a left outer join of <code>this</code> and <code>other</code>. For each element (k, v) in <code>this</code>, the
   * resulting RDD will either contain all pairs (k, (v, Some(w))) for w in <code>other</code>, or the
   * pair (k, (v, None)) if no elements in <code>other</code> have key k. Hash-partitions the output
   * using the existing partitioner/parallelism level.
   */
  public <W extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple2<V, scala.Option<W>>>> leftOuterJoin (org.apache.spark.rdd.RDD<scala.Tuple2<K, W>> other) { throw new RuntimeException(); }
  /**
   * Perform a left outer join of <code>this</code> and <code>other</code>. For each element (k, v) in <code>this</code>, the
   * resulting RDD will either contain all pairs (k, (v, Some(w))) for w in <code>other</code>, or the
   * pair (k, (v, None)) if no elements in <code>other</code> have key k. Hash-partitions the output
   * into <code>numPartitions</code> partitions.
   */
  public <W extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple2<V, scala.Option<W>>>> leftOuterJoin (org.apache.spark.rdd.RDD<scala.Tuple2<K, W>> other, int numPartitions) { throw new RuntimeException(); }
  /**
   * Perform a right outer join of <code>this</code> and <code>other</code>. For each element (k, w) in <code>other</code>, the
   * resulting RDD will either contain all pairs (k, (Some(v), w)) for v in <code>this</code>, or the
   * pair (k, (None, w)) if no elements in <code>this</code> have key k. Hash-partitions the resulting
   * RDD using the existing partitioner/parallelism level.
   */
  public <W extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple2<scala.Option<V>, W>>> rightOuterJoin (org.apache.spark.rdd.RDD<scala.Tuple2<K, W>> other) { throw new RuntimeException(); }
  /**
   * Perform a right outer join of <code>this</code> and <code>other</code>. For each element (k, w) in <code>other</code>, the
   * resulting RDD will either contain all pairs (k, (Some(v), w)) for v in <code>this</code>, or the
   * pair (k, (None, w)) if no elements in <code>this</code> have key k. Hash-partitions the resulting
   * RDD into the given number of partitions.
   */
  public <W extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple2<scala.Option<V>, W>>> rightOuterJoin (org.apache.spark.rdd.RDD<scala.Tuple2<K, W>> other, int numPartitions) { throw new RuntimeException(); }
  /**
   * Return the key-value pairs in this RDD to the master as a Map.
   * <p>
   * Warning: this doesn't return a multimap (so if you have multiple values to the same key, only
   *          one value per key is preserved in the map returned)
   */
  public  scala.collection.Map<K, V> collectAsMap () { throw new RuntimeException(); }
  /**
   * Pass each value in the key-value pair RDD through a map function without changing the keys;
   * this also retains the original RDD's partitioning.
   */
  public <U extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, U>> mapValues (scala.Function1<V, U> f) { throw new RuntimeException(); }
  /**
   * Pass each value in the key-value pair RDD through a flatMap function without changing the
   * keys; this also retains the original RDD's partitioning.
   */
  public <U extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, U>> flatMapValues (scala.Function1<V, scala.collection.TraversableOnce<U>> f) { throw new RuntimeException(); }
  /**
   * For each key k in <code>this</code> or <code>other1</code> or <code>other2</code> or <code>other3</code>,
   * return a resulting RDD that contains a tuple with the list of values
   * for that key in <code>this</code>, <code>other1</code>, <code>other2</code> and <code>other3</code>.
   */
  public <W1 extends java.lang.Object, W2 extends java.lang.Object, W3 extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple4<scala.collection.Iterable<V>, scala.collection.Iterable<W1>, scala.collection.Iterable<W2>, scala.collection.Iterable<W3>>>> cogroup (org.apache.spark.rdd.RDD<scala.Tuple2<K, W1>> other1, org.apache.spark.rdd.RDD<scala.Tuple2<K, W2>> other2, org.apache.spark.rdd.RDD<scala.Tuple2<K, W3>> other3, org.apache.spark.Partitioner partitioner) { throw new RuntimeException(); }
  // not preceding
  public <W extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple2<scala.collection.Iterable<V>, scala.collection.Iterable<W>>>> cogroup (org.apache.spark.rdd.RDD<scala.Tuple2<K, W>> other, org.apache.spark.Partitioner partitioner) { throw new RuntimeException(); }
  // not preceding
  public <W1 extends java.lang.Object, W2 extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple3<scala.collection.Iterable<V>, scala.collection.Iterable<W1>, scala.collection.Iterable<W2>>>> cogroup (org.apache.spark.rdd.RDD<scala.Tuple2<K, W1>> other1, org.apache.spark.rdd.RDD<scala.Tuple2<K, W2>> other2, org.apache.spark.Partitioner partitioner) { throw new RuntimeException(); }
  // not preceding
  public <W1 extends java.lang.Object, W2 extends java.lang.Object, W3 extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple4<scala.collection.Iterable<V>, scala.collection.Iterable<W1>, scala.collection.Iterable<W2>, scala.collection.Iterable<W3>>>> cogroup (org.apache.spark.rdd.RDD<scala.Tuple2<K, W1>> other1, org.apache.spark.rdd.RDD<scala.Tuple2<K, W2>> other2, org.apache.spark.rdd.RDD<scala.Tuple2<K, W3>> other3) { throw new RuntimeException(); }
  /**
   * For each key k in <code>this</code> or <code>other</code>, return a resulting RDD that contains a tuple with the
   * list of values for that key in <code>this</code> as well as <code>other</code>.
   */
  public <W extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple2<scala.collection.Iterable<V>, scala.collection.Iterable<W>>>> cogroup (org.apache.spark.rdd.RDD<scala.Tuple2<K, W>> other) { throw new RuntimeException(); }
  /**
   * For each key k in <code>this</code> or <code>other1</code> or <code>other2</code>, return a resulting RDD that contains a
   * tuple with the list of values for that key in <code>this</code>, <code>other1</code> and <code>other2</code>.
   */
  public <W1 extends java.lang.Object, W2 extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple3<scala.collection.Iterable<V>, scala.collection.Iterable<W1>, scala.collection.Iterable<W2>>>> cogroup (org.apache.spark.rdd.RDD<scala.Tuple2<K, W1>> other1, org.apache.spark.rdd.RDD<scala.Tuple2<K, W2>> other2) { throw new RuntimeException(); }
  /**
   * For each key k in <code>this</code> or <code>other</code>, return a resulting RDD that contains a tuple with the
   * list of values for that key in <code>this</code> as well as <code>other</code>.
   */
  public <W extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple2<scala.collection.Iterable<V>, scala.collection.Iterable<W>>>> cogroup (org.apache.spark.rdd.RDD<scala.Tuple2<K, W>> other, int numPartitions) { throw new RuntimeException(); }
  /**
   * For each key k in <code>this</code> or <code>other1</code> or <code>other2</code>, return a resulting RDD that contains a
   * tuple with the list of values for that key in <code>this</code>, <code>other1</code> and <code>other2</code>.
   */
  public <W1 extends java.lang.Object, W2 extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple3<scala.collection.Iterable<V>, scala.collection.Iterable<W1>, scala.collection.Iterable<W2>>>> cogroup (org.apache.spark.rdd.RDD<scala.Tuple2<K, W1>> other1, org.apache.spark.rdd.RDD<scala.Tuple2<K, W2>> other2, int numPartitions) { throw new RuntimeException(); }
  /**
   * For each key k in <code>this</code> or <code>other1</code> or <code>other2</code> or <code>other3</code>,
   * return a resulting RDD that contains a tuple with the list of values
   * for that key in <code>this</code>, <code>other1</code>, <code>other2</code> and <code>other3</code>.
   */
  public <W1 extends java.lang.Object, W2 extends java.lang.Object, W3 extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple4<scala.collection.Iterable<V>, scala.collection.Iterable<W1>, scala.collection.Iterable<W2>, scala.collection.Iterable<W3>>>> cogroup (org.apache.spark.rdd.RDD<scala.Tuple2<K, W1>> other1, org.apache.spark.rdd.RDD<scala.Tuple2<K, W2>> other2, org.apache.spark.rdd.RDD<scala.Tuple2<K, W3>> other3, int numPartitions) { throw new RuntimeException(); }
  /** Alias for cogroup. */
  public <W extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple2<scala.collection.Iterable<V>, scala.collection.Iterable<W>>>> groupWith (org.apache.spark.rdd.RDD<scala.Tuple2<K, W>> other) { throw new RuntimeException(); }
  /** Alias for cogroup. */
  public <W1 extends java.lang.Object, W2 extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple3<scala.collection.Iterable<V>, scala.collection.Iterable<W1>, scala.collection.Iterable<W2>>>> groupWith (org.apache.spark.rdd.RDD<scala.Tuple2<K, W1>> other1, org.apache.spark.rdd.RDD<scala.Tuple2<K, W2>> other2) { throw new RuntimeException(); }
  /** Alias for cogroup. */
  public <W1 extends java.lang.Object, W2 extends java.lang.Object, W3 extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, scala.Tuple4<scala.collection.Iterable<V>, scala.collection.Iterable<W1>, scala.collection.Iterable<W2>, scala.collection.Iterable<W3>>>> groupWith (org.apache.spark.rdd.RDD<scala.Tuple2<K, W1>> other1, org.apache.spark.rdd.RDD<scala.Tuple2<K, W2>> other2, org.apache.spark.rdd.RDD<scala.Tuple2<K, W3>> other3) { throw new RuntimeException(); }
  /**
   * Return an RDD with the pairs from <code>this</code> whose keys are not in <code>other</code>.
   * <p>
   * Uses <code>this</code> partitioner/partition size, because even if <code>other</code> is huge, the resulting
   * RDD will be <= us.
   */
  public <W extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> subtractByKey (org.apache.spark.rdd.RDD<scala.Tuple2<K, W>> other, scala.reflect.ClassTag<W> evidence$4) { throw new RuntimeException(); }
  /** Return an RDD with the pairs from `this` whose keys are not in `other`. */
  public <W extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> subtractByKey (org.apache.spark.rdd.RDD<scala.Tuple2<K, W>> other, int numPartitions, scala.reflect.ClassTag<W> evidence$5) { throw new RuntimeException(); }
  /** Return an RDD with the pairs from `this` whose keys are not in `other`. */
  public <W extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> subtractByKey (org.apache.spark.rdd.RDD<scala.Tuple2<K, W>> other, org.apache.spark.Partitioner p, scala.reflect.ClassTag<W> evidence$6) { throw new RuntimeException(); }
  /**
   * Return the list of values in the RDD for key <code>key</code>. This operation is done efficiently if the
   * RDD has a known partitioner by only searching the partition that the key maps to.
   */
  public  scala.collection.Seq<V> lookup (K key) { throw new RuntimeException(); }
  /**
   * Output the RDD to any Hadoop-supported file system, using a Hadoop <code>OutputFormat</code> class
   * supporting the key and value types K and V in this RDD.
   */
  public <F extends org.apache.hadoop.mapred.OutputFormat<K, V>> void saveAsHadoopFile (java.lang.String path, scala.reflect.ClassTag<F> fm) { throw new RuntimeException(); }
  /**
   * Output the RDD to any Hadoop-supported file system, using a Hadoop <code>OutputFormat</code> class
   * supporting the key and value types K and V in this RDD. Compress the result with the
   * supplied codec.
   */
  public <F extends org.apache.hadoop.mapred.OutputFormat<K, V>> void saveAsHadoopFile (java.lang.String path, java.lang.Class<? extends org.apache.hadoop.io.compress.CompressionCodec> codec, scala.reflect.ClassTag<F> fm) { throw new RuntimeException(); }
  /**
   * Output the RDD to any Hadoop-supported file system, using a new Hadoop API <code>OutputFormat</code>
   * (mapreduce.OutputFormat) object supporting the key and value types K and V in this RDD.
   */
  public <F extends org.apache.hadoop.mapreduce.OutputFormat<K, V>> void saveAsNewAPIHadoopFile (java.lang.String path, scala.reflect.ClassTag<F> fm) { throw new RuntimeException(); }
  /**
   * Output the RDD to any Hadoop-supported file system, using a new Hadoop API <code>OutputFormat</code>
   * (mapreduce.OutputFormat) object supporting the key and value types K and V in this RDD.
   */
  public  void saveAsNewAPIHadoopFile (java.lang.String path, java.lang.Class<?> keyClass, java.lang.Class<?> valueClass, java.lang.Class<? extends org.apache.hadoop.mapreduce.OutputFormat<?, ?>> outputFormatClass, org.apache.hadoop.conf.Configuration conf) { throw new RuntimeException(); }
  /**
   * Output the RDD to any Hadoop-supported file system, using a Hadoop <code>OutputFormat</code> class
   * supporting the key and value types K and V in this RDD. Compress with the supplied codec.
   */
  public  void saveAsHadoopFile (java.lang.String path, java.lang.Class<?> keyClass, java.lang.Class<?> valueClass, java.lang.Class<? extends org.apache.hadoop.mapred.OutputFormat<?, ?>> outputFormatClass, java.lang.Class<? extends org.apache.hadoop.io.compress.CompressionCodec> codec) { throw new RuntimeException(); }
  /**
   * Output the RDD to any Hadoop-supported file system, using a Hadoop <code>OutputFormat</code> class
   * supporting the key and value types K and V in this RDD.
   */
  public  void saveAsHadoopFile (java.lang.String path, java.lang.Class<?> keyClass, java.lang.Class<?> valueClass, java.lang.Class<? extends org.apache.hadoop.mapred.OutputFormat<?, ?>> outputFormatClass, org.apache.hadoop.mapred.JobConf conf, scala.Option<java.lang.Class<? extends org.apache.hadoop.io.compress.CompressionCodec>> codec) { throw new RuntimeException(); }
  /**
   * Output the RDD to any Hadoop-supported storage system with new Hadoop API, using a Hadoop
   * Configuration object for that storage system. The Conf should set an OutputFormat and any
   * output paths required (e.g. a table name to write to) in the same way as it would be
   * configured for a Hadoop MapReduce job.
   */
  public  void saveAsNewAPIHadoopDataset (org.apache.hadoop.conf.Configuration conf) { throw new RuntimeException(); }
  /**
   * Output the RDD to any Hadoop-supported storage system, using a Hadoop JobConf object for
   * that storage system. The JobConf should set an OutputFormat and any output paths required
   * (e.g. a table name to write to) in the same way as it would be configured for a Hadoop
   * MapReduce job.
   */
  public  void saveAsHadoopDataset (org.apache.hadoop.mapred.JobConf conf) { throw new RuntimeException(); }
  /**
   * Return an RDD with the keys of each tuple.
   */
  public  org.apache.spark.rdd.RDD<K> keys () { throw new RuntimeException(); }
  /**
   * Return an RDD with the values of each tuple.
   */
  public  org.apache.spark.rdd.RDD<V> values () { throw new RuntimeException(); }
  private  java.lang.Class<?> keyClass () { throw new RuntimeException(); }
  private  java.lang.Class<?> valueClass () { throw new RuntimeException(); }
  private  scala.Option<scala.math.Ordering<K>> keyOrdering () { throw new RuntimeException(); }
}
