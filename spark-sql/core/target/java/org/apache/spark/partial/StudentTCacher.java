package org.apache.spark.partial;
/**
 * A utility class for caching Student's T distribution values for a given confidence level
 * and various sample sizes. This is used by the MeanEvaluator to efficiently calculate
 * confidence intervals for many keys.
 */
private  class StudentTCacher {
  public   StudentTCacher (double confidence) { throw new RuntimeException(); }
  public  int NORMAL_APPROX_SAMPLE_SIZE () { throw new RuntimeException(); }
  public  double normalApprox () { throw new RuntimeException(); }
  public  double[] cache () { throw new RuntimeException(); }
  public  double get (long sampleSize) { throw new RuntimeException(); }
}
