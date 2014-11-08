package org.apache.spark.sql.columnar.compression;
private abstract interface CompressibleColumnAccessor<T extends org.apache.spark.sql.catalyst.types.NativeType> extends org.apache.spark.sql.columnar.ColumnAccessor {
  private  org.apache.spark.sql.columnar.compression.Decoder<T> decoder () ;
  protected  void initialize () ;
  public  boolean hasNext () ;
  public  java.lang.Object extractSingle (java.nio.ByteBuffer buffer) ;
}
