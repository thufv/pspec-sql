package org.apache.spark.sql.catalyst.types;
public abstract class FractionalType extends org.apache.spark.sql.catalyst.types.NumericType {
  static public  boolean unapply (org.apache.spark.sql.catalyst.expressions.Expression a) { throw new RuntimeException(); }
  public   FractionalType () { throw new RuntimeException(); }
  public abstract  scala.math.Fractional<java.lang.Object> fractional () ;
}
