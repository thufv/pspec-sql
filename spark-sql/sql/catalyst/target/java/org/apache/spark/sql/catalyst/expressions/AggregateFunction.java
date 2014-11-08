package org.apache.spark.sql.catalyst.expressions;
/**
 * A specific implementation of an aggregate function. Used to wrap a generic
 * {@link AggregateExpression} with an algorithm that will be used to compute one specific result.
 */
public abstract class AggregateFunction extends org.apache.spark.sql.catalyst.expressions.AggregateExpression implements scala.Serializable, org.apache.spark.sql.catalyst.trees.LeafNode<org.apache.spark.sql.catalyst.expressions.Expression> {
  public   AggregateFunction () { throw new RuntimeException(); }
  /** Base should return the generic aggregate expression that this function is computing */
  public abstract  org.apache.spark.sql.catalyst.expressions.AggregateExpression base () ;
  public  boolean nullable () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.types.DataType dataType () { throw new RuntimeException(); }
  public abstract  void update (org.apache.spark.sql.catalyst.expressions.Row input) ;
  public  org.apache.spark.sql.catalyst.expressions.AggregateFunction newInstance () { throw new RuntimeException(); }
}
