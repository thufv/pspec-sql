package org.apache.spark.sql.columnar;
// no position
public  class STRING extends org.apache.spark.sql.columnar.NativeColumnType<org.apache.spark.sql.types.StringType$> {
  static public  int actualSize (org.apache.spark.sql.Row row, int ordinal) { throw new RuntimeException(); }
  static public  void append (java.lang.String v, java.nio.ByteBuffer buffer) { throw new RuntimeException(); }
  static public  java.lang.String extract (java.nio.ByteBuffer buffer) { throw new RuntimeException(); }
  static public  void setField (org.apache.spark.sql.catalyst.expressions.MutableRow row, int ordinal, java.lang.String value) { throw new RuntimeException(); }
  static public  java.lang.String getField (org.apache.spark.sql.Row row, int ordinal) { throw new RuntimeException(); }
  static public  void copyField (org.apache.spark.sql.Row from, int fromOrdinal, org.apache.spark.sql.catalyst.expressions.MutableRow to, int toOrdinal) { throw new RuntimeException(); }
}
