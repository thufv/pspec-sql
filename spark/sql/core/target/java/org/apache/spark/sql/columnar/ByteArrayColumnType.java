package org.apache.spark.sql.columnar;
public abstract class ByteArrayColumnType<T extends org.apache.spark.sql.types.DataType> extends org.apache.spark.sql.columnar.ColumnType<T, byte[]> {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Select(Select(Select(Ident(org), org.apache), org.apache.spark), org.apache.spark.sql), org.apache.spark.sql.types), org.apache.spark.sql.types.DataType))))
  public   ByteArrayColumnType (int typeId, int defaultSize) { throw new RuntimeException(); }
  public  int actualSize (org.apache.spark.sql.Row row, int ordinal) { throw new RuntimeException(); }
  public  void append (byte[] v, java.nio.ByteBuffer buffer) { throw new RuntimeException(); }
  public  byte[] extract (java.nio.ByteBuffer buffer) { throw new RuntimeException(); }
}
