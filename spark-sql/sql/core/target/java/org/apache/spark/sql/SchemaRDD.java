package org.apache.spark.sql;
/**
 * :: AlphaComponent ::
 * An RDD of {@link Row} objects that has an associated schema. In addition to standard RDD functions,
 * SchemaRDDs can be used in relational queries, as shown in the examples below.
 * <p>
 * Importing a SQLContext brings an implicit into scope that automatically converts a standard RDD
 * whose elements are scala case classes into a SchemaRDD.  This conversion can also be done
 * explicitly using the <code>createSchemaRDD</code> function on a {@link SQLContext}.
 * <p>
 * A <code>SchemaRDD</code> can also be created by loading data in from external sources.
 * Examples are loading data from Parquet files by using by using the
 * <code>parquetFile</code> method on {@link SQLContext}, and loading JSON datasets
 * by using <code>jsonFile</code> and <code>jsonRDD</code> methods on {@link SQLContext}.
 * <p>
 * == SQL Queries ==
 * A SchemaRDD can be registered as a table in the {@link SQLContext} that was used to create it.  Once
 * an RDD has been registered as a table, it can be used in the FROM clause of SQL statements.
 * <p>
 * <pre><code>
 *  // One method for defining the schema of an RDD is to make a case class with the desired column
 *  // names and types.
 *  case class Record(key: Int, value: String)
 *
 *  val sc: SparkContext // An existing spark context.
 *  val sqlContext = new SQLContext(sc)
 *
 *  // Importing the SQL context gives access to all the SQL functions and implicit conversions.
 *  import sqlContext._
 *
 *  val rdd = sc.parallelize((1 to 100).map(i =&gt; Record(i, s"val_$i")))
 *  // Any RDD containing case classes can be registered as a table.  The schema of the table is
 *  // automatically inferred using scala reflection.
 *  rdd.registerTempTable("records")
 *
 *  val results: SchemaRDD = sql("SELECT * FROM records")
 * </code></pre>
 * <p>
 * == Language Integrated Queries ==
 * <p>
 * <pre><code>
 *
 *  case class Record(key: Int, value: String)
 *
 *  val sc: SparkContext // An existing spark context.
 *  val sqlContext = new SQLContext(sc)
 *
 *  // Importing the SQL context gives access to all the SQL functions and implicit conversions.
 *  import sqlContext._
 *
 *  val rdd = sc.parallelize((1 to 100).map(i =&gt; Record(i, "val_" + i)))
 *
 *  // Example of language integrated queries.
 *  rdd.where('key === 1).orderBy('value.asc).select('key).collect()
 * </code></pre>
 * <p>
 *  @groupname Query Language Integrated Queries
 *  @groupdesc Query Functions that create new queries from SchemaRDDs.  The
 *             result of all query functions is also a SchemaRDD, allowing multiple operations to be
 *             chained using a builder pattern.
 *  @groupprio Query -2
 *  @groupname schema SchemaRDD Functions
 *  @groupprio schema -1
 *  @groupname Ungrouped Base RDD Functions
 */
public  class SchemaRDD extends org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> implements org.apache.spark.sql.SchemaRDDLike {
  public  org.apache.spark.sql.SQLContext sqlContext () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan baseLogicalPlan () { throw new RuntimeException(); }
  // not preceding
  public   SchemaRDD (org.apache.spark.sql.SQLContext sqlContext, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan baseLogicalPlan) { throw new RuntimeException(); }
  public  org.apache.spark.sql.SchemaRDD baseSchemaRDD () { throw new RuntimeException(); }
  public  scala.collection.Iterator<org.apache.spark.sql.catalyst.expressions.Row> compute (org.apache.spark.Partition split, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
  public  org.apache.spark.Partition[] getPartitions () { throw new RuntimeException(); }
  protected  scala.collection.Seq<org.apache.spark.Dependency<?>> getDependencies () { throw new RuntimeException(); }
  /** Returns the schema of this SchemaRDD (represented by a {@link StructType}).
   * <p>
   * @group schema
   */
  public  org.apache.spark.sql.catalyst.types.StructType schema () { throw new RuntimeException(); }
  /**
   * Changes the output of this relation to the given expressions, similar to the <code>SELECT</code> clause
   * in SQL.
   * <p>
   * <pre><code>
   *   schemaRDD.select('a, 'b + 'c, 'd as 'aliasedName)
   * </code></pre>
   * <p>
   * @param exprs a set of logical expression that will be evaluated for each input row.
   * <p>
   * @group Query
   */
  public  org.apache.spark.sql.SchemaRDD select (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> exprs) { throw new RuntimeException(); }
  /**
   * Filters the output, only returning those rows where <code>condition</code> evaluates to true.
   * <p>
   * <pre><code>
   *   schemaRDD.where('a === 'b)
   *   schemaRDD.where('a === 1)
   *   schemaRDD.where('a + 'b &gt; 10)
   * </code></pre>
   * <p>
   * @group Query
   */
  public  org.apache.spark.sql.SchemaRDD where (org.apache.spark.sql.catalyst.expressions.Expression condition) { throw new RuntimeException(); }
  /**
   * Performs a relational join on two SchemaRDDs
   * <p>
   * @param otherPlan the {@link SchemaRDD} that should be joined with this one.
   * @param joinType One of <code>Inner</code>, <code>LeftOuter</code>, <code>RightOuter</code>, or <code>FullOuter</code>. Defaults to <code>Inner.</code>
   * @param on       An optional condition for the join operation.  This is equivalent to the <code>ON</code>
   *                 clause in standard SQL.  In the case of <code>Inner</code> joins, specifying a
   *                 <code>condition</code> is equivalent to adding <code>where</code> clauses after the <code>join</code>.
   * <p>
   * @group Query
   */
  public  org.apache.spark.sql.SchemaRDD join (org.apache.spark.sql.SchemaRDD otherPlan, org.apache.spark.sql.catalyst.plans.JoinType joinType, scala.Option<org.apache.spark.sql.catalyst.expressions.Expression> on) { throw new RuntimeException(); }
  /**
   * Sorts the results by the given expressions.
   * <pre><code>
   *   schemaRDD.orderBy('a)
   *   schemaRDD.orderBy('a, 'b)
   *   schemaRDD.orderBy('a.asc, 'b.desc)
   * </code></pre>
   * <p>
   * @group Query
   */
  public  org.apache.spark.sql.SchemaRDD orderBy (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.SortOrder> sortExprs) { throw new RuntimeException(); }
  public  org.apache.spark.sql.SchemaRDD limit (org.apache.spark.sql.catalyst.expressions.Expression limitExpr) { throw new RuntimeException(); }
  /**
   * Limits the results by the given integer.
   * <pre><code>
   *   schemaRDD.limit(10)
   * </code></pre>
   */
  public  org.apache.spark.sql.SchemaRDD limit (int limitNum) { throw new RuntimeException(); }
  /**
   * Performs a grouping followed by an aggregation.
   * <p>
   * <pre><code>
   *   schemaRDD.groupBy('year)(Sum('sales) as 'totalSales)
   * </code></pre>
   * <p>
   * @group Query
   */
  public  org.apache.spark.sql.SchemaRDD groupBy (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> groupingExprs, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> aggregateExprs) { throw new RuntimeException(); }
  /**
   * Performs an aggregation over all Rows in this RDD.
   * This is equivalent to a groupBy with no grouping expressions.
   * <p>
   * <pre><code>
   *   schemaRDD.aggregate(Sum('sales) as 'totalSales)
   * </code></pre>
   * <p>
   * @group Query
   */
  public  org.apache.spark.sql.SchemaRDD aggregate (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> aggregateExprs) { throw new RuntimeException(); }
  /**
   * Applies a qualifier to the attributes of this relation.  Can be used to disambiguate attributes
   * with the same name, for example, when performing self-joins.
   * <p>
   * <pre><code>
   *   val x = schemaRDD.where('a === 1).as('x)
   *   val y = schemaRDD.where('a === 2).as('y)
   *   x.join(y).where("x.a".attr === "y.a".attr),
   * </code></pre>
   * <p>
   * @group Query
   */
  public  org.apache.spark.sql.SchemaRDD as (scala.Symbol alias) { throw new RuntimeException(); }
  /**
   * Combines the tuples of two RDDs with the same schema, keeping duplicates.
   * <p>
   * @group Query
   */
  public  org.apache.spark.sql.SchemaRDD unionAll (org.apache.spark.sql.SchemaRDD otherPlan) { throw new RuntimeException(); }
  /**
   * Performs a relational except on two SchemaRDDs
   * <p>
   * @param otherPlan the {@link SchemaRDD} that should be excepted from this one.
   * <p>
   * @group Query
   */
  public  org.apache.spark.sql.SchemaRDD except (org.apache.spark.sql.SchemaRDD otherPlan) { throw new RuntimeException(); }
  /**
   * Performs a relational intersect on two SchemaRDDs
   * <p>
   * @param otherPlan the {@link SchemaRDD} that should be intersected with this one.
   * <p>
   * @group Query
   */
  public  org.apache.spark.sql.SchemaRDD intersect (org.apache.spark.sql.SchemaRDD otherPlan) { throw new RuntimeException(); }
  /**
   * Filters tuples using a function over the value of the specified column.
   * <p>
   * <pre><code>
   *   schemaRDD.sfilter('a)((a: Int) =&gt; ...)
   * </code></pre>
   * <p>
   * @group Query
   */
  public <T1 extends java.lang.Object> org.apache.spark.sql.SchemaRDD where (scala.Symbol arg1, scala.Function1<T1, java.lang.Object> udf) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Filters tuples using a function over a <code>Dynamic</code> version of a given Row.  DynamicRows use
   * scala's Dynamic trait to emulate an ORM of in a dynamically typed language.  Since the type of
   * the column is not known at compile time, all attributes are converted to strings before
   * being passed to the function.
   * <p>
   * <pre><code>
   *   schemaRDD.where(r =&gt; r.firstName == "Bob" && r.lastName == "Smith")
   * </code></pre>
   * <p>
   * @group Query
   */
  public  org.apache.spark.sql.SchemaRDD where (scala.Function1<org.apache.spark.sql.catalyst.expressions.DynamicRow, java.lang.Object> dynamicUdf) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Returns a sampled version of the underlying dataset.
   * <p>
   * @group Query
   */
  public  org.apache.spark.sql.SchemaRDD sample (boolean withReplacement, double fraction, long seed) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Return the number of elements in the RDD. Unlike the base RDD implementation of count, this
   * implementation leverages the query optimizer to compute the count on the SchemaRDD, which
   * supports features such as filter pushdown.
   */
  public  long count () { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Applies the given Generator, or table generating function, to this relation.
   * <p>
   * @param generator A table generating function.  The API for such functions is likely to change
   *                  in future releases
   * @param join when set to true, each output row of the generator is joined with the input row
   *             that produced it.
   * @param outer when set to true, at least one row will be produced for each input row, similar to
   *              an <code>OUTER JOIN</code> in SQL.  When no output rows are produced by the generator for a
   *              given row, a single row will be output, with <code>NULL</code> values for each of the
   *              generated columns.
   * @param alias an optional alias that can be used as qualifier for the attributes that are
   *              produced by this generate operation.
   * <p>
   * @group Query
   */
  public  org.apache.spark.sql.SchemaRDD generate (org.apache.spark.sql.catalyst.expressions.Generator generator, boolean join, boolean outer, scala.Option<java.lang.String> alias) { throw new RuntimeException(); }
  /**
   * Returns this RDD as a SchemaRDD.  Intended primarily to force the invocation of the implicit
   * conversion from a standard RDD to a SchemaRDD.
   * <p>
   * @group schema
   */
  public  org.apache.spark.sql.SchemaRDD toSchemaRDD () { throw new RuntimeException(); }
  /**
   * Returns this RDD as a JavaSchemaRDD.
   * <p>
   * @group schema
   */
  public  org.apache.spark.sql.api.java.JavaSchemaRDD toJavaSchemaRDD () { throw new RuntimeException(); }
  /**
   * Converts a JavaRDD to a PythonRDD. It is used by pyspark.
   */
  private  org.apache.spark.api.java.JavaRDD<byte[]> javaToPython () { throw new RuntimeException(); }
  /**
   * Creates SchemaRDD by applying own schema to derived RDD. Typically used to wrap return value
   * of base RDD functions that do not change schema.
   * <p>
   * @param rdd RDD derived from this one and has same schema
   * <p>
   * @group schema
   */
  private  org.apache.spark.sql.SchemaRDD applySchema (org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> rdd) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Row[] collect () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Row[] take (int num) { throw new RuntimeException(); }
  public  org.apache.spark.sql.SchemaRDD coalesce (int numPartitions, boolean shuffle, scala.math.Ordering<org.apache.spark.sql.catalyst.expressions.Row> ord) { throw new RuntimeException(); }
  public  org.apache.spark.sql.SchemaRDD distinct () { throw new RuntimeException(); }
  public  org.apache.spark.sql.SchemaRDD distinct (int numPartitions, scala.math.Ordering<org.apache.spark.sql.catalyst.expressions.Row> ord) { throw new RuntimeException(); }
  public  org.apache.spark.sql.SchemaRDD filter (scala.Function1<org.apache.spark.sql.catalyst.expressions.Row, java.lang.Object> f) { throw new RuntimeException(); }
  public  org.apache.spark.sql.SchemaRDD intersection (org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> other) { throw new RuntimeException(); }
  public  org.apache.spark.sql.SchemaRDD intersection (org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> other, org.apache.spark.Partitioner partitioner, scala.math.Ordering<org.apache.spark.sql.catalyst.expressions.Row> ord) { throw new RuntimeException(); }
  public  org.apache.spark.sql.SchemaRDD intersection (org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> other, int numPartitions) { throw new RuntimeException(); }
  public  org.apache.spark.sql.SchemaRDD repartition (int numPartitions, scala.math.Ordering<org.apache.spark.sql.catalyst.expressions.Row> ord) { throw new RuntimeException(); }
  public  org.apache.spark.sql.SchemaRDD subtract (org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> other) { throw new RuntimeException(); }
  public  org.apache.spark.sql.SchemaRDD subtract (org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> other, int numPartitions) { throw new RuntimeException(); }
  public  org.apache.spark.sql.SchemaRDD subtract (org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> other, org.apache.spark.Partitioner p, scala.math.Ordering<org.apache.spark.sql.catalyst.expressions.Row> ord) { throw new RuntimeException(); }
}
