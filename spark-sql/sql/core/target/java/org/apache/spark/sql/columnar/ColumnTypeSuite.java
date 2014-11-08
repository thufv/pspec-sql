package org.apache.spark.sql.columnar;
public  class ColumnTypeSuite extends org.scalatest.FunSuite implements org.apache.spark.Logging {
  public   ColumnTypeSuite () { throw new RuntimeException(); }
  public  int DEFAULT_BUFFER_SIZE () { throw new RuntimeException(); }
  public <T extends org.apache.spark.sql.catalyst.types.NativeType> void testNativeColumnType (org.apache.spark.sql.columnar.NativeColumnType<T> columnType, scala.Function2<java.nio.ByteBuffer, java.lang.Object, scala.runtime.BoxedUnit> putter, scala.Function1<java.nio.ByteBuffer, java.lang.Object> getter) { throw new RuntimeException(); }
  public <T extends org.apache.spark.sql.catalyst.types.DataType, JvmType extends java.lang.Object> void testColumnType (org.apache.spark.sql.columnar.ColumnType<T, JvmType> columnType, scala.Function2<java.nio.ByteBuffer, JvmType, scala.runtime.BoxedUnit> putter, scala.Function1<java.nio.ByteBuffer, JvmType> getter) { throw new RuntimeException(); }
  private  java.lang.String hexDump (Object value) { throw new RuntimeException(); }
  private  Object dumpBuffer (java.nio.ByteBuffer buff) { throw new RuntimeException(); }
}
