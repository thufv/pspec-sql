package org.apache.spark.sql.catalyst.plans.logical;
/**
 * Performs a physical redistribution of the data.  Used when the consumer of the query
 * result have expectations about the distribution and ordering of partitioned input data.
 */
public abstract class RedistributeData extends org.apache.spark.sql.catalyst.plans.logical.UnaryNode {
  public   RedistributeData () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () { throw new RuntimeException(); }
}
