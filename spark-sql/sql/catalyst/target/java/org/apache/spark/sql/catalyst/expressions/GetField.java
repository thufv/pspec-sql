package org.apache.spark.sql.catalyst.expressions;
/**
 * Returns the value of fields in the Struct <code>child</code>.
 */
public  class GetField extends org.apache.spark.sql.catalyst.expressions.UnaryExpression implements scala.Product, scala.Serializable {
  public  org.apache.spark.sql.catalyst.expressions.Expression child () { throw new RuntimeException(); }
  public  java.lang.String fieldName () { throw new RuntimeException(); }
  // not preceding
  public   GetField (org.apache.spark.sql.catalyst.expressions.Expression child, java.lang.String fieldName) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.types.DataType dataType () { throw new RuntimeException(); }
  public  boolean nullable () { throw new RuntimeException(); }
  public  boolean foldable () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.types.StructType structType () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.types.StructField field () { throw new RuntimeException(); }
  public  int ordinal () { throw new RuntimeException(); }
  public  boolean resolved () { throw new RuntimeException(); }
  public  Object eval (org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
