package org.apache.spark.util.random;
/**
 * :: DeveloperApi ::
 * A pseudorandom sampler. It is possible to change the sampled item type. For example, we might
 * want to add weights for stratified sampling or importance sampling. Should only use
 * transformations that are tied to the sampler and cannot be applied after sampling.
 * <p>
 * @tparam T item type
 * @tparam U sampled item type
 */
public abstract interface RandomSampler<T extends java.lang.Object, U extends java.lang.Object> extends org.apache.spark.util.random.Pseudorandom, scala.Cloneable, scala.Serializable {
  /** take a random sample */
  public abstract  scala.collection.Iterator<U> sample (scala.collection.Iterator<T> items) ;
  public  org.apache.spark.util.random.RandomSampler<T, U> clone () ;
}
