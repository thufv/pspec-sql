package org.apache.spark.sql.hive.execution;
/**
 * A set of test cases expressed in Hive QL that are not covered by the tests included in the hive distribution.
 */
public  class HiveQuerySuite extends org.apache.spark.sql.hive.execution.HiveComparisonTest implements org.scalatest.BeforeAndAfter {
  public   HiveQuerySuite () { throw new RuntimeException(); }
  private  java.util.TimeZone originalTimeZone () { throw new RuntimeException(); }
  private  java.util.Locale originalLocale () { throw new RuntimeException(); }
  public  void beforeAll () { throw new RuntimeException(); }
  public  void afterAll () { throw new RuntimeException(); }
  public  java.lang.String delimiter () { throw new RuntimeException(); }
  public  boolean isExplanation (org.apache.spark.sql.DataFrame result) { throw new RuntimeException(); }
  public  class LogEntry implements scala.Product, scala.Serializable {
    public  java.lang.String filename () { throw new RuntimeException(); }
    public  java.lang.String message () { throw new RuntimeException(); }
    // not preceding
    public   LogEntry (java.lang.String filename, java.lang.String message) { throw new RuntimeException(); }
  }
  // no position
  public  class LogEntry extends scala.runtime.AbstractFunction2<java.lang.String, java.lang.String, org.apache.spark.sql.hive.execution.HiveQuerySuite.LogEntry> implements scala.Serializable {
    public   LogEntry () { throw new RuntimeException(); }
  }
  public  class LogFile implements scala.Product, scala.Serializable {
    public  java.lang.String name () { throw new RuntimeException(); }
    // not preceding
    public   LogFile (java.lang.String name) { throw new RuntimeException(); }
  }
  // no position
  public  class LogFile extends scala.runtime.AbstractFunction1<java.lang.String, org.apache.spark.sql.hive.execution.HiveQuerySuite.LogFile> implements scala.Serializable {
    public   LogFile () { throw new RuntimeException(); }
  }
}
