package org.apache.spark.sql.columnar;
public abstract class ComplexColumnBuilder<T extends org.apache.spark.sql.types.DataType, JvmType extends java.lang.Object> extends org.apache.spark.sql.columnar.BasicColumnBuilder<T, JvmType> implements org.apache.spark.sql.columnar.NullableColumnBuilder {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Select(Select(Select(Ident(org), org.apache), org.apache.spark), org.apache.spark.sql), org.apache.spark.sql.types), org.apache.spark.sql.types.DataType))))
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   ComplexColumnBuilder (org.apache.spark.sql.columnar.ColumnStats columnStats, org.apache.spark.sql.columnar.ColumnType<T, JvmType> columnType) { throw new RuntimeException(); }
}
