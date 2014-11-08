package org.apache.spark;
// no position
/**
 * The SparkContext object contains a number of implicit conversions and parameters for use with
 * various Spark features.
 */
public  class SparkContext$ implements org.apache.spark.Logging {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final SparkContext$ MODULE$ = null;
  public   SparkContext$ () { throw new RuntimeException(); }
  public  java.lang.String SPARK_VERSION () { throw new RuntimeException(); }
  public  java.lang.String SPARK_JOB_DESCRIPTION () { throw new RuntimeException(); }
  public  java.lang.String SPARK_JOB_GROUP_ID () { throw new RuntimeException(); }
  public  java.lang.String SPARK_JOB_INTERRUPT_ON_CANCEL () { throw new RuntimeException(); }
  public  java.lang.String SPARK_UNKNOWN_USER () { throw new RuntimeException(); }
  public <K extends java.lang.Object, V extends java.lang.Object> org.apache.spark.rdd.PairRDDFunctions<K, V> rddToPairRDDFunctions (org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> rdd, scala.reflect.ClassTag<K> kt, scala.reflect.ClassTag<V> vt, scala.math.Ordering<K> ord) { throw new RuntimeException(); }
  public <T extends java.lang.Object> org.apache.spark.rdd.AsyncRDDActions<T> rddToAsyncRDDActions (org.apache.spark.rdd.RDD<T> rdd, scala.reflect.ClassTag<T> evidence$19) { throw new RuntimeException(); }
  public <K extends java.lang.Object, V extends java.lang.Object> org.apache.spark.rdd.SequenceFileRDDFunctions<K, V> rddToSequenceFileRDDFunctions (org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> rdd, scala.Function1<K, org.apache.hadoop.io.Writable> evidence$20, scala.reflect.ClassTag<K> evidence$21, scala.Function1<V, org.apache.hadoop.io.Writable> evidence$22, scala.reflect.ClassTag<V> evidence$23) { throw new RuntimeException(); }
  public <K extends java.lang.Object, V extends java.lang.Object> org.apache.spark.rdd.OrderedRDDFunctions<K, V, scala.Tuple2<K, V>> rddToOrderedRDDFunctions (org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> rdd, scala.math.Ordering<K> evidence$24, scala.reflect.ClassTag<K> evidence$25, scala.reflect.ClassTag<V> evidence$26) { throw new RuntimeException(); }
  public  org.apache.spark.rdd.DoubleRDDFunctions doubleRDDToDoubleRDDFunctions (org.apache.spark.rdd.RDD<java.lang.Object> rdd) { throw new RuntimeException(); }
  public <T extends java.lang.Object> org.apache.spark.rdd.DoubleRDDFunctions numericRDDToDoubleRDDFunctions (org.apache.spark.rdd.RDD<T> rdd, scala.math.Numeric<T> num) { throw new RuntimeException(); }
  public  org.apache.hadoop.io.IntWritable intToIntWritable (int i) { throw new RuntimeException(); }
  public  org.apache.hadoop.io.LongWritable longToLongWritable (long l) { throw new RuntimeException(); }
  public  org.apache.hadoop.io.FloatWritable floatToFloatWritable (float f) { throw new RuntimeException(); }
  public  org.apache.hadoop.io.DoubleWritable doubleToDoubleWritable (double d) { throw new RuntimeException(); }
  public  org.apache.hadoop.io.BooleanWritable boolToBoolWritable (boolean b) { throw new RuntimeException(); }
  public  org.apache.hadoop.io.BytesWritable bytesToBytesWritable (byte[] aob) { throw new RuntimeException(); }
  public  org.apache.hadoop.io.Text stringToText (java.lang.String s) { throw new RuntimeException(); }
  private <T extends java.lang.Object> org.apache.hadoop.io.ArrayWritable arrayToArrayWritable (scala.collection.Traversable<T> arr, scala.Function1<T, org.apache.hadoop.io.Writable> evidence$27, scala.reflect.ClassTag<T> evidence$28) { throw new RuntimeException(); }
  private <T extends java.lang.Object, W extends org.apache.hadoop.io.Writable> org.apache.spark.WritableConverter<T> simpleWritableConverter (scala.Function1<W, T> convert, scala.reflect.ClassTag<W> evidence$30) { throw new RuntimeException(); }
  public  org.apache.spark.WritableConverter<java.lang.Object> intWritableConverter () { throw new RuntimeException(); }
  public  org.apache.spark.WritableConverter<java.lang.Object> longWritableConverter () { throw new RuntimeException(); }
  public  org.apache.spark.WritableConverter<java.lang.Object> doubleWritableConverter () { throw new RuntimeException(); }
  public  org.apache.spark.WritableConverter<java.lang.Object> floatWritableConverter () { throw new RuntimeException(); }
  public  org.apache.spark.WritableConverter<java.lang.Object> booleanWritableConverter () { throw new RuntimeException(); }
  public  org.apache.spark.WritableConverter<byte[]> bytesWritableConverter () { throw new RuntimeException(); }
  public  org.apache.spark.WritableConverter<java.lang.String> stringWritableConverter () { throw new RuntimeException(); }
  public <T extends org.apache.hadoop.io.Writable> org.apache.spark.WritableConverter<T> writableWritableConverter () { throw new RuntimeException(); }
  /**
   * Find the JAR from which a given class was loaded, to make it easy for users to pass
   * their JARs to SparkContext.
   */
  public  scala.Option<java.lang.String> jarOfClass (java.lang.Class<?> cls) { throw new RuntimeException(); }
  /**
   * Find the JAR that contains the class of a particular object, to make it easy for users
   * to pass their JARs to SparkContext. In most cases you can call jarOfObject(this) in
   * your driver program.
   */
  public  scala.Option<java.lang.String> jarOfObject (java.lang.Object obj) { throw new RuntimeException(); }
  /**
   * Creates a modified version of a SparkConf with the parameters that can be passed separately
   * to SparkContext, to make it easier to write SparkContext's constructors. This ignores
   * parameters that are passed as the default value of null, instead of throwing an exception
   * like SparkConf would.
   */
  private  org.apache.spark.SparkConf updatedConf (org.apache.spark.SparkConf conf, java.lang.String master, java.lang.String appName, java.lang.String sparkHome, scala.collection.Seq<java.lang.String> jars, scala.collection.Map<java.lang.String, java.lang.String> environment) { throw new RuntimeException(); }
  /** Creates a task scheduler based on a given master URL. Extracted for testing. */
  private  org.apache.spark.scheduler.TaskScheduler createTaskScheduler (org.apache.spark.SparkContext sc, java.lang.String master) { throw new RuntimeException(); }
}
