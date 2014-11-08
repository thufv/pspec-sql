package org.apache.spark;
/**
 * :: Experimental ::
 * A {@link FutureAction} for actions that could trigger multiple Spark jobs. Examples include take,
 * takeSample. Cancellation works by setting the cancelled flag to true and interrupting the
 * action thread if it is being blocked by a job.
 */
public  class ComplexFutureAction<T extends java.lang.Object> implements org.apache.spark.FutureAction<T> {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   ComplexFutureAction () { throw new RuntimeException(); }
  private  java.lang.Thread thread () { throw new RuntimeException(); }
  private  boolean _cancelled () { throw new RuntimeException(); }
  private  scala.concurrent.Promise<T> p () { throw new RuntimeException(); }
  public  void cancel () { throw new RuntimeException(); }
  /**
   * Executes some action enclosed in the closure. To properly enable cancellation, the closure
   * should use runJob implementation in this promise. See takeAsync for example.
   */
  public  org.apache.spark.ComplexFutureAction<T> run (scala.Function0<T> func, scala.concurrent.ExecutionContext executor) { throw new RuntimeException(); }
  /**
   * Runs a Spark job. This is a wrapper around the same functionality provided by SparkContext
   * to enable cancellation.
   */
  public <T extends java.lang.Object, U extends java.lang.Object, R extends java.lang.Object> void runJob (org.apache.spark.rdd.RDD<T> rdd, scala.Function1<scala.collection.Iterator<T>, U> processPartition, scala.collection.Seq<java.lang.Object> partitions, scala.Function2<java.lang.Object, U, scala.runtime.BoxedUnit> resultHandler, scala.Function0<R> resultFunc) { throw new RuntimeException(); }
  /**
   * Returns whether the promise has been cancelled.
   */
  public  boolean cancelled () { throw new RuntimeException(); }
  public  org.apache.spark.ComplexFutureAction<T> ready (scala.concurrent.duration.Duration atMost, scala.concurrent.CanAwait permit) { throw new RuntimeException(); }
  public  T result (scala.concurrent.duration.Duration atMost, scala.concurrent.CanAwait permit) { throw new RuntimeException(); }
  public <U extends java.lang.Object> void onComplete (scala.Function1<scala.util.Try<T>, U> func, scala.concurrent.ExecutionContext executor) { throw new RuntimeException(); }
  public  boolean isCompleted () { throw new RuntimeException(); }
  public  scala.Option<scala.util.Try<T>> value () { throw new RuntimeException(); }
}
