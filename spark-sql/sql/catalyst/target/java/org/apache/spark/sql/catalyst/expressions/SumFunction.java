package org.apache.spark.sql.catalyst.expressions;
public  class SumFunction extends org.apache.spark.sql.catalyst.expressions.AggregateFunction implements scala.Product, scala.Serializable {
  public  org.apache.spark.sql.catalyst.expressions.Expression expr () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.AggregateExpression base () { throw new RuntimeException(); }
  // not preceding
  public   SumFunction (org.apache.spark.sql.catalyst.expressions.Expression expr, org.apache.spark.sql.catalyst.expressions.AggregateExpression base) { throw new RuntimeException(); }
  public   SumFunction () { throw new RuntimeException(); }
  private  org.apache.spark.sql.catalyst.expressions.Cast zero () { throw new RuntimeException(); }
  private  org.apache.spark.sql.catalyst.expressions.MutableLiteral sum () { throw new RuntimeException(); }
  private  org.apache.spark.sql.catalyst.expressions.Add addFunction () { throw new RuntimeException(); }
  public  void update (org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
  public  Object eval (org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
}
