package org.apache.spark.scheduler;
// no position
public  class InputFormatInfo$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final InputFormatInfo$ MODULE$ = null;
  public   InputFormatInfo$ () { throw new RuntimeException(); }
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
  public  scala.collection.immutable.Map<java.lang.String, scala.collection.immutable.Set<org.apache.spark.scheduler.SplitInfo>> computePreferredLocations (scala.collection.Seq<org.apache.spark.scheduler.InputFormatInfo> formats) { throw new RuntimeException(); }
}
