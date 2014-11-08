package org.apache.spark.sql.catalyst.plans.logical;
/**
 * Applies a {@link Generator} to a stream of input rows, combining the
 * output of each into a new stream of rows.  This operation is similar to a <code>flatMap</code> in functional
 * programming with one important additional feature, which allows the input rows to be joined with
 * their output.
 * @param join  when true, each output row is implicitly joined with the input tuple that produced
 *              it.
 * @param outer when true, each input row will be output at least once, even if the output of the
 *              given <code>generator</code> is empty. <code>outer</code> has no effect when <code>join</code> is false.
 * @param alias when set, this string is applied to the schema of the output of the transformation
 *              as a qualifier.
 */
public  class Generate extends org.apache.spark.sql.catalyst.plans.logical.UnaryNode implements scala.Product, scala.Serializable {
  public  org.apache.spark.sql.catalyst.expressions.Generator generator () { throw new RuntimeException(); }
  public  boolean join () { throw new RuntimeException(); }
  public  boolean outer () { throw new RuntimeException(); }
  public  scala.Option<java.lang.String> alias () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan child () { throw new RuntimeException(); }
  // not preceding
  public   Generate (org.apache.spark.sql.catalyst.expressions.Generator generator, boolean join, boolean outer, scala.Option<java.lang.String> alias, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan child) { throw new RuntimeException(); }
  protected  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> generatorOutput () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () { throw new RuntimeException(); }
}
