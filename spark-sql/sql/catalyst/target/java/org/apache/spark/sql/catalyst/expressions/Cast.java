package org.apache.spark.sql.catalyst.expressions;
/** Cast the child expression to the target data type. */
public  class Cast extends org.apache.spark.sql.catalyst.expressions.UnaryExpression implements scala.Product, scala.Serializable {
  static public  java.lang.ThreadLocal<java.text.DateFormat> threadLocalDateFormat () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Expression child () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.types.DataType dataType () { throw new RuntimeException(); }
  // not preceding
  public   Cast (org.apache.spark.sql.catalyst.expressions.Expression child, org.apache.spark.sql.catalyst.types.DataType dataType) { throw new RuntimeException(); }
  public  boolean foldable () { throw new RuntimeException(); }
  public  boolean nullable () { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
  private <T extends java.lang.Object> Object buildCast (Object a, scala.Function1<T, java.lang.Object> func) { throw new RuntimeException(); }
  private  scala.Function1<java.lang.Object, java.lang.Object> castToString () { throw new RuntimeException(); }
  private  scala.Function1<java.lang.Object, java.lang.Object> castToBinary () { throw new RuntimeException(); }
  private  scala.Function1<java.lang.Object, java.lang.Object> castToBoolean () { throw new RuntimeException(); }
  private  scala.Function1<java.lang.Object, java.lang.Object> castToTimestamp () { throw new RuntimeException(); }
  private  java.sql.Timestamp decimalToTimestamp (scala.math.BigDecimal d) { throw new RuntimeException(); }
  private  long timestampToLong (java.sql.Timestamp ts) { throw new RuntimeException(); }
  private  double timestampToDouble (java.sql.Timestamp ts) { throw new RuntimeException(); }
  private  java.lang.String timestampToString (java.sql.Timestamp ts) { throw new RuntimeException(); }
  private  scala.Function1<java.lang.Object, java.lang.Object> castToLong () { throw new RuntimeException(); }
  private  scala.Function1<java.lang.Object, java.lang.Object> castToInt () { throw new RuntimeException(); }
  private  scala.Function1<java.lang.Object, java.lang.Object> castToShort () { throw new RuntimeException(); }
  private  scala.Function1<java.lang.Object, java.lang.Object> castToByte () { throw new RuntimeException(); }
  private  scala.Function1<java.lang.Object, java.lang.Object> castToDecimal () { throw new RuntimeException(); }
  private  scala.Function1<java.lang.Object, java.lang.Object> castToDouble () { throw new RuntimeException(); }
  private  scala.Function1<java.lang.Object, java.lang.Object> castToFloat () { throw new RuntimeException(); }
  private  scala.Function1<java.lang.Object, java.lang.Object> cast () { throw new RuntimeException(); }
  public  Object eval (org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
}
