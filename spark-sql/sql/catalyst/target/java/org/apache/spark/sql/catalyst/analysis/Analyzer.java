package org.apache.spark.sql.catalyst.analysis;
/**
 * Provides a logical query plan analyzer, which translates {@link UnresolvedAttribute}s and
 * {@link UnresolvedRelation}s into fully typed objects using information in a schema {@link Catalog} and
 * a {@link FunctionRegistry}.
 */
public  class Analyzer extends org.apache.spark.sql.catalyst.rules.RuleExecutor<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> implements org.apache.spark.sql.catalyst.analysis.HiveTypeCoercion {
  public   Analyzer (org.apache.spark.sql.catalyst.analysis.Catalog catalog, org.apache.spark.sql.catalyst.analysis.FunctionRegistry registry, boolean caseSensitive) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.rules.RuleExecutor<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan>.FixedPoint fixedPoint () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.rules.RuleExecutor<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan>.Batch> batches () { throw new RuntimeException(); }
  // no position
  public  class CheckResolution extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
    /**
     * Makes sure all attributes have been resolved.
     */
    public   CheckResolution () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // not preceding
  public  org.apache.spark.sql.catalyst.analysis.Analyzer.CheckResolution$ CheckResolution () { throw new RuntimeException(); }
  // no position
  public  class ResolveRelations extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
    /**
     * Replaces {@link UnresolvedRelation}s with concrete relations from the catalog.
     */
    public   ResolveRelations () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // not preceding
  public  org.apache.spark.sql.catalyst.analysis.Analyzer.ResolveRelations$ ResolveRelations () { throw new RuntimeException(); }
  // no position
  public  class LowercaseAttributeReferences extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
    /**
     * Makes attribute naming case insensitive by turning all UnresolvedAttributes to lowercase.
     */
    public   LowercaseAttributeReferences () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // not preceding
  public  org.apache.spark.sql.catalyst.analysis.Analyzer.LowercaseAttributeReferences$ LowercaseAttributeReferences () { throw new RuntimeException(); }
  // no position
  public  class ResolveReferences extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
    /**
     * Replaces {@link UnresolvedAttribute}s with concrete
     * {@link catalyst.expressions.AttributeReference AttributeReferences} from a logical plan node's
     * children.
     */
    public   ResolveReferences () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // not preceding
  public  org.apache.spark.sql.catalyst.analysis.Analyzer.ResolveReferences$ ResolveReferences () { throw new RuntimeException(); }
  // no position
  public  class ResolveSortReferences extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
    /**
     * In many dialects of SQL is it valid to sort by attributes that are not present in the SELECT
     * clause.  This rule detects such queries and adds the required attributes to the original
     * projection, so that they will be available during sorting. Another projection is added to
     * remove these attributes after sorting.
     */
    public   ResolveSortReferences () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // not preceding
  public  org.apache.spark.sql.catalyst.analysis.Analyzer.ResolveSortReferences$ ResolveSortReferences () { throw new RuntimeException(); }
  // no position
  public  class ResolveFunctions extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
    /**
     * Replaces {@link UnresolvedFunction}s with concrete {@link catalyst.expressions.Expression Expressions}.
     */
    public   ResolveFunctions () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // not preceding
  public  org.apache.spark.sql.catalyst.analysis.Analyzer.ResolveFunctions$ ResolveFunctions () { throw new RuntimeException(); }
  // no position
  public  class GlobalAggregates extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
    /**
     * Turns projections that contain aggregate expressions into aggregations.
     */
    public   GlobalAggregates () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
    public  boolean containsAggregates (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> exprs) { throw new RuntimeException(); }
  }
  // not preceding
  public  org.apache.spark.sql.catalyst.analysis.Analyzer.GlobalAggregates$ GlobalAggregates () { throw new RuntimeException(); }
  // no position
  public  class UnresolvedHavingClauseAttributes extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
    /**
     * This rule finds expressions in HAVING clause filters that depend on
     * unresolved attributes.  It pushes these expressions down to the underlying
     * aggregates and then projects them away above the filter.
     */
    public   UnresolvedHavingClauseAttributes () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
    protected  boolean containsAggregate (org.apache.spark.sql.catalyst.expressions.Expression condition) { throw new RuntimeException(); }
  }
  // not preceding
  public  org.apache.spark.sql.catalyst.analysis.Analyzer.UnresolvedHavingClauseAttributes$ UnresolvedHavingClauseAttributes () { throw new RuntimeException(); }
  // no position
  public  class ImplicitGenerate extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
    /**
     * When a SELECT clause has only a single expression and that expression is a
     * {@link catalyst.expressions.Generator Generator} we convert the
     * {@link catalyst.plans.logical.Project Project} to a {@link catalyst.plans.logical.Generate Generate}.
     */
    public   ImplicitGenerate () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // not preceding
  public  org.apache.spark.sql.catalyst.analysis.Analyzer.ImplicitGenerate$ ImplicitGenerate () { throw new RuntimeException(); }
  // no position
  public  class StarExpansion extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
    /**
     * Expands any references to {@link Star} (*) in project operators.
     */
    public   StarExpansion () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
    /**
     * Returns true if <code>exprs</code> contains a {@link Star}.
     */
    protected  boolean containsStar (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> exprs) { throw new RuntimeException(); }
  }
  // not preceding
  public  org.apache.spark.sql.catalyst.analysis.Analyzer.StarExpansion$ StarExpansion () { throw new RuntimeException(); }
}
