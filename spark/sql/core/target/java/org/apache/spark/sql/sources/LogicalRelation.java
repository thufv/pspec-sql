package org.apache.spark.sql.sources;
/**
 * Used to link a {@link BaseRelation} in to a logical query plan.
 */
public  class LogicalRelation extends org.apache.spark.sql.catalyst.plans.logical.LeafNode implements org.apache.spark.sql.catalyst.analysis.MultiInstanceRelation, scala.Product, scala.Serializable {
  public  org.apache.spark.sql.sources.BaseRelation relation () { throw new RuntimeException(); }
  // not preceding
  public   LogicalRelation (org.apache.spark.sql.sources.BaseRelation relation) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.AttributeReference> output () { throw new RuntimeException(); }
  public  boolean equals (Object other) { throw new RuntimeException(); }
  public  boolean sameResult (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan otherPlan) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.plans.logical.Statistics statistics () { throw new RuntimeException(); }
  /** Used to lookup original attribute capitalization */
  public  org.apache.spark.sql.catalyst.expressions.AttributeMap<org.apache.spark.sql.catalyst.expressions.AttributeReference> attributeMap () { throw new RuntimeException(); }
  public  org.apache.spark.sql.sources.LogicalRelation newInstance () { throw new RuntimeException(); }
  public  java.lang.String simpleString () { throw new RuntimeException(); }
}
