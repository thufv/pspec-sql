package org.apache.spark.sql.catalyst.plans.logical;
public abstract class LogicalPlan extends org.apache.spark.sql.catalyst.plans.QueryPlan<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
  public   LogicalPlan () { throw new RuntimeException(); }
  public  scala.collection.mutable.Map<org.apache.spark.sql.catalyst.expressions.Attribute, org.apache.spark.sql.catalyst.checker.Label> projections () { throw new RuntimeException(); }
  public  scala.collection.mutable.Set<org.apache.spark.sql.catalyst.checker.Label> tests () { throw new RuntimeException(); }
  public  scala.Tuple2<scala.collection.mutable.Map<org.apache.spark.sql.catalyst.expressions.Attribute, org.apache.spark.sql.catalyst.checker.Label>, scala.collection.mutable.Set<org.apache.spark.sql.catalyst.checker.Label>> calculateLabels () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.checker.Label childLabel (org.apache.spark.sql.catalyst.expressions.Attribute attr) { throw new RuntimeException(); }
  /**
   * Estimates of various statistics.  The default estimation logic simply lazily multiplies the
   * corresponding statistic produced by the children.  To override this behavior, override
   * <code>statistics</code> and assign it an overriden version of <code>Statistics</code>.
   * <p>
   * '''NOTE''': concrete and/or overriden versions of statistics fields should pay attention to the
   * performance of the implementations.  The reason is that estimations might get triggered in
   * performance-critical processes, such as query plan planning.
   * <p>
   * @param sizeInBytes Physical size in bytes. For leaf operators this defaults to 1, otherwise it
   *                    defaults to the product of children's <code>sizeInBytes</code>.
   */
  public  class Statistics implements scala.Product, scala.Serializable {
    public  scala.math.BigInt sizeInBytes () { throw new RuntimeException(); }
    // not preceding
    public   Statistics (scala.math.BigInt sizeInBytes) { throw new RuntimeException(); }
  }
  // no position
  public  class Statistics extends scala.runtime.AbstractFunction1<scala.math.BigInt, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan.Statistics> implements scala.Serializable {
    public   Statistics () { throw new RuntimeException(); }
  }
  public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan.Statistics statistics () { throw new RuntimeException(); }
  /**
   * Returns the set of attributes that this node takes as
   * input from its children.
   */
  public  org.apache.spark.sql.catalyst.expressions.AttributeSet inputSet () { throw new RuntimeException(); }
  /**
   * Returns true if this expression and all its children have been resolved to a specific schema
   * and false if it is still contains any unresolved placeholders. Implementations of LogicalPlan
   * can override this (e.g.
   * {@link org.apache.spark.sql.catalyst.analysis.UnresolvedRelation UnresolvedRelation}
   * should return <code>false</code>).
   */
  public  boolean resolved () { throw new RuntimeException(); }
  /**
   * Returns true if all its children of this query plan have been resolved.
   */
  public  boolean childrenResolved () { throw new RuntimeException(); }
  /**
   * Optionally resolves the given string to a {@link NamedExpression} using the input from all child
   * nodes of this LogicalPlan. The attribute is expressed as
   * as string in the following form: <code>[scope].AttributeName.[nested].[fields]...</code>.
   */
  public  scala.Option<org.apache.spark.sql.catalyst.expressions.NamedExpression> resolveChildren (java.lang.String name) { throw new RuntimeException(); }
  /**
   * Optionally resolves the given string to a {@link NamedExpression} based on the output of this
   * LogicalPlan. The attribute is expressed as string in the following form:
   * <code>[scope].AttributeName.[nested].[fields]...</code>.
   */
  public  scala.Option<org.apache.spark.sql.catalyst.expressions.NamedExpression> resolve (java.lang.String name) { throw new RuntimeException(); }
  /** Performs attribute resolution given a name and a sequence of possible attributes. */
  protected  scala.Option<org.apache.spark.sql.catalyst.expressions.NamedExpression> resolve (java.lang.String name, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> input) { throw new RuntimeException(); }
}
