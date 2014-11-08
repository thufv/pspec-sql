package org.apache.spark.util.collection;
/**
 * Abstraction for sorting an arbitrary input buffer of data. This interface requires determining
 * the sort key for a given element index, as well as swapping elements and moving data from one
 * buffer to another.
 * <p>
 * Example format: an array of numbers, where each element is also the key.
 * See {@link KVArraySortDataFormat} for a more exciting format.
 * <p>
 * This trait extends Any to ensure it is universal (and thus compiled to a Java interface).
 * <p>
 * @tparam K Type of the sort key of each element
 * @tparam Buffer Internal data structure used by a particular format (e.g., Array[Int]).
 */
private  interface SortDataFormat<K extends java.lang.Object, Buffer extends java.lang.Object> {
  /** Return the sort key for the element at the given index. */
  protected abstract  K getKey (Buffer data, int pos) ;
  /** Swap two elements. */
  protected abstract  void swap (Buffer data, int pos0, int pos1) ;
  /** Copy a single element from src(srcPos) to dst(dstPos). */
  protected abstract  void copyElement (Buffer src, int srcPos, Buffer dst, int dstPos) ;
  /**
   * Copy a range of elements starting at src(srcPos) to dst, starting at dstPos.
   * Overlapping ranges are allowed.
   */
  protected abstract  void copyRange (Buffer src, int srcPos, Buffer dst, int dstPos, int length) ;
  /**
   * Allocates a Buffer that can hold up to 'length' elements.
   * All elements of the buffer should be considered invalid until data is explicitly copied in.
   */
  protected abstract  Buffer allocate (int length) ;
}
