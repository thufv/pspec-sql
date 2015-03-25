package org.apache.spark.sql.parquet;
/**
 * An alternative to {@link ParquetRelation} that plugs in using the data sources API.  This class is
 * intended as a full replacement of the Parquet support in Spark SQL.  The old implementation will
 * be deprecated and eventually removed once this version is proved to be stable enough.
 * <p>
 * Compared with the old implementation, this class has the following notable differences:
 * <p>
 *  - Partitioning discovery: Hive style multi-level partitions are auto discovered.
 *  - Metadata discovery: Parquet is a format comes with schema evolving support.  This data source
 *    can detect and merge schemas from all Parquet part-files as long as they are compatible.
 *    Also, metadata and {@link FileStatus}es are cached for better performance.
 *  - Statistics: Statistics for the size of the table are automatically populated during schema
 *    discovery.
 */
public  class ParquetRelation2 extends org.apache.spark.sql.sources.BaseRelation implements org.apache.spark.sql.sources.CatalystScan, org.apache.spark.sql.sources.InsertableRelation, org.apache.spark.mapreduce.SparkHadoopMapReduceUtil, org.apache.spark.Logging, scala.Product, scala.Serializable {
  static public  class PartitionValues implements scala.Product, scala.Serializable {
    public  scala.collection.Seq<java.lang.String> columnNames () { throw new RuntimeException(); }
    public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Literal> literals () { throw new RuntimeException(); }
    // not preceding
    public   PartitionValues (scala.collection.Seq<java.lang.String> columnNames, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Literal> literals) { throw new RuntimeException(); }
  }
  // no position
  static public  class PartitionValues$ extends scala.runtime.AbstractFunction2<scala.collection.Seq<java.lang.String>, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Literal>, org.apache.spark.sql.parquet.ParquetRelation2.PartitionValues> implements scala.Serializable {
    /**
     * Static reference to the singleton instance of this Scala object.
     */
    public static final PartitionValues$ MODULE$ = null;
    public   PartitionValues$ () { throw new RuntimeException(); }
  }
  private  class MetadataCache {
    public   MetadataCache () { throw new RuntimeException(); }
    private  org.apache.hadoop.fs.FileStatus[] metadataStatuses () { throw new RuntimeException(); }
    private  org.apache.hadoop.fs.FileStatus[] commonMetadataStatuses () { throw new RuntimeException(); }
    public  scala.collection.immutable.Map<org.apache.hadoop.fs.FileStatus, parquet.hadoop.Footer> footers () { throw new RuntimeException(); }
    public  org.apache.hadoop.fs.FileStatus[] dataStatuses () { throw new RuntimeException(); }
    public  org.apache.spark.sql.parquet.PartitionSpec partitionSpec () { throw new RuntimeException(); }
    public  org.apache.spark.sql.types.StructType parquetSchema () { throw new RuntimeException(); }
    public  org.apache.spark.sql.types.StructType schema () { throw new RuntimeException(); }
    public  boolean partitionKeysIncludedInParquetSchema () { throw new RuntimeException(); }
    public  void prepareMetadata (org.apache.hadoop.fs.Path path, org.apache.spark.sql.types.StructType schema, org.apache.hadoop.conf.Configuration conf) { throw new RuntimeException(); }
    /**
     * Refreshes <code>FileStatus</code>es, footers, partition spec, and table schema.
     */
    public  void refresh () { throw new RuntimeException(); }
    private  scala.Option<org.apache.spark.sql.types.StructType> readSchema () { throw new RuntimeException(); }
  }
  static public  java.lang.String MERGE_SCHEMA () { throw new RuntimeException(); }
  static public  java.lang.String DEFAULT_PARTITION_NAME () { throw new RuntimeException(); }
  static public  java.lang.String METASTORE_SCHEMA () { throw new RuntimeException(); }
  static public  scala.Option<org.apache.spark.sql.types.StructType> readSchema (scala.collection.Seq<parquet.hadoop.Footer> footers, org.apache.spark.sql.SQLContext sqlContext) { throw new RuntimeException(); }
  /**
   * Reconciles Hive Metastore case insensitivity issue and data type conflicts between Metastore
   * schema and Parquet schema.
   * <p>
   * Hive doesn't retain case information, while Parquet is case sensitive. On the other hand, the
   * schema read from Parquet files may be incomplete (e.g. older versions of Parquet doesn't
   * distinguish binary and string).  This method generates a correct schema by merging Metastore
   * schema data types and Parquet schema field names.
   */
  static public  org.apache.spark.sql.types.StructType mergeMetastoreParquetSchema (org.apache.spark.sql.types.StructType metastoreSchema, org.apache.spark.sql.types.StructType parquetSchema) { throw new RuntimeException(); }
  /**
   * Given a group of qualified paths, tries to parse them and returns a partition specification.
   * For example, given:
   * <pre><code>
   *   hdfs://&lt;host&gt;:&lt;port&gt;/path/to/partition/a=1/b=hello/c=3.14
   *   hdfs://&lt;host&gt;:&lt;port&gt;/path/to/partition/a=2/b=world/c=6.28
   * </code></pre>
   * it returns:
   * <pre><code>
   *   PartitionSpec(
   *     partitionColumns = StructType(
   *       StructField(name = "a", dataType = IntegerType, nullable = true),
   *       StructField(name = "b", dataType = StringType, nullable = true),
   *       StructField(name = "c", dataType = DoubleType, nullable = true)),
   *     partitions = Seq(
   *       Partition(
   *         values = Row(1, "hello", 3.14),
   *         path = "hdfs://&lt;host&gt;:&lt;port&gt;/path/to/partition/a=1/b=hello/c=3.14"),
   *       Partition(
   *         values = Row(2, "world", 6.28),
   *         path = "hdfs://&lt;host&gt;:&lt;port&gt;/path/to/partition/a=2/b=world/c=6.28")))
   * </code></pre>
   */
  static public  org.apache.spark.sql.parquet.PartitionSpec parsePartitions (scala.collection.Seq<org.apache.hadoop.fs.Path> paths, java.lang.String defaultPartitionName) { throw new RuntimeException(); }
  /**
   * Parses a single partition, returns column names and values of each partition column.  For
   * example, given:
   * <pre><code>
   *   path = hdfs://&lt;host&gt;:&lt;port&gt;/path/to/partition/a=42/b=hello/c=3.14
   * </code></pre>
   * it returns:
   * <pre><code>
   *   PartitionValues(
   *     Seq("a", "b", "c"),
   *     Seq(
   *       Literal(42, IntegerType),
   *       Literal("hello", StringType),
   *       Literal(3.14, FloatType)))
   * </code></pre>
   */
  static public  org.apache.spark.sql.parquet.ParquetRelation2.PartitionValues parsePartition (org.apache.hadoop.fs.Path path, java.lang.String defaultPartitionName) { throw new RuntimeException(); }
  static private  scala.Option<scala.Tuple2<java.lang.String, org.apache.spark.sql.catalyst.expressions.Literal>> parsePartitionColumn (java.lang.String columnSpec, java.lang.String defaultPartitionName) { throw new RuntimeException(); }
  /**
   * Resolves possible type conflicts between partitions by up-casting "lower" types.  The up-
   * casting order is:
   * <pre><code>
   *   NullType -&gt;
   *   IntegerType -&gt; LongType -&gt;
   *   FloatType -&gt; DoubleType -&gt; DecimalType.Unlimited -&gt;
   *   StringType
   * </code></pre>
   */
  static public  scala.collection.Seq<org.apache.spark.sql.parquet.ParquetRelation2.PartitionValues> resolvePartitions (scala.collection.Seq<org.apache.spark.sql.parquet.ParquetRelation2.PartitionValues> values) { throw new RuntimeException(); }
  /**
   * Converts a string to a <code>Literal</code> with automatic type inference.  Currently only supports
   * {@link IntegerType}, {@link LongType}, {@link FloatType}, {@link DoubleType}, {@link DecimalType.Unlimited}, and
   * {@link StringType}.
   */
  static public  org.apache.spark.sql.catalyst.expressions.Literal inferPartitionColumnValue (java.lang.String raw, java.lang.String defaultPartitionName) { throw new RuntimeException(); }
  static private  scala.collection.Seq<org.apache.spark.sql.types.DataType> upCastingOrder () { throw new RuntimeException(); }
  /**
   * Given a collection of {@link Literal}s, resolves possible type conflicts by up-casting "lower"
   * types.
   */
  static private  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Literal> resolveTypeConflicts (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Literal> literals) { throw new RuntimeException(); }
  public  scala.collection.Seq<java.lang.String> paths () { throw new RuntimeException(); }
  public  scala.collection.immutable.Map<java.lang.String, java.lang.String> parameters () { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.sql.types.StructType> maybeSchema () { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.sql.parquet.PartitionSpec> maybePartitionSpec () { throw new RuntimeException(); }
  public  org.apache.spark.sql.SQLContext sqlContext () { throw new RuntimeException(); }
  // not preceding
  public   ParquetRelation2 (scala.collection.Seq<java.lang.String> paths, scala.collection.immutable.Map<java.lang.String, java.lang.String> parameters, scala.Option<org.apache.spark.sql.types.StructType> maybeSchema, scala.Option<org.apache.spark.sql.parquet.PartitionSpec> maybePartitionSpec, org.apache.spark.sql.SQLContext sqlContext) { throw new RuntimeException(); }
  private  boolean shouldMergeSchemas () { throw new RuntimeException(); }
  private  scala.Option<org.apache.spark.sql.types.StructType> maybeMetastoreSchema () { throw new RuntimeException(); }
  private  java.lang.String defaultPartitionName () { throw new RuntimeException(); }
  public  boolean equals (Object other) { throw new RuntimeException(); }
  public  org.apache.spark.SparkContext sparkContext () { throw new RuntimeException(); }
  private  org.apache.spark.sql.parquet.ParquetRelation2.MetadataCache metadataCache () { throw new RuntimeException(); }
  public  org.apache.spark.sql.parquet.PartitionSpec partitionSpec () { throw new RuntimeException(); }
  public  org.apache.spark.sql.types.StructType partitionColumns () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.parquet.Partition> partitions () { throw new RuntimeException(); }
  public  boolean isPartitioned () { throw new RuntimeException(); }
  private  boolean partitionKeysIncludedInDataSchema () { throw new RuntimeException(); }
  private  org.apache.spark.sql.types.StructType parquetSchema () { throw new RuntimeException(); }
  public  org.apache.spark.sql.types.StructType schema () { throw new RuntimeException(); }
  private  boolean isSummaryFile (org.apache.hadoop.fs.Path file) { throw new RuntimeException(); }
  public  long sizeInBytes () { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<org.apache.spark.sql.Row> buildScan (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> predicates) { throw new RuntimeException(); }
  private  scala.collection.Seq<org.apache.spark.sql.parquet.Partition> prunePartitions (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> predicates, scala.collection.Seq<org.apache.spark.sql.parquet.Partition> partitions) { throw new RuntimeException(); }
  public  void insert (org.apache.spark.sql.DataFrame data, boolean overwrite) { throw new RuntimeException(); }
}
