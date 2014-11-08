package org.apache.spark.util;
/**
 * Bounded priority queue. This class wraps the original PriorityQueue
 * class and modifies it such that only the top K elements are retained.
 * The top K elements are defined by an implicit Ordering[A].
 */
private  class BoundedPriorityQueue<A extends java.lang.Object> implements scala.collection.Iterable<A>, scala.collection.generic.Growable<A>, java.io.Serializable {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   BoundedPriorityQueue (int maxSize, scala.math.Ordering<A> ord) { throw new RuntimeException(); }
  private  java.util.PriorityQueue<A> underlying () { throw new RuntimeException(); }
  public  scala.collection.Iterator<A> iterator () { throw new RuntimeException(); }
  public  int size () { throw new RuntimeException(); }
  public  void clear () { throw new RuntimeException(); }
  private  boolean maybeReplaceLowest (A a) { throw new RuntimeException(); }
}
