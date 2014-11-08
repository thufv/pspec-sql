package org.apache.spark.sql.parquet;
private  class OrFilter extends org.apache.spark.sql.parquet.CatalystFilter implements scala.Product, scala.Serializable {
  private  parquet.filter.UnboundRecordFilter filter () { throw new RuntimeException(); }
  public  org.apache.spark.sql.parquet.CatalystFilter left () { throw new RuntimeException(); }
  public  org.apache.spark.sql.parquet.CatalystFilter right () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Or predicate () { throw new RuntimeException(); }
  // not preceding
  public   OrFilter (parquet.filter.UnboundRecordFilter filter, org.apache.spark.sql.parquet.CatalystFilter left, org.apache.spark.sql.parquet.CatalystFilter right, org.apache.spark.sql.catalyst.expressions.Or predicate) { throw new RuntimeException(); }
  public   OrFilter (org.apache.spark.sql.parquet.CatalystFilter l, org.apache.spark.sql.parquet.CatalystFilter r) { throw new RuntimeException(); }
  public  parquet.filter.RecordFilter bind (java.lang.Iterable<parquet.column.ColumnReader> readers) { throw new RuntimeException(); }
}
