package org.apache.spark.rdd;
/**
 * :: DeveloperApi ::
 * An RDD that provides core functionality for reading data stored in Hadoop (e.g., files in HDFS,
 * sources in HBase, or S3), using the older MapReduce API (<code>org.apache.hadoop.mapred</code>).
 * <p>
 * Note: Instantiating this class directly is not recommended, please use
 * {@link org.apache.spark.SparkContext.hadoopRDD()}
 * <p>
 * @param sc The SparkContext to associate the RDD with.
 * @param broadcastedConf A general Hadoop Configuration, or a subclass of it. If the enclosed
 *     variabe references an instance of JobConf, then that JobConf will be used for the Hadoop job.
 *     Otherwise, a new JobConf will be created on each slave using the enclosed Configuration.
 * @param initLocalJobConfFuncOpt Optional closure used to initialize any JobConf that HadoopRDD
 *     creates.
 * @param inputFormatClass Storage format of the data to be read.
 * @param keyClass Class of the key associated with the inputFormatClass.
 * @param valueClass Class of the value associated with the inputFormatClass.
 * @param minPartitions Minimum number of HadoopRDD partitions (Hadoop Splits) to generate.
 */
public  class HadoopRDD<K extends java.lang.Object, V extends java.lang.Object> extends org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> implements org.apache.spark.Logging {
  /**
   * Analogous to {@link org.apache.spark.rdd.MapPartitionsRDD}, but passes in an InputSplit to
   * the given function rather than the index of the partition.
   */
  static private  class HadoopMapPartitionsWithSplitRDD<U extends java.lang.Object, T extends java.lang.Object> extends org.apache.spark.rdd.RDD<U> {
    // not preceding
    // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
    // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
    public   HadoopMapPartitionsWithSplitRDD (org.apache.spark.rdd.RDD<T> prev, scala.Function2<org.apache.hadoop.mapred.InputSplit, scala.collection.Iterator<T>, scala.collection.Iterator<U>> f, boolean preservesPartitioning, scala.reflect.ClassTag<U> evidence$2, scala.reflect.ClassTag<T> evidence$3) { throw new RuntimeException(); }
    public  scala.Option<org.apache.spark.Partitioner> partitioner () { throw new RuntimeException(); }
    public  org.apache.spark.Partition[] getPartitions () { throw new RuntimeException(); }
    public  scala.collection.Iterator<U> compute (org.apache.spark.Partition split, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
  }
  // no position
  // not preceding
  static private  class HadoopMapPartitionsWithSplitRDD$ implements scala.Serializable {
    public   HadoopMapPartitionsWithSplitRDD$ () { throw new RuntimeException(); }
  }
  /** Constructing Configuration objects is not threadsafe, use this lock to serialize. */
  static public  java.lang.Object CONFIGURATION_INSTANTIATION_LOCK () { throw new RuntimeException(); }
  /**
   * The three methods below are helpers for accessing the local map, a property of the SparkEnv of
   * the local process.
   */
  static public  Object getCachedMetadata (java.lang.String key) { throw new RuntimeException(); }
  static public  boolean containsCachedMetadata (java.lang.String key) { throw new RuntimeException(); }
  static public  Object putCachedMetadata (java.lang.String key, Object value) { throw new RuntimeException(); }
  /** Add Hadoop configuration specific to a single partition and attempt. */
  static public  void addLocalConfiguration (java.lang.String jobTrackerId, int jobId, int splitId, int attemptId, org.apache.hadoop.mapred.JobConf conf) { throw new RuntimeException(); }
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   HadoopRDD (org.apache.spark.SparkContext sc, org.apache.spark.broadcast.Broadcast<org.apache.spark.SerializableWritable<org.apache.hadoop.conf.Configuration>> broadcastedConf, scala.Option<scala.Function1<org.apache.hadoop.mapred.JobConf, scala.runtime.BoxedUnit>> initLocalJobConfFuncOpt, java.lang.Class<? extends org.apache.hadoop.mapred.InputFormat<K, V>> inputFormatClass, java.lang.Class<K> keyClass, java.lang.Class<V> valueClass, int minPartitions) { throw new RuntimeException(); }
  public   HadoopRDD (org.apache.spark.SparkContext sc, org.apache.hadoop.mapred.JobConf conf, java.lang.Class<? extends org.apache.hadoop.mapred.InputFormat<K, V>> inputFormatClass, java.lang.Class<K> keyClass, java.lang.Class<V> valueClass, int minPartitions) { throw new RuntimeException(); }
  protected  java.lang.String jobConfCacheKey () { throw new RuntimeException(); }
  protected  java.lang.String inputFormatCacheKey () { throw new RuntimeException(); }
  private  java.util.Date createTime () { throw new RuntimeException(); }
  protected  org.apache.hadoop.mapred.JobConf getJobConf () { throw new RuntimeException(); }
  protected  org.apache.hadoop.mapred.InputFormat<K, V> getInputFormat (org.apache.hadoop.mapred.JobConf conf) { throw new RuntimeException(); }
  public  org.apache.spark.Partition[] getPartitions () { throw new RuntimeException(); }
  public  org.apache.spark.InterruptibleIterator<scala.Tuple2<K, V>> compute (org.apache.spark.Partition theSplit, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
  /** Maps over a partition, providing the InputSplit that was used as the base of the partition. */
  public <U extends java.lang.Object> org.apache.spark.rdd.RDD<U> mapPartitionsWithInputSplit (scala.Function2<org.apache.hadoop.mapred.InputSplit, scala.collection.Iterator<scala.Tuple2<K, V>>, scala.collection.Iterator<U>> f, boolean preservesPartitioning, scala.reflect.ClassTag<U> evidence$1) { throw new RuntimeException(); }
  public  scala.collection.Seq<java.lang.String> getPreferredLocations (org.apache.spark.Partition split) { throw new RuntimeException(); }
  public  void checkpoint () { throw new RuntimeException(); }
  public  org.apache.hadoop.conf.Configuration getConf () { throw new RuntimeException(); }
}
