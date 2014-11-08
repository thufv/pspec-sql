package org.apache.spark.sql.hive.execution;
/**
 * Allows the creations of tests that execute the same query against both hive
 * and catalyst, comparing the results.
 * <p>
 * The "golden" results from Hive are cached in an retrieved both from the classpath and
 * {@link answerCache} to speed up testing.
 * <p>
 * See the documentation of public vals in this class for information on how test execution can be
 * configured using system properties.
 */
public abstract class HiveComparisonTest extends org.scalatest.FunSuite implements org.scalatest.BeforeAndAfterAll, org.scalatest.GivenWhenThen, org.apache.spark.Logging {
  public   HiveComparisonTest () { throw new RuntimeException(); }
  /**
   * When set, any cache files that result in test failures will be deleted.  Used when the test
   * harness or hive have been updated thus requiring new golden answers to be computed for some
   * tests. Also prevents the classpath being used when looking for golden answers as these are
   * usually stale.
   */
  public  boolean recomputeCache () { throw new RuntimeException(); }
  protected  scala.util.matching.Regex shardRegEx () { throw new RuntimeException(); }
  /**
   * Allows multiple JVMs to be run in parallel, each responsible for portion of all test cases.
   * Format <code>shardId:numShards</code>. Shard ids should be zero indexed.  E.g. -Dspark.hive.testshard=0:4.
   */
  public  scala.Option<scala.Tuple2<java.lang.Object, java.lang.Object>> shardInfo () { throw new RuntimeException(); }
  protected  java.io.File targetDir () { throw new RuntimeException(); }
  /**
   * When set, this comma separated list is defines directories that contain the names of test cases
   * that should be skipped.
   * <p>
   * For example when <code>-Dspark.hive.skiptests=passed,hiveFailed</code> is specified and test cases listed
   * in {@link passedDirectory} or {@link hiveFailedDirectory} will be skipped.
   */
  public  scala.collection.Seq<java.io.File> skipDirectories () { throw new RuntimeException(); }
  public  scala.collection.Seq<java.io.File> runOnlyDirectories () { throw new RuntimeException(); }
  /** The local directory with cached golden answer will be stored. */
  protected  java.io.File answerCache () { throw new RuntimeException(); }
  /** The {@link ClassLoader} that contains test dependencies.  Used to look for golden answers. */
  protected  java.lang.ClassLoader testClassLoader () { throw new RuntimeException(); }
  /** Directory containing a file for each test case that passes. */
  public  java.io.File passedDirectory () { throw new RuntimeException(); }
  /** Directory containing output of tests that fail to execute with Catalyst. */
  public  java.io.File failedDirectory () { throw new RuntimeException(); }
  /** Directory containing output of tests where catalyst produces the wrong answer. */
  public  java.io.File wrongDirectory () { throw new RuntimeException(); }
  /** Directory containing output of tests where we fail to generate golden output with Hive. */
  public  java.io.File hiveFailedDirectory () { throw new RuntimeException(); }
  /** All directories that contain per-query output files */
  public  scala.collection.Seq<java.io.File> outputDirectories () { throw new RuntimeException(); }
  protected  java.security.MessageDigest cacheDigest () { throw new RuntimeException(); }
  protected  java.lang.String getMd5 (java.lang.String str) { throw new RuntimeException(); }
  protected  scala.collection.Seq<java.lang.String> prepareAnswer (org.apache.spark.sql.hive.test.TestHiveContext.HiveQLQueryExecution hiveQuery, scala.collection.Seq<java.lang.String> answer) { throw new RuntimeException(); }
  public  scala.collection.Seq<java.lang.String> nonDeterministicLineIndicators () { throw new RuntimeException(); }
  protected  boolean nonDeterministicLine (java.lang.String line) { throw new RuntimeException(); }
  public  scala.collection.Seq<java.lang.String> ignoredLineIndicators () { throw new RuntimeException(); }
  protected  boolean ignoredLine (java.lang.String line) { throw new RuntimeException(); }
  /**
   * Removes non-deterministic paths from <code>str</code> so cached answers will compare correctly.
   */
  protected  java.lang.String cleanPaths (java.lang.String str) { throw new RuntimeException(); }
  public  scala.util.matching.Regex installHooksCommand () { throw new RuntimeException(); }
  public  void createQueryTest (java.lang.String testCaseName, java.lang.String sql, boolean reset) { throw new RuntimeException(); }
}
