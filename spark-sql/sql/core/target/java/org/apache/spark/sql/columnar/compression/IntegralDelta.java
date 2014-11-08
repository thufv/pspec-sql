package org.apache.spark.sql.columnar.compression;
private abstract class IntegralDelta<I extends org.apache.spark.sql.catalyst.types.IntegralType> implements org.apache.spark.sql.columnar.compression.CompressionScheme {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Select(Select(Select(Select(Ident(org), org.apache), org.apache.spark), org.apache.spark.sql), org.apache.spark.sql.catalyst), org.apache.spark.sql.catalyst.types), org.apache.spark.sql.catalyst.types.IntegralType))))
  public   IntegralDelta () { throw new RuntimeException(); }
  public <T extends org.apache.spark.sql.catalyst.types.NativeType> org.apache.spark.sql.columnar.compression.Decoder<T> decoder (java.nio.ByteBuffer buffer, org.apache.spark.sql.columnar.NativeColumnType<T> columnType) { throw new RuntimeException(); }
  public <T extends org.apache.spark.sql.catalyst.types.NativeType> org.apache.spark.sql.columnar.compression.Encoder<T> encoder () { throw new RuntimeException(); }
  /**
   * Computes <code>delta = x - y</code>, returns <code>(true, delta)</code> if <code>delta</code> can fit into a single byte, or
   * <code>(false, 0: Byte)</code> otherwise.
   */
  protected abstract  scala.Tuple2<java.lang.Object, java.lang.Object> byteSizedDelta (java.lang.Object x, java.lang.Object y) ;
  /**
   * Simply computes <code>x + delta</code>
   */
  protected abstract  java.lang.Object addDelta (java.lang.Object x, byte delta) ;
  public  class Encoder implements org.apache.spark.sql.columnar.compression.Encoder<I> {
    public   Encoder () { throw new RuntimeException(); }
    private  int _compressedSize () { throw new RuntimeException(); }
    private  int _uncompressedSize () { throw new RuntimeException(); }
    private  java.lang.Object prev () { throw new RuntimeException(); }
    private  boolean initial () { throw new RuntimeException(); }
    public  void gatherCompressibilityStats (java.lang.Object value, org.apache.spark.sql.columnar.NativeColumnType<I> columnType) { throw new RuntimeException(); }
    public  java.nio.ByteBuffer compress (java.nio.ByteBuffer from, java.nio.ByteBuffer to, org.apache.spark.sql.columnar.NativeColumnType<I> columnType) { throw new RuntimeException(); }
    public  int uncompressedSize () { throw new RuntimeException(); }
    public  int compressedSize () { throw new RuntimeException(); }
  }
  public  class Decoder implements org.apache.spark.sql.columnar.compression.Decoder<I> {
    public   Decoder (java.nio.ByteBuffer buffer, org.apache.spark.sql.columnar.NativeColumnType<I> columnType) { throw new RuntimeException(); }
    private  java.lang.Object prev () { throw new RuntimeException(); }
    public  java.lang.Object next () { throw new RuntimeException(); }
    public  boolean hasNext () { throw new RuntimeException(); }
  }
}
