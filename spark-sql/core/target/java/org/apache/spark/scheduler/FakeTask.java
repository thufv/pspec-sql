package org.apache.spark.scheduler;
public  class FakeTask extends org.apache.spark.scheduler.Task<java.lang.Object> {
  /**
   * Utility method to create a TaskSet, potentially setting a particular sequence of preferred
   * locations for each task (given as varargs) if this sequence is not empty.
   */
  static public  org.apache.spark.scheduler.TaskSet createTaskSet (int numTasks, scala.collection.Seq<scala.collection.Seq<org.apache.spark.scheduler.TaskLocation>> prefLocs) { throw new RuntimeException(); }
  public   FakeTask (int stageId, scala.collection.Seq<org.apache.spark.scheduler.TaskLocation> prefLocs) { throw new RuntimeException(); }
  public  int runTask (org.apache.spark.TaskContext context) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.scheduler.TaskLocation> preferredLocations () { throw new RuntimeException(); }
}
