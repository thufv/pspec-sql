package org.apache.spark.sql;
/**
 * Contains functions that are shared between all SchemaRDD types (i.e., Scala, Java)
 */
private abstract interface SchemaRDDLike {
  public abstract  org.apache.spark.sql.SQLContext sqlContext () ;
  public abstract  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan baseLogicalPlan () ;
  private abstract  org.apache.spark.sql.SchemaRDD baseSchemaRDD () ;
  /**
   * :: DeveloperApi ::
   * A lazily computed query execution workflow.  All other RDD operations are passed
   * through to the RDD that is produced by this workflow. This workflow is produced lazily because
   * invoking the whole query optimization pipeline can be expensive.
   * <p>
   * The query execution is considered a Developer API as phases may be added or removed in future
   * releases.  This execution is only exposed to provide an interface for inspecting the various
   * phases for debugging purposes.  Applications should not depend on particular phases existing
   * or producing any specific output, even for exactly the same query.
   * <p>
   * Additionally, the RDD exposed by this execution is not designed for consumption by end users.
   * In particular, it does not contain any schema information, and it reuses Row objects
   * internally.  This object reuse improves performance, but can make programming against the RDD
   * more difficult.  Instead end users should perform RDD operations on a SchemaRDD directly.
   */
  public  org.apache.spark.sql.SQLContext.QueryExecution queryExecution () ;
  protected  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan logicalPlan () ;
  public  java.lang.String toString () ;
  /**
   * Saves the contents of this <code>SchemaRDD</code> as a parquet file, preserving the schema.  Files that
   * are written out using this method can be read back in as a SchemaRDD using the <code>parquetFile</code>
   * function.
   * <p>
   * @group schema
   */
  public  void saveAsParquetFile (java.lang.String path) ;
  /**
   * Registers this RDD as a temporary table using the given name.  The lifetime of this temporary
   * table is tied to the {@link SQLContext} that was used to create this SchemaRDD.
   * <p>
   * @group schema
   */
  public  void registerTempTable (java.lang.String tableName) ;
  public  void registerAsTable (java.lang.String tableName) ;
  /**
   * :: Experimental ::
   * Adds the rows from this RDD to the specified table, optionally overwriting the existing data.
   * <p>
   * @group schema
   */
  public  void insertInto (java.lang.String tableName, boolean overwrite) ;
  /**
   * :: Experimental ::
   * Appends the rows from this RDD to the specified table.
   * <p>
   * @group schema
   */
  public  void insertInto (java.lang.String tableName) ;
  /**
   * :: Experimental ::
   * Creates a table from the the contents of this SchemaRDD.  This will fail if the table already
   * exists.
   * <p>
   * Note that this currently only works with SchemaRDDs that are created from a HiveContext as
   * there is no notion of a persisted catalog in a standard SQL context.  Instead you can write
   * an RDD out to a parquet file, and then register that file as a table.  This "table" can then
   * be the target of an <code>insertInto</code>.
   * <p>
   * @group schema
   */
  public  void saveAsTable (java.lang.String tableName) ;
  /** Returns the schema as a string in the tree format.
   * <p>
   * @group schema
   */
  public  java.lang.String schemaString () ;
  /** Prints out the schema.
   * <p>
   * @group schema
   */
  public  void printSchema () ;
}
