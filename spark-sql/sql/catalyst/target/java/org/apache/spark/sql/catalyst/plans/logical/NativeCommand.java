package org.apache.spark.sql.catalyst.plans.logical;
/**
 * Returned for commands supported by a given parser, but not catalyst.  In general these are DDL
 * commands that are passed directly to another system.
 */
public  class NativeCommand extends org.apache.spark.sql.catalyst.plans.logical.Command implements scala.Product, scala.Serializable {
  public  java.lang.String cmd () { throw new RuntimeException(); }
  // not preceding
  public   NativeCommand (java.lang.String cmd) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.AttributeReference> output () { throw new RuntimeException(); }
}
