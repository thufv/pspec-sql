package org.apache.spark.util.random;
// no position
/** Contains benchmark method and main method to run benchmark of the RNG */
private  class XORShiftRandom$ implements scala.Serializable {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final XORShiftRandom$ MODULE$ = null;
  public   XORShiftRandom$ () { throw new RuntimeException(); }
  /** Hash seeds to have 0/1 bits throughout. */
  private  long hashSeed (long seed) { throw new RuntimeException(); }
  /**
   * Main method for running benchmark
   * @param args takes one argument - the number of random numbers to generate
   */
  public  void main (java.lang.String[] args) { throw new RuntimeException(); }
  /**
   * @param numIters Number of random numbers to generate while running the benchmark
   * @return Map of execution times for {@link java.util.Random java.util.Random}
   * and XORShift
   */
  public  scala.collection.immutable.Map<java.lang.String, java.lang.Object> benchmark (int numIters) { throw new RuntimeException(); }
}
