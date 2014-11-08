package org.apache.spark.storage;
public  class ShuffleIndexBlockId extends org.apache.spark.storage.BlockId implements scala.Product, scala.Serializable {
  public  int shuffleId () { throw new RuntimeException(); }
  public  int mapId () { throw new RuntimeException(); }
  public  int reduceId () { throw new RuntimeException(); }
  // not preceding
  public   ShuffleIndexBlockId (int shuffleId, int mapId, int reduceId) { throw new RuntimeException(); }
  public  java.lang.String name () { throw new RuntimeException(); }
}
