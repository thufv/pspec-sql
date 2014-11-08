package org.apache.spark.sql.catalyst.expressions;
public abstract class BinaryExpression extends org.apache.spark.sql.catalyst.expressions.Expression implements org.apache.spark.sql.catalyst.trees.BinaryNode<org.apache.spark.sql.catalyst.expressions.Expression> {
  public   BinaryExpression () { throw new RuntimeException(); }
  public abstract  java.lang.String symbol () ;
  public  boolean foldable () { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
