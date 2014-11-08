package org.apache.spark.sql.hive;
/**
 * A trait for subclasses that handle table scans.
 */
private  interface TableReader {
  public abstract  org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> makeRDDForTable (org.apache.hadoop.hive.ql.metadata.Table hiveTable) ;
  public abstract  org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> makeRDDForPartitionedTable (scala.collection.Seq<org.apache.hadoop.hive.ql.metadata.Partition> partitions) ;
}
