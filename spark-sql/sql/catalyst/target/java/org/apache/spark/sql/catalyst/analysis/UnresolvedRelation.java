package org.apache.spark.sql.catalyst.analysis;
/**
 * Holds the name of a relation that has yet to be looked up in a {@link Catalog}.
 */
public  class UnresolvedRelation extends org.apache.spark.sql.catalyst.plans.logical.LeafNode implements scala.Product, scala.Serializable {
  public  scala.Option<java.lang.String> databaseName () { throw new RuntimeException(); }
  public  java.lang.String tableName () { throw new RuntimeException(); }
  public  scala.Option<java.lang.String> alias () { throw new RuntimeException(); }
  // not preceding
  public   UnresolvedRelation (scala.Option<java.lang.String> databaseName, java.lang.String tableName, scala.Option<java.lang.String> alias) { throw new RuntimeException(); }
  public  scala.collection.immutable.Nil$ output () { throw new RuntimeException(); }
  public  boolean resolved () { throw new RuntimeException(); }
}
