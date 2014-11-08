package org.apache.spark.io;
/**
 * :: DeveloperApi ::
 * Snappy implementation of {@link org.apache.spark.io.CompressionCodec}.
 * Block size can be configured by <code>spark.io.compression.snappy.block.size</code>.
 * <p>
 * Note: The wire protocol for this codec is not guaranteed to be compatible across versions
 *       of Spark. This is intended for use as an internal compression utility within a single Spark
 *       application.
 */
public  class SnappyCompressionCodec implements org.apache.spark.io.CompressionCodec {
  public   SnappyCompressionCodec (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  public  java.io.OutputStream compressedOutputStream (java.io.OutputStream s) { throw new RuntimeException(); }
  public  java.io.InputStream compressedInputStream (java.io.InputStream s) { throw new RuntimeException(); }
}
