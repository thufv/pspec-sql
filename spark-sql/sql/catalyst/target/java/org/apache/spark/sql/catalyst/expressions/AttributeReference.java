package org.apache.spark.sql.catalyst.expressions;
/**
 * A reference to an attribute produced by another operator in the tree.
 * <p>
 * @param name The name of this attribute, should only be used during analysis or for debugging.
 * @param dataType The {@link DataType} of this attribute.
 * @param nullable True if null is a valid value for this attribute.
 * @param exprId A globally unique id used to check if different AttributeReferences refer to the
 *               same attribute.
 * @param qualifiers a list of strings that can be used to referred to this attribute in a fully
 *                   qualified way. Consider the examples tableName.name, subQueryAlias.name.
 *                   tableName and subQueryAlias are possible qualifiers.
 */
public  class AttributeReference extends org.apache.spark.sql.catalyst.expressions.Attribute implements org.apache.spark.sql.catalyst.trees.LeafNode<org.apache.spark.sql.catalyst.expressions.Expression>, scala.Product, scala.Serializable {
  public  java.lang.String name () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.types.DataType dataType () { throw new RuntimeException(); }
  public  boolean nullable () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.ExprId exprId () { throw new RuntimeException(); }
  public  scala.collection.Seq<java.lang.String> qualifiers () { throw new RuntimeException(); }
  // not preceding
  public   AttributeReference (java.lang.String name, org.apache.spark.sql.catalyst.types.DataType dataType, boolean nullable, org.apache.spark.sql.catalyst.expressions.ExprId exprId, scala.collection.Seq<java.lang.String> qualifiers) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.AttributeSet references () { throw new RuntimeException(); }
  public  boolean equals (Object other) { throw new RuntimeException(); }
  public  int hashCode () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.AttributeReference newInstance () { throw new RuntimeException(); }
  /**
   * Returns a copy of this {@link AttributeReference} with changed nullability.
   */
  public  org.apache.spark.sql.catalyst.expressions.AttributeReference withNullability (boolean newNullability) { throw new RuntimeException(); }
  /**
   * Returns a copy of this {@link AttributeReference} with new qualifiers.
   */
  public  org.apache.spark.sql.catalyst.expressions.AttributeReference withQualifiers (scala.collection.Seq<java.lang.String> newQualifiers) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Expression.EvaluatedType eval (org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
