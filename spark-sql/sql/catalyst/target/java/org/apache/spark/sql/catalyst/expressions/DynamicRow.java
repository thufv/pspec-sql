package org.apache.spark.sql.catalyst.expressions;
/**
 * DynamicRows use scala's Dynamic trait to emulate an ORM of in a dynamically typed language.
 * Since the type of the column is not known at compile time, all attributes are converted to
 * strings before being passed to the function.
 */
public  class DynamicRow extends org.apache.spark.sql.catalyst.expressions.GenericRow implements scala.Dynamic {
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> schema () { throw new RuntimeException(); }
  // not preceding
  public   DynamicRow (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> schema, java.lang.Object[] values) { throw new RuntimeException(); }
  public  java.lang.String selectDynamic (java.lang.String attributeName) { throw new RuntimeException(); }
}
