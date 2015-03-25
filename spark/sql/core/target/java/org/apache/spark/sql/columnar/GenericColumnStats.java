package org.apache.spark.sql.columnar;
public  class GenericColumnStats implements org.apache.spark.sql.columnar.ColumnStats {
  public   GenericColumnStats () { throw new RuntimeException(); }
  public  void gatherStats (org.apache.spark.sql.Row row, int ordinal) { throw new RuntimeException(); }
  public  org.apache.spark.sql.Row collectedStatistics () { throw new RuntimeException(); }
}
