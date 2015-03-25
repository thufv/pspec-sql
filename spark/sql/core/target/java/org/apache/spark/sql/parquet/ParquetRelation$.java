package org.apache.spark.sql.parquet;
// no position
/** Attributes */
public  class ParquetRelation$ implements scala.Serializable {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final ParquetRelation$ MODULE$ = null;
  public   ParquetRelation$ () { throw new RuntimeException(); }
  public  void enableLogForwarding () { throw new RuntimeException(); }
  public  scala.collection.immutable.Map<java.lang.String, parquet.hadoop.metadata.CompressionCodecName> shortParquetCompressionCodecNames () { throw new RuntimeException(); }
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
  public  org.apache.spark.sql.parquet.ParquetRelation create (java.lang.String pathString, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan child, org.apache.hadoop.conf.Configuration conf, org.apache.spark.sql.SQLContext sqlContext) { throw new RuntimeException(); }
  /**
   * Creates an empty ParquetRelation and underlying Parquetfile that only
   * consists of the Metadata for the given schema.
   * <p>
   * @param pathString The directory the Parquetfile will be stored in.
   * @param attributes The schema of the relation.
   * @param conf A configuration to be used.
   * @return An empty ParquetRelation.
   */
  public  org.apache.spark.sql.parquet.ParquetRelation createEmpty (java.lang.String pathString, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> attributes, boolean allowExisting, org.apache.hadoop.conf.Configuration conf, org.apache.spark.sql.SQLContext sqlContext) { throw new RuntimeException(); }
  private  org.apache.hadoop.fs.Path checkPath (java.lang.String pathStr, boolean allowExisting, org.apache.hadoop.conf.Configuration conf) { throw new RuntimeException(); }
}
