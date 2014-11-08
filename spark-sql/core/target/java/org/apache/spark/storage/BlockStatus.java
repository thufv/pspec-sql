package org.apache.spark.storage;
public  class BlockStatus implements scala.Product, scala.Serializable {
  static public  org.apache.spark.storage.BlockStatus empty () { throw new RuntimeException(); }
  public  org.apache.spark.storage.StorageLevel storageLevel () { throw new RuntimeException(); }
  public  long memSize () { throw new RuntimeException(); }
  public  long diskSize () { throw new RuntimeException(); }
  public  long tachyonSize () { throw new RuntimeException(); }
  // not preceding
  public   BlockStatus (org.apache.spark.storage.StorageLevel storageLevel, long memSize, long diskSize, long tachyonSize) { throw new RuntimeException(); }
  public  boolean isCached () { throw new RuntimeException(); }
}
