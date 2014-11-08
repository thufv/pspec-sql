package org.apache.spark.util;
/**
 * Util for getting some stats from a small sample of numeric values, with some handy
 * summary functions.
 * <p>
 * Entirely in memory, not intended as a good way to compute stats over large data sets.
 * <p>
 * Assumes you are giving it a non-empty set of data
 */
private  class Distribution {
  static public  scala.Option<org.apache.spark.util.Distribution> apply (scala.collection.Traversable<java.lang.Object> data) { throw new RuntimeException(); }
  public  double[] data () { throw new RuntimeException(); }
  public  int startIdx () { throw new RuntimeException(); }
  public  int endIdx () { throw new RuntimeException(); }
  // not preceding
  public   Distribution (double[] data, int startIdx, int endIdx) { throw new RuntimeException(); }
  public   Distribution (scala.collection.Traversable<java.lang.Object> data) { throw new RuntimeException(); }
  public  int length () { throw new RuntimeException(); }
  public  double[] defaultProbabilities () { throw new RuntimeException(); }
  /**
   * Get the value of the distribution at the given probabilities.  Probabilities should be
   * given from 0 to 1
   * @param probabilities
   */
  public  scala.collection.immutable.IndexedSeq<java.lang.Object> getQuantiles (scala.collection.Traversable<java.lang.Object> probabilities) { throw new RuntimeException(); }
  private  int closestIndex (double p) { throw new RuntimeException(); }
  public  void showQuantiles (java.io.PrintStream out) { throw new RuntimeException(); }
  public  org.apache.spark.util.StatCounter statCounter () { throw new RuntimeException(); }
  /**
   * print a summary of this distribution to the given PrintStream.
   * @param out
   */
  public  void summary (java.io.PrintStream out) { throw new RuntimeException(); }
}
