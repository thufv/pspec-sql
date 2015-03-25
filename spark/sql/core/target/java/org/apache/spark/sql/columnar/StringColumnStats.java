package org.apache.spark.sql.columnar;
public  class StringColumnStats implements org.apache.spark.sql.columnar.ColumnStats {
  public   StringColumnStats () { throw new RuntimeException(); }
  protected  java.lang.String upper () { throw new RuntimeException(); }
  protected  java.lang.String lower () { throw new RuntimeException(); }
  public  void gatherStats (org.apache.spark.sql.Row row, int ordinal) { throw new RuntimeException(); }
  public  org.apache.spark.sql.Row collectedStatistics () { throw new RuntimeException(); }
}
