package org.apache.spark.sql.api.java;
/**
 * An RDD of {@link Row} objects that is returned as the result of a Spark SQL query.  In addition to
 * standard RDD operations, a JavaSchemaRDD can also be registered as a table in the JavaSQLContext
 * that was used to create. Registering a JavaSchemaRDD allows its contents to be queried in
 * future SQL statement.
 * <p>
 * @groupname schema SchemaRDD Functions
 * @groupprio schema -1
 * @groupname Ungrouped Base RDD Functions
 */
public  class JavaSchemaRDD implements org.apache.spark.api.java.JavaRDDLike<org.apache.spark.sql.api.java.Row, org.apache.spark.api.java.JavaRDD<org.apache.spark.sql.api.java.Row>>, org.apache.spark.sql.SchemaRDDLike {
  public  org.apache.spark.sql.SQLContext sqlContext () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan baseLogicalPlan () { throw new RuntimeException(); }
  // not preceding
  public   JavaSchemaRDD (org.apache.spark.sql.SQLContext sqlContext, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan baseLogicalPlan) { throw new RuntimeException(); }
  public  org.apache.spark.sql.SchemaRDD baseSchemaRDD () { throw new RuntimeException(); }
  public  scala.reflect.ClassTag<org.apache.spark.sql.api.java.Row> classTag () { throw new RuntimeException(); }
  public  org.apache.spark.api.java.JavaRDD<org.apache.spark.sql.api.java.Row> wrapRDD (org.apache.spark.rdd.RDD<org.apache.spark.sql.api.java.Row> rdd) { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<org.apache.spark.sql.api.java.Row> rdd () { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
  /** Returns the schema of this JavaSchemaRDD (represented by a StructType). */
  public  org.apache.spark.sql.api.java.StructType schema () { throw new RuntimeException(); }
  /** Persist this RDD with the default storage level (`MEMORY_ONLY`). */
  public  org.apache.spark.sql.api.java.JavaSchemaRDD cache () { throw new RuntimeException(); }
  /** Persist this RDD with the default storage level (`MEMORY_ONLY`). */
  public  org.apache.spark.sql.api.java.JavaSchemaRDD persist () { throw new RuntimeException(); }
  /**
   * Set this RDD's storage level to persist its values across operations after the first time
   * it is computed. This can only be used to assign a new storage level if the RDD does not
   * have a storage level set yet..
   */
  public  org.apache.spark.sql.api.java.JavaSchemaRDD persist (org.apache.spark.storage.StorageLevel newLevel) { throw new RuntimeException(); }
  /**
   * Mark the RDD as non-persistent, and remove all blocks for it from memory and disk.
   * <p>
   * @param blocking Whether to block until all blocks are deleted.
   * @return This RDD.
   */
  public  org.apache.spark.sql.api.java.JavaSchemaRDD unpersist (boolean blocking) { throw new RuntimeException(); }
  /** Assign a name to this RDD */
  public  org.apache.spark.sql.api.java.JavaSchemaRDD setName (java.lang.String name) { throw new RuntimeException(); }
  public  java.util.List<org.apache.spark.sql.api.java.Row> collect () { throw new RuntimeException(); }
  public  java.util.List<org.apache.spark.sql.api.java.Row> take (int num) { throw new RuntimeException(); }
  /**
   * Return a new RDD that is reduced into <code>numPartitions</code> partitions.
   */
  public  org.apache.spark.sql.api.java.JavaSchemaRDD coalesce (int numPartitions, boolean shuffle) { throw new RuntimeException(); }
  /**
   * Return a new RDD containing the distinct elements in this RDD.
   */
  public  org.apache.spark.sql.api.java.JavaSchemaRDD distinct () { throw new RuntimeException(); }
  /**
   * Return a new RDD containing the distinct elements in this RDD.
   */
  public  org.apache.spark.sql.api.java.JavaSchemaRDD distinct (int numPartitions) { throw new RuntimeException(); }
  /**
   * Return a new RDD containing only the elements that satisfy a predicate.
   */
  public  org.apache.spark.sql.api.java.JavaSchemaRDD filter (org.apache.spark.api.java.function.Function<org.apache.spark.sql.api.java.Row, java.lang.Boolean> f) { throw new RuntimeException(); }
  /**
   * Return the intersection of this RDD and another one. The output will not contain any
   * duplicate elements, even if the input RDDs did.
   * <p>
   * Note that this method performs a shuffle internally.
   */
  public  org.apache.spark.sql.api.java.JavaSchemaRDD intersection (org.apache.spark.sql.api.java.JavaSchemaRDD other) { throw new RuntimeException(); }
  /**
   * Return the intersection of this RDD and another one. The output will not contain any
   * duplicate elements, even if the input RDDs did.
   * <p>
   * Note that this method performs a shuffle internally.
   * <p>
   * @param partitioner Partitioner to use for the resulting RDD
   */
  public  org.apache.spark.sql.api.java.JavaSchemaRDD intersection (org.apache.spark.sql.api.java.JavaSchemaRDD other, org.apache.spark.Partitioner partitioner) { throw new RuntimeException(); }
  /**
   * Return the intersection of this RDD and another one. The output will not contain any
   * duplicate elements, even if the input RDDs did.  Performs a hash partition across the cluster
   * <p>
   * Note that this method performs a shuffle internally.
   * <p>
   * @param numPartitions How many partitions to use in the resulting RDD
   */
  public  org.apache.spark.sql.api.java.JavaSchemaRDD intersection (org.apache.spark.sql.api.java.JavaSchemaRDD other, int numPartitions) { throw new RuntimeException(); }
  /**
   * Return a new RDD that has exactly <code>numPartitions</code> partitions.
   * <p>
   * Can increase or decrease the level of parallelism in this RDD. Internally, this uses
   * a shuffle to redistribute data.
   * <p>
   * If you are decreasing the number of partitions in this RDD, consider using <code>coalesce</code>,
   * which can avoid performing a shuffle.
   */
  public  org.apache.spark.sql.api.java.JavaSchemaRDD repartition (int numPartitions) { throw new RuntimeException(); }
  /**
   * Return an RDD with the elements from <code>this</code> that are not in <code>other</code>.
   * <p>
   * Uses <code>this</code> partitioner/partition size, because even if <code>other</code> is huge, the resulting
   * RDD will be <= us.
   */
  public  org.apache.spark.sql.api.java.JavaSchemaRDD subtract (org.apache.spark.sql.api.java.JavaSchemaRDD other) { throw new RuntimeException(); }
  /**
   * Return an RDD with the elements from <code>this</code> that are not in <code>other</code>.
   */
  public  org.apache.spark.sql.api.java.JavaSchemaRDD subtract (org.apache.spark.sql.api.java.JavaSchemaRDD other, int numPartitions) { throw new RuntimeException(); }
  /**
   * Return an RDD with the elements from <code>this</code> that are not in <code>other</code>.
   */
  public  org.apache.spark.sql.api.java.JavaSchemaRDD subtract (org.apache.spark.sql.api.java.JavaSchemaRDD other, org.apache.spark.Partitioner p) { throw new RuntimeException(); }
}
