package org.apache.spark.sql.execution;
/**
 * :: DeveloperApi ::
 */
public  class SetCommand extends org.apache.spark.sql.catalyst.plans.logical.Command implements org.apache.spark.sql.execution.RunnableCommand, org.apache.spark.Logging, scala.Product, scala.Serializable {
  public  scala.Option<scala.Tuple2<java.lang.String, scala.Option<java.lang.String>>> kv () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () { throw new RuntimeException(); }
  // not preceding
  public   SetCommand (scala.Option<scala.Tuple2<java.lang.String, scala.Option<java.lang.String>>> kv, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.Row> run (org.apache.spark.sql.SQLContext sqlContext) { throw new RuntimeException(); }
}
