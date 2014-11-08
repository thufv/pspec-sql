package org.apache.spark.util.random;
/**
 * :: DeveloperApi ::
 * A sampler based on values drawn from Poisson distribution.
 * <p>
 * @param mean Poisson mean
 * @tparam T item type
 */
public  class PoissonSampler<T extends java.lang.Object> implements org.apache.spark.util.random.RandomSampler<T, T> {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   PoissonSampler (double mean) { throw new RuntimeException(); }
  public  cern.jet.random.Poisson rng () { throw new RuntimeException(); }
  public  void setSeed (long seed) { throw new RuntimeException(); }
  public  scala.collection.Iterator<T> sample (scala.collection.Iterator<T> items) { throw new RuntimeException(); }
  public  org.apache.spark.util.random.PoissonSampler<T> clone () { throw new RuntimeException(); }
}
