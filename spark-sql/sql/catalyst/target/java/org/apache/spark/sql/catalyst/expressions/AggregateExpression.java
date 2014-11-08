package org.apache.spark.sql.catalyst.expressions;
public abstract class AggregateExpression extends org.apache.spark.sql.catalyst.expressions.Expression {
  public   AggregateExpression () { throw new RuntimeException(); }
  /**
   * Creates a new instance that can be used to compute this aggregate expression for a group
   * of input rows/
   */
  public abstract  org.apache.spark.sql.catalyst.expressions.AggregateFunction newInstance () ;
  /**
   * {@link AggregateExpression.eval} should never be invoked because {@link AggregateExpression}'s are
   * replaced with a physical aggregate operator at runtime.
   */
  public  org.apache.spark.sql.catalyst.expressions.Expression.EvaluatedType eval (org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
}
