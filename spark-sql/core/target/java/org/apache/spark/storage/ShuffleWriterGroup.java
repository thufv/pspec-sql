package org.apache.spark.storage;
/** A group of writers for a ShuffleMapTask, one writer per reducer. */
private  interface ShuffleWriterGroup {
  public abstract  org.apache.spark.storage.BlockObjectWriter[] writers () ;
  /** @param success Indicates all writes were successful. If false, no blocks will be recorded. */
  public abstract  void releaseWriters (boolean success) ;
}
