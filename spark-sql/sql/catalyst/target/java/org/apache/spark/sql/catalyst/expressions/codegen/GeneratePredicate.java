package org.apache.spark.sql.catalyst.expressions.codegen;
// no position
/**
 * Generates bytecode that evaluates a boolean {@link Expression} on a given input {@link Row}.
 */
public  class GeneratePredicate extends org.apache.spark.sql.catalyst.expressions.codegen.CodeGenerator<org.apache.spark.sql.catalyst.expressions.Expression, scala.Function1<org.apache.spark.sql.catalyst.expressions.Row, java.lang.Object>> {
  static protected  org.apache.spark.sql.catalyst.expressions.Expression canonicalize (org.apache.spark.sql.catalyst.expressions.Expression in) { throw new RuntimeException(); }
  static protected  org.apache.spark.sql.catalyst.expressions.Expression bind (org.apache.spark.sql.catalyst.expressions.Expression in, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> inputSchema) { throw new RuntimeException(); }
  static protected  scala.Function1<org.apache.spark.sql.catalyst.expressions.Row, java.lang.Object> create (org.apache.spark.sql.catalyst.expressions.Expression predicate) { throw new RuntimeException(); }
}
