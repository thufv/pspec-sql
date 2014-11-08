package org.apache.spark.scheduler;
/**
 * Description of a task that gets passed onto executors to be executed, usually created by
 * {@link TaskSetManager.resourceOffer}.
 */
private  class TaskDescription implements scala.Serializable {
  public  long taskId () { throw new RuntimeException(); }
  public  java.lang.String executorId () { throw new RuntimeException(); }
  public  java.lang.String name () { throw new RuntimeException(); }
  public  int index () { throw new RuntimeException(); }
  // not preceding
  public   TaskDescription (long taskId, java.lang.String executorId, java.lang.String name, int index, java.nio.ByteBuffer _serializedTask) { throw new RuntimeException(); }
  private  org.apache.spark.util.SerializableBuffer buffer () { throw new RuntimeException(); }
  public  java.nio.ByteBuffer serializedTask () { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
