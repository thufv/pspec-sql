package org.apache.spark.sql.hive;
/**
 * An instance of the Spark SQL execution engine that integrates with data stored in Hive.
 * Configuration for Hive is read from hive-site.xml on the classpath.
 */
public  class HiveContext extends org.apache.spark.sql.SQLContext {
  public   HiveContext (org.apache.spark.SparkContext sc) { throw new RuntimeException(); }
  private  java.lang.String dialect () { throw new RuntimeException(); }
  /**
   * When true, enables an experimental feature where metastore tables that use the parquet SerDe
   * are automatically converted to use the Spark SQL parquet table scan, instead of the Hive
   * SerDe.
   */
  private  boolean convertMetastoreParquet () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.hive.HiveContext.QueryExecution executePlan (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  public  org.apache.spark.sql.SchemaRDD sql (java.lang.String sqlText) { throw new RuntimeException(); }
  public  org.apache.spark.sql.SchemaRDD hiveql (java.lang.String hqlQuery) { throw new RuntimeException(); }
  public  org.apache.spark.sql.SchemaRDD hql (java.lang.String hqlQuery) { throw new RuntimeException(); }
  /**
   * Creates a table using the schema of the given class.
   * <p>
   * @param tableName The name of the table to create.
   * @param allowExisting When false, an exception will be thrown if the table already exists.
   * @tparam A A case class that is used to describe the schema of the table to be created.
   */
  public <A extends scala.Product> void createTable (java.lang.String tableName, boolean allowExisting, scala.reflect.api.TypeTags.TypeTag<A> evidence$1) { throw new RuntimeException(); }
  /**
   * Analyzes the given table in the current database to generate statistics, which will be
   * used in query optimizations.
   * <p>
   * Right now, it only supports Hive tables and it only updates the size of a Hive table
   * in the Hive metastore.
   */
  public  void analyze (java.lang.String tableName) { throw new RuntimeException(); }
  protected  java.io.OutputStream outputBuffer () { throw new RuntimeException(); }
  /**
   * SQLConf and HiveConf contracts: when the hive session is first initialized, params in
   * HiveConf will get picked up by the SQLConf.  Additionally, any properties set by
   * set() or a SET command inside sql() will be set in the SQLConf *as well as*
   * in the HiveConf.
   */
  protected  org.apache.hadoop.hive.conf.HiveConf hiveconf () { throw new RuntimeException(); }
  protected  org.apache.hadoop.hive.ql.session.SessionState sessionState () { throw new RuntimeException(); }
  public  void setConf (java.lang.String key, java.lang.String value) { throw new RuntimeException(); }
  protected  org.apache.spark.sql.hive.HiveMetastoreCatalog catalog () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.hive.HiveFunctionRegistry functionRegistry () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.analysis.Analyzer analyzer () { throw new RuntimeException(); }
  /**
   * Runs the specified SQL query using Hive.
   */
  protected  scala.collection.Seq<java.lang.String> runSqlHive (java.lang.String sql) { throw new RuntimeException(); }
  /**
   * Execute the command using Hive and return the results as a sequence. Each element
   * in the sequence is one row.
   */
  protected  scala.collection.Seq<java.lang.String> runHive (java.lang.String cmd, int maxRows) { throw new RuntimeException(); }
  public  org.apache.spark.sql.SQLContext.SparkPlanner hivePlanner () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.SQLContext.SparkPlanner planner () { throw new RuntimeException(); }
  /** Extends QueryExecution with hive specific features. */
  protected abstract class QueryExecution extends org.apache.spark.sql.SQLContext.QueryExecution {
    public   QueryExecution () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan optimizedPlan () { throw new RuntimeException(); }
    public  org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> toRdd () { throw new RuntimeException(); }
    protected  scala.collection.Seq<scala.Product> primitiveTypes () { throw new RuntimeException(); }
    protected  java.lang.String toHiveString (scala.Tuple2<java.lang.Object, org.apache.spark.sql.catalyst.types.DataType> a) { throw new RuntimeException(); }
    /** Hive outputs fields of structs slightly differently than top level attributes. */
    protected  java.lang.String toHiveStructString (scala.Tuple2<java.lang.Object, org.apache.spark.sql.catalyst.types.DataType> a) { throw new RuntimeException(); }
    /**
     * Returns the result as a hive compatible sequence of strings.  For native commands, the
     * execution is simply passed back to Hive.
     */
    public  scala.collection.Seq<java.lang.String> stringResult () { throw new RuntimeException(); }
    public  java.lang.String simpleString () { throw new RuntimeException(); }
  }
}
