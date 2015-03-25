package org.apache.spark.sql.parquet;
public  class ParquetSchemaSuite extends org.scalatest.FunSuite implements org.apache.spark.sql.parquet.ParquetTest {
  public   ParquetSchemaSuite () { throw new RuntimeException(); }
  public  org.apache.spark.sql.test.TestSQLContext$ sqlContext () { throw new RuntimeException(); }
  /**
   * Checks whether the reflected Parquet message type for product type <code>T</code> conforms <code>messageType</code>.
   */
  private <T extends scala.Product> void testSchema (java.lang.String testName, java.lang.String messageType, boolean isThriftDerived, scala.reflect.ClassTag<T> evidence$1, scala.reflect.api.TypeTags.TypeTag<T> evidence$2) { throw new RuntimeException(); }
}
