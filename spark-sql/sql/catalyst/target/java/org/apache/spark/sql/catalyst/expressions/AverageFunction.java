package org.apache.spark.sql.catalyst.expressions;
public  class AverageFunction extends org.apache.spark.sql.catalyst.expressions.AggregateFunction implements scala.Product, scala.Serializable {
  public  org.apache.spark.sql.catalyst.expressions.Expression expr () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.AggregateExpression base () { throw new RuntimeException(); }
  // not preceding
  public   AverageFunction (org.apache.spark.sql.catalyst.expressions.Expression expr, org.apache.spark.sql.catalyst.expressions.AggregateExpression base) { throw new RuntimeException(); }
  public   AverageFunction () { throw new RuntimeException(); }
  private  org.apache.spark.sql.catalyst.expressions.Cast zero () { throw new RuntimeException(); }
  private  long count () { throw new RuntimeException(); }
  private  org.apache.spark.sql.catalyst.expressions.MutableLiteral sum () { throw new RuntimeException(); }
  private  org.apache.spark.sql.catalyst.expressions.Cast sumAsDouble () { throw new RuntimeException(); }
  private  org.apache.spark.sql.catalyst.expressions.Add addFunction (Object value) { throw new RuntimeException(); }
  public  Object eval (org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
  public  void update (org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
}
