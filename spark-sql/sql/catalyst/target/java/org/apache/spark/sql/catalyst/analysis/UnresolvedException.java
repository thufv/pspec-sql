package org.apache.spark.sql.catalyst.analysis;
/**
 * Thrown when an invalid attempt is made to access a property of a tree that has yet to be fully
 * resolved.
 */
public  class UnresolvedException<TreeType extends org.apache.spark.sql.catalyst.trees.TreeNode<?>> extends org.apache.spark.sql.catalyst.errors.TreeNodeException<TreeType> {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(ExistentialTypeTree(AppliedTypeTree(Select(Select(Select(Select(Select(Select(Ident(org), org.apache), org.apache.spark), org.apache.spark.sql), org.apache.spark.sql.catalyst), org.apache.spark.sql.catalyst.trees), org.apache.spark.sql.catalyst.trees.TreeNode), List(TypeTree().setOriginal(Ident(newTypeName("_$1"))))), List(TypeDef(Modifiers(DEFERRED | SYNTHETIC), newTypeName("_$1"), List(), TypeBoundsTree(Select(Select(Ident(_root_), scala), scala.Nothing), Select(Select(Ident(_root_), scala), scala.Any))))))))
  public   UnresolvedException (TreeType tree, java.lang.String function) { throw new RuntimeException(); }
}
