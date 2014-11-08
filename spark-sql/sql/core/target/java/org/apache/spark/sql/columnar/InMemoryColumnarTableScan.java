package org.apache.spark.sql.columnar;
private  class InMemoryColumnarTableScan extends org.apache.spark.sql.execution.SparkPlan implements org.apache.spark.sql.execution.LeafNode, scala.Product, scala.Serializable {
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> attributes () { throw new RuntimeException(); }
  public  org.apache.spark.sql.columnar.InMemoryRelation relation () { throw new RuntimeException(); }
  // not preceding
  public   InMemoryColumnarTableScan (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> attributes, org.apache.spark.sql.columnar.InMemoryRelation relation) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> execute () { throw new RuntimeException(); }
}
