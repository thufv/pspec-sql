package org.apache.spark.sql.catalyst.checker;
public  class LabelPropagator {
  public   LabelPropagator () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.checker.MetaRegistry meta () { throw new RuntimeException(); }
  public  void apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  public  void propagateUnary (org.apache.spark.sql.catalyst.plans.logical.UnaryNode unary) { throw new RuntimeException(); }
  private  void propagateBinary (org.apache.spark.sql.catalyst.plans.logical.BinaryNode binary) { throw new RuntimeException(); }
  private  void resolveConditionExpression (org.apache.spark.sql.catalyst.expressions.Expression expression, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  private  void resolveProjectExpression (org.apache.spark.sql.catalyst.expressions.NamedExpression expression, org.apache.spark.sql.catalyst.plans.logical.UnaryNode unary) { throw new RuntimeException(); }
  private  void resolvePredicate (org.apache.spark.sql.catalyst.expressions.Expression predicate, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  private  org.apache.spark.sql.catalyst.checker.Label resolveTerm (org.apache.spark.sql.catalyst.expressions.Expression expression, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  private  org.apache.spark.sql.catalyst.checker.Function resolveTermFunction (org.apache.spark.sql.catalyst.expressions.Expression expression, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  private  void propogateSetLabels (org.apache.spark.sql.catalyst.plans.logical.BinaryNode binary, java.lang.String name) { throw new RuntimeException(); }
  /**
   * Default propagation, inherit down
   */
  private  void propagateDefault (org.apache.spark.sql.catalyst.plans.logical.UnaryNode unary) { throw new RuntimeException(); }
}
