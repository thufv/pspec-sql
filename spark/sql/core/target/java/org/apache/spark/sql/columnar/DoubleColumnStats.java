package org.apache.spark.sql.columnar;
public  class DoubleColumnStats implements org.apache.spark.sql.columnar.ColumnStats {
  public   DoubleColumnStats () { throw new RuntimeException(); }
  protected  double upper () { throw new RuntimeException(); }
  protected  double lower () { throw new RuntimeException(); }
  public  void gatherStats (org.apache.spark.sql.Row row, int ordinal) { throw new RuntimeException(); }
  public  org.apache.spark.sql.Row collectedStatistics () { throw new RuntimeException(); }
}
