package org.apache.spark;
/**
 * :: DeveloperApi ::
 * Contextual information about a task which can be read or mutated during execution.
 * <p>
 * @param stageId stage id
 * @param partitionId index of the partition
 * @param attemptId the number of attempts to execute this task
 * @param runningLocally whether the task is running locally in the driver JVM
 * @param taskMetrics performance metrics of the task
 */
public  class TaskContext implements scala.Serializable {
  public  int stageId () { throw new RuntimeException(); }
  public  int partitionId () { throw new RuntimeException(); }
  public  long attemptId () { throw new RuntimeException(); }
  public  boolean runningLocally () { throw new RuntimeException(); }
  public  org.apache.spark.executor.TaskMetrics taskMetrics () { throw new RuntimeException(); }
  // not preceding
  public   TaskContext (int stageId, int partitionId, long attemptId, boolean runningLocally, org.apache.spark.executor.TaskMetrics taskMetrics) { throw new RuntimeException(); }
  public  int splitId () { throw new RuntimeException(); }
  private  scala.collection.mutable.ArrayBuffer<org.apache.spark.util.TaskCompletionListener> onCompleteCallbacks () { throw new RuntimeException(); }
  private  boolean interrupted () { throw new RuntimeException(); }
  private  boolean completed () { throw new RuntimeException(); }
  /** Checks whether the task has completed. */
  public  boolean isCompleted () { throw new RuntimeException(); }
  /** Checks whether the task has been killed. */
  public  boolean isInterrupted () { throw new RuntimeException(); }
  /**
   * Add a (Java friendly) listener to be executed on task completion.
   * This will be called in all situation - success, failure, or cancellation.
   * <p>
   * An example use is for HadoopRDD to register a callback to close the input stream.
   */
  public  org.apache.spark.TaskContext addTaskCompletionListener (org.apache.spark.util.TaskCompletionListener listener) { throw new RuntimeException(); }
  /**
   * Add a listener in the form of a Scala closure to be executed on task completion.
   * This will be called in all situation - success, failure, or cancellation.
   * <p>
   * An example use is for HadoopRDD to register a callback to close the input stream.
   */
  public  org.apache.spark.TaskContext addTaskCompletionListener (scala.Function1<org.apache.spark.TaskContext, scala.runtime.BoxedUnit> f) { throw new RuntimeException(); }
  /**
   * Add a callback function to be executed on task completion. An example use
   * is for HadoopRDD to register a callback to close the input stream.
   * Will be called in any situation - success, failure, or cancellation.
   * @param f Callback function.
   */
  public  void addOnCompleteCallback (scala.Function0<scala.runtime.BoxedUnit> f) { throw new RuntimeException(); }
  /** Marks the task as completed and triggers the listeners. */
  private  void markTaskCompleted () { throw new RuntimeException(); }
  /** Marks the task for interruption, i.e. cancellation. */
  private  void markInterrupted () { throw new RuntimeException(); }
}
