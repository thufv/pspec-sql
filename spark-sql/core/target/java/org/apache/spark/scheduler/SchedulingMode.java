package org.apache.spark.scheduler;
// no position
/**
 *  "FAIR" and "FIFO" determines which policy is used
 *    to order tasks amongst a Schedulable's sub-queues
 *  "NONE" is used when the a Schedulable has no sub-queues.
 */
public  class SchedulingMode extends scala.Enumeration {
  static public  scala.Enumeration.Value FAIR () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value FIFO () { throw new RuntimeException(); }
  static public  scala.Enumeration.Value NONE () { throw new RuntimeException(); }
}
