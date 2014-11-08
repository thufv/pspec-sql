package org.apache.spark.sql.execution;
/**
 * :: DeveloperApi ::
 */
public  class ExistingRdd extends org.apache.spark.sql.execution.SparkPlan implements org.apache.spark.sql.execution.LeafNode, scala.Product, scala.Serializable {
  static public <A extends scala.Product> org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> productToRowRdd (org.apache.spark.rdd.RDD<A> data) { throw new RuntimeException(); }
  static public <A extends scala.Product> org.apache.spark.sql.execution.ExistingRdd fromProductRdd (org.apache.spark.rdd.RDD<A> productRdd, scala.reflect.api.TypeTags.TypeTag<A> evidence$1) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> rdd () { throw new RuntimeException(); }
  // not preceding
  public   ExistingRdd (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output, org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> rdd) { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> execute () { throw new RuntimeException(); }
}
