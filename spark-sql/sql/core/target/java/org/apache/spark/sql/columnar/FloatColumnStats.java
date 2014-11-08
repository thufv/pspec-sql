package org.apache.spark.sql.columnar;
private  class FloatColumnStats extends org.apache.spark.sql.columnar.BasicColumnStats<org.apache.spark.sql.catalyst.types.FloatType$> {
  public   FloatColumnStats () { throw new RuntimeException(); }
  public  scala.Tuple2<java.lang.Object, java.lang.Object> initialBounds () { throw new RuntimeException(); }
  public  boolean isBelow (org.apache.spark.sql.catalyst.expressions.Row row, int ordinal) { throw new RuntimeException(); }
  public  boolean isAbove (org.apache.spark.sql.catalyst.expressions.Row row, int ordinal) { throw new RuntimeException(); }
  public  boolean contains (org.apache.spark.sql.catalyst.expressions.Row row, int ordinal) { throw new RuntimeException(); }
  public  void gatherStats (org.apache.spark.sql.catalyst.expressions.Row row, int ordinal) { throw new RuntimeException(); }
}
