package org.apache.spark.util.collection;
/** Format to sort a simple Array[Int]. Could be easily generified and specialized. */
public  class IntArraySortDataFormat implements org.apache.spark.util.collection.SortDataFormat<java.lang.Object, int[]> {
  public   IntArraySortDataFormat () { throw new RuntimeException(); }
  protected  int getKey (int[] data, int pos) { throw new RuntimeException(); }
  protected  void swap (int[] data, int pos0, int pos1) { throw new RuntimeException(); }
  protected  void copyElement (int[] src, int srcPos, int[] dst, int dstPos) { throw new RuntimeException(); }
  /** Copy a range of elements starting at src(srcPos) to dest, starting at destPos. */
  protected  void copyRange (int[] src, int srcPos, int[] dst, int dstPos, int length) { throw new RuntimeException(); }
  /** Allocates a new structure that can hold up to 'length' elements. */
  protected  int[] allocate (int length) { throw new RuntimeException(); }
}
