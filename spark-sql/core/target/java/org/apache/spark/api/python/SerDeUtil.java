package org.apache.spark.api.python;
// no position
/** Utilities for serialization / deserialization between Python and Java, using Pickle. */
private  class SerDeUtil implements org.apache.spark.Logging {
  static private  scala.Tuple2<java.lang.Object, java.lang.Object> checkPickle (scala.Tuple2<java.lang.Object, java.lang.Object> t) { throw new RuntimeException(); }
  /**
   * Convert an RDD of key-value pairs to an RDD of serialized Python objects, that is usable
   * by PySpark. By default, if serialization fails, toString is called and the string
   * representation is serialized
   */
  static public  org.apache.spark.rdd.RDD<byte[]> pairRDDToPython (org.apache.spark.rdd.RDD<scala.Tuple2<java.lang.Object, java.lang.Object>> rdd, int batchSize) { throw new RuntimeException(); }
  /**
   * Convert an RDD of serialized Python tuple (K, V) to RDD[(K, V)].
   */
  static public <K extends java.lang.Object, V extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> pythonToPairRDD (org.apache.spark.rdd.RDD<byte[]> pyRDD, boolean batchSerialized) { throw new RuntimeException(); }
}
