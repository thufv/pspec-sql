package org.apache.spark.sql.catalyst.rules;
public abstract class RuleExecutor<TreeType extends org.apache.spark.sql.catalyst.trees.TreeNode<?>> implements org.apache.spark.Logging {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(ExistentialTypeTree(AppliedTypeTree(Select(Select(Select(Select(Select(Select(Ident(org), org.apache), org.apache.spark), org.apache.spark.sql), org.apache.spark.sql.catalyst), org.apache.spark.sql.catalyst.trees), org.apache.spark.sql.catalyst.trees.TreeNode), List(TypeTree().setOriginal(Ident(newTypeName("_$1"))))), List(TypeDef(Modifiers(DEFERRED | SYNTHETIC), newTypeName("_$1"), List(), TypeBoundsTree(Select(Select(Ident(_root_), scala), scala.Nothing), Select(Select(Ident(_root_), scala), scala.Any))))))))
  public   RuleExecutor () { throw new RuntimeException(); }
  /**
   * An execution strategy for rules that indicates the maximum number of executions. If the
   * execution reaches fix point (i.e. converge) before maxIterations, it will stop.
   */
  public abstract class Strategy {
    public   Strategy () { throw new RuntimeException(); }
    public abstract  int maxIterations () ;
  }
  // no position
  public  class Once extends org.apache.spark.sql.catalyst.rules.RuleExecutor<TreeType>.Strategy implements scala.Product, scala.Serializable {
    /** A strategy that only runs once. */
    public   Once () { throw new RuntimeException(); }
    public  int maxIterations () { throw new RuntimeException(); }
  }
  public  org.apache.spark.sql.catalyst.rules.RuleExecutor<TreeType>.Once$ Once () { throw new RuntimeException(); }
  /** A strategy that runs until fix point or maxIterations times, whichever comes first. */
  public  class FixedPoint extends org.apache.spark.sql.catalyst.rules.RuleExecutor<TreeType>.Strategy implements scala.Product, scala.Serializable {
    public  int maxIterations () { throw new RuntimeException(); }
    // not preceding
    public   FixedPoint (int maxIterations) { throw new RuntimeException(); }
  }
  // no position
  public  class FixedPoint extends scala.runtime.AbstractFunction1<java.lang.Object, org.apache.spark.sql.catalyst.rules.RuleExecutor<TreeType>.FixedPoint> implements scala.Serializable {
    public   FixedPoint () { throw new RuntimeException(); }
  }
  /** A batch of rules. */
  protected  class Batch implements scala.Product, scala.Serializable {
    public  java.lang.String name () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.rules.RuleExecutor<TreeType>.Strategy strategy () { throw new RuntimeException(); }
    public  scala.collection.Seq<org.apache.spark.sql.catalyst.rules.Rule<TreeType>> rules () { throw new RuntimeException(); }
    // not preceding
    public   Batch (java.lang.String name, org.apache.spark.sql.catalyst.rules.RuleExecutor<TreeType>.Strategy strategy, scala.collection.Seq<org.apache.spark.sql.catalyst.rules.Rule<TreeType>> rules) { throw new RuntimeException(); }
  }
  // no position
  protected  class Batch extends scala.runtime.AbstractFunction3<java.lang.String, org.apache.spark.sql.catalyst.rules.RuleExecutor<TreeType>.Strategy, scala.collection.Seq<org.apache.spark.sql.catalyst.rules.Rule<TreeType>>, org.apache.spark.sql.catalyst.rules.RuleExecutor<TreeType>.Batch> implements scala.Serializable {
    public   Batch () { throw new RuntimeException(); }
  }
  /** Defines a sequence of rule batches, to be overridden by the implementation. */
  protected abstract  scala.collection.Seq<org.apache.spark.sql.catalyst.rules.RuleExecutor<TreeType>.Batch> batches () ;
  /**
   * Executes the batches of rules defined by the subclass. The batches are executed serially
   * using the defined execution strategy. Within each batch, rules are also executed serially.
   */
  public  TreeType apply (TreeType plan) { throw new RuntimeException(); }
}
