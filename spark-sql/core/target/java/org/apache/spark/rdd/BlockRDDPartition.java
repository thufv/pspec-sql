package org.apache.spark.rdd;
private  class BlockRDDPartition implements org.apache.spark.Partition {
  public  org.apache.spark.storage.BlockId blockId () { throw new RuntimeException(); }
  // not preceding
  public   BlockRDDPartition (org.apache.spark.storage.BlockId blockId, int idx) { throw new RuntimeException(); }
  public  int index () { throw new RuntimeException(); }
}
