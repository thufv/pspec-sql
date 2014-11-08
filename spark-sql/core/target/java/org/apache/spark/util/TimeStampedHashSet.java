package org.apache.spark.util;
private  class TimeStampedHashSet<A extends java.lang.Object> implements scala.collection.mutable.Set<A> {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   TimeStampedHashSet () { throw new RuntimeException(); }
  public  java.util.concurrent.ConcurrentHashMap<A, java.lang.Object> internalMap () { throw new RuntimeException(); }
  public  boolean contains (A key) { throw new RuntimeException(); }
  public  scala.collection.Iterator<A> iterator () { throw new RuntimeException(); }
  public  scala.collection.mutable.Set<A> empty () { throw new RuntimeException(); }
  public  int size () { throw new RuntimeException(); }
  public <U extends java.lang.Object> void foreach (scala.Function1<A, U> f) { throw new RuntimeException(); }
  /**
   * Removes old values that have timestamp earlier than <code>threshTime</code>
   */
  public  void clearOldValues (long threshTime) { throw new RuntimeException(); }
  private  long currentTime () { throw new RuntimeException(); }
}
