package org.apache.spark.sql.columnar;
// no position
public  class STRING$ extends org.apache.spark.sql.columnar.NativeColumnType<org.apache.spark.sql.types.StringType$> {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final STRING$ MODULE$ = null;
  public   STRING$ () { throw new RuntimeException(); }
  public  int actualSize (org.apache.spark.sql.Row row, int ordinal) { throw new RuntimeException(); }
  public  void append (java.lang.String v, java.nio.ByteBuffer buffer) { throw new RuntimeException(); }
  public  java.lang.String extract (java.nio.ByteBuffer buffer) { throw new RuntimeException(); }
  public  void setField (org.apache.spark.sql.catalyst.expressions.MutableRow row, int ordinal, java.lang.String value) { throw new RuntimeException(); }
  public  java.lang.String getField (org.apache.spark.sql.Row row, int ordinal) { throw new RuntimeException(); }
  public  void copyField (org.apache.spark.sql.Row from, int fromOrdinal, org.apache.spark.sql.catalyst.expressions.MutableRow to, int toOrdinal) { throw new RuntimeException(); }
}
