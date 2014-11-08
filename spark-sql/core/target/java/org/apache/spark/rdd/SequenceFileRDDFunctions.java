package org.apache.spark.rdd;
/**
 * Extra functions available on RDDs of (key, value) pairs to create a Hadoop SequenceFile,
 * through an implicit conversion. Note that this can't be part of PairRDDFunctions because
 * we need more implicit parameters to convert our keys and values to Writable.
 * <p>
 * Import <code>org.apache.spark.SparkContext._</code> at the top of their program to use these functions.
 */
public  class SequenceFileRDDFunctions<K extends java.lang.Object, V extends java.lang.Object> implements org.apache.spark.Logging, scala.Serializable {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   SequenceFileRDDFunctions (org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> self, scala.Function1<K, org.apache.hadoop.io.Writable> evidence$1, scala.reflect.ClassTag<K> evidence$2, scala.Function1<V, org.apache.hadoop.io.Writable> evidence$3, scala.reflect.ClassTag<V> evidence$4) { throw new RuntimeException(); }
  private <T extends java.lang.Object> java.lang.Class<? extends org.apache.hadoop.io.Writable> getWritableClass (scala.Function1<T, org.apache.hadoop.io.Writable> evidence$5, scala.reflect.ClassTag<T> evidence$6) { throw new RuntimeException(); }
  /**
   * Output the RDD as a Hadoop SequenceFile using the Writable types we infer from the RDD's key
   * and value types. If the key or value are Writable, then we use their classes directly;
   * otherwise we map primitive types such as Int and Double to IntWritable, DoubleWritable, etc,
   * byte arrays to BytesWritable, and Strings to Text. The <code>path</code> can be on any Hadoop-supported
   * file system.
   */
  public  void saveAsSequenceFile (java.lang.String path, scala.Option<java.lang.Class<? extends org.apache.hadoop.io.compress.CompressionCodec>> codec) { throw new RuntimeException(); }
}
