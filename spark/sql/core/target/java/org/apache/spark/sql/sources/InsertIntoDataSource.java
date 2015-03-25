package org.apache.spark.sql.sources;
public  class InsertIntoDataSource extends org.apache.spark.sql.catalyst.plans.logical.Command implements org.apache.spark.sql.execution.RunnableCommand, scala.Product, scala.Serializable {
  public  org.apache.spark.sql.sources.LogicalRelation logicalRelation () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan query () { throw new RuntimeException(); }
  public  boolean overwrite () { throw new RuntimeException(); }
  // not preceding
  public   InsertIntoDataSource (org.apache.spark.sql.sources.LogicalRelation logicalRelation, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan query, boolean overwrite) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.Row> run (org.apache.spark.sql.SQLContext sqlContext) { throw new RuntimeException(); }
}
