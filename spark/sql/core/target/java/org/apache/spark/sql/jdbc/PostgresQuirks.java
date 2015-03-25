package org.apache.spark.sql.jdbc;
public  class PostgresQuirks extends org.apache.spark.sql.jdbc.DriverQuirks {
  public   PostgresQuirks () { throw new RuntimeException(); }
  public  org.apache.spark.sql.types.DataType getCatalystType (int sqlType, java.lang.String typeName, int size, org.apache.spark.sql.types.MetadataBuilder md) { throw new RuntimeException(); }
  public  scala.Tuple2<java.lang.String, scala.Option<java.lang.Object>> getJDBCType (org.apache.spark.sql.types.DataType dt) { throw new RuntimeException(); }
}
