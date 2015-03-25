package org.apache.spark.sql.parquet;
/**
 * Allows creation of Parquet based tables using the syntax:
 * <pre><code>
 *   CREATE TEMPORARY TABLE ... USING org.apache.spark.sql.parquet OPTIONS (...)
 * </code></pre>
 * <p>
 * Supported options include:
 * <p>
 *  - <code>path</code>: Required. When reading Parquet files, <code>path</code> should point to the location of the
 *    Parquet file(s). It can be either a single raw Parquet file, or a directory of Parquet files.
 *    In the latter case, this data source tries to discover partitioning information if the the
 *    directory is structured in the same style of Hive partitioned tables. When writing Parquet
 *    file, <code>path</code> should point to the destination folder.
 * <p>
 *  - <code>mergeSchema</code>: Optional. Indicates whether we should merge potentially different (but
 *    compatible) schemas stored in all Parquet part-files.
 * <p>
 *  - <code>partition.defaultName</code>: Optional. Partition name used when a value of a partition column is
 *    null or empty string. This is similar to the <code>hive.exec.default.partition.name</code> configuration
 *    in Hive.
 */
public  class DefaultSource implements org.apache.spark.sql.sources.RelationProvider, org.apache.spark.sql.sources.SchemaRelationProvider, org.apache.spark.sql.sources.CreatableRelationProvider {
  public   DefaultSource () { throw new RuntimeException(); }
  private  java.lang.String checkPath (scala.collection.immutable.Map<java.lang.String, java.lang.String> parameters) { throw new RuntimeException(); }
  /** Returns a new base relation with the given parameters. */
  public  org.apache.spark.sql.sources.BaseRelation createRelation (org.apache.spark.sql.SQLContext sqlContext, scala.collection.immutable.Map<java.lang.String, java.lang.String> parameters) { throw new RuntimeException(); }
  /** Returns a new base relation with the given parameters and schema. */
  public  org.apache.spark.sql.sources.BaseRelation createRelation (org.apache.spark.sql.SQLContext sqlContext, scala.collection.immutable.Map<java.lang.String, java.lang.String> parameters, org.apache.spark.sql.types.StructType schema) { throw new RuntimeException(); }
  /** Returns a new base relation with the given parameters and save given data into it. */
  public  org.apache.spark.sql.sources.BaseRelation createRelation (org.apache.spark.sql.SQLContext sqlContext, org.apache.spark.sql.SaveMode mode, scala.collection.immutable.Map<java.lang.String, java.lang.String> parameters, org.apache.spark.sql.DataFrame data) { throw new RuntimeException(); }
}
