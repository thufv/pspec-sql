package org.apache.spark.sql.hive;
/**
 * A trait for subclasses that handle table scans.
 */
public  interface TableReader {
  public  org.apache.spark.rdd.RDD<org.apache.spark.sql.Row> makeRDDForTable (org.apache.hadoop.hive.ql.metadata.Table hiveTable) ;
  public  org.apache.spark.rdd.RDD<org.apache.spark.sql.Row> makeRDDForPartitionedTable (scala.collection.Seq<org.apache.hadoop.hive.ql.metadata.Partition> partitions) ;
}
