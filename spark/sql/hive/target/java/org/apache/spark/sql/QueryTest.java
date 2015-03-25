package org.apache.spark.sql;
/**
 * *** DUPLICATED FROM sql/core. ***
 * <p>
 * It is hard to have maven allow one subproject depend on another subprojects test code.
 * So, we duplicate this code here.
 */
public  class QueryTest extends org.apache.spark.sql.catalyst.plans.PlanTest {
  public   QueryTest () { throw new RuntimeException(); }
  /**
   * Runs the plan and makes sure the answer contains all of the keywords, or the
   * none of keywords are listed in the answer
   * @param rdd the {@link DataFrame} to be executed
   * @param exists true for make sure the keywords are listed in the output, otherwise
   *               to make sure none of the keyword are not listed in the output
   * @param keywords keyword in string array
   */
  public  void checkExistence (org.apache.spark.sql.DataFrame rdd, boolean exists, scala.collection.Seq<java.lang.String> keywords) { throw new RuntimeException(); }
  /**
   * Runs the plan and makes sure the answer matches the expected result.
   * @param rdd the {@link DataFrame} to be executed
   * @param expectedAnswer the expected result in a {@link Seq} of {@link Row}s.
   */
  protected  void checkAnswer (org.apache.spark.sql.DataFrame rdd, scala.collection.Seq<org.apache.spark.sql.Row> expectedAnswer) { throw new RuntimeException(); }
  protected  void checkAnswer (org.apache.spark.sql.DataFrame rdd, org.apache.spark.sql.Row expectedAnswer) { throw new RuntimeException(); }
  public  void sqlTest (java.lang.String sqlString, scala.collection.Seq<org.apache.spark.sql.Row> expectedAnswer, org.apache.spark.sql.SQLContext sqlContext) { throw new RuntimeException(); }
}
