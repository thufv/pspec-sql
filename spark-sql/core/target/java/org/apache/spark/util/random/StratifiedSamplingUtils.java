package org.apache.spark.util.random;
// no position
/**
 * Auxiliary functions and data structures for the sampleByKey method in PairRDDFunctions.
 * <p>
 * Essentially, when exact sample size is necessary, we make additional passes over the RDD to
 * compute the exact threshold value to use for each stratum to guarantee exact sample size with
 * high probability. This is achieved by maintaining a waitlist of size O(log(s)), where s is the
 * desired sample size for each stratum.
 * <p>
 * Like in simple random sampling, we generate a random value for each item from the
 * uniform  distribution [0.0, 1.0]. All items with values <= min(values of items in the waitlist)
 * are accepted into the sample instantly. The threshold for instant accept is designed so that
 * s - numAccepted = O(sqrt(s)), where s is again the desired sample size. Thus, by maintaining a
 * waitlist size = O(sqrt(s)), we will be able to create a sample of the exact size s by adding
 * a portion of the waitlist to the set of items that are instantly accepted. The exact threshold
 * is computed by sorting the values in the waitlist and picking the value at (s - numAccepted).
 * <p>
 * Note that since we use the same seed for the RNG when computing the thresholds and the actual
 * sample, our computed thresholds are guaranteed to produce the desired sample size.
 * <p>
 * For more theoretical background on the sampling techniques used here, please refer to
 * http://jmlr.org/proceedings/papers/v28/meng13a.html
 */
private  class StratifiedSamplingUtils implements org.apache.spark.Logging {
  /** A random data generator that generates both uniform values and Poisson values. */
  static private  class RandomDataGenerator {
    public   RandomDataGenerator () { throw new RuntimeException(); }
    public  org.apache.spark.util.random.XORShiftRandom uniform () { throw new RuntimeException(); }
    public  cern.jet.random.Poisson poisson () { throw new RuntimeException(); }
    public  void reSeed (long seed) { throw new RuntimeException(); }
    public  int nextPoisson (double mean) { throw new RuntimeException(); }
    public  double nextUniform () { throw new RuntimeException(); }
  }
  /**
   * Count the number of items instantly accepted and generate the waitlist for each stratum.
   * <p>
   * This is only invoked when exact sample size is required.
   */
  static public <K extends java.lang.Object, V extends java.lang.Object> scala.collection.mutable.Map<K, org.apache.spark.util.random.AcceptanceResult> getAcceptanceResults (org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> rdd, boolean withReplacement, scala.collection.Map<K, java.lang.Object> fractions, scala.Option<scala.collection.Map<K, java.lang.Object>> counts, long seed) { throw new RuntimeException(); }
  /**
   * Returns the function used by aggregate to collect sampling statistics for each partition.
   */
  static public <K extends java.lang.Object, V extends java.lang.Object> scala.Function2<scala.collection.mutable.Map<K, org.apache.spark.util.random.AcceptanceResult>, scala.Tuple2<K, V>, scala.collection.mutable.Map<K, org.apache.spark.util.random.AcceptanceResult>> getSeqOp (boolean withReplacement, scala.collection.Map<K, java.lang.Object> fractions, org.apache.spark.util.random.StratifiedSamplingUtils.RandomDataGenerator rng, scala.Option<scala.collection.Map<K, java.lang.Object>> counts) { throw new RuntimeException(); }
  /**
   * Returns the function used combine results returned by seqOp from different partitions.
   */
  static public <K extends java.lang.Object> scala.Function2<scala.collection.mutable.Map<K, org.apache.spark.util.random.AcceptanceResult>, scala.collection.mutable.Map<K, org.apache.spark.util.random.AcceptanceResult>, scala.collection.mutable.Map<K, org.apache.spark.util.random.AcceptanceResult>> getCombOp () { throw new RuntimeException(); }
  /**
   * Given the result returned by getCounts, determine the threshold for accepting items to
   * generate exact sample size.
   * <p>
   * To do so, we compute sampleSize = math.ceil(size * samplingRate) for each stratum and compare
   * it to the number of items that were accepted instantly and the number of items in the waitlist
   * for that stratum. Most of the time, numAccepted <= sampleSize <= (numAccepted + numWaitlisted),
   * which means we need to sort the elements in the waitlist by their associated values in order
   * to find the value T s.t. |{elements in the stratum whose associated values <= T}| = sampleSize.
   * Note that all elements in the waitlist have values >= bound for instant accept, so a T value
   * in the waitlist range would allow all elements that were instantly accepted on the first pass
   * to be included in the sample.
   */
  static public <K extends java.lang.Object> scala.collection.Map<K, java.lang.Object> computeThresholdByKey (scala.collection.Map<K, org.apache.spark.util.random.AcceptanceResult> finalResult, scala.collection.Map<K, java.lang.Object> fractions) { throw new RuntimeException(); }
  /**
   * Return the per partition sampling function used for sampling without replacement.
   * <p>
   * When exact sample size is required, we make an additional pass over the RDD to determine the
   * exact sampling rate that guarantees sample size with high confidence.
   * <p>
   * The sampling function has a unique seed per partition.
   */
  static public <K extends java.lang.Object, V extends java.lang.Object> scala.Function2<java.lang.Object, scala.collection.Iterator<scala.Tuple2<K, V>>, scala.collection.Iterator<scala.Tuple2<K, V>>> getBernoulliSamplingFunction (org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> rdd, scala.collection.Map<K, java.lang.Object> fractions, boolean exact, long seed) { throw new RuntimeException(); }
  /**
   * Return the per partition sampling function used for sampling with replacement.
   * <p>
   * When exact sample size is required, we make two additional passed over the RDD to determine
   * the exact sampling rate that guarantees sample size with high confidence. The first pass
   * counts the number of items in each stratum (group of items with the same key) in the RDD, and
   * the second pass uses the counts to determine exact sampling rates.
   * <p>
   * The sampling function has a unique seed per partition.
   */
  static public <K extends java.lang.Object, V extends java.lang.Object> scala.Function2<java.lang.Object, scala.collection.Iterator<scala.Tuple2<K, V>>, scala.collection.Iterator<scala.Tuple2<K, V>>> getPoissonSamplingFunction (org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> rdd, scala.collection.Map<K, java.lang.Object> fractions, boolean exact, long seed, scala.reflect.ClassTag<K> evidence$1, scala.reflect.ClassTag<V> evidence$2) { throw new RuntimeException(); }
}
