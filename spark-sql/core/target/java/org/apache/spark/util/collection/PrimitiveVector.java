package org.apache.spark.util.collection;
/**
 * An append-only, non-threadsafe, array-backed vector that is optimized for primitive types.
 */
private  class PrimitiveVector<V extends java.lang.Object> {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   PrimitiveVector (int initialSize, scala.reflect.ClassTag<V> evidence$1) { throw new RuntimeException(); }
  private  int _numElements () { throw new RuntimeException(); }
  private  java.lang.Object _array () { throw new RuntimeException(); }
  public  V apply (int index) { throw new RuntimeException(); }
  public  int capacity () { throw new RuntimeException(); }
  public  int length () { throw new RuntimeException(); }
  public  int size () { throw new RuntimeException(); }
  public  scala.collection.Iterator<V> iterator () { throw new RuntimeException(); }
  /** Gets the underlying array backing this vector. */
  public  java.lang.Object array () { throw new RuntimeException(); }
  /** Trims this vector so that the capacity is equal to the size. */
  public  org.apache.spark.util.collection.PrimitiveVector<V> trim () { throw new RuntimeException(); }
  /** Resizes the array, dropping elements if the total length decreases. */
  public  org.apache.spark.util.collection.PrimitiveVector<V> resize (int newLength) { throw new RuntimeException(); }
}
