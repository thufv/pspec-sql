package org.apache.spark.sql.sources;
/**
 * A node used to support CTAS statements and saveAsTable for the data source API.
 * This node is a {@link UnaryNode} instead of a {@link Command} because we want the analyzer
 * can analyze the logical plan that will be used to populate the table.
 * So, {@link PreWriteCheck} can detect cases that are not allowed.
 */
public  class CreateTableUsingAsSelect extends org.apache.spark.sql.catalyst.plans.logical.UnaryNode implements scala.Product, scala.Serializable {
  public  java.lang.String tableName () { throw new RuntimeException(); }
  public  java.lang.String provider () { throw new RuntimeException(); }
  public  boolean temporary () { throw new RuntimeException(); }
  public  org.apache.spark.sql.SaveMode mode () { throw new RuntimeException(); }
  public  scala.collection.immutable.Map<java.lang.String, java.lang.String> options () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan child () { throw new RuntimeException(); }
  // not preceding
  public   CreateTableUsingAsSelect (java.lang.String tableName, java.lang.String provider, boolean temporary, org.apache.spark.sql.SaveMode mode, scala.collection.immutable.Map<java.lang.String, java.lang.String> options, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan child) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () { throw new RuntimeException(); }
}
