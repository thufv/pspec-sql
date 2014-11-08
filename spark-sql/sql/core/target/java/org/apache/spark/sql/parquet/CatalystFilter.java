package org.apache.spark.sql.parquet;
private abstract class CatalystFilter implements parquet.filter.UnboundRecordFilter {
  public  org.apache.spark.sql.catalyst.expressions.Predicate predicate () { throw new RuntimeException(); }
  // not preceding
  public   CatalystFilter (org.apache.spark.sql.catalyst.expressions.Predicate predicate) { throw new RuntimeException(); }
}
