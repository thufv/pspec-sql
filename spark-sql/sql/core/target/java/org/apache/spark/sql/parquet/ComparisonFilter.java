package org.apache.spark.sql.parquet;
private  class ComparisonFilter extends org.apache.spark.sql.parquet.CatalystFilter implements scala.Product, scala.Serializable {
  static public  org.apache.spark.sql.parquet.CatalystFilter createBooleanFilter (java.lang.String columnName, boolean value, org.apache.spark.sql.catalyst.expressions.Predicate predicate) { throw new RuntimeException(); }
  static public  org.apache.spark.sql.parquet.CatalystFilter createStringFilter (java.lang.String columnName, java.lang.String value, org.apache.spark.sql.catalyst.expressions.Predicate predicate) { throw new RuntimeException(); }
  static public  org.apache.spark.sql.parquet.CatalystFilter createIntFilter (java.lang.String columnName, scala.Function1<java.lang.Object, java.lang.Object> func, org.apache.spark.sql.catalyst.expressions.Predicate predicate) { throw new RuntimeException(); }
  static public  org.apache.spark.sql.parquet.CatalystFilter createLongFilter (java.lang.String columnName, scala.Function1<java.lang.Object, java.lang.Object> func, org.apache.spark.sql.catalyst.expressions.Predicate predicate) { throw new RuntimeException(); }
  static public  org.apache.spark.sql.parquet.CatalystFilter createDoubleFilter (java.lang.String columnName, scala.Function1<java.lang.Object, java.lang.Object> func, org.apache.spark.sql.catalyst.expressions.Predicate predicate) { throw new RuntimeException(); }
  static public  org.apache.spark.sql.parquet.CatalystFilter createFloatFilter (java.lang.String columnName, scala.Function1<java.lang.Object, java.lang.Object> func, org.apache.spark.sql.catalyst.expressions.Predicate predicate) { throw new RuntimeException(); }
  // not preceding
  public  java.lang.String columnName () { throw new RuntimeException(); }
  private  parquet.filter.UnboundRecordFilter filter () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Predicate predicate () { throw new RuntimeException(); }
  // not preceding
  public   ComparisonFilter (java.lang.String columnName, parquet.filter.UnboundRecordFilter filter, org.apache.spark.sql.catalyst.expressions.Predicate predicate) { throw new RuntimeException(); }
  public  parquet.filter.RecordFilter bind (java.lang.Iterable<parquet.column.ColumnReader> readers) { throw new RuntimeException(); }
}
