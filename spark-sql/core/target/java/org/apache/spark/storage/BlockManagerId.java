package org.apache.spark.storage;
/**
 * :: DeveloperApi ::
 * This class represent an unique identifier for a BlockManager.
 * <p>
 * The first 2 constructors of this class is made private to ensure that BlockManagerId objects
 * can be created only using the apply method in the companion object. This allows de-duplication
 * of ID objects. Also, constructor parameters are private to ensure that parameters cannot be
 * modified from outside this class.
 */
public  class BlockManagerId implements java.io.Externalizable {
  /**
   * Returns a {@link org.apache.spark.storage.BlockManagerId} for the given configuration.
   * <p>
   * @param execId ID of the executor.
   * @param host Host name of the block manager.
   * @param port Port of the block manager.
   * @param nettyPort Optional port for the Netty-based shuffle sender.
   * @return A new {@link org.apache.spark.storage.BlockManagerId}.
   */
  static public  org.apache.spark.storage.BlockManagerId apply (java.lang.String execId, java.lang.String host, int port, int nettyPort) { throw new RuntimeException(); }
  static public  org.apache.spark.storage.BlockManagerId apply (java.io.ObjectInput in) { throw new RuntimeException(); }
  static public  java.util.concurrent.ConcurrentHashMap<org.apache.spark.storage.BlockManagerId, org.apache.spark.storage.BlockManagerId> blockManagerIdCache () { throw new RuntimeException(); }
  static public  org.apache.spark.storage.BlockManagerId getCachedBlockManagerId (org.apache.spark.storage.BlockManagerId id) { throw new RuntimeException(); }
  private  java.lang.String executorId_ () { throw new RuntimeException(); }
  private  java.lang.String host_ () { throw new RuntimeException(); }
  private  int port_ () { throw new RuntimeException(); }
  private  int nettyPort_ () { throw new RuntimeException(); }
  // not preceding
  private   BlockManagerId (java.lang.String executorId_, java.lang.String host_, int port_, int nettyPort_) { throw new RuntimeException(); }
  private   BlockManagerId () { throw new RuntimeException(); }
  public  java.lang.String executorId () { throw new RuntimeException(); }
  public  java.lang.String hostPort () { throw new RuntimeException(); }
  public  java.lang.String host () { throw new RuntimeException(); }
  public  int port () { throw new RuntimeException(); }
  public  int nettyPort () { throw new RuntimeException(); }
  public  void writeExternal (java.io.ObjectOutput out) { throw new RuntimeException(); }
  public  void readExternal (java.io.ObjectInput in) { throw new RuntimeException(); }
  private  java.lang.Object readResolve () { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
  public  int hashCode () { throw new RuntimeException(); }
  public  boolean equals (Object that) { throw new RuntimeException(); }
}
