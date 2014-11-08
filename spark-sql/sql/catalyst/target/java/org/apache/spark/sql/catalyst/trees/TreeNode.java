package org.apache.spark.sql.catalyst.trees;
public abstract class TreeNode<BaseType extends org.apache.spark.sql.catalyst.trees.TreeNode<BaseType>> {
  static private  java.util.concurrent.atomic.AtomicLong currentId () { throw new RuntimeException(); }
  static protected  long nextId () { throw new RuntimeException(); }
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(AppliedTypeTree(Ident(org.apache.spark.sql.catalyst.trees.TreeNode), List(TypeTree().setOriginal(Ident(newTypeName("BaseType"))))))))
  public   TreeNode () { throw new RuntimeException(); }
  /** Returns a Seq of the children of this node */
  public abstract  scala.collection.Seq<BaseType> children () ;
  /**
   * A globally unique id for this specific instance. Not preserved across copies.
   * Unlike <code>equals</code>, <code>id</code> can be used to differentiate distinct but structurally
   * identical branches of a tree.
   */
  public  long id () { throw new RuntimeException(); }
  /**
   * Returns true if other is the same {@link catalyst.trees.TreeNode TreeNode} instance.  Unlike
   * <code>equals</code> this function will return false for different instances of structurally identical
   * trees.
   */
  public  boolean sameInstance (org.apache.spark.sql.catalyst.trees.TreeNode<?> other) { throw new RuntimeException(); }
  /**
   * Faster version of equality which short-circuits when two treeNodes are the same instance.
   * We don't just override Object.Equals, as doing so prevents the scala compiler from from
   * generating case class <code>equals</code> methods
   */
  public  boolean fastEquals (org.apache.spark.sql.catalyst.trees.TreeNode<?> other) { throw new RuntimeException(); }
  /**
   * Runs the given function on this node and then recursively on {@link children}.
   * @param f the function to be applied to each node in the tree.
   */
  public  void foreach (scala.Function1<BaseType, scala.runtime.BoxedUnit> f) { throw new RuntimeException(); }
  /**
   * Returns a Seq containing the result of applying the given function to each
   * node in this tree in a preorder traversal.
   * @param f the function to be applied.
   */
  public <A extends java.lang.Object> scala.collection.Seq<A> map (scala.Function1<BaseType, A> f) { throw new RuntimeException(); }
  /**
   * Returns a Seq by applying a function to all nodes in this tree and using the elements of the
   * resulting collections.
   */
  public <A extends java.lang.Object> scala.collection.Seq<A> flatMap (scala.Function1<BaseType, scala.collection.TraversableOnce<A>> f) { throw new RuntimeException(); }
  /**
   * Returns a Seq containing the result of applying a partial function to all elements in this
   * tree on which the function is defined.
   */
  public <B extends java.lang.Object> scala.collection.Seq<B> collect (scala.PartialFunction<BaseType, B> pf) { throw new RuntimeException(); }
  /**
   * Returns a copy of this node where <code>f</code> has been applied to all the nodes children.
   */
  public  org.apache.spark.sql.catalyst.trees.TreeNode<BaseType> mapChildren (scala.Function1<BaseType, BaseType> f) { throw new RuntimeException(); }
  /**
   * Returns a copy of this node with the children replaced.
   * TODO: Validate somewhere (in debug mode?) that children are ordered correctly.
   */
  public  org.apache.spark.sql.catalyst.trees.TreeNode<BaseType> withNewChildren (scala.collection.Seq<BaseType> newChildren) { throw new RuntimeException(); }
  /**
   * Returns a copy of this node where <code>rule</code> has been recursively applied to the tree.
   * When <code>rule</code> does not apply to a given node it is left unchanged.
   * Users should not expect a specific directionality. If a specific directionality is needed,
   * transformDown or transformUp should be used.
   * @param rule the function use to transform this nodes children
   */
  public  BaseType transform (scala.PartialFunction<BaseType, BaseType> rule) { throw new RuntimeException(); }
  /**
   * Returns a copy of this node where <code>rule</code> has been recursively applied to it and all of its
   * children (pre-order). When <code>rule</code> does not apply to a given node it is left unchanged.
   * @param rule the function used to transform this nodes children
   */
  public  BaseType transformDown (scala.PartialFunction<BaseType, BaseType> rule) { throw new RuntimeException(); }
  /**
   * Returns a copy of this node where <code>rule</code> has been recursively applied to all the children of
   * this node.  When <code>rule</code> does not apply to a given node it is left unchanged.
   * @param rule the function used to transform this nodes children
   */
  public  org.apache.spark.sql.catalyst.trees.TreeNode<BaseType> transformChildrenDown (scala.PartialFunction<BaseType, BaseType> rule) { throw new RuntimeException(); }
  /**
   * Returns a copy of this node where <code>rule</code> has been recursively applied first to all of its
   * children and then itself (post-order). When <code>rule</code> does not apply to a given node, it is left
   * unchanged.
   * @param rule the function use to transform this nodes children
   */
  public  BaseType transformUp (scala.PartialFunction<BaseType, BaseType> rule) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.trees.TreeNode<BaseType> transformChildrenUp (scala.PartialFunction<BaseType, BaseType> rule) { throw new RuntimeException(); }
  /**
   * Args to the constructor that should be copied, but not transformed.
   * These are appended to the transformed args automatically by makeCopy
   * @return
   */
  protected  scala.collection.Seq<java.lang.Object> otherCopyArgs () { throw new RuntimeException(); }
  /**
   * Creates a copy of this type of tree node after a transformation.
   * Must be overridden by child classes that have constructor arguments
   * that are not present in the productIterator.
   * @param newArgs the new product arguments.
   */
  public  org.apache.spark.sql.catalyst.trees.TreeNode<BaseType> makeCopy (java.lang.Object[] newArgs) { throw new RuntimeException(); }
  /** Returns the name of this type of TreeNode.  Defaults to the class name. */
  public  java.lang.String nodeName () { throw new RuntimeException(); }
  /**
   * The arguments that should be included in the arg string.  Defaults to the <code>productIterator</code>.
   */
  protected  scala.collection.Iterator<java.lang.Object> stringArgs () { throw new RuntimeException(); }
  /** Returns a string representing the arguments to this node, minus any children */
  public  java.lang.String argString () { throw new RuntimeException(); }
  /** String representation of this node without any children */
  public  java.lang.String simpleString () { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
  /** Returns a string representation of the nodes in this tree */
  public  java.lang.String treeString () { throw new RuntimeException(); }
  /**
   * Returns a string representation of the nodes in this tree, where each operator is numbered.
   * The numbers can be used with {@link trees.TreeNode.apply apply} to easily access specific subtrees.
   */
  public  java.lang.String numberedTreeString () { throw new RuntimeException(); }
  /**
   * Returns the tree node at the specified number.
   * Numbers for each node can be found in the {@link numberedTreeString}.
   */
  public  BaseType apply (int number) { throw new RuntimeException(); }
  protected  BaseType getNodeNumbered (org.apache.spark.sql.catalyst.trees.MutableInt number) { throw new RuntimeException(); }
  /** Appends the string represent of this node and its children to the given StringBuilder. */
  protected  scala.collection.mutable.StringBuilder generateTreeString (int depth, scala.collection.mutable.StringBuilder builder) { throw new RuntimeException(); }
  /**
   * Returns a 'scala code' representation of this <code>TreeNode</code> and its children.  Intended for use
   * when debugging where the prettier toString function is obfuscating the actual structure. In the
   * case of 'pure' <code>TreeNodes</code> that only contain primitives and other TreeNodes, the result can be
   * pasted in the REPL to build an equivalent Tree.
   */
  public  java.lang.String asCode () { throw new RuntimeException(); }
}
