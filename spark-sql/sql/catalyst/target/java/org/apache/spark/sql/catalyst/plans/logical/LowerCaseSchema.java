package org.apache.spark.sql.catalyst.plans.logical;
/**
 * Converts the schema of <code>child</code> to all lowercase, together with LowercaseAttributeReferences
 * this allows for optional case insensitive attribute resolution.  This node can be elided after
 * analysis.
 */
public  class LowerCaseSchema extends org.apache.spark.sql.catalyst.plans.logical.UnaryNode implements scala.Product, scala.Serializable {
  public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan child () { throw new RuntimeException(); }
  // not preceding
  public   LowerCaseSchema (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan child) { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.types.DataType lowerCaseSchema (org.apache.spark.sql.catalyst.types.DataType dataType) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () { throw new RuntimeException(); }
}
