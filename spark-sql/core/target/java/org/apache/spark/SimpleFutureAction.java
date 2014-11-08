package org.apache.spark;
/**
 * :: Experimental ::
 * A {@link FutureAction} holding the result of an action that triggers a single job. Examples include
 * count, collect, reduce.
 */
public  class SimpleFutureAction<T extends java.lang.Object> implements org.apache.spark.FutureAction<T> {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  private   SimpleFutureAction (org.apache.spark.scheduler.JobWaiter<?> jobWaiter, scala.Function0<T> resultFunc) { throw new RuntimeException(); }
  public  void cancel () { throw new RuntimeException(); }
  public  org.apache.spark.SimpleFutureAction<T> ready (scala.concurrent.duration.Duration atMost, scala.concurrent.CanAwait permit) { throw new RuntimeException(); }
  public  T result (scala.concurrent.duration.Duration atMost, scala.concurrent.CanAwait permit) { throw new RuntimeException(); }
  public <U extends java.lang.Object> void onComplete (scala.Function1<scala.util.Try<T>, U> func, scala.concurrent.ExecutionContext executor) { throw new RuntimeException(); }
  public  boolean isCompleted () { throw new RuntimeException(); }
  public  scala.Option<scala.util.Try<T>> value () { throw new RuntimeException(); }
  private  scala.util.Try<T> awaitResult () { throw new RuntimeException(); }
}
