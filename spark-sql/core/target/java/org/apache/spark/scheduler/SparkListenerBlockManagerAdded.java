package org.apache.spark.scheduler;
public  class SparkListenerBlockManagerAdded implements org.apache.spark.scheduler.SparkListenerEvent, scala.Product, scala.Serializable {
  public  org.apache.spark.storage.BlockManagerId blockManagerId () { throw new RuntimeException(); }
  public  long maxMem () { throw new RuntimeException(); }
  // not preceding
  public   SparkListenerBlockManagerAdded (org.apache.spark.storage.BlockManagerId blockManagerId, long maxMem) { throw new RuntimeException(); }
}
