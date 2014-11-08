package org.apache.spark.api.python;
/**
 * Convert and RDD of Java objects to and RDD of serialized Python objects, that is usable by
 * PySpark.
 */
private  class BytesToString implements org.apache.spark.api.java.function.Function<byte[], java.lang.String> {
  public   BytesToString () { throw new RuntimeException(); }
  public  java.lang.String call (byte[] arr) { throw new RuntimeException(); }
}
