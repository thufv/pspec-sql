package org.apache.spark.sql.columnar;
private  class BasicColumnBuilder<T extends org.apache.spark.sql.catalyst.types.DataType, JvmType extends java.lang.Object> implements org.apache.spark.sql.columnar.ColumnBuilder {
  public  org.apache.spark.sql.columnar.ColumnStats<T, JvmType> columnStats () { throw new RuntimeException(); }
  public  org.apache.spark.sql.columnar.ColumnType<T, JvmType> columnType () { throw new RuntimeException(); }
  // not preceding
  public   BasicColumnBuilder (org.apache.spark.sql.columnar.ColumnStats<T, JvmType> columnStats, org.apache.spark.sql.columnar.ColumnType<T, JvmType> columnType) { throw new RuntimeException(); }
  protected  java.lang.String columnName () { throw new RuntimeException(); }
  protected  java.nio.ByteBuffer buffer () { throw new RuntimeException(); }
  public  void initialize (int initialSize, java.lang.String columnName, boolean useCompression) { throw new RuntimeException(); }
  public  void appendFrom (org.apache.spark.sql.catalyst.expressions.Row row, int ordinal) { throw new RuntimeException(); }
  public  java.nio.ByteBuffer build () { throw new RuntimeException(); }
}
