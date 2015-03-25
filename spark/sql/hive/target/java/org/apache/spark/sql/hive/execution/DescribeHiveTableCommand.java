package org.apache.spark.sql.hive.execution;
/**
 * Implementation for "describe [extended] table".
 */
public  class DescribeHiveTableCommand extends org.apache.spark.sql.catalyst.plans.logical.Command implements org.apache.spark.sql.execution.RunnableCommand, scala.Product, scala.Serializable {
  public  org.apache.spark.sql.hive.MetastoreRelation table () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () { throw new RuntimeException(); }
  public  boolean isExtended () { throw new RuntimeException(); }
  // not preceding
  public   DescribeHiveTableCommand (org.apache.spark.sql.hive.MetastoreRelation table, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output, boolean isExtended) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.Row> run (org.apache.spark.sql.SQLContext sqlContext) { throw new RuntimeException(); }
}
