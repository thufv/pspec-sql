package org.apache.spark.sql.hive.execution;
/**
 * Implementation for "describe [extended] table".
 * <p>
 * :: DeveloperApi ::
 */
public  class DescribeHiveTableCommand extends org.apache.spark.sql.execution.SparkPlan implements org.apache.spark.sql.execution.LeafNode, org.apache.spark.sql.execution.Command, scala.Product, scala.Serializable {
  public  org.apache.spark.sql.hive.MetastoreRelation table () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () { throw new RuntimeException(); }
  public  boolean isExtended () { throw new RuntimeException(); }
  // not preceding
  public   DescribeHiveTableCommand (org.apache.spark.sql.hive.MetastoreRelation table, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output, boolean isExtended, org.apache.spark.sql.hive.HiveContext context) { throw new RuntimeException(); }
  public  scala.collection.Seq<java.lang.String> hiveString () { throw new RuntimeException(); }
  protected  scala.collection.Seq<scala.Tuple3<java.lang.String, java.lang.String, java.lang.String>> sideEffectResult () { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> execute () { throw new RuntimeException(); }
  public  scala.collection.immutable.List<org.apache.spark.sql.hive.HiveContext> otherCopyArgs () { throw new RuntimeException(); }
}
