package org.apache.spark.sql.hive.execution;
/**
 * A set of test cases expressed in Hive QL that are not covered by the tests included in the hive distribution.
 */
public  class HiveQuerySuite extends org.apache.spark.sql.hive.execution.HiveComparisonTest {
  public   HiveQuerySuite () { throw new RuntimeException(); }
  public  boolean isExplanation (org.apache.spark.sql.SchemaRDD result) { throw new RuntimeException(); }
}
