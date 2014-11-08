package org.apache.spark.scheduler;
/**
 * :: DeveloperApi ::
 * Parses and holds information about inputFormat (and files) specified as a parameter.
 */
public  class InputFormatInfo implements org.apache.spark.Logging {
  /**
   Computes the preferred locations based on input(s) and returned a location to block map.
   Typical use of this method for allocation would follow some algo like this:
   * <p>
   a) For each host, count number of splits hosted on that host.
   b) Decrement the currently allocated containers on that host.
   c) Compute rack info for each host and update rack -> count map based on (b).
   d) Allocate nodes based on (c)
   e) On the allocation result, ensure that we dont allocate "too many" jobs on a single node
   (even if data locality on that is very high) : this is to prevent fragility of job if a
   single (or small set of) hosts go down.
   * <p>
   go to (a) until required nodes are allocated.
   * <p>
   If a node 'dies', follow same procedure.
   * <p>
   PS: I know the wording here is weird, hopefully it makes some sense !
   */
  static public  scala.collection.immutable.Map<java.lang.String, scala.collection.immutable.Set<org.apache.spark.scheduler.SplitInfo>> computePreferredLocations (scala.collection.Seq<org.apache.spark.scheduler.InputFormatInfo> formats) { throw new RuntimeException(); }
  public  org.apache.hadoop.conf.Configuration configuration () { throw new RuntimeException(); }
  public  Object inputFormatClazz () { throw new RuntimeException(); }
  public  java.lang.String path () { throw new RuntimeException(); }
  // not preceding
  public   InputFormatInfo (org.apache.hadoop.conf.Configuration configuration, java.lang.Class<?> inputFormatClazz, java.lang.String path) { throw new RuntimeException(); }
  public  boolean mapreduceInputFormat () { throw new RuntimeException(); }
  public  boolean mapredInputFormat () { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
  public  int hashCode () { throw new RuntimeException(); }
  public  boolean equals (Object other) { throw new RuntimeException(); }
  private  void validate () { throw new RuntimeException(); }
  private  scala.collection.immutable.Set<org.apache.spark.scheduler.SplitInfo> prefLocsFromMapreduceInputFormat () { throw new RuntimeException(); }
  private  scala.collection.immutable.Set<org.apache.spark.scheduler.SplitInfo> prefLocsFromMapredInputFormat () { throw new RuntimeException(); }
  private  scala.collection.immutable.Set<org.apache.spark.scheduler.SplitInfo> findPreferredLocations () { throw new RuntimeException(); }
}
