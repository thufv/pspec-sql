package org.apache.spark.partial;
/**
 * An ApproximateEvaluator for counts.
 * <p>
 * TODO: There's currently a lot of shared code between this and GroupedCountEvaluator. It might
 * be best to make this a special case of GroupedCountEvaluator with one group.
 */
private  class CountEvaluator implements org.apache.spark.partial.ApproximateEvaluator<java.lang.Object, org.apache.spark.partial.BoundedDouble> {
  public   CountEvaluator (int totalOutputs, double confidence) { throw new RuntimeException(); }
  public  int outputsMerged () { throw new RuntimeException(); }
  public  long sum () { throw new RuntimeException(); }
  public  void merge (int outputId, long taskResult) { throw new RuntimeException(); }
  public  org.apache.spark.partial.BoundedDouble currentResult () { throw new RuntimeException(); }
}
