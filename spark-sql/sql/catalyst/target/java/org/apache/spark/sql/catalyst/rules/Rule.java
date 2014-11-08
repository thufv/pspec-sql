package org.apache.spark.sql.catalyst.rules;
public abstract class Rule<TreeType extends org.apache.spark.sql.catalyst.trees.TreeNode<?>> implements org.apache.spark.Logging {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(ExistentialTypeTree(AppliedTypeTree(Select(Select(Select(Select(Select(Select(Ident(org), org.apache), org.apache.spark), org.apache.spark.sql), org.apache.spark.sql.catalyst), org.apache.spark.sql.catalyst.trees), org.apache.spark.sql.catalyst.trees.TreeNode), List(TypeTree().setOriginal(Ident(newTypeName("_$1"))))), List(TypeDef(Modifiers(DEFERRED | SYNTHETIC), newTypeName("_$1"), List(), TypeBoundsTree(Select(Select(Ident(_root_), scala), scala.Nothing), Select(Select(Ident(_root_), scala), scala.Any))))))))
  public   Rule () { throw new RuntimeException(); }
  /** Name for this rule, automatically inferred based on class name. */
  public  java.lang.String ruleName () { throw new RuntimeException(); }
  public abstract  TreeType apply (TreeType plan) ;
}
