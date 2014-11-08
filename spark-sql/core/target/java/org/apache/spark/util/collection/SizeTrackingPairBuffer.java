package org.apache.spark.util.collection;
/**
 * Append-only buffer of key-value pairs that keeps track of its estimated size in bytes.
 */
private  class SizeTrackingPairBuffer<K extends java.lang.Object, V extends java.lang.Object> implements org.apache.spark.util.collection.SizeTracker, org.apache.spark.util.collection.SizeTrackingPairCollection<K, V> {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   SizeTrackingPairBuffer (int initialCapacity) { throw new RuntimeException(); }
  private  int capacity () { throw new RuntimeException(); }
  private  int curSize () { throw new RuntimeException(); }
  private  java.lang.Object[] data () { throw new RuntimeException(); }
  /** Add an element into the buffer */
  public  void insert (K key, V value) { throw new RuntimeException(); }
  /** Total number of elements in buffer */
  public  int size () { throw new RuntimeException(); }
  /** Iterate over the elements of the buffer */
  public  scala.collection.Iterator<scala.Tuple2<K, V>> iterator () { throw new RuntimeException(); }
  /** Double the size of the array because we've reached capacity */
  private  void growArray () { throw new RuntimeException(); }
  /** Iterate through the data in a given order. For this class this is not really destructive. */
  public  scala.collection.Iterator<scala.Tuple2<K, V>> destructiveSortedIterator (java.util.Comparator<K> keyComparator) { throw new RuntimeException(); }
}
