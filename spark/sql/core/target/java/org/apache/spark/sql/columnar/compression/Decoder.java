package org.apache.spark.sql.columnar.compression;
public  interface Decoder<T extends org.apache.spark.sql.types.NativeType> {
  public  void next (org.apache.spark.sql.catalyst.expressions.MutableRow row, int ordinal) ;
  public  boolean hasNext () ;
}
