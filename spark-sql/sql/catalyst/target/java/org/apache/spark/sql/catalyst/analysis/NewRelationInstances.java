package org.apache.spark.sql.catalyst.analysis;
// no position
/**
 * If any MultiInstanceRelation appears more than once in the query plan then the plan is updated so
 * that each instance has unique expression ids for the attributes produced.
 */
public  class NewRelationInstances extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
  static public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
}
