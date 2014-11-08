package org.apache.spark.partial;
/**
 * An ApproximateEvaluator for sums by key. Returns a map of key to confidence interval.
 */
private  class GroupedSumEvaluator<T extends java.lang.Object> implements org.apache.spark.partial.ApproximateEvaluator<java.util.HashMap<T, org.apache.spark.util.StatCounter>, scala.collection.Map<T, org.apache.spark.partial.BoundedDouble>> {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   GroupedSumEvaluator (int totalOutputs, double confidence) { throw new RuntimeException(); }
  public  int outputsMerged () { throw new RuntimeException(); }
  public  java.util.HashMap<T, org.apache.spark.util.StatCounter> sums () { throw new RuntimeException(); }
  public  void merge (int outputId, java.util.HashMap<T, org.apache.spark.util.StatCounter> taskResult) { throw new RuntimeException(); }
  public  scala.collection.Map<T, org.apache.spark.partial.BoundedDouble> currentResult () { throw new RuntimeException(); }
}
