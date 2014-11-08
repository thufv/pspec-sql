package org.apache.spark.api.python;
// no position
/** Utilities for working with Python objects <-> Hadoop-related objects */
private  class PythonHadoopUtil {
  /**
   * Convert a {@link java.util.Map} of properties to a {@link org.apache.hadoop.conf.Configuration}
   */
  static public  org.apache.hadoop.conf.Configuration mapToConf (java.util.Map<java.lang.String, java.lang.String> map) { throw new RuntimeException(); }
  /**
   * Merges two configurations, returns a copy of left with keys from right overwriting
   * any matching keys in left
   */
  static public  org.apache.hadoop.conf.Configuration mergeConfs (org.apache.hadoop.conf.Configuration left, org.apache.hadoop.conf.Configuration right) { throw new RuntimeException(); }
  /**
   * Converts an RDD of key-value pairs, where key and/or value could be instances of
   * {@link org.apache.hadoop.io.Writable}, into an RDD of base types, or vice versa.
   */
  static public <K extends java.lang.Object, V extends java.lang.Object> org.apache.spark.rdd.RDD<scala.Tuple2<java.lang.Object, java.lang.Object>> convertRDD (org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> rdd, org.apache.spark.api.python.Converter<java.lang.Object, java.lang.Object> keyConverter, org.apache.spark.api.python.Converter<java.lang.Object, java.lang.Object> valueConverter) { throw new RuntimeException(); }
}
