package org.apache.spark.sql.columnar.compression;
// no position
private  class BooleanBitSet implements org.apache.spark.sql.columnar.compression.CompressionScheme, scala.Product, scala.Serializable {
  static public  class Encoder implements org.apache.spark.sql.columnar.compression.Encoder<org.apache.spark.sql.catalyst.types.BooleanType$> {
    public   Encoder () { throw new RuntimeException(); }
    private  int _uncompressedSize () { throw new RuntimeException(); }
    public  void gatherCompressibilityStats (boolean value, org.apache.spark.sql.columnar.NativeColumnType<org.apache.spark.sql.catalyst.types.BooleanType$> columnType) { throw new RuntimeException(); }
    public  java.nio.ByteBuffer compress (java.nio.ByteBuffer from, java.nio.ByteBuffer to, org.apache.spark.sql.columnar.NativeColumnType<org.apache.spark.sql.catalyst.types.BooleanType$> columnType) { throw new RuntimeException(); }
    public  int uncompressedSize () { throw new RuntimeException(); }
    public  int compressedSize () { throw new RuntimeException(); }
  }
  static public  class Decoder implements org.apache.spark.sql.columnar.compression.Decoder<org.apache.spark.sql.catalyst.types.BooleanType$> {
    public   Decoder (java.nio.ByteBuffer buffer) { throw new RuntimeException(); }
    private  int count () { throw new RuntimeException(); }
    private  long currentWord () { throw new RuntimeException(); }
    private  int visited () { throw new RuntimeException(); }
    public  boolean next () { throw new RuntimeException(); }
    public  boolean hasNext () { throw new RuntimeException(); }
  }
  static public  int typeId () { throw new RuntimeException(); }
  static public  int BITS_PER_LONG () { throw new RuntimeException(); }
  static public <T extends org.apache.spark.sql.catalyst.types.NativeType> org.apache.spark.sql.columnar.compression.Decoder<T> decoder (java.nio.ByteBuffer buffer, org.apache.spark.sql.columnar.NativeColumnType<T> columnType) { throw new RuntimeException(); }
  static public <T extends org.apache.spark.sql.catalyst.types.NativeType> org.apache.spark.sql.columnar.compression.Encoder<T> encoder () { throw new RuntimeException(); }
  static public  boolean supports (org.apache.spark.sql.columnar.ColumnType<?, ?> columnType) { throw new RuntimeException(); }
}
