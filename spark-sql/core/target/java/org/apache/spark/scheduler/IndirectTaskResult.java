package org.apache.spark.scheduler;
/** A reference to a DirectTaskResult that has been stored in the worker's BlockManager. */
private  class IndirectTaskResult<T extends java.lang.Object> implements org.apache.spark.scheduler.TaskResult<T>, java.io.Serializable, scala.Product, scala.Serializable {
  public  org.apache.spark.storage.BlockId blockId () { throw new RuntimeException(); }
  // not preceding
  public   IndirectTaskResult (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
}
