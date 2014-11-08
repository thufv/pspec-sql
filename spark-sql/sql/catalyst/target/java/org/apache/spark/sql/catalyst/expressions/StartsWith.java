package org.apache.spark.sql.catalyst.expressions;
/**
 * A function that returns true if the string <code>left</code> starts with the string <code>right</code>.
 */
public  class StartsWith extends org.apache.spark.sql.catalyst.expressions.BinaryExpression implements org.apache.spark.sql.catalyst.expressions.StringComparison, scala.Product, scala.Serializable {
  public  org.apache.spark.sql.catalyst.expressions.Expression left () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Expression right () { throw new RuntimeException(); }
  // not preceding
  public   StartsWith (org.apache.spark.sql.catalyst.expressions.Expression left, org.apache.spark.sql.catalyst.expressions.Expression right) { throw new RuntimeException(); }
  public  boolean compare (java.lang.String l, java.lang.String r) { throw new RuntimeException(); }
}
