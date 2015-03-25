package org.apache.spark.sql.columnar;
public  interface ColumnBuilder {
  /**
   * Initializes with an approximate lower bound on the expected number of elements in this column.
   */
  public  void initialize (int initialSize, java.lang.String columnName, boolean useCompression) ;
  /**
   * Appends <code>row(ordinal)</code> to the column builder.
   */
  public  void appendFrom (org.apache.spark.sql.Row row, int ordinal) ;
  /**
   * Column statistics information
   */
  public  org.apache.spark.sql.columnar.ColumnStats columnStats () ;
  /**
   * Returns the final columnar byte buffer.
   */
  public  java.nio.ByteBuffer build () ;
}
