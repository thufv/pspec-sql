package org.apache.spark.util.collection;
/**
 * Supports sorting an array of key-value pairs where the elements of the array alternate between
 * keys and values, as used in {@link AppendOnlyMap}.
 * <p>
 * @tparam K Type of the sort key of each element
 * @tparam T Type of the Array we're sorting. Typically this must extend AnyRef, to support cases
 *           when the keys and values are not the same type.
 */
private  class KVArraySortDataFormat<K extends java.lang.Object, T extends java.lang.Object> implements org.apache.spark.util.collection.SortDataFormat<K, T[]> {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Ident(scala), newTypeName("AnyRef")))))
  public   KVArraySortDataFormat (scala.reflect.ClassTag<T> evidence$1) { throw new RuntimeException(); }
  protected  K getKey (T[] data, int pos) { throw new RuntimeException(); }
  protected  void swap (T[] data, int pos0, int pos1) { throw new RuntimeException(); }
  protected  void copyElement (T[] src, int srcPos, T[] dst, int dstPos) { throw new RuntimeException(); }
  protected  void copyRange (T[] src, int srcPos, T[] dst, int dstPos, int length) { throw new RuntimeException(); }
  protected  T[] allocate (int length) { throw new RuntimeException(); }
}
