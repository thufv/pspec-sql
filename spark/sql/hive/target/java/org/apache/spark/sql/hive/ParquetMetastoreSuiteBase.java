package org.apache.spark.sql.hive;
/**
 * A suite to test the automatic conversion of metastore tables with parquet data to use the
 * built in parquet support.
 */
public  class ParquetMetastoreSuiteBase extends org.apache.spark.sql.hive.ParquetPartitioningTest {
  public   ParquetMetastoreSuiteBase () { throw new RuntimeException(); }
  public  void beforeAll () { throw new RuntimeException(); }
  public  void afterAll () { throw new RuntimeException(); }
}
