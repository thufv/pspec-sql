package org.apache.spark.sql;
/**
 * :: AlphaComponent ::
 * The entry point for running relational queries using Spark.  Allows the creation of {@link SchemaRDD}
 * objects and the execution of SQL queries.
 * <p>
 * @groupname userf Spark SQL Functions
 * @groupname Ungrouped Support functions for language integrated queries.
 */
public  class SQLContext implements org.apache.spark.Logging, org.apache.spark.sql.SQLConf, org.apache.spark.sql.catalyst.dsl.ExpressionConversions, org.apache.spark.sql.UDFRegistration, scala.Serializable {
  public  org.apache.spark.SparkContext sparkContext () { throw new RuntimeException(); }
  // not preceding
  public   SQLContext (org.apache.spark.SparkContext sparkContext) { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.analysis.Catalog catalog () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.analysis.FunctionRegistry functionRegistry () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.analysis.Analyzer analyzer () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.optimizer.Optimizer$ optimizer () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser parser () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan parseSql (java.lang.String sql) { throw new RuntimeException(); }
  protected  org.apache.spark.sql.SQLContext.QueryExecution executeSql (java.lang.String sql) { throw new RuntimeException(); }
  protected  org.apache.spark.sql.SQLContext.QueryExecution executePlan (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  /**
   * :: DeveloperApi ::
   * Allows catalyst LogicalPlans to be executed as a SchemaRDD.  Note that the LogicalPlan
   * interface is considered internal, and thus not guaranteed to be stable.  As a result, using
   * them directly is not recommended.
   */
  public  org.apache.spark.sql.SchemaRDD logicalPlanToSparkQuery (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  /**
   * Creates a SchemaRDD from an RDD of case classes.
   * <p>
   * @group userf
   */
  public <A extends scala.Product> org.apache.spark.sql.SchemaRDD createSchemaRDD (org.apache.spark.rdd.RDD<A> rdd, scala.reflect.api.TypeTags.TypeTag<A> evidence$1) { throw new RuntimeException(); }
  /**
   * :: DeveloperApi ::
   * Creates a {@link SchemaRDD} from an {@link RDD} containing {@link Row}s by applying a schema to this RDD.
   * It is important to make sure that the structure of every {@link Row} of the provided RDD matches
   * the provided schema. Otherwise, there will be runtime exception.
   * Example:
   * <pre><code>
   *  import org.apache.spark.sql._
   *  val sqlContext = new org.apache.spark.sql.SQLContext(sc)
   *
   *  val schema =
   *    StructType(
   *      StructField("name", StringType, false) ::
   *      StructField("age", IntegerType, true) :: Nil)
   *
   *  val people =
   *    sc.textFile("examples/src/main/resources/people.txt").map(
   *      _.split(",")).map(p =&gt; Row(p(0), p(1).trim.toInt))
   *  val peopleSchemaRDD = sqlContext. applySchema(people, schema)
   *  peopleSchemaRDD.printSchema
   *  // root
   *  // |-- name: string (nullable = false)
   *  // |-- age: integer (nullable = true)
   *
   *    peopleSchemaRDD.registerTempTable("people")
   *  sqlContext.sql("select name from people").collect.foreach(println)
   * </code></pre>
   * <p>
   * @group userf
   */
  public  org.apache.spark.sql.SchemaRDD applySchema (org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> rowRDD, org.apache.spark.sql.catalyst.types.StructType schema) { throw new RuntimeException(); }
  /**
   * Loads a Parquet file, returning the result as a {@link SchemaRDD}.
   * <p>
   * @group userf
   */
  public  org.apache.spark.sql.SchemaRDD parquetFile (java.lang.String path) { throw new RuntimeException(); }
  /**
   * Loads a JSON file (one object per line), returning the result as a {@link SchemaRDD}.
   * It goes through the entire dataset once to determine the schema.
   * <p>
   * @group userf
   */
  public  org.apache.spark.sql.SchemaRDD jsonFile (java.lang.String path) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Loads a JSON file (one object per line) and applies the given schema,
   * returning the result as a {@link SchemaRDD}.
   * <p>
   * @group userf
   */
  public  org.apache.spark.sql.SchemaRDD jsonFile (java.lang.String path, org.apache.spark.sql.catalyst.types.StructType schema) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   */
  public  org.apache.spark.sql.SchemaRDD jsonFile (java.lang.String path, double samplingRatio) { throw new RuntimeException(); }
  /**
   * Loads an RDD[String] storing JSON objects (one object per record), returning the result as a
   * {@link SchemaRDD}.
   * It goes through the entire dataset once to determine the schema.
   * <p>
   * @group userf
   */
  public  org.apache.spark.sql.SchemaRDD jsonRDD (org.apache.spark.rdd.RDD<java.lang.String> json) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Loads an RDD[String] storing JSON objects (one object per record) and applies the given schema,
   * returning the result as a {@link SchemaRDD}.
   * <p>
   * @group userf
   */
  public  org.apache.spark.sql.SchemaRDD jsonRDD (org.apache.spark.rdd.RDD<java.lang.String> json, org.apache.spark.sql.catalyst.types.StructType schema) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   */
  public  org.apache.spark.sql.SchemaRDD jsonRDD (org.apache.spark.rdd.RDD<java.lang.String> json, double samplingRatio) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Creates an empty parquet file with the schema of class <code>A</code>, which can be registered as a table.
   * This registered table can be used as the target of future <code>insertInto</code> operations.
   * <p>
   * <pre><code>
   *   val sqlContext = new SQLContext(...)
   *   import sqlContext._
   *
   *   case class Person(name: String, age: Int)
   *   createParquetFile[Person]("path/to/file.parquet").registerTempTable("people")
   *   sql("INSERT INTO people SELECT 'michael', 29")
   * </code></pre>
   * <p>
   * @tparam A A case class type that describes the desired schema of the parquet file to be
   *           created.
   * @param path The path where the directory containing parquet metadata should be created.
   *             Data inserted into this table will also be stored at this location.
   * @param allowExisting When false, an exception will be thrown if this directory already exists.
   * @param conf A Hadoop configuration object that can be used to specify options to the parquet
   *             output format.
   * <p>
   * @group userf
   */
  public <A extends scala.Product> org.apache.spark.sql.SchemaRDD createParquetFile (java.lang.String path, boolean allowExisting, org.apache.hadoop.conf.Configuration conf, scala.reflect.api.TypeTags.TypeTag<A> evidence$2) { throw new RuntimeException(); }
  /**
   * Registers the given RDD as a temporary table in the catalog.  Temporary tables exist only
   * during the lifetime of this instance of SQLContext.
   * <p>
   * @group userf
   */
  public  void registerRDDAsTable (org.apache.spark.sql.SchemaRDD rdd, java.lang.String tableName) { throw new RuntimeException(); }
  /**
   * Executes a SQL query using Spark, returning the result as a SchemaRDD.  The dialect that is
   * used for SQL parsing can be configured with 'spark.sql.dialect'.
   * <p>
   * @group userf
   */
  public  org.apache.spark.sql.SchemaRDD sql (java.lang.String sqlText) { throw new RuntimeException(); }
  /** Returns the specified table as a SchemaRDD */
  public  org.apache.spark.sql.SchemaRDD table (java.lang.String tableName) { throw new RuntimeException(); }
  /** Caches the specified table in-memory. */
  public  void cacheTable (java.lang.String tableName) { throw new RuntimeException(); }
  /** Removes the specified table from the in-memory cache. */
  public  void uncacheTable (java.lang.String tableName) { throw new RuntimeException(); }
  /** Returns true if the table is currently cached in-memory. */
  public  boolean isCached (java.lang.String tableName) { throw new RuntimeException(); }
  protected  class SparkPlanner extends org.apache.spark.sql.execution.SparkStrategies {
    public   SparkPlanner () { throw new RuntimeException(); }
    public  org.apache.spark.SparkContext sparkContext () { throw new RuntimeException(); }
    public  org.apache.spark.sql.SQLContext sqlContext () { throw new RuntimeException(); }
    public  boolean codegenEnabled () { throw new RuntimeException(); }
    public  int numPartitions () { throw new RuntimeException(); }
    public  scala.collection.Seq<org.apache.spark.sql.catalyst.planning.QueryPlanner<org.apache.spark.sql.execution.SparkPlan>.Strategy> strategies () { throw new RuntimeException(); }
    /**
     * Used to build table scan operators where complex projection and filtering are done using
     * separate physical operators.  This function returns the given scan operator with Project and
     * Filter nodes added only when needed.  For example, a Project operator is only used when the
     * final desired output requires complex expressions to be evaluated or when columns can be
     * further eliminated out after filtering has been done.
     * <p>
     * The <code>prunePushedDownFilters</code> parameter is used to remove those filters that can be optimized
     * away by the filter pushdown optimization.
     * <p>
     * The required attributes for both filtering and expression evaluation are passed to the
     * provided <code>scanBuilder</code> function so that it can avoid unnecessary column materialization.
     */
    public  org.apache.spark.sql.execution.SparkPlan pruneFilterProject (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.NamedExpression> projectList, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> filterPredicates, scala.Function1<scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression>, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression>> prunePushedDownFilters, scala.Function1<scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute>, org.apache.spark.sql.execution.SparkPlan> scanBuilder) { throw new RuntimeException(); }
  }
  protected  org.apache.spark.sql.SQLContext.SparkPlanner planner () { throw new RuntimeException(); }
  protected  org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> emptyResult () { throw new RuntimeException(); }
  /**
   * Prepares a planned SparkPlan for execution by inserting shuffle operations as needed.
   */
  protected  org.apache.spark.sql.catalyst.rules.RuleExecutor<org.apache.spark.sql.execution.SparkPlan> prepareForExecution () { throw new RuntimeException(); }
  /**
   * :: DeveloperApi ::
   * The primary workflow for executing relational queries using Spark.  Designed to allow easy
   * access to the intermediate phases of query execution for developers.
   */
  protected abstract class QueryExecution {
    public   QueryExecution () { throw new RuntimeException(); }
    public abstract  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan logical () ;
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan analyzed () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan optimizedPlan () { throw new RuntimeException(); }
    public  org.apache.spark.sql.execution.SparkPlan sparkPlan () { throw new RuntimeException(); }
    public  org.apache.spark.sql.execution.SparkPlan executedPlan () { throw new RuntimeException(); }
    /** Internal version of the RDD. Avoids copies and has no schema */
    public  org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> toRdd () { throw new RuntimeException(); }
    protected <A extends java.lang.Object> java.lang.String stringOrError (scala.Function0<A> f) { throw new RuntimeException(); }
    public  java.lang.String simpleString () { throw new RuntimeException(); }
    public  java.lang.String toString () { throw new RuntimeException(); }
  }
  /**
   * Parses the data type in our internal string representation. The data type string should
   * have the same format as the one generated by <code>toString</code> in scala.
   * It is only used by PySpark.
   */
  private  org.apache.spark.sql.catalyst.types.DataType parseDataType (java.lang.String dataTypeString) { throw new RuntimeException(); }
  /**
   * Apply a schema defined by the schemaString to an RDD. It is only used by PySpark.
   */
  private  org.apache.spark.sql.SchemaRDD applySchemaToPythonRDD (org.apache.spark.rdd.RDD<java.lang.Object[]> rdd, java.lang.String schemaString) { throw new RuntimeException(); }
  /**
   * Apply a schema defined by the schema to an RDD. It is only used by PySpark.
   */
  private  org.apache.spark.sql.SchemaRDD applySchemaToPythonRDD (org.apache.spark.rdd.RDD<java.lang.Object[]> rdd, org.apache.spark.sql.catalyst.types.StructType schema) { throw new RuntimeException(); }
}
