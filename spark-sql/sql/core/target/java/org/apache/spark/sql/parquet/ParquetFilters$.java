package org.apache.spark.sql.parquet;
// no position
public  class ParquetFilters$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final ParquetFilters$ MODULE$ = null;
  public   ParquetFilters$ () { throw new RuntimeException(); }
  public  java.lang.String PARQUET_FILTER_DATA () { throw new RuntimeException(); }
  public  java.lang.String PARQUET_FILTER_PUSHDOWN_ENABLED () { throw new RuntimeException(); }
  public  parquet.filter.UnboundRecordFilter createRecordFilter (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> filterExpressions) { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.sql.parquet.CatalystFilter> createFilter (org.apache.spark.sql.catalyst.expressions.Expression expression) { throw new RuntimeException(); }
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
  /**
   * Try to find the given expression in the tree of filters in order to
   * determine whether it is safe to remove it from the higher level filters. Note
   * that strictly speaking we could stop the search whenever an expression is found
   * that contains this expression as subexpression (e.g., when searching for "a"
   * and "(a or c)" is found) but we don't care about optimizations here since the
   * filter tree is assumed to be small.
   * <p>
   * @param filter The {@link org.apache.spark.sql.parquet.CatalystFilter} to expand
   *               and search
   * @param expression The expression to look for
   * @return An optional {@link org.apache.spark.sql.parquet.CatalystFilter} that
   *         contains the expression.
   */
  public  scala.Option<org.apache.spark.sql.parquet.CatalystFilter> findExpression (org.apache.spark.sql.parquet.CatalystFilter filter, org.apache.spark.sql.catalyst.expressions.Expression expression) { throw new RuntimeException(); }
}
