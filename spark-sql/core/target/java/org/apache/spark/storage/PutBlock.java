package org.apache.spark.storage;
private  class PutBlock implements scala.Product, scala.Serializable {
  public  org.apache.spark.storage.BlockId id () { throw new RuntimeException(); }
  public  java.nio.ByteBuffer data () { throw new RuntimeException(); }
  public  org.apache.spark.storage.StorageLevel level () { throw new RuntimeException(); }
  // not preceding
  public   PutBlock (org.apache.spark.storage.BlockId id, java.nio.ByteBuffer data, org.apache.spark.storage.StorageLevel level) { throw new RuntimeException(); }
}
