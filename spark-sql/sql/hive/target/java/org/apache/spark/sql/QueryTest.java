package org.apache.spark.sql;
/**
 * *** DUPLICATED FROM sql/core. ***
 * <p>
 * It is hard to have maven allow one subproject depend on another subprojects test code.
 * So, we duplicate this code here.
 */
public  class QueryTest extends org.scalatest.FunSuite {
  public   QueryTest () { throw new RuntimeException(); }
  /**
   * Runs the plan and makes sure the answer matches the expected result.
   * @param rdd the {@link SchemaRDD} to be executed
   * @param expectedAnswer the expected result, can either be an Any, Seq[Product], or Seq[ Seq[Any] ].
   */
  protected  void checkAnswer (org.apache.spark.sql.SchemaRDD rdd, Object expectedAnswer) { throw new RuntimeException(); }
}
