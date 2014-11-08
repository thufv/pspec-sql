package org.apache.spark.sql.catalyst.plans.logical;
/**
 * A logical node that represents a non-query command to be executed by the system.  For example,
 * commands can be used by parsers to represent DDL operations.
 */
public abstract class Command extends org.apache.spark.sql.catalyst.plans.logical.LeafNode {
  public   Command () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () { throw new RuntimeException(); }
}
