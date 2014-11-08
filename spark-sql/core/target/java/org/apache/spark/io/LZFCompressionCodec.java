package org.apache.spark.io;
/**
 * :: DeveloperApi ::
 * LZF implementation of {@link org.apache.spark.io.CompressionCodec}.
 * <p>
 * Note: The wire protocol for this codec is not guaranteed to be compatible across versions
 *       of Spark. This is intended for use as an internal compression utility within a single Spark
 *       application.
 */
public  class LZFCompressionCodec implements org.apache.spark.io.CompressionCodec {
  public   LZFCompressionCodec (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  public  java.io.OutputStream compressedOutputStream (java.io.OutputStream s) { throw new RuntimeException(); }
  public  java.io.InputStream compressedInputStream (java.io.InputStream s) { throw new RuntimeException(); }
}
