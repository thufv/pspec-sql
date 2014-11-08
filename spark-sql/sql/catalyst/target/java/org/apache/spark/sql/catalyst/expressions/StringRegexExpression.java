package org.apache.spark.sql.catalyst.expressions;
public abstract interface StringRegexExpression {
  public abstract  java.lang.String escape (java.lang.String v) ;
  public abstract  boolean matches (java.util.regex.Pattern regex, java.lang.String str) ;
  public  boolean nullable () ;
  public  org.apache.spark.sql.catalyst.types.DataType dataType () ;
  private  java.util.regex.Pattern cache () ;
  protected  java.util.regex.Pattern compile (java.lang.String str) ;
  protected  java.util.regex.Pattern pattern (java.lang.String str) ;
  public  Object eval (org.apache.spark.sql.catalyst.expressions.Row input) ;
}
