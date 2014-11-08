package org.apache.spark.storage;
// no position
/**
 * This class tests the BlockManager and MemoryStore for thread safety and
 * deadlocks. It spawns a number of producer and consumer threads. Producer
 * threads continuously pushes blocks into the BlockManager and consumer
 * threads continuously retrieves the blocks form the BlockManager and tests
 * whether the block is correct or not.
 */
private  class ThreadingTest$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final ThreadingTest$ MODULE$ = null;
  public   ThreadingTest$ () { throw new RuntimeException(); }
  public  int numProducers () { throw new RuntimeException(); }
  public  int numBlocksPerProducer () { throw new RuntimeException(); }
  public  void main (java.lang.String[] args) { throw new RuntimeException(); }
}
