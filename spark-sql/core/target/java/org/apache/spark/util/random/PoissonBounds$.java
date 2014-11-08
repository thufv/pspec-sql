package org.apache.spark.util.random;
// no position
/**
 * Utility functions that help us determine bounds on adjusted sampling rate to guarantee exact
 * sample sizes with high confidence when sampling with replacement.
 */
private  class PoissonBounds$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final PoissonBounds$ MODULE$ = null;
  public   PoissonBounds$ () { throw new RuntimeException(); }
  /**
   * Returns a lambda such that Pr[X > s] is very small, where X ~ Pois(lambda).
   */
  public  double getLowerBound (double s) { throw new RuntimeException(); }
  /**
   * Returns a lambda such that Pr[X < s] is very small, where X ~ Pois(lambda).
   * <p>
   * @param s sample size
   */
  public  double getUpperBound (double s) { throw new RuntimeException(); }
  private  double numStd (double s) { throw new RuntimeException(); }
}
