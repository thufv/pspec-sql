package org.apache.spark.scheduler;
// no position
/**
 * Handles transmission of tasks and their dependencies, because this can be slightly tricky. We
 * need to send the list of JARs and files added to the SparkContext with each task to ensure that
 * worker nodes find out about it, but we can't make it part of the Task because the user's code in
 * the task might depend on one of the JARs. Thus we serialize each task as multiple objects, by
 * first writing out its dependencies.
 */
private  class Task$ implements scala.Serializable {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final Task$ MODULE$ = null;
  public   Task$ () { throw new RuntimeException(); }
  /**
   * Serialize a task and the current app dependencies (files and JARs added to the SparkContext)
   */
  public  java.nio.ByteBuffer serializeWithDependencies (org.apache.spark.scheduler.Task<?> task, scala.collection.mutable.HashMap<java.lang.String, java.lang.Object> currentFiles, scala.collection.mutable.HashMap<java.lang.String, java.lang.Object> currentJars, org.apache.spark.serializer.SerializerInstance serializer) { throw new RuntimeException(); }
  /**
   * Deserialize the list of dependencies in a task serialized with serializeWithDependencies,
   * and return the task itself as a serialized ByteBuffer. The caller can then update its
   * ClassLoaders and deserialize the task.
   * <p>
   * @return (taskFiles, taskJars, taskBytes)
   */
  public  scala.Tuple3<scala.collection.mutable.HashMap<java.lang.String, java.lang.Object>, scala.collection.mutable.HashMap<java.lang.String, java.lang.Object>, java.nio.ByteBuffer> deserializeWithDependencies (java.nio.ByteBuffer serializedTask) { throw new RuntimeException(); }
}
