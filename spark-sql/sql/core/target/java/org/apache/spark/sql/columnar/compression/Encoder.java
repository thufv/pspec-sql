package org.apache.spark.sql.columnar.compression;
private abstract interface Encoder<T extends org.apache.spark.sql.catalyst.types.NativeType> {
  public  void gatherCompressibilityStats (java.lang.Object value, org.apache.spark.sql.columnar.NativeColumnType<T> columnType) ;
  public abstract  int compressedSize () ;
  public abstract  int uncompressedSize () ;
  public  double compressionRatio () ;
  public abstract  java.nio.ByteBuffer compress (java.nio.ByteBuffer from, java.nio.ByteBuffer to, org.apache.spark.sql.columnar.NativeColumnType<T> columnType) ;
}
