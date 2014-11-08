package org.apache.spark;
/**
 * :: Experimental ::
 * A future for the result of an action to support cancellation. This is an extension of the
 * Scala Future interface to support cancellation.
 */
public abstract interface FutureAction<T extends java.lang.Object> extends scala.concurrent.Future<T> {
  /**
   * Cancels the execution of this action.
   */
  public abstract  void cancel () ;
  /**
   * Blocks until this action completes.
   * @param atMost maximum wait time, which may be negative (no waiting is done), Duration.Inf
   *               for unbounded waiting, or a finite positive duration
   * @return this FutureAction
   */
  public abstract  org.apache.spark.FutureAction<T> ready (scala.concurrent.duration.Duration atMost, scala.concurrent.CanAwait permit) ;
  /**
   * Awaits and returns the result (of type T) of this action.
   * @param atMost maximum wait time, which may be negative (no waiting is done), Duration.Inf
   *               for unbounded waiting, or a finite positive duration
   * @throws Exception exception during action execution
   * @return the result value if the action is completed within the specific maximum wait time
   */
  public abstract  T result (scala.concurrent.duration.Duration atMost, scala.concurrent.CanAwait permit) ;
  /**
   * When this action is completed, either through an exception, or a value, applies the provided
   * function.
   */
  public abstract <U extends java.lang.Object> void onComplete (scala.Function1<scala.util.Try<T>, U> func, scala.concurrent.ExecutionContext executor) ;
  /**
   * Returns whether the action has already been completed with a value or an exception.
   */
  public abstract  boolean isCompleted () ;
  /**
   * The value of this Future.
   * <p>
   * If the future is not completed the returned value will be None. If the future is completed
   * the value will be Some(Success(t)) if it contains a valid result, or Some(Failure(error)) if
   * it contains an exception.
   */
  public abstract  scala.Option<scala.util.Try<T>> value () ;
  /**
   * Blocks and returns the result of this job.
   */
  public  T get () ;
}
