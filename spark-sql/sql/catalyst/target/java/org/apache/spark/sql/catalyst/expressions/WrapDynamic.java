package org.apache.spark.sql.catalyst.expressions;
/**
 * Wrap a {@link Row} as a {@link DynamicRow}.
 */
public  class WrapDynamic extends org.apache.spark.sql.catalyst.expressions.Expression implements scala.Product, scala.Serializable {
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> children () { throw new RuntimeException(); }
  // not preceding
  public   WrapDynamic (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> children) { throw new RuntimeException(); }
  public  boolean nullable () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.DynamicType$ dataType () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.DynamicRow eval (org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
}
