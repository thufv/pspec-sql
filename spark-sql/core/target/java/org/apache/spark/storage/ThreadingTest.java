package org.apache.spark.storage;
// no position
/**
 * This class tests the BlockManager and MemoryStore for thread safety and
 * deadlocks. It spawns a number of producer and consumer threads. Producer
 * threads continuously pushes blocks into the BlockManager and consumer
 * threads continuously retrieves the blocks form the BlockManager and tests
 * whether the block is correct or not.
 */
private  class ThreadingTest {
  static private  class ProducerThread extends java.lang.Thread {
    public   ProducerThread (org.apache.spark.storage.BlockManager manager, int id) { throw new RuntimeException(); }
    public  java.util.concurrent.ArrayBlockingQueue<scala.Tuple2<org.apache.spark.storage.BlockId, scala.collection.Seq<java.lang.Object>>> queue () { throw new RuntimeException(); }
    public  void run () { throw new RuntimeException(); }
    public  org.apache.spark.storage.StorageLevel randomLevel () { throw new RuntimeException(); }
  }
  static private  class ConsumerThread extends java.lang.Thread {
    public   ConsumerThread (org.apache.spark.storage.BlockManager manager, java.util.concurrent.ArrayBlockingQueue<scala.Tuple2<org.apache.spark.storage.BlockId, scala.collection.Seq<java.lang.Object>>> queue) { throw new RuntimeException(); }
    public  int numBlockConsumed () { throw new RuntimeException(); }
    public  void run () { throw new RuntimeException(); }
  }
  static public  int numProducers () { throw new RuntimeException(); }
  static public  int numBlocksPerProducer () { throw new RuntimeException(); }
  static public  void main (java.lang.String[] args) { throw new RuntimeException(); }
}
