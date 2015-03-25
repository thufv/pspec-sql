package org.apache.spark.sql;
/**
 * The entry point for working with structured data (rows and columns) in Spark.  Allows the
 * creation of {@link DataFrame} objects as well as the execution of SQL queries.
 * <p>
 * @groupname basic Basic Operations
 * @groupname ddl_ops Persistent Catalog DDL
 * @groupname cachemgmt Cached Table Management
 * @groupname genericdata Generic Data Sources
 * @groupname specificdata Specific Data Sources
 * @groupname config Configuration
 * @groupname dataframes Custom DataFrame Creation
 * @groupname Ungrouped Support functions for language integrated queries.
 */
public  class SQLContext implements org.apache.spark.Logging, scala.Serializable {
  /**
   * Loads a Parquet file, returning the result as a {@link DataFrame}. This function returns an empty
   * {@link DataFrame} if no paths are passed in.
   * <p>
   * @group specificdata
   */
  public  org.apache.spark.sql.DataFrame parquetFile (java.lang.String... paths) { throw new RuntimeException(); }
  // not preceding
  public  org.apache.spark.SparkContext sparkContext () { throw new RuntimeException(); }
  // not preceding
  public   SQLContext (org.apache.spark.SparkContext sparkContext) { throw new RuntimeException(); }
  public   SQLContext (org.apache.spark.api.java.JavaSparkContext sparkContext) { throw new RuntimeException(); }
  protected  org.apache.spark.sql.SQLConf conf () { throw new RuntimeException(); }
  /**
   * Set Spark SQL configuration properties.
   * <p>
   * @group config
   */
  public  void setConf (java.util.Properties props) { throw new RuntimeException(); }
  /**
   * Set the given Spark SQL configuration property.
   * <p>
   * @group config
   */
  public  void setConf (java.lang.String key, java.lang.String value) { throw new RuntimeException(); }
  /**
   * Return the value of Spark SQL configuration property for the given key.
   * <p>
   * @group config
   */
  public  java.lang.String getConf (java.lang.String key) { throw new RuntimeException(); }
  /**
   * Return the value of Spark SQL configuration property for the given key. If the key is not set
   * yet, return <code>defaultValue</code>.
   * <p>
   * @group config
   */
  public  java.lang.String getConf (java.lang.String key, java.lang.String defaultValue) { throw new RuntimeException(); }
  /**
   * Return all the configuration properties that have been set (i.e. not the default).
   * This creates a new copy of the config properties in the form of a Map.
   * <p>
   * @group config
   */
  public  scala.collection.immutable.Map<java.lang.String, java.lang.String> getAllConfs () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.analysis.Catalog catalog () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.analysis.FunctionRegistry functionRegistry () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.analysis.Analyzer analyzer () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.optimizer.Optimizer optimizer () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.sources.DDLParser ddlParser () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.SparkSQLParser sqlParser () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan parseSql (java.lang.String sql) { throw new RuntimeException(); }
  protected  org.apache.spark.sql.SQLContext.QueryExecution executeSql (java.lang.String sql) { throw new RuntimeException(); }
  protected  org.apache.spark.sql.SQLContext.QueryExecution executePlan (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  protected  org.apache.spark.sql.CacheManager cacheManager () { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * A collection of methods that are considered experimental, but can be used to hook into
   * the query planner for advanced functionality.
   * <p>
   * @group basic
   */
  public  org.apache.spark.sql.ExperimentalMethods experimental () { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Returns a {@link DataFrame} with no rows or columns.
   * <p>
   * @group basic
   */
  public  org.apache.spark.sql.DataFrame emptyDataFrame () { throw new RuntimeException(); }
  /**
   * A collection of methods for registering user-defined functions (UDF).
   * <p>
   * The following example registers a Scala closure as UDF:
   * <pre><code>
   *   sqlContext.udf.register("myUdf", (arg1: Int, arg2: String) =&gt; arg2 + arg1)
   * </code></pre>
   * <p>
   * The following example registers a UDF in Java:
   * <pre><code>
   *   sqlContext.udf().register("myUDF",
   *       new UDF2&lt;Integer, String, String&gt;() {
   *           &#64;Override
   *           public String call(Integer arg1, String arg2) {
   *               return arg2 + arg1;
   *           }
   *      }, DataTypes.StringType);
   * </code></pre>
   * <p>
   * Or, to use Java 8 lambda syntax:
   * <pre><code>
   *   sqlContext.udf().register("myUDF",
   *       (Integer arg1, String arg2) -&gt; arg2 + arg1),
   *       DataTypes.StringType);
   * </code></pre>
   * <p>
   * @group basic
   */
  public  org.apache.spark.sql.UDFRegistration udf () { throw new RuntimeException(); }
  /**
   * Returns true if the table is currently cached in-memory.
   * @group cachemgmt
   */
  public  boolean isCached (java.lang.String tableName) { throw new RuntimeException(); }
  /**
   * Caches the specified table in-memory.
   * @group cachemgmt
   */
  public  void cacheTable (java.lang.String tableName) { throw new RuntimeException(); }
  /**
   * Removes the specified table from the in-memory cache.
   * @group cachemgmt
   */
  public  void uncacheTable (java.lang.String tableName) { throw new RuntimeException(); }
  /**
   * Removes all cached tables from the in-memory cache.
   */
  public  void clearCache () { throw new RuntimeException(); }
  // no position
  public  class implicits implements scala.Serializable {
    /**
     * :: Experimental ::
     * (Scala-specific) Implicit methods available in Scala for converting
     * common Scala objects into {@link DataFrame}s.
     * <p>
     * <pre><code>
     *   val sqlContext = new SQLContext
     *   import sqlContext._
     * </code></pre>
     * <p>
     * @group basic
     */
    public   implicits () { throw new RuntimeException(); }
    /** Converts $"col name" into an {@link Column}. */
    public  class StringToColumn {
      public  scala.StringContext sc () { throw new RuntimeException(); }
      // not preceding
      public   StringToColumn (scala.StringContext sc) { throw new RuntimeException(); }
    }
    /** An implicit conversion that turns a Scala `Symbol` into a {@link Column}. */
    public  org.apache.spark.sql.ColumnName symbolToColumn (scala.Symbol s) { throw new RuntimeException(); }
    /** Creates a DataFrame from an RDD of case classes or tuples. */
    public <A extends scala.Product> org.apache.spark.sql.DataFrameHolder rddToDataFrameHolder (org.apache.spark.rdd.RDD<A> rdd, scala.reflect.api.TypeTags.TypeTag<A> evidence$1) { throw new RuntimeException(); }
    /** Creates a DataFrame from a local Seq of Product. */
    public <A extends scala.Product> org.apache.spark.sql.DataFrameHolder localSeqToDataFrameHolder (scala.collection.Seq<A> data, scala.reflect.api.TypeTags.TypeTag<A> evidence$2) { throw new RuntimeException(); }
    /** Creates a single column DataFrame from an RDD[Int]. */
    public  org.apache.spark.sql.DataFrameHolder intRddToDataFrameHolder (org.apache.spark.rdd.RDD<java.lang.Object> data) { throw new RuntimeException(); }
    /** Creates a single column DataFrame from an RDD[Long]. */
    public  org.apache.spark.sql.DataFrameHolder longRddToDataFrameHolder (org.apache.spark.rdd.RDD<java.lang.Object> data) { throw new RuntimeException(); }
    /** Creates a single column DataFrame from an RDD[String]. */
    public  org.apache.spark.sql.DataFrameHolder stringRddToDataFrameHolder (org.apache.spark.rdd.RDD<java.lang.String> data) { throw new RuntimeException(); }
  }
  public  org.apache.spark.sql.SQLContext.implicits$ implicits () { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Creates a DataFrame from an RDD of case classes.
   * <p>
   * @group dataframes
   */
  public <A extends scala.Product> org.apache.spark.sql.DataFrame createDataFrame (org.apache.spark.rdd.RDD<A> rdd, scala.reflect.api.TypeTags.TypeTag<A> evidence$3) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Creates a DataFrame from a local Seq of Product.
   * <p>
   * @group dataframes
   */
  public <A extends scala.Product> org.apache.spark.sql.DataFrame createDataFrame (scala.collection.Seq<A> data, scala.reflect.api.TypeTags.TypeTag<A> evidence$4) { throw new RuntimeException(); }
  /**
   * Convert a {@link BaseRelation} created for external data sources into a {@link DataFrame}.
   * <p>
   * @group dataframes
   */
  public  org.apache.spark.sql.DataFrame baseRelationToDataFrame (org.apache.spark.sql.sources.BaseRelation baseRelation) { throw new RuntimeException(); }
  /**
   * :: DeveloperApi ::
   * Creates a {@link DataFrame} from an {@link RDD} containing {@link Row}s using the given schema.
   * It is important to make sure that the structure of every {@link Row} of the provided RDD matches
   * the provided schema. Otherwise, there will be runtime exception.
   * Example:
   * <pre><code>
   *  import org.apache.spark.sql._
   *  import org.apache.spark.sql.types._
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
   *  val dataFrame = sqlContext.createDataFrame(people, schema)
   *  dataFrame.printSchema
   *  // root
   *  // |-- name: string (nullable = false)
   *  // |-- age: integer (nullable = true)
   *
   *  dataFrame.registerTempTable("people")
   *  sqlContext.sql("select name from people").collect.foreach(println)
   * </code></pre>
   * <p>
   * @group dataframes
   */
  public  org.apache.spark.sql.DataFrame createDataFrame (org.apache.spark.rdd.RDD<org.apache.spark.sql.Row> rowRDD, org.apache.spark.sql.types.StructType schema) { throw new RuntimeException(); }
  /**
   * :: DeveloperApi ::
   * Creates a {@link DataFrame} from an {@link JavaRDD} containing {@link Row}s using the given schema.
   * It is important to make sure that the structure of every {@link Row} of the provided RDD matches
   * the provided schema. Otherwise, there will be runtime exception.
   * <p>
   * @group dataframes
   */
  public  org.apache.spark.sql.DataFrame createDataFrame (org.apache.spark.api.java.JavaRDD<org.apache.spark.sql.Row> rowRDD, org.apache.spark.sql.types.StructType schema) { throw new RuntimeException(); }
  /**
   * Creates a {@link DataFrame} from an {@link JavaRDD} containing {@link Row}s by applying
   * a seq of names of columns to this RDD, the data type for each column will
   * be inferred by the first row.
   * <p>
   * @param rowRDD an JavaRDD of Row
   * @param columns names for each column
   * @return DataFrame
   * @group dataframes
   */
  public  org.apache.spark.sql.DataFrame createDataFrame (org.apache.spark.api.java.JavaRDD<org.apache.spark.sql.Row> rowRDD, java.util.List<java.lang.String> columns) { throw new RuntimeException(); }
  /**
   * Applies a schema to an RDD of Java Beans.
   * <p>
   * WARNING: Since there is no guaranteed ordering for fields in a Java Bean,
   *          SELECT * queries will return the columns in an undefined order.
   * @group dataframes
   */
  public  org.apache.spark.sql.DataFrame createDataFrame (org.apache.spark.rdd.RDD<?> rdd, java.lang.Class<?> beanClass) { throw new RuntimeException(); }
  /**
   * Applies a schema to an RDD of Java Beans.
   * <p>
   * WARNING: Since there is no guaranteed ordering for fields in a Java Bean,
   *          SELECT * queries will return the columns in an undefined order.
   * @group dataframes
   */
  public  org.apache.spark.sql.DataFrame createDataFrame (org.apache.spark.api.java.JavaRDD<?> rdd, java.lang.Class<?> beanClass) { throw new RuntimeException(); }
  /**
   * :: DeveloperApi ::
   * Creates a {@link DataFrame} from an {@link RDD} containing {@link Row}s by applying a schema to this RDD.
   * It is important to make sure that the structure of every {@link Row} of the provided RDD matches
   * the provided schema. Otherwise, there will be runtime exception.
   * Example:
   * <pre><code>
   *  import org.apache.spark.sql._
   *  import org.apache.spark.sql.types._
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
   *  val dataFrame = sqlContext. applySchema(people, schema)
   *  dataFrame.printSchema
   *  // root
   *  // |-- name: string (nullable = false)
   *  // |-- age: integer (nullable = true)
   *
   *  dataFrame.registerTempTable("people")
   *  sqlContext.sql("select name from people").collect.foreach(println)
   * </code></pre>
   */
  public  org.apache.spark.sql.DataFrame applySchema (org.apache.spark.rdd.RDD<org.apache.spark.sql.Row> rowRDD, org.apache.spark.sql.types.StructType schema) { throw new RuntimeException(); }
  public  org.apache.spark.sql.DataFrame applySchema (org.apache.spark.api.java.JavaRDD<org.apache.spark.sql.Row> rowRDD, org.apache.spark.sql.types.StructType schema) { throw new RuntimeException(); }
  /**
   * Applies a schema to an RDD of Java Beans.
   * <p>
   * WARNING: Since there is no guaranteed ordering for fields in a Java Bean,
   *          SELECT * queries will return the columns in an undefined order.
   */
  public  org.apache.spark.sql.DataFrame applySchema (org.apache.spark.rdd.RDD<?> rdd, java.lang.Class<?> beanClass) { throw new RuntimeException(); }
  /**
   * Applies a schema to an RDD of Java Beans.
   * <p>
   * WARNING: Since there is no guaranteed ordering for fields in a Java Bean,
   *          SELECT * queries will return the columns in an undefined order.
   */
  public  org.apache.spark.sql.DataFrame applySchema (org.apache.spark.api.java.JavaRDD<?> rdd, java.lang.Class<?> beanClass) { throw new RuntimeException(); }
  /**
   * Loads a Parquet file, returning the result as a {@link DataFrame}. This function returns an empty
   * {@link DataFrame} if no paths are passed in.
   * <p>
   * @group specificdata
   */
  public  org.apache.spark.sql.DataFrame parquetFile (scala.collection.Seq<java.lang.String> paths) { throw new RuntimeException(); }
  /**
   * Loads a JSON file (one object per line), returning the result as a {@link DataFrame}.
   * It goes through the entire dataset once to determine the schema.
   * <p>
   * @group specificdata
   */
  public  org.apache.spark.sql.DataFrame jsonFile (java.lang.String path) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Loads a JSON file (one object per line) and applies the given schema,
   * returning the result as a {@link DataFrame}.
   * <p>
   * @group specificdata
   */
  public  org.apache.spark.sql.DataFrame jsonFile (java.lang.String path, org.apache.spark.sql.types.StructType schema) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * @group specificdata
   */
  public  org.apache.spark.sql.DataFrame jsonFile (java.lang.String path, double samplingRatio) { throw new RuntimeException(); }
  /**
   * Loads an RDD[String] storing JSON objects (one object per record), returning the result as a
   * {@link DataFrame}.
   * It goes through the entire dataset once to determine the schema.
   * <p>
   * @group specificdata
   */
  public  org.apache.spark.sql.DataFrame jsonRDD (org.apache.spark.rdd.RDD<java.lang.String> json) { throw new RuntimeException(); }
  /**
   * Loads an RDD[String] storing JSON objects (one object per record), returning the result as a
   * {@link DataFrame}.
   * It goes through the entire dataset once to determine the schema.
   * <p>
   * @group specificdata
   */
  public  org.apache.spark.sql.DataFrame jsonRDD (org.apache.spark.api.java.JavaRDD<java.lang.String> json) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Loads an RDD[String] storing JSON objects (one object per record) and applies the given schema,
   * returning the result as a {@link DataFrame}.
   * <p>
   * @group specificdata
   */
  public  org.apache.spark.sql.DataFrame jsonRDD (org.apache.spark.rdd.RDD<java.lang.String> json, org.apache.spark.sql.types.StructType schema) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Loads an JavaRDD<String> storing JSON objects (one object per record) and applies the given
   * schema, returning the result as a {@link DataFrame}.
   * <p>
   * @group specificdata
   */
  public  org.apache.spark.sql.DataFrame jsonRDD (org.apache.spark.api.java.JavaRDD<java.lang.String> json, org.apache.spark.sql.types.StructType schema) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Loads an RDD[String] storing JSON objects (one object per record) inferring the
   * schema, returning the result as a {@link DataFrame}.
   * <p>
   * @group specificdata
   */
  public  org.apache.spark.sql.DataFrame jsonRDD (org.apache.spark.rdd.RDD<java.lang.String> json, double samplingRatio) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Loads a JavaRDD[String] storing JSON objects (one object per record) inferring the
   * schema, returning the result as a {@link DataFrame}.
   * <p>
   * @group specificdata
   */
  public  org.apache.spark.sql.DataFrame jsonRDD (org.apache.spark.api.java.JavaRDD<java.lang.String> json, double samplingRatio) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Returns the dataset stored at path as a DataFrame,
   * using the default data source configured by spark.sql.sources.default.
   * <p>
   * @group genericdata
   */
  public  org.apache.spark.sql.DataFrame load (java.lang.String path) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Returns the dataset stored at path as a DataFrame, using the given data source.
   * <p>
   * @group genericdata
   */
  public  org.apache.spark.sql.DataFrame load (java.lang.String path, java.lang.String source) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * (Java-specific) Returns the dataset specified by the given data source and
   * a set of options as a DataFrame.
   * <p>
   * @group genericdata
   */
  public  org.apache.spark.sql.DataFrame load (java.lang.String source, java.util.Map<java.lang.String, java.lang.String> options) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * (Scala-specific) Returns the dataset specified by the given data source and
   * a set of options as a DataFrame.
   * <p>
   * @group genericdata
   */
  public  org.apache.spark.sql.DataFrame load (java.lang.String source, scala.collection.immutable.Map<java.lang.String, java.lang.String> options) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * (Java-specific) Returns the dataset specified by the given data source and
   * a set of options as a DataFrame, using the given schema as the schema of the DataFrame.
   * <p>
   * @group genericdata
   */
  public  org.apache.spark.sql.DataFrame load (java.lang.String source, org.apache.spark.sql.types.StructType schema, java.util.Map<java.lang.String, java.lang.String> options) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * (Scala-specific) Returns the dataset specified by the given data source and
   * a set of options as a DataFrame, using the given schema as the schema of the DataFrame.
   * @group genericdata
   */
  public  org.apache.spark.sql.DataFrame load (java.lang.String source, org.apache.spark.sql.types.StructType schema, scala.collection.immutable.Map<java.lang.String, java.lang.String> options) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Creates an external table from the given path and returns the corresponding DataFrame.
   * It will use the default data source configured by spark.sql.sources.default.
   * <p>
   * @group ddl_ops
   */
  public  org.apache.spark.sql.DataFrame createExternalTable (java.lang.String tableName, java.lang.String path) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Creates an external table from the given path based on a data source
   * and returns the corresponding DataFrame.
   * <p>
   * @group ddl_ops
   */
  public  org.apache.spark.sql.DataFrame createExternalTable (java.lang.String tableName, java.lang.String path, java.lang.String source) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Creates an external table from the given path based on a data source and a set of options.
   * Then, returns the corresponding DataFrame.
   * <p>
   * @group ddl_ops
   */
  public  org.apache.spark.sql.DataFrame createExternalTable (java.lang.String tableName, java.lang.String source, java.util.Map<java.lang.String, java.lang.String> options) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * (Scala-specific)
   * Creates an external table from the given path based on a data source and a set of options.
   * Then, returns the corresponding DataFrame.
   * <p>
   * @group ddl_ops
   */
  public  org.apache.spark.sql.DataFrame createExternalTable (java.lang.String tableName, java.lang.String source, scala.collection.immutable.Map<java.lang.String, java.lang.String> options) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Create an external table from the given path based on a data source, a schema and
   * a set of options. Then, returns the corresponding DataFrame.
   * <p>
   * @group ddl_ops
   */
  public  org.apache.spark.sql.DataFrame createExternalTable (java.lang.String tableName, java.lang.String source, org.apache.spark.sql.types.StructType schema, java.util.Map<java.lang.String, java.lang.String> options) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * (Scala-specific)
   * Create an external table from the given path based on a data source, a schema and
   * a set of options. Then, returns the corresponding DataFrame.
   * <p>
   * @group ddl_ops
   */
  public  org.apache.spark.sql.DataFrame createExternalTable (java.lang.String tableName, java.lang.String source, org.apache.spark.sql.types.StructType schema, scala.collection.immutable.Map<java.lang.String, java.lang.String> options) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Construct a {@link DataFrame} representing the database table accessible via JDBC URL
   * url named table.
   * <p>
   * @group specificdata
   */
  public  org.apache.spark.sql.DataFrame jdbc (java.lang.String url, java.lang.String table) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Construct a {@link DataFrame} representing the database table accessible via JDBC URL
   * url named table.  Partitions of the table will be retrieved in parallel based on the parameters
   * passed to this function.
   * <p>
   * @param columnName the name of a column of integral type that will be used for partitioning.
   * @param lowerBound the minimum value of <code>columnName</code> to retrieve
   * @param upperBound the maximum value of <code>columnName</code> to retrieve
   * @param numPartitions the number of partitions.  the range <code>minValue</code>-<code>maxValue</code> will be split
   *                      evenly into this many partitions
   * <p>
   * @group specificdata
   */
  public  org.apache.spark.sql.DataFrame jdbc (java.lang.String url, java.lang.String table, java.lang.String columnName, long lowerBound, long upperBound, int numPartitions) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Construct a {@link DataFrame} representing the database table accessible via JDBC URL
   * url named table.  The theParts parameter gives a list expressions
   * suitable for inclusion in WHERE clauses; each one defines one partition
   * of the {@link DataFrame}.
   * <p>
   * @group specificdata
   */
  public  org.apache.spark.sql.DataFrame jdbc (java.lang.String url, java.lang.String table, java.lang.String[] theParts) { throw new RuntimeException(); }
  private  org.apache.spark.sql.DataFrame jdbc (java.lang.String url, java.lang.String table, org.apache.spark.Partition[] parts) { throw new RuntimeException(); }
  /**
   * Registers the given {@link DataFrame} as a temporary table in the catalog. Temporary tables exist
   * only during the lifetime of this instance of SQLContext.
   */
  public  void registerDataFrameAsTable (org.apache.spark.sql.DataFrame df, java.lang.String tableName) { throw new RuntimeException(); }
  /**
   * Drops the temporary table with the given table name in the catalog. If the table has been
   * cached/persisted before, it's also unpersisted.
   * <p>
   * @param tableName the name of the table to be unregistered.
   * <p>
   * @group basic
   */
  public  void dropTempTable (java.lang.String tableName) { throw new RuntimeException(); }
  /**
   * Executes a SQL query using Spark, returning the result as a {@link DataFrame}. The dialect that is
   * used for SQL parsing can be configured with 'spark.sql.dialect'.
   * <p>
   * @group basic
   */
  public  org.apache.spark.sql.DataFrame sql (java.lang.String sqlText) { throw new RuntimeException(); }
  /**
   * Returns the specified table as a {@link DataFrame}.
   * <p>
   * @group ddl_ops
   */
  public  org.apache.spark.sql.DataFrame table (java.lang.String tableName) { throw new RuntimeException(); }
  /**
   * Returns a {@link DataFrame} containing names of existing tables in the current database.
   * The returned DataFrame has two columns, tableName and isTemporary (a Boolean
   * indicating if a table is a temporary one or not).
   * <p>
   * @group ddl_ops
   */
  public  org.apache.spark.sql.DataFrame tables () { throw new RuntimeException(); }
  /**
   * Returns a {@link DataFrame} containing names of existing tables in the given database.
   * The returned DataFrame has two columns, tableName and isTemporary (a Boolean
   * indicating if a table is a temporary one or not).
   * <p>
   * @group ddl_ops
   */
  public  org.apache.spark.sql.DataFrame tables (java.lang.String databaseName) { throw new RuntimeException(); }
  /**
   * Returns the names of tables in the current database as an array.
   * <p>
   * @group ddl_ops
   */
  public  java.lang.String[] tableNames () { throw new RuntimeException(); }
  /**
   * Returns the names of tables in the given database as an array.
   * <p>
   * @group ddl_ops
   */
  public  java.lang.String[] tableNames (java.lang.String databaseName) { throw new RuntimeException(); }
  protected  class SparkPlanner extends org.apache.spark.sql.execution.SparkStrategies {
    public   SparkPlanner () { throw new RuntimeException(); }
    public  org.apache.spark.SparkContext sparkContext () { throw new RuntimeException(); }
    public  org.apache.spark.sql.SQLContext sqlContext () { throw new RuntimeException(); }
    public  boolean codegenEnabled () { throw new RuntimeException(); }
    public  int numPartitions () { throw new RuntimeException(); }
    public  scala.collection.Seq<org.apache.spark.sql.catalyst.planning.GenericStrategy<org.apache.spark.sql.execution.SparkPlan>> strategies () { throw new RuntimeException(); }
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
  protected  org.apache.spark.rdd.RDD<org.apache.spark.sql.Row> emptyResult () { throw new RuntimeException(); }
  /**
   * Prepares a planned SparkPlan for execution by inserting shuffle operations as needed.
   */
  protected  org.apache.spark.sql.catalyst.rules.RuleExecutor<org.apache.spark.sql.execution.SparkPlan> prepareForExecution () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.analysis.CheckAnalysis checkAnalysis () { throw new RuntimeException(); }
  /**
   * :: DeveloperApi ::
   * The primary workflow for executing relational queries using Spark.  Designed to allow easy
   * access to the intermediate phases of query execution for developers.
   */
  protected  class QueryExecution {
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan logical () { throw new RuntimeException(); }
    // not preceding
    public   QueryExecution (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan logical) { throw new RuntimeException(); }
    public  void assertAnalyzed () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan analyzed () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan withCachedData () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan optimizedPlan () { throw new RuntimeException(); }
    public  org.apache.spark.sql.execution.SparkPlan sparkPlan () { throw new RuntimeException(); }
    public  org.apache.spark.sql.execution.SparkPlan executedPlan () { throw new RuntimeException(); }
    /** Internal version of the RDD. Avoids copies and has no schema */
    public  org.apache.spark.rdd.RDD<org.apache.spark.sql.Row> toRdd () { throw new RuntimeException(); }
    protected <A extends java.lang.Object> java.lang.String stringOrError (scala.Function0<A> f) { throw new RuntimeException(); }
    public  java.lang.String simpleString () { throw new RuntimeException(); }
    public  java.lang.String toString () { throw new RuntimeException(); }
  }
  /**
   * Parses the data type in our internal string representation. The data type string should
   * have the same format as the one generated by <code>toString</code> in scala.
   * It is only used by PySpark.
   */
  protected  org.apache.spark.sql.types.DataType parseDataType (java.lang.String dataTypeString) { throw new RuntimeException(); }
  /**
   * Apply a schema defined by the schemaString to an RDD. It is only used by PySpark.
   */
  protected  org.apache.spark.sql.DataFrame applySchemaToPythonRDD (org.apache.spark.rdd.RDD<java.lang.Object[]> rdd, java.lang.String schemaString) { throw new RuntimeException(); }
  /**
   * Apply a schema defined by the schema to an RDD. It is only used by PySpark.
   */
  protected  org.apache.spark.sql.DataFrame applySchemaToPythonRDD (org.apache.spark.rdd.RDD<java.lang.Object[]> rdd, org.apache.spark.sql.types.StructType schema) { throw new RuntimeException(); }
  /**
   * Returns a Catalyst Schema for the given java bean class.
   */
  protected  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.AttributeReference> getSchema (java.lang.Class<?> beanClass) { throw new RuntimeException(); }
}
