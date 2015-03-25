package org.apache.spark.sql.hive.execution;
/**
 * A collection of hive query tests where we generate the answers ourselves instead of depending on
 * Hive to generate them (in contrast to HiveQuerySuite).  Often this is because the query is
 * valid, but Hive currently cannot execute it.
 */
public  class SQLQuerySuite extends org.apache.spark.sql.QueryTest {
  public   SQLQuerySuite () { throw new RuntimeException(); }
}
