package org.apache.spark.sql.parquet;
/**
 * Relation that consists of data stored in a Parquet columnar format.
 * <p>
 * Users should interact with parquet files though a SchemaRDD, created by a {@link SQLContext} instead
 * of using this class directly.
 * <p>
 * <pre><code>
 *   val parquetRDD = sqlContext.parquetFile("path/to/parquet.file")
 * </code></pre>
 * <p>
 * @param path The path to the Parquet file.
 */
private  class ParquetRelation extends org.apache.spark.sql.catalyst.plans.logical.LeafNode implements org.apache.spark.sql.catalyst.analysis.MultiInstanceRelation, scala.Product, scala.Serializable {
  static public  void enableLogForwarding () { throw new RuntimeException(); }
  static public  scala.collection.immutable.Map<java.lang.String, parquet.hadoop.metadata.CompressionCodecName> shortParquetCompressionCodecNames () { throw new RuntimeException(); }
  /**
   * Creates a new ParquetRelation and underlying Parquetfile for the given LogicalPlan. Note that
   * this is used inside {@link org.apache.spark.sql.execution.SparkStrategies SparkStrategies} to
   * create a resolved relation as a data sink for writing to a Parquetfile. The relation is empty
   * but is initialized with ParquetMetadata and can be inserted into.
   * <p>
   * @param pathString The directory the Parquetfile will be stored in.
   * @param child The child node that will be used for extracting the schema.
   * @param conf A configuration to be used.
   * @return An empty ParquetRelation with inferred metadata.
   */
  static public  org.apache.spark.sql.parquet.ParquetRelation create (java.lang.String pathString, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan child, org.apache.hadoop.conf.Configuration conf, org.apache.spark.sql.SQLContext sqlContext) { throw new RuntimeException(); }
  /**
   * Creates an empty ParquetRelation and underlying Parquetfile that only
   * consists of the Metadata for the given schema.
   * <p>
   * @param pathString The directory the Parquetfile will be stored in.
   * @param attributes The schema of the relation.
   * @param conf A configuration to be used.
   * @return An empty ParquetRelation.
   */
  static public  org.apache.spark.sql.parquet.ParquetRelation createEmpty (java.lang.String pathString, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> attributes, boolean allowExisting, org.apache.hadoop.conf.Configuration conf, org.apache.spark.sql.SQLContext sqlContext) { throw new RuntimeException(); }
  static private  org.apache.hadoop.fs.Path checkPath (java.lang.String pathStr, boolean allowExisting, org.apache.hadoop.conf.Configuration conf) { throw new RuntimeException(); }
  public  java.lang.String path () { throw new RuntimeException(); }
  public  scala.Option<org.apache.hadoop.conf.Configuration> conf () { throw new RuntimeException(); }
  public  org.apache.spark.sql.SQLContext sqlContext () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> partitioningAttributes () { throw new RuntimeException(); }
  // not preceding
  public   ParquetRelation (java.lang.String path, scala.Option<org.apache.hadoop.conf.Configuration> conf, org.apache.spark.sql.SQLContext sqlContext, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> partitioningAttributes) { throw new RuntimeException(); }
  /** Schema derived from ParquetFile */
  public  parquet.schema.MessageType parquetSchema () { throw new RuntimeException(); }
  /** Attributes */
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () { throw new RuntimeException(); }
  public  org.apache.spark.sql.parquet.ParquetRelation newInstance () { throw new RuntimeException(); }
  public  boolean equals (Object other) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan.Statistics statistics () { throw new RuntimeException(); }
}
