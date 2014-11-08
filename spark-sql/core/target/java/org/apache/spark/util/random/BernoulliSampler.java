package org.apache.spark.util.random;
/**
 * :: DeveloperApi ::
 * A sampler based on Bernoulli trials.
 * <p>
 * @param lb lower bound of the acceptance range
 * @param ub upper bound of the acceptance range
 * @param complement whether to use the complement of the range specified, default to false
 * @tparam T item type
 */
public  class BernoulliSampler<T extends java.lang.Object> implements org.apache.spark.util.random.RandomSampler<T, T> {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   BernoulliSampler (double lb, double ub, boolean complement) { throw new RuntimeException(); }
  public  java.util.Random rng () { throw new RuntimeException(); }
  public   BernoulliSampler (double ratio) { throw new RuntimeException(); }
  public  void setSeed (long seed) { throw new RuntimeException(); }
  public  scala.collection.Iterator<T> sample (scala.collection.Iterator<T> items) { throw new RuntimeException(); }
  /**
   *  Return a sampler that is the complement of the range specified of the current sampler.
   */
  public  org.apache.spark.util.random.BernoulliSampler<T> cloneComplement () { throw new RuntimeException(); }
  public  org.apache.spark.util.random.BernoulliSampler<T> clone () { throw new RuntimeException(); }
}
