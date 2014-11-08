package org.apache.spark.sql.columnar;
private abstract class NativeColumnStats<T extends org.apache.spark.sql.catalyst.types.NativeType> extends org.apache.spark.sql.columnar.ColumnStats<T, java.lang.Object> {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Select(Select(Select(Select(Ident(org), org.apache), org.apache.spark), org.apache.spark.sql), org.apache.spark.sql.catalyst), org.apache.spark.sql.catalyst.types), org.apache.spark.sql.catalyst.types.NativeType))))
  public   NativeColumnStats () { throw new RuntimeException(); }
  protected  java.lang.Object _lower () { throw new RuntimeException(); }
  protected  java.lang.Object _upper () { throw new RuntimeException(); }
  public abstract  scala.Tuple2<java.lang.Object, java.lang.Object> initialBounds () ;
  protected abstract  org.apache.spark.sql.columnar.NativeColumnType<T> columnType () ;
  public  java.lang.Object lowerBound () { throw new RuntimeException(); }
  public  java.lang.Object upperBound () { throw new RuntimeException(); }
  public  boolean isAtOrAbove (org.apache.spark.sql.catalyst.expressions.Row row, int ordinal) { throw new RuntimeException(); }
  public  boolean isAtOrBelow (org.apache.spark.sql.catalyst.expressions.Row row, int ordinal) { throw new RuntimeException(); }
}
