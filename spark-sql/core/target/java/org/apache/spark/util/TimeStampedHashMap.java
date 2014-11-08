package org.apache.spark.util;
/**
 * This is a custom implementation of scala.collection.mutable.Map which stores the insertion
 * timestamp along with each key-value pair. If specified, the timestamp of each pair can be
 * updated every time it is accessed. Key-value pairs whose timestamp are older than a particular
 * threshold time can then be removed using the clearOldValues method. This is intended to
 * be a drop-in replacement of scala.collection.mutable.HashMap.
 * <p>
 * @param updateTimeStampOnGet Whether timestamp of a pair will be updated when it is accessed
 */
private  class TimeStampedHashMap<A extends java.lang.Object, B extends java.lang.Object> implements scala.collection.mutable.Map<A, B>, org.apache.spark.Logging {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   TimeStampedHashMap (boolean updateTimeStampOnGet) { throw new RuntimeException(); }
  private  java.util.concurrent.ConcurrentHashMap<A, org.apache.spark.util.TimeStampedValue<B>> internalMap () { throw new RuntimeException(); }
  public  scala.Option<B> get (A key) { throw new RuntimeException(); }
  public  scala.collection.Iterator<scala.Tuple2<A, B>> iterator () { throw new RuntimeException(); }
  public  java.util.Set<java.util.Map.Entry<A, org.apache.spark.util.TimeStampedValue<B>>> getEntrySet () { throw new RuntimeException(); }
  public  void update (A key, B value) { throw new RuntimeException(); }
  public  B apply (A key) { throw new RuntimeException(); }
  public  scala.collection.mutable.Map<A, B> filter (scala.Function1<scala.Tuple2<A, B>, java.lang.Object> p) { throw new RuntimeException(); }
  public  scala.collection.mutable.Map<A, B> empty () { throw new RuntimeException(); }
  public  int size () { throw new RuntimeException(); }
  public <U extends java.lang.Object> void foreach (scala.Function1<scala.Tuple2<A, B>, U> f) { throw new RuntimeException(); }
  public  scala.Option<B> putIfAbsent (A key, B value) { throw new RuntimeException(); }
  public  void putAll (scala.collection.immutable.Map<A, B> map) { throw new RuntimeException(); }
  public  scala.collection.immutable.Map<A, B> toMap () { throw new RuntimeException(); }
  public  void clearOldValues (long threshTime, scala.Function2<A, B, scala.runtime.BoxedUnit> f) { throw new RuntimeException(); }
  /** Removes old key-value pairs that have timestamp earlier than `threshTime`. */
  public  void clearOldValues (long threshTime) { throw new RuntimeException(); }
  private  long currentTime () { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.util.TimeStampedValue<B>> getTimeStampedValue (A key) { throw new RuntimeException(); }
  public  scala.Option<java.lang.Object> getTimestamp (A key) { throw new RuntimeException(); }
}
