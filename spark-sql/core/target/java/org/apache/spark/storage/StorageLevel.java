package org.apache.spark.storage;
/**
 * :: DeveloperApi ::
 * Flags for controlling the storage of an RDD. Each StorageLevel records whether to use memory,
 * or Tachyon, whether to drop the RDD to disk if it falls out of memory or Tachyon , whether to
 * keep the data in memory in a serialized format, and whether to replicate the RDD partitions on
 * multiple nodes.
 * <p>
 * The {@link org.apache.spark.storage.StorageLevel$} singleton object contains some static constants
 * for commonly useful storage levels. To create your own storage level object, use the
 * factory method of the singleton object (<code>StorageLevel(...)</code>).
 */
public  class StorageLevel implements java.io.Externalizable {
  static public  org.apache.spark.storage.StorageLevel NONE () { throw new RuntimeException(); }
  static public  org.apache.spark.storage.StorageLevel DISK_ONLY () { throw new RuntimeException(); }
  static public  org.apache.spark.storage.StorageLevel DISK_ONLY_2 () { throw new RuntimeException(); }
  static public  org.apache.spark.storage.StorageLevel MEMORY_ONLY () { throw new RuntimeException(); }
  static public  org.apache.spark.storage.StorageLevel MEMORY_ONLY_2 () { throw new RuntimeException(); }
  static public  org.apache.spark.storage.StorageLevel MEMORY_ONLY_SER () { throw new RuntimeException(); }
  static public  org.apache.spark.storage.StorageLevel MEMORY_ONLY_SER_2 () { throw new RuntimeException(); }
  static public  org.apache.spark.storage.StorageLevel MEMORY_AND_DISK () { throw new RuntimeException(); }
  static public  org.apache.spark.storage.StorageLevel MEMORY_AND_DISK_2 () { throw new RuntimeException(); }
  static public  org.apache.spark.storage.StorageLevel MEMORY_AND_DISK_SER () { throw new RuntimeException(); }
  static public  org.apache.spark.storage.StorageLevel MEMORY_AND_DISK_SER_2 () { throw new RuntimeException(); }
  static public  org.apache.spark.storage.StorageLevel OFF_HEAP () { throw new RuntimeException(); }
  /**
   * :: DeveloperApi ::
   * Return the StorageLevel object with the specified name.
   */
  static public  org.apache.spark.storage.StorageLevel fromString (java.lang.String s) { throw new RuntimeException(); }
  /**
   * :: DeveloperApi ::
   * Create a new StorageLevel object without setting useOffHeap.
   */
  static public  org.apache.spark.storage.StorageLevel apply (boolean useDisk, boolean useMemory, boolean useOffHeap, boolean deserialized, int replication) { throw new RuntimeException(); }
  /**
   * :: DeveloperApi ::
   * Create a new StorageLevel object.
   */
  static public  org.apache.spark.storage.StorageLevel apply (boolean useDisk, boolean useMemory, boolean deserialized, int replication) { throw new RuntimeException(); }
  /**
   * :: DeveloperApi ::
   * Create a new StorageLevel object from its integer representation.
   */
  static public  org.apache.spark.storage.StorageLevel apply (int flags, int replication) { throw new RuntimeException(); }
  /**
   * :: DeveloperApi ::
   * Read StorageLevel object from ObjectInput stream.
   */
  static public  org.apache.spark.storage.StorageLevel apply (java.io.ObjectInput in) { throw new RuntimeException(); }
  /**
   * :: DeveloperApi ::
   * Read StorageLevel object from ObjectInput stream.
   */
  static public  java.util.concurrent.ConcurrentHashMap<org.apache.spark.storage.StorageLevel, org.apache.spark.storage.StorageLevel> storageLevelCache () { throw new RuntimeException(); }
  static private  org.apache.spark.storage.StorageLevel getCachedStorageLevel (org.apache.spark.storage.StorageLevel level) { throw new RuntimeException(); }
  private  boolean _useDisk () { throw new RuntimeException(); }
  private  boolean _useMemory () { throw new RuntimeException(); }
  private  boolean _useOffHeap () { throw new RuntimeException(); }
  private  boolean _deserialized () { throw new RuntimeException(); }
  private  int _replication () { throw new RuntimeException(); }
  // not preceding
  private   StorageLevel (boolean _useDisk, boolean _useMemory, boolean _useOffHeap, boolean _deserialized, int _replication) { throw new RuntimeException(); }
  private   StorageLevel (int flags, int replication) { throw new RuntimeException(); }
  public   StorageLevel () { throw new RuntimeException(); }
  public  boolean useDisk () { throw new RuntimeException(); }
  public  boolean useMemory () { throw new RuntimeException(); }
  public  boolean useOffHeap () { throw new RuntimeException(); }
  public  boolean deserialized () { throw new RuntimeException(); }
  public  int replication () { throw new RuntimeException(); }
  public  org.apache.spark.storage.StorageLevel clone () { throw new RuntimeException(); }
  public  boolean equals (Object other) { throw new RuntimeException(); }
  public  boolean isValid () { throw new RuntimeException(); }
  public  int toInt () { throw new RuntimeException(); }
  public  void writeExternal (java.io.ObjectOutput out) { throw new RuntimeException(); }
  public  void readExternal (java.io.ObjectInput in) { throw new RuntimeException(); }
  private  java.lang.Object readResolve () { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
  public  int hashCode () { throw new RuntimeException(); }
  public  java.lang.String description () { throw new RuntimeException(); }
}
