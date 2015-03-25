package org.apache.spark.sql.columnar;
// no position
public  class TIMESTAMP$ extends org.apache.spark.sql.columnar.NativeColumnType<org.apache.spark.sql.types.TimestampType$> {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final TIMESTAMP$ MODULE$ = null;
  public   TIMESTAMP$ () { throw new RuntimeException(); }
  public  java.sql.Timestamp extract (java.nio.ByteBuffer buffer) { throw new RuntimeException(); }
  public  void append (java.sql.Timestamp v, java.nio.ByteBuffer buffer) { throw new RuntimeException(); }
  public  java.sql.Timestamp getField (org.apache.spark.sql.Row row, int ordinal) { throw new RuntimeException(); }
  public  void setField (org.apache.spark.sql.catalyst.expressions.MutableRow row, int ordinal, java.sql.Timestamp value) { throw new RuntimeException(); }
}
