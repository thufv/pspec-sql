package org.apache.spark.sql.catalyst.expressions;
/** A base trait for functions that compare two strings, returning a boolean. */
public abstract interface StringComparison {
  public  boolean nullable () ;
  public  org.apache.spark.sql.catalyst.types.DataType dataType () ;
  public abstract  boolean compare (java.lang.String l, java.lang.String r) ;
  public  Object eval (org.apache.spark.sql.catalyst.expressions.Row input) ;
  public  java.lang.String symbol () ;
  public  java.lang.String toString () ;
}
