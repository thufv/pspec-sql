package org.apache.spark.rdd;
// no position
/**
 * Enumeration to manage state transitions of an RDD through checkpointing
 * [ Initialized --> marked for checkpointing --> checkpointing in progress --> checkpointed ]
 */
private  class CheckpointState$ extends scala.Enumeration {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final CheckpointState$ MODULE$ = null;
  public   CheckpointState$ () { throw new RuntimeException(); }
  public  scala.Enumeration.Value Initialized () { throw new RuntimeException(); }
  public  scala.Enumeration.Value MarkedForCheckpoint () { throw new RuntimeException(); }
  public  scala.Enumeration.Value CheckpointingInProgress () { throw new RuntimeException(); }
  public  scala.Enumeration.Value Checkpointed () { throw new RuntimeException(); }
}
