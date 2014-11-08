package org.apache.spark.sql.catalyst.expressions.codegen;
// no position
/**
 * Generates bytecode for an {@link Ordering} of {@link Row Rows} for a given set of
 * {@link Expression Expressions}.
 */
public  class GenerateOrdering$ extends org.apache.spark.sql.catalyst.expressions.codegen.CodeGenerator<scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.SortOrder>, scala.math.Ordering<org.apache.spark.sql.catalyst.expressions.Row>> implements org.apache.spark.Logging {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final GenerateOrdering$ MODULE$ = null;
  public   GenerateOrdering$ () { throw new RuntimeException(); }
  protected  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.SortOrder> canonicalize (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.SortOrder> in) { throw new RuntimeException(); }
  protected  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.SortOrder> bind (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.SortOrder> in, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> inputSchema) { throw new RuntimeException(); }
  protected  scala.math.Ordering<org.apache.spark.sql.catalyst.expressions.Row> create (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.SortOrder> ordering) { throw new RuntimeException(); }
}
