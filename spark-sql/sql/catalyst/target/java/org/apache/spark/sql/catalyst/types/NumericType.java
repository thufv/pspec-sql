package org.apache.spark.sql.catalyst.types;
public abstract class NumericType extends org.apache.spark.sql.catalyst.types.NativeType implements org.apache.spark.sql.catalyst.types.PrimitiveType {
  static public  boolean unapply (org.apache.spark.sql.catalyst.expressions.Expression e) { throw new RuntimeException(); }
  public   NumericType () { throw new RuntimeException(); }
  public abstract  scala.math.Numeric<java.lang.Object> numeric () ;
}
