package org.apache.spark.scheduler;
/**
 * A ShuffleMapTask divides the elements of an RDD into multiple buckets (based on a partitioner
 * specified in the ShuffleDependency).
 * <p>
 * See {@link org.apache.spark.scheduler.Task} for more information.
 * <p>
 * @param stageId id of the stage this task belongs to
 * @param taskBinary broadcast version of of the RDD and the ShuffleDependency. Once deserialized,
 *                   the type should be (RDD[_], ShuffleDependency[_, _, _]).
 * @param partition partition of the RDD this task is associated with
 * @param locs preferred task execution locations for locality scheduling
 */
private  class ShuffleMapTask extends org.apache.spark.scheduler.Task<org.apache.spark.scheduler.MapStatus> implements org.apache.spark.Logging {
  private  scala.collection.Seq<org.apache.spark.scheduler.TaskLocation> locs () { throw new RuntimeException(); }
  // not preceding
  public   ShuffleMapTask (int stageId, org.apache.spark.broadcast.Broadcast<byte[]> taskBinary, org.apache.spark.Partition partition, scala.collection.Seq<org.apache.spark.scheduler.TaskLocation> locs) { throw new RuntimeException(); }
  /** A constructor used only in test suites. This does not require passing in an RDD. */
  public   ShuffleMapTask (int partitionId) { throw new RuntimeException(); }
  private  scala.collection.Seq<org.apache.spark.scheduler.TaskLocation> preferredLocs () { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.MapStatus runTask (org.apache.spark.TaskContext context) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.scheduler.TaskLocation> preferredLocations () { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
