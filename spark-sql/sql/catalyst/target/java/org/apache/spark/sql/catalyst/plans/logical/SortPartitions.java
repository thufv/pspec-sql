package org.apache.spark.sql.catalyst.plans.logical;
public  class SortPartitions extends org.apache.spark.sql.catalyst.plans.logical.RedistributeData implements scala.Product, scala.Serializable {
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.SortOrder> sortExpressions () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan child () { throw new RuntimeException(); }
  // not preceding
  public   SortPartitions (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.SortOrder> sortExpressions, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan child) { throw new RuntimeException(); }
}
