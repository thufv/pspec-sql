package org.apache.spark.sql.catalyst.analysis;
/**
 * Holds the name of an attribute that has yet to be resolved.
 */
public  class UnresolvedAttribute extends org.apache.spark.sql.catalyst.expressions.Attribute implements org.apache.spark.sql.catalyst.trees.LeafNode<org.apache.spark.sql.catalyst.expressions.Expression>, scala.Product, scala.Serializable {
  public  java.lang.String name () { throw new RuntimeException(); }
  // not preceding
  public   UnresolvedAttribute (java.lang.String name) { throw new RuntimeException(); }
  public  scala.Nothing exprId () { throw new RuntimeException(); }
  public  scala.Nothing dataType () { throw new RuntimeException(); }
  public  scala.Nothing nullable () { throw new RuntimeException(); }
  public  scala.Nothing qualifiers () { throw new RuntimeException(); }
  public  boolean resolved () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.analysis.UnresolvedAttribute newInstance () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.analysis.UnresolvedAttribute withNullability (boolean newNullability) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.analysis.UnresolvedAttribute withQualifiers (scala.collection.Seq<java.lang.String> newQualifiers) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Expression.EvaluatedType eval (org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
