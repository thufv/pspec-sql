package org.apache.spark.io;
/**
 * :: DeveloperApi ::
 * LZ4 implementation of {@link org.apache.spark.io.CompressionCodec}.
 * Block size can be configured by <code>spark.io.compression.lz4.block.size</code>.
 * <p>
 * Note: The wire protocol for this codec is not guaranteed to be compatible across versions
 *       of Spark. This is intended for use as an internal compression utility within a single Spark
 *       application.
 */
public  class LZ4CompressionCodec implements org.apache.spark.io.CompressionCodec {
  public   LZ4CompressionCodec (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  public  java.io.OutputStream compressedOutputStream (java.io.OutputStream s) { throw new RuntimeException(); }
  public  java.io.InputStream compressedInputStream (java.io.InputStream s) { throw new RuntimeException(); }
}
