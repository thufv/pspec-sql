package org.apache.spark.scheduler.local;
private  class KillTask implements scala.Product, scala.Serializable {
  public  long taskId () { throw new RuntimeException(); }
  public  boolean interruptThread () { throw new RuntimeException(); }
  // not preceding
  public   KillTask (long taskId, boolean interruptThread) { throw new RuntimeException(); }
}
