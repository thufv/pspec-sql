package org.apache.spark.sql.catalyst.analysis;
/**
 * Represents all of the input attributes to a given relational operator, for example in
 * "SELECT * FROM ...".
 * <p>
 * @param table an optional table that should be the target of the expansion.  If omitted all
 *              tables' columns are produced.
 */
public  class Star extends org.apache.spark.sql.catalyst.expressions.Attribute implements org.apache.spark.sql.catalyst.trees.LeafNode<org.apache.spark.sql.catalyst.expressions.Expression>, scala.Product, scala.Serializable {
  public  scala.Option<java.lang.String> table () { throw new RuntimeException(); }
  public  scala.Function1<org.apache.spark.sql.catalyst.expressions.Attribute, org.apache.spark.sql.catalyst.expressions.Expression> mapFunction () { throw new RuntimeException(); }
  // not preceding
  public   Star (scala.Option<java.lang.String> table, scala.Function1<org.apache.spark.sql.catalyst.expressions.Attribute, org.apache.spark.sql.catalyst.expressions.Expression> mapFunction) { throw new RuntimeException(); }
  public  scala.Nothing name () { throw new RuntimeException(); }
  public  scala.Nothing exprId () { throw new RuntimeException(); }
  public  scala.Nothing dataType () { throw new RuntimeException(); }
  public  scala.Nothing nullable () { throw new RuntimeException(); }
  public  scala.Nothing qualifiers () { throw new RuntimeException(); }
  public  boolean resolved () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.analysis.Star newInstance () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.analysis.Star withNullability (boolean newNullability) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.analysis.Star withQualifiers (scala.collection.Seq<java.lang.String> newQualifiers) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.NamedExpression> expand (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> input) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Expression.EvaluatedType eval (org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
