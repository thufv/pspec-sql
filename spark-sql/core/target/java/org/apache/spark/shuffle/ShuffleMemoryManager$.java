package org.apache.spark.shuffle;
// no position
private  class ShuffleMemoryManager$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final ShuffleMemoryManager$ MODULE$ = null;
  public   ShuffleMemoryManager$ () { throw new RuntimeException(); }
  /**
   * Figure out the shuffle memory limit from a SparkConf. We currently have both a fraction
   * of the memory pool and a safety factor since collections can sometimes grow bigger than
   * the size we target before we estimate their sizes again.
   */
  public  long getMaxMemory (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
}
