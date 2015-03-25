package org.apache.spark.sql.hive.execution;
/**
 * A set of test cases that validate partition and column pruning.
 */
public  class PruningSuite extends org.apache.spark.sql.hive.execution.HiveComparisonTest implements org.scalatest.BeforeAndAfter {
  public   PruningSuite () { throw new RuntimeException(); }
  public  void createPruningTest (java.lang.String testCaseName, java.lang.String sql, scala.collection.Seq<java.lang.String> expectedOutputColumns, scala.collection.Seq<java.lang.String> expectedScannedColumns, scala.collection.Seq<scala.collection.Seq<java.lang.String>> expectedPartValues) { throw new RuntimeException(); }
}
