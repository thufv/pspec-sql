package org.apache.spark.sql.columnar;
private abstract interface NullableColumnAccessor extends org.apache.spark.sql.columnar.ColumnAccessor {
  private  java.nio.ByteBuffer nullsBuffer () ;
  private  int nullCount () ;
  private  int seenNulls () ;
  private  int nextNullIndex () ;
  private  int pos () ;
  protected  void initialize () ;
  public  void extractTo (org.apache.spark.sql.catalyst.expressions.MutableRow row, int ordinal) ;
  public  boolean hasNext () ;
}
