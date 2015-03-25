package org.apache.spark.sql.columnar;
public  class TimestampColumnStats implements org.apache.spark.sql.columnar.ColumnStats {
  public   TimestampColumnStats () { throw new RuntimeException(); }
  protected  java.sql.Timestamp upper () { throw new RuntimeException(); }
  protected  java.sql.Timestamp lower () { throw new RuntimeException(); }
  public  void gatherStats (org.apache.spark.sql.Row row, int ordinal) { throw new RuntimeException(); }
  public  org.apache.spark.sql.Row collectedStatistics () { throw new RuntimeException(); }
}
