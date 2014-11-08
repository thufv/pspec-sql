package org.apache.spark.sql.hive;
private abstract interface HiveFunctionFactory {
  public abstract  java.lang.String functionClassName () ;
  public <UDFType extends java.lang.Object> UDFType createFunction () ;
}
