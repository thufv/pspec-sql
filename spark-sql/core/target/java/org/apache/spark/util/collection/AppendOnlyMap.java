package org.apache.spark.util.collection;
/**
 * :: DeveloperApi ::
 * A simple open hash table optimized for the append-only use case, where keys
 * are never removed, but the value for each key may be changed.
 * <p>
 * This implementation uses quadratic probing with a power-of-2 hash table
 * size, which is guaranteed to explore all spaces for each key (see
 * http://en.wikipedia.org/wiki/Quadratic_probing).
 * <p>
 * TODO: Cache the hash values of each key? java.util.HashMap does that.
 */
public  class AppendOnlyMap<K extends java.lang.Object, V extends java.lang.Object> implements scala.collection.Iterable<scala.Tuple2<K, V>>, scala.Serializable {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   AppendOnlyMap (int initialCapacity) { throw new RuntimeException(); }
  private  double LOAD_FACTOR () { throw new RuntimeException(); }
  private  int capacity () { throw new RuntimeException(); }
  private  int mask () { throw new RuntimeException(); }
  private  int curSize () { throw new RuntimeException(); }
  private  int growThreshold () { throw new RuntimeException(); }
  private  java.lang.Object[] data () { throw new RuntimeException(); }
  private  boolean haveNullValue () { throw new RuntimeException(); }
  private  V nullValue () { throw new RuntimeException(); }
  private  boolean destroyed () { throw new RuntimeException(); }
  private  java.lang.String destructionMessage () { throw new RuntimeException(); }
  /** Get the value for a given key */
  public  V apply (K key) { throw new RuntimeException(); }
  /** Set the value for a key */
  public  void update (K key, V value) { throw new RuntimeException(); }
  /**
   * Set the value for key to updateFunc(hadValue, oldValue), where oldValue will be the old value
   * for key, if any, or null otherwise. Returns the newly updated value.
   */
  public  V changeValue (K key, scala.Function2<java.lang.Object, V, V> updateFunc) { throw new RuntimeException(); }
  /** Iterator method from Iterable */
  public  scala.collection.Iterator<scala.Tuple2<K, V>> iterator () { throw new RuntimeException(); }
  public  int size () { throw new RuntimeException(); }
  /** Increase table size by 1, rehashing if necessary */
  private  void incrementSize () { throw new RuntimeException(); }
  /**
   * Re-hash a value to deal better with hash functions that don't differ in the lower bits.
   */
  private  int rehash (int h) { throw new RuntimeException(); }
  /** Double the table's size and re-hash everything */
  protected  void growTable () { throw new RuntimeException(); }
  private  int nextPowerOf2 (int n) { throw new RuntimeException(); }
  /**
   * Return an iterator of the map in sorted order. This provides a way to sort the map without
   * using additional memory, at the expense of destroying the validity of the map.
   */
  public  scala.collection.Iterator<scala.Tuple2<K, V>> destructiveSortedIterator (java.util.Comparator<K> keyComparator) { throw new RuntimeException(); }
  /**
   * Return whether the next insert will cause the map to grow
   */
  public  boolean atGrowThreshold () { throw new RuntimeException(); }
}
