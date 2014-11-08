package org.apache.spark.sql.columnar;
private abstract class NativeColumnBuilder<T extends org.apache.spark.sql.catalyst.types.NativeType> extends org.apache.spark.sql.columnar.BasicColumnBuilder<T, java.lang.Object> implements org.apache.spark.sql.columnar.NullableColumnBuilder, org.apache.spark.sql.columnar.compression.AllCompressionSchemes, org.apache.spark.sql.columnar.compression.CompressibleColumnBuilder<T> {
  public  org.apache.spark.sql.columnar.NativeColumnStats<T> columnStats () { throw new RuntimeException(); }
  public  org.apache.spark.sql.columnar.NativeColumnType<T> columnType () { throw new RuntimeException(); }
  // not preceding
  public   NativeColumnBuilder (org.apache.spark.sql.columnar.NativeColumnStats<T> columnStats, org.apache.spark.sql.columnar.NativeColumnType<T> columnType) { throw new RuntimeException(); }
}
