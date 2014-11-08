package org.apache.spark.util;
/**
 * A class for tracking the statistics of a set of numbers (count, mean and variance) in a
 * numerically robust way. Includes support for merging two StatCounters. Based on Welford
 * and Chan's {@link http://en.wikipedia.org/wiki/Algorithms_for_calculating_variance algorithms}
 * for running variance.
 * <p>
 * @constructor Initialize the StatCounter with the given values.
 */
public  class StatCounter implements scala.Serializable {
  /** Build a StatCounter from a list of values. */
  static public  org.apache.spark.util.StatCounter apply (scala.collection.TraversableOnce<java.lang.Object> values) { throw new RuntimeException(); }
  /** Build a StatCounter from a list of values passed as variable-length arguments. */
  static public  org.apache.spark.util.StatCounter apply (scala.collection.Seq<java.lang.Object> values) { throw new RuntimeException(); }
  public   StatCounter (scala.collection.TraversableOnce<java.lang.Object> values) { throw new RuntimeException(); }
  private  long n () { throw new RuntimeException(); }
  private  double mu () { throw new RuntimeException(); }
  private  double m2 () { throw new RuntimeException(); }
  private  double maxValue () { throw new RuntimeException(); }
  private  double minValue () { throw new RuntimeException(); }
  /** Initialize the StatCounter with no values. */
  public   StatCounter () { throw new RuntimeException(); }
  /** Add a value into this StatCounter, updating the internal statistics. */
  public  org.apache.spark.util.StatCounter merge (double value) { throw new RuntimeException(); }
  /** Add multiple values into this StatCounter, updating the internal statistics. */
  public  org.apache.spark.util.StatCounter merge (scala.collection.TraversableOnce<java.lang.Object> values) { throw new RuntimeException(); }
  /** Merge another StatCounter into this one, adding up the internal statistics. */
  public  org.apache.spark.util.StatCounter merge (org.apache.spark.util.StatCounter other) { throw new RuntimeException(); }
  /** Clone this StatCounter */
  public  org.apache.spark.util.StatCounter copy () { throw new RuntimeException(); }
  public  long count () { throw new RuntimeException(); }
  public  double mean () { throw new RuntimeException(); }
  public  double sum () { throw new RuntimeException(); }
  public  double max () { throw new RuntimeException(); }
  public  double min () { throw new RuntimeException(); }
  /** Return the variance of the values. */
  public  double variance () { throw new RuntimeException(); }
  /**
   * Return the sample variance, which corrects for bias in estimating the variance by dividing
   * by N-1 instead of N.
   */
  public  double sampleVariance () { throw new RuntimeException(); }
  /** Return the standard deviation of the values. */
  public  double stdev () { throw new RuntimeException(); }
  /**
   * Return the sample standard deviation of the values, which corrects for bias in estimating the
   * variance by dividing by N-1 instead of N.
   */
  public  double sampleStdev () { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
