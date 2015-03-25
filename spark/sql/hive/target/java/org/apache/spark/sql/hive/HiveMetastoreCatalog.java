package org.apache.spark.sql.hive;
public  class HiveMetastoreCatalog implements org.apache.spark.sql.catalyst.analysis.Catalog, org.apache.spark.Logging {
  public   HiveMetastoreCatalog (org.apache.spark.sql.hive.HiveContext hive) { throw new RuntimeException(); }
  /** Connection to hive metastore.  Usages should lock on `this`. */
  protected  org.apache.hadoop.hive.ql.metadata.Hive client () { throw new RuntimeException(); }
  /** Usages should lock on `this`. */
  protected  org.apache.hadoop.hive.metastore.Warehouse hiveWarehouse () { throw new RuntimeException(); }
  /** A fully qualified identifier for a table (i.e., database.tableName) */
  public  class QualifiedTableName implements scala.Product, scala.Serializable {
    public  java.lang.String database () { throw new RuntimeException(); }
    public  java.lang.String name () { throw new RuntimeException(); }
    // not preceding
    public   QualifiedTableName (java.lang.String database, java.lang.String name) { throw new RuntimeException(); }
    public  org.apache.spark.sql.hive.HiveMetastoreCatalog.QualifiedTableName toLowerCase () { throw new RuntimeException(); }
  }
  // no position
  public  class QualifiedTableName extends scala.runtime.AbstractFunction2<java.lang.String, java.lang.String, org.apache.spark.sql.hive.HiveMetastoreCatalog.QualifiedTableName> implements scala.Serializable {
    public   QualifiedTableName () { throw new RuntimeException(); }
  }
  /** A cache of Spark SQL data source tables that have been accessed. */
  protected  com.google.common.cache.LoadingCache<org.apache.spark.sql.hive.HiveMetastoreCatalog.QualifiedTableName, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> cachedDataSourceTables () { throw new RuntimeException(); }
  public  void refreshTable (java.lang.String databaseName, java.lang.String tableName) { throw new RuntimeException(); }
  public  void invalidateTable (java.lang.String databaseName, java.lang.String tableName) { throw new RuntimeException(); }
  public  boolean caseSensitive () { throw new RuntimeException(); }
  /**
   * Creates a data source table (a table created with USING clause) in Hive's metastore.
   * Returns true when the table has been created. Otherwise, false.
   */
  public  void createDataSourceTable (java.lang.String tableName, scala.Option<org.apache.spark.sql.types.StructType> userSpecifiedSchema, java.lang.String provider, scala.collection.immutable.Map<java.lang.String, java.lang.String> options, boolean isExternal) { throw new RuntimeException(); }
  public  java.lang.String hiveDefaultTableFilePath (java.lang.String tableName) { throw new RuntimeException(); }
  public  boolean tableExists (scala.collection.Seq<java.lang.String> tableIdentifier) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan lookupRelation (scala.collection.Seq<java.lang.String> tableIdentifier, scala.Option<java.lang.String> alias) { throw new RuntimeException(); }
  private  org.apache.spark.sql.sources.LogicalRelation convertToParquetRelation (org.apache.spark.sql.hive.MetastoreRelation metastoreRelation) { throw new RuntimeException(); }
  public  scala.collection.Seq<scala.Tuple2<java.lang.String, java.lang.Object>> getTables (scala.Option<java.lang.String> databaseName) { throw new RuntimeException(); }
  /**
   * Create table with specified database, table name, table description and schema
   * @param databaseName Database Name
   * @param tableName Table Name
   * @param schema Schema of the new table, if not specified, will use the schema
   *               specified in crtTbl
   * @param allowExisting if true, ignore AlreadyExistsException
   * @param desc CreateTableDesc object which contains the SerDe info. Currently
   *               we support most of the features except the bucket.
   */
  public  void createTable (java.lang.String databaseName, java.lang.String tableName, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> schema, boolean allowExisting, scala.Option<org.apache.hadoop.hive.ql.plan.CreateTableDesc> desc) { throw new RuntimeException(); }
  protected  scala.Tuple2<scala.Option<java.lang.String>, java.lang.String> processDatabaseAndTableName (scala.Option<java.lang.String> databaseName, java.lang.String tableName) { throw new RuntimeException(); }
  protected  scala.Tuple2<java.lang.String, java.lang.String> processDatabaseAndTableName (java.lang.String databaseName, java.lang.String tableName) { throw new RuntimeException(); }
  // no position
  public  class ParquetConversions extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
    /**
     * When scanning or writing to non-partitioned Metastore Parquet tables, convert them to Parquet
     * data source relations for better performance.
     * <p>
     * This rule can be considered as {@link HiveStrategies.ParquetConversion} done right.
     */
    public   ParquetConversions () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // not preceding
  public  org.apache.spark.sql.hive.HiveMetastoreCatalog.ParquetConversions$ ParquetConversions () { throw new RuntimeException(); }
  // no position
  public  class CreateTables extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
    /**
     * Creates any tables required for query execution.
     * For example, because of a CREATE TABLE X AS statement.
     */
    public   CreateTables () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // not preceding
  public  org.apache.spark.sql.hive.HiveMetastoreCatalog.CreateTables$ CreateTables () { throw new RuntimeException(); }
  // no position
  public  class PreInsertionCasts extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
    /**
     * Casts input data to correct data types according to table definition before inserting into
     * that table.
     */
    public   PreInsertionCasts () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan castChildOutput (org.apache.spark.sql.catalyst.plans.logical.InsertIntoTable p, org.apache.spark.sql.hive.MetastoreRelation table, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan child) { throw new RuntimeException(); }
  }
  // not preceding
  public  org.apache.spark.sql.hive.HiveMetastoreCatalog.PreInsertionCasts$ PreInsertionCasts () { throw new RuntimeException(); }
  /**
   * UNIMPLEMENTED: It needs to be decided how we will persist in-memory tables to the metastore.
   * For now, if this functionality is desired mix in the in-memory {@link OverrideCatalog}.
   */
  public  void registerTable (scala.collection.Seq<java.lang.String> tableIdentifier, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  /**
   * UNIMPLEMENTED: It needs to be decided how we will persist in-memory tables to the metastore.
   * For now, if this functionality is desired mix in the in-memory {@link OverrideCatalog}.
   */
  public  void unregisterTable (scala.collection.Seq<java.lang.String> tableIdentifier) { throw new RuntimeException(); }
  public  void unregisterAllTables () { throw new RuntimeException(); }
}
