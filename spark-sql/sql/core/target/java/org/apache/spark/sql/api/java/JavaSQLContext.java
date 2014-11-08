package org.apache.spark.sql.api.java;
/**
 * The entry point for executing Spark SQL queries from a Java program.
 */
public  class JavaSQLContext implements org.apache.spark.sql.api.java.UDFRegistration {
  public  org.apache.spark.sql.SQLContext sqlContext () { throw new RuntimeException(); }
  // not preceding
  public   JavaSQLContext (org.apache.spark.sql.SQLContext sqlContext) { throw new RuntimeException(); }
  public   JavaSQLContext (org.apache.spark.api.java.JavaSparkContext sparkContext) { throw new RuntimeException(); }
  /**
   * Executes a SQL query using Spark, returning the result as a SchemaRDD.  The dialect that is
   * used for SQL parsing can be configured with 'spark.sql.dialect'.
   * <p>
   * @group userf
   */
  public  org.apache.spark.sql.api.java.JavaSchemaRDD sql (java.lang.String sqlText) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Creates an empty parquet file with the schema of class <code>beanClass</code>, which can be registered as
   * a table. This registered table can be used as the target of future <code>insertInto</code> operations.
   * <p>
   * <pre><code>
   *   JavaSQLContext sqlCtx = new JavaSQLContext(...)
   *
   *   sqlCtx.createParquetFile(Person.class, "path/to/file.parquet").registerTempTable("people")
   *   sqlCtx.sql("INSERT INTO people SELECT 'michael', 29")
   * </code></pre>
   * <p>
   * @param beanClass A java bean class object that will be used to determine the schema of the
   *                  parquet file.
   * @param path The path where the directory containing parquet metadata should be created.
   *             Data inserted into this table will also be stored at this location.
   * @param allowExisting When false, an exception will be thrown if this directory already exists.
   * @param conf A Hadoop configuration object that can be used to specific options to the parquet
   *             output format.
   */
  public  org.apache.spark.sql.api.java.JavaSchemaRDD createParquetFile (java.lang.Class<?> beanClass, java.lang.String path, boolean allowExisting, org.apache.hadoop.conf.Configuration conf) { throw new RuntimeException(); }
  /**
   * Applies a schema to an RDD of Java Beans.
   */
  public  org.apache.spark.sql.api.java.JavaSchemaRDD applySchema (org.apache.spark.api.java.JavaRDD<?> rdd, java.lang.Class<?> beanClass) { throw new RuntimeException(); }
  /**
   * :: DeveloperApi ::
   * Creates a JavaSchemaRDD from an RDD containing Rows by applying a schema to this RDD.
   * It is important to make sure that the structure of every Row of the provided RDD matches the
   * provided schema. Otherwise, there will be runtime exception.
   */
  public  org.apache.spark.sql.api.java.JavaSchemaRDD applySchema (org.apache.spark.api.java.JavaRDD<org.apache.spark.sql.api.java.Row> rowRDD, org.apache.spark.sql.api.java.StructType schema) { throw new RuntimeException(); }
  /**
   * Loads a parquet file, returning the result as a {@link JavaSchemaRDD}.
   */
  public  org.apache.spark.sql.api.java.JavaSchemaRDD parquetFile (java.lang.String path) { throw new RuntimeException(); }
  /**
   * Loads a JSON file (one object per line), returning the result as a JavaSchemaRDD.
   * It goes through the entire dataset once to determine the schema.
   */
  public  org.apache.spark.sql.api.java.JavaSchemaRDD jsonFile (java.lang.String path) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Loads a JSON file (one object per line) and applies the given schema,
   * returning the result as a JavaSchemaRDD.
   */
  public  org.apache.spark.sql.api.java.JavaSchemaRDD jsonFile (java.lang.String path, org.apache.spark.sql.api.java.StructType schema) { throw new RuntimeException(); }
  /**
   * Loads an RDD[String] storing JSON objects (one object per record), returning the result as a
   * JavaSchemaRDD.
   * It goes through the entire dataset once to determine the schema.
   */
  public  org.apache.spark.sql.api.java.JavaSchemaRDD jsonRDD (org.apache.spark.api.java.JavaRDD<java.lang.String> json) { throw new RuntimeException(); }
  /**
   * :: Experimental ::
   * Loads an RDD[String] storing JSON objects (one object per record) and applies the given schema,
   * returning the result as a JavaSchemaRDD.
   */
  public  org.apache.spark.sql.api.java.JavaSchemaRDD jsonRDD (org.apache.spark.api.java.JavaRDD<java.lang.String> json, org.apache.spark.sql.api.java.StructType schema) { throw new RuntimeException(); }
  /**
   * Registers the given RDD as a temporary table in the catalog.  Temporary tables exist only
   * during the lifetime of this instance of SQLContext.
   */
  public  void registerRDDAsTable (org.apache.spark.sql.api.java.JavaSchemaRDD rdd, java.lang.String tableName) { throw new RuntimeException(); }
  /** Returns a Catalyst Schema for the given java bean class. */
  protected  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.AttributeReference> getSchema (java.lang.Class<?> beanClass) { throw new RuntimeException(); }
}
