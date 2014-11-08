package org.apache.spark.storage;
/**
 * A block fetcher iterator interface. There are two implementations:
 * <p>
 * BasicBlockFetcherIterator: uses a custom-built NIO communication layer.
 * NettyBlockFetcherIterator: uses Netty (OIO) as the communication layer.
 * <p>
 * Eventually we would like the two to converge and use a single NIO-based communication layer,
 * but extensive tests show that under some circumstances (e.g. large shuffles with lots of cores),
 * NIO would perform poorly and thus the need for the Netty OIO one.
 */
private  interface BlockFetcherIterator extends scala.collection.Iterator<scala.Tuple2<org.apache.spark.storage.BlockId, scala.Option<scala.collection.Iterator<java.lang.Object>>>>, org.apache.spark.Logging {
  /**
   * A request to fetch blocks from a remote BlockManager.
   * @param address remote BlockManager to fetch from.
   * @param blocks Sequence of tuple, where the first element is the block id,
   *               and the second element is the estimated size, used to calculate bytesInFlight.
   */
  static public  class FetchRequest {
    public  org.apache.spark.storage.BlockManagerId address () { throw new RuntimeException(); }
    public  scala.collection.Seq<scala.Tuple2<org.apache.spark.storage.BlockId, java.lang.Object>> blocks () { throw new RuntimeException(); }
    // not preceding
    public   FetchRequest (org.apache.spark.storage.BlockManagerId address, scala.collection.Seq<scala.Tuple2<org.apache.spark.storage.BlockId, java.lang.Object>> blocks) { throw new RuntimeException(); }
    public  long size () { throw new RuntimeException(); }
  }
  /**
   * Result of a fetch from a remote block. A failure is represented as size == -1.
   * @param blockId block id
   * @param size estimated size of the block, used to calculate bytesInFlight.
   *             Note that this is NOT the exact bytes.
   * @param deserialize closure to return the result in the form of an Iterator.
   */
  static public  class FetchResult {
    public  org.apache.spark.storage.BlockId blockId () { throw new RuntimeException(); }
    public  long size () { throw new RuntimeException(); }
    public  scala.Function0<scala.collection.Iterator<java.lang.Object>> deserialize () { throw new RuntimeException(); }
    // not preceding
    public   FetchResult (org.apache.spark.storage.BlockId blockId, long size, scala.Function0<scala.collection.Iterator<java.lang.Object>> deserialize) { throw new RuntimeException(); }
    public  boolean failed () { throw new RuntimeException(); }
  }
  static public  class BasicBlockFetcherIterator implements org.apache.spark.storage.BlockFetcherIterator {
    private  org.apache.spark.storage.BlockManager blockManager () { throw new RuntimeException(); }
    public  scala.collection.Seq<scala.Tuple2<org.apache.spark.storage.BlockManagerId, scala.collection.Seq<scala.Tuple2<org.apache.spark.storage.BlockId, java.lang.Object>>>> blocksByAddress () { throw new RuntimeException(); }
    // not preceding
    public   BasicBlockFetcherIterator (org.apache.spark.storage.BlockManager blockManager, scala.collection.Seq<scala.Tuple2<org.apache.spark.storage.BlockManagerId, scala.collection.Seq<scala.Tuple2<org.apache.spark.storage.BlockId, java.lang.Object>>>> blocksByAddress, org.apache.spark.serializer.Serializer serializer, org.apache.spark.executor.ShuffleReadMetrics readMetrics) { throw new RuntimeException(); }
    protected  int _numBlocksToFetch () { throw new RuntimeException(); }
    protected  long startTime () { throw new RuntimeException(); }
    protected  scala.collection.mutable.ArrayBuffer<org.apache.spark.storage.BlockId> localBlocksToFetch () { throw new RuntimeException(); }
    protected  scala.collection.mutable.HashSet<org.apache.spark.storage.BlockId> remoteBlocksToFetch () { throw new RuntimeException(); }
    protected  java.util.concurrent.LinkedBlockingQueue<org.apache.spark.storage.BlockFetcherIterator.FetchResult> results () { throw new RuntimeException(); }
    protected  scala.collection.mutable.Queue<org.apache.spark.storage.BlockFetcherIterator.FetchRequest> fetchRequests () { throw new RuntimeException(); }
    protected  long bytesInFlight () { throw new RuntimeException(); }
    protected  void sendRequest (org.apache.spark.storage.BlockFetcherIterator.FetchRequest req) { throw new RuntimeException(); }
    protected  scala.collection.mutable.ArrayBuffer<org.apache.spark.storage.BlockFetcherIterator.FetchRequest> splitLocalRemoteBlocks () { throw new RuntimeException(); }
    protected  void getLocalBlocks () { throw new RuntimeException(); }
    public  void initialize () { throw new RuntimeException(); }
    protected  int resultsGotten () { throw new RuntimeException(); }
    public  boolean hasNext () { throw new RuntimeException(); }
    public  scala.Tuple2<org.apache.spark.storage.BlockId, scala.Option<scala.collection.Iterator<java.lang.Object>>> next () { throw new RuntimeException(); }
  }
  static public  class NettyBlockFetcherIterator extends org.apache.spark.storage.BlockFetcherIterator.BasicBlockFetcherIterator {
    public   NettyBlockFetcherIterator (org.apache.spark.storage.BlockManager blockManager, scala.collection.Seq<scala.Tuple2<org.apache.spark.storage.BlockManagerId, scala.collection.Seq<scala.Tuple2<org.apache.spark.storage.BlockId, java.lang.Object>>>> blocksByAddress, org.apache.spark.serializer.Serializer serializer, org.apache.spark.executor.ShuffleReadMetrics readMetrics) { throw new RuntimeException(); }
    protected  void sendRequest (org.apache.spark.storage.BlockFetcherIterator.FetchRequest req) { throw new RuntimeException(); }
  }
  public abstract  void initialize () ;
}
