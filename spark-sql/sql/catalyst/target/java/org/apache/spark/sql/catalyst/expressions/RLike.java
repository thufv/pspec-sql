package org.apache.spark.sql.catalyst.expressions;
public  class RLike extends org.apache.spark.sql.catalyst.expressions.BinaryExpression implements org.apache.spark.sql.catalyst.expressions.StringRegexExpression, scala.Product, scala.Serializable {
  public  org.apache.spark.sql.catalyst.expressions.Expression left () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Expression right () { throw new RuntimeException(); }
  // not preceding
  public   RLike (org.apache.spark.sql.catalyst.expressions.Expression left, org.apache.spark.sql.catalyst.expressions.Expression right) { throw new RuntimeException(); }
  public  java.lang.String symbol () { throw new RuntimeException(); }
  public  java.lang.String escape (java.lang.String v) { throw new RuntimeException(); }
  public  boolean matches (java.util.regex.Pattern regex, java.lang.String str) { throw new RuntimeException(); }
}
