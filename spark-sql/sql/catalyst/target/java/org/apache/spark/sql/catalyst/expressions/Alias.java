package org.apache.spark.sql.catalyst.expressions;
/**
 * Used to assign a new name to a computation.
 * For example the SQL expression "1 + 1 AS a" could be represented as follows:
 *  Alias(Add(Literal(1), Literal(1), "a")()
 * <p>
 * @param child the computation being performed
 * @param name the name to be associated with the result of computing {@link child}.
 * @param exprId A globally unique id used to check if an {@link AttributeReference} refers to this
 *               alias. Auto-assigned if left blank.
 */
public  class Alias extends org.apache.spark.sql.catalyst.expressions.NamedExpression implements org.apache.spark.sql.catalyst.trees.UnaryNode<org.apache.spark.sql.catalyst.expressions.Expression>, scala.Product, scala.Serializable {
  public  org.apache.spark.sql.catalyst.expressions.Expression child () { throw new RuntimeException(); }
  public  java.lang.String name () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.ExprId exprId () { throw new RuntimeException(); }
  public  scala.collection.Seq<java.lang.String> qualifiers () { throw new RuntimeException(); }
  // not preceding
  public   Alias (org.apache.spark.sql.catalyst.expressions.Expression child, java.lang.String name, org.apache.spark.sql.catalyst.expressions.ExprId exprId, scala.collection.Seq<java.lang.String> qualifiers) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Expression.EvaluatedType eval (org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.types.DataType dataType () { throw new RuntimeException(); }
  public  boolean nullable () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Attribute toAttribute () { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
  protected final  scala.collection.immutable.List<scala.Equals> otherCopyArgs () { throw new RuntimeException(); }
}
