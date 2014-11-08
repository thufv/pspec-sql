package org.apache.spark.scheduler;
/**
 * A Task implementation that results in a large serialized task.
 */
public  class LargeTask extends org.apache.spark.scheduler.Task<byte[]> {
  public   LargeTask (int stageId) { throw new RuntimeException(); }
  public  byte[] randomBuffer () { throw new RuntimeException(); }
  public  java.util.Random random () { throw new RuntimeException(); }
  public  byte[] runTask (org.apache.spark.TaskContext context) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.scheduler.TaskLocation> preferredLocations () { throw new RuntimeException(); }
}
