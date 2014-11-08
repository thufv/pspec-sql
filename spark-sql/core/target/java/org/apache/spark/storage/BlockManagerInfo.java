package org.apache.spark.storage;
private  class BlockManagerInfo implements org.apache.spark.Logging {
  public  org.apache.spark.storage.BlockManagerId blockManagerId () { throw new RuntimeException(); }
  public  long maxMem () { throw new RuntimeException(); }
  public  akka.actor.ActorRef slaveActor () { throw new RuntimeException(); }
  // not preceding
  public   BlockManagerInfo (org.apache.spark.storage.BlockManagerId blockManagerId, long timeMs, long maxMem, akka.actor.ActorRef slaveActor) { throw new RuntimeException(); }
  private  long _lastSeenMs () { throw new RuntimeException(); }
  private  long _remainingMem () { throw new RuntimeException(); }
  private  java.util.HashMap<org.apache.spark.storage.BlockId, org.apache.spark.storage.BlockStatus> _blocks () { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.storage.BlockStatus> getStatus (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
  public  void updateLastSeenMs () { throw new RuntimeException(); }
  public  void updateBlockInfo (org.apache.spark.storage.BlockId blockId, org.apache.spark.storage.StorageLevel storageLevel, long memSize, long diskSize, long tachyonSize) { throw new RuntimeException(); }
  public  void removeBlock (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
  public  long remainingMem () { throw new RuntimeException(); }
  public  long lastSeenMs () { throw new RuntimeException(); }
  public  java.util.HashMap<org.apache.spark.storage.BlockId, org.apache.spark.storage.BlockStatus> blocks () { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
  public  void clear () { throw new RuntimeException(); }
}
