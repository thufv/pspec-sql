package org.apache.spark.sql.catalyst.plans;
/**
 * *** DUPLICATED FROM sql/catalyst/plans. ***
 * <p>
 * It is hard to have maven allow one subproject depend on another subprojects test code.
 * So, we duplicate this code here.
 */
public  class PlanTest extends org.scalatest.FunSuite {
  public   PlanTest () { throw new RuntimeException(); }
  /**
   * Since attribute references are given globally unique ids during analysis,
   * we must normalize them to check if two different queries are identical.
   */
  protected  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan normalizeExprIds (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  /** Fails the test if the two plans do not match */
  protected  void comparePlans (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan1, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan2) { throw new RuntimeException(); }
}
