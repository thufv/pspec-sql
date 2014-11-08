package org.apache.spark.api.java;
public abstract interface JavaRDDLike<T extends java.lang.Object, This extends org.apache.spark.api.java.JavaRDDLike<T, This>> extends scala.Serializable {
  public abstract  This wrapRDD (org.apache.spark.rdd.RDD<T> rdd) ;
  public abstract  scala.reflect.ClassTag<T> classTag () ;
  public abstract  org.apache.spark.rdd.RDD<T> rdd () ;
  public  java.util.List<org.apache.spark.Partition> splits () ;
  /** Set of partitions in this RDD. */
  public  java.util.List<org.apache.spark.Partition> partitions () ;
  /** The {@link org.apache.spark.SparkContext} that this RDD was created on. */
  public  org.apache.spark.SparkContext context () ;
  /** A unique ID for this RDD (within its SparkContext). */
  public  int id () ;
  /** Get the RDD's current storage level, or StorageLevel.NONE if none is set. */
  public  org.apache.spark.storage.StorageLevel getStorageLevel () ;
  /**
   * Internal method to this RDD; will read from cache if applicable, or otherwise compute it.
   * This should ''not'' be called by users directly, but is available for implementors of custom
   * subclasses of RDD.
   */
  public  java.util.Iterator<T> iterator (org.apache.spark.Partition split, org.apache.spark.TaskContext taskContext) ;
  /**
   * Return a new RDD by applying a function to all elements of this RDD.
   */
  public <R extends java.lang.Object> org.apache.spark.api.java.JavaRDD<R> map (org.apache.spark.api.java.function.Function<T, R> f) ;
  /**
   * Return a new RDD by applying a function to each partition of this RDD, while tracking the index
   * of the original partition.
   */
  public <R extends java.lang.Object> org.apache.spark.api.java.JavaRDD<R> mapPartitionsWithIndex (org.apache.spark.api.java.function.Function2<java.lang.Integer, java.util.Iterator<T>, java.util.Iterator<R>> f, boolean preservesPartitioning) ;
  /**
   * Return a new RDD by applying a function to all elements of this RDD.
   */
  public <R extends java.lang.Object> org.apache.spark.api.java.JavaDoubleRDD mapToDouble (org.apache.spark.api.java.function.DoubleFunction<T> f) ;
  /**
   * Return a new RDD by applying a function to all elements of this RDD.
   */
  public <K2 extends java.lang.Object, V2 extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K2, V2> mapToPair (org.apache.spark.api.java.function.PairFunction<T, K2, V2> f) ;
  /**
   *  Return a new RDD by first applying a function to all elements of this
   *  RDD, and then flattening the results.
   */
  public <U extends java.lang.Object> org.apache.spark.api.java.JavaRDD<U> flatMap (org.apache.spark.api.java.function.FlatMapFunction<T, U> f) ;
  /**
   *  Return a new RDD by first applying a function to all elements of this
   *  RDD, and then flattening the results.
   */
  public  org.apache.spark.api.java.JavaDoubleRDD flatMapToDouble (org.apache.spark.api.java.function.DoubleFlatMapFunction<T> f) ;
  /**
   *  Return a new RDD by first applying a function to all elements of this
   *  RDD, and then flattening the results.
   */
  public <K2 extends java.lang.Object, V2 extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K2, V2> flatMapToPair (org.apache.spark.api.java.function.PairFlatMapFunction<T, K2, V2> f) ;
  /**
   * Return a new RDD by applying a function to each partition of this RDD.
   */
  public <U extends java.lang.Object> org.apache.spark.api.java.JavaRDD<U> mapPartitions (org.apache.spark.api.java.function.FlatMapFunction<java.util.Iterator<T>, U> f) ;
  /**
   * Return a new RDD by applying a function to each partition of this RDD.
   */
  public <U extends java.lang.Object> org.apache.spark.api.java.JavaRDD<U> mapPartitions (org.apache.spark.api.java.function.FlatMapFunction<java.util.Iterator<T>, U> f, boolean preservesPartitioning) ;
  /**
   * Return a new RDD by applying a function to each partition of this RDD.
   */
  public  org.apache.spark.api.java.JavaDoubleRDD mapPartitionsToDouble (org.apache.spark.api.java.function.DoubleFlatMapFunction<java.util.Iterator<T>> f) ;
  /**
   * Return a new RDD by applying a function to each partition of this RDD.
   */
  public <K2 extends java.lang.Object, V2 extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K2, V2> mapPartitionsToPair (org.apache.spark.api.java.function.PairFlatMapFunction<java.util.Iterator<T>, K2, V2> f) ;
  /**
   * Return a new RDD by applying a function to each partition of this RDD.
   */
  public  org.apache.spark.api.java.JavaDoubleRDD mapPartitionsToDouble (org.apache.spark.api.java.function.DoubleFlatMapFunction<java.util.Iterator<T>> f, boolean preservesPartitioning) ;
  /**
   * Return a new RDD by applying a function to each partition of this RDD.
   */
  public <K2 extends java.lang.Object, V2 extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K2, V2> mapPartitionsToPair (org.apache.spark.api.java.function.PairFlatMapFunction<java.util.Iterator<T>, K2, V2> f, boolean preservesPartitioning) ;
  /**
   * Applies a function f to each partition of this RDD.
   */
  public  void foreachPartition (org.apache.spark.api.java.function.VoidFunction<java.util.Iterator<T>> f) ;
  /**
   * Return an RDD created by coalescing all elements within each partition into an array.
   */
  public  org.apache.spark.api.java.JavaRDD<java.util.List<T>> glom () ;
  /**
   * Return the Cartesian product of this RDD and another one, that is, the RDD of all pairs of
   * elements (a, b) where a is in <code>this</code> and b is in <code>other</code>.
   */
  public <U extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<T, U> cartesian (org.apache.spark.api.java.JavaRDDLike<U, ?> other) ;
  /**
   * Return an RDD of grouped elements. Each group consists of a key and a sequence of elements
   * mapping to that key.
   */
  public <K extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, java.lang.Iterable<T>> groupBy (org.apache.spark.api.java.function.Function<T, K> f) ;
  /**
   * Return an RDD of grouped elements. Each group consists of a key and a sequence of elements
   * mapping to that key.
   */
  public <K extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, java.lang.Iterable<T>> groupBy (org.apache.spark.api.java.function.Function<T, K> f, int numPartitions) ;
  /**
   * Return an RDD created by piping elements to a forked external process.
   */
  public  org.apache.spark.api.java.JavaRDD<java.lang.String> pipe (java.lang.String command) ;
  /**
   * Return an RDD created by piping elements to a forked external process.
   */
  public  org.apache.spark.api.java.JavaRDD<java.lang.String> pipe (java.util.List<java.lang.String> command) ;
  /**
   * Return an RDD created by piping elements to a forked external process.
   */
  public  org.apache.spark.api.java.JavaRDD<java.lang.String> pipe (java.util.List<java.lang.String> command, java.util.Map<java.lang.String, java.lang.String> env) ;
  /**
   * Zips this RDD with another one, returning key-value pairs with the first element in each RDD,
   * second element in each RDD, etc. Assumes that the two RDDs have the *same number of
   * partitions* and the *same number of elements in each partition* (e.g. one was made through
   * a map on the other).
   */
  public <U extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<T, U> zip (org.apache.spark.api.java.JavaRDDLike<U, ?> other) ;
  /**
   * Zip this RDD's partitions with one (or more) RDD(s) and return a new RDD by
   * applying a function to the zipped partitions. Assumes that all the RDDs have the
   * *same number of partitions*, but does *not* require them to have the same number
   * of elements in each partition.
   */
  public <U extends java.lang.Object, V extends java.lang.Object> org.apache.spark.api.java.JavaRDD<V> zipPartitions (org.apache.spark.api.java.JavaRDDLike<U, ?> other, org.apache.spark.api.java.function.FlatMapFunction2<java.util.Iterator<T>, java.util.Iterator<U>, V> f) ;
  /**
   * Zips this RDD with generated unique Long ids. Items in the kth partition will get ids k, n+k,
   * 2*n+k, ..., where n is the number of partitions. So there may exist gaps, but this method
   * won't trigger a spark job, which is different from {@link org.apache.spark.rdd.RDD#zipWithIndex}.
   */
  public  org.apache.spark.api.java.JavaPairRDD<T, java.lang.Long> zipWithUniqueId () ;
  /**
   * Zips this RDD with its element indices. The ordering is first based on the partition index
   * and then the ordering of items within each partition. So the first item in the first
   * partition gets index 0, and the last item in the last partition receives the largest index.
   * This is similar to Scala's zipWithIndex but it uses Long instead of Int as the index type.
   * This method needs to trigger a spark job when this RDD contains more than one partitions.
   */
  public  org.apache.spark.api.java.JavaPairRDD<T, java.lang.Long> zipWithIndex () ;
  /**
   * Applies a function f to all elements of this RDD.
   */
  public  void foreach (org.apache.spark.api.java.function.VoidFunction<T> f) ;
  /**
   * Return an array that contains all of the elements in this RDD.
   */
  public  java.util.List<T> collect () ;
  /**
   * Return an iterator that contains all of the elements in this RDD.
   * <p>
   * The iterator will consume as much memory as the largest partition in this RDD.
   */
  public  java.util.Iterator<T> toLocalIterator () ;
  /**
   * Return an array that contains all of the elements in this RDD.
   * @deprecated As of Spark 1.0.0, toArray() is deprecated, use {@link #collect()} instead
   */
  public  java.util.List<T> toArray () ;
  /**
   * Return an array that contains all of the elements in a specific partition of this RDD.
   */
  public  java.util.List<T>[] collectPartitions (int[] partitionIds) ;
  /**
   * Reduces the elements of this RDD using the specified commutative and associative binary
   * operator.
   */
  public  T reduce (org.apache.spark.api.java.function.Function2<T, T, T> f) ;
  /**
   * Aggregate the elements of each partition, and then the results for all the partitions, using a
   * given associative function and a neutral "zero value". The function op(t1, t2) is allowed to
   * modify t1 and return it as its result value to avoid object allocation; however, it should not
   * modify t2.
   */
  public  T fold (T zeroValue, org.apache.spark.api.java.function.Function2<T, T, T> f) ;
  /**
   * Aggregate the elements of each partition, and then the results for all the partitions, using
   * given combine functions and a neutral "zero value". This function can return a different result
   * type, U, than the type of this RDD, T. Thus, we need one operation for merging a T into an U
   * and one operation for merging two U's, as in scala.TraversableOnce. Both of these functions are
   * allowed to modify and return their first argument instead of creating a new U to avoid memory
   * allocation.
   */
  public <U extends java.lang.Object> U aggregate (U zeroValue, org.apache.spark.api.java.function.Function2<U, T, U> seqOp, org.apache.spark.api.java.function.Function2<U, U, U> combOp) ;
  /**
   * Return the number of elements in the RDD.
   */
  public  long count () ;
  /**
   * :: Experimental ::
   * Approximate version of count() that returns a potentially incomplete result
   * within a timeout, even if not all tasks have finished.
   */
  public  org.apache.spark.partial.PartialResult<org.apache.spark.partial.BoundedDouble> countApprox (long timeout, double confidence) ;
  /**
   * :: Experimental ::
   * Approximate version of count() that returns a potentially incomplete result
   * within a timeout, even if not all tasks have finished.
   */
  public  org.apache.spark.partial.PartialResult<org.apache.spark.partial.BoundedDouble> countApprox (long timeout) ;
  /**
   * Return the count of each unique value in this RDD as a map of (value, count) pairs. The final
   * combine step happens locally on the master, equivalent to running a single reduce task.
   */
  public  java.util.Map<T, java.lang.Long> countByValue () ;
  /**
   * (Experimental) Approximate version of countByValue().
   */
  public  org.apache.spark.partial.PartialResult<java.util.Map<T, org.apache.spark.partial.BoundedDouble>> countByValueApprox (long timeout, double confidence) ;
  /**
   * (Experimental) Approximate version of countByValue().
   */
  public  org.apache.spark.partial.PartialResult<java.util.Map<T, org.apache.spark.partial.BoundedDouble>> countByValueApprox (long timeout) ;
  /**
   * Take the first num elements of the RDD. This currently scans the partitions *one by one*, so
   * it will be slow if a lot of partitions are required. In that case, use collect() to get the
   * whole RDD instead.
   */
  public  java.util.List<T> take (int num) ;
  public  java.util.List<T> takeSample (boolean withReplacement, int num) ;
  public  java.util.List<T> takeSample (boolean withReplacement, int num, long seed) ;
  /**
   * Return the first element in this RDD.
   */
  public  T first () ;
  /**
   * Save this RDD as a text file, using string representations of elements.
   */
  public  void saveAsTextFile (java.lang.String path) ;
  /**
   * Save this RDD as a compressed text file, using string representations of elements.
   */
  public  void saveAsTextFile (java.lang.String path, java.lang.Class<? extends org.apache.hadoop.io.compress.CompressionCodec> codec) ;
  /**
   * Save this RDD as a SequenceFile of serialized objects.
   */
  public  void saveAsObjectFile (java.lang.String path) ;
  /**
   * Creates tuples of the elements in this RDD by applying <code>f</code>.
   */
  public <K extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, T> keyBy (org.apache.spark.api.java.function.Function<T, K> f) ;
  /**
   * Mark this RDD for checkpointing. It will be saved to a file inside the checkpoint
   * directory set with SparkContext.setCheckpointDir() and all references to its parent
   * RDDs will be removed. This function must be called before any job has been
   * executed on this RDD. It is strongly recommended that this RDD is persisted in
   * memory, otherwise saving it on a file will require recomputation.
   */
  public  void checkpoint () ;
  /**
   * Return whether this RDD has been checkpointed or not
   */
  public  boolean isCheckpointed () ;
  /**
   * Gets the name of the file to which this RDD was checkpointed
   */
  public  com.google.common.base.Optional<java.lang.String> getCheckpointFile () ;
  /** A description of this RDD and its recursive dependencies for debugging. */
  public  java.lang.String toDebugString () ;
  /**
   * Returns the top K elements from this RDD as defined by
   * the specified Comparator[T].
   * @param num the number of top elements to return
   * @param comp the comparator that defines the order
   * @return an array of top elements
   */
  public  java.util.List<T> top (int num, java.util.Comparator<T> comp) ;
  /**
   * Returns the top K elements from this RDD using the
   * natural ordering for T.
   * @param num the number of top elements to return
   * @return an array of top elements
   */
  public  java.util.List<T> top (int num) ;
  /**
   * Returns the first K elements from this RDD as defined by
   * the specified Comparator[T] and maintains the order.
   * @param num the number of top elements to return
   * @param comp the comparator that defines the order
   * @return an array of top elements
   */
  public  java.util.List<T> takeOrdered (int num, java.util.Comparator<T> comp) ;
  /**
   * Returns the maximum element from this RDD as defined by the specified
   * Comparator[T].
   * @param comp the comparator that defines ordering
   * @return the maximum of the RDD
   * */
  public  T max (java.util.Comparator<T> comp) ;
  /**
   * Returns the minimum element from this RDD as defined by the specified
   * Comparator[T].
   * @param comp the comparator that defines ordering
   * @return the minimum of the RDD
   * */
  public  T min (java.util.Comparator<T> comp) ;
  /**
   * Returns the first K elements from this RDD using the
   * natural ordering for T while maintain the order.
   * @param num the number of top elements to return
   * @return an array of top elements
   */
  public  java.util.List<T> takeOrdered (int num) ;
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
  public  long countApproxDistinct (double relativeSD) ;
  public  java.lang.String name () ;
}
