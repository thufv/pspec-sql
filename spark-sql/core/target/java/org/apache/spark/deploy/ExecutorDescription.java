package org.apache.spark.deploy;
/**
 * Used to send state on-the-wire about Executors from Worker to Master.
 * This state is sufficient for the Master to reconstruct its internal data structures during
 * failover.
 */
private  class ExecutorDescription implements scala.Serializable {
  public  java.lang.String appId () { throw new RuntimeException(); }
  public  int execId () { throw new RuntimeException(); }
  public  int cores () { throw new RuntimeException(); }
  public  scala.Enumeration.Value state () { throw new RuntimeException(); }
  // not preceding
  public   ExecutorDescription (java.lang.String appId, int execId, int cores, scala.Enumeration.Value state) { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
