package org.apache.spark.sql.parquet;
public  class PartitionSpec implements scala.Product, scala.Serializable {
  public  org.apache.spark.sql.types.StructType partitionColumns () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.parquet.Partition> partitions () { throw new RuntimeException(); }
  // not preceding
  public   PartitionSpec (org.apache.spark.sql.types.StructType partitionColumns, scala.collection.Seq<org.apache.spark.sql.parquet.Partition> partitions) { throw new RuntimeException(); }
}
