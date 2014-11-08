package org.apache.spark.sql.catalyst.expressions;
public  class MutableLiteral extends org.apache.spark.sql.catalyst.expressions.LeafExpression implements scala.Product, scala.Serializable {
  public  Object value () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.types.DataType dataType () { throw new RuntimeException(); }
  public  boolean nullable () { throw new RuntimeException(); }
  // not preceding
  public   MutableLiteral (Object value, org.apache.spark.sql.catalyst.types.DataType dataType, boolean nullable) { throw new RuntimeException(); }
  public  void update (org.apache.spark.sql.catalyst.expressions.Expression expression, org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
  public  Object eval (org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
}
