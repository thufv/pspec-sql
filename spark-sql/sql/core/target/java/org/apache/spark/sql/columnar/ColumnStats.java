package org.apache.spark.sql.columnar;
/**
 * Used to collect statistical information when building in-memory columns.
 * <p>
 * NOTE: we intentionally avoid using <code>Ordering[T]</code> to compare values here because <code>Ordering[T]</code>
 * brings significant performance penalty.
 */
private abstract class ColumnStats<T extends org.apache.spark.sql.catalyst.types.DataType, JvmType extends java.lang.Object> implements scala.Serializable {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Select(Select(Select(Select(Ident(org), org.apache), org.apache.spark), org.apache.spark.sql), org.apache.spark.sql.catalyst), org.apache.spark.sql.catalyst.types), org.apache.spark.sql.catalyst.types.DataType))))
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   ColumnStats () { throw new RuntimeException(); }
  /**
   * Closed lower bound of this column.
   */
  public abstract  JvmType lowerBound () ;
  /**
   * Closed upper bound of this column.
   */
  public abstract  JvmType upperBound () ;
  /**
   * Gathers statistics information from <code>row(ordinal)</code>.
   */
  public abstract  void gatherStats (org.apache.spark.sql.catalyst.expressions.Row row, int ordinal) ;
  /**
   * Returns <code>true</code> if <code>lower <= row(ordinal) <= upper</code>.
   */
  public abstract  boolean contains (org.apache.spark.sql.catalyst.expressions.Row row, int ordinal) ;
  /**
   * Returns <code>true</code> if <code>row(ordinal) < upper</code> holds.
   */
  public abstract  boolean isAbove (org.apache.spark.sql.catalyst.expressions.Row row, int ordinal) ;
  /**
   * Returns <code>true</code> if <code>lower < row(ordinal)</code> holds.
   */
  public abstract  boolean isBelow (org.apache.spark.sql.catalyst.expressions.Row row, int ordinal) ;
  /**
   * Returns <code>true</code> if <code>row(ordinal) <= upper</code> holds.
   */
  public abstract  boolean isAtOrAbove (org.apache.spark.sql.catalyst.expressions.Row row, int ordinal) ;
  /**
   * Returns <code>true</code> if <code>lower <= row(ordinal)</code> holds.
   */
  public abstract  boolean isAtOrBelow (org.apache.spark.sql.catalyst.expressions.Row row, int ordinal) ;
}
