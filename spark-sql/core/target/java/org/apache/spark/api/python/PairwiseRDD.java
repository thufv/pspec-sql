package org.apache.spark.api.python;
/**
 * Form an RDD[(Array[Byte], Array[Byte])] from key-value pairs returned from Python.
 * This is used by PySpark's shuffle operations.
 */
private  class PairwiseRDD extends org.apache.spark.rdd.RDD<scala.Tuple2<java.lang.Object, byte[]>> {
  public   PairwiseRDD (org.apache.spark.rdd.RDD<byte[]> prev) { throw new RuntimeException(); }
  public  org.apache.spark.Partition[] getPartitions () { throw new RuntimeException(); }
  public  scala.collection.Iterator<scala.Tuple2<java.lang.Object, byte[]>> compute (org.apache.spark.Partition split, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
  // not preceding
  public  org.apache.spark.api.java.JavaPairRDD<java.lang.Object, byte[]> asJavaPairRDD () { throw new RuntimeException(); }
}
