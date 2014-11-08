package org.apache.spark.sql.catalyst.expressions.codegen;
// no position
/**
 * Generates bytecode that evaluates a boolean {@link Expression} on a given input {@link Row}.
 */
public  class GeneratePredicate$ extends org.apache.spark.sql.catalyst.expressions.codegen.CodeGenerator<org.apache.spark.sql.catalyst.expressions.Expression, scala.Function1<org.apache.spark.sql.catalyst.expressions.Row, java.lang.Object>> {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final GeneratePredicate$ MODULE$ = null;
  public   GeneratePredicate$ () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.expressions.Expression canonicalize (org.apache.spark.sql.catalyst.expressions.Expression in) { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.expressions.Expression bind (org.apache.spark.sql.catalyst.expressions.Expression in, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> inputSchema) { throw new RuntimeException(); }
  protected  scala.Function1<org.apache.spark.sql.catalyst.expressions.Row, java.lang.Object> create (org.apache.spark.sql.catalyst.expressions.Expression predicate) { throw new RuntimeException(); }
}
