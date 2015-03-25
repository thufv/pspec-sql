package org.apache.spark.sql.parquet;
// no position
public  class ParquetFilters$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final ParquetFilters$ MODULE$ = null;
  public   ParquetFilters$ () { throw new RuntimeException(); }
  public  java.lang.String PARQUET_FILTER_DATA () { throw new RuntimeException(); }
  public  scala.Option<parquet.filter2.compat.FilterCompat.Filter> createRecordFilter (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> filterExpressions) { throw new RuntimeException(); }
  public  scala.Option<parquet.filter2.predicate.FilterPredicate> createFilter (org.apache.spark.sql.catalyst.expressions.Expression predicate) { throw new RuntimeException(); }
  /**
   * Note: Inside the Hadoop API we only have access to <code>Configuration</code>, not to
   * {@link org.apache.spark.SparkContext}, so we cannot use broadcasts to convey
   * the actual filter predicate.
   */
  public  void serializeFilterExpressions (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> filters, org.apache.hadoop.conf.Configuration conf) { throw new RuntimeException(); }
  /**
   * Note: Inside the Hadoop API we only have access to <code>Configuration</code>, not to
   * {@link org.apache.spark.SparkContext}, so we cannot use broadcasts to convey
   * the actual filter predicate.
   */
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> deserializeFilterExpressions (org.apache.hadoop.conf.Configuration conf) { throw new RuntimeException(); }
}
