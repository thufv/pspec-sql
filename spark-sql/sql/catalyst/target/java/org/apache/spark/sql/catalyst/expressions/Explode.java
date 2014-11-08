package org.apache.spark.sql.catalyst.expressions;
/**
 * Given an input array produces a sequence of rows for each value in the array.
 */
public  class Explode extends org.apache.spark.sql.catalyst.expressions.Generator implements org.apache.spark.sql.catalyst.trees.UnaryNode<org.apache.spark.sql.catalyst.expressions.Expression>, scala.Product, scala.Serializable {
  public  scala.collection.Seq<java.lang.String> attributeNames () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Expression child () { throw new RuntimeException(); }
  // not preceding
  public   Explode (scala.collection.Seq<java.lang.String> attributeNames, org.apache.spark.sql.catalyst.expressions.Expression child) { throw new RuntimeException(); }
  public  boolean resolved () { throw new RuntimeException(); }
  private  scala.collection.immutable.List<scala.Tuple2<org.apache.spark.sql.catalyst.types.DataType, java.lang.Object>> elementTypes () { throw new RuntimeException(); }
  protected  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> makeOutput () { throw new RuntimeException(); }
  public  scala.collection.TraversableOnce<org.apache.spark.sql.catalyst.expressions.Row> eval (org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
