package org.apache.spark.sql;
/**
 * :: Experimental ::
 * A set of methods for aggregations on a {@link DataFrame}, created by {@link DataFrame.groupBy}.
 */
public  class GroupedData {
  /**
   * Compute aggregates by specifying a series of aggregate columns. Unlike other methods in this
   * class, the resulting {@link DataFrame} won't automatically include the grouping columns.
   * <p>
   * The available aggregate methods are defined in {@link org.apache.spark.sql.functions}.
   * <p>
   * <pre><code>
   *   // Selects the age of the oldest employee and the aggregate expense for each department
   *
   *   // Scala:
   *   import org.apache.spark.sql.functions._
   *   df.groupBy("department").agg($"department", max($"age"), sum($"expense"))
   *
   *   // Java:
   *   import static org.apache.spark.sql.functions.*;
   *   df.groupBy("department").agg(col("department"), max(col("age")), sum(col("expense")));
   * </code></pre>
   */
  public  org.apache.spark.sql.DataFrame agg (org.apache.spark.sql.Column expr, org.apache.spark.sql.Column... exprs) { throw new RuntimeException(); }
  /**
   * Compute the average value for each numeric columns for each group. This is an alias for <code>avg</code>.
   * The resulting {@link DataFrame} will also contain the grouping columns.
   * When specified columns are given, only compute the average values for them.
   */
  public  org.apache.spark.sql.DataFrame mean (java.lang.String... colNames) { throw new RuntimeException(); }
  /**
   * Compute the max value for each numeric columns for each group.
   * The resulting {@link DataFrame} will also contain the grouping columns.
   * When specified columns are given, only compute the max values for them.
   */
  public  org.apache.spark.sql.DataFrame max (java.lang.String... colNames) { throw new RuntimeException(); }
  /**
   * Compute the mean value for each numeric columns for each group.
   * The resulting {@link DataFrame} will also contain the grouping columns.
   * When specified columns are given, only compute the mean values for them.
   */
  public  org.apache.spark.sql.DataFrame avg (java.lang.String... colNames) { throw new RuntimeException(); }
  /**
   * Compute the min value for each numeric column for each group.
   * The resulting {@link DataFrame} will also contain the grouping columns.
   * When specified columns are given, only compute the min values for them.
   */
  public  org.apache.spark.sql.DataFrame min (java.lang.String... colNames) { throw new RuntimeException(); }
  /**
   * Compute the sum for each numeric columns for each group.
   * The resulting {@link DataFrame} will also contain the grouping columns.
   * When specified columns are given, only compute the sum for them.
   */
  public  org.apache.spark.sql.DataFrame sum (java.lang.String... colNames) { throw new RuntimeException(); }
  // not preceding
  protected   GroupedData (org.apache.spark.sql.DataFrame df, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> groupingExprs) { throw new RuntimeException(); }
  private  org.apache.spark.sql.DataFrame toDF (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.NamedExpression> aggExprs) { throw new RuntimeException(); }
  private  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.NamedExpression> aggregateNumericColumns (scala.collection.Seq<java.lang.String> colNames, scala.Function1<org.apache.spark.sql.catalyst.expressions.Expression, org.apache.spark.sql.catalyst.expressions.Expression> f) { throw new RuntimeException(); }
  private  scala.Function1<org.apache.spark.sql.catalyst.expressions.Expression, org.apache.spark.sql.catalyst.expressions.Expression> strToExpr (java.lang.String expr) { throw new RuntimeException(); }
  /**
   * (Scala-specific) Compute aggregates by specifying a map from column name to
   * aggregate methods. The resulting {@link DataFrame} will also contain the grouping columns.
   * <p>
   * The available aggregate methods are <code>avg</code>, <code>max</code>, <code>min</code>, <code>sum</code>, <code>count</code>.
   * <pre><code>
   *   // Selects the age of the oldest employee and the aggregate expense for each department
   *   df.groupBy("department").agg(
   *     "age" -&gt; "max",
   *     "expense" -&gt; "sum"
   *   )
   * </code></pre>
   */
  public  org.apache.spark.sql.DataFrame agg (scala.Tuple2<java.lang.String, java.lang.String> aggExpr, scala.collection.Seq<scala.Tuple2<java.lang.String, java.lang.String>> aggExprs) { throw new RuntimeException(); }
  /**
   * (Scala-specific) Compute aggregates by specifying a map from column name to
   * aggregate methods. The resulting {@link DataFrame} will also contain the grouping columns.
   * <p>
   * The available aggregate methods are <code>avg</code>, <code>max</code>, <code>min</code>, <code>sum</code>, <code>count</code>.
   * <pre><code>
   *   // Selects the age of the oldest employee and the aggregate expense for each department
   *   df.groupBy("department").agg(Map(
   *     "age" -&gt; "max",
   *     "expense" -&gt; "sum"
   *   ))
   * </code></pre>
   */
  public  org.apache.spark.sql.DataFrame agg (scala.collection.immutable.Map<java.lang.String, java.lang.String> exprs) { throw new RuntimeException(); }
  /**
   * (Java-specific) Compute aggregates by specifying a map from column name to
   * aggregate methods. The resulting {@link DataFrame} will also contain the grouping columns.
   * <p>
   * The available aggregate methods are <code>avg</code>, <code>max</code>, <code>min</code>, <code>sum</code>, <code>count</code>.
   * <pre><code>
   *   // Selects the age of the oldest employee and the aggregate expense for each department
   *   import com.google.common.collect.ImmutableMap;
   *   df.groupBy("department").agg(ImmutableMap.&lt;String, String&gt;builder()
   *     .put("age", "max")
   *     .put("expense", "sum")
   *     .build());
   * </code></pre>
   */
  public  org.apache.spark.sql.DataFrame agg (java.util.Map<java.lang.String, java.lang.String> exprs) { throw new RuntimeException(); }
  /**
   * Compute aggregates by specifying a series of aggregate columns. Unlike other methods in this
   * class, the resulting {@link DataFrame} won't automatically include the grouping columns.
   * <p>
   * The available aggregate methods are defined in {@link org.apache.spark.sql.functions}.
   * <p>
   * <pre><code>
   *   // Selects the age of the oldest employee and the aggregate expense for each department
   *
   *   // Scala:
   *   import org.apache.spark.sql.functions._
   *   df.groupBy("department").agg($"department", max($"age"), sum($"expense"))
   *
   *   // Java:
   *   import static org.apache.spark.sql.functions.*;
   *   df.groupBy("department").agg(col("department"), max(col("age")), sum(col("expense")));
   * </code></pre>
   */
  public  org.apache.spark.sql.DataFrame agg (org.apache.spark.sql.Column expr, scala.collection.Seq<org.apache.spark.sql.Column> exprs) { throw new RuntimeException(); }
  /**
   * Count the number of rows for each group.
   * The resulting {@link DataFrame} will also contain the grouping columns.
   */
  public  org.apache.spark.sql.DataFrame count () { throw new RuntimeException(); }
  /**
   * Compute the average value for each numeric columns for each group. This is an alias for <code>avg</code>.
   * The resulting {@link DataFrame} will also contain the grouping columns.
   * When specified columns are given, only compute the average values for them.
   */
  public  org.apache.spark.sql.DataFrame mean (scala.collection.Seq<java.lang.String> colNames) { throw new RuntimeException(); }
  /**
   * Compute the max value for each numeric columns for each group.
   * The resulting {@link DataFrame} will also contain the grouping columns.
   * When specified columns are given, only compute the max values for them.
   */
  public  org.apache.spark.sql.DataFrame max (scala.collection.Seq<java.lang.String> colNames) { throw new RuntimeException(); }
  /**
   * Compute the mean value for each numeric columns for each group.
   * The resulting {@link DataFrame} will also contain the grouping columns.
   * When specified columns are given, only compute the mean values for them.
   */
  public  org.apache.spark.sql.DataFrame avg (scala.collection.Seq<java.lang.String> colNames) { throw new RuntimeException(); }
  /**
   * Compute the min value for each numeric column for each group.
   * The resulting {@link DataFrame} will also contain the grouping columns.
   * When specified columns are given, only compute the min values for them.
   */
  public  org.apache.spark.sql.DataFrame min (scala.collection.Seq<java.lang.String> colNames) { throw new RuntimeException(); }
  /**
   * Compute the sum for each numeric columns for each group.
   * The resulting {@link DataFrame} will also contain the grouping columns.
   * When specified columns are given, only compute the sum for them.
   */
  public  org.apache.spark.sql.DataFrame sum (scala.collection.Seq<java.lang.String> colNames) { throw new RuntimeException(); }
}
