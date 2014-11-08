package org.apache.spark.sql.catalyst.expressions;
/**
 * Adds an item to a set.
 * For performance, this expression mutates its input during evaluation.
 */
public  class AddItemToSet extends org.apache.spark.sql.catalyst.expressions.Expression implements scala.Product, scala.Serializable {
  public  org.apache.spark.sql.catalyst.expressions.Expression item () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Expression set () { throw new RuntimeException(); }
  // not preceding
  public   AddItemToSet (org.apache.spark.sql.catalyst.expressions.Expression item, org.apache.spark.sql.catalyst.expressions.Expression set) { throw new RuntimeException(); }
  public  scala.collection.immutable.List<org.apache.spark.sql.catalyst.expressions.Expression> children () { throw new RuntimeException(); }
  public  boolean nullable () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.types.DataType dataType () { throw new RuntimeException(); }
  public  Object eval (org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
