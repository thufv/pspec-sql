package org.apache.spark.util.collection;
/**
 * A common interface for our size-tracking collections of key-value pairs, which are used in
 * external operations. These all support estimating the size and obtaining a memory-efficient
 * sorted iterator.
 */
private  interface SizeTrackingPairCollection<K extends java.lang.Object, V extends java.lang.Object> extends scala.collection.Iterable<scala.Tuple2<K, V>> {
  /** Estimate the collection's current memory usage in bytes. */
  public abstract  long estimateSize () ;
  /** Iterate through the data in a given key order. This may destroy the underlying collection. */
  public abstract  scala.collection.Iterator<scala.Tuple2<K, V>> destructiveSortedIterator (java.util.Comparator<K> keyComparator) ;
}
