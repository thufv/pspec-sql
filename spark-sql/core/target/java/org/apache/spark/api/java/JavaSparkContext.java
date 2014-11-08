package org.apache.spark.api.java;
/**
 * A Java-friendly version of {@link org.apache.spark.SparkContext} that returns
 * {@link org.apache.spark.api.java.JavaRDD}s and works with Java collections instead of Scala ones.
 */
public  class JavaSparkContext extends org.apache.spark.api.java.JavaSparkContextVarargsWorkaround {
  static public  org.apache.spark.api.java.JavaSparkContext fromSparkContext (org.apache.spark.SparkContext sc) { throw new RuntimeException(); }
  static public  org.apache.spark.SparkContext toSparkContext (org.apache.spark.api.java.JavaSparkContext jsc) { throw new RuntimeException(); }
  /**
   * Find the JAR from which a given class was loaded, to make it easy for users to pass
   * their JARs to SparkContext.
   */
  static public  java.lang.String[] jarOfClass (java.lang.Class<?> cls) { throw new RuntimeException(); }
  /**
   * Find the JAR that contains the class of a particular object, to make it easy for users
   * to pass their JARs to SparkContext. In most cases you can call jarOfObject(this) in
   * your driver program.
   */
  static public  java.lang.String[] jarOfObject (java.lang.Object obj) { throw new RuntimeException(); }
  /**
   * Produces a ClassTag[T], which is actually just a casted ClassTag[AnyRef].
   * <p>
   * This method is used to keep ClassTags out of the external Java API, as the Java compiler
   * cannot produce them automatically. While this ClassTag-faking does please the compiler,
   * it can cause problems at runtime if the Scala API relies on ClassTags for correctness.
   * <p>
   * Often, though, a ClassTag[AnyRef] will not lead to incorrect behavior, just worse performance
   * or security issues. For instance, an Array[AnyRef] can hold any type T, but may lose primitive
   * specialization.
   */
  static private <T extends java.lang.Object> scala.reflect.ClassTag<T> fakeClassTag () { throw new RuntimeException(); }
  public  org.apache.spark.SparkContext sc () { throw new RuntimeException(); }
  // not preceding
  public   JavaSparkContext (org.apache.spark.SparkContext sc) { throw new RuntimeException(); }
  /**
   * Create a JavaSparkContext that loads settings from system properties (for instance, when
   * launching with ./bin/spark-submit).
   */
  public   JavaSparkContext () { throw new RuntimeException(); }
  /**
   * @param conf a {@link org.apache.spark.SparkConf} object specifying Spark parameters
   */
  public   JavaSparkContext (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  /**
   * @param master Cluster URL to connect to (e.g. mesos://host:port, spark://host:port, local[4]).
   * @param appName A name for your application, to display on the cluster web UI
   */
  public   JavaSparkContext (java.lang.String master, java.lang.String appName) { throw new RuntimeException(); }
  /**
   * @param master Cluster URL to connect to (e.g. mesos://host:port, spark://host:port, local[4]).
   * @param appName A name for your application, to display on the cluster web UI
   * @param conf a {@link org.apache.spark.SparkConf} object specifying other Spark parameters
   */
  public   JavaSparkContext (java.lang.String master, java.lang.String appName, org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  /**
   * @param master Cluster URL to connect to (e.g. mesos://host:port, spark://host:port, local[4]).
   * @param appName A name for your application, to display on the cluster web UI
   * @param sparkHome The SPARK_HOME directory on the slave nodes
   * @param jarFile JAR file to send to the cluster. This can be a path on the local file system
   *                or an HDFS, HTTP, HTTPS, or FTP URL.
   */
  public   JavaSparkContext (java.lang.String master, java.lang.String appName, java.lang.String sparkHome, java.lang.String jarFile) { throw new RuntimeException(); }
  /**
   * @param master Cluster URL to connect to (e.g. mesos://host:port, spark://host:port, local[4]).
   * @param appName A name for your application, to display on the cluster web UI
   * @param sparkHome The SPARK_HOME directory on the slave nodes
   * @param jars Collection of JARs to send to the cluster. These can be paths on the local file
   *             system or HDFS, HTTP, HTTPS, or FTP URLs.
   */
  public   JavaSparkContext (java.lang.String master, java.lang.String appName, java.lang.String sparkHome, java.lang.String[] jars) { throw new RuntimeException(); }
  /**
   * @param master Cluster URL to connect to (e.g. mesos://host:port, spark://host:port, local[4]).
   * @param appName A name for your application, to display on the cluster web UI
   * @param sparkHome The SPARK_HOME directory on the slave nodes
   * @param jars Collection of JARs to send to the cluster. These can be paths on the local file
   *             system or HDFS, HTTP, HTTPS, or FTP URLs.
   * @param environment Environment variables to set on worker nodes
   */
  public   JavaSparkContext (java.lang.String master, java.lang.String appName, java.lang.String sparkHome, java.lang.String[] jars, java.util.Map<java.lang.String, java.lang.String> environment) { throw new RuntimeException(); }
  public  org.apache.spark.SparkEnv env () { throw new RuntimeException(); }
  public  java.lang.Boolean isLocal () { throw new RuntimeException(); }
  public  java.lang.String sparkUser () { throw new RuntimeException(); }
  public  java.lang.String master () { throw new RuntimeException(); }
  public  java.lang.String appName () { throw new RuntimeException(); }
  public  java.util.List<java.lang.String> jars () { throw new RuntimeException(); }
  public  java.lang.Long startTime () { throw new RuntimeException(); }
  /** The version of Spark on which this application is running. */
  public  java.lang.String version () { throw new RuntimeException(); }
  /** Default level of parallelism to use when not given by user (e.g. parallelize and makeRDD). */
  public  java.lang.Integer defaultParallelism () { throw new RuntimeException(); }
  /**
   * Default min number of partitions for Hadoop RDDs when not given by user.
   * @deprecated As of Spark 1.0.0, defaultMinSplits is deprecated, use
   *            {@link #defaultMinPartitions()} instead
   */
  public  java.lang.Integer defaultMinSplits () { throw new RuntimeException(); }
  /** Default min number of partitions for Hadoop RDDs when not given by user */
  public  java.lang.Integer defaultMinPartitions () { throw new RuntimeException(); }
  /** Distribute a local Scala collection to form an RDD. */
  public <T extends java.lang.Object> org.apache.spark.api.java.JavaRDD<T> parallelize (java.util.List<T> list, int numSlices) { throw new RuntimeException(); }
  /** Get an RDD that has no partitions or elements. */
  public <T extends java.lang.Object> org.apache.spark.api.java.JavaRDD<T> emptyRDD () { throw new RuntimeException(); }
  /** Distribute a local Scala collection to form an RDD. */
  public <T extends java.lang.Object> org.apache.spark.api.java.JavaRDD<T> parallelize (java.util.List<T> list) { throw new RuntimeException(); }
  /** Distribute a local Scala collection to form an RDD. */
  public <K extends java.lang.Object, V extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, V> parallelizePairs (java.util.List<scala.Tuple2<K, V>> list, int numSlices) { throw new RuntimeException(); }
  /** Distribute a local Scala collection to form an RDD. */
  public <K extends java.lang.Object, V extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, V> parallelizePairs (java.util.List<scala.Tuple2<K, V>> list) { throw new RuntimeException(); }
  /** Distribute a local Scala collection to form an RDD. */
  public  org.apache.spark.api.java.JavaDoubleRDD parallelizeDoubles (java.util.List<java.lang.Double> list, int numSlices) { throw new RuntimeException(); }
  /** Distribute a local Scala collection to form an RDD. */
  public  org.apache.spark.api.java.JavaDoubleRDD parallelizeDoubles (java.util.List<java.lang.Double> list) { throw new RuntimeException(); }
  /**
   * Read a text file from HDFS, a local file system (available on all nodes), or any
   * Hadoop-supported file system URI, and return it as an RDD of Strings.
   */
  public  org.apache.spark.api.java.JavaRDD<java.lang.String> textFile (java.lang.String path) { throw new RuntimeException(); }
  /**
   * Read a text file from HDFS, a local file system (available on all nodes), or any
   * Hadoop-supported file system URI, and return it as an RDD of Strings.
   */
  public  org.apache.spark.api.java.JavaRDD<java.lang.String> textFile (java.lang.String path, int minPartitions) { throw new RuntimeException(); }
  /**
   * Read a directory of text files from HDFS, a local file system (available on all nodes), or any
   * Hadoop-supported file system URI. Each file is read as a single record and returned in a
   * key-value pair, where the key is the path of each file, the value is the content of each file.
   * <p>
   * <p> For example, if you have the following files:
   * <pre><code>
   *   hdfs://a-hdfs-path/part-00000
   *   hdfs://a-hdfs-path/part-00001
   *   ...
   *   hdfs://a-hdfs-path/part-nnnnn
   * </code></pre>
   * <p>
   * Do <code>JavaPairRDD<String, String> rdd = sparkContext.wholeTextFiles("hdfs://a-hdfs-path")</code>,
   * <p>
   * <p> then <code>rdd</code> contains
   * <pre><code>
   *   (a-hdfs-path/part-00000, its content)
   *   (a-hdfs-path/part-00001, its content)
   *   ...
   *   (a-hdfs-path/part-nnnnn, its content)
   * </code></pre>
   * <p>
   * @note Small files are preferred, large file is also allowable, but may cause bad performance.
   * <p>
   * @param minPartitions A suggestion value of the minimal splitting number for input data.
   */
  public  org.apache.spark.api.java.JavaPairRDD<java.lang.String, java.lang.String> wholeTextFiles (java.lang.String path, int minPartitions) { throw new RuntimeException(); }
  /**
   * Read a directory of text files from HDFS, a local file system (available on all nodes), or any
   * Hadoop-supported file system URI. Each file is read as a single record and returned in a
   * key-value pair, where the key is the path of each file, the value is the content of each file.
   * <p>
   * @see <code>wholeTextFiles(path: String, minPartitions: Int)</code>.
   */
  public  org.apache.spark.api.java.JavaPairRDD<java.lang.String, java.lang.String> wholeTextFiles (java.lang.String path) { throw new RuntimeException(); }
  /** Get an RDD for a Hadoop SequenceFile with given key and value types.
   * <p>
   * '''Note:''' Because Hadoop's RecordReader class re-uses the same Writable object for each
   * record, directly caching the returned RDD will create many references to the same object.
   * If you plan to directly cache Hadoop writable objects, you should first copy them using
   * a <code>map</code> function.
   * */
  public <K extends java.lang.Object, V extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, V> sequenceFile (java.lang.String path, java.lang.Class<K> keyClass, java.lang.Class<V> valueClass, int minPartitions) { throw new RuntimeException(); }
  /** Get an RDD for a Hadoop SequenceFile.
   * <p>
   * '''Note:''' Because Hadoop's RecordReader class re-uses the same Writable object for each
   * record, directly caching the returned RDD will create many references to the same object.
   * If you plan to directly cache Hadoop writable objects, you should first copy them using
   * a <code>map</code> function.
   */
  public <K extends java.lang.Object, V extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, V> sequenceFile (java.lang.String path, java.lang.Class<K> keyClass, java.lang.Class<V> valueClass) { throw new RuntimeException(); }
  /**
   * Load an RDD saved as a SequenceFile containing serialized objects, with NullWritable keys and
   * BytesWritable values that contain a serialized partition. This is still an experimental storage
   * format and may not be supported exactly as is in future Spark releases. It will also be pretty
   * slow if you use the default serializer (Java serialization), though the nice thing about it is
   * that there's very little effort required to save arbitrary objects.
   */
  public <T extends java.lang.Object> org.apache.spark.api.java.JavaRDD<T> objectFile (java.lang.String path, int minPartitions) { throw new RuntimeException(); }
  /**
   * Load an RDD saved as a SequenceFile containing serialized objects, with NullWritable keys and
   * BytesWritable values that contain a serialized partition. This is still an experimental storage
   * format and may not be supported exactly as is in future Spark releases. It will also be pretty
   * slow if you use the default serializer (Java serialization), though the nice thing about it is
   * that there's very little effort required to save arbitrary objects.
   */
  public <T extends java.lang.Object> org.apache.spark.api.java.JavaRDD<T> objectFile (java.lang.String path) { throw new RuntimeException(); }
  /**
   * Get an RDD for a Hadoop-readable dataset from a Hadooop JobConf giving its InputFormat and any
   * other necessary info (e.g. file name for a filesystem-based dataset, table name for HyperTable,
   * etc).
   * <p>
   * '''Note:''' Because Hadoop's RecordReader class re-uses the same Writable object for each
   * record, directly caching the returned RDD will create many references to the same object.
   * If you plan to directly cache Hadoop writable objects, you should first copy them using
   * a <code>map</code> function.
   */
  public <K extends java.lang.Object, V extends java.lang.Object, F extends org.apache.hadoop.mapred.InputFormat<K, V>> org.apache.spark.api.java.JavaPairRDD<K, V> hadoopRDD (org.apache.hadoop.mapred.JobConf conf, java.lang.Class<F> inputFormatClass, java.lang.Class<K> keyClass, java.lang.Class<V> valueClass, int minPartitions) { throw new RuntimeException(); }
  /**
   * Get an RDD for a Hadoop-readable dataset from a Hadooop JobConf giving its InputFormat and any
   * other necessary info (e.g. file name for a filesystem-based dataset, table name for HyperTable,
   * <p>
   * '''Note:''' Because Hadoop's RecordReader class re-uses the same Writable object for each
   * record, directly caching the returned RDD will create many references to the same object.
   * If you plan to directly cache Hadoop writable objects, you should first copy them using
   * a <code>map</code> function.
   */
  public <K extends java.lang.Object, V extends java.lang.Object, F extends org.apache.hadoop.mapred.InputFormat<K, V>> org.apache.spark.api.java.JavaPairRDD<K, V> hadoopRDD (org.apache.hadoop.mapred.JobConf conf, java.lang.Class<F> inputFormatClass, java.lang.Class<K> keyClass, java.lang.Class<V> valueClass) { throw new RuntimeException(); }
  /** Get an RDD for a Hadoop file with an arbitrary InputFormat.
   * <p>
   * '''Note:''' Because Hadoop's RecordReader class re-uses the same Writable object for each
   * record, directly caching the returned RDD will create many references to the same object.
   * If you plan to directly cache Hadoop writable objects, you should first copy them using
   * a <code>map</code> function.
   */
  public <K extends java.lang.Object, V extends java.lang.Object, F extends org.apache.hadoop.mapred.InputFormat<K, V>> org.apache.spark.api.java.JavaPairRDD<K, V> hadoopFile (java.lang.String path, java.lang.Class<F> inputFormatClass, java.lang.Class<K> keyClass, java.lang.Class<V> valueClass, int minPartitions) { throw new RuntimeException(); }
  /** Get an RDD for a Hadoop file with an arbitrary InputFormat
   * <p>
   * '''Note:''' Because Hadoop's RecordReader class re-uses the same Writable object for each
   * record, directly caching the returned RDD will create many references to the same object.
   * If you plan to directly cache Hadoop writable objects, you should first copy them using
   * a <code>map</code> function.
   */
  public <K extends java.lang.Object, V extends java.lang.Object, F extends org.apache.hadoop.mapred.InputFormat<K, V>> org.apache.spark.api.java.JavaPairRDD<K, V> hadoopFile (java.lang.String path, java.lang.Class<F> inputFormatClass, java.lang.Class<K> keyClass, java.lang.Class<V> valueClass) { throw new RuntimeException(); }
  /**
   * Get an RDD for a given Hadoop file with an arbitrary new API InputFormat
   * and extra configuration options to pass to the input format.
   * <p>
   * '''Note:''' Because Hadoop's RecordReader class re-uses the same Writable object for each
   * record, directly caching the returned RDD will create many references to the same object.
   * If you plan to directly cache Hadoop writable objects, you should first copy them using
   * a <code>map</code> function.
   */
  public <K extends java.lang.Object, V extends java.lang.Object, F extends org.apache.hadoop.mapreduce.InputFormat<K, V>> org.apache.spark.api.java.JavaPairRDD<K, V> newAPIHadoopFile (java.lang.String path, java.lang.Class<F> fClass, java.lang.Class<K> kClass, java.lang.Class<V> vClass, org.apache.hadoop.conf.Configuration conf) { throw new RuntimeException(); }
  /**
   * Get an RDD for a given Hadoop file with an arbitrary new API InputFormat
   * and extra configuration options to pass to the input format.
   * <p>
   * '''Note:''' Because Hadoop's RecordReader class re-uses the same Writable object for each
   * record, directly caching the returned RDD will create many references to the same object.
   * If you plan to directly cache Hadoop writable objects, you should first copy them using
   * a <code>map</code> function.
   */
  public <K extends java.lang.Object, V extends java.lang.Object, F extends org.apache.hadoop.mapreduce.InputFormat<K, V>> org.apache.spark.api.java.JavaPairRDD<K, V> newAPIHadoopRDD (org.apache.hadoop.conf.Configuration conf, java.lang.Class<F> fClass, java.lang.Class<K> kClass, java.lang.Class<V> vClass) { throw new RuntimeException(); }
  /** Build the union of two or more RDDs. */
  public <T extends java.lang.Object> org.apache.spark.api.java.JavaRDD<T> union (org.apache.spark.api.java.JavaRDD<T> first, java.util.List<org.apache.spark.api.java.JavaRDD<T>> rest) { throw new RuntimeException(); }
  /** Build the union of two or more RDDs. */
  public <K extends java.lang.Object, V extends java.lang.Object> org.apache.spark.api.java.JavaPairRDD<K, V> union (org.apache.spark.api.java.JavaPairRDD<K, V> first, java.util.List<org.apache.spark.api.java.JavaPairRDD<K, V>> rest) { throw new RuntimeException(); }
  /** Build the union of two or more RDDs. */
  public  org.apache.spark.api.java.JavaDoubleRDD union (org.apache.spark.api.java.JavaDoubleRDD first, java.util.List<org.apache.spark.api.java.JavaDoubleRDD> rest) { throw new RuntimeException(); }
  /**
   * Create an {@link org.apache.spark.Accumulator} integer variable, which tasks can "add" values
   * to using the <code>add</code> method. Only the master can access the accumulator's <code>value</code>.
   */
  public  org.apache.spark.Accumulator<java.lang.Integer> intAccumulator (int initialValue) { throw new RuntimeException(); }
  /**
   * Create an {@link org.apache.spark.Accumulator} integer variable, which tasks can "add" values
   * to using the <code>add</code> method. Only the master can access the accumulator's <code>value</code>.
   * <p>
   * This version supports naming the accumulator for display in Spark's web UI.
   */
  public  org.apache.spark.Accumulator<java.lang.Integer> intAccumulator (int initialValue, java.lang.String name) { throw new RuntimeException(); }
  /**
   * Create an {@link org.apache.spark.Accumulator} double variable, which tasks can "add" values
   * to using the <code>add</code> method. Only the master can access the accumulator's <code>value</code>.
   */
  public  org.apache.spark.Accumulator<java.lang.Double> doubleAccumulator (double initialValue) { throw new RuntimeException(); }
  /**
   * Create an {@link org.apache.spark.Accumulator} double variable, which tasks can "add" values
   * to using the <code>add</code> method. Only the master can access the accumulator's <code>value</code>.
   * <p>
   * This version supports naming the accumulator for display in Spark's web UI.
   */
  public  org.apache.spark.Accumulator<java.lang.Double> doubleAccumulator (double initialValue, java.lang.String name) { throw new RuntimeException(); }
  /**
   * Create an {@link org.apache.spark.Accumulator} integer variable, which tasks can "add" values
   * to using the <code>add</code> method. Only the master can access the accumulator's <code>value</code>.
   */
  public  org.apache.spark.Accumulator<java.lang.Integer> accumulator (int initialValue) { throw new RuntimeException(); }
  /**
   * Create an {@link org.apache.spark.Accumulator} integer variable, which tasks can "add" values
   * to using the <code>add</code> method. Only the master can access the accumulator's <code>value</code>.
   * <p>
   * This version supports naming the accumulator for display in Spark's web UI.
   */
  public  org.apache.spark.Accumulator<java.lang.Integer> accumulator (int initialValue, java.lang.String name) { throw new RuntimeException(); }
  /**
   * Create an {@link org.apache.spark.Accumulator} double variable, which tasks can "add" values
   * to using the <code>add</code> method. Only the master can access the accumulator's <code>value</code>.
   */
  public  org.apache.spark.Accumulator<java.lang.Double> accumulator (double initialValue) { throw new RuntimeException(); }
  /**
   * Create an {@link org.apache.spark.Accumulator} double variable, which tasks can "add" values
   * to using the <code>add</code> method. Only the master can access the accumulator's <code>value</code>.
   * <p>
   * This version supports naming the accumulator for display in Spark's web UI.
   */
  public  org.apache.spark.Accumulator<java.lang.Double> accumulator (double initialValue, java.lang.String name) { throw new RuntimeException(); }
  /**
   * Create an {@link org.apache.spark.Accumulator} variable of a given type, which tasks can "add"
   * values to using the <code>add</code> method. Only the master can access the accumulator's <code>value</code>.
   */
  public <T extends java.lang.Object> org.apache.spark.Accumulator<T> accumulator (T initialValue, org.apache.spark.AccumulatorParam<T> accumulatorParam) { throw new RuntimeException(); }
  /**
   * Create an {@link org.apache.spark.Accumulator} variable of a given type, which tasks can "add"
   * values to using the <code>add</code> method. Only the master can access the accumulator's <code>value</code>.
   * <p>
   * This version supports naming the accumulator for display in Spark's web UI.
   */
  public <T extends java.lang.Object> org.apache.spark.Accumulator<T> accumulator (T initialValue, java.lang.String name, org.apache.spark.AccumulatorParam<T> accumulatorParam) { throw new RuntimeException(); }
  /**
   * Create an {@link org.apache.spark.Accumulable} shared variable of the given type, to which tasks
   * can "add" values with <code>add</code>. Only the master can access the accumuable's <code>value</code>.
   */
  public <T extends java.lang.Object, R extends java.lang.Object> org.apache.spark.Accumulable<T, R> accumulable (T initialValue, org.apache.spark.AccumulableParam<T, R> param) { throw new RuntimeException(); }
  /**
   * Create an {@link org.apache.spark.Accumulable} shared variable of the given type, to which tasks
   * can "add" values with <code>add</code>. Only the master can access the accumuable's <code>value</code>.
   * <p>
   * This version supports naming the accumulator for display in Spark's web UI.
   */
  public <T extends java.lang.Object, R extends java.lang.Object> org.apache.spark.Accumulable<T, R> accumulable (T initialValue, java.lang.String name, org.apache.spark.AccumulableParam<T, R> param) { throw new RuntimeException(); }
  /**
   * Broadcast a read-only variable to the cluster, returning a
   * {@link org.apache.spark.broadcast.Broadcast} object for reading it in distributed functions.
   * The variable will be sent to each cluster only once.
   */
  public <T extends java.lang.Object> org.apache.spark.broadcast.Broadcast<T> broadcast (T value) { throw new RuntimeException(); }
  /** Shut down the SparkContext. */
  public  void stop () { throw new RuntimeException(); }
  /**
   * Get Spark's home location from either a value set through the constructor,
   * or the spark.home Java property, or the SPARK_HOME environment variable
   * (in that order of preference). If neither of these is set, return None.
   */
  public  com.google.common.base.Optional<java.lang.String> getSparkHome () { throw new RuntimeException(); }
  /**
   * Add a file to be downloaded with this Spark job on every node.
   * The <code>path</code> passed can be either a local file, a file in HDFS (or other Hadoop-supported
   * filesystems), or an HTTP, HTTPS or FTP URI.  To access the file in Spark jobs,
   * use <code>SparkFiles.get(path)</code> to find its download location.
   */
  public  void addFile (java.lang.String path) { throw new RuntimeException(); }
  /**
   * Adds a JAR dependency for all tasks to be executed on this SparkContext in the future.
   * The <code>path</code> passed can be either a local file, a file in HDFS (or other Hadoop-supported
   * filesystems), or an HTTP, HTTPS or FTP URI.
   */
  public  void addJar (java.lang.String path) { throw new RuntimeException(); }
  /**
   * Clear the job's list of JARs added by <code>addJar</code> so that they do not get downloaded to
   * any new nodes.
   */
  public  void clearJars () { throw new RuntimeException(); }
  /**
   * Clear the job's list of files added by <code>addFile</code> so that they do not get downloaded to
   * any new nodes.
   */
  public  void clearFiles () { throw new RuntimeException(); }
  /**
   * Returns the Hadoop configuration used for the Hadoop code (e.g. file systems) we reuse.
   */
  public  org.apache.hadoop.conf.Configuration hadoopConfiguration () { throw new RuntimeException(); }
  /**
   * Set the directory under which RDDs are going to be checkpointed. The directory must
   * be a HDFS path if running on a cluster.
   */
  public  void setCheckpointDir (java.lang.String dir) { throw new RuntimeException(); }
  public  com.google.common.base.Optional<java.lang.String> getCheckpointDir () { throw new RuntimeException(); }
  protected <T extends java.lang.Object> org.apache.spark.api.java.JavaRDD<T> checkpointFile (java.lang.String path) { throw new RuntimeException(); }
  /**
   * Return a copy of this JavaSparkContext's configuration. The configuration ''cannot'' be
   * changed at runtime.
   */
  public  org.apache.spark.SparkConf getConf () { throw new RuntimeException(); }
  /**
   * Pass-through to SparkContext.setCallSite.  For API support only.
   */
  public  void setCallSite (java.lang.String site) { throw new RuntimeException(); }
  /**
   * Pass-through to SparkContext.setCallSite.  For API support only.
   */
  public  void clearCallSite () { throw new RuntimeException(); }
  /**
   * Set a local property that affects jobs submitted from this thread, such as the
   * Spark fair scheduler pool.
   */
  public  void setLocalProperty (java.lang.String key, java.lang.String value) { throw new RuntimeException(); }
  /**
   * Get a local property set in this thread, or null if it is missing. See
   * {@link org.apache.spark.api.java.JavaSparkContext.setLocalProperty}.
   */
  public  java.lang.String getLocalProperty (java.lang.String key) { throw new RuntimeException(); }
  /**
   * Assigns a group ID to all the jobs started by this thread until the group ID is set to a
   * different value or cleared.
   * <p>
   * Often, a unit of execution in an application consists of multiple Spark actions or jobs.
   * Application programmers can use this method to group all those jobs together and give a
   * group description. Once set, the Spark web UI will associate such jobs with this group.
   * <p>
   * The application can also use {@link org.apache.spark.api.java.JavaSparkContext.cancelJobGroup}
   * to cancel all running jobs in this group. For example,
   * <pre><code>
   * // In the main thread:
   * sc.setJobGroup("some_job_to_cancel", "some job description");
   * rdd.map(...).count();
   *
   * // In a separate thread:
   * sc.cancelJobGroup("some_job_to_cancel");
   * </code></pre>
   * <p>
   * If interruptOnCancel is set to true for the job group, then job cancellation will result
   * in Thread.interrupt() being called on the job's executor threads. This is useful to help ensure
   * that the tasks are actually stopped in a timely manner, but is off by default due to HDFS-1208,
   * where HDFS may respond to Thread.interrupt() by marking nodes as dead.
   */
  public  void setJobGroup (java.lang.String groupId, java.lang.String description, boolean interruptOnCancel) { throw new RuntimeException(); }
  /**
   * Assigns a group ID to all the jobs started by this thread until the group ID is set to a
   * different value or cleared.
   * <p>
   * @see <code>setJobGroup(groupId: String, description: String, interruptThread: Boolean)</code>.
   *      This method sets interruptOnCancel to false.
   */
  public  void setJobGroup (java.lang.String groupId, java.lang.String description) { throw new RuntimeException(); }
  /** Clear the current thread's job group ID and its description. */
  public  void clearJobGroup () { throw new RuntimeException(); }
  /**
   * Cancel active jobs for the specified group. See
   * {@link org.apache.spark.api.java.JavaSparkContext.setJobGroup} for more information.
   */
  public  void cancelJobGroup (java.lang.String groupId) { throw new RuntimeException(); }
  /** Cancel all jobs that have been scheduled or are running. */
  public  void cancelAllJobs () { throw new RuntimeException(); }
}
