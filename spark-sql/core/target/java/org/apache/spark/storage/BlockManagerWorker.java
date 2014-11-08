package org.apache.spark.storage;
/**
 * A network interface for BlockManager. Each slave should have one
 * BlockManagerWorker.
 * <p>
 * TODO: Use event model.
 */
private  class BlockManagerWorker implements org.apache.spark.Logging {
  static private  org.apache.spark.storage.BlockManagerWorker blockManagerWorker () { throw new RuntimeException(); }
  static public  void startBlockManagerWorker (org.apache.spark.storage.BlockManager manager) { throw new RuntimeException(); }
  static public  boolean syncPutBlock (org.apache.spark.storage.PutBlock msg, org.apache.spark.network.ConnectionManagerId toConnManagerId) { throw new RuntimeException(); }
  static public  java.nio.ByteBuffer syncGetBlock (org.apache.spark.storage.GetBlock msg, org.apache.spark.network.ConnectionManagerId toConnManagerId) { throw new RuntimeException(); }
  public  org.apache.spark.storage.BlockManager blockManager () { throw new RuntimeException(); }
  // not preceding
  public   BlockManagerWorker (org.apache.spark.storage.BlockManager blockManager) { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.network.Message> onBlockMessageReceive (org.apache.spark.network.Message msg, org.apache.spark.network.ConnectionManagerId id) { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.storage.BlockMessage> processBlockMessage (org.apache.spark.storage.BlockMessage blockMessage) { throw new RuntimeException(); }
  private  void putBlock (org.apache.spark.storage.BlockId id, java.nio.ByteBuffer bytes, org.apache.spark.storage.StorageLevel level) { throw new RuntimeException(); }
  private  java.nio.ByteBuffer getBlock (org.apache.spark.storage.BlockId id) { throw new RuntimeException(); }
}
