package org.apache.spark.sql.catalyst.analysis;
// no position
/**
 * A trivial catalog that returns an error when a function is requested.  Used for testing when all
 * functions are already filled in and the analyser needs only to resolve attribute references.
 */
public  class EmptyFunctionRegistry implements org.apache.spark.sql.catalyst.analysis.FunctionRegistry {
  static public  scala.Nothing registerFunction (java.lang.String name, scala.Function1<scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression>, org.apache.spark.sql.catalyst.expressions.Expression> builder) { throw new RuntimeException(); }
  static public  org.apache.spark.sql.catalyst.expressions.Expression lookupFunction (java.lang.String name, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> children) { throw new RuntimeException(); }
}
