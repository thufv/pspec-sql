package org.apache.spark.partial;
/**
 * A JobListener for an approximate single-result action, such as count() or non-parallel reduce().
 * This listener waits up to timeout milliseconds and will return a partial answer even if the
 * complete answer is not available by then.
 * <p>
 * This class assumes that the action is performed on an entire RDD[T] via a function that computes
 * a result of type U for each partition, and that the action returns a partial or complete result
 * of type R. Note that the type R must *include* any error bars on it (e.g. see BoundedInt).
 */
private  class ApproximateActionListener<T extends java.lang.Object, U extends java.lang.Object, R extends java.lang.Object> implements org.apache.spark.scheduler.JobListener {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   ApproximateActionListener (org.apache.spark.rdd.RDD<T> rdd, scala.Function2<org.apache.spark.TaskContext, scala.collection.Iterator<T>, U> func, org.apache.spark.partial.ApproximateEvaluator<U, R> evaluator, long timeout) { throw new RuntimeException(); }
  public  long startTime () { throw new RuntimeException(); }
  public  int totalTasks () { throw new RuntimeException(); }
  public  int finishedTasks () { throw new RuntimeException(); }
  public  scala.Option<java.lang.Exception> failure () { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.partial.PartialResult<R>> resultObject () { throw new RuntimeException(); }
  public  void taskSucceeded (int index, Object result) { throw new RuntimeException(); }
  public  void jobFailed (java.lang.Exception exception) { throw new RuntimeException(); }
  /**
   * Waits for up to timeout milliseconds since the listener was created and then returns a
   * PartialResult with the result so far. This may be complete if the whole job is done.
   */
  public  org.apache.spark.partial.PartialResult<R> awaitResult () { throw new RuntimeException(); }
}
