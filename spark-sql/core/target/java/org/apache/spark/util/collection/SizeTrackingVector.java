package org.apache.spark.util.collection;
/**
 * An append-only buffer that keeps track of its estimated size in bytes.
 */
private  class SizeTrackingVector<T extends java.lang.Object> extends org.apache.spark.util.collection.PrimitiveVector<T> implements org.apache.spark.util.collection.SizeTracker {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   SizeTrackingVector (scala.reflect.ClassTag<T> evidence$1) { throw new RuntimeException(); }
  public  org.apache.spark.util.collection.PrimitiveVector<T> resize (int newLength) { throw new RuntimeException(); }
  /**
   * Return a trimmed version of the underlying array.
   */
  public  java.lang.Object toArray () { throw new RuntimeException(); }
}
