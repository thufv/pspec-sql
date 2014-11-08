package org.apache.spark.sql.hive.test;
/**
 * A locally running test instance of Spark's Hive execution engine.
 * <p>
 * Data from {@link testTables} will be automatically loaded whenever a query is run over those tables.
 * Calling {@link reset} will delete all tables and other state in the database, leaving the database
 * in a "clean" state.
 * <p>
 * TestHive is singleton object version of this class because instantiating multiple copies of the
 * hive metastore seems to lead to weird non-deterministic failures.  Therefore, the execution of
 * test cases that rely on TestHive must be serialized.
 */
public  class TestHiveContext extends org.apache.spark.sql.hive.HiveContext {
  public   TestHiveContext (org.apache.spark.SparkContext sc) { throw new RuntimeException(); }
  public  java.lang.String warehousePath () { throw new RuntimeException(); }
  public  java.lang.String metastorePath () { throw new RuntimeException(); }
  /** Sets up the system initially or after a RESET command */
  protected  void configure () { throw new RuntimeException(); }
  public  java.io.File testTempDir () { throw new RuntimeException(); }
  /** The location of the compiled hive distribution */
  public  scala.Option<java.io.File> hiveHome () { throw new RuntimeException(); }
  /** The location of the hive source code. */
  public  scala.Option<java.io.File> hiveDevHome () { throw new RuntimeException(); }
  public  scala.collection.Seq<java.lang.String> runSqlHive (java.lang.String sql) { throw new RuntimeException(); }
  public  org.apache.spark.sql.hive.test.TestHiveContext.QueryExecution executePlan (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  /**
   * Returns the value of specified environmental variable as a {@link java.io.File} after checking
   * to ensure it exists
   */
  private  scala.Option<java.io.File> envVarToFile (java.lang.String envVar) { throw new RuntimeException(); }
  /**
   * Replaces relative paths to the parent directory "../" with hiveDevHome since this is how the
   * hive test cases assume the system is set up.
   */
  private  java.lang.String rewritePaths (java.lang.String cmd) { throw new RuntimeException(); }
  public  java.io.File hiveFilesTemp () { throw new RuntimeException(); }
  public  java.io.File inRepoTests () { throw new RuntimeException(); }
  public  java.io.File getHiveFile (java.lang.String path) { throw new RuntimeException(); }
  public  scala.util.matching.Regex describedTable () { throw new RuntimeException(); }
  protected  class HiveQLQueryExecution extends org.apache.spark.sql.hive.test.TestHiveContext.QueryExecution {
    public   HiveQLQueryExecution (java.lang.String hql) { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan logical () { throw new RuntimeException(); }
    public  scala.collection.Seq<java.lang.String> hiveExec () { throw new RuntimeException(); }
    public  java.lang.String toString () { throw new RuntimeException(); }
  }
  /**
   * Override QueryExecution with special debug workflow.
   */
  public abstract class QueryExecution extends org.apache.spark.sql.hive.HiveContext.QueryExecution {
    public   QueryExecution () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan analyzed () { throw new RuntimeException(); }
  }
  // not preceding
  public  class TestTable implements scala.Product, scala.Serializable {
    public  java.lang.String name () { throw new RuntimeException(); }
    public  scala.collection.Seq<scala.Function0<scala.runtime.BoxedUnit>> commands () { throw new RuntimeException(); }
    // not preceding
    public   TestTable (java.lang.String name, scala.collection.Seq<scala.Function0<scala.runtime.BoxedUnit>> commands) { throw new RuntimeException(); }
  }
  // no position
  public  class TestTable extends scala.runtime.AbstractFunction2<java.lang.String, scala.collection.Seq<scala.Function0<scala.runtime.BoxedUnit>>, org.apache.spark.sql.hive.test.TestHiveContext.TestTable> implements scala.Serializable {
    // not preceding
    public   TestTable () { throw new RuntimeException(); }
  }
  protected  class SqlCmd {
    public   SqlCmd (java.lang.String sql) { throw new RuntimeException(); }
    public  scala.Function0<scala.runtime.BoxedUnit> cmd () { throw new RuntimeException(); }
  }
  /**
   * A list of test tables and the DDL required to initialize them.  A test table is loaded on
   * demand when a query are run against it.
   */
  public  scala.collection.mutable.HashMap<java.lang.String, org.apache.spark.sql.hive.test.TestHiveContext.TestTable> testTables () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<java.lang.String, org.apache.spark.sql.hive.test.TestHiveContext.TestTable> registerTestTable (org.apache.spark.sql.hive.test.TestHiveContext.TestTable testTable) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.hive.test.TestHiveContext.TestTable> hiveQTestUtilTables () { throw new RuntimeException(); }
  private  scala.collection.mutable.HashSet<java.lang.String> loadedTables () { throw new RuntimeException(); }
  public  boolean cacheTables () { throw new RuntimeException(); }
  public  void loadTestTable (java.lang.String name) { throw new RuntimeException(); }
  /**
   * Records the UDFs present when the server starts, so we can delete ones that are created by
   * tests.
   */
  protected  java.util.Set<java.lang.String> originalUdfs () { throw new RuntimeException(); }
  /**
   * Resets the test instance by deleting any tables that have been created.
   * TODO: also clear out UDFs, views, etc.
   */
  public  void reset () { throw new RuntimeException(); }
}
