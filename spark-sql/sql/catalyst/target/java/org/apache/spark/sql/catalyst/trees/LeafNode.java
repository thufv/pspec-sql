package org.apache.spark.sql.catalyst.trees;
/**
 * A {@link TreeNode} with no children.
 */
public abstract interface LeafNode<BaseType extends org.apache.spark.sql.catalyst.trees.TreeNode<BaseType>> {
  public  scala.collection.immutable.Nil$ children () ;
}
