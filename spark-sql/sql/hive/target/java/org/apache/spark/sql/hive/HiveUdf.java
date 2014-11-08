package org.apache.spark.sql.hive;
private abstract class HiveUdf extends org.apache.spark.sql.catalyst.expressions.Expression implements org.apache.spark.Logging, org.apache.spark.sql.hive.HiveFunctionFactory {
  public   HiveUdf () { throw new RuntimeException(); }
  public  boolean nullable () { throw new RuntimeException(); }
  public  org.apache.spark.sql.hive.HiveUdf.UDFType function () { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
