package org.apache.spark.sql.catalyst.trees;
/**
 * A {@link TreeNode} that has two children, {@link left} and {@link right}.
 */
public abstract interface BinaryNode<BaseType extends org.apache.spark.sql.catalyst.trees.TreeNode<BaseType>> {
  public abstract  BaseType left () ;
  public abstract  BaseType right () ;
  public  scala.collection.Seq<BaseType> children () ;
}
