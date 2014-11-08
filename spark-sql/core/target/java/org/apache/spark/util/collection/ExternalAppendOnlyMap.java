package org.apache.spark.util.collection;
/**
 * :: DeveloperApi ::
 * An append-only map that spills sorted content to disk when there is insufficient space for it
 * to grow.
 * <p>
 * This map takes two passes over the data:
 * <p>
 *   (1) Values are merged into combiners, which are sorted and spilled to disk as necessary
 *   (2) Combiners are read from disk and merged together
 * <p>
 * The setting of the spill threshold faces the following trade-off: If the spill threshold is
 * too high, the in-memory map may occupy more memory than is available, resulting in OOM.
 * However, if the spill threshold is too low, we spill frequently and incur unnecessary disk
 * writes. This may lead to a performance regression compared to the normal case of using the
 * non-spilling AppendOnlyMap.
 * <p>
 * Two parameters control the memory threshold:
 * <p>
 *   <code>spark.shuffle.memoryFraction</code> specifies the collective amount of memory used for storing
 *   these maps as a fraction of the executor's total memory. Since each concurrently running
 *   task maintains one map, the actual threshold for each map is this quantity divided by the
 *   number of running tasks.
 * <p>
 *   <code>spark.shuffle.safetyFraction</code> specifies an additional margin of safety as a fraction of
 *   this threshold, in case map size estimation is not sufficiently accurate.
 */
public  class ExternalAppendOnlyMap<K extends java.lang.Object, V extends java.lang.Object, C extends java.lang.Object> implements scala.collection.Iterable<scala.Tuple2<K, C>>, java.io.Serializable, org.apache.spark.Logging {
  /**
   * An iterator that sort-merges (K, C) pairs from the in-memory map and the spilled maps
   */
  private  class ExternalIterator implements scala.collection.Iterator<scala.Tuple2<K, C>> {
    public   ExternalIterator () { throw new RuntimeException(); }
    private  scala.collection.mutable.PriorityQueue<org.apache.spark.util.collection.ExternalAppendOnlyMap<K, V, C>.ExternalIterator.StreamBuffer> mergeHeap () { throw new RuntimeException(); }
    private  scala.collection.Iterator<scala.Tuple2<K, C>> sortedMap () { throw new RuntimeException(); }
    private  scala.collection.Seq<scala.collection.BufferedIterator<scala.Tuple2<K, C>>> inputStreams () { throw new RuntimeException(); }
    /**
     * Fill a buffer with the next set of keys with the same hash code from a given iterator. We
     * read streams one hash code at a time to ensure we don't miss elements when they are merged.
     * <p>
     * Assumes the given iterator is in sorted order of hash code.
     * <p>
     * @param it iterator to read from
     * @param buf buffer to write the results into
     */
    private  void readNextHashCode (scala.collection.BufferedIterator<scala.Tuple2<K, C>> it, scala.collection.mutable.ArrayBuffer<scala.Tuple2<K, C>> buf) { throw new RuntimeException(); }
    /**
     * If the given buffer contains a value for the given key, merge that value into
     * baseCombiner and remove the corresponding (K, C) pair from the buffer.
     */
    private  C mergeIfKeyExists (K key, C baseCombiner, org.apache.spark.util.collection.ExternalAppendOnlyMap<K, V, C>.ExternalIterator.StreamBuffer buffer) { throw new RuntimeException(); }
    /**
     * Remove the index'th element from an ArrayBuffer in constant time, swapping another element
     * into its place. This is more efficient than the ArrayBuffer.remove method because it does
     * not have to shift all the elements in the array over. It works for our array buffers because
     * we don't care about the order of elements inside, we just want to search them for a key.
     */
    private <T extends java.lang.Object> T removeFromBuffer (scala.collection.mutable.ArrayBuffer<T> buffer, int index) { throw new RuntimeException(); }
    /**
     * Return true if there exists an input stream that still has unvisited pairs.
     */
    public  boolean hasNext () { throw new RuntimeException(); }
    /**
     * Select a key with the minimum hash, then combine all values with the same key from all
     * input streams.
     */
    public  scala.Tuple2<K, C> next () { throw new RuntimeException(); }
    /**
     * A buffer for streaming from a map iterator (in-memory or on-disk) sorted by key hash.
     * Each buffer maintains all of the key-value pairs with what is currently the lowest hash
     * code among keys in the stream. There may be multiple keys if there are hash collisions.
     * Note that because when we spill data out, we only spill one value for each key, there is
     * at most one element for each key.
     * <p>
     * StreamBuffers are ordered by the minimum key hash currently available in their stream so
     * that we can put them into a heap and sort that.
     */
    private  class StreamBuffer implements java.lang.Comparable<org.apache.spark.util.collection.ExternalAppendOnlyMap<K, V, C>.ExternalIterator.StreamBuffer> {
      public  scala.collection.BufferedIterator<scala.Tuple2<K, C>> iterator () { throw new RuntimeException(); }
      public  scala.collection.mutable.ArrayBuffer<scala.Tuple2<K, C>> pairs () { throw new RuntimeException(); }
      // not preceding
      public   StreamBuffer (scala.collection.BufferedIterator<scala.Tuple2<K, C>> iterator, scala.collection.mutable.ArrayBuffer<scala.Tuple2<K, C>> pairs) { throw new RuntimeException(); }
      public  boolean isEmpty () { throw new RuntimeException(); }
      public  int minKeyHash () { throw new RuntimeException(); }
      public  int compareTo (org.apache.spark.util.collection.ExternalAppendOnlyMap<K, V, C>.ExternalIterator.StreamBuffer other) { throw new RuntimeException(); }
    }
  }
  /**
   * An iterator that returns (K, C) pairs in sorted order from an on-disk map
   */
  private  class DiskMapIterator implements scala.collection.Iterator<scala.Tuple2<K, C>> {
    public   DiskMapIterator (java.io.File file, org.apache.spark.storage.BlockId blockId, scala.collection.mutable.ArrayBuffer<java.lang.Object> batchSizes) { throw new RuntimeException(); }
    private  scala.collection.mutable.ArrayBuffer<java.lang.Object> batchOffsets () { throw new RuntimeException(); }
    private  int batchIndex () { throw new RuntimeException(); }
    private  java.io.FileInputStream fileStream () { throw new RuntimeException(); }
    private  org.apache.spark.serializer.DeserializationStream deserializeStream () { throw new RuntimeException(); }
    private  scala.Tuple2<K, C> nextItem () { throw new RuntimeException(); }
    private  int objectsRead () { throw new RuntimeException(); }
    /**
     * Construct a stream that reads only from the next batch.
     */
    private  org.apache.spark.serializer.DeserializationStream nextBatchStream () { throw new RuntimeException(); }
    /**
     * Return the next (K, C) pair from the deserialization stream.
     * <p>
     * If the current batch is drained, construct a stream for the next batch and read from it.
     * If no more pairs are left, return null.
     */
    private  scala.Tuple2<K, C> readNextItem () { throw new RuntimeException(); }
    public  boolean hasNext () { throw new RuntimeException(); }
    public  scala.Tuple2<K, C> next () { throw new RuntimeException(); }
    private  void cleanup () { throw new RuntimeException(); }
  }
  /**
   * A comparator which sorts arbitrary keys based on their hash codes.
   */
  static private  class HashComparator<K extends java.lang.Object> implements java.util.Comparator<K> {
    // not preceding
    // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
    public   HashComparator () { throw new RuntimeException(); }
    public  int compare (K key1, K key2) { throw new RuntimeException(); }
  }
  /**
   * Return the hash code of the given object. If the object is null, return a special hash code.
   */
  static private <T extends java.lang.Object> int hash (T obj) { throw new RuntimeException(); }
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   ExternalAppendOnlyMap (scala.Function1<V, C> createCombiner, scala.Function2<C, V, C> mergeValue, scala.Function2<C, C, C> mergeCombiners, org.apache.spark.serializer.Serializer serializer, org.apache.spark.storage.BlockManager blockManager) { throw new RuntimeException(); }
  private  org.apache.spark.util.collection.SizeTrackingAppendOnlyMap<K, C> currentMap () { throw new RuntimeException(); }
  private  scala.collection.mutable.ArrayBuffer<org.apache.spark.util.collection.ExternalAppendOnlyMap<K, V, C>.DiskMapIterator> spilledMaps () { throw new RuntimeException(); }
  private  org.apache.spark.SparkConf sparkConf () { throw new RuntimeException(); }
  private  org.apache.spark.storage.DiskBlockManager diskBlockManager () { throw new RuntimeException(); }
  private  org.apache.spark.shuffle.ShuffleMemoryManager shuffleMemoryManager () { throw new RuntimeException(); }
  private  long elementsRead () { throw new RuntimeException(); }
  private  int trackMemoryThreshold () { throw new RuntimeException(); }
  private  long myMemoryThreshold () { throw new RuntimeException(); }
  /**
   * Size of object batches when reading/writing from serializers.
   * <p>
   * Objects are written in batches, with each batch using its own serialization stream. This
   * cuts down on the size of reference-tracking maps constructed when deserializing a stream.
   * <p>
   * NOTE: Setting this too low can cause excessive copying when serializing, since some serializers
   * grow internal data structures by growing + copying every time the number of objects doubles.
   */
  private  long serializerBatchSize () { throw new RuntimeException(); }
  private  int spillCount () { throw new RuntimeException(); }
  private  long _memoryBytesSpilled () { throw new RuntimeException(); }
  private  long _diskBytesSpilled () { throw new RuntimeException(); }
  private  int fileBufferSize () { throw new RuntimeException(); }
  private  org.apache.spark.executor.ShuffleWriteMetrics curWriteMetrics () { throw new RuntimeException(); }
  private  org.apache.spark.util.collection.ExternalAppendOnlyMap.HashComparator<K> keyComparator () { throw new RuntimeException(); }
  private  org.apache.spark.serializer.SerializerInstance ser () { throw new RuntimeException(); }
  /**
   * Insert the given key and value into the map.
   */
  public  void insert (K key, V value) { throw new RuntimeException(); }
  /**
   * Insert the given iterator of keys and values into the map.
   * <p>
   * When the underlying map needs to grow, check if the global pool of shuffle memory has
   * enough room for this to happen. If so, allocate the memory required to grow the map;
   * otherwise, spill the in-memory map to disk.
   * <p>
   * The shuffle memory usage of the first trackMemoryThreshold entries is not tracked.
   */
  public  void insertAll (scala.collection.Iterator<scala.Product2<K, V>> entries) { throw new RuntimeException(); }
  /**
   * Insert the given iterable of keys and values into the map.
   * <p>
   * When the underlying map needs to grow, check if the global pool of shuffle memory has
   * enough room for this to happen. If so, allocate the memory required to grow the map;
   * otherwise, spill the in-memory map to disk.
   * <p>
   * The shuffle memory usage of the first trackMemoryThreshold entries is not tracked.
   */
  public  void insertAll (scala.collection.Iterable<scala.Product2<K, V>> entries) { throw new RuntimeException(); }
  /**
   * Sort the existing contents of the in-memory map and spill them to a temporary file on disk.
   */
  private  void spill (long mapSize) { throw new RuntimeException(); }
  public  long memoryBytesSpilled () { throw new RuntimeException(); }
  public  long diskBytesSpilled () { throw new RuntimeException(); }
  /**
   * Return an iterator that merges the in-memory map with the spilled maps.
   * If no spill has occurred, simply return the in-memory map's iterator.
   */
  public  scala.collection.Iterator<scala.Tuple2<K, C>> iterator () { throw new RuntimeException(); }
  /** Convenience function to hash the given (K, C) pair by the key. */
  private  int hashKey (scala.Tuple2<K, C> kc) { throw new RuntimeException(); }
}
