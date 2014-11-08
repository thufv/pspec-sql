package org.apache.spark.storage;
private  class ResultWithDroppedBlocks implements scala.Product, scala.Serializable {
  public  boolean success () { throw new RuntimeException(); }
  public  scala.collection.Seq<scala.Tuple2<org.apache.spark.storage.BlockId, org.apache.spark.storage.BlockStatus>> droppedBlocks () { throw new RuntimeException(); }
  // not preceding
  public   ResultWithDroppedBlocks (boolean success, scala.collection.Seq<scala.Tuple2<org.apache.spark.storage.BlockId, org.apache.spark.storage.BlockStatus>> droppedBlocks) { throw new RuntimeException(); }
}
