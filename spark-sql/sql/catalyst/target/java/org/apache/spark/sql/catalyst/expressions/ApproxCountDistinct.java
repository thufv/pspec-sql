package org.apache.spark.sql.catalyst.expressions;
public  class ApproxCountDistinct extends org.apache.spark.sql.catalyst.expressions.PartialAggregate implements org.apache.spark.sql.catalyst.trees.UnaryNode<org.apache.spark.sql.catalyst.expressions.Expression>, scala.Product, scala.Serializable {
  public  org.apache.spark.sql.catalyst.expressions.Expression child () { throw new RuntimeException(); }
  public  double relativeSD () { throw new RuntimeException(); }
  // not preceding
  public   ApproxCountDistinct (org.apache.spark.sql.catalyst.expressions.Expression child, double relativeSD) { throw new RuntimeException(); }
  public  boolean nullable () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.types.LongType$ dataType () { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.SplitEvaluation asPartial () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.CountDistinctFunction newInstance () { throw new RuntimeException(); }
}
