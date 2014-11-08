package org.apache.spark.sql.catalyst.plans.logical;
public  class InsertIntoCreatedTable extends org.apache.spark.sql.catalyst.plans.logical.UnaryNode implements scala.Product, scala.Serializable {
  public  scala.Option<java.lang.String> databaseName () { throw new RuntimeException(); }
  public  java.lang.String tableName () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan child () { throw new RuntimeException(); }
  // not preceding
  public   InsertIntoCreatedTable (scala.Option<java.lang.String> databaseName, java.lang.String tableName, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan child) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () { throw new RuntimeException(); }
}
