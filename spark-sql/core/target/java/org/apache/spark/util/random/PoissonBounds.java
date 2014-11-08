package org.apache.spark.util.random;
// no position
/**
 * Utility functions that help us determine bounds on adjusted sampling rate to guarantee exact
 * sample sizes with high confidence when sampling with replacement.
 */
private  class PoissonBounds {
  /**
   * Returns a lambda such that Pr[X > s] is very small, where X ~ Pois(lambda).
   */
  static public  double getLowerBound (double s) { throw new RuntimeException(); }
  /**
   * Returns a lambda such that Pr[X < s] is very small, where X ~ Pois(lambda).
   * <p>
   * @param s sample size
   */
  static public  double getUpperBound (double s) { throw new RuntimeException(); }
  static private  double numStd (double s) { throw new RuntimeException(); }
}
