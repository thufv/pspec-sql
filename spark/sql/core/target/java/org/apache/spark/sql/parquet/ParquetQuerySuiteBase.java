package org.apache.spark.sql.parquet;
/**
 * A test suite that tests various Parquet queries.
 */
public  class ParquetQuerySuiteBase extends org.apache.spark.sql.QueryTest implements org.apache.spark.sql.parquet.ParquetTest {
  public   ParquetQuerySuiteBase () { throw new RuntimeException(); }
  public  org.apache.spark.sql.test.TestSQLContext$ sqlContext () { throw new RuntimeException(); }
}
