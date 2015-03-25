package org.apache.spark.sql.parquet;
// no position
/**
 * Refreshes <code>FileStatus</code>es, footers, partition spec, and table schema.
 */
public  class ParquetRelation2$ implements scala.Serializable {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final ParquetRelation2$ MODULE$ = null;
  public   ParquetRelation2$ () { throw new RuntimeException(); }
  public  java.lang.String MERGE_SCHEMA () { throw new RuntimeException(); }
  public  java.lang.String DEFAULT_PARTITION_NAME () { throw new RuntimeException(); }
  public  java.lang.String METASTORE_SCHEMA () { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.sql.types.StructType> readSchema (scala.collection.Seq<parquet.hadoop.Footer> footers, org.apache.spark.sql.SQLContext sqlContext) { throw new RuntimeException(); }
  /**
   * Reconciles Hive Metastore case insensitivity issue and data type conflicts between Metastore
   * schema and Parquet schema.
   * <p>
   * Hive doesn't retain case information, while Parquet is case sensitive. On the other hand, the
   * schema read from Parquet files may be incomplete (e.g. older versions of Parquet doesn't
   * distinguish binary and string).  This method generates a correct schema by merging Metastore
   * schema data types and Parquet schema field names.
   */
  public  org.apache.spark.sql.types.StructType mergeMetastoreParquetSchema (org.apache.spark.sql.types.StructType metastoreSchema, org.apache.spark.sql.types.StructType parquetSchema) { throw new RuntimeException(); }
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
  public  org.apache.spark.sql.parquet.PartitionSpec parsePartitions (scala.collection.Seq<org.apache.hadoop.fs.Path> paths, java.lang.String defaultPartitionName) { throw new RuntimeException(); }
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
  public  org.apache.spark.sql.parquet.ParquetRelation2.PartitionValues parsePartition (org.apache.hadoop.fs.Path path, java.lang.String defaultPartitionName) { throw new RuntimeException(); }
  private  scala.Option<scala.Tuple2<java.lang.String, org.apache.spark.sql.catalyst.expressions.Literal>> parsePartitionColumn (java.lang.String columnSpec, java.lang.String defaultPartitionName) { throw new RuntimeException(); }
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
  public  scala.collection.Seq<org.apache.spark.sql.parquet.ParquetRelation2.PartitionValues> resolvePartitions (scala.collection.Seq<org.apache.spark.sql.parquet.ParquetRelation2.PartitionValues> values) { throw new RuntimeException(); }
  /**
   * Converts a string to a <code>Literal</code> with automatic type inference.  Currently only supports
   * {@link IntegerType}, {@link LongType}, {@link FloatType}, {@link DoubleType}, {@link DecimalType.Unlimited}, and
   * {@link StringType}.
   */
  public  org.apache.spark.sql.catalyst.expressions.Literal inferPartitionColumnValue (java.lang.String raw, java.lang.String defaultPartitionName) { throw new RuntimeException(); }
  private  scala.collection.Seq<org.apache.spark.sql.types.DataType> upCastingOrder () { throw new RuntimeException(); }
  /**
   * Given a collection of {@link Literal}s, resolves possible type conflicts by up-casting "lower"
   * types.
   */
  private  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Literal> resolveTypeConflicts (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Literal> literals) { throw new RuntimeException(); }
}
