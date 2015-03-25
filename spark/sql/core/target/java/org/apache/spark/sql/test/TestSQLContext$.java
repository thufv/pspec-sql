package org.apache.spark.sql.test;
// no position
/** A SQLContext that can be used for local testing. */
public  class TestSQLContext$ extends org.apache.spark.sql.SQLContext {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final TestSQLContext$ MODULE$ = null;
  public   TestSQLContext$ () { throw new RuntimeException(); }
  /** Fewer partitions to speed up testing. */
  protected  org.apache.spark.sql.SQLConf conf () { throw new RuntimeException(); }
  /**
   * Turn a logical plan into a {@link DataFrame}. This should be removed once we have an easier way to
   * construct {@link DataFrame} directly out of local data without relying on implicits.
   */
  protected  org.apache.spark.sql.DataFrame logicalPlanToSparkQuery (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
}