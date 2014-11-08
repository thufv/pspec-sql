package org.apache.spark.util.random;
/**
 * :: DeveloperApi ::
 * A class with pseudorandom behavior.
 */
public  interface Pseudorandom {
  /** Set random seed. */
  public abstract  void setSeed (long seed) ;
}
