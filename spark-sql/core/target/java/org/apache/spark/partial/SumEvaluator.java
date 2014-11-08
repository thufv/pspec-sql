package org.apache.spark.partial;
/**
 * An ApproximateEvaluator for sums. It estimates the mean and the cont and multiplies them
 * together, then uses the formula for the variance of two independent random variables to get
 * a variance for the result and compute a confidence interval.
 */
private  class SumEvaluator implements org.apache.spark.partial.ApproximateEvaluator<org.apache.spark.util.StatCounter, org.apache.spark.partial.BoundedDouble> {
  public   SumEvaluator (int totalOutputs, double confidence) { throw new RuntimeException(); }
  public  int outputsMerged () { throw new RuntimeException(); }
  public  org.apache.spark.util.StatCounter counter () { throw new RuntimeException(); }
  public  void merge (int outputId, org.apache.spark.util.StatCounter taskResult) { throw new RuntimeException(); }
  public  org.apache.spark.partial.BoundedDouble currentResult () { throw new RuntimeException(); }
}
