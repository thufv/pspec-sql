package org.apache.spark.sql.catalyst.expressions;
/**
 * A globally unique (within this JVM) id for a given named expression.
 * Used to identify which attribute output by a relation is being
 * referenced in a subsequent computation.
 */
public  class ExprId implements scala.Product, scala.Serializable {
  public  long id () { throw new RuntimeException(); }
  // not preceding
  public   ExprId (long id) { throw new RuntimeException(); }
}
