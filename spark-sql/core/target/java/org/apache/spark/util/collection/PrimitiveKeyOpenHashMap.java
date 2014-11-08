package org.apache.spark.util.collection;
/**
 * A fast hash map implementation for primitive, non-null keys. This hash map supports
 * insertions and updates, but not deletions. This map is about an order of magnitude
 * faster than java.util.HashMap, while using much less space overhead.
 * <p>
 * Under the hood, it uses our OpenHashSet implementation.
 */
private  class PrimitiveKeyOpenHashMap<K extends java.lang.Object, V extends java.lang.Object> implements scala.collection.Iterable<scala.Tuple2<K, V>>, scala.Serializable {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   PrimitiveKeyOpenHashMap (int initialCapacity, scala.reflect.ClassTag<K> evidence$1, scala.reflect.ClassTag<V> evidence$2) { throw new RuntimeException(); }
  public   PrimitiveKeyOpenHashMap (scala.reflect.ClassTag<K> evidence$3, scala.reflect.ClassTag<V> evidence$4) { throw new RuntimeException(); }
  protected  org.apache.spark.util.collection.OpenHashSet<K> _keySet () { throw new RuntimeException(); }
  private  java.lang.Object _values () { throw new RuntimeException(); }
  private  java.lang.Object _oldValues () { throw new RuntimeException(); }
  public  int size () { throw new RuntimeException(); }
  /** Get the value for a given key */
  public  V apply (K k) { throw new RuntimeException(); }
  /** Get the value for a given key, or returns elseValue if it doesn't exist. */
  public  V getOrElse (K k, V elseValue) { throw new RuntimeException(); }
  /** Set the value for a key */
  public  void update (K k, V v) { throw new RuntimeException(); }
  /**
   * If the key doesn't exist yet in the hash map, set its value to defaultValue; otherwise,
   * set its value to mergeValue(oldValue).
   * <p>
   * @return the newly updated value.
   */
  public  V changeValue (K k, scala.Function0<V> defaultValue, scala.Function1<V, V> mergeValue) { throw new RuntimeException(); }
  public  java.lang.Object iterator () { throw new RuntimeException(); }
  protected  scala.Function1<java.lang.Object, scala.runtime.BoxedUnit> grow () { throw new RuntimeException(); }
  protected  scala.Function2<java.lang.Object, java.lang.Object, scala.runtime.BoxedUnit> move () { throw new RuntimeException(); }
}
