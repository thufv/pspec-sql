package org.apache.spark.sql;
/**
 * :: Experimental ::
 * A distributed collection of data organized into named columns.
 * <p>
 * A {@link DataFrame} is equivalent to a relational table in Spark SQL. There are multiple ways
 * to create a {@link DataFrame}:
 * <pre><code>
 *   // Create a DataFrame from Parquet files
 *   val people = sqlContext.parquetFile("...")
 *
 *   // Create a DataFrame from data sources
 *   val df = sqlContext.load("...", "json")
 * </code></pre>
 * <p>
 * Once created, it can be manipulated using the various domain-specific-language (DSL) functions
 * defined in: {@link DataFrame} (this class), {@link Column}, and {@link functions}.
 * <p>
 * To select a column from the data frame, use <code>apply</code> method in Scala and <code>col</code> in Java.
 * <pre><code>
 *   val ageCol = people("age")  // in Scala
 *   Column ageCol = people.col("age")  // in Java
 * </code></pre>
 * <p>
 * Note that the {@link Column} type can also be manipulated through its various functions.
 * <pre><code>
 *   // The following creates a new column that increases everybody's age by 10.
 *   people("age") + 10  // in Scala
 *   people.col("age").plus(10);  // in Java
 * </code></pre>
 * <p>
 * A more concrete example in Scala:
 * <pre><code>
 *   // To create DataFrame using SQLContext
 *   val people = sqlContext.parquetFile("...")
 *   val department = sqlContext.parquetFile("...")
 *
 *   people.filter("age" &gt; 30)
 *     .join(department, people("deptId") === department("id"))
 *     .groupBy(department("name"), "gender")
 *     .agg(avg(people("salary")), max(people("age")))
 * </code></pre>
 * <p>
 * and in Java:
 * <pre><code>
 *   // To create DataFrame using SQLContext
 *   DataFrame people = sqlContext.parquetFile("...");
 *   DataFrame department = sqlContext.parquetFile("...");
 *
 *   people.filter("age".gt(30))
 *     .join(department, people.col("deptId").equalTo(department("id")))
 *     .groupBy(department.col("name"), "gender")
 *     .agg(avg(people.col("salary")), max(people.col("age")));
 * </code></pre>
 * <p>
 * @groupname basic Basic DataFrame functions
 * @groupname dfops Language Integrated Queries
 * @groupname rdd RDD Operations
 * @groupname output Output Operations
 * @groupname action Actions
 */
public  class DataFrame implements org.apache.spark.sql.RDDApi<org.apache.spark.sql.Row>, scala.Serializable {
  /**
   * Returns a new {@link DataFrame} with columns renamed. This can be quite convenient in conversion
   * from a RDD of tuples into a {@link DataFrame} with meaningful names. For example:
   * <pre><code>
   *   val rdd: RDD[(Int, String)] = ...
   *   rdd.toDF()  // this implicit conversion creates a DataFrame with column name _1 and _2
   *   rdd.toDF("id", "name")  // this creates a DataFrame with column name "id" and "name"
   * </code></pre>
   * @group basic
   */
  public  org.apache.spark.sql.DataFrame toDF (java.lang.String... colNames) { throw new RuntimeException(); }
  /**
   * Returns a new {@link DataFrame} sorted by the specified column, all in ascending order.
   * <pre><code>
   *   // The following 3 are equivalent
   *   df.sort("sortcol")
   *   df.sort($"sortcol")
   *   df.sort($"sortcol".asc)
   * </code></pre>
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame sort (java.lang.String sortCol, java.lang.String... sortCols) { throw new RuntimeException(); }
  /**
   * Returns a new {@link DataFrame} sorted by the given expressions. For example:
   * <pre><code>
   *   df.sort($"col1", $"col2".desc)
   * </code></pre>
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame sort (org.apache.spark.sql.Column... sortExprs) { throw new RuntimeException(); }
  /**
   * Returns a new {@link DataFrame} sorted by the given expressions.
   * This is an alias of the <code>sort</code> function.
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame orderBy (java.lang.String sortCol, java.lang.String... sortCols) { throw new RuntimeException(); }
  /**
   * Returns a new {@link DataFrame} sorted by the given expressions.
   * This is an alias of the <code>sort</code> function.
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame orderBy (org.apache.spark.sql.Column... sortExprs) { throw new RuntimeException(); }
  /**
   * Selects a set of expressions.
   * <pre><code>
   *   df.select($"colA", $"colB" + 1)
   * </code></pre>
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame select (org.apache.spark.sql.Column... cols) { throw new RuntimeException(); }
  /**
   * Selects a set of columns. This is a variant of <code>select</code> that can only select
   * existing columns using column names (i.e. cannot construct expressions).
   * <p>
   * <pre><code>
   *   // The following two are equivalent:
   *   df.select("colA", "colB")
   *   df.select($"colA", $"colB")
   * </code></pre>
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame select (java.lang.String col, java.lang.String... cols) { throw new RuntimeException(); }
  /**
   * Selects a set of SQL expressions. This is a variant of <code>select</code> that accepts
   * SQL expressions.
   * <p>
   * <pre><code>
   *   df.selectExpr("colA", "colB as newName", "abs(colC)")
   * </code></pre>
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame selectExpr (java.lang.String... exprs) { throw new RuntimeException(); }
  /**
   * Groups the {@link DataFrame} using the specified columns, so we can run aggregation on them.
   * See {@link GroupedData} for all the available aggregate functions.
   * <p>
   * <pre><code>
   *   // Compute the average for all numeric columns grouped by department.
   *   df.groupBy($"department").avg()
   *
   *   // Compute the max age and average salary, grouped by department and gender.
   *   df.groupBy($"department", $"gender").agg(Map(
   *     "salary" -&gt; "avg",
   *     "age" -&gt; "max"
   *   ))
   * </code></pre>
   * @group dfops
   */
  public  org.apache.spark.sql.GroupedData groupBy (org.apache.spark.sql.Column... cols) { throw new RuntimeException(); }
  /**
   * Groups the {@link DataFrame} using the specified columns, so we can run aggregation on them.
   * See {@link GroupedData} for all the available aggregate functions.
   * <p>
   * This is a variant of groupBy that can only group by existing columns using column names
   * (i.e. cannot construct expressions).
   * <p>
   * <pre><code>
   *   // Compute the average for all numeric columns grouped by department.
   *   df.groupBy("department").avg()
   *
   *   // Compute the max age and average salary, grouped by department and gender.
   *   df.groupBy($"department", $"gender").agg(Map(
   *     "salary" -&gt; "avg",
   *     "age" -&gt; "max"
   *   ))
   * </code></pre>
   * @group dfops
   */
  public  org.apache.spark.sql.GroupedData groupBy (java.lang.String col1, java.lang.String... cols) { throw new RuntimeException(); }
  /**
   * Aggregates on the entire {@link DataFrame} without groups.
   * {{
   *   // df.agg(...) is a shorthand for df.groupBy().agg(...)
   *   df.agg(max($"age"), avg($"salary"))
   *   df.groupBy().agg(max($"age"), avg($"salary"))
   * }}
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame agg (org.apache.spark.sql.Column expr, org.apache.spark.sql.Column... exprs) { throw new RuntimeException(); }
  // not preceding
  public  org.apache.spark.sql.SQLContext sqlContext () { throw new RuntimeException(); }
  public  org.apache.spark.sql.SQLContext.QueryExecution queryExecution () { throw new RuntimeException(); }
  // not preceding
  public   DataFrame (org.apache.spark.sql.SQLContext sqlContext, org.apache.spark.sql.SQLContext.QueryExecution queryExecution) { throw new RuntimeException(); }
  /**
   * A constructor that automatically analyzes the logical plan.
   * <p>
   * This reports error eagerly as the {@link DataFrame} is constructed, unless
   * {@link SQLConf.dataFrameEagerAnalysis} is turned off.
   */
  public   DataFrame (org.apache.spark.sql.SQLContext sqlContext, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan logicalPlan) { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan logicalPlan () { throw new RuntimeException(); }
  /**
   * An implicit conversion function internal to this class for us to avoid doing
   * "new DataFrame(...)" everywhere.
   */
  private  org.apache.spark.sql.DataFrame logicalPlanToDataFrame (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan logicalPlan) { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.expressions.NamedExpression resolve (java.lang.String colName) { throw new RuntimeException(); }
  protected  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> numericColumns () { throw new RuntimeException(); }
  /**
   * Internal API for Python
   * @param numRows Number of rows to show
   */
  public  java.lang.String showString (int numRows) { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
  /** Left here for backward compatibility. */
  public  org.apache.spark.sql.DataFrame toSchemaRDD () { throw new RuntimeException(); }
  /**
   * Returns the object itself.
   * @group basic
   */
  public  org.apache.spark.sql.DataFrame toDF () { throw new RuntimeException(); }
  /**
   * Returns a new {@link DataFrame} with columns renamed. This can be quite convenient in conversion
   * from a RDD of tuples into a {@link DataFrame} with meaningful names. For example:
   * <pre><code>
   *   val rdd: RDD[(Int, String)] = ...
   *   rdd.toDF()  // this implicit conversion creates a DataFrame with column name _1 and _2
   *   rdd.toDF("id", "name")  // this creates a DataFrame with column name "id" and "name"
   * </code></pre>
   * @group basic
   */
  public  org.apache.spark.sql.DataFrame toDF (scala.collection.Seq<java.lang.String> colNames) { throw new RuntimeException(); }
  /**
   * Returns the schema of this {@link DataFrame}.
   * @group basic
   */
  public  org.apache.spark.sql.types.StructType schema () { throw new RuntimeException(); }
  /**
   * Returns all column names and their data types as an array.
   * @group basic
   */
  public  scala.Tuple2<java.lang.String, java.lang.String>[] dtypes () { throw new RuntimeException(); }
  /**
   * Returns all column names as an array.
   * @group basic
   */
  public  java.lang.String[] columns () { throw new RuntimeException(); }
  /**
   * Prints the schema to the console in a nice tree format.
   * @group basic
   */
  public  void printSchema () { throw new RuntimeException(); }
  /**
   * Prints the plans (logical and physical) to the console for debugging purpose.
   * @group basic
   */
  public  void explain (boolean extended) { throw new RuntimeException(); }
  /**
   * Only prints the physical plan to the console for debugging purpose.
   * @group basic
   */
  public  void explain () { throw new RuntimeException(); }
  /**
   * Returns true if the <code>collect</code> and <code>take</code> methods can be run locally
   * (without any Spark executors).
   * @group basic
   */
  public  boolean isLocal () { throw new RuntimeException(); }
  /**
   * Displays the {@link DataFrame} in a tabular form. For example:
   * <pre><code>
   *   year  month AVG('Adj Close) MAX('Adj Close)
   *   1980  12    0.503218        0.595103
   *   1981  01    0.523289        0.570307
   *   1982  02    0.436504        0.475256
   *   1983  03    0.410516        0.442194
   *   1984  04    0.450090        0.483521
   * </code></pre>
   * @param numRows Number of rows to show
   * <p>
   * @group action
   */
  public  void show (int numRows) { throw new RuntimeException(); }
  /**
   * Displays the top 20 rows of {@link DataFrame} in a tabular form.
   * @group action
   */
  public  void show () { throw new RuntimeException(); }
  /**
   * Cartesian join with another {@link DataFrame}.
   * <p>
   * Note that cartesian joins are very expensive without an extra filter that can be pushed down.
   * <p>
   * @param right Right side of the join operation.
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame join (org.apache.spark.sql.DataFrame right) { throw new RuntimeException(); }
  /**
   * Inner join with another {@link DataFrame}, using the given join expression.
   * <p>
   * <pre><code>
   *   // The following two are equivalent:
   *   df1.join(df2, $"df1Key" === $"df2Key")
   *   df1.join(df2).where($"df1Key" === $"df2Key")
   * </code></pre>
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame join (org.apache.spark.sql.DataFrame right, org.apache.spark.sql.Column joinExprs) { throw new RuntimeException(); }
  /**
   * Join with another {@link DataFrame}, using the given join expression. The following performs
   * a full outer join between <code>df1</code> and <code>df2</code>.
   * <p>
   * <pre><code>
   *   // Scala:
   *   import org.apache.spark.sql.functions._
   *   df1.join(df2, $"df1Key" === $"df2Key", "outer")
   *
   *   // Java:
   *   import static org.apache.spark.sql.functions.*;
   *   df1.join(df2, col("df1Key").equalTo(col("df2Key")), "outer");
   * </code></pre>
   * <p>
   * @param right Right side of the join.
   * @param joinExprs Join expression.
   * @param joinType One of: <code>inner</code>, <code>outer</code>, <code>left_outer</code>, <code>right_outer</code>, <code>semijoin</code>.
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame join (org.apache.spark.sql.DataFrame right, org.apache.spark.sql.Column joinExprs, java.lang.String joinType) { throw new RuntimeException(); }
  /**
   * Returns a new {@link DataFrame} sorted by the specified column, all in ascending order.
   * <pre><code>
   *   // The following 3 are equivalent
   *   df.sort("sortcol")
   *   df.sort($"sortcol")
   *   df.sort($"sortcol".asc)
   * </code></pre>
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame sort (java.lang.String sortCol, scala.collection.Seq<java.lang.String> sortCols) { throw new RuntimeException(); }
  /**
   * Returns a new {@link DataFrame} sorted by the given expressions. For example:
   * <pre><code>
   *   df.sort($"col1", $"col2".desc)
   * </code></pre>
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame sort (scala.collection.Seq<org.apache.spark.sql.Column> sortExprs) { throw new RuntimeException(); }
  /**
   * Returns a new {@link DataFrame} sorted by the given expressions.
   * This is an alias of the <code>sort</code> function.
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame orderBy (java.lang.String sortCol, scala.collection.Seq<java.lang.String> sortCols) { throw new RuntimeException(); }
  /**
   * Returns a new {@link DataFrame} sorted by the given expressions.
   * This is an alias of the <code>sort</code> function.
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame orderBy (scala.collection.Seq<org.apache.spark.sql.Column> sortExprs) { throw new RuntimeException(); }
  /**
   * Selects column based on the column name and return it as a {@link Column}.
   * @group dfops
   */
  public  org.apache.spark.sql.Column apply (java.lang.String colName) { throw new RuntimeException(); }
  /**
   * Selects column based on the column name and return it as a {@link Column}.
   * @group dfops
   */
  public  org.apache.spark.sql.Column col (java.lang.String colName) { throw new RuntimeException(); }
  /**
   * Returns a new {@link DataFrame} with an alias set.
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame as (java.lang.String alias) { throw new RuntimeException(); }
  /**
   * (Scala-specific) Returns a new {@link DataFrame} with an alias set.
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame as (scala.Symbol alias) { throw new RuntimeException(); }
  /**
   * Selects a set of expressions.
   * <pre><code>
   *   df.select($"colA", $"colB" + 1)
   * </code></pre>
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame select (scala.collection.Seq<org.apache.spark.sql.Column> cols) { throw new RuntimeException(); }
  /**
   * Selects a set of columns. This is a variant of <code>select</code> that can only select
   * existing columns using column names (i.e. cannot construct expressions).
   * <p>
   * <pre><code>
   *   // The following two are equivalent:
   *   df.select("colA", "colB")
   *   df.select($"colA", $"colB")
   * </code></pre>
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame select (java.lang.String col, scala.collection.Seq<java.lang.String> cols) { throw new RuntimeException(); }
  /**
   * Selects a set of SQL expressions. This is a variant of <code>select</code> that accepts
   * SQL expressions.
   * <p>
   * <pre><code>
   *   df.selectExpr("colA", "colB as newName", "abs(colC)")
   * </code></pre>
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame selectExpr (scala.collection.Seq<java.lang.String> exprs) { throw new RuntimeException(); }
  /**
   * Filters rows using the given condition.
   * <pre><code>
   *   // The following are equivalent:
   *   peopleDf.filter($"age" &gt; 15)
   *   peopleDf.where($"age" &gt; 15)
   *   peopleDf($"age" &gt; 15)
   * </code></pre>
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame filter (org.apache.spark.sql.Column condition) { throw new RuntimeException(); }
  /**
   * Filters rows using the given SQL expression.
   * <pre><code>
   *   peopleDf.filter("age &gt; 15")
   * </code></pre>
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame filter (java.lang.String conditionExpr) { throw new RuntimeException(); }
  /**
   * Filters rows using the given condition. This is an alias for <code>filter</code>.
   * <pre><code>
   *   // The following are equivalent:
   *   peopleDf.filter($"age" &gt; 15)
   *   peopleDf.where($"age" &gt; 15)
   *   peopleDf($"age" &gt; 15)
   * </code></pre>
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame where (org.apache.spark.sql.Column condition) { throw new RuntimeException(); }
  /**
   * Groups the {@link DataFrame} using the specified columns, so we can run aggregation on them.
   * See {@link GroupedData} for all the available aggregate functions.
   * <p>
   * <pre><code>
   *   // Compute the average for all numeric columns grouped by department.
   *   df.groupBy($"department").avg()
   *
   *   // Compute the max age and average salary, grouped by department and gender.
   *   df.groupBy($"department", $"gender").agg(Map(
   *     "salary" -&gt; "avg",
   *     "age" -&gt; "max"
   *   ))
   * </code></pre>
   * @group dfops
   */
  public  org.apache.spark.sql.GroupedData groupBy (scala.collection.Seq<org.apache.spark.sql.Column> cols) { throw new RuntimeException(); }
  /**
   * Groups the {@link DataFrame} using the specified columns, so we can run aggregation on them.
   * See {@link GroupedData} for all the available aggregate functions.
   * <p>
   * This is a variant of groupBy that can only group by existing columns using column names
   * (i.e. cannot construct expressions).
   * <p>
   * <pre><code>
   *   // Compute the average for all numeric columns grouped by department.
   *   df.groupBy("department").avg()
   *
   *   // Compute the max age and average salary, grouped by department and gender.
   *   df.groupBy($"department", $"gender").agg(Map(
   *     "salary" -&gt; "avg",
   *     "age" -&gt; "max"
   *   ))
   * </code></pre>
   * @group dfops
   */
  public  org.apache.spark.sql.GroupedData groupBy (java.lang.String col1, scala.collection.Seq<java.lang.String> cols) { throw new RuntimeException(); }
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
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame agg (scala.Tuple2<java.lang.String, java.lang.String> aggExpr, scala.collection.Seq<scala.Tuple2<java.lang.String, java.lang.String>> aggExprs) { throw new RuntimeException(); }
  /**
   * (Scala-specific) Aggregates on the entire {@link DataFrame} without groups.
   * {{
   *   // df.agg(...) is a shorthand for df.groupBy().agg(...)
   *   df.agg(Map("age" -> "max", "salary" -> "avg"))
   *   df.groupBy().agg(Map("age" -> "max", "salary" -> "avg"))
   * }}
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame agg (scala.collection.immutable.Map<java.lang.String, java.lang.String> exprs) { throw new RuntimeException(); }
  /**
   * (Java-specific) Aggregates on the entire {@link DataFrame} without groups.
   * {{
   *   // df.agg(...) is a shorthand for df.groupBy().agg(...)
   *   df.agg(Map("age" -> "max", "salary" -> "avg"))
   *   df.groupBy().agg(Map("age" -> "max", "salary" -> "avg"))
   * }}
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame agg (java.util.Map<java.lang.String, java.lang.String> exprs) { throw new RuntimeException(); }
  /**
   * Aggregates on the entire {@link DataFrame} without groups.
   * {{
   *   // df.agg(...) is a shorthand for df.groupBy().agg(...)
   *   df.agg(max($"age"), avg($"salary"))
   *   df.groupBy().agg(max($"age"), avg($"salary"))
   * }}
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame agg (org.apache.spark.sql.Column expr, scala.collection.Seq<org.apache.spark.sql.Column> exprs) { throw new RuntimeException(); }
  /**
   * Returns a new {@link DataFrame} by taking the first <code>n</code> rows. The difference between this function
   * and <code>head</code> is that <code>head</code> returns an array while <code>limit</code> returns a new {@link DataFrame}.
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame limit (int n) { throw new RuntimeException(); }
  /**
   * Returns a new {@link DataFrame} containing union of rows in this frame and another frame.
   * This is equivalent to <code>UNION ALL</code> in SQL.
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame unionAll (org.apache.spark.sql.DataFrame other) { throw new RuntimeException(); }
  /**
   * Returns a new {@link DataFrame} containing rows only in both this frame and another frame.
   * This is equivalent to <code>INTERSECT</code> in SQL.
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame intersect (org.apache.spark.sql.DataFrame other) { throw new RuntimeException(); }
  /**
   * Returns a new {@link DataFrame} containing rows in this frame but not in another frame.
   * This is equivalent to <code>EXCEPT</code> in SQL.
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame except (org.apache.spark.sql.DataFrame other) { throw new RuntimeException(); }
  /**
   * Returns a new {@link DataFrame} by sampling a fraction of rows.
   * <p>
   * @param withReplacement Sample with replacement or not.
   * @param fraction Fraction of rows to generate.
   * @param seed Seed for sampling.
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame sample (boolean withReplacement, double fraction, long seed) { throw new RuntimeException(); }
  /**
   * Returns a new {@link DataFrame} by sampling a fraction of rows, using a random seed.
   * <p>
   * @param withReplacement Sample with replacement or not.
   * @param fraction Fraction of rows to generate.
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame sample (boolean withReplacement, double fraction) { throw new RuntimeException(); }
  /**
   * (Scala-specific) Returns a new {@link DataFrame} where each row has been expanded to zero or more
   * rows by the provided function.  This is similar to a <code>LATERAL VIEW</code> in HiveQL. The columns of
   * the input row are implicitly joined with each row that is output by the function.
   * <p>
   * The following example uses this function to count the number of books which contain
   * a given word:
   * <p>
   * <pre><code>
   *   case class Book(title: String, words: String)
   *   val df: RDD[Book]
   *
   *   case class Word(word: String)
   *   val allWords = df.explode('words) {
   *     case Row(words: String) =&gt; words.split(" ").map(Word(_))
   *   }
   *
   *   val bookCountPerWord = allWords.groupBy("word").agg(countDistinct("title"))
   * </code></pre>
   * @group dfops
   */
  public <A extends scala.Product> org.apache.spark.sql.DataFrame explode (scala.collection.Seq<org.apache.spark.sql.Column> input, scala.Function1<org.apache.spark.sql.Row, scala.collection.TraversableOnce<A>> f, scala.reflect.api.TypeTags.TypeTag<A> evidence$1) { throw new RuntimeException(); }
  /**
   * (Scala-specific) Returns a new {@link DataFrame} where a single column has been expanded to zero
   * or more rows by the provided function.  This is similar to a <code>LATERAL VIEW</code> in HiveQL. All
   * columns of the input row are implicitly joined with each value that is output by the function.
   * <p>
   * <pre><code>
   *   df.explode("words", "word")(words: String =&gt; words.split(" "))
   * </code></pre>
   * @group dfops
   */
  public <A extends java.lang.Object, B extends java.lang.Object> org.apache.spark.sql.DataFrame explode (java.lang.String inputColumn, java.lang.String outputColumn, scala.Function1<A, scala.collection.TraversableOnce<B>> f, scala.reflect.api.TypeTags.TypeTag<B> evidence$2) { throw new RuntimeException(); }
  /**
   * Returns a new {@link DataFrame} by adding a column.
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame withColumn (java.lang.String colName, org.apache.spark.sql.Column col) { throw new RuntimeException(); }
  /**
   * Returns a new {@link DataFrame} with a column renamed.
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame withColumnRenamed (java.lang.String existingName, java.lang.String newName) { throw new RuntimeException(); }
  /**
   * Returns the first <code>n</code> rows.
   * @group action
   */
  public  org.apache.spark.sql.Row[] head (int n) { throw new RuntimeException(); }
  /**
   * Returns the first row.
   * @group action
   */
  public  org.apache.spark.sql.Row head () { throw new RuntimeException(); }
  /**
   * Returns the first row. Alias for head().
   * @group action
   */
  public  org.apache.spark.sql.Row first () { throw new RuntimeException(); }
  /**
   * Returns a new RDD by applying a function to all rows of this DataFrame.
   * @group rdd
   */
  public <R extends java.lang.Object> org.apache.spark.rdd.RDD<R> map (scala.Function1<org.apache.spark.sql.Row, R> f, scala.reflect.ClassTag<R> evidence$3) { throw new RuntimeException(); }
  /**
   * Returns a new RDD by first applying a function to all rows of this {@link DataFrame},
   * and then flattening the results.
   * @group rdd
   */
  public <R extends java.lang.Object> org.apache.spark.rdd.RDD<R> flatMap (scala.Function1<org.apache.spark.sql.Row, scala.collection.TraversableOnce<R>> f, scala.reflect.ClassTag<R> evidence$4) { throw new RuntimeException(); }
  /**
   * Returns a new RDD by applying a function to each partition of this DataFrame.
   * @group rdd
   */
  public <R extends java.lang.Object> org.apache.spark.rdd.RDD<R> mapPartitions (scala.Function1<scala.collection.Iterator<org.apache.spark.sql.Row>, scala.collection.Iterator<R>> f, scala.reflect.ClassTag<R> evidence$5) { throw new RuntimeException(); }
  /**
   * Applies a function <code>f</code> to all rows.
   * @group rdd
   */
  public  void foreach (scala.Function1<org.apache.spark.sql.Row, scala.runtime.BoxedUnit> f) { throw new RuntimeException(); }
  /**
   * Applies a function f to each partition of this {@link DataFrame}.
   * @group rdd
   */
  public  void foreachPartition (scala.Function1<scala.collection.Iterator<org.apache.spark.sql.Row>, scala.runtime.BoxedUnit> f) { throw new RuntimeException(); }
  /**
   * Returns the first <code>n</code> rows in the {@link DataFrame}.
   * @group action
   */
  public  org.apache.spark.sql.Row[] take (int n) { throw new RuntimeException(); }
  /**
   * Returns an array that contains all of {@link Row}s in this {@link DataFrame}.
   * @group action
   */
  public  org.apache.spark.sql.Row[] collect () { throw new RuntimeException(); }
  /**
   * Returns a Java list that contains all of {@link Row}s in this {@link DataFrame}.
   * @group action
   */
  public  java.util.List<org.apache.spark.sql.Row> collectAsList () { throw new RuntimeException(); }
  /**
   * Returns the number of rows in the {@link DataFrame}.
   * @group action
   */
  public  long count () { throw new RuntimeException(); }
  /**
   * Returns a new {@link DataFrame} that has exactly <code>numPartitions</code> partitions.
   * @group rdd
   */
  public  org.apache.spark.sql.DataFrame repartition (int numPartitions) { throw new RuntimeException(); }
  /**
   * Returns a new {@link DataFrame} that contains only the unique rows from this {@link DataFrame}.
   * @group dfops
   */
  public  org.apache.spark.sql.DataFrame distinct () { throw new RuntimeException(); }
  /**
   * @group basic
   */
  public  org.apache.spark.sql.DataFrame persist () { throw new RuntimeException(); }
  /**
   * @group basic
   */
  public  org.apache.spark.sql.DataFrame cache () { throw new RuntimeException(); }
  /**
   * @group basic
   */
  public  org.apache.spark.sql.DataFrame persist (org.apache.spark.storage.StorageLevel newLevel) { throw new RuntimeException(); }
  /**
   * @group basic
   */
  public  org.apache.spark.sql.DataFrame unpersist (boolean blocking) { throw new RuntimeException(); }
  /**
   * @group basic
   */
  public  org.apache.spark.sql.DataFrame unpersist () { throw new RuntimeException(); }
  /**
   * Returns the content of the {@link DataFrame} as an {@link RDD} of {@link Row}s.
   * @group rdd
   */
  public  org.apache.spark.rdd.RDD<org.apache.spark.sql.Row> rdd () { throw new RuntimeException(); }
  /**
   * Returns the content of the {@link DataFrame} as a {@link JavaRDD} of {@link Row}s.
   * @group rdd
   */
  public  org.apache.spark.api.java.JavaRDD<org.apache.spark.sql.Row> toJavaRDD () { throw new RuntimeException(); }
  /**
   * Returns the content of the {@link DataFrame} as a {@link JavaRDD} of {@link Row}s.
   * @group rdd
   */
  public  org.apache.spark.api.java.JavaRDD<org.apache.spark.sql.Row> javaRDD () { throw new RuntimeException(); }
  /**
   * Registers this RDD as a temporary table using the given name.  The lifetime of this temporary
   * table is tied to the {@link SQLContext} that was used to create this DataFrame.
   * <p>
   * @group basic
   */
  public  void registerTempTable (java.lang.String tableName) { throw new RuntimeException(); }
  /**
   * Saves the contents of this {@link DataFrame} as a parquet file, preserving the schema.
   * Files that are written out using this method can be read back in as a {@link DataFrame}
   * using the <code>parquetFile</code> function in {@link SQLContext}.
   * @group output
   */
  public  void saveAsParquetFile (java.lang.String path) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Creates a table from the the contents of this DataFrame.
   * It will use the default data source configured by spark.sql.sources.default.
   * This will fail if the table already exists.
   * <p>
   * Note that this currently only works with DataFrames that are created from a HiveContext as
   * there is no notion of a persisted catalog in a standard SQL context.  Instead you can write
   * an RDD out to a parquet file, and then register that file as a table.  This "table" can then
   * be the target of an <code>insertInto</code>.
   * @group output
   */
  public  void saveAsTable (java.lang.String tableName) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Creates a table from the the contents of this DataFrame, using the default data source
   * configured by spark.sql.sources.default and {@link SaveMode.ErrorIfExists} as the save mode.
   * <p>
   * Note that this currently only works with DataFrames that are created from a HiveContext as
   * there is no notion of a persisted catalog in a standard SQL context.  Instead you can write
   * an RDD out to a parquet file, and then register that file as a table.  This "table" can then
   * be the target of an <code>insertInto</code>.
   * @group output
   */
  public  void saveAsTable (java.lang.String tableName, org.apache.spark.sql.SaveMode mode) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Creates a table at the given path from the the contents of this DataFrame
   * based on a given data source and a set of options,
   * using {@link SaveMode.ErrorIfExists} as the save mode.
   * <p>
   * Note that this currently only works with DataFrames that are created from a HiveContext as
   * there is no notion of a persisted catalog in a standard SQL context.  Instead you can write
   * an RDD out to a parquet file, and then register that file as a table.  This "table" can then
   * be the target of an <code>insertInto</code>.
   * @group output
   */
  public  void saveAsTable (java.lang.String tableName, java.lang.String source) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Creates a table at the given path from the the contents of this DataFrame
   * based on a given data source, {@link SaveMode} specified by mode, and a set of options.
   * <p>
   * Note that this currently only works with DataFrames that are created from a HiveContext as
   * there is no notion of a persisted catalog in a standard SQL context.  Instead you can write
   * an RDD out to a parquet file, and then register that file as a table.  This "table" can then
   * be the target of an <code>insertInto</code>.
   * @group output
   */
  public  void saveAsTable (java.lang.String tableName, java.lang.String source, org.apache.spark.sql.SaveMode mode) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Creates a table at the given path from the the contents of this DataFrame
   * based on a given data source, {@link SaveMode} specified by mode, and a set of options.
   * <p>
   * Note that this currently only works with DataFrames that are created from a HiveContext as
   * there is no notion of a persisted catalog in a standard SQL context.  Instead you can write
   * an RDD out to a parquet file, and then register that file as a table.  This "table" can then
   * be the target of an <code>insertInto</code>.
   * @group output
   */
  public  void saveAsTable (java.lang.String tableName, java.lang.String source, org.apache.spark.sql.SaveMode mode, java.util.Map<java.lang.String, java.lang.String> options) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * (Scala-specific)
   * Creates a table from the the contents of this DataFrame based on a given data source,
   * {@link SaveMode} specified by mode, and a set of options.
   * <p>
   * Note that this currently only works with DataFrames that are created from a HiveContext as
   * there is no notion of a persisted catalog in a standard SQL context.  Instead you can write
   * an RDD out to a parquet file, and then register that file as a table.  This "table" can then
   * be the target of an <code>insertInto</code>.
   * @group output
   */
  public  void saveAsTable (java.lang.String tableName, java.lang.String source, org.apache.spark.sql.SaveMode mode, scala.collection.immutable.Map<java.lang.String, java.lang.String> options) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Saves the contents of this DataFrame to the given path,
   * using the default data source configured by spark.sql.sources.default and
   * {@link SaveMode.ErrorIfExists} as the save mode.
   * @group output
   */
  public  void save (java.lang.String path) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Saves the contents of this DataFrame to the given path and {@link SaveMode} specified by mode,
   * using the default data source configured by spark.sql.sources.default.
   * @group output
   */
  public  void save (java.lang.String path, org.apache.spark.sql.SaveMode mode) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Saves the contents of this DataFrame to the given path based on the given data source,
   * using {@link SaveMode.ErrorIfExists} as the save mode.
   * @group output
   */
  public  void save (java.lang.String path, java.lang.String source) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Saves the contents of this DataFrame to the given path based on the given data source and
   * {@link SaveMode} specified by mode.
   * @group output
   */
  public  void save (java.lang.String path, java.lang.String source, org.apache.spark.sql.SaveMode mode) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Saves the contents of this DataFrame based on the given data source,
   * {@link SaveMode} specified by mode, and a set of options.
   * @group output
   */
  public  void save (java.lang.String source, org.apache.spark.sql.SaveMode mode, java.util.Map<java.lang.String, java.lang.String> options) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * (Scala-specific)
   * Saves the contents of this DataFrame based on the given data source,
   * {@link SaveMode} specified by mode, and a set of options
   * @group output
   */
  public  void save (java.lang.String source, org.apache.spark.sql.SaveMode mode, scala.collection.immutable.Map<java.lang.String, java.lang.String> options) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Adds the rows from this RDD to the specified table, optionally overwriting the existing data.
   * @group output
   */
  public  void insertInto (java.lang.String tableName, boolean overwrite) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Adds the rows from this RDD to the specified table.
   * Throws an exception if the table already exists.
   * @group output
   */
  public  void insertInto (java.lang.String tableName) { throw new RuntimeException(); }
  /**
   * Returns the content of the {@link DataFrame} as a RDD of JSON strings.
   * @group rdd
   */
  public  org.apache.spark.rdd.RDD<java.lang.String> toJSON () { throw new RuntimeException(); }
  /**
   * Save this RDD to a JDBC database at <code>url</code> under the table name <code>table</code>.
   * This will run a <code>CREATE TABLE</code> and a bunch of <code>INSERT INTO</code> statements.
   * If you pass <code>true</code> for <code>allowExisting</code>, it will drop any table with the
   * given name; if you pass <code>false</code>, it will throw if the table already
   * exists.
   * @group output
   */
  public  void createJDBCTable (java.lang.String url, java.lang.String table, boolean allowExisting) { throw new RuntimeException(); }
  /**
   * Save this RDD to a JDBC database at <code>url</code> under the table name <code>table</code>.
   * Assumes the table already exists and has a compatible schema.  If you
   * pass <code>true</code> for <code>overwrite</code>, it will <code>TRUNCATE</code> the table before
   * performing the <code>INSERT</code>s.
   * <p>
   * The table must already exist on the database.  It must have a schema
   * that is compatible with the schema of this RDD; inserting the rows of
   * the RDD in order via the simple statement
   * <code>INSERT INTO table VALUES (?, ?, ..., ?)</code> should not fail.
   * @group output
   */
  public  void insertIntoJDBC (java.lang.String url, java.lang.String table, boolean overwrite) { throw new RuntimeException(); }
  /**
   * Converts a JavaRDD to a PythonRDD.
   */
  protected  org.apache.spark.api.java.JavaRDD<byte[]> javaToPython () { throw new RuntimeException(); }
}
