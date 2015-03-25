package org.apache.spark.sql.hive;
/**
 * A collection of tests for parquet data with various forms of partitioning.
 */
public abstract class ParquetPartitioningTest extends org.apache.spark.sql.QueryTest implements org.scalatest.BeforeAndAfterAll {
  public   ParquetPartitioningTest () { throw new RuntimeException(); }
  public  java.io.File partitionedTableDir () { throw new RuntimeException(); }
  public  java.io.File normalTableDir () { throw new RuntimeException(); }
  public  java.io.File partitionedTableDirWithKey () { throw new RuntimeException(); }
  public  java.io.File partitionedTableDirWithComplexTypes () { throw new RuntimeException(); }
  public  java.io.File partitionedTableDirWithKeyAndComplexTypes () { throw new RuntimeException(); }
  public  void beforeAll () { throw new RuntimeException(); }
  protected  void afterAll () { throw new RuntimeException(); }
}
