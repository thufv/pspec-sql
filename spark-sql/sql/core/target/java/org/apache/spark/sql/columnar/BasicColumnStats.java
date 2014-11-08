package org.apache.spark.sql.columnar;
private abstract class BasicColumnStats<T extends org.apache.spark.sql.catalyst.types.NativeType> extends org.apache.spark.sql.columnar.NativeColumnStats<T> {
  protected  org.apache.spark.sql.columnar.NativeColumnType<T> columnType () { throw new RuntimeException(); }
  // not preceding
  public   BasicColumnStats (org.apache.spark.sql.columnar.NativeColumnType<T> columnType) { throw new RuntimeException(); }
}
