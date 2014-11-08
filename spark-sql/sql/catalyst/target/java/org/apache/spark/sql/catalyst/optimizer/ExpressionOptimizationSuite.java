package org.apache.spark.sql.catalyst.optimizer;
/**
 * Overrides our expression evaluation tests and reruns them after optimization has occured.  This
 * is to ensure that constant folding and other optimizations do not break anything.
 */
public  class ExpressionOptimizationSuite extends org.apache.spark.sql.catalyst.expressions.ExpressionEvaluationSuite {
  public   ExpressionOptimizationSuite () { throw new RuntimeException(); }
  public  void checkEvaluation (org.apache.spark.sql.catalyst.expressions.Expression expression, Object expected, org.apache.spark.sql.catalyst.expressions.Row inputRow) { throw new RuntimeException(); }
}
