package org.apache.spark.sql.execution;
/**
 * :: DeveloperApi ::
 */
public  class SetCommand extends org.apache.spark.sql.execution.SparkPlan implements org.apache.spark.sql.execution.LeafNode, org.apache.spark.sql.execution.Command, org.apache.spark.Logging, scala.Product, scala.Serializable {
  public  scala.Option<java.lang.String> key () { throw new RuntimeException(); }
  public  scala.Option<java.lang.String> value () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () { throw new RuntimeException(); }
  // not preceding
  public   SetCommand (scala.Option<java.lang.String> key, scala.Option<java.lang.String> value, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output, org.apache.spark.sql.SQLContext context) { throw new RuntimeException(); }
  protected  scala.collection.Seq<java.lang.String> sideEffectResult () { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> execute () { throw new RuntimeException(); }
  public  scala.collection.immutable.List<org.apache.spark.sql.SQLContext> otherCopyArgs () { throw new RuntimeException(); }
}
