package org.apache.spark.sql;
/**
 * A class that enables the setting and getting of mutable config parameters/hints.
 * <p>
 * In the presence of a SQLContext, these can be set and queried by passing SET commands
 * into Spark SQL's query functions (i.e. sql()). Otherwise, users of this class can
 * modify the hints by programmatically calling the setters and getters of this class.
 * <p>
 * SQLConf is thread-safe (internally synchronized, so safe to be used in multiple threads).
 */
public  class SQLConf implements scala.Serializable {
  // no position
  static public  class Deprecated$ {
    /**
     * Static reference to the singleton instance of this Scala object.
     */
    public static final Deprecated$ MODULE$ = null;
    public   Deprecated$ () { throw new RuntimeException(); }
    public  java.lang.String MAPRED_REDUCE_TASKS () { throw new RuntimeException(); }
  }
  static public  java.lang.String COMPRESS_CACHED () { throw new RuntimeException(); }
  static public  java.lang.String COLUMN_BATCH_SIZE () { throw new RuntimeException(); }
  static public  java.lang.String IN_MEMORY_PARTITION_PRUNING () { throw new RuntimeException(); }
  static public  java.lang.String AUTO_BROADCASTJOIN_THRESHOLD () { throw new RuntimeException(); }
  static public  java.lang.String DEFAULT_SIZE_IN_BYTES () { throw new RuntimeException(); }
  static public  java.lang.String SHUFFLE_PARTITIONS () { throw new RuntimeException(); }
  static public  java.lang.String CODEGEN_ENABLED () { throw new RuntimeException(); }
  static public  java.lang.String DIALECT () { throw new RuntimeException(); }
  static public  java.lang.String PARQUET_BINARY_AS_STRING () { throw new RuntimeException(); }
  static public  java.lang.String PARQUET_INT96_AS_TIMESTAMP () { throw new RuntimeException(); }
  static public  java.lang.String PARQUET_CACHE_METADATA () { throw new RuntimeException(); }
  static public  java.lang.String PARQUET_COMPRESSION () { throw new RuntimeException(); }
  static public  java.lang.String PARQUET_FILTER_PUSHDOWN_ENABLED () { throw new RuntimeException(); }
  static public  java.lang.String PARQUET_USE_DATA_SOURCE_API () { throw new RuntimeException(); }
  static public  java.lang.String COLUMN_NAME_OF_CORRUPT_RECORD () { throw new RuntimeException(); }
  static public  java.lang.String BROADCAST_TIMEOUT () { throw new RuntimeException(); }
  static public  java.lang.String EXTERNAL_SORT () { throw new RuntimeException(); }
  static public  java.lang.String THRIFTSERVER_POOL () { throw new RuntimeException(); }
  static public  java.lang.String DEFAULT_DATA_SOURCE_NAME () { throw new RuntimeException(); }
  static public  java.lang.String SCHEMA_STRING_LENGTH_THRESHOLD () { throw new RuntimeException(); }
  static public  java.lang.String DATAFRAME_EAGER_ANALYSIS () { throw new RuntimeException(); }
  public   SQLConf () { throw new RuntimeException(); }
  /** Only low degree of contention is expected for conf, thus NOT using ConcurrentHashMap. */
  protected  java.util.Map<java.lang.String, java.lang.String> settings () { throw new RuntimeException(); }
  /**
   * The SQL dialect that is used when parsing queries.  This defaults to 'sql' which uses
   * a simple SQL parser provided by Spark SQL.  This is currently the only option for users of
   * SQLContext.
   * <p>
   * When using a HiveContext, this value defaults to 'hiveql', which uses the Hive 0.12.0 HiveQL
   * parser.  Users can change this to 'sql' if they want to run queries that aren't supported by
   * HiveQL (e.g., SELECT 1).
   * <p>
   * Note that the choice of dialect does not affect things like what tables are available or
   * how query execution is performed.
   */
  public  java.lang.String dialect () { throw new RuntimeException(); }
  /** When true tables cached using the in-memory columnar caching will be compressed. */
  public  boolean useCompression () { throw new RuntimeException(); }
  /** The compression codec for writing to a Parquetfile */
  public  java.lang.String parquetCompressionCodec () { throw new RuntimeException(); }
  /** The number of rows that will be  */
  public  int columnBatchSize () { throw new RuntimeException(); }
  /** Number of partitions to use for shuffle operators. */
  public  int numShufflePartitions () { throw new RuntimeException(); }
  /** When true predicates will be passed to the parquet record reader when possible. */
  public  boolean parquetFilterPushDown () { throw new RuntimeException(); }
  /** When true uses Parquet implementation based on data source API */
  public  boolean parquetUseDataSourceApi () { throw new RuntimeException(); }
  /** When true the planner will use the external sort, which may spill to disk. */
  public  boolean externalSortEnabled () { throw new RuntimeException(); }
  /**
   * When set to true, Spark SQL will use the Scala compiler at runtime to generate custom bytecode
   * that evaluates expressions found in queries.  In general this custom code runs much faster
   * than interpreted evaluation, but there are significant start-up costs due to compilation.
   * As a result codegen is only beneficial when queries run for a long time, or when the same
   * expressions are used multiple times.
   * <p>
   * Defaults to false as this feature is currently experimental.
   */
  public  boolean codegenEnabled () { throw new RuntimeException(); }
  /**
   * Upper bound on the sizes (in bytes) of the tables qualified for the auto conversion to
   * a broadcast value during the physical executions of join operations.  Setting this to -1
   * effectively disables auto conversion.
   * <p>
   * Hive setting: hive.auto.convert.join.noconditionaltask.size, whose default value is 10000.
   */
  public  int autoBroadcastJoinThreshold () { throw new RuntimeException(); }
  /**
   * The default size in bytes to assign to a logical operator's estimation statistics.  By default,
   * it is set to a larger value than <code>autoBroadcastJoinThreshold</code>, hence any logical operator
   * without a properly implemented estimation of this statistic will not be incorrectly broadcasted
   * in joins.
   */
  public  long defaultSizeInBytes () { throw new RuntimeException(); }
  /**
   * When set to true, we always treat byte arrays in Parquet files as strings.
   */
  public  boolean isParquetBinaryAsString () { throw new RuntimeException(); }
  /**
   * When set to true, we always treat INT96Values in Parquet files as timestamp.
   */
  public  boolean isParquetINT96AsTimestamp () { throw new RuntimeException(); }
  /**
   * When set to true, partition pruning for in-memory columnar tables is enabled.
   */
  public  boolean inMemoryPartitionPruning () { throw new RuntimeException(); }
  public  java.lang.String columnNameOfCorruptRecord () { throw new RuntimeException(); }
  /**
   * Timeout in seconds for the broadcast wait time in hash join
   */
  public  int broadcastTimeout () { throw new RuntimeException(); }
  public  java.lang.String defaultDataSourceName () { throw new RuntimeException(); }
  public  int schemaStringLengthThreshold () { throw new RuntimeException(); }
  public  boolean dataFrameEagerAnalysis () { throw new RuntimeException(); }
  /** Set Spark SQL configuration properties. */
  public  void setConf (java.util.Properties props) { throw new RuntimeException(); }
  /** Set the given Spark SQL configuration property. */
  public  void setConf (java.lang.String key, java.lang.String value) { throw new RuntimeException(); }
  /** Return the value of Spark SQL configuration property for the given key. */
  public  java.lang.String getConf (java.lang.String key) { throw new RuntimeException(); }
  /**
   * Return the value of Spark SQL configuration property for the given key. If the key is not set
   * yet, return <code>defaultValue</code>.
   */
  public  java.lang.String getConf (java.lang.String key, java.lang.String defaultValue) { throw new RuntimeException(); }
  /**
   * Return all the configuration properties that have been set (i.e. not the default).
   * This creates a new copy of the config properties in the form of a Map.
   */
  public  scala.collection.immutable.Map<java.lang.String, java.lang.String> getAllConfs () { throw new RuntimeException(); }
  public  void unsetConf (java.lang.String key) { throw new RuntimeException(); }
  public  void clear () { throw new RuntimeException(); }
}
