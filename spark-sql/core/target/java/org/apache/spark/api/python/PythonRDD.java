package org.apache.spark.api.python;
private  class PythonRDD extends org.apache.spark.rdd.RDD<byte[]> {
  /**
   * The thread responsible for writing the data from the PythonRDD's parent iterator to the
   * Python process.
   */
  public  class WriterThread extends java.lang.Thread {
    public   WriterThread (org.apache.spark.SparkEnv env, java.net.Socket worker, org.apache.spark.Partition split, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
    private  java.lang.Exception _exception () { throw new RuntimeException(); }
    /** Contains the exception thrown while writing the parent iterator to the Python process. */
    public  scala.Option<java.lang.Exception> exception () { throw new RuntimeException(); }
    /** Terminates the writer thread, ignoring any exceptions that may occur due to cleanup. */
    public  void shutdownOnTaskCompletion () { throw new RuntimeException(); }
    public  void run () { throw new RuntimeException(); }
  }
  /**
   * It is necessary to have a monitor thread for python workers if the user cancels with
   * interrupts disabled. In that case we will need to explicitly kill the worker, otherwise the
   * threads can block indefinitely.
   */
  public  class MonitorThread extends java.lang.Thread {
    public   MonitorThread (org.apache.spark.SparkEnv env, java.net.Socket worker, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
    public  void run () { throw new RuntimeException(); }
  }
  static public  java.nio.charset.Charset UTF8 () { throw new RuntimeException(); }
  /**
   * Adapter for calling SparkContext#runJob from Python.
   * <p>
   * This method will return an iterator of an array that contains all elements in the RDD
   * (effectively a collect()), but allows you to run on a certain subset of partitions,
   * or to enable local execution.
   */
  static public  scala.collection.Iterator<byte[]> runJob (org.apache.spark.SparkContext sc, org.apache.spark.api.java.JavaRDD<byte[]> rdd, java.util.ArrayList<java.lang.Object> partitions, boolean allowLocal) { throw new RuntimeException(); }
  static public  org.apache.spark.api.java.JavaRDD<byte[]> readRDDFromFile (org.apache.spark.api.java.JavaSparkContext sc, java.lang.String filename, int parallelism) { throw new RuntimeException(); }
  static public  org.apache.spark.broadcast.Broadcast<byte[]> readBroadcastFromFile (org.apache.spark.api.java.JavaSparkContext sc, java.lang.String filename) { throw new RuntimeException(); }
  static public <T extends java.lang.Object> void writeIteratorToStream (scala.collection.Iterator<T> iter, java.io.DataOutputStream dataOut) { throw new RuntimeException(); }
  /**
   * Create an RDD from a path using {@link org.apache.hadoop.mapred.SequenceFileInputFormat},
   * key and value class.
   * A key and/or value converter class can optionally be passed in
   * (see {@link org.apache.spark.api.python.Converter})
   */
  static public <K extends java.lang.Object, V extends java.lang.Object> org.apache.spark.api.java.JavaRDD<byte[]> sequenceFile (org.apache.spark.api.java.JavaSparkContext sc, java.lang.String path, java.lang.String keyClassMaybeNull, java.lang.String valueClassMaybeNull, java.lang.String keyConverterClass, java.lang.String valueConverterClass, int minSplits, int batchSize) { throw new RuntimeException(); }
  /**
   * Create an RDD from a file path, using an arbitrary {@link org.apache.hadoop.mapreduce.InputFormat},
   * key and value class.
   * A key and/or value converter class can optionally be passed in
   * (see {@link org.apache.spark.api.python.Converter})
   */
  static public <K extends java.lang.Object, V extends java.lang.Object, F extends org.apache.hadoop.mapreduce.InputFormat<K, V>> org.apache.spark.api.java.JavaRDD<byte[]> newAPIHadoopFile (org.apache.spark.api.java.JavaSparkContext sc, java.lang.String path, java.lang.String inputFormatClass, java.lang.String keyClass, java.lang.String valueClass, java.lang.String keyConverterClass, java.lang.String valueConverterClass, java.util.HashMap<java.lang.String, java.lang.String> confAsMap, int batchSize) { throw new RuntimeException(); }
  /**
   * Create an RDD from a {@link org.apache.hadoop.conf.Configuration} converted from a map that is
   * passed in from Python, using an arbitrary {@link org.apache.hadoop.mapreduce.InputFormat},
   * key and value class.
   * A key and/or value converter class can optionally be passed in
   * (see {@link org.apache.spark.api.python.Converter})
   */
  static public <K extends java.lang.Object, V extends java.lang.Object, F extends org.apache.hadoop.mapreduce.InputFormat<K, V>> org.apache.spark.api.java.JavaRDD<byte[]> newAPIHadoopRDD (org.apache.spark.api.java.JavaSparkContext sc, java.lang.String inputFormatClass, java.lang.String keyClass, java.lang.String valueClass, java.lang.String keyConverterClass, java.lang.String valueConverterClass, java.util.HashMap<java.lang.String, java.lang.String> confAsMap, int batchSize) { throw new RuntimeException(); }
  static private <K extends java.lang.Object, V extends java.lang.Object, F extends org.apache.hadoop.mapreduce.InputFormat<K, V>> org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> newAPIHadoopRDDFromClassNames (org.apache.spark.api.java.JavaSparkContext sc, scala.Option<java.lang.String> path, java.lang.String inputFormatClass, java.lang.String keyClass, java.lang.String valueClass, org.apache.hadoop.conf.Configuration conf) { throw new RuntimeException(); }
  /**
   * Create an RDD from a file path, using an arbitrary {@link org.apache.hadoop.mapred.InputFormat},
   * key and value class.
   * A key and/or value converter class can optionally be passed in
   * (see {@link org.apache.spark.api.python.Converter})
   */
  static public <K extends java.lang.Object, V extends java.lang.Object, F extends org.apache.hadoop.mapred.InputFormat<K, V>> org.apache.spark.api.java.JavaRDD<byte[]> hadoopFile (org.apache.spark.api.java.JavaSparkContext sc, java.lang.String path, java.lang.String inputFormatClass, java.lang.String keyClass, java.lang.String valueClass, java.lang.String keyConverterClass, java.lang.String valueConverterClass, java.util.HashMap<java.lang.String, java.lang.String> confAsMap, int batchSize) { throw new RuntimeException(); }
  /**
   * Create an RDD from a {@link org.apache.hadoop.conf.Configuration} converted from a map
   * that is passed in from Python, using an arbitrary {@link org.apache.hadoop.mapred.InputFormat},
   * key and value class
   * A key and/or value converter class can optionally be passed in
   * (see {@link org.apache.spark.api.python.Converter})
   */
  static public <K extends java.lang.Object, V extends java.lang.Object, F extends org.apache.hadoop.mapred.InputFormat<K, V>> org.apache.spark.api.java.JavaRDD<byte[]> hadoopRDD (org.apache.spark.api.java.JavaSparkContext sc, java.lang.String inputFormatClass, java.lang.String keyClass, java.lang.String valueClass, java.lang.String keyConverterClass, java.lang.String valueConverterClass, java.util.HashMap<java.lang.String, java.lang.String> confAsMap, int batchSize) { throw new RuntimeException(); }
  static private <K extends java.lang.Object, V extends java.lang.Object, F extends org.apache.hadoop.mapred.InputFormat<K, V>> org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> hadoopRDDFromClassNames (org.apache.spark.api.java.JavaSparkContext sc, scala.Option<java.lang.String> path, java.lang.String inputFormatClass, java.lang.String keyClass, java.lang.String valueClass, org.apache.hadoop.conf.Configuration conf) { throw new RuntimeException(); }
  static public  void writeUTF (java.lang.String str, java.io.DataOutputStream dataOut) { throw new RuntimeException(); }
  static public <T extends java.lang.Object> void writeToFile (java.util.Iterator<T> items, java.lang.String filename) { throw new RuntimeException(); }
  static public <T extends java.lang.Object> void writeToFile (scala.collection.Iterator<T> items, java.lang.String filename) { throw new RuntimeException(); }
  static private  org.apache.hadoop.conf.Configuration getMergedConf (java.util.HashMap<java.lang.String, java.lang.String> confAsMap, org.apache.hadoop.conf.Configuration baseConf) { throw new RuntimeException(); }
  static private <K extends java.lang.Object, V extends java.lang.Object> scala.Tuple2<java.lang.Class<?>, java.lang.Class<?>> inferKeyValueTypes (org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> rdd, java.lang.String keyConverterClass, java.lang.String valueConverterClass) { throw new RuntimeException(); }
  static private  scala.Option<scala.Tuple2<java.lang.Class<?>, java.lang.Class<?>>> getKeyValueTypes (java.lang.String keyClass, java.lang.String valueClass) { throw new RuntimeException(); }
  static private  scala.Tuple2<org.apache.spark.api.python.Converter<java.lang.Object, java.lang.Object>, org.apache.spark.api.python.Converter<java.lang.Object, java.lang.Object>> getKeyValueConverters (java.lang.String keyConverterClass, java.lang.String valueConverterClass, org.apache.spark.api.python.Converter<java.lang.Object, java.lang.Object> defaultConverter) { throw new RuntimeException(); }
  /**
   * Convert an RDD of key-value pairs from internal types to serializable types suitable for
   * output, or vice versa.
   */
  static private <K extends java.lang.Object, V extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<java.lang.Object, java.lang.Object>> convertRDD (org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> rdd, java.lang.String keyConverterClass, java.lang.String valueConverterClass, org.apache.spark.api.python.Converter<java.lang.Object, java.lang.Object> defaultConverter) { throw new RuntimeException(); }
  /**
   * Output a Python RDD of key-value pairs as a Hadoop SequenceFile using the Writable types
   * we convert from the RDD's key and value types. Note that keys and values can't be
   * {@link org.apache.hadoop.io.Writable} types already, since Writables are not Java
   * <code>Serializable</code> and we can't peek at them. The <code>path</code> can be on any Hadoop file system.
   */
  static public <K extends java.lang.Object, V extends java.lang.Object, C extends org.apache.hadoop.io.compress.CompressionCodec> void saveAsSequenceFile (org.apache.spark.api.java.JavaRDD<byte[]> pyRDD, boolean batchSerialized, java.lang.String path, java.lang.String compressionCodecClass) { throw new RuntimeException(); }
  /**
   * Output a Python RDD of key-value pairs to any Hadoop file system, using old Hadoop
   * <code>OutputFormat</code> in mapred package. Keys and values are converted to suitable output
   * types using either user specified converters or, if not specified,
   * {@link org.apache.spark.api.python.JavaToWritableConverter}. Post-conversion types
   * <code>keyClass</code> and <code>valueClass</code> are automatically inferred if not specified. The passed-in
   * <code>confAsMap</code> is merged with the default Hadoop conf associated with the SparkContext of
   * this RDD.
   */
  static public <K extends java.lang.Object, V extends java.lang.Object, F extends org.apache.hadoop.mapred.OutputFormat<?, ?>, C extends org.apache.hadoop.io.compress.CompressionCodec> void saveAsHadoopFile (org.apache.spark.api.java.JavaRDD<byte[]> pyRDD, boolean batchSerialized, java.lang.String path, java.lang.String outputFormatClass, java.lang.String keyClass, java.lang.String valueClass, java.lang.String keyConverterClass, java.lang.String valueConverterClass, java.util.HashMap<java.lang.String, java.lang.String> confAsMap, java.lang.String compressionCodecClass) { throw new RuntimeException(); }
  /**
   * Output a Python RDD of key-value pairs to any Hadoop file system, using new Hadoop
   * <code>OutputFormat</code> in mapreduce package. Keys and values are converted to suitable output
   * types using either user specified converters or, if not specified,
   * {@link org.apache.spark.api.python.JavaToWritableConverter}. Post-conversion types
   * <code>keyClass</code> and <code>valueClass</code> are automatically inferred if not specified. The passed-in
   * <code>confAsMap</code> is merged with the default Hadoop conf associated with the SparkContext of
   * this RDD.
   */
  static public <K extends java.lang.Object, V extends java.lang.Object, F extends org.apache.hadoop.mapreduce.OutputFormat<?, ?>> void saveAsNewAPIHadoopFile (org.apache.spark.api.java.JavaRDD<byte[]> pyRDD, boolean batchSerialized, java.lang.String path, java.lang.String outputFormatClass, java.lang.String keyClass, java.lang.String valueClass, java.lang.String keyConverterClass, java.lang.String valueConverterClass, java.util.HashMap<java.lang.String, java.lang.String> confAsMap) { throw new RuntimeException(); }
  /**
   * Output a Python RDD of key-value pairs to any Hadoop file system, using a Hadoop conf
   * converted from the passed-in <code>confAsMap</code>. The conf should set relevant output params (
   * e.g., output path, output format, etc), in the same way as it would be configured for
   * a Hadoop MapReduce job. Both old and new Hadoop OutputFormat APIs are supported
   * (mapred vs. mapreduce). Keys/values are converted for output using either user specified
   * converters or, by default, {@link org.apache.spark.api.python.JavaToWritableConverter}.
   */
  static public <K extends java.lang.Object, V extends java.lang.Object> void saveAsHadoopDataset (org.apache.spark.api.java.JavaRDD<byte[]> pyRDD, boolean batchSerialized, java.util.HashMap<java.lang.String, java.lang.String> confAsMap, java.lang.String keyConverterClass, java.lang.String valueConverterClass, boolean useNewAPI) { throw new RuntimeException(); }
  /**
   * Convert an RDD of serialized Python dictionaries to Scala Maps (no recursive conversions).
   */
  static public  org.apache.spark.api.java.JavaRDD<scala.collection.immutable.Map<java.lang.String, java.lang.Object>> pythonToJavaMap (org.apache.spark.api.java.JavaRDD<byte[]> pyRDD) { throw new RuntimeException(); }
  /**
   * Convert an RDD of serialized Python tuple to Array (no recursive conversions).
   * It is only used by pyspark.sql.
   */
  static public  org.apache.spark.api.java.JavaRDD<java.lang.Object> pythonToJavaArray (org.apache.spark.api.java.JavaRDD<byte[]> pyRDD, boolean batched) { throw new RuntimeException(); }
  /**
   * Convert and RDD of Java objects to and RDD of serialized Python objects, that is usable by
   * PySpark.
   */
  static public  org.apache.spark.api.java.JavaRDD<byte[]> javaToPython (org.apache.spark.api.java.JavaRDD<java.lang.Object> jRDD) { throw new RuntimeException(); }
  public   PythonRDD (org.apache.spark.rdd.RDD<?> parent, byte[] command, java.util.Map<java.lang.String, java.lang.String> envVars, java.util.List<java.lang.String> pythonIncludes, boolean preservePartitoning, java.lang.String pythonExec, java.util.List<org.apache.spark.broadcast.Broadcast<byte[]>> broadcastVars, org.apache.spark.Accumulator<java.util.List<byte[]>> accumulator) { throw new RuntimeException(); }
  public  int bufferSize () { throw new RuntimeException(); }
  public  org.apache.spark.Partition[] getPartitions () { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.Partitioner> partitioner () { throw new RuntimeException(); }
  public  scala.collection.Iterator<byte[]> compute (org.apache.spark.Partition split, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
  public  org.apache.spark.api.java.JavaRDD<byte[]> asJavaRDD () { throw new RuntimeException(); }
}
