package org.apache.spark.sql.catalyst.expressions;
/**
 * Creates a new set of the specified type
 */
public  class NewSet extends org.apache.spark.sql.catalyst.expressions.LeafExpression implements scala.Product, scala.Serializable {
  public  org.apache.spark.sql.catalyst.types.DataType elementType () { throw new RuntimeException(); }
  // not preceding
  public   NewSet (org.apache.spark.sql.catalyst.types.DataType elementType) { throw new RuntimeException(); }
  public  boolean nullable () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.types.ArrayType dataType () { throw new RuntimeException(); }
  public  Object eval (org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
