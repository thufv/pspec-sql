package org.apache.spark.rdd;
// no position
private  class HadoopRDD$ implements scala.Serializable {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final HadoopRDD$ MODULE$ = null;
  public   HadoopRDD$ () { throw new RuntimeException(); }
  /** Constructing Configuration objects is not threadsafe, use this lock to serialize. */
  public  java.lang.Object CONFIGURATION_INSTANTIATION_LOCK () { throw new RuntimeException(); }
  /**
   * The three methods below are helpers for accessing the local map, a property of the SparkEnv of
   * the local process.
   */
  public  Object getCachedMetadata (java.lang.String key) { throw new RuntimeException(); }
  public  boolean containsCachedMetadata (java.lang.String key) { throw new RuntimeException(); }
  public  Object putCachedMetadata (java.lang.String key, Object value) { throw new RuntimeException(); }
  /** Add Hadoop configuration specific to a single partition and attempt. */
  public  void addLocalConfiguration (java.lang.String jobTrackerId, int jobId, int splitId, int attemptId, org.apache.hadoop.mapred.JobConf conf) { throw new RuntimeException(); }
}
