package org.apache.spark.scheduler;
/**
 * Result returned by a ShuffleMapTask to a scheduler. Includes the block manager address that the
 * task ran on as well as the sizes of outputs for each reducer, for passing on to the reduce tasks.
 * The map output sizes are compressed using MapOutputTracker.compressSize.
 */
private  class MapStatus implements java.io.Externalizable {
  public  org.apache.spark.storage.BlockManagerId location () { throw new RuntimeException(); }
  public  byte[] compressedSizes () { throw new RuntimeException(); }
  // not preceding
  public   MapStatus (org.apache.spark.storage.BlockManagerId location, byte[] compressedSizes) { throw new RuntimeException(); }
  public   MapStatus () { throw new RuntimeException(); }
  public  void writeExternal (java.io.ObjectOutput out) { throw new RuntimeException(); }
  public  void readExternal (java.io.ObjectInput in) { throw new RuntimeException(); }
}
