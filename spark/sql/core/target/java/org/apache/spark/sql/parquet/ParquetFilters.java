package org.apache.spark.sql.parquet;
// no position
public  class ParquetFilters {
  static public  java.lang.String PARQUET_FILTER_DATA () { throw new RuntimeException(); }
  static public  scala.Option<parquet.filter2.compat.FilterCompat.Filter> createRecordFilter (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> filterExpressions) { throw new RuntimeException(); }
  static public  scala.Option<parquet.filter2.predicate.FilterPredicate> createFilter (org.apache.spark.sql.catalyst.expressions.Expression predicate) { throw new RuntimeException(); }
  /**
   * Note: Inside the Hadoop API we only have access to <code>Configuration</code>, not to
   * {@link org.apache.spark.SparkContext}, so we cannot use broadcasts to convey
   * the actual filter predicate.
   */
  static public  void serializeFilterExpressions (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> filters, org.apache.hadoop.conf.Configuration conf) { throw new RuntimeException(); }
  /**
   * Note: Inside the Hadoop API we only have access to <code>Configuration</code>, not to
   * {@link org.apache.spark.SparkContext}, so we cannot use broadcasts to convey
   * the actual filter predicate.
   */
  static public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> deserializeFilterExpressions (org.apache.hadoop.conf.Configuration conf) { throw new RuntimeException(); }
}
