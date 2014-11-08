package org.apache.spark.util;
/**
 * A wrapper of TimeStampedHashMap that ensures the values are weakly referenced and timestamped.
 * <p>
 * If the value is garbage collected and the weak reference is null, get() will return a
 * non-existent value. These entries are removed from the map periodically (every N inserts), as
 * their values are no longer strongly reachable. Further, key-value pairs whose timestamps are
 * older than a particular threshold can be removed using the clearOldValues method.
 * <p>
 * TimeStampedWeakValueHashMap exposes a scala.collection.mutable.Map interface, which allows it
 * to be a drop-in replacement for Scala HashMaps. Internally, it uses a Java ConcurrentHashMap,
 * so all operations on this HashMap are thread-safe.
 * <p>
 * @param updateTimeStampOnGet Whether timestamp of a pair will be updated when it is accessed.
 */
private  class TimeStampedWeakValueHashMap<A extends java.lang.Object, B extends java.lang.Object> implements scala.collection.mutable.Map<A, B>, org.apache.spark.Logging {
  static public  int CLEAR_NULL_VALUES_INTERVAL () { throw new RuntimeException(); }
  static public <V extends java.lang.Object> java.lang.ref.WeakReference<V> toWeakReference (V v) { throw new RuntimeException(); }
  static public <K extends java.lang.Object, V extends java.lang.Object> scala.Tuple2<K, java.lang.ref.WeakReference<V>> toWeakReferenceTuple (scala.Tuple2<K, V> kv) { throw new RuntimeException(); }
  static public <K extends java.lang.Object, V extends java.lang.Object, R extends java.lang.Object> scala.Function1<scala.Tuple2<K, java.lang.ref.WeakReference<V>>, R> toWeakReferenceFunction (scala.Function1<scala.Tuple2<K, V>, R> p) { throw new RuntimeException(); }
  static public <V extends java.lang.Object> V fromWeakReference (java.lang.ref.WeakReference<V> ref) { throw new RuntimeException(); }
  static public <V extends java.lang.Object> scala.Option<V> fromWeakReferenceOption (scala.Option<java.lang.ref.WeakReference<V>> v) { throw new RuntimeException(); }
  static public <K extends java.lang.Object, V extends java.lang.Object> scala.Tuple2<K, V> fromWeakReferenceTuple (scala.Tuple2<K, java.lang.ref.WeakReference<V>> kv) { throw new RuntimeException(); }
  static public <K extends java.lang.Object, V extends java.lang.Object> scala.collection.Iterator<scala.Tuple2<K, V>> fromWeakReferenceIterator (scala.collection.Iterator<scala.Tuple2<K, java.lang.ref.WeakReference<V>>> it) { throw new RuntimeException(); }
  static public <K extends java.lang.Object, V extends java.lang.Object> scala.collection.mutable.Map<K, V> fromWeakReferenceMap (scala.collection.mutable.Map<K, java.lang.ref.WeakReference<V>> map) { throw new RuntimeException(); }
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   TimeStampedWeakValueHashMap (boolean updateTimeStampOnGet) { throw new RuntimeException(); }
  private  org.apache.spark.util.TimeStampedHashMap<A, java.lang.ref.WeakReference<B>> internalMap () { throw new RuntimeException(); }
  private  java.util.concurrent.atomic.AtomicInteger insertCount () { throw new RuntimeException(); }
  /** Return a map consisting only of entries whose values are still strongly reachable. */
  private  scala.collection.mutable.Map<A, java.lang.ref.WeakReference<B>> nonNullReferenceMap () { throw new RuntimeException(); }
  public  scala.Option<B> get (A key) { throw new RuntimeException(); }
  public  scala.collection.Iterator<scala.Tuple2<A, B>> iterator () { throw new RuntimeException(); }
  public  void update (A key, B value) { throw new RuntimeException(); }
  public  B apply (A key) { throw new RuntimeException(); }
  public  scala.collection.mutable.Map<A, B> filter (scala.Function1<scala.Tuple2<A, B>, java.lang.Object> p) { throw new RuntimeException(); }
  public  scala.collection.mutable.Map<A, B> empty () { throw new RuntimeException(); }
  public  int size () { throw new RuntimeException(); }
  public <U extends java.lang.Object> void foreach (scala.Function1<scala.Tuple2<A, B>, U> f) { throw new RuntimeException(); }
  public  scala.Option<B> putIfAbsent (A key, B value) { throw new RuntimeException(); }
  public  scala.collection.immutable.Map<A, B> toMap () { throw new RuntimeException(); }
  /** Remove old key-value pairs with timestamps earlier than `threshTime`. */
  public  void clearOldValues (long threshTime) { throw new RuntimeException(); }
  /** Remove entries with values that are no longer strongly reachable. */
  public  void clearNullValues () { throw new RuntimeException(); }
  public  scala.Option<java.lang.Object> getTimestamp (A key) { throw new RuntimeException(); }
  public  scala.Option<java.lang.ref.WeakReference<B>> getReference (A key) { throw new RuntimeException(); }
}
