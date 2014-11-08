package org.apache.spark.partial;
/**
 * An ApproximateEvaluator for means.
 */
private  class MeanEvaluator implements org.apache.spark.partial.ApproximateEvaluator<org.apache.spark.util.StatCounter, org.apache.spark.partial.BoundedDouble> {
  public   MeanEvaluator (int totalOutputs, double confidence) { throw new RuntimeException(); }
  public  int outputsMerged () { throw new RuntimeException(); }
  public  org.apache.spark.util.StatCounter counter () { throw new RuntimeException(); }
  public  void merge (int outputId, org.apache.spark.util.StatCounter taskResult) { throw new RuntimeException(); }
  public  org.apache.spark.partial.BoundedDouble currentResult () { throw new RuntimeException(); }
}
