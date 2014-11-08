package org.apache.spark.partial;
/**
 * An ApproximateEvaluator for counts by key. Returns a map of key to confidence interval.
 */
private  class GroupedCountEvaluator<T extends java.lang.Object> implements org.apache.spark.partial.ApproximateEvaluator<org.apache.spark.util.collection.OpenHashMap<T, java.lang.Object>, scala.collection.Map<T, org.apache.spark.partial.BoundedDouble>> {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   GroupedCountEvaluator (int totalOutputs, double confidence, scala.reflect.ClassTag<T> evidence$1) { throw new RuntimeException(); }
  public  int outputsMerged () { throw new RuntimeException(); }
  public  org.apache.spark.util.collection.OpenHashMap<T, java.lang.Object> sums () { throw new RuntimeException(); }
  public  void merge (int outputId, org.apache.spark.util.collection.OpenHashMap<T, java.lang.Object> taskResult) { throw new RuntimeException(); }
  public  scala.collection.Map<T, org.apache.spark.partial.BoundedDouble> currentResult () { throw new RuntimeException(); }
}
