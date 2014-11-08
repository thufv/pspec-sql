package org.apache.spark;
/**
 * An asynchronous cleaner for RDD, shuffle, and broadcast state.
 * <p>
 * This maintains a weak reference for each RDD, ShuffleDependency, and Broadcast of interest,
 * to be processed when the associated object goes out of scope of the application. Actual
 * cleanup is performed in a separate daemon thread.
 */
private  class ContextCleaner implements org.apache.spark.Logging {
  static private  int REF_QUEUE_POLL_TIMEOUT () { throw new RuntimeException(); }
  public   ContextCleaner (org.apache.spark.SparkContext sc) { throw new RuntimeException(); }
  private  scala.collection.mutable.ArrayBuffer<org.apache.spark.CleanupTaskWeakReference> referenceBuffer () { throw new RuntimeException(); }
  private  java.lang.ref.ReferenceQueue<java.lang.Object> referenceQueue () { throw new RuntimeException(); }
  private  scala.collection.mutable.ArrayBuffer<org.apache.spark.CleanerListener> listeners () { throw new RuntimeException(); }
  private  java.lang.Thread cleaningThread () { throw new RuntimeException(); }
  /**
   * Whether the cleaning thread will block on cleanup tasks (other than shuffle, which
   * is controlled by the <code>spark.cleaner.referenceTracking.blocking.shuffle</code> parameter).
   * <p>
   * Due to SPARK-3015, this is set to true by default. This is intended to be only a temporary
   * workaround for the issue, which is ultimately caused by the way the BlockManager actors
   * issue inter-dependent blocking Akka messages to each other at high frequencies. This happens,
   * for instance, when the driver performs a GC and cleans up all broadcast blocks that are no
   * longer in scope.
   */
  private  boolean blockOnCleanupTasks () { throw new RuntimeException(); }
  /**
   * Whether the cleaning thread will block on shuffle cleanup tasks.
   * <p>
   * When context cleaner is configured to block on every delete request, it can throw timeout
   * exceptions on cleanup of shuffle blocks, as reported in SPARK-3139. To avoid that, this
   * parameter by default disables blocking on shuffle cleanups. Note that this does not affect
   * the cleanup of RDDs and broadcasts. This is intended to be a temporary workaround,
   * until the real Akka issue (referred to in the comment above <code>blockOnCleanupTasks</code>) is
   * resolved.
   */
  private  boolean blockOnShuffleCleanupTasks () { throw new RuntimeException(); }
  private  boolean stopped () { throw new RuntimeException(); }
  /** Attach a listener object to get information of when objects are cleaned. */
  public  void attachListener (org.apache.spark.CleanerListener listener) { throw new RuntimeException(); }
  /** Start the cleaner. */
  public  void start () { throw new RuntimeException(); }
  /** Stop the cleaner. */
  public  void stop () { throw new RuntimeException(); }
  /** Register a RDD for cleanup when it is garbage collected. */
  public  void registerRDDForCleanup (org.apache.spark.rdd.RDD<?> rdd) { throw new RuntimeException(); }
  /** Register a ShuffleDependency for cleanup when it is garbage collected. */
  public  void registerShuffleForCleanup (org.apache.spark.ShuffleDependency<?, ?, ?> shuffleDependency) { throw new RuntimeException(); }
  /** Register a Broadcast for cleanup when it is garbage collected. */
  public <T extends java.lang.Object> void registerBroadcastForCleanup (org.apache.spark.broadcast.Broadcast<T> broadcast) { throw new RuntimeException(); }
  /** Register an object for cleanup. */
  private  void registerForCleanup (java.lang.Object objectForCleanup, org.apache.spark.CleanupTask task) { throw new RuntimeException(); }
  /** Keep cleaning RDD, shuffle, and broadcast state. */
  private  void keepCleaning () { throw new RuntimeException(); }
  /** Perform RDD cleanup. */
  public  void doCleanupRDD (int rddId, boolean blocking) { throw new RuntimeException(); }
  /** Perform shuffle cleanup, asynchronously. */
  public  void doCleanupShuffle (int shuffleId, boolean blocking) { throw new RuntimeException(); }
  /** Perform broadcast cleanup. */
  public  void doCleanupBroadcast (long broadcastId, boolean blocking) { throw new RuntimeException(); }
  private  org.apache.spark.storage.BlockManagerMaster blockManagerMaster () { throw new RuntimeException(); }
  private  org.apache.spark.broadcast.BroadcastManager broadcastManager () { throw new RuntimeException(); }
  private  org.apache.spark.MapOutputTrackerMaster mapOutputTrackerMaster () { throw new RuntimeException(); }
}
