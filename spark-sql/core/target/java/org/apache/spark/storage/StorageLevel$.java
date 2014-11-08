package org.apache.spark.storage;
// no position
/**
 * Various {@link org.apache.spark.storage.StorageLevel} defined and utility functions for creating
 * new storage levels.
 */
public  class StorageLevel$ implements scala.Serializable {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final StorageLevel$ MODULE$ = null;
  public   StorageLevel$ () { throw new RuntimeException(); }
  public  org.apache.spark.storage.StorageLevel NONE () { throw new RuntimeException(); }
  public  org.apache.spark.storage.StorageLevel DISK_ONLY () { throw new RuntimeException(); }
  public  org.apache.spark.storage.StorageLevel DISK_ONLY_2 () { throw new RuntimeException(); }
  public  org.apache.spark.storage.StorageLevel MEMORY_ONLY () { throw new RuntimeException(); }
  public  org.apache.spark.storage.StorageLevel MEMORY_ONLY_2 () { throw new RuntimeException(); }
  public  org.apache.spark.storage.StorageLevel MEMORY_ONLY_SER () { throw new RuntimeException(); }
  public  org.apache.spark.storage.StorageLevel MEMORY_ONLY_SER_2 () { throw new RuntimeException(); }
  public  org.apache.spark.storage.StorageLevel MEMORY_AND_DISK () { throw new RuntimeException(); }
  public  org.apache.spark.storage.StorageLevel MEMORY_AND_DISK_2 () { throw new RuntimeException(); }
  public  org.apache.spark.storage.StorageLevel MEMORY_AND_DISK_SER () { throw new RuntimeException(); }
  public  org.apache.spark.storage.StorageLevel MEMORY_AND_DISK_SER_2 () { throw new RuntimeException(); }
  public  org.apache.spark.storage.StorageLevel OFF_HEAP () { throw new RuntimeException(); }
  /**
   * :: DeveloperApi ::
   * Return the StorageLevel object with the specified name.
   */
  public  org.apache.spark.storage.StorageLevel fromString (java.lang.String s) { throw new RuntimeException(); }
  /**
   * :: DeveloperApi ::
   * Create a new StorageLevel object without setting useOffHeap.
   */
  public  org.apache.spark.storage.StorageLevel apply (boolean useDisk, boolean useMemory, boolean useOffHeap, boolean deserialized, int replication) { throw new RuntimeException(); }
  /**
   * :: DeveloperApi ::
   * Create a new StorageLevel object.
   */
  public  org.apache.spark.storage.StorageLevel apply (boolean useDisk, boolean useMemory, boolean deserialized, int replication) { throw new RuntimeException(); }
  /**
   * :: DeveloperApi ::
   * Create a new StorageLevel object from its integer representation.
   */
  public  org.apache.spark.storage.StorageLevel apply (int flags, int replication) { throw new RuntimeException(); }
  /**
   * :: DeveloperApi ::
   * Read StorageLevel object from ObjectInput stream.
   */
  public  org.apache.spark.storage.StorageLevel apply (java.io.ObjectInput in) { throw new RuntimeException(); }
  /**
   * :: DeveloperApi ::
   * Read StorageLevel object from ObjectInput stream.
   */
  public  java.util.concurrent.ConcurrentHashMap<org.apache.spark.storage.StorageLevel, org.apache.spark.storage.StorageLevel> storageLevelCache () { throw new RuntimeException(); }
  private  org.apache.spark.storage.StorageLevel getCachedStorageLevel (org.apache.spark.storage.StorageLevel level) { throw new RuntimeException(); }
}
