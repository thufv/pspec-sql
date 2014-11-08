package org.apache.spark.sql.columnar.compression;
// no position
private  class DictionaryEncoding implements org.apache.spark.sql.columnar.compression.CompressionScheme, scala.Product, scala.Serializable {
  static public  class Encoder<T extends org.apache.spark.sql.catalyst.types.NativeType> implements org.apache.spark.sql.columnar.compression.Encoder<T> {
    // not preceding
    // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Select(Select(Select(Select(Ident(org), org.apache), org.apache.spark), org.apache.spark.sql), org.apache.spark.sql.catalyst), org.apache.spark.sql.catalyst.types), org.apache.spark.sql.catalyst.types.NativeType))))
    public   Encoder () { throw new RuntimeException(); }
    private  int _uncompressedSize () { throw new RuntimeException(); }
    private  boolean overflow () { throw new RuntimeException(); }
    private  int count () { throw new RuntimeException(); }
    private  scala.collection.mutable.ArrayBuffer<java.lang.Object> values () { throw new RuntimeException(); }
    private  scala.collection.mutable.HashMap<java.lang.Object, java.lang.Object> dictionary () { throw new RuntimeException(); }
    private  int dictionarySize () { throw new RuntimeException(); }
    public  void gatherCompressibilityStats (java.lang.Object value, org.apache.spark.sql.columnar.NativeColumnType<T> columnType) { throw new RuntimeException(); }
    public  java.nio.ByteBuffer compress (java.nio.ByteBuffer from, java.nio.ByteBuffer to, org.apache.spark.sql.columnar.NativeColumnType<T> columnType) { throw new RuntimeException(); }
    public  int uncompressedSize () { throw new RuntimeException(); }
    public  int compressedSize () { throw new RuntimeException(); }
  }
  static public  class Decoder<T extends org.apache.spark.sql.catalyst.types.NativeType> implements org.apache.spark.sql.columnar.compression.Decoder<T> {
    // not preceding
    // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Select(Select(Select(Select(Ident(org), org.apache), org.apache.spark), org.apache.spark.sql), org.apache.spark.sql.catalyst), org.apache.spark.sql.catalyst.types), org.apache.spark.sql.catalyst.types.NativeType))))
    public   Decoder (java.nio.ByteBuffer buffer, org.apache.spark.sql.columnar.NativeColumnType<T> columnType) { throw new RuntimeException(); }
    private  java.lang.Object dictionary () { throw new RuntimeException(); }
    public  java.lang.Object next () { throw new RuntimeException(); }
    public  boolean hasNext () { throw new RuntimeException(); }
  }
  static public  int typeId () { throw new RuntimeException(); }
  static public  short MAX_DICT_SIZE () { throw new RuntimeException(); }
  static public <T extends org.apache.spark.sql.catalyst.types.NativeType> org.apache.spark.sql.columnar.compression.DictionaryEncoding.Decoder<T> decoder (java.nio.ByteBuffer buffer, org.apache.spark.sql.columnar.NativeColumnType<T> columnType) { throw new RuntimeException(); }
  static public <T extends org.apache.spark.sql.catalyst.types.NativeType> org.apache.spark.sql.columnar.compression.DictionaryEncoding.Encoder<T> encoder () { throw new RuntimeException(); }
  static public  boolean supports (org.apache.spark.sql.columnar.ColumnType<?, ?> columnType) { throw new RuntimeException(); }
}
