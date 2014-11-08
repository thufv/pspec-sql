package org.apache.spark.storage;
// no position
private  class BlockManagerWorker$ implements org.apache.spark.Logging {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final BlockManagerWorker$ MODULE$ = null;
  public   BlockManagerWorker$ () { throw new RuntimeException(); }
  private  org.apache.spark.storage.BlockManagerWorker blockManagerWorker () { throw new RuntimeException(); }
  public  void startBlockManagerWorker (org.apache.spark.storage.BlockManager manager) { throw new RuntimeException(); }
  public  boolean syncPutBlock (org.apache.spark.storage.PutBlock msg, org.apache.spark.network.ConnectionManagerId toConnManagerId) { throw new RuntimeException(); }
  public  java.nio.ByteBuffer syncGetBlock (org.apache.spark.storage.GetBlock msg, org.apache.spark.network.ConnectionManagerId toConnManagerId) { throw new RuntimeException(); }
}
