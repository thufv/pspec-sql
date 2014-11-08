package org.apache.spark.sql.columnar;
private  interface ColumnBuilder {
  static public  int DEFAULT_INITIAL_BUFFER_SIZE () { throw new RuntimeException(); }
  static private  java.nio.ByteBuffer ensureFreeSpace (java.nio.ByteBuffer orig, int size) { throw new RuntimeException(); }
  static public  org.apache.spark.sql.columnar.ColumnBuilder apply (int typeId, int initialSize, java.lang.String columnName, boolean useCompression) { throw new RuntimeException(); }
  /**
   * Initializes with an approximate lower bound on the expected number of elements in this column.
   */
  public abstract  void initialize (int initialSize, java.lang.String columnName, boolean useCompression) ;
  /**
   * Appends <code>row(ordinal)</code> to the column builder.
   */
  public abstract  void appendFrom (org.apache.spark.sql.catalyst.expressions.Row row, int ordinal) ;
  /**
   * Column statistics information
   */
  public abstract  org.apache.spark.sql.columnar.ColumnStats<?, ?> columnStats () ;
  /**
   * Returns the final columnar byte buffer.
   */
  public abstract  java.nio.ByteBuffer build () ;
}
