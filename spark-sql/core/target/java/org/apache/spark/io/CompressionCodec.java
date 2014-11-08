package org.apache.spark.io;
/**
 * :: DeveloperApi ::
 * CompressionCodec allows the customization of choosing different compression implementations
 * to be used in block storage.
 * <p>
 * Note: The wire protocol for a codec is not guaranteed compatible across versions of Spark.
 *       This is intended for use as an internal compression utility within a single
 *       Spark application.
 */
public  interface CompressionCodec {
  static private  scala.collection.immutable.Map<java.lang.String, java.lang.String> shortCompressionCodecNames () { throw new RuntimeException(); }
  static public  org.apache.spark.io.CompressionCodec createCodec (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  static public  org.apache.spark.io.CompressionCodec createCodec (org.apache.spark.SparkConf conf, java.lang.String codecName) { throw new RuntimeException(); }
  static public  java.lang.String DEFAULT_COMPRESSION_CODEC () { throw new RuntimeException(); }
  static public  scala.collection.Seq<java.lang.String> ALL_COMPRESSION_CODECS () { throw new RuntimeException(); }
  public abstract  java.io.OutputStream compressedOutputStream (java.io.OutputStream s) ;
  public abstract  java.io.InputStream compressedInputStream (java.io.InputStream s) ;
}
