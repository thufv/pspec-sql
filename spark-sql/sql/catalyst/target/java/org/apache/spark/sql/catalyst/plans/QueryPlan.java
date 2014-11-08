package org.apache.spark.sql.catalyst.plans;
public abstract class QueryPlan<PlanType extends org.apache.spark.sql.catalyst.trees.TreeNode<PlanType>> extends org.apache.spark.sql.catalyst.trees.TreeNode<PlanType> {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(AppliedTypeTree(Select(Select(Select(Select(Select(Select(Ident(org), org.apache), org.apache.spark), org.apache.spark.sql), org.apache.spark.sql.catalyst), org.apache.spark.sql.catalyst.trees), org.apache.spark.sql.catalyst.trees.TreeNode), List(TypeTree().setOriginal(Ident(newTypeName("PlanType"))))))))
  public   QueryPlan () { throw new RuntimeException(); }
  public abstract  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () ;
  /**
   * Returns the set of attributes that are output by this node.
   */
  public  org.apache.spark.sql.catalyst.expressions.AttributeSet outputSet () { throw new RuntimeException(); }
  /**
   * Runs {@link transform} with <code>rule</code> on all expressions present in this query operator.
   * Users should not expect a specific directionality. If a specific directionality is needed,
   * transformExpressionsDown or transformExpressionsUp should be used.
   * @param rule the rule to be applied to every expression in this operator.
   */
  public  org.apache.spark.sql.catalyst.plans.QueryPlan<PlanType> transformExpressions (scala.PartialFunction<org.apache.spark.sql.catalyst.expressions.Expression, org.apache.spark.sql.catalyst.expressions.Expression> rule) { throw new RuntimeException(); }
  /**
   * Runs {@link transformDown} with <code>rule</code> on all expressions present in this query operator.
   * @param rule the rule to be applied to every expression in this operator.
   */
  public  org.apache.spark.sql.catalyst.plans.QueryPlan<PlanType> transformExpressionsDown (scala.PartialFunction<org.apache.spark.sql.catalyst.expressions.Expression, org.apache.spark.sql.catalyst.expressions.Expression> rule) { throw new RuntimeException(); }
  /**
   * Runs {@link transformUp} with <code>rule</code> on all expressions present in this query operator.
   * @param rule the rule to be applied to every expression in this operator.
   * @return
   */
  public  org.apache.spark.sql.catalyst.plans.QueryPlan<PlanType> transformExpressionsUp (scala.PartialFunction<org.apache.spark.sql.catalyst.expressions.Expression, org.apache.spark.sql.catalyst.expressions.Expression> rule) { throw new RuntimeException(); }
  /** Returns the result of running {@link transformExpressions} on this node
   * and all its children. */
  public  org.apache.spark.sql.catalyst.plans.QueryPlan<PlanType> transformAllExpressions (scala.PartialFunction<org.apache.spark.sql.catalyst.expressions.Expression, org.apache.spark.sql.catalyst.expressions.Expression> rule) { throw new RuntimeException(); }
  /** Returns all of the expressions present in this query plan operator. */
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> expressions () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.types.StructType schema () { throw new RuntimeException(); }
  /** Returns the output schema in the tree format. */
  public  java.lang.String schemaString () { throw new RuntimeException(); }
  /** Prints out the schema in the tree format */
  public  void printSchema () { throw new RuntimeException(); }
}
