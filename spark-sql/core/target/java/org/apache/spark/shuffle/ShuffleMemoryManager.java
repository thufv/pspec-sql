package org.apache.spark.shuffle;
/**
 * Allocates a pool of memory to task threads for use in shuffle operations. Each disk-spilling
 * collection (ExternalAppendOnlyMap or ExternalSorter) used by these tasks can acquire memory
 * from this pool and release it as it spills data out. When a task ends, all its memory will be
 * released by the Executor.
 * <p>
 * This class tries to ensure that each thread gets a reasonable share of memory, instead of some
 * thread ramping up to a large amount first and then causing others to spill to disk repeatedly.
 * If there are N threads, it ensures that each thread can acquire at least 1 / 2N of the memory
 * before it has to spill, and at most 1 / N. Because N varies dynamically, we keep track of the
 * set of active threads and redo the calculations of 1 / 2N and 1 / N in waiting threads whenever
 * this set changes. This is all done by synchronizing access on "this" to mutate state and using
 * wait() and notifyAll() to signal changes.
 */
private  class ShuffleMemoryManager implements org.apache.spark.Logging {
  /**
   * Figure out the shuffle memory limit from a SparkConf. We currently have both a fraction
   * of the memory pool and a safety factor since collections can sometimes grow bigger than
   * the size we target before we estimate their sizes again.
   */
  static public  long getMaxMemory (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  public   ShuffleMemoryManager (long maxMemory) { throw new RuntimeException(); }
  private  scala.collection.mutable.HashMap<java.lang.Object, java.lang.Object> threadMemory () { throw new RuntimeException(); }
  public   ShuffleMemoryManager (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  /**
   * Try to acquire up to numBytes memory for the current thread, and return the number of bytes
   * obtained, or 0 if none can be allocated. This call may block until there is enough free memory
   * in some situations, to make sure each thread has a chance to ramp up to at least 1 / 2N of the
   * total memory pool (where N is the # of active threads) before it is forced to spill. This can
   * happen if the number of threads increases but an older thread had a lot of memory already.
   */
  public  long tryToAcquire (long numBytes) { throw new RuntimeException(); }
  /** Release numBytes bytes for the current thread. */
  public  void release (long numBytes) { throw new RuntimeException(); }
  /** Release all memory for the current thread and mark it as inactive (e.g. when a task ends). */
  public  void releaseMemoryForThisThread () { throw new RuntimeException(); }
}
