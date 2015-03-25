package org.apache.spark.sql.hive.execution;
/**
 * A framework for running the query tests that are listed as a set of text files.
 * <p>
 * TestSuites that derive from this class must provide a map of testCaseName -> testCaseFiles that should be included.
 * Additionally, there is support for whitelisting and blacklisting tests as development progresses.
 */
public abstract class HiveQueryFileTest extends org.apache.spark.sql.hive.execution.HiveComparisonTest {
  public   HiveQueryFileTest () { throw new RuntimeException(); }
  /** A list of tests deemed out of scope and thus completely disregarded */
  public  scala.collection.Seq<java.lang.String> blackList () { throw new RuntimeException(); }
  /**
   * The set of tests that are believed to be working in catalyst. Tests not in whiteList
   * blacklist are implicitly marked as ignored.
   */
  public  scala.collection.Seq<java.lang.String> whiteList () { throw new RuntimeException(); }
  public abstract  scala.collection.Seq<scala.Tuple2<java.lang.String, java.io.File>> testCases () ;
  public  boolean runAll () { throw new RuntimeException(); }
  public  java.lang.String whiteListProperty () { throw new RuntimeException(); }
  public  scala.collection.Seq<java.lang.String> realWhiteList () { throw new RuntimeException(); }
}
