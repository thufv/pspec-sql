package org.apache.spark.sql.hive;
public  class CachedTableSuite extends org.apache.spark.sql.QueryTest {
  public   CachedTableSuite () { throw new RuntimeException(); }
  /**
   * Throws a test failed exception when the number of cached tables differs from the expected
   * number.
   */
  public  void assertCached (org.apache.spark.sql.DataFrame query, int numCachedTables) { throw new RuntimeException(); }
  public  int rddIdOf (java.lang.String tableName) { throw new RuntimeException(); }
  public  boolean isMaterialized (int rddId) { throw new RuntimeException(); }
}
