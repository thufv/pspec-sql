package org.apache.spark.sql.catalyst.expressions;
public abstract class NamedExpression extends org.apache.spark.sql.catalyst.expressions.Expression {
  static private  java.util.concurrent.atomic.AtomicLong curId () { throw new RuntimeException(); }
  static public  org.apache.spark.sql.catalyst.expressions.ExprId newExprId () { throw new RuntimeException(); }
  public   NamedExpression () { throw new RuntimeException(); }
  public abstract  java.lang.String name () ;
  public abstract  org.apache.spark.sql.catalyst.expressions.ExprId exprId () ;
  public abstract  scala.collection.Seq<java.lang.String> qualifiers () ;
  public abstract  org.apache.spark.sql.catalyst.expressions.Attribute toAttribute () ;
  protected  java.lang.String typeSuffix () { throw new RuntimeException(); }
}
