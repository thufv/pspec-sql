package org.apache.spark.sql.catalyst.expressions;
/**
 * A function that takes a substring of its first argument starting at a given position.
 * Defined for String and Binary types.
 */
public  class Substring extends org.apache.spark.sql.catalyst.expressions.Expression implements scala.Product, scala.Serializable {
  public  org.apache.spark.sql.catalyst.expressions.Expression str () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Expression pos () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Expression len () { throw new RuntimeException(); }
  // not preceding
  public   Substring (org.apache.spark.sql.catalyst.expressions.Expression str, org.apache.spark.sql.catalyst.expressions.Expression pos, org.apache.spark.sql.catalyst.expressions.Expression len) { throw new RuntimeException(); }
  public  boolean foldable () { throw new RuntimeException(); }
  public  boolean nullable () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.types.DataType dataType () { throw new RuntimeException(); }
  public  scala.collection.immutable.List<org.apache.spark.sql.catalyst.expressions.Expression> children () { throw new RuntimeException(); }
  public <T extends java.lang.Object, C extends java.lang.Object> Object slice (C str, int startPos, int sliceLen, scala.Function1<C, scala.collection.IndexedSeqOptimized<T, ?>> ev) { throw new RuntimeException(); }
  public  Object eval (org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
