package org.apache.spark.scheduler;
// no position
public  class FakeTask$ implements scala.Serializable {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final FakeTask$ MODULE$ = null;
  public   FakeTask$ () { throw new RuntimeException(); }
  /**
   * Utility method to create a TaskSet, potentially setting a particular sequence of preferred
   * locations for each task (given as varargs) if this sequence is not empty.
   */
  public  org.apache.spark.scheduler.TaskSet createTaskSet (int numTasks, scala.collection.Seq<scala.collection.Seq<org.apache.spark.scheduler.TaskLocation>> prefLocs) { throw new RuntimeException(); }
}
