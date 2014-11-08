package org.apache.spark.util.collection;
/**
 * An append-only buffer similar to ArrayBuffer, but more memory-efficient for small buffers.
 * ArrayBuffer always allocates an Object array to store the data, with 16 entries by default,
 * so it has about 80-100 bytes of overhead. In contrast, CompactBuffer can keep up to two
 * elements in fields of the main object, and only allocates an Array[AnyRef] if there are more
 * entries than that. This makes it more efficient for operations like groupBy where we expect
 * some keys to have very few elements.
 */
private  class CompactBuffer<T extends java.lang.Object> implements scala.collection.Seq<T>, scala.Serializable {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   CompactBuffer () { throw new RuntimeException(); }
  private  T element0 () { throw new RuntimeException(); }
  private  T element1 () { throw new RuntimeException(); }
  private  int curSize () { throw new RuntimeException(); }
  private  java.lang.Object[] otherElements () { throw new RuntimeException(); }
  public  T apply (int position) { throw new RuntimeException(); }
  private  void update (int position, T value) { throw new RuntimeException(); }
  public  int length () { throw new RuntimeException(); }
  public  int size () { throw new RuntimeException(); }
  public  scala.collection.Iterator<T> iterator () { throw new RuntimeException(); }
  /** Increase our size to newSize and grow the backing array if needed. */
  private  void growToSize (int newSize) { throw new RuntimeException(); }
}
