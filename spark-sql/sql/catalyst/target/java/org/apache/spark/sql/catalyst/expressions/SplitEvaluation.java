package org.apache.spark.sql.catalyst.expressions;
/**
 * Represents an aggregation that has been rewritten to be performed in two steps.
 * <p>
 * @param finalEvaluation an aggregate expression that evaluates to same final result as the
 *                        original aggregation.
 * @param partialEvaluations A sequence of {@link NamedExpression}s that can be computed on partial
 *                           data sets and are required to compute the <code>finalEvaluation</code>.
 */
public  class SplitEvaluation implements scala.Product, scala.Serializable {
  public  org.apache.spark.sql.catalyst.expressions.Expression finalEvaluation () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.NamedExpression> partialEvaluations () { throw new RuntimeException(); }
  // not preceding
  public   SplitEvaluation (org.apache.spark.sql.catalyst.expressions.Expression finalEvaluation, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.NamedExpression> partialEvaluations) { throw new RuntimeException(); }
}
