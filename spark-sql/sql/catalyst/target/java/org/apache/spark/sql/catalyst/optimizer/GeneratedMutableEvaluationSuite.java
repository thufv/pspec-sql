package org.apache.spark.sql.catalyst.optimizer;
/**
 * Overrides our expression evaluation tests to use generated code on mutable rows.
 */
public  class GeneratedMutableEvaluationSuite extends org.apache.spark.sql.catalyst.expressions.ExpressionEvaluationSuite {
  public   GeneratedMutableEvaluationSuite () { throw new RuntimeException(); }
  public  void checkEvaluation (org.apache.spark.sql.catalyst.expressions.Expression expression, Object expected, org.apache.spark.sql.catalyst.expressions.Row inputRow) { throw new RuntimeException(); }
}
