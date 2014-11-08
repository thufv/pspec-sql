package org.apache.spark.util;
/**
 * Wrapper around an iterator which calls a completion method after it successfully iterates
 * through all the elements.
 */
private abstract class CompletionIterator<A extends java.lang.Object, I extends scala.collection.Iterator<A>> implements scala.collection.Iterator<A> {
  static public <A extends java.lang.Object, I extends scala.collection.Iterator<A>> org.apache.spark.util.CompletionIterator<A, I> apply (I sub, scala.Function0<scala.runtime.BoxedUnit> completionFunction) { throw new RuntimeException(); }
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(AppliedTypeTree(Select(Select(Ident(scala), scala.package), newTypeName("Iterator")), List(TypeTree().setOriginal(Ident(newTypeName("A"))))))))
  public   CompletionIterator (I sub) { throw new RuntimeException(); }
  public  A next () { throw new RuntimeException(); }
  public  boolean hasNext () { throw new RuntimeException(); }
  public abstract  void completion () ;
}
