package org.apache.spark;
/**
 * Main entry point for Spark functionality. A SparkContext represents the connection to a Spark
 * cluster, and can be used to create RDDs, accumulators and broadcast variables on that cluster.
 * <p>
 * @param config a Spark Config object describing the application configuration. Any settings in
 *   this config overrides the default configs as well as system properties.
 */
public  class SparkContext implements org.apache.spark.Logging {
  // no position
  static public  class DoubleAccumulatorParam$ implements org.apache.spark.AccumulatorParam<java.lang.Object> {
    public   DoubleAccumulatorParam$ () { throw new RuntimeException(); }
    public  double addInPlace (double t1, double t2) { throw new RuntimeException(); }
    public  double zero (double initialValue) { throw new RuntimeException(); }
  }
  // no position
  static public  class IntAccumulatorParam$ implements org.apache.spark.AccumulatorParam<java.lang.Object> {
    public   IntAccumulatorParam$ () { throw new RuntimeException(); }
    public  int addInPlace (int t1, int t2) { throw new RuntimeException(); }
    public  int zero (int initialValue) { throw new RuntimeException(); }
  }
  // no position
  static public  class LongAccumulatorParam$ implements org.apache.spark.AccumulatorParam<java.lang.Object> {
    public   LongAccumulatorParam$ () { throw new RuntimeException(); }
    public  long addInPlace (long t1, long t2) { throw new RuntimeException(); }
    public  long zero (long initialValue) { throw new RuntimeException(); }
  }
  // no position
  static public  class FloatAccumulatorParam$ implements org.apache.spark.AccumulatorParam<java.lang.Object> {
    public   FloatAccumulatorParam$ () { throw new RuntimeException(); }
    public  float addInPlace (float t1, float t2) { throw new RuntimeException(); }
    public  float zero (float initialValue) { throw new RuntimeException(); }
  }
  static public  java.lang.String SPARK_VERSION () { throw new RuntimeException(); }
  static public  java.lang.String SPARK_JOB_DESCRIPTION () { throw new RuntimeException(); }
  static public  java.lang.String SPARK_JOB_GROUP_ID () { throw new RuntimeException(); }
  static public  java.lang.String SPARK_JOB_INTERRUPT_ON_CANCEL () { throw new RuntimeException(); }
  static public  java.lang.String SPARK_UNKNOWN_USER () { throw new RuntimeException(); }
  static public <K extends java.lang.Object, V extends java.lang.Object> org.apache.spark.rdd.PairRDDFunctions<K, V> rddToPairRDDFunctions (org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> rdd, scala.reflect.ClassTag<K> kt, scala.reflect.ClassTag<V> vt, scala.math.Ordering<K> ord) { throw new RuntimeException(); }
  static public <T extends java.lang.Object> org.apache.spark.rdd.AsyncRDDActions<T> rddToAsyncRDDActions (org.apache.spark.rdd.RDD<T> rdd, scala.reflect.ClassTag<T> evidence$19) { throw new RuntimeException(); }
  static public <K extends java.lang.Object, V extends java.lang.Object> org.apache.spark.rdd.SequenceFileRDDFunctions<K, V> rddToSequenceFileRDDFunctions (org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> rdd, scala.Function1<K, org.apache.hadoop.io.Writable> evidence$20, scala.reflect.ClassTag<K> evidence$21, scala.Function1<V, org.apache.hadoop.io.Writable> evidence$22, scala.reflect.ClassTag<V> evidence$23) { throw new RuntimeException(); }
  static public <K extends java.lang.Object, V extends java.lang.Object> org.apache.spark.rdd.OrderedRDDFunctions<K, V, scala.Tuple2<K, V>> rddToOrderedRDDFunctions (org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> rdd, scala.math.Ordering<K> evidence$24, scala.reflect.ClassTag<K> evidence$25, scala.reflect.ClassTag<V> evidence$26) { throw new RuntimeException(); }
  static public  org.apache.spark.rdd.DoubleRDDFunctions doubleRDDToDoubleRDDFunctions (org.apache.spark.rdd.RDD<java.lang.Object> rdd) { throw new RuntimeException(); }
  static public <T extends java.lang.Object> org.apache.spark.rdd.DoubleRDDFunctions numericRDDToDoubleRDDFunctions (org.apache.spark.rdd.RDD<T> rdd, scala.math.Numeric<T> num) { throw new RuntimeException(); }
  static public  org.apache.hadoop.io.IntWritable intToIntWritable (int i) { throw new RuntimeException(); }
  static public  org.apache.hadoop.io.LongWritable longToLongWritable (long l) { throw new RuntimeException(); }
  static public  org.apache.hadoop.io.FloatWritable floatToFloatWritable (float f) { throw new RuntimeException(); }
  static public  org.apache.hadoop.io.DoubleWritable doubleToDoubleWritable (double d) { throw new RuntimeException(); }
  static public  org.apache.hadoop.io.BooleanWritable boolToBoolWritable (boolean b) { throw new RuntimeException(); }
  static public  org.apache.hadoop.io.BytesWritable bytesToBytesWritable (byte[] aob) { throw new RuntimeException(); }
  static public  org.apache.hadoop.io.Text stringToText (java.lang.String s) { throw new RuntimeException(); }
  static private <T extends java.lang.Object> org.apache.hadoop.io.ArrayWritable arrayToArrayWritable (scala.collection.Traversable<T> arr, scala.Function1<T, org.apache.hadoop.io.Writable> evidence$27, scala.reflect.ClassTag<T> evidence$28) { throw new RuntimeException(); }
  static private <T extends java.lang.Object, W extends org.apache.hadoop.io.Writable> org.apache.spark.WritableConverter<T> simpleWritableConverter (scala.Function1<W, T> convert, scala.reflect.ClassTag<W> evidence$30) { throw new RuntimeException(); }
  static public  org.apache.spark.WritableConverter<java.lang.Object> intWritableConverter () { throw new RuntimeException(); }
  static public  org.apache.spark.WritableConverter<java.lang.Object> longWritableConverter () { throw new RuntimeException(); }
  static public  org.apache.spark.WritableConverter<java.lang.Object> doubleWritableConverter () { throw new RuntimeException(); }
  static public  org.apache.spark.WritableConverter<java.lang.Object> floatWritableConverter () { throw new RuntimeException(); }
  static public  org.apache.spark.WritableConverter<java.lang.Object> booleanWritableConverter () { throw new RuntimeException(); }
  static public  org.apache.spark.WritableConverter<byte[]> bytesWritableConverter () { throw new RuntimeException(); }
  static public  org.apache.spark.WritableConverter<java.lang.String> stringWritableConverter () { throw new RuntimeException(); }
  static public <T extends org.apache.hadoop.io.Writable> org.apache.spark.WritableConverter<T> writableWritableConverter () { throw new RuntimeException(); }
  /**
   * Find the JAR from which a given class was loaded, to make it easy for users to pass
   * their JARs to SparkContext.
   */
  static public  scala.Option<java.lang.String> jarOfClass (java.lang.Class<?> cls) { throw new RuntimeException(); }
  /**
   * Find the JAR that contains the class of a particular object, to make it easy for users
   * to pass their JARs to SparkContext. In most cases you can call jarOfObject(this) in
   * your driver program.
   */
  static public  scala.Option<java.lang.String> jarOfObject (java.lang.Object obj) { throw new RuntimeException(); }
  /**
   * Creates a modified version of a SparkConf with the parameters that can be passed separately
   * to SparkContext, to make it easier to write SparkContext's constructors. This ignores
   * parameters that are passed as the default value of null, instead of throwing an exception
   * like SparkConf would.
   */
  static private  org.apache.spark.SparkConf updatedConf (org.apache.spark.SparkConf conf, java.lang.String master, java.lang.String appName, java.lang.String sparkHome, scala.collection.Seq<java.lang.String> jars, scala.collection.Map<java.lang.String, java.lang.String> environment) { throw new RuntimeException(); }
  /** Creates a task scheduler based on a given master URL. Extracted for testing. */
  static private  org.apache.spark.scheduler.TaskScheduler createTaskScheduler (org.apache.spark.SparkContext sc, java.lang.String master) { throw new RuntimeException(); }
  public   SparkContext (org.apache.spark.SparkConf config) { throw new RuntimeException(); }
  public  scala.collection.Map<java.lang.String, scala.collection.Set<org.apache.spark.scheduler.SplitInfo>> preferredNodeLocationData () { throw new RuntimeException(); }
  /**
   * Create a SparkContext that loads settings from system properties (for instance, when
   * launching with ./bin/spark-submit).
   */
  public   SparkContext () { throw new RuntimeException(); }
  /**
   * :: DeveloperApi ::
   * Alternative constructor for setting preferred locations where Spark will create executors.
   * <p>
   * @param preferredNodeLocationData used in YARN mode to select nodes to launch containers on.
   * Can be generated using {@link org.apache.spark.scheduler.InputFormatInfo.computePreferredLocations}
   * from a list of input files or InputFormats for the application.
   */
  public   SparkContext (org.apache.spark.SparkConf config, scala.collection.Map<java.lang.String, scala.collection.Set<org.apache.spark.scheduler.SplitInfo>> preferredNodeLocationData) { throw new RuntimeException(); }
  /**
   * Alternative constructor that allows setting common Spark properties directly
   * <p>
   * @param master Cluster URL to connect to (e.g. mesos://host:port, spark://host:port, local[4]).
   * @param appName A name for your application, to display on the cluster web UI
   * @param conf a {@link org.apache.spark.SparkConf} object specifying other Spark parameters
   */
  public   SparkContext (java.lang.String master, java.lang.String appName, org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  /**
   * Alternative constructor that allows setting common Spark properties directly
   * <p>
   * @param master Cluster URL to connect to (e.g. mesos://host:port, spark://host:port, local[4]).
   * @param appName A name for your application, to display on the cluster web UI.
   * @param sparkHome Location where Spark is installed on cluster nodes.
   * @param jars Collection of JARs to send to the cluster. These can be paths on the local file
   *             system or HDFS, HTTP, HTTPS, or FTP URLs.
   * @param environment Environment variables to set on worker nodes.
   */
  public   SparkContext (java.lang.String master, java.lang.String appName, java.lang.String sparkHome, scala.collection.Seq<java.lang.String> jars, scala.collection.Map<java.lang.String, java.lang.String> environment, scala.collection.Map<java.lang.String, scala.collection.Set<org.apache.spark.scheduler.SplitInfo>> preferredNodeLocationData) { throw new RuntimeException(); }
  /**
   * Alternative constructor that allows setting common Spark properties directly
   * <p>
   * @param master Cluster URL to connect to (e.g. mesos://host:port, spark://host:port, local[4]).
   * @param appName A name for your application, to display on the cluster web UI.
   */
  private   SparkContext (java.lang.String master, java.lang.String appName) { throw new RuntimeException(); }
  /**
   * Alternative constructor that allows setting common Spark properties directly
   * <p>
   * @param master Cluster URL to connect to (e.g. mesos://host:port, spark://host:port, local[4]).
   * @param appName A name for your application, to display on the cluster web UI.
   * @param sparkHome Location where Spark is installed on cluster nodes.
   */
  private   SparkContext (java.lang.String master, java.lang.String appName, java.lang.String sparkHome) { throw new RuntimeException(); }
  /**
   * Alternative constructor that allows setting common Spark properties directly
   * <p>
   * @param master Cluster URL to connect to (e.g. mesos://host:port, spark://host:port, local[4]).
   * @param appName A name for your application, to display on the cluster web UI.
   * @param sparkHome Location where Spark is installed on cluster nodes.
   * @param jars Collection of JARs to send to the cluster. These can be paths on the local file
   *             system or HDFS, HTTP, HTTPS, or FTP URLs.
   */
  private   SparkContext (java.lang.String master, java.lang.String appName, java.lang.String sparkHome, scala.collection.Seq<java.lang.String> jars) { throw new RuntimeException(); }
  public  org.apache.spark.SparkConf conf () { throw new RuntimeException(); }
  /**
   * Return a copy of this SparkContext's configuration. The configuration ''cannot'' be
   * changed at runtime.
   */
  public  org.apache.spark.SparkConf getConf () { throw new RuntimeException(); }
  public  scala.collection.Seq<java.lang.String> jars () { throw new RuntimeException(); }
  public  scala.collection.Seq<java.lang.String> files () { throw new RuntimeException(); }
  public  java.lang.String master () { throw new RuntimeException(); }
  public  java.lang.String appName () { throw new RuntimeException(); }
  public  java.lang.String tachyonFolderName () { throw new RuntimeException(); }
  public  boolean isLocal () { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.LiveListenerBus listenerBus () { throw new RuntimeException(); }
  public  org.apache.spark.SparkEnv env () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<java.lang.String, java.lang.Object> addedFiles () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<java.lang.String, java.lang.Object> addedJars () { throw new RuntimeException(); }
  public  org.apache.spark.util.TimeStampedWeakValueHashMap<java.lang.Object, org.apache.spark.rdd.RDD<?>> persistentRdds () { throw new RuntimeException(); }
  public  org.apache.spark.util.MetadataCleaner metadataCleaner () { throw new RuntimeException(); }
  public  org.apache.spark.ui.SparkUI ui () { throw new RuntimeException(); }
  /** A default Hadoop Configuration for the Hadoop code (e.g. file systems) that we reuse. */
  public  org.apache.hadoop.conf.Configuration hadoopConfiguration () { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.scheduler.EventLoggingListener> eventLogger () { throw new RuntimeException(); }
  public  long startTime () { throw new RuntimeException(); }
  private  java.lang.String warnSparkMem (java.lang.String value) { throw new RuntimeException(); }
  public  int executorMemory () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<java.lang.String, java.lang.String> executorEnvs () { throw new RuntimeException(); }
  public  java.lang.String sparkUser () { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.TaskScheduler taskScheduler () { throw new RuntimeException(); }
  private  akka.actor.ActorRef heartbeatReceiver () { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.DAGScheduler dagScheduler () { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.ContextCleaner> cleaner () { throw new RuntimeException(); }
  public  scala.Option<java.lang.String> checkpointDir () { throw new RuntimeException(); }
  private  java.lang.InheritableThreadLocal<java.util.Properties> localProperties () { throw new RuntimeException(); }
  private  java.util.Properties getLocalProperties () { throw new RuntimeException(); }
  private  void setLocalProperties (java.util.Properties props) { throw new RuntimeException(); }
  public  void initLocalProperties () { throw new RuntimeException(); }
  /**
   * Set a local property that affects jobs submitted from this thread, such as the
   * Spark fair scheduler pool.
   */
  public  void setLocalProperty (java.lang.String key, java.lang.String value) { throw new RuntimeException(); }
  /**
   * Get a local property set in this thread, or null if it is missing. See
   * {@link org.apache.spark.SparkContext.setLocalProperty}.
   */
  public  java.lang.String getLocalProperty (java.lang.String key) { throw new RuntimeException(); }
  /** Set a human readable description of the current job. */
  public  void setJobDescription (java.lang.String value) { throw new RuntimeException(); }
  /**
   * Assigns a group ID to all the jobs started by this thread until the group ID is set to a
   * different value or cleared.
   * <p>
   * Often, a unit of execution in an application consists of multiple Spark actions or jobs.
   * Application programmers can use this method to group all those jobs together and give a
   * group description. Once set, the Spark web UI will associate such jobs with this group.
   * <p>
   * The application can also use {@link org.apache.spark.SparkContext.cancelJobGroup} to cancel all
   * running jobs in this group. For example,
   * <pre><code>
   * // In the main thread:
   * sc.setJobGroup("some_job_to_cancel", "some job description")
   * sc.parallelize(1 to 10000, 2).map { i =&gt; Thread.sleep(10); i }.count()
   *
   * // In a separate thread:
   * sc.cancelJobGroup("some_job_to_cancel")
   * </code></pre>
   * <p>
   * If interruptOnCancel is set to true for the job group, then job cancellation will result
   * in Thread.interrupt() being called on the job's executor threads. This is useful to help ensure
   * that the tasks are actually stopped in a timely manner, but is off by default due to HDFS-1208,
   * where HDFS may respond to Thread.interrupt() by marking nodes as dead.
   */
  public  void setJobGroup (java.lang.String groupId, java.lang.String description, boolean interruptOnCancel) { throw new RuntimeException(); }
  /** Clear the current thread's job group ID and its description. */
  public  void clearJobGroup () { throw new RuntimeException(); }
  private  org.apache.spark.scheduler.DAGSchedulerSource dagSchedulerSource () { throw new RuntimeException(); }
  private  org.apache.spark.storage.BlockManagerSource blockManagerSource () { throw new RuntimeException(); }
  private  void initDriverMetrics () { throw new RuntimeException(); }
  /**
   * Distribute a local Scala collection to form an RDD.
   * <p>
   * @note Parallelize acts lazily. If <code>seq</code> is a mutable collection and is
   * altered after the call to parallelize and before the first action on the
   * RDD, the resultant RDD will reflect the modified collection. Pass a copy of
   * the argument to avoid this.
   */
  public <T extends java.lang.Object> org.apache.spark.rdd.RDD<T> parallelize (scala.collection.Seq<T> seq, int numSlices, scala.reflect.ClassTag<T> evidence$1) { throw new RuntimeException(); }
  /**
   * Distribute a local Scala collection to form an RDD.
   * <p>
   * This method is identical to <code>parallelize</code>.
   */
  public <T extends java.lang.Object> org.apache.spark.rdd.RDD<T> makeRDD (scala.collection.Seq<T> seq, int numSlices, scala.reflect.ClassTag<T> evidence$2) { throw new RuntimeException(); }
  /**
   * Distribute a local Scala collection to form an RDD, with one or more
   * location preferences (hostnames of Spark nodes) for each object.
   * Create a new partition for each collection item.
   */
  public <T extends java.lang.Object> org.apache.spark.rdd.RDD<T> makeRDD (scala.collection.Seq<scala.Tuple2<T, scala.collection.Seq<java.lang.String>>> seq, scala.reflect.ClassTag<T> evidence$3) { throw new RuntimeException(); }
  /**
   * Read a text file from HDFS, a local file system (available on all nodes), or any
   * Hadoop-supported file system URI, and return it as an RDD of Strings.
   */
  public  org.apache.spark.rdd.RDD<java.lang.String> textFile (java.lang.String path, int minPartitions) { throw new RuntimeException(); }
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
   * Do <code>val rdd = sparkContext.wholeTextFile("hdfs://a-hdfs-path")</code>,
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
  public  org.apache.spark.rdd.RDD<scala.Tuple2<java.lang.String, java.lang.String>> wholeTextFiles (java.lang.String path, int minPartitions) { throw new RuntimeException(); }
  /**
   * Get an RDD for a Hadoop-readable dataset from a Hadoop JobConf given its InputFormat and other
   * necessary info (e.g. file name for a filesystem-based dataset, table name for HyperTable),
   * using the older MapReduce API (<code>org.apache.hadoop.mapred</code>).
   * <p>
   * @param conf JobConf for setting up the dataset
   * @param inputFormatClass Class of the InputFormat
   * @param keyClass Class of the keys
   * @param valueClass Class of the values
   * @param minPartitions Minimum number of Hadoop Splits to generate.
   * <p>
   * '''Note:''' Because Hadoop's RecordReader class re-uses the same Writable object for each
   * record, directly caching the returned RDD will create many references to the same object.
   * If you plan to directly cache Hadoop writable objects, you should first copy them using
   * a <code>map</code> function.
   */
  public <K extends java.lang.Object, V extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> hadoopRDD (org.apache.hadoop.mapred.JobConf conf, java.lang.Class<? extends org.apache.hadoop.mapred.InputFormat<K, V>> inputFormatClass, java.lang.Class<K> keyClass, java.lang.Class<V> valueClass, int minPartitions) { throw new RuntimeException(); }
  /**
   * Get an RDD for a Hadoop file with an arbitrary InputFormat
   * <p>
   * '''Note:''' Because Hadoop's RecordReader class re-uses the same Writable object for each
   * record, directly caching the returned RDD will create many references to the same object.
   * If you plan to directly cache Hadoop writable objects, you should first copy them using
   * a <code>map</code> function.
   */
  public <K extends java.lang.Object, V extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> hadoopFile (java.lang.String path, java.lang.Class<? extends org.apache.hadoop.mapred.InputFormat<K, V>> inputFormatClass, java.lang.Class<K> keyClass, java.lang.Class<V> valueClass, int minPartitions) { throw new RuntimeException(); }
  /**
   * Smarter version of hadoopFile() that uses class tags to figure out the classes of keys,
   * values and the InputFormat so that users don't need to pass them directly. Instead, callers
   * can just write, for example,
   * <pre><code>
   * val file = sparkContext.hadoopFile[LongWritable, Text, TextInputFormat](path, minPartitions)
   * </code></pre>
   * <p>
   * '''Note:''' Because Hadoop's RecordReader class re-uses the same Writable object for each
   * record, directly caching the returned RDD will create many references to the same object.
   * If you plan to directly cache Hadoop writable objects, you should first copy them using
   * a <code>map</code> function.
   */
  public <K extends java.lang.Object, V extends java.lang.Object, F extends org.apache.hadoop.mapred.InputFormat<K, V>> org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> hadoopFile (java.lang.String path, int minPartitions, scala.reflect.ClassTag<K> km, scala.reflect.ClassTag<V> vm, scala.reflect.ClassTag<F> fm) { throw new RuntimeException(); }
  /**
   * Smarter version of hadoopFile() that uses class tags to figure out the classes of keys,
   * values and the InputFormat so that users don't need to pass them directly. Instead, callers
   * can just write, for example,
   * <pre><code>
   * val file = sparkContext.hadoopFile[LongWritable, Text, TextInputFormat](path)
   * </code></pre>
   * <p>
   * '''Note:''' Because Hadoop's RecordReader class re-uses the same Writable object for each
   * record, directly caching the returned RDD will create many references to the same object.
   * If you plan to directly cache Hadoop writable objects, you should first copy them using
   * a <code>map</code> function.
   */
  public <K extends java.lang.Object, V extends java.lang.Object, F extends org.apache.hadoop.mapred.InputFormat<K, V>> org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> hadoopFile (java.lang.String path, scala.reflect.ClassTag<K> km, scala.reflect.ClassTag<V> vm, scala.reflect.ClassTag<F> fm) { throw new RuntimeException(); }
  /** Get an RDD for a Hadoop file with an arbitrary new API InputFormat. */
  public <K extends java.lang.Object, V extends java.lang.Object, F extends org.apache.hadoop.mapreduce.InputFormat<K, V>> org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> newAPIHadoopFile (java.lang.String path, scala.reflect.ClassTag<K> km, scala.reflect.ClassTag<V> vm, scala.reflect.ClassTag<F> fm) { throw new RuntimeException(); }
  /**
   * Get an RDD for a given Hadoop file with an arbitrary new API InputFormat
   * and extra configuration options to pass to the input format.
   * <p>
   * '''Note:''' Because Hadoop's RecordReader class re-uses the same Writable object for each
   * record, directly caching the returned RDD will create many references to the same object.
   * If you plan to directly cache Hadoop writable objects, you should first copy them using
   * a <code>map</code> function.
   */
  public <K extends java.lang.Object, V extends java.lang.Object, F extends org.apache.hadoop.mapreduce.InputFormat<K, V>> org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> newAPIHadoopFile (java.lang.String path, java.lang.Class<F> fClass, java.lang.Class<K> kClass, java.lang.Class<V> vClass, org.apache.hadoop.conf.Configuration conf) { throw new RuntimeException(); }
  /**
   * Get an RDD for a given Hadoop file with an arbitrary new API InputFormat
   * and extra configuration options to pass to the input format.
   * <p>
   * '''Note:''' Because Hadoop's RecordReader class re-uses the same Writable object for each
   * record, directly caching the returned RDD will create many references to the same object.
   * If you plan to directly cache Hadoop writable objects, you should first copy them using
   * a <code>map</code> function.
   */
  public <K extends java.lang.Object, V extends java.lang.Object, F extends org.apache.hadoop.mapreduce.InputFormat<K, V>> org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> newAPIHadoopRDD (org.apache.hadoop.conf.Configuration conf, java.lang.Class<F> fClass, java.lang.Class<K> kClass, java.lang.Class<V> vClass) { throw new RuntimeException(); }
  /**
   * Get an RDD for a Hadoop SequenceFile with given key and value types.
   * <p>
   * '''Note:''' Because Hadoop's RecordReader class re-uses the same Writable object for each
   * record, directly caching the returned RDD will create many references to the same object.
   * If you plan to directly cache Hadoop writable objects, you should first copy them using
   * a <code>map</code> function.
   */
  public <K extends java.lang.Object, V extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> sequenceFile (java.lang.String path, java.lang.Class<K> keyClass, java.lang.Class<V> valueClass, int minPartitions) { throw new RuntimeException(); }
  /**
   * Get an RDD for a Hadoop SequenceFile with given key and value types.
   * <p>
   * '''Note:''' Because Hadoop's RecordReader class re-uses the same Writable object for each
   * record, directly caching the returned RDD will create many references to the same object.
   * If you plan to directly cache Hadoop writable objects, you should first copy them using
   * a <code>map</code> function.
   */
  public <K extends java.lang.Object, V extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> sequenceFile (java.lang.String path, java.lang.Class<K> keyClass, java.lang.Class<V> valueClass) { throw new RuntimeException(); }
  /**
   * Version of sequenceFile() for types implicitly convertible to Writables through a
   * WritableConverter. For example, to access a SequenceFile where the keys are Text and the
   * values are IntWritable, you could simply write
   * <pre><code>
   * sparkContext.sequenceFile[String, Int](path, ...)
   * </code></pre>
   * <p>
   * WritableConverters are provided in a somewhat strange way (by an implicit function) to support
   * both subclasses of Writable and types for which we define a converter (e.g. Int to
   * IntWritable). The most natural thing would've been to have implicit objects for the
   * converters, but then we couldn't have an object for every subclass of Writable (you can't
   * have a parameterized singleton object). We use functions instead to create a new converter
   * for the appropriate type. In addition, we pass the converter a ClassTag of its type to
   * allow it to figure out the Writable class to use in the subclass case.
   * <p>
   * '''Note:''' Because Hadoop's RecordReader class re-uses the same Writable object for each
   * record, directly caching the returned RDD will create many references to the same object.
   * If you plan to directly cache Hadoop writable objects, you should first copy them using
   * a <code>map</code> function.
   */
  public <K extends java.lang.Object, V extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> sequenceFile (java.lang.String path, int minPartitions, scala.reflect.ClassTag<K> km, scala.reflect.ClassTag<V> vm, scala.Function0<org.apache.spark.WritableConverter<K>> kcf, scala.Function0<org.apache.spark.WritableConverter<V>> vcf) { throw new RuntimeException(); }
  /**
   * Load an RDD saved as a SequenceFile containing serialized objects, with NullWritable keys and
   * BytesWritable values that contain a serialized partition. This is still an experimental
   * storage format and may not be supported exactly as is in future Spark releases. It will also
   * be pretty slow if you use the default serializer (Java serialization),
   * though the nice thing about it is that there's very little effort required to save arbitrary
   * objects.
   */
  public <T extends java.lang.Object> org.apache.spark.rdd.RDD<T> objectFile (java.lang.String path, int minPartitions, scala.reflect.ClassTag<T> evidence$4) { throw new RuntimeException(); }
  protected <T extends java.lang.Object> org.apache.spark.rdd.RDD<T> checkpointFile (java.lang.String path, scala.reflect.ClassTag<T> evidence$5) { throw new RuntimeException(); }
  /** Build the union of a list of RDDs. */
  public <T extends java.lang.Object> org.apache.spark.rdd.RDD<T> union (scala.collection.Seq<org.apache.spark.rdd.RDD<T>> rdds, scala.reflect.ClassTag<T> evidence$6) { throw new RuntimeException(); }
  /** Build the union of a list of RDDs passed as variable-length arguments. */
  public <T extends java.lang.Object> org.apache.spark.rdd.RDD<T> union (org.apache.spark.rdd.RDD<T> first, scala.collection.Seq<org.apache.spark.rdd.RDD<T>> rest, scala.reflect.ClassTag<T> evidence$7) { throw new RuntimeException(); }
  /** Get an RDD that has no partitions or elements. */
  public <T extends java.lang.Object> org.apache.spark.rdd.EmptyRDD<T> emptyRDD (scala.reflect.ClassTag<T> evidence$8) { throw new RuntimeException(); }
  /**
   * Create an {@link org.apache.spark.Accumulator} variable of a given type, which tasks can "add"
   * values to using the <code>+=</code> method. Only the driver can access the accumulator's <code>value</code>.
   */
  public <T extends java.lang.Object> org.apache.spark.Accumulator<T> accumulator (T initialValue, org.apache.spark.AccumulatorParam<T> param) { throw new RuntimeException(); }
  /**
   * Create an {@link org.apache.spark.Accumulator} variable of a given type, with a name for display
   * in the Spark UI. Tasks can "add" values to the accumulator using the <code>+=</code> method. Only the
   * driver can access the accumulator's <code>value</code>.
   */
  public <T extends java.lang.Object> org.apache.spark.Accumulator<T> accumulator (T initialValue, java.lang.String name, org.apache.spark.AccumulatorParam<T> param) { throw new RuntimeException(); }
  /**
   * Create an {@link org.apache.spark.Accumulable} shared variable, to which tasks can add values
   * with <code>+=</code>. Only the driver can access the accumuable's <code>value</code>.
   * @tparam T accumulator type
   * @tparam R type that can be added to the accumulator
   */
  public <T extends java.lang.Object, R extends java.lang.Object> org.apache.spark.Accumulable<T, R> accumulable (T initialValue, org.apache.spark.AccumulableParam<T, R> param) { throw new RuntimeException(); }
  /**
   * Create an {@link org.apache.spark.Accumulable} shared variable, with a name for display in the
   * Spark UI. Tasks can add values to the accumuable using the <code>+=</code> operator. Only the driver can
   * access the accumuable's <code>value</code>.
   * @tparam T accumulator type
   * @tparam R type that can be added to the accumulator
   */
  public <T extends java.lang.Object, R extends java.lang.Object> org.apache.spark.Accumulable<T, R> accumulable (T initialValue, java.lang.String name, org.apache.spark.AccumulableParam<T, R> param) { throw new RuntimeException(); }
  /**
   * Create an accumulator from a "mutable collection" type.
   * <p>
   * Growable and TraversableOnce are the standard APIs that guarantee += and ++=, implemented by
   * standard mutable collections. So you can use this with mutable Map, Set, etc.
   */
  public <R extends java.lang.Object, T extends java.lang.Object> org.apache.spark.Accumulable<R, T> accumulableCollection (R initialValue, scala.Function1<R, scala.collection.generic.Growable<T>> evidence$9, scala.reflect.ClassTag<R> evidence$10) { throw new RuntimeException(); }
  /**
   * Broadcast a read-only variable to the cluster, returning a
   * {@link org.apache.spark.broadcast.Broadcast} object for reading it in distributed functions.
   * The variable will be sent to each cluster only once.
   */
  public <T extends java.lang.Object> org.apache.spark.broadcast.Broadcast<T> broadcast (T value, scala.reflect.ClassTag<T> evidence$11) { throw new RuntimeException(); }
  /**
   * Add a file to be downloaded with this Spark job on every node.
   * The <code>path</code> passed can be either a local file, a file in HDFS (or other Hadoop-supported
   * filesystems), or an HTTP, HTTPS or FTP URI.  To access the file in Spark jobs,
   * use <code>SparkFiles.get(path)</code> to find its download location.
   */
  public  void addFile (java.lang.String path) { throw new RuntimeException(); }
  /**
   * :: DeveloperApi ::
   * Register a listener to receive up-calls from events that happen during execution.
   */
  public  void addSparkListener (org.apache.spark.scheduler.SparkListener listener) { throw new RuntimeException(); }
  /** The version of Spark on which this application is running. */
  public  java.lang.String version () { throw new RuntimeException(); }
  /**
   * Return a map from the slave to the max memory available for caching and the remaining
   * memory available for caching.
   */
  public  scala.collection.Map<java.lang.String, scala.Tuple2<java.lang.Object, java.lang.Object>> getExecutorMemoryStatus () { throw new RuntimeException(); }
  /**
   * :: DeveloperApi ::
   * Return information about what RDDs are cached, if they are in mem or on disk, how much space
   * they take, etc.
   */
  public  org.apache.spark.storage.RDDInfo[] getRDDStorageInfo () { throw new RuntimeException(); }
  /**
   * Returns an immutable map of RDDs that have marked themselves as persistent via cache() call.
   * Note that this does not necessarily mean the caching or computation was successful.
   */
  public  scala.collection.Map<java.lang.Object, org.apache.spark.rdd.RDD<?>> getPersistentRDDs () { throw new RuntimeException(); }
  /**
   * :: DeveloperApi ::
   * Return information about blocks stored in all of the slaves
   */
  public  org.apache.spark.storage.StorageStatus[] getExecutorStorageStatus () { throw new RuntimeException(); }
  /**
   * :: DeveloperApi ::
   * Return pools for fair scheduler
   */
  public  scala.collection.Seq<org.apache.spark.scheduler.Schedulable> getAllPools () { throw new RuntimeException(); }
  /**
   * :: DeveloperApi ::
   * Return the pool associated with the given name, if one exists
   */
  public  scala.Option<org.apache.spark.scheduler.Schedulable> getPoolForName (java.lang.String pool) { throw new RuntimeException(); }
  /**
   * Return current scheduling mode
   */
  public  scala.Enumeration.Value getSchedulingMode () { throw new RuntimeException(); }
  /**
   * Clear the job's list of files added by <code>addFile</code> so that they do not get downloaded to
   * any new nodes.
   */
  public  void clearFiles () { throw new RuntimeException(); }
  /**
   * Gets the locality information associated with the partition in a particular rdd
   * @param rdd of interest
   * @param partition to be looked up for locality
   * @return list of preferred locations for the partition
   */
  private  scala.collection.Seq<org.apache.spark.scheduler.TaskLocation> getPreferredLocs (org.apache.spark.rdd.RDD<?> rdd, int partition) { throw new RuntimeException(); }
  /**
   * Register an RDD to be persisted in memory and/or disk storage
   */
  private  void persistRDD (org.apache.spark.rdd.RDD<?> rdd) { throw new RuntimeException(); }
  /**
   * Unpersist an RDD from memory and/or disk storage
   */
  private  void unpersistRDD (int rddId, boolean blocking) { throw new RuntimeException(); }
  /**
   * Adds a JAR dependency for all tasks to be executed on this SparkContext in the future.
   * The <code>path</code> passed can be either a local file, a file in HDFS (or other Hadoop-supported
   * filesystems), an HTTP, HTTPS or FTP URI, or local:/path for a file on every worker node.
   */
  public  void addJar (java.lang.String path) { throw new RuntimeException(); }
  /**
   * Clear the job's list of JARs added by <code>addJar</code> so that they do not get downloaded to
   * any new nodes.
   */
  public  void clearJars () { throw new RuntimeException(); }
  /** Shut down the SparkContext. */
  public  void stop () { throw new RuntimeException(); }
  /**
   * Get Spark's home location from either a value set through the constructor,
   * or the spark.home Java property, or the SPARK_HOME environment variable
   * (in that order of preference). If neither of these is set, return None.
   */
  private  scala.Option<java.lang.String> getSparkHome () { throw new RuntimeException(); }
  /**
   * Support function for API backtraces.
   */
  public  void setCallSite (java.lang.String site) { throw new RuntimeException(); }
  /**
   * Support function for API backtraces.
   */
  public  void clearCallSite () { throw new RuntimeException(); }
  /**
   * Capture the current user callsite and return a formatted version for printing. If the user
   * has overridden the call site, this will return the user's version.
   */
  private  org.apache.spark.util.CallSite getCallSite () { throw new RuntimeException(); }
  /**
   * Run a function on a given set of partitions in an RDD and pass the results to the given
   * handler function. This is the main entry point for all actions in Spark. The allowLocal
   * flag specifies whether the scheduler can run the computation on the driver rather than
   * shipping it out to the cluster, for short actions like first().
   */
  public <T extends java.lang.Object, U extends java.lang.Object> void runJob (org.apache.spark.rdd.RDD<T> rdd, scala.Function2<org.apache.spark.TaskContext, scala.collection.Iterator<T>, U> func, scala.collection.Seq<java.lang.Object> partitions, boolean allowLocal, scala.Function2<java.lang.Object, U, scala.runtime.BoxedUnit> resultHandler, scala.reflect.ClassTag<U> evidence$12) { throw new RuntimeException(); }
  /**
   * Run a function on a given set of partitions in an RDD and return the results as an array. The
   * allowLocal flag specifies whether the scheduler can run the computation on the driver rather
   * than shipping it out to the cluster, for short actions like first().
   */
  public <T extends java.lang.Object, U extends java.lang.Object> java.lang.Object runJob (org.apache.spark.rdd.RDD<T> rdd, scala.Function2<org.apache.spark.TaskContext, scala.collection.Iterator<T>, U> func, scala.collection.Seq<java.lang.Object> partitions, boolean allowLocal, scala.reflect.ClassTag<U> evidence$13) { throw new RuntimeException(); }
  /**
   * Run a job on a given set of partitions of an RDD, but take a function of type
   * <code>Iterator[T] => U</code> instead of <code>(TaskContext, Iterator[T]) => U</code>.
   */
  public <T extends java.lang.Object, U extends java.lang.Object> java.lang.Object runJob (org.apache.spark.rdd.RDD<T> rdd, scala.Function1<scala.collection.Iterator<T>, U> func, scala.collection.Seq<java.lang.Object> partitions, boolean allowLocal, scala.reflect.ClassTag<U> evidence$14) { throw new RuntimeException(); }
  /**
   * Run a job on all partitions in an RDD and return the results in an array.
   */
  public <T extends java.lang.Object, U extends java.lang.Object> java.lang.Object runJob (org.apache.spark.rdd.RDD<T> rdd, scala.Function2<org.apache.spark.TaskContext, scala.collection.Iterator<T>, U> func, scala.reflect.ClassTag<U> evidence$15) { throw new RuntimeException(); }
  /**
   * Run a job on all partitions in an RDD and return the results in an array.
   */
  public <T extends java.lang.Object, U extends java.lang.Object> java.lang.Object runJob (org.apache.spark.rdd.RDD<T> rdd, scala.Function1<scala.collection.Iterator<T>, U> func, scala.reflect.ClassTag<U> evidence$16) { throw new RuntimeException(); }
  /**
   * Run a job on all partitions in an RDD and pass the results to a handler function.
   */
  public <T extends java.lang.Object, U extends java.lang.Object> void runJob (org.apache.spark.rdd.RDD<T> rdd, scala.Function2<org.apache.spark.TaskContext, scala.collection.Iterator<T>, U> processPartition, scala.Function2<java.lang.Object, U, scala.runtime.BoxedUnit> resultHandler, scala.reflect.ClassTag<U> evidence$17) { throw new RuntimeException(); }
  /**
   * Run a job on all partitions in an RDD and pass the results to a handler function.
   */
  public <T extends java.lang.Object, U extends java.lang.Object> void runJob (org.apache.spark.rdd.RDD<T> rdd, scala.Function1<scala.collection.Iterator<T>, U> processPartition, scala.Function2<java.lang.Object, U, scala.runtime.BoxedUnit> resultHandler, scala.reflect.ClassTag<U> evidence$18) { throw new RuntimeException(); }
  /**
   * :: DeveloperApi ::
   * Run a job that can return approximate results.
   */
  public <T extends java.lang.Object, U extends java.lang.Object, R extends java.lang.Object> org.apache.spark.partial.PartialResult<R> runApproximateJob (org.apache.spark.rdd.RDD<T> rdd, scala.Function2<org.apache.spark.TaskContext, scala.collection.Iterator<T>, U> func, org.apache.spark.partial.ApproximateEvaluator<U, R> evaluator, long timeout) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Submit a job for execution and return a FutureJob holding the result.
   */
  public <T extends java.lang.Object, U extends java.lang.Object, R extends java.lang.Object> org.apache.spark.SimpleFutureAction<R> submitJob (org.apache.spark.rdd.RDD<T> rdd, scala.Function1<scala.collection.Iterator<T>, U> processPartition, scala.collection.Seq<java.lang.Object> partitions, scala.Function2<java.lang.Object, U, scala.runtime.BoxedUnit> resultHandler, scala.Function0<R> resultFunc) { throw new RuntimeException(); }
  /**
   * Cancel active jobs for the specified group. See {@link org.apache.spark.SparkContext.setJobGroup}
   * for more information.
   */
  public  void cancelJobGroup (java.lang.String groupId) { throw new RuntimeException(); }
  /** Cancel all jobs that have been scheduled or are running.  */
  public  void cancelAllJobs () { throw new RuntimeException(); }
  /** Cancel a given job if it's scheduled or running */
  private  void cancelJob (int jobId) { throw new RuntimeException(); }
  /** Cancel a given stage and all jobs associated with it */
  private  void cancelStage (int stageId) { throw new RuntimeException(); }
  /**
   * Clean a closure to make it ready to serialized and send to tasks
   * (removes unreferenced variables in $outer's, updates REPL variables)
   * If <tt>checkSerializable</tt> is set, <tt>clean</tt> will also proactively
   * check to see if <tt>f</tt> is serializable and throw a <tt>SparkException</tt>
   * if not.
   * <p>
   * @param f the closure to clean
   * @param checkSerializable whether or not to immediately check <tt>f</tt> for serializability
   * @throws <tt>SparkException<tt> if <tt>checkSerializable</tt> is set but <tt>f</tt> is not
   *   serializable
   */
  private <F extends java.lang.Object> F clean (F f, boolean checkSerializable) { throw new RuntimeException(); }
  /**
   * Set the directory under which RDDs are going to be checkpointed. The directory must
   * be a HDFS path if running on a cluster.
   */
  public  void setCheckpointDir (java.lang.String directory) { throw new RuntimeException(); }
  public  scala.Option<java.lang.String> getCheckpointDir () { throw new RuntimeException(); }
  /** Default level of parallelism to use when not given by user (e.g. parallelize and makeRDD). */
  public  int defaultParallelism () { throw new RuntimeException(); }
  /** Default min number of partitions for Hadoop RDDs when not given by user */
  public  int defaultMinSplits () { throw new RuntimeException(); }
  /** Default min number of partitions for Hadoop RDDs when not given by user */
  public  int defaultMinPartitions () { throw new RuntimeException(); }
  private  java.util.concurrent.atomic.AtomicInteger nextShuffleId () { throw new RuntimeException(); }
  private  int newShuffleId () { throw new RuntimeException(); }
  private  java.util.concurrent.atomic.AtomicInteger nextRddId () { throw new RuntimeException(); }
  /** Register a new RDD, returning its RDD ID */
  private  int newRddId () { throw new RuntimeException(); }
  /** Post the application start event */
  private  void postApplicationStart () { throw new RuntimeException(); }
  /** Post the application end event */
  private  void postApplicationEnd () { throw new RuntimeException(); }
  /** Post the environment update event once the task scheduler is ready */
  private  void postEnvironmentUpdate () { throw new RuntimeException(); }
  /** Called by MetadataCleaner to clean up the persistentRdds map periodically */
  private  void cleanup (long cleanupTime) { throw new RuntimeException(); }
}
