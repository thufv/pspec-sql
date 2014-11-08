package org.apache.spark.sql;
public  class QueryTest extends org.apache.spark.sql.catalyst.plans.PlanTest {
  public   QueryTest () { throw new RuntimeException(); }
  /**
   * Runs the plan and makes sure the answer matches the expected result.
   * @param rdd the {@link SchemaRDD} to be executed
   * @param expectedAnswer the expected result, can either be an Any, Seq[Product], or Seq[ Seq[Any] ].
   */
  protected  void checkAnswer (org.apache.spark.sql.SchemaRDD rdd, Object expectedAnswer) { throw new RuntimeException(); }
}
