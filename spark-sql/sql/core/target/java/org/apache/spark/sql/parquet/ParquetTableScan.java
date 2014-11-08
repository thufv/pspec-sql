package org.apache.spark.sql.parquet;
/**
 * Parquet table scan operator. Imports the file that backs the given
 * {@link org.apache.spark.sql.parquet.ParquetRelation} as a <code></code>RDD[Row]<code></code>.
 */
public  class ParquetTableScan extends org.apache.spark.sql.execution.SparkPlan implements org.apache.spark.sql.execution.LeafNode, scala.Product, scala.Serializable {
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> attributes () { throw new RuntimeException(); }
  public  org.apache.spark.sql.parquet.ParquetRelation relation () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> columnPruningPred () { throw new RuntimeException(); }
  // not preceding
  public   ParquetTableScan (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> attributes, org.apache.spark.sql.parquet.ParquetRelation relation, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> columnPruningPred) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> normalOutput () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> partOutput () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> execute () { throw new RuntimeException(); }
  public  org.apache.spark.sql.parquet.ParquetTableScan pruneColumns (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> prunedAttributes) { throw new RuntimeException(); }
  /**
   * Evaluates a candidate projection by checking whether the candidate is a subtype
   * of the original type.
   * <p>
   * @param projection The candidate projection.
   * @return True if the projection is valid, false otherwise.
   */
  private  boolean validateProjection (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> projection) { throw new RuntimeException(); }
}
