package org.apache.spark.util;
// no position
/**
 * Helper methods for converting to and from WeakReferences.
 */
private  class TimeStampedWeakValueHashMap$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final TimeStampedWeakValueHashMap$ MODULE$ = null;
  public   TimeStampedWeakValueHashMap$ () { throw new RuntimeException(); }
  public  int CLEAR_NULL_VALUES_INTERVAL () { throw new RuntimeException(); }
  public <V extends java.lang.Object> java.lang.ref.WeakReference<V> toWeakReference (V v) { throw new RuntimeException(); }
  public <K extends java.lang.Object, V extends java.lang.Object> scala.Tuple2<K, java.lang.ref.WeakReference<V>> toWeakReferenceTuple (scala.Tuple2<K, V> kv) { throw new RuntimeException(); }
  public <K extends java.lang.Object, V extends java.lang.Object, R extends java.lang.Object> scala.Function1<scala.Tuple2<K, java.lang.ref.WeakReference<V>>, R> toWeakReferenceFunction (scala.Function1<scala.Tuple2<K, V>, R> p) { throw new RuntimeException(); }
  public <V extends java.lang.Object> V fromWeakReference (java.lang.ref.WeakReference<V> ref) { throw new RuntimeException(); }
  public <V extends java.lang.Object> scala.Option<V> fromWeakReferenceOption (scala.Option<java.lang.ref.WeakReference<V>> v) { throw new RuntimeException(); }
  public <K extends java.lang.Object, V extends java.lang.Object> scala.Tuple2<K, V> fromWeakReferenceTuple (scala.Tuple2<K, java.lang.ref.WeakReference<V>> kv) { throw new RuntimeException(); }
  public <K extends java.lang.Object, V extends java.lang.Object> scala.collection.Iterator<scala.Tuple2<K, V>> fromWeakReferenceIterator (scala.collection.Iterator<scala.Tuple2<K, java.lang.ref.WeakReference<V>>> it) { throw new RuntimeException(); }
  public <K extends java.lang.Object, V extends java.lang.Object> scala.collection.mutable.Map<K, V> fromWeakReferenceMap (scala.collection.mutable.Map<K, java.lang.ref.WeakReference<V>> map) { throw new RuntimeException(); }
}
