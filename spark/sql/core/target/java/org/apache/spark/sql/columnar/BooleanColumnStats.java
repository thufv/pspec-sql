package org.apache.spark.sql.columnar;
public  class BooleanColumnStats implements org.apache.spark.sql.columnar.ColumnStats {
  public   BooleanColumnStats () { throw new RuntimeException(); }
  protected  boolean upper () { throw new RuntimeException(); }
  protected  boolean lower () { throw new RuntimeException(); }
  public  void gatherStats (org.apache.spark.sql.Row row, int ordinal) { throw new RuntimeException(); }
  public  org.apache.spark.sql.Row collectedStatistics () { throw new RuntimeException(); }
}
