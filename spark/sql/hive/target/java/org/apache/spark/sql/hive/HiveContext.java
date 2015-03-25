package org.apache.spark.sql.hive;
/**
 * An instance of the Spark SQL execution engine that integrates with data stored in Hive.
 * Configuration for Hive is read from hive-site.xml on the classpath.
 */
public  class HiveContext extends org.apache.spark.sql.SQLContext {
  /** Extends QueryExecution with hive specific features. */
  protected  class QueryExecution extends org.apache.spark.sql.SQLContext.QueryExecution {
    public   QueryExecution (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan logicalPlan) { throw new RuntimeException(); }
    /**
     * Returns the result as a hive compatible sequence of strings.  For native commands, the
     * execution is simply passed back to Hive.
     */
    public  scala.collection.Seq<java.lang.String> stringResult () { throw new RuntimeException(); }
    public  java.lang.String simpleString () { throw new RuntimeException(); }
  }
  static protected  scala.collection.Seq<org.apache.spark.sql.types.NativeType> primitiveTypes () { throw new RuntimeException(); }
  static protected  java.lang.String toHiveString (scala.Tuple2<java.lang.Object, org.apache.spark.sql.types.DataType> a) { throw new RuntimeException(); }
  /** Hive outputs fields of structs slightly differently than top level attributes. */
  static protected  java.lang.String toHiveStructString (scala.Tuple2<java.lang.Object, org.apache.spark.sql.types.DataType> a) { throw new RuntimeException(); }
  public   HiveContext (org.apache.spark.SparkContext sc) { throw new RuntimeException(); }
  protected  org.apache.spark.sql.SQLConf conf () { throw new RuntimeException(); }
  /**
   * When true, enables an experimental feature where metastore tables that use the parquet SerDe
   * are automatically converted to use the Spark SQL parquet table scan, instead of the Hive
   * SerDe.
   */
  protected  boolean convertMetastoreParquet () { throw new RuntimeException(); }
  /**
   * When true, a table created by a Hive CTAS statement (no USING clause) will be
   * converted to a data source table, using the data source set by spark.sql.sources.default.
   * The table in CTAS statement will be converted when it meets any of the following conditions:
   *   - The CTAS does not specify any of a SerDe (ROW FORMAT SERDE), a File Format (STORED AS), or
   *     a Storage Hanlder (STORED BY), and the value of hive.default.fileformat in hive-site.xml
   *     is either TextFile or SequenceFile.
   *   - The CTAS statement specifies TextFile (STORED AS TEXTFILE) as the file format and no SerDe
   *     is specified (no ROW FORMAT SERDE clause).
   *   - The CTAS statement specifies SequenceFile (STORED AS SEQUENCEFILE) as the file format
   *     and no SerDe is specified (no ROW FORMAT SERDE clause).
   */
  protected  boolean convertCTAS () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.hive.HiveContext.QueryExecution executePlan (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  protected  org.apache.spark.sql.sources.DDLParser ddlParserWithHiveQL () { throw new RuntimeException(); }
  public  org.apache.spark.sql.DataFrame sql (java.lang.String sqlText) { throw new RuntimeException(); }
  /**
   * Invalidate and refresh all the cached the metadata of the given table. For performance reasons,
   * Spark SQL or the external data source library it uses might cache certain metadata about a
   * table, such as the location of blocks. When those change outside of Spark SQL, users should
   * call this function to invalidate the cache.
   */
  public  void refreshTable (java.lang.String tableName) { throw new RuntimeException(); }
  protected  void invalidateTable (java.lang.String tableName) { throw new RuntimeException(); }
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
   * SQLConf and HiveConf contracts:
   * <p>
   * 1. reuse existing started SessionState if any
   * 2. when the Hive session is first initialized, params in HiveConf will get picked up by the
   *    SQLConf.  Additionally, any properties set by set() or a SET command inside sql() will be
   *    set in the SQLConf *as well as* in the HiveConf.
   */
  protected  org.apache.hadoop.hive.ql.session.SessionState sessionState () { throw new RuntimeException(); }
  protected  org.apache.hadoop.hive.conf.HiveConf hiveconf () { throw new RuntimeException(); }
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
  private  org.apache.spark.sql.SQLContext.SparkPlanner hivePlanner () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.SQLContext.SparkPlanner planner () { throw new RuntimeException(); }
  /**
   * added by luochen
   * load policy during startups
   */
  private  java.lang.String policyPath () { throw new RuntimeException(); }
  private  java.lang.String metaPath () { throw new RuntimeException(); }
  public  org.apache.spark.sql.hive.HiveTableInfo hiveInfo () { throw new RuntimeException(); }
}
